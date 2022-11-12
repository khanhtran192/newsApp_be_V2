package com.newsApp_be.entity;

import com.newsApp_be.dto.request.UserInfoDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String avatar;

    @NotBlank
    private String password;

    private String fullName;

    private String phoneNumber;


    private String verifyCode;

    private Boolean enable;

    private Timestamp createdTime;


    @JsonIgnore
    @OneToMany(mappedBy = "userCreator",fetch = FetchType.LAZY)
    private Set<Comment> listComments;

    @Enumerated(EnumType.STRING)
    private ERole role;


    public UserInfoDTO toUserInfoDTO() {
        return UserInfoDTO.builder()
                .avatar(this.avatar)
                .email(this.email)
                .fullName(this.fullName)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
