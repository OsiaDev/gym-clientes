package com.holdings.gym.client.infrastructure.adapters.out.persistence.repository;

import com.holdings.gym.client.infrastructure.adapters.out.persistence.entity.ClienteEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClienteRepository extends ReactiveCrudRepository<ClienteEntity, UUID> {

    Mono<ClienteEntity> findByDocumentoCliente(String documentoCliente);

    @Modifying
    @Query("INSERT INTO cliente (uuidcliente, tipo_documento, numero_identificacion, nombrescliente, " +
            "apellidoscliente, celularcliente, emailcliente, estadocliente, created_at) " +
            "VALUES (:#{#e.uuidCliente}, :#{#e.tipoDocumento.name()}::tipo_documento, :#{#e.documentoCliente}, " +
            ":#{#e.nombresCliente}, :#{#e.apellidosCliente}, :#{#e.celularCliente}, " +
            ":#{#e.emailCliente}, :#{#e.estadoCliente}, :#{#e.createdAt})")
    Mono<Integer> insertCliente(@Param("e") ClienteEntity entity);

}
