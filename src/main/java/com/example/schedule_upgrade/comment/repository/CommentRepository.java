package com.example.schedule_upgrade.comment.repository;

import com.example.schedule_upgrade.comment.entity.Comment;
import com.example.schedule_upgrade.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findAllById(Long id);

    Page<Comment> findAllBySchedule_Id(Long schedule_Id, Pageable pageable);

    int countBySchedule_Id(Long scheduleId);
}
