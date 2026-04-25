package com.holdings.gym.client.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    private UUID uuidCliente;
    private String documentoCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String celularCliente;
    private String emailCliente;
    private Boolean estadoCliente;
    private LocalDateTime createdAt;
}
