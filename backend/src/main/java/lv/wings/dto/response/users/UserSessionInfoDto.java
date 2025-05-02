package lv.wings.dto.response.users;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserSessionInfoDto {
    private Integer id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> authorities;
}
