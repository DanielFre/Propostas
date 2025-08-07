package com.danielfrey.propostas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielfrey.propostas.dto.PropostaRequest;
import com.danielfrey.propostas.dto.PropostaResponse;
import com.danielfrey.propostas.model.Proposta;
import com.danielfrey.propostas.repository.ParcelaRepository;
import com.danielfrey.propostas.repository.PropostaRepository;

@ExtendWith(MockitoExtension.class)
class PropostaServiceTest {

    @InjectMocks // Cria uma instância do PropostaService e injeta os mocks abaixo nela
    private PropostaService propostaService;

    @Mock // Cria um mock (objeto falso) para o PropostaRepository
    private PropostaRepository propostaRepository;

    @Mock
    private ParcelaRepository parcelaRepository;

    @Test
    void deveCriarPropostaComSucesso() {
        // ARRANGE (Preparação)
        PropostaRequest request = new PropostaRequest();
        request.setCpf("12345678909");
        request.setValorSolicitado(new BigDecimal("2000.00"));
        request.setQuantidadeParcelas(10);
        
        Proposta propostaSalva = new Proposta();
        propostaSalva.setId(1L);

        // Quando o método save do repositório for chamado com qualquer Proposta,
        // retorna o objeto 'propostaSalva'.
        when(propostaRepository.save(any(Proposta.class))).thenReturn(propostaSalva);

        // ACT (Ação)
        PropostaResponse response = propostaService.criarProposta(request);

        // ASSERT (Verificação)
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Proposta criada com sucesso.", response.getMensagem());
    }
}