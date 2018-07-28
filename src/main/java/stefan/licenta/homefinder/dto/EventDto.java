package stefan.licenta.homefinder.dto;

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
    private String startDate;
    private String endDate;
    private String status;
}
