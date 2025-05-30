package kuit.baemin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class User {
    private Long userId;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
