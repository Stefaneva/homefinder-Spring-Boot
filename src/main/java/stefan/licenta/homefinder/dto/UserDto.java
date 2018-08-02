package stefan.licenta.homefinder.dto;

import lombok.*;
import stefan.licenta.homefinder.entity.UserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String mail;
    private UserType userType;
    private String lastLoginDate;
    private Long phone;
    private Boolean enabled;
}