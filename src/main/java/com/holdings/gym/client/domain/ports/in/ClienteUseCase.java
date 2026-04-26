package com.holdings.gym.client.domain.ports.in;

import com.holdings.gym.client.domain.model.Cliente;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ClienteUseCase {
    
    /**
     * Busca un cliente globalmente por su documento.
     * Si no existe, lanza ClienteNoRegistradoException.
     */
    CompletableFuture<Cliente> buscarGlobal(String documento);

    /**
     * Crea un cliente globalmente y lo asocia directamente a la empresa indicada.
     */
    CompletableFuture<Cliente> registrarClienteGlobalYEmpresa(Cliente cliente, UUID empresaId);
    
}
