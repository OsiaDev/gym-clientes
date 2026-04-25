package com.holdings.gym.client.domain.ports.out;

import com.holdings.gym.client.domain.model.Cliente;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ClienteRepositoryPort {
    CompletableFuture<Cliente> save(Cliente cliente);
    CompletableFuture<Optional<Cliente>> findById(UUID uuid);
    CompletableFuture<Optional<Cliente>> findByDocumento(String documento);
}
