package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Supplier;
import com.doubleshan.scenery.repository.SupplierRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final GiftService giftService;

    public SupplierService(SupplierRepository supplierRepository, GiftService giftService) {
        this.supplierRepository = supplierRepository;
        this.giftService = giftService;
    }

    public Supplier create(String name) {
        Supplier s = new Supplier(name);
        supplierRepository.save(s);
        return s;
    }

    public List<Supplier> all() {
        return supplierRepository.findAll();
    }

    public Supplier get(String id) {
        return supplierRepository.findById(id).orElseThrow(() -> new NotFoundException("供应商不存在"));
    }

    public void assignSupplierToGift(String giftId, String supplierId) {
        get(supplierId); // 校验供应商存在
        com.doubleshan.scenery.model.Gift gift = giftService.get(giftId);
        gift.setSupplierId(supplierId);
    }
}
