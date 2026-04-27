package com.holdings.gym.client.infrastructure.adapters.in.web;

import com.holdings.gym.client.domain.model.Cliente;
import com.holdings.gym.client.domain.model.TipoDocumento;
import com.holdings.gym.client.domain.ports.in.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    @GetMapping("buscar-documento/{documento}")
    public CompletableFuture<ResponseEntity<Cliente>> buscarCliente(
            @PathVariable String documento) {
        log.info("Petición recibida para buscar cliente global por documento: {}", documento);
        
        return clienteUseCase.buscarGlobal(documento)
                .thenApply(client -> {
                    log.info("Cliente encontrado: {}", documento);
                    return ResponseEntity.ok(client);
                })
                .exceptionally(ex -> {
                    log.warn("Cliente no encontrado o error en búsqueda: {} - {}", documento, ex.getMessage());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<Cliente>> registrarCliente(
            @RequestBody Cliente cliente) {
        log.info("Petición para registrar nuevo cliente global. Documento: {}",
                cliente.getDocumentoCliente());
        
        return clienteUseCase.registrarClienteGlobal(cliente)
                .thenApply(c -> {
                    log.info("Cliente registrado con éxito globalmente. UUID: {}", c.getUuidCliente());
                    return ResponseEntity.status(HttpStatus.CREATED).body(c);
                })
                .exceptionally(ex -> {
                    log.error("Error al registrar cliente: {}", ex.getMessage(), ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @GetMapping("/tipos-documento")
    public ResponseEntity<List<TipoDocumento>> getTiposDocumento() {
        log.debug("Obteniendo lista de tipos de documento");
        return ResponseEntity.ok(Arrays.asList(TipoDocumento.values()));
    }

}
