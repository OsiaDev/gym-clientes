package com.holdings.gym.client.infrastructure.adapters.out.persistence.mapper;

import com.holdings.gym.client.domain.model.Cliente;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        return Cliente.builder()
                .uuidCliente(entity.getUuidCliente())
                .tipoDocumento(entity.getTipoDocumento())
                .documentoCliente(entity.getDocumentoCliente())
                .nombresCliente(entity.getNombresCliente())
                .apellidosCliente(entity.getApellidosCliente())
                .celularCliente(entity.getCelularCliente())
                .emailCliente(entity.getEmailCliente())
                .estadoCliente(entity.getEstadoCliente())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ClienteEntity toEntity(Cliente domain) {
        if (domain == null) return null;
        return ClienteEntity.builder()
                .uuidCliente(domain.getUuidCliente())
                .tipoDocumento(domain.getTipoDocumento())
                .documentoCliente(domain.getDocumentoCliente())
                .nombresCliente(domain.getNombresCliente())
                .apellidosCliente(domain.getApellidosCliente())
                .celularCliente(domain.getCelularCliente())
                .emailCliente(domain.getEmailCliente())
                .estadoCliente(domain.getEstadoCliente())
                .createdAt(domain.getCreatedAt())
                .build();
    }
    
}
