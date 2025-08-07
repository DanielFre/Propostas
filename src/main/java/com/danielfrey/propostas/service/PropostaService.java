package com.danielfrey.propostas.service;

import com.danielfrey.propostas.dto.PropostaRequest;
import com.danielfrey.propostas.dto.PropostaResponse;
import com.danielfrey.propostas.model.Parcela;
import com.danielfrey.propostas.model.Proposta;
import com.danielfrey.propostas.model.enums.StatusParcela;
import com.danielfrey.propostas.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private final PropostaRepository propostaRepository;

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
}