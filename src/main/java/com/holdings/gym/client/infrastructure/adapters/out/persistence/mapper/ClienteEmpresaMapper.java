package com.holdings.gym.client.infrastructure.adapters.out.persistence.mapper;

import com.holdings.gym.client.domain.model.ClienteEmpresa;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.entity.ClienteEmpresaEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteEmpresaMapper {

    public ClienteEmpresa toDomain(ClienteEmpresaEntity entity) {
        if (entity == null) return null;
        return ClienteEmpresa.builder()
                .uuidClienteEmpresa(entity.getUuidClienteEmpresa())
                .uuidCliente(entity.getUuidCliente())
                .uuidEmpresa(entity.getUuidEmpresa())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ClienteEmpresaEntity toEntity(ClienteEmpresa domain) {
        if (domain == null) return null;
        return ClienteEmpresaEntity.builder()
                .uuidClienteEmpresa(domain.getUuidClienteEmpresa())
                .uuidCliente(domain.getUuidCliente())
                .uuidEmpresa(domain.getUuidEmpresa())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
