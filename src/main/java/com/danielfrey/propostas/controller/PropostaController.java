package com.danielfrey.propostas.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.danielfrey.propostas.dto.PagamentoResponse;
import com.danielfrey.propostas.dto.PropostaDetalhadaResponse;
import com.danielfrey.propostas.dto.PropostaRequest;
import com.danielfrey.propostas.dto.PropostaResponse;
import com.danielfrey.propostas.service.PropostaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/propostas")
@RequiredArgsConstructor
@Tag(name = "Propostas", description = "Endpoints para gerenciamento de propostas de crédito")
public class PropostaController {

    private final PropostaService propostaService;

    @Operation(summary = "Cria uma nova proposta", description = "Recebe os dados da proposta, cria o recurso no banco de dados e gera as parcelas associadas com status 'Em Aberto'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proposta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos (ex: CPF inválido, valor fora do limite)")
    })
    @PostMapping
    public ResponseEntity<PropostaResponse> criarProposta(@Valid @RequestBody PropostaRequest request) {
        PropostaResponse response = propostaService.criarProposta(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(summary = "Busca uma proposta por ID", description = "Retorna os detalhes completos de uma única proposta, incluindo a lista de parcelas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proposta encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Proposta não encontrada para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PropostaDetalhadaResponse> buscarPorId(@PathVariable Long id) {
        PropostaDetalhadaResponse proposta = propostaService.buscarPorId(id);
        return ResponseEntity.ok(proposta);
    }

    @Operation(summary = "Lista todas as propostas", description = "Retorna uma lista paginada de todas as propostas, incluindo suas respectivas parcelas. Os parâmetros de paginação e ordenação são opcionais.")
    @GetMapping
    public ResponseEntity<Page<PropostaDetalhadaResponse>> listarPaginado(Pageable pageable) {
        Page<PropostaDetalhadaResponse> propostas = propostaService.listarTodos(pageable);
        return ResponseEntity.ok(propostas);
    }

    @Operation(summary = "Paga uma parcela de uma proposta", description = "Altera o status de uma parcela específica de 'Em Aberto' para 'Paga'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento da parcela realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Proposta ou parcela não encontrada para os IDs informados"),
            @ApiResponse(responseCode = "409", description = "Conflito de negócio, a parcela já se encontra paga")
    })
    @PostMapping("/{propostaId}/parcelas/{numeroParcela}/pagar")
    public ResponseEntity<PagamentoResponse> pagarParcela(@PathVariable Long propostaId,
            @PathVariable Integer numeroParcela) {
        PagamentoResponse response = propostaService.pagarParcela(propostaId, numeroParcela);
        return ResponseEntity.ok(response);
    }
}