package com.trendyol.ecommerce.core.util;

import com.trendyol.ecommerce.core.domain.Campaign;
import com.trendyol.ecommerce.core.domain.DiscountType;
import com.trendyol.ecommerce.core.domain.Product;
import com.trendyol.ecommerce.core.domain.ShoppingCartItem;
import com.trendyol.ecommerce.core.entity.ShoppingCartItemEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class CampaignDiscountCalculatorImpl implements CampaignDiscountCalculator{

    @Override
    public void applyCampaignDiscount(ShoppingCartItem shoppingCartItem) {

        List<Campaign> campaigns = shoppingCartItem.getCampaigns();
        Product product = shoppingCartItem.getProduct();
        Integer quantity = shoppingCartItem.getQuantity();

        BigDecimal totalDiscountAmount = calculateTotalDisocunt(product, quantity, campaigns);
        shoppingCartItem.setDiscount(totalDiscountAmount);

        BigDecimal salePrice = shoppingCartItem.getPrice().subtract(totalDiscountAmount);
        shoppingCartItem.setSalePrice(salePrice);
    }

    @Override
    public void applyCampaignDiscount(ShoppingCartItemEntity shoppingCartItemEntity, Product product, List<Campaign> campaigns) {
        BigDecimal totalDiscountAmount = calculateTotalDisocunt(product, shoppingCartItemEntity.getQuantity(), campaigns);
        shoppingCartItemEntity.setDiscount(totalDiscountAmount);
        BigDecimal salePrice = shoppingCartItemEntity.getPrice().subtract(totalDiscountAmount);
        shoppingCartItemEntity.setSalePrice(salePrice);
    }

    private BigDecimal calculateTotalDisocunt(Product product, Integer quantity, List<Campaign> campaigns) {
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);
        BigDecimal totalDiscountAmount = BigDecimal.ZERO;
        for (Campaign campaign: campaigns) {
            BigDecimal discountAmount = BigDecimal.ZERO;
            DiscountType discountType = campaign.getDiscountType();
            if (discountType.isRate()) {
                discountAmount = product.getPrice().multiply(campaign.getDiscount()).divide(BigDecimal.valueOf(100)).multiply(quantityBigDecimal);
            } else if (discountType.isAmount()) {
                discountAmount = campaign.getDiscount().multiply(quantityBigDecimal);
            }
            totalDiscountAmount = totalDiscountAmount.add(discountAmount);
        }
        return totalDiscountAmount;
    }
}
