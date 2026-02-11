package com.example.schedule_upgrade.user.entity;

import com.example.schedule_upgrade.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String name;
    @NotBlank
    @Column(unique = true, comment = "이메일은 중복될 수 없습니다.")
    private String email;
    @NotBlank
    private String pw;

    public User(String name, String email, String pw) {
        this.name = name;
        this.email = email;
        this.pw = pw;
    }
}
