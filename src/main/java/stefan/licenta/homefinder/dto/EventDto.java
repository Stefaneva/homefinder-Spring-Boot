package stefan.licenta.homefinder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EventDto {
    private Long eventId;
    private String userEmail;
    private Long adId;
    private String message;
    @JsonFormat(timezone = "GMT+03:00")
    private String startDate;
    @JsonFormat(timezone = "GMT+03:00")
    private String endDate;
    private String status;
}
