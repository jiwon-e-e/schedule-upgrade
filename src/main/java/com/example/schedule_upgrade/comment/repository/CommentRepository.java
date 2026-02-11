package com.example.schedule_upgrade.comment.repository;

import com.example.schedule_upgrade.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllById(Long id);
}
