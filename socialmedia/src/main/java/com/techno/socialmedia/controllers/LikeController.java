package com.techno.socialmedia.controllers;

import com.techno.socialmedia.dto.LikeDTO;
import com.techno.socialmedia.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin("http://localhost:3000")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public LikeDTO createdLike(@RequestParam("userId")Long userId, @RequestParam("postId") Long postId)
    {
        return likeService.createLike(postId, userId);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable("likeId") Long likeId) {
        likeService.deleteLike(likeId);
        return ResponseEntity.noContent().build();
    }
}
