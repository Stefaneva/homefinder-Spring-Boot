package stefan.licenta.homefinder.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EventReportDto {
    private Integer pendingEvents;
    private Integer acceptedEvents;
    private Integer allEvents;
}
