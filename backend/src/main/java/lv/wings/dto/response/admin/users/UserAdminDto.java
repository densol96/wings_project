package lv.wings.dto.response.admin.users;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminDto {
    private Integer id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean accountLocked;
    private boolean accountBanned;
    private String lastIpAddress;
    private String lastUserAgent;
    private int loginAttempts;
    LocalDateTime lastActivityDateTime;
}
