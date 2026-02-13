package com.example.schedule_upgrade.user.entity;

import com.example.schedule_upgrade.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String pw;

    // 양방향 매핑은 구현하지 않았습니다.
    // User 는 자신이 작성한 글이나 댓글을 확인할 때 추가적인 로직이 필요합니다.

    public User(String name, String email, String pw) {
        this.name = name;
        this.email = email;
        this.pw = pw;
    }

    public void update(String name) {
        this.name = name;
    }
}
