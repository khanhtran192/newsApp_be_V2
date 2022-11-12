package com.newsApp_be.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    private String email;
    private String avatar;
    private String linkAvatar;
    private Boolean sex; //0-Nữ ; 1- Nam
    private String fullName;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDay;
    private String phoneNumber;
    private String address;
}
