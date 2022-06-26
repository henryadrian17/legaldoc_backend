package dev.henrymolina.legaldoc_web_services.Exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationError {
    private String error;
    private String field;
}
