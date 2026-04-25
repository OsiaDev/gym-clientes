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

@Table("cliente_empresa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEmpresaEntity {
    @Id
    @Column("uuidclienteempresa")
    private UUID uuidClienteEmpresa;

    @Column("uuidcliente")
    private UUID uuidCliente;

    @Column("uuidempresa")
    private UUID uuidEmpresa;

    @Column("created_at")
    private LocalDateTime createdAt;
}
