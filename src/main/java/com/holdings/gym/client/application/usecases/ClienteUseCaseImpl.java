package com.holdings.gym.client.application.usecases;

import com.holdings.gym.client.application.config.ExecutorConfig;
import com.holdings.gym.client.domain.model.Cliente;
import com.holdings.gym.client.domain.model.ClienteEmpresa;
import com.holdings.gym.client.domain.model.exceptions.ClienteNoRegistradoException;
import com.holdings.gym.client.domain.ports.in.ClienteUseCase;
import com.holdings.gym.client.domain.ports.out.ClienteEmpresaRepositoryPort;
import com.holdings.gym.client.domain.ports.out.ClienteRepositoryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
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
    public CompletableFuture<Cliente> buscarGlobal(String documento) {
        return CompletableFuture.supplyAsync(() -> {
            log.debug("Iniciando búsqueda global del cliente en el repositorio. Documento: {}", documento);
            return clienteRepository.findByDocumento(documento).join()
                    .orElseThrow(() -> {
                        log.warn("El cliente con documento {} no existe en la base de datos global", documento);
                        return new ClienteNoRegistradoException("El cliente con el documento " + documento + " no se encuentra registrado globalmente.");
                    });
        }, domainExecutor);
    }

    @Override
    public CompletableFuture<Cliente> registrarClienteGlobalYEmpresa(Cliente cliente, UUID empresaId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Iniciando flujo de registro global para cliente: {} {}", cliente.getNombresCliente(), cliente.getApellidosCliente());
            
            if (cliente.getUuidCliente() == null) {
                cliente.setUuidCliente(UUID.randomUUID());
            }
            if (cliente.getCreatedAt() == null) {
                cliente.setCreatedAt(LocalDateTime.now());
            }
            if (cliente.getEstadoCliente() == null) {
                cliente.setEstadoCliente(true);
            }

            log.debug("Guardando cliente en repositorio global...");
            Cliente guardado = clienteRepository.save(cliente).join();
            log.info("Cliente guardado globalmente con UUID: {}", guardado.getUuidCliente());

            log.debug("Asociando cliente {} a la empresa {}", guardado.getUuidCliente(), empresaId);
            ClienteEmpresa nuevaRelacion = ClienteEmpresa.builder()
                    .uuidClienteEmpresa(UUID.randomUUID())
                    .uuidCliente(guardado.getUuidCliente())
                    .uuidEmpresa(empresaId)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            clienteEmpresaRepository.save(nuevaRelacion).join();
            log.info("Relación Cliente-Empresa creada con éxito para empresa: {}", empresaId);

            return guardado;
        }, domainExecutor);
    }
    
}
