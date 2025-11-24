package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Spot;
import com.doubleshan.scenery.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;

    public List<Spot> listActive() {
        return spotRepository.listActive();
    }

    public Spot get(Long id) {
        return spotRepository.findById(id).orElse(null);
    }
}
