package com.ecommerce.discount.client.configuration;

public class UrlProperties {

    private static final String DISCOUNT_PATH = "http://discount/api/v1";

    public class Campaign {

        public static final String CREATE_CAMPAIGN_URL = DISCOUNT_PATH + "/campaigns";;
        public static final String EXIST_CAMPAIGN_BY_FILTER_URL = DISCOUNT_PATH + "/campaigns/exist";
        public static final String GET_CAMPAIGN_BY_ID_URL = DISCOUNT_PATH + "/campaigns/{campaignId}";
        public static final String CALCULATE_PRODUCT_DISCOUNT_IF_CAMPAIGN_EXISTS_URL = "/campaigns/calculateDiscount";
    }

    public class Coupon {

        public static final String CREATE_COUPON_URL = DISCOUNT_PATH + "/coupons";;
        public static final String GET_COUPON_BY_ID_URL = DISCOUNT_PATH + "/coupons/{couponId}";
        public static final String GET_THRESHOLD_FILTER_URL = DISCOUNT_PATH + "/coupons/thresholdFilter";
    }
}
