package stefan.licenta.homefinder.dto;

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
    private Date startDate;
    private Date endDate;
    private String status;
}
