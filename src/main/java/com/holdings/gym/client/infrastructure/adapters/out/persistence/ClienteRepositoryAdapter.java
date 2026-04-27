package com.holdings.gym.client.infrastructure.adapters.out.persistence;

import com.holdings.gym.client.domain.model.Cliente;
import com.holdings.gym.client.domain.ports.out.ClienteRepositoryPort;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.entity.ClienteEntity;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.mapper.ClienteMapper;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    @Override
    public CompletableFuture<Cliente> save(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        log.debug("Ejecutando guardado de cliente en BD: {}", entity.getDocumentoCliente());
        return repository.insertCliente(entity)
                .flatMap(rows -> repository.findById(entity.getUuidCliente()))
                .map(mapper::toDomain)
                .doOnSuccess(c -> {
                    assert c != null;
                    log.debug("Guardado exitoso en BD para cliente: {}", c.getUuidCliente());
                })
                .doOnError(e -> log.error("Error al guardar cliente en BD: {}", e.getMessage()))
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<Cliente>> findById(UUID uuid) {
        return repository.findById(uuid)
                .map(mapper::toDomain)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<Cliente>> findByDocumento(String documento) {
        log.debug("Buscando en BD cliente con documento: {}", documento);
        return repository.findByDocumentoCliente(documento)
                .map(mapper::toDomain)
                .map(Optional::of)
                .doOnNext(c -> log.debug("Cliente encontrado en BD: {}", documento))
                .defaultIfEmpty(Optional.empty())
                .doOnSuccess(opt -> {
                    if (Objects.requireNonNull(opt).isEmpty()) {
                        log.debug("No se encontró ningún cliente en BD con documento: {}", documento);
                    }
                })
                .toFuture();
    }
    
}
