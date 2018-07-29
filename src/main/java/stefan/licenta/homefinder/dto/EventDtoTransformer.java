package stefan.licenta.homefinder.dto;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import stefan.licenta.homefinder.entity.Event;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Component
public class EventDtoTransformer {

    public EventDto transform(Event event){
        EventDto eventDto = EventDto.builder()
                .adId(event.getAd().getAdId())
                .userEmail(event.getUser().getEmail())
                .eventId(event.getEventId())
                .status(event.getStatus())
                .startDate(event.getStartDate().toString())
                .endDate(event.getEndDate().toString())
                .message(event.getMessage())
                .build();
        System.out.println(eventDto);
        return eventDto;
    }

    public List<EventDto> transformList(List<Event> events) {
        return events.stream().map(this::transform).collect(Collectors.toList());
    }
}
