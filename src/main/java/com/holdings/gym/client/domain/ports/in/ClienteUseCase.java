package com.holdings.gym.client.domain.ports.in;

import com.holdings.gym.client.domain.model.Cliente;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ClienteUseCase {
    
    /**
     * Busca un cliente por documento. Si existe, verifica que esté asociado al Gimnasio (Empresa),
     * si no lo está, lo asocia automáticamente.
     * Si no existe globalmente, lanza ClienteNoRegistradoException.
     */
    CompletableFuture<Cliente> buscarOPermitirRegistro(String documento, UUID empresaId);

    /**
     * Crea un cliente globalmente y lo asocia directamente a la empresa indicada.
     */
    CompletableFuture<Cliente> registrarClienteGlobalYEmpresa(Cliente cliente, UUID empresaId);
}
