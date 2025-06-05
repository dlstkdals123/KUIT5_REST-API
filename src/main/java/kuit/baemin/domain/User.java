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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User compareUser))
            return false;
        return compareUser.getUserId().equals(userId);
    }
}
