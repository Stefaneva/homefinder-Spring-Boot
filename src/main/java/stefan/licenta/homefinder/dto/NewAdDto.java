package stefan.licenta.homefinder.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewAdDto {
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
    private List<MultipartFile> uploadFiles;

    @Override
    public String toString() {
        return "NewAdDto{" +
                "title='" + title + '\'' +
                ", adType='" + adType + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", surface=" + surface +
                ", rooms=" + rooms +
                ", adItemType='" + adItemType + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
