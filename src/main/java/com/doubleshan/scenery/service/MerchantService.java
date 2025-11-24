package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Merchant;
import com.doubleshan.scenery.repository.MerchantRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public Merchant create(Merchant m) {
        return merchantRepository.save(m);
    }

    public Merchant get(String id) {
        return merchantRepository.findById(id).orElseThrow(() -> new NotFoundException("商家不存在"));
    }

    public List<Merchant> all() {
        return merchantRepository.findAll();
    }

    public Merchant update(String id, String addr, String phone) {
        Merchant m = get(id);
        if (addr != null)
            m.setAddress(addr);
        if (phone != null)
            m.setPhone(phone);
        return m;
    }

    public void delete(String id) {
        merchantRepository.deleteById(id);
    }
}
