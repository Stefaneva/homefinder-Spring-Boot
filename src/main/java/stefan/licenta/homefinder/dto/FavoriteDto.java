package stefan.licenta.homefinder.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDto {
    String userEmail;
    Long adId;
}
