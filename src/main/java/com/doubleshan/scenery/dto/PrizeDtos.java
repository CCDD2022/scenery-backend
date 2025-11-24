package com.doubleshan.scenery.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

public class PrizeDtos {
    @Data
    public static class ExchangeReq {
        @NotNull
        private Long prizeId;
    }

    @Data
    public static class ExchangeResp {
        private String exchangeCode;
    }

    @Data
    public static class AdjustPointsReq {
        @NotNull
        private Long userId;
        @NotNull
        private Integer points;
        private String reason;
    }
}
