package net.guilhermejr.sistema.energia.service;

import net.guilhermejr.sistema.energia.api.request.AcompanhamentoRequest;
import net.guilhermejr.sistema.energia.api.response.AcompanhamentoResponse;
import net.guilhermejr.sistema.energia.domain.entity.Acompanhamento;
import net.guilhermejr.sistema.energia.domain.repository.AcompanhamentoRepository;
import net.guilhermejr.sistema.energia.exception.ExceptionNotFound;
import net.guilhermejr.sistema.energia.util.DTOFactory;
import net.guilhermejr.sistema.energia.util.EntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AcompanhamentoServiceIntegrationTest {

    @Autowired
    private AcompanhamentoService acompanhamentoService;

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Test
    @DisplayName("Deve retornar lista ordenada de acompanhamentos")
    public void deve_retornar_lista_ordenada_de_acompanhamentos() {

        List<Acompanhamento> acompanhamentos = new ArrayList<>();
        acompanhamentos.add(EntityFactory.acompanhamento2);
        acompanhamentos.add(EntityFactory.acompanhamento3);
        acompanhamentos.add(EntityFactory.acompanhamento1);
        acompanhamentoRepository.saveAll(acompanhamentos);

        List<AcompanhamentoResponse> acompanhamentoResponses = acompanhamentoService.retornar();

        Assertions.assertEquals(3L, acompanhamentoRepository.count());
        Assertions.assertEquals(new BigDecimal("653.5"), acompanhamentoResponses.get(0).getEnergiaGerada());
        Assertions.assertEquals(new BigDecimal("772.5"), acompanhamentoResponses.get(1).getEnergiaGerada());
        Assertions.assertEquals(new BigDecimal("713.6"), acompanhamentoResponses.get(2).getEnergiaGerada());

    }

    @Test
    @DisplayName("Deve retornar um acompanhamento quando passar id existente")
    public void deve_retornar_um_acompanhamento_quando_passar_id_existente() {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

        AcompanhamentoResponse acompanhamentoRetorno = acompanhamentoService.retornarUm(acompanhamento.getId());

        Assertions.assertEquals(EntityFactory.acompanhamento1.getInicio(), acompanhamentoRetorno.getInicio());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getFim(), acompanhamentoRetorno.getFim());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getDias(), acompanhamentoRetorno.getDias());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaGerada(), acompanhamentoRetorno.getEnergiaGerada());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaInjetada(), acompanhamentoRetorno.getEnergiaInjetada());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaConsumida(), acompanhamentoRetorno.getEnergiaConsumida());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getSaldoMes(), acompanhamentoRetorno.getSaldoMes());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getTusd(), acompanhamentoRetorno.getTusd());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getTe(), acompanhamentoRetorno.getTe());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getBandeira(), acompanhamentoRetorno.getBandeira());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getIluminacaoPublica(), acompanhamentoRetorno.getIluminacaoPublica());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getDesconto(), acompanhamentoRetorno.getDesconto());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getValorTotal(), acompanhamentoRetorno.getValorTotal());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getCriado(), acompanhamentoRetorno.getCriado());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getAtualizado(), acompanhamentoRetorno.getAtualizado());

    }

    @Test
    @DisplayName("Deve retornar vazio quando tentar retornar acompanhamento com id inexistente")
    public void deve_retornar_vazio_quando_tentar_retornar_acompanhamento_com_id_inexistente() {

        Assertions.assertThrows(ExceptionNotFound.class, () -> {
            AcompanhamentoResponse acompanhamentoRetorno = acompanhamentoService.retornarUm(0L);
        });

    }

    @Test
    @DisplayName("Deve criar um acompanhamento")
    public void deve_criar_um_acompanhamento() {

        AcompanhamentoResponse acompanhamento = acompanhamentoService.inserir(DTOFactory.acompanhamentoRequest1);

        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getInicio(), acompanhamento.getInicio());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getFim(), acompanhamento.getFim());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getDias(), acompanhamento.getDias());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getEnergiaGerada(), acompanhamento.getEnergiaGerada());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getEnergiaInjetada(), acompanhamento.getEnergiaInjetada());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getEnergiaConsumida(), acompanhamento.getEnergiaConsumida());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getSaldoMes(), acompanhamento.getSaldoMes());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getTusd(), acompanhamento.getTusd());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getTe(), acompanhamento.getTe());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getBandeira(), acompanhamento.getBandeira());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getIluminacaoPublica(), acompanhamento.getIluminacaoPublica());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getDesconto(), acompanhamento.getDesconto());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getValorTotal(), acompanhamento.getValorTotal());

    }

    @Test
    @DisplayName("Deve atualizar um acompanhamento quando passar id existente")
    public void deve_atualizar_um_acompanhamento_quando_passar_id_existente() {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

        AcompanhamentoRequest acompanhamentoNovo = DTOFactory.acompanhamentoRequest1;

        AcompanhamentoResponse acompanhamentoAtualizado = acompanhamentoService.atualizar(acompanhamento.getId(), acompanhamentoNovo);

        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getInicio(), acompanhamentoAtualizado.getInicio());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getFim(), acompanhamentoAtualizado.getFim());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getDias(), acompanhamentoAtualizado.getDias());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getEnergiaGerada(), acompanhamentoAtualizado.getEnergiaGerada());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getEnergiaInjetada(), acompanhamentoAtualizado.getEnergiaInjetada());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getEnergiaConsumida(), acompanhamentoAtualizado.getEnergiaConsumida());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getSaldoMes(), acompanhamentoAtualizado.getSaldoMes());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getTusd(), acompanhamentoAtualizado.getTusd());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getTe(), acompanhamentoAtualizado.getTe());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getBandeira(), acompanhamentoAtualizado.getBandeira());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getIluminacaoPublica(), acompanhamentoAtualizado.getIluminacaoPublica());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getDesconto(), acompanhamentoAtualizado.getDesconto());
        Assertions.assertEquals(DTOFactory.acompanhamentoResponseAtualizar.getValorTotal(), acompanhamentoAtualizado.getValorTotal());

    }

    @Test
    @DisplayName("Deve lançar uma exceção quando tentar atualizar acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_quando_tentar_atualizar_acompanhamento_com_id_inexistente() {

        Assertions.assertThrows(ExceptionNotFound.class, () -> {
            acompanhamentoService.atualizar(0L, DTOFactory.acompanhamentoRequest1);
        });

    }

    @Test
    @DisplayName("Deve apagar um acompanhamento quando passar id existente")
    public void deve_apagar_um_acompanhamento_quando_passar_id_existente() {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

       acompanhamentoService.apagar(acompanhamento.getId());

        Optional<Acompanhamento> acompanhamentoApagado = acompanhamentoRepository.findById(acompanhamento.getId());

        Assertions.assertFalse(acompanhamentoApagado.isPresent());

    }

    @Test
    @DisplayName("Deve lançar uma exceção quando tentar apagar acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_quando_tentar_apagar_acompanhamento_com_id_inexistente() {

        Assertions.assertThrows(ExceptionNotFound.class, () -> {
            acompanhamentoService.apagar(0L);
        });

    }

}
