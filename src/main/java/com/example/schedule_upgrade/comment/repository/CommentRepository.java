package com.example.schedule_upgrade.comment.repository;

import com.example.schedule_upgrade.comment.entity.Comment;
import com.example.schedule_upgrade.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Pageable 은 page와 관련된 정보를 갖고있습니다.
    // 매개변수 마지막에 입력해주면 Page 형태로 처리됩니다.
    Page<Comment> findAllBySchedule_Id(Long schedule_Id, Pageable pageable);

    int countBySchedule_Id(Long scheduleId);
}
