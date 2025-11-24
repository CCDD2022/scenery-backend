package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Merchant;
import com.doubleshan.scenery.model.POI;
import com.doubleshan.scenery.repository.MerchantRepository;
import com.doubleshan.scenery.repository.POIRepository;
import com.doubleshan.scenery.util.DistanceUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendService {
    private final POIRepository poiRepository;
    private final MerchantRepository merchantRepository;

    public RecommendService(POIRepository p, MerchantRepository m) {
        this.poiRepository = p;
        this.merchantRepository = m;
    }

    public List<POI> recommendPois(double lat, double lon, String sort) {
        return poiRepository.findAll().stream().sorted(getPoiComparator(lat, lon, sort)).collect(Collectors.toList());
    }

    private Comparator<POI> getPoiComparator(double lat, double lon, String sort) {
        if ("heat".equalsIgnoreCase(sort))
            return Comparator.comparingInt(POI::getHeat).reversed();
        return Comparator
                .comparingDouble(p -> DistanceUtil.distanceMeters(lat, lon, p.getLatitude(), p.getLongitude()));
    }

    public List<Merchant> recommendMerchants(double lat, double lon, String sort) {
        return merchantRepository.findAll().stream().sorted(getMerchantComparator(lat, lon, sort))
                .collect(Collectors.toList());
    }

    private Comparator<Merchant> getMerchantComparator(double lat, double lon, String sort) {
        if ("heat".equalsIgnoreCase(sort))
            return Comparator.comparingInt(Merchant::getHeat).reversed();
        return Comparator
                .comparingDouble(m -> DistanceUtil.distanceMeters(lat, lon, m.getLatitude(), m.getLongitude()));
    }
}
