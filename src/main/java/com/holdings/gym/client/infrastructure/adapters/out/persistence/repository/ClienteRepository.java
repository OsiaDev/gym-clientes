package com.holdings.gym.client.infrastructure.adapters.out.persistence.repository;

import com.holdings.gym.client.infrastructure.adapters.out.persistence.entity.ClienteEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClienteRepository extends ReactiveCrudRepository<ClienteEntity, UUID> {
    Mono<ClienteEntity> findByDocumentoCliente(String documentoCliente);
}
