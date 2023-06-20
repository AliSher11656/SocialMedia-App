package com.techno.socialmedia.services;

import com.techno.socialmedia.dto.CommentDTO;
import com.techno.socialmedia.dto.PostDTO;
import com.techno.socialmedia.dto.UserDTO;
import com.techno.socialmedia.entities.Comment;
import com.techno.socialmedia.entities.Post;
import com.techno.socialmedia.entities.User;
import com.techno.socialmedia.repositories.CommentRepository;
import com.techno.socialmedia.repositories.PostRepository;
import com.techno.socialmedia.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    public List<CommentDTO> getAllComments()
    {
        List<Comment> comments = (List<Comment>) commentRepository.findAll();
        return mapCommentsToDTO(comments);
    }
    public CommentDTO createComment(CommentDTO commentDTO, Long postId, Long userId) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);

        // Set the post and user for the comment
        comment.setPost(postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found")));
        comment.setUser(userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found")));

        Comment createdComment = commentRepository.save(comment);
        return modelMapper.map(createdComment, CommentDTO.class);
    }


    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return modelMapper.map(comment, CommentDTO.class);
    }

    public List<CommentDTO> getPostComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return mapCommentsToDTO(comments);
    }

    public CommentDTO updateComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        comment.setContent(commentDTO.getContent());

        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDTO.class);
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("Comment not found");
        }
        commentRepository.deleteById(commentId);
    }

    private List<CommentDTO> mapCommentsToDTO(List<Comment> comments) {
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }
}
