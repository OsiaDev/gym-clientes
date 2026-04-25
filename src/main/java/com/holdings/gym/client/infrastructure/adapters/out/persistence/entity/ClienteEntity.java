package com.holdings.gym.client.infrastructure.adapters.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("cliente")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    @Id
    @Column("uuidcliente")
    private UUID uuidCliente;

    @Column("documentocliente")
    private String documentoCliente;

    @Column("nombrescliente")
    private String nombresCliente;

    @Column("apellidoscliente")
    private String apellidosCliente;

    @Column("celularcliente")
    private String celularCliente;

    @Column("emailcliente")
    private String emailCliente;

    @Column("estadocliente")
    private Boolean estadoCliente;

    @Column("created_at")
    private LocalDateTime createdAt;
}
