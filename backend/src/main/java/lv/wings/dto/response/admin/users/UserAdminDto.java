package lv.wings.dto.response.admin.users;

import java.time.LocalDateTime;
import java.util.Set;
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
    private Set<String> roles;
    private String status;
    private int loginAttempts;
    private LocalDateTime lastActivityDateTime;
    private LocalDateTime joinDateTime;
}
