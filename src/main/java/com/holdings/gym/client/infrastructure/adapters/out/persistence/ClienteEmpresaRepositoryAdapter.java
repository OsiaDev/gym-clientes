package com.holdings.gym.client.infrastructure.adapters.out.persistence;

import com.holdings.gym.client.domain.model.ClienteEmpresa;
import com.holdings.gym.client.domain.ports.out.ClienteEmpresaRepositoryPort;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.mapper.ClienteEmpresaMapper;
import com.holdings.gym.client.infrastructure.adapters.out.persistence.repository.ClienteEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ClienteEmpresaRepositoryAdapter implements ClienteEmpresaRepositoryPort {

    private final ClienteEmpresaRepository repository;
    private final ClienteEmpresaMapper mapper;

    @Override
    public CompletableFuture<ClienteEmpresa> save(ClienteEmpresa clienteEmpresa) {
        return repository.save(mapper.toEntity(clienteEmpresa))
                .map(mapper::toDomain)
                .toFuture();
    }

    @Override
    public CompletableFuture<Optional<ClienteEmpresa>> findByUuidClienteAndUuidEmpresa(UUID uuidCliente, UUID uuidEmpresa) {
        return repository.findByUuidClienteAndUuidEmpresa(uuidCliente, uuidEmpresa)
                .map(mapper::toDomain)
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .toFuture();
    }
}
