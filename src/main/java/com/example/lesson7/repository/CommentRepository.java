package com.example.lesson7.repository;

import com.example.lesson7.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> findAllByUser_Id(Integer id);
}
