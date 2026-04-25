package com.holdings.gym.client.domain.ports.out;

import com.holdings.gym.client.domain.model.ClienteEmpresa;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ClienteEmpresaRepositoryPort {
    CompletableFuture<ClienteEmpresa> save(ClienteEmpresa clienteEmpresa);
    CompletableFuture<Optional<ClienteEmpresa>> findByUuidClienteAndUuidEmpresa(UUID uuidCliente, UUID uuidEmpresa);
}
