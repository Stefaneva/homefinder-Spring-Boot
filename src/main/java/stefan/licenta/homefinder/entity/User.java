package stefan.licenta.homefinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name="pass", nullable = false)
    private String password;

    @Column(name="user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "blocked", nullable = false)
    private boolean enabled;

    @Column(name = "last_password_reset_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

    @JsonIgnore
    @Column(name="last_login_date")
    private Date lastLoginDate;

    @JsonIgnore
    @Column(name="current_login_date")
    private Date currentLoginDate;
}
