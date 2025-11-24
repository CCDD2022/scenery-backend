package com.doubleshan.scenery.vo;

import com.doubleshan.scenery.model.Supplier;

public class SupplierVO {
    private String id;
    private String name;

    public static SupplierVO from(Supplier s) {
        if (s == null)
            return null;
        SupplierVO vo = new SupplierVO();
        vo.id = s.getId();
        vo.name = s.getName();
        return vo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
