package com.example.schedule_upgrade.user.entity;

import com.example.schedule_upgrade.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    public User(String name, String email, String pw) {
        this.name = name;
        this.email = email;
        this.pw = pw;
    }

    public void update(@Size(max=10) String name) {
        this.name = name;
    }
}
