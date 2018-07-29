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
public class EventDtoDate {
    private Long eventId;
    private String userEmail;
    private Long adId;
    private String message;
    @JsonFormat(timezone = "GMT+00:00")
    private Date startDate;
    @JsonFormat(timezone = "GMT+00:00")
    private Date endDate;
    private String status;
}
