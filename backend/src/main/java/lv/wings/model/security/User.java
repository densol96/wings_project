package lv.wings.model.security;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.Where;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // can become inactive e.g. if login limits exceeded
    private boolean accountLocked = false;

    // can become banned e.g. if admin removes access
    private boolean accountBanned = false;

    @Column(length = 45)
    private String lastIpAddress;

    private String lastUserAgent;

    private int loginAttempts = 0;

    private LocalDateTime lastActivityDateTime;

    @Column(updatable = false, nullable = false)
    private LocalDateTime joinDateTime = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Builder
    public User(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean isSystemUser() {
        return "system".equalsIgnoreCase(this.username);
    }
}
