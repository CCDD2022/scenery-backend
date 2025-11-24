package com.doubleshan.scenery.vo;

import java.util.Map;

public class PointsStatsVO {
    private int earned;
    private int spent;
    private int balance;

    public static PointsStatsVO from(Map<String, Object> m) {
        PointsStatsVO vo = new PointsStatsVO();
        vo.earned = (int) m.getOrDefault("earned", 0);
        vo.spent = (int) m.getOrDefault("spent", 0);
        vo.balance = (int) m.getOrDefault("balance", 0);
        return vo;
    }

    public int getEarned() {
        return earned;
    }

    public int getSpent() {
        return spent;
    }

    public int getBalance() {
        return balance;
    }
}
