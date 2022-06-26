package dev.henrymolina.legaldoc_web_services.services.utils.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardServiceResponse {
    private Object data;
    private ServiceStatus serviceStatus;
}
