package stefan.licenta.homefinder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @JsonFormat(timezone = "GMT+03:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @JsonFormat(timezone = "GMT+03:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    private String status;
}
