package com.trendyol.ecommerce.shoppingcart.service;

import com.trendyol.ecommerce.core.domain.*;
import com.trendyol.ecommerce.core.entity.ShoppingCartEntity;
import com.trendyol.ecommerce.core.exception.BusinessException;
import com.trendyol.ecommerce.core.util.CouponDiscountCalculator;
import com.trendyol.ecommerce.shoppingcart.calculator.ShoppingCartCalculator;
import com.trendyol.ecommerce.shoppingcart.mapper.ShoppingCartMapper;
import com.trendyol.ecommerce.shoppingcart.model.request.ShoppingCartCreateRequest;
import com.trendyol.ecommerce.shoppingcart.model.response.ShoppingCartCreateResponse;
import com.trendyol.ecommerce.shoppingcart.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.trendyol.ecommerce.core.exception.faultCode.ShoppingCartFaultCode.SHOPPING_CART_NOT_FOUND;
import static com.trendyol.ecommerce.core.exception.faultCode.UserFaultCode.USER_NOT_FOUND;

@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartItemService shoppingCartItemService;
    private final ShoppingCartCalculator shoppingCartCalculator;
    private final CouponDiscountCalculator couponDiscountCalculator;
    private final RestTemplate restTemplate;

    @Override
    public ShoppingCartCreateResponse createShoppingCart(ShoppingCartCreateRequest shoppingCartCreateRequest) {
        Long userId = shoppingCartCreateRequest.getUserId();
        boolean existsUser = existsUser(userId);
        if (!existsUser) {
            throw new BusinessException(USER_NOT_FOUND, userId);
        }
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.save(shoppingCartMapper.mapToEntity(shoppingCartCreateRequest));
        return shoppingCartMapper.mapToResponse(shoppingCartEntity);
    }

    @Override
    public ShoppingCart getShoppingCartById(Long shoppingCartId) {
        Optional<ShoppingCartEntity> optionalShoppingCartEntity = shoppingCartRepository.findById(shoppingCartId);
        if (!optionalShoppingCartEntity.isPresent()) {
            throw new BusinessException(SHOPPING_CART_NOT_FOUND, shoppingCartId);
        }
        ShoppingCartEntity shoppingCartEntity = optionalShoppingCartEntity.get();
        ShoppingCart shoppingCart = shoppingCartMapper.mapToDTO(shoppingCartEntity);

        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemService.getShoppingCartItemsByCartId(shoppingCartId);
        shoppingCart.setShoppingCartItems(shoppingCartItems);

        BigDecimal totalPrice = shoppingCartCalculator.calculateShoppingCartTotalPrice(shoppingCart.getShoppingCartItems());
        shoppingCart.setTotalPrice(totalPrice);

        BigDecimal salePrice = shoppingCartCalculator.calculateShoppingCartSalePrice(shoppingCart.getShoppingCartItems());
        shoppingCart.setSalePrice(salePrice);

        Integer totalQuantity = getTotalQuantity(shoppingCart);
        shoppingCart.setTotalQuantity(totalQuantity);

        Optional<Coupon> optionalCoupon = getCouponByTotalPrice(shoppingCart.getTotalPrice());
        if (optionalCoupon.isPresent()) {
            Coupon coupon = optionalCoupon.get();
            shoppingCart.setCoupon(coupon);
            applyDiscountIfCouponExists(shoppingCart);
        } else {
            BigDecimal discount = totalPrice.subtract(salePrice);
            shoppingCart.setDiscount(discount);
        }
        return shoppingCart;
    }

    private Optional<Coupon> getCouponByTotalPrice(BigDecimal totalPrice) {
        URI uri = UriComponentsBuilder.fromUriString("http://discount/api/v1/coupons/thresholdFilter").queryParam("price", String.valueOf(totalPrice)).build().toUri();
        ResponseEntity<Coupon> response = restTemplate.exchange(uri, HttpMethod.GET, null, Coupon.class);
        return Optional.ofNullable(response.getBody());
    }

    private Integer getTotalQuantity(ShoppingCart shoppingCart) {
        return shoppingCart.getShoppingCartItems().stream()
                .map(ShoppingCartItem::getQuantity)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private boolean existsUser(Long userId) {
        // request account-service, default true.
        return userId != null;
    }

    private void applyDiscountIfCouponExists(ShoppingCart shoppingCart) {
        Coupon coupon = shoppingCart.getCoupon();

        BigDecimal discount = couponDiscountCalculator.calculateCouponDiscount(coupon, shoppingCart.getSalePrice());

        BigDecimal totalDiscount = shoppingCart.getTotalPrice().subtract(shoppingCart.getSalePrice()).add(discount);
        shoppingCart.setDiscount(totalDiscount);

        BigDecimal totalSale = shoppingCart.getTotalPrice().subtract(totalDiscount);
        shoppingCart.setSalePrice(totalSale);
    }
}
