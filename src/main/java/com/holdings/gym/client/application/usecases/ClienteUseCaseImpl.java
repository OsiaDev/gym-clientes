package com.holdings.gym.client.application.usecases;

import com.holdings.gym.client.application.config.ExecutorConfig;
import com.holdings.gym.client.domain.model.Cliente;
import com.holdings.gym.client.domain.model.ClienteEmpresa;
import com.holdings.gym.client.domain.model.exceptions.ClienteNoRegistradoException;
import com.holdings.gym.client.domain.ports.in.ClienteUseCase;
import com.holdings.gym.client.domain.ports.out.ClienteEmpresaRepositoryPort;
import com.holdings.gym.client.domain.ports.out.ClienteRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class ClienteUseCaseImpl implements ClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;
    private final ClienteEmpresaRepositoryPort clienteEmpresaRepository;
    private final Executor domainExecutor;

    public ClienteUseCaseImpl(
            ClienteRepositoryPort clienteRepository,
            ClienteEmpresaRepositoryPort clienteEmpresaRepository,
            @Qualifier(ExecutorConfig.DOMAIN_EXECUTOR) Executor domainExecutor) {
        this.clienteRepository = clienteRepository;
        this.clienteEmpresaRepository = clienteEmpresaRepository;
        this.domainExecutor = domainExecutor;
    }

    @Override
    public CompletableFuture<Cliente> buscarOPermitirRegistro(String documento, UUID empresaId) {
        return CompletableFuture.supplyAsync(() -> {
            Cliente cliente = clienteRepository.findByDocumento(documento).join()
                    .orElseThrow(() -> new ClienteNoRegistradoException("El cliente con el documento " + documento + " no se encuentra registrado globalmente."));

            Optional<ClienteEmpresa> ceOpt = clienteEmpresaRepository
                    .findByUuidClienteAndUuidEmpresa(cliente.getUuidCliente(), empresaId).join();

            if (ceOpt.isEmpty()) {
                ClienteEmpresa nuevaRelacion = ClienteEmpresa.builder()
                        .uuidClienteEmpresa(UUID.randomUUID())
                        .uuidCliente(cliente.getUuidCliente())
                        .uuidEmpresa(empresaId)
                        .createdAt(LocalDateTime.now())
                        .build();
                clienteEmpresaRepository.save(nuevaRelacion).join();
            }

            return cliente;
        }, domainExecutor);
    }

    @Override
    public CompletableFuture<Cliente> registrarClienteGlobalYEmpresa(Cliente cliente, UUID empresaId) {
        return CompletableFuture.supplyAsync(() -> {
            if (cliente.getUuidCliente() == null) {
                cliente.setUuidCliente(UUID.randomUUID());
            }
            if (cliente.getCreatedAt() == null) {
                cliente.setCreatedAt(LocalDateTime.now());
            }
            if (cliente.getEstadoCliente() == null) {
                cliente.setEstadoCliente(true);
            }

            Cliente guardado = clienteRepository.save(cliente).join();

            ClienteEmpresa nuevaRelacion = ClienteEmpresa.builder()
                    .uuidClienteEmpresa(UUID.randomUUID())
                    .uuidCliente(guardado.getUuidCliente())
                    .uuidEmpresa(empresaId)
                    .createdAt(LocalDateTime.now())
                    .build();
            clienteEmpresaRepository.save(nuevaRelacion).join();

            return guardado;
        }, domainExecutor);
    }
}
