package com.ssafy.camping.board.model.service;

import java.sql.SQLException;
import java.util.*;

import com.ssafy.camping.board.dto.Review;
import com.ssafy.camping.board.model.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewMapper reviewMapper) {this.reviewMapper = reviewMapper;}

    public List<Review> getAllReview(int content_id, int now) throws SQLException{
        return reviewMapper.selectAll(content_id, now);
    }

    public void addReview(Review review) throws SQLException{
        reviewMapper.insertReview(review);
    }

    public void deleteByIdx(int idx) throws SQLException{
        reviewMapper.deleteByIdx(idx);
    }

    public void deleteByContent(int content_id) throws SQLException{
        reviewMapper.deleteByContent(content_id);
    }
}
