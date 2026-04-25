package com.holdings.gym.client.infrastructure.adapters.out.persistence.repository;

import com.holdings.gym.client.infrastructure.adapters.out.persistence.entity.ClienteEmpresaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClienteEmpresaRepository extends ReactiveCrudRepository<ClienteEmpresaEntity, UUID> {
    Mono<ClienteEmpresaEntity> findByUuidClienteAndUuidEmpresa(UUID uuidCliente, UUID uuidEmpresa);
}
