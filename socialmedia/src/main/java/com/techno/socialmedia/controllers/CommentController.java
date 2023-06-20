package com.techno.socialmedia.controllers;

import com.techno.socialmedia.dto.CommentDTO;
import com.techno.socialmedia.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@CrossOrigin("http://localhost:3000")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments()
    {
        List<CommentDTO> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
    @PostMapping("/create/{postId}/&/{userId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,
                                                    @PathVariable("postId") Long postId,
                                                    @PathVariable("userId") Long userId) {
        CommentDTO createdComment = commentService.createComment(commentDTO, postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable("commentId") Long commentId)
    {
        CommentDTO commentDTO = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentDTO);
    }

    @PutMapping("/{commentId}/edit")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("commentId") Long commentId,
                                                    @RequestBody CommentDTO commentDTO) {
        commentDTO.setId(commentId);
        CommentDTO updatedComment = commentService.updateComment(commentDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
