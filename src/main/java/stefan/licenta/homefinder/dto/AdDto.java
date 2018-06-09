package stefan.licenta.homefinder.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdDto {
    private Long id;
    private String title;
    private String adType;
    private String description;
    private Integer price;
    private Integer surface;
    private Integer rooms;
    private String adItemType;
    private Double lat;
    private Double lng;
    private String userEmail;
    private String image;
}
