package com.ssafy.camping.favorites.model.mapper;

import com.ssafy.camping.favorites.dto.Favorites;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface FavoritesMapper {
    public abstract List<Favorites> selectByUserIdx(int user_idx) throws SQLException;

    public abstract int selectExists(int content_id, int user_idx) throws SQLException;

    public abstract void insertFavorites(Favorites favorites) throws SQLException;

    public abstract void deleteFavorites(int content_id, int user_idx) throws SQLException;
}
