package com.newsApp_be.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvatarResponse {

    private String avatar;
    private String link;
}
