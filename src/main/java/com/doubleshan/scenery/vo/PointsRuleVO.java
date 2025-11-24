package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.PointsRule;

public class PointsRuleVO {
    private String id;
    private String code;
    private int points;
    private String description;

    public static PointsRuleVO from(PointsRule r) {
        if (r == null)
            return null;
        PointsRuleVO vo = new PointsRuleVO();
        vo.id = r.getId();
        vo.code = r.getCode();
        vo.points = r.getPoints();
        vo.description = r.getDescription();
        return vo;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }
}
