package stefan.licenta.homefinder.dto;

import lombok.*;
import stefan.licenta.homefinder.entity.UserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataDto {
    private String username;
    private String email;
    private UserType type;
    private Long phone;
    private String lastPasswordResetDate;
    private String lastLoginDate;
    private Boolean enabled;
}
