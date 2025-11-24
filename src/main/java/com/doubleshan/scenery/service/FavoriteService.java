package com.doubleshan.scenery.service;

import com.doubleshan.scenery.model.Favorite;
import com.doubleshan.scenery.repository.FavoriteRepository;
import com.doubleshan.scenery.response.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Favorite add(String userId, String poiId) {
        return favoriteRepository.findByUserIdAndPoiId(userId, poiId)
                .orElseGet(() -> favoriteRepository.save(new Favorite(userId, poiId)));
    }

    public List<Favorite> list(String userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public void remove(String id) {
        favoriteRepository.findById(id).orElseThrow(() -> new NotFoundException("收藏不存在"));
        favoriteRepository.deleteById(id);
    }
}
