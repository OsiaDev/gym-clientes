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
public class ClienteEmpresa {
    private UUID uuidClienteEmpresa;
    private UUID uuidCliente;
    private UUID uuidEmpresa;
    private LocalDateTime createdAt;
}
