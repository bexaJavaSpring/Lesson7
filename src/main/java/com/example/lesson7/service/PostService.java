package com.example.lesson7.service;

import com.example.lesson7.dto.ApiResponse;
import com.example.lesson7.dto.PostDTO;
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
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;


    public ApiResponse save(PostDTO postDTO) {

        Post post = new Post();
        Optional<User> optionalUser = userRepository.findById(postDTO.getUserId());

        if (optionalUser.isPresent()) {
            post.setUser(optionalUser.get());
            post.setName(postDTO.getName());
            Post save = postRepository.save(post);
            return new ApiResponse("Added!", true, save);
        }
        return new ApiResponse("No Added!",false);
    }


    public ApiResponse getOne(Integer id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isPresent() && byId.get().getActive()) {
            return new ApiResponse("One Post", true, byId.get());
        }
        return new ApiResponse("No content",false);
    }


    public ApiResponse edit(Integer id, PostDTO dto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            Optional<User> optionalUser = userRepository.findById(dto.getUserId());
            if (optionalUser.isPresent()){
                User user = optionalUser.get();
                post.setUser(user);

                List<Comment> commentList = commentRepository.findAllById(dto.getCommentIds());
                post.setComments(commentList);
                post.setName(dto.getName());
                Post save = postRepository.save(post);
                return new ApiResponse("Edit!",true,save);
            }
            return new ApiResponse("No User",false);
        }
        return new ApiResponse("No Post",false);
    }

    public ApiResponse getAll() {
        List<Post> postList=new ArrayList<>();

        for (Post post : postRepository.findAll()) {
            if (post.getActive()){
                postList.add(post);
            }
        }
        return new ApiResponse("Get All Post",true,postList);
    }

    public ApiResponse delete(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();
            post.setActive(false);
            Post save = postRepository.save(post);
            return new ApiResponse("Delete!",true,optionalPost.get());
        }
        return new ApiResponse("Not found!",false);
    }
}
