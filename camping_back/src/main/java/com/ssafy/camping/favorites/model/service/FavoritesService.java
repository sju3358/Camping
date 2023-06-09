package com.ssafy.camping.favorites.model.service;

import com.ssafy.camping.favorites.dto.Favorites;
import com.ssafy.camping.favorites.model.mapper.FavoritesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class FavoritesService {
    private final FavoritesMapper favoritesMapper;

    @Autowired
    public FavoritesService(FavoritesMapper favoritesMapper) {this.favoritesMapper = favoritesMapper;}

    public List<Favorites> getAllFavorites(int user_idx) throws SQLException{
        return favoritesMapper.selectByUserIdx(user_idx);
    }

    public int checkFavorites(int content_id, int user_idx) throws SQLException{
        return favoritesMapper.selectExists(content_id, user_idx);
    }

    public void addFavorites(Favorites favorites) throws SQLException{
        favoritesMapper.insertFavorites(favorites);
    }

    public void deleteFavorites(int content_id, int user_idx) throws SQLException{
        favoritesMapper.deleteFavorites(content_id, user_idx);
    }
}
