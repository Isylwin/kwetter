package nl.oscar.kwetter.service.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerError {
    private String message;
}
