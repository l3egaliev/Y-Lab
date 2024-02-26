package kg.rakhim.classes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReadingResponseDTO {
    private String user;
    private Integer value;
    private String type;
    private LocalDateTime dateTime;
    private String errorMessage;
}
