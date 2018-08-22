package stefan.licenta.homefinder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import stefan.licenta.homefinder.entity.User;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdDetailsDto {
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
    private UserDto userDetails;
    private String partitioning;
    private Integer comfort;
    private String furnished;
    private String floorLevel;
    private Integer areaSurface;
    private Integer yearBuilt;
    private Double avgAdReview;
}
