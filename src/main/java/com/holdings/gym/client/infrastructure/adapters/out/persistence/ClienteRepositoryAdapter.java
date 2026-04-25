package com.holdings.gym.client.infrastructure.adapters.out.persistence;

import com.holdings.gym.client.domain.model.Cliente;
import com.holdings.gym.client.domain.ports.out.ClienteRepositoryPort;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.mapper.ClienteMapper;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    @Override
    public CompletableFuture<Cliente> save(Cliente cliente) {
        return repository.save(mapper.toEntity(cliente))
                .map(mapper::toDomain)
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
        return repository.findByDocumentoCliente(documento)
                .map(mapper::toDomain)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }
}
