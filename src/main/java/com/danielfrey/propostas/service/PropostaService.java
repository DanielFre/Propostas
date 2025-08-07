package com.danielfrey.propostas.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielfrey.propostas.dto.PagamentoResponse;
import com.danielfrey.propostas.dto.ParcelaResponse;
import com.danielfrey.propostas.dto.PropostaDetalhadaResponse;
import com.danielfrey.propostas.dto.PropostaRequest;
import com.danielfrey.propostas.dto.PropostaResponse;
import com.danielfrey.propostas.model.Parcela;
import com.danielfrey.propostas.model.Proposta;
import com.danielfrey.propostas.model.enums.StatusParcela;
import com.danielfrey.propostas.repository.ParcelaRepository;
import com.danielfrey.propostas.repository.PropostaRepository;
import com.danielfrey.propostas.service.exception.ParcelaJaPagaException;
import com.danielfrey.propostas.service.exception.ParcelaNotFoundException;
import com.danielfrey.propostas.service.exception.PropostaNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;
    private final ParcelaRepository parcelaRepository;

    @Transactional
    public PropostaResponse criarProposta(PropostaRequest request) {
        // 1. Converte o DTO de requisição para a entidade Proposta
        Proposta proposta = new Proposta(
                request.getCpf(),
                request.getValorSolicitado(),
                request.getQuantidadeParcelas(),
                request.getDataSolicitacao());

        // 2. Gera as parcelas
        List<Parcela> parcelas = gerarParcelas(proposta);
        proposta.setParcelas(parcelas);

        // 3. Salva a proposta e as parcelas no banco de dados
        Proposta propostaSalva = propostaRepository.save(proposta);

        // 4. Retorna a resposta com o ID gerado
        return new PropostaResponse(propostaSalva.getId(), "Proposta criada com sucesso.");
    }

    private List<Parcela> gerarParcelas(Proposta proposta) {
        List<Parcela> parcelas = new ArrayList<>();
        BigDecimal valorParcela = proposta.getValorSolicitado()
                .divide(new BigDecimal(proposta.getQuantidadeParcelas()), 2, RoundingMode.HALF_UP);

        for (int i = 1; i <= proposta.getQuantidadeParcelas(); i++) {
            Parcela parcela = new Parcela();
            parcela.setProposta(proposta);
            parcela.setNumeroParcela(i);
            parcela.setValor(valorParcela);
            parcela.setDataVencimento(proposta.getDataSolicitacao().plusMonths(i));
            parcela.setStatus(StatusParcela.EM_ABERTO);
            parcelas.add(parcela);
        }
        return parcelas;
    }

    @Transactional(readOnly = true)
    public PropostaDetalhadaResponse buscarPorId(Long id) {
        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new PropostaNotFoundException("Proposta com ID " + id + " não encontrada."));

        return mapEntityToDto(proposta);
    }

    // Método auxiliar para mapear a Entidade para o DTO
    private PropostaDetalhadaResponse mapEntityToDto(Proposta proposta) {
        PropostaDetalhadaResponse response = new PropostaDetalhadaResponse();
        response.setId(proposta.getId());
        response.setCpf(proposta.getCpf());
        response.setValorSolicitado(proposta.getValorSolicitado());
        response.setQuantidadeParcelas(proposta.getQuantidadeParcelas());
        response.setDataSolicitacao(proposta.getDataSolicitacao());

        List<ParcelaResponse> parcelasResponse = proposta.getParcelas().stream().map(parcela -> {
            ParcelaResponse pr = new ParcelaResponse();
            pr.setNumeroParcela(parcela.getNumeroParcela());
            pr.setValor(parcela.getValor());
            pr.setDataVencimento(parcela.getDataVencimento());
            pr.setStatus(parcela.getStatus().getNome());
            return pr;
        }).collect(Collectors.toList());

        response.setParcelas(parcelasResponse);
        return response;
    }

    @Transactional(readOnly = true)
    public Page<PropostaDetalhadaResponse> listarTodos(Pageable pageable) {
        Page<Proposta> propostasPage = propostaRepository.findAll(pageable);
        return propostasPage.map(this::mapEntityToDto);
    }

    @Transactional
    public PagamentoResponse  pagarParcela(Long propostaId, Integer numeroParcela) {
        Parcela parcela = parcelaRepository.findByPropostaIdAndNumeroParcela(propostaId, numeroParcela)
                .orElseThrow(() -> new ParcelaNotFoundException(
                        "Parcela de número " + numeroParcela + " para a proposta " + propostaId
                                + " não foi encontrada."));

        if (StatusParcela.PAGA.equals(parcela.getStatus())) {
            throw new ParcelaJaPagaException("Esta parcela já foi paga.");
        }

        parcela.setStatus(StatusParcela.PAGA);
        parcelaRepository.save(parcela);
        
        return new PagamentoResponse("Pagamento da parcela " + numeroParcela + " da proposta " + propostaId + " realizado com sucesso.");
    }
}