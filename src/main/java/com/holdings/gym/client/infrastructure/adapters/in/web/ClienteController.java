package com.holdings.gym.client.infrastructure.adapters.in.web;

import com.holdings.gym.client.domain.model.Cliente;
import com.holdings.gym.client.domain.ports.in.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/gym/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @GetMapping("/{documento}")
    public CompletableFuture<ResponseEntity<Cliente>> buscarCliente(
            @PathVariable String documento,
            @RequestParam UUID empresaId) {
        
        return clienteUseCase.buscarOPermitirRegistro(documento, empresaId)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    // Aquí podríamos hacer match del ClienteNoRegistradoException
                    // para devolver un 404 NOT_FOUND. Por simplicidad devolvemos 400.
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Cliente>> registrarCliente(
            @RequestBody Cliente cliente,
            @RequestParam UUID empresaId) {
        
        return clienteUseCase.registrarClienteGlobalYEmpresa(cliente, empresaId)
                .thenApply(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

}
