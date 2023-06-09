package com.ssafy.camping.favorites.controller;

import com.ssafy.camping.board.model.mapper.BoardMapper;
import com.ssafy.camping.favorites.dto.Favorites;
import com.ssafy.camping.favorites.model.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequestMapping("/favorites")
@RestController
public class FavoritesController {
    private final FavoritesService favoritesService;

    @Autowired
    public FavoritesController(FavoritesService favoritesService) {this.favoritesService = favoritesService;}

    @GetMapping("/{user_idx}")
    public ResponseEntity<?> getFavoritesList(@PathVariable int user_idx) throws SQLException{
        System.out.println("a");
        List<Favorites> favoritesList = favoritesService.getAllFavorites(user_idx);
        return ResponseEntity.ok().body(favoritesList);
    }

    @GetMapping("/{content_id}/{user_idx}")
    public ResponseEntity<?> checkFavorites(@PathVariable int content_id, @PathVariable int user_idx) throws SQLException{
        return ResponseEntity.ok().body(favoritesService.checkFavorites(content_id, user_idx));
    }

    @PostMapping
    public ResponseEntity<?> registFavorites(@RequestBody Favorites favorites) throws SQLException{
        favoritesService.addFavorites(favorites);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{content_id}/{user_idx}")
    public ResponseEntity<?> deleteFavorites(@PathVariable int content_id, @PathVariable int user_idx) throws SQLException{
        favoritesService.deleteFavorites(content_id, user_idx);
        return ResponseEntity.ok().build();
    }
}
