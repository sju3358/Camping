package com.ssafy.camping.board.model.mapper;

import com.ssafy.camping.board.dto.Review;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ReviewMapper {

    public abstract List<Review> selectAll(int content_id, int now) throws SQLException;

    public abstract void insertReview(Review review) throws SQLException;

    public abstract void deleteByIdx(int idx) throws SQLException;

    public abstract void deleteByContent(int content_id) throws SQLException;
}
