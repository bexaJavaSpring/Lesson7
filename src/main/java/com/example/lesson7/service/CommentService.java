package com.example.lesson7.service;

import com.example.lesson7.dto.ApiResponse;
import com.example.lesson7.dto.CommentDto;
import com.example.lesson7.entity.Comment;
import com.example.lesson7.entity.Post;
import com.example.lesson7.entity.User;
import com.example.lesson7.repository.CommentRepository;
import com.example.lesson7.repository.PostRepository;
import com.example.lesson7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse getAllCommentPostId(Integer id) {
        List<Comment> allById = commentRepository.findAllByUser_Id(id);
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : allById) {
            if (comment.getActive()) {
                commentList.add(comment);
            }
        }
        return new ApiResponse("All Comments", true, commentList);
    }


    public ApiResponse delete(Integer id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setActive(false);
            commentRepository.save(comment);
            return new ApiResponse("Delete!", true, optionalComment.get());
        }

        return new ApiResponse("No delete comment!", false);
    }

    public ApiResponse save(CommentDto dto) {
        Comment comment = new Comment();
        Optional<Post> optionalPost = postRepository.findById(dto.getPostId());
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalPost.isPresent()) {

            if (optionalUser.isPresent()) {
                comment.setPost(optionalPost.get());
                comment.setText(dto.getText());
                comment.setUser(optionalUser.get());
                Comment save = commentRepository.save(comment);
                return new ApiResponse("Added Comment", true, save);
            }
            return new ApiResponse("No User", false);

        }
        return new ApiResponse("No post", false);

    }

}
