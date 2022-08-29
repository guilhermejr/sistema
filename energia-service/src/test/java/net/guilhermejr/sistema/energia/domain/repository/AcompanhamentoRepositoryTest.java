package net.guilhermejr.sistema.energia.domain.repository;

import net.guilhermejr.sistema.energia.domain.entity.Acompanhamento;
import net.guilhermejr.sistema.energia.util.EntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AcompanhamentoRepositoryTest {

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

        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAllByOrderByInicioAsc();

        Assertions.assertEquals(3L, acompanhamentoRepository.count());
        Assertions.assertEquals(new BigDecimal("590.2"), acompanhamentoList.get(0).getEnergiaGerada());
        Assertions.assertEquals(new BigDecimal("659.3"), acompanhamentoList.get(1).getEnergiaGerada());
        Assertions.assertEquals(new BigDecimal("678.4"), acompanhamentoList.get(2).getEnergiaGerada());

    }

    @Test
    @DisplayName("Deve retornar um acompanhamento quando passar id existente")
    public void deve_retornar_um_acompanhamento_quando_passar_id_existente() {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

        Optional<Acompanhamento> acompanhamentoRetorno = acompanhamentoRepository.findById(acompanhamento.getId());

        Assertions.assertTrue(acompanhamentoRetorno.isPresent());

        acompanhamento = acompanhamentoRetorno.get();

        Assertions.assertEquals(EntityFactory.acompanhamento1.getInicio(), acompanhamento.getInicio());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getFim(), acompanhamento.getFim());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getDias(), acompanhamento.getDias());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaGerada(), acompanhamento.getEnergiaGerada());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaInjetada(), acompanhamento.getEnergiaInjetada());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaConsumidaConcessionaria(), acompanhamento.getEnergiaConsumidaConcessionaria());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaConsumidaTotal(), acompanhamento.getEnergiaConsumidaTotal());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getSaldoMes(), acompanhamento.getSaldoMes());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getTusd(), acompanhamento.getTusd());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getTe(), acompanhamento.getTe());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getBandeira(), acompanhamento.getBandeira());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getIluminacaoPublica(), acompanhamento.getIluminacaoPublica());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getDesconto(), acompanhamento.getDesconto());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getValorTotal(), acompanhamento.getValorTotal());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getCriado(), acompanhamento.getCriado());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getAtualizado(), acompanhamento.getAtualizado());

    }

    @Test
    @DisplayName("Deve retornar vazio quando tentar retornar acompanhamento com id inexistente")
    public void deve_retornar_vazio_quando_tentar_retornar_acompanhamento_com_id_inexistente() {

        Optional<Acompanhamento> acompanhamentoRetorno = acompanhamentoRepository.findById(0L);
        Assertions.assertTrue(acompanhamentoRetorno.isEmpty());

    }

    @Test
    @DisplayName("Deve criar um acompanhamento")
    public void deve_criar_um_acompanhamento() {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);
        Long quantidade = acompanhamentoRepository.count();

        Assertions.assertEquals(1L, quantidade);
        Assertions.assertEquals(EntityFactory.acompanhamento1.getInicio(), acompanhamento.getInicio());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getFim(), acompanhamento.getFim());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getDias(), acompanhamento.getDias());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaGerada(), acompanhamento.getEnergiaGerada());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaInjetada(), acompanhamento.getEnergiaInjetada());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaConsumidaConcessionaria(), acompanhamento.getEnergiaConsumidaConcessionaria());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getEnergiaConsumidaTotal(), acompanhamento.getEnergiaConsumidaTotal());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getSaldoMes(), acompanhamento.getSaldoMes());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getTusd(), acompanhamento.getTusd());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getTe(), acompanhamento.getTe());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getBandeira(), acompanhamento.getBandeira());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getIluminacaoPublica(), acompanhamento.getIluminacaoPublica());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getDesconto(), acompanhamento.getDesconto());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getValorTotal(), acompanhamento.getValorTotal());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getCriado(), acompanhamento.getCriado());
        Assertions.assertEquals(EntityFactory.acompanhamento1.getAtualizado(), acompanhamento.getAtualizado());

    }

    @Test
    @DisplayName("Deve atualizar um acompanhamento quando passar id existente")
    public void deve_atualizar_um_acompanhamento_quando_passar_id_existente() {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

        Acompanhamento acompanhamentoNovo = EntityFactory.acompanhamento2;
        acompanhamentoNovo.setId(acompanhamento.getId());

        Acompanhamento acompanhamentoAtualizado = acompanhamentoRepository.save(acompanhamentoNovo);

        Assertions.assertEquals(EntityFactory.acompanhamento2.getInicio(), acompanhamentoAtualizado.getInicio());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getFim(), acompanhamentoAtualizado.getFim());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getDias(), acompanhamentoAtualizado.getDias());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getEnergiaGerada(), acompanhamentoAtualizado.getEnergiaGerada());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getEnergiaInjetada(), acompanhamentoAtualizado.getEnergiaInjetada());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getEnergiaConsumidaConcessionaria(), acompanhamentoAtualizado.getEnergiaConsumidaConcessionaria());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getEnergiaConsumidaTotal(), acompanhamentoAtualizado.getEnergiaConsumidaTotal());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getSaldoMes(), acompanhamentoAtualizado.getSaldoMes());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getTusd(), acompanhamentoAtualizado.getTusd());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getTe(), acompanhamentoAtualizado.getTe());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getBandeira(), acompanhamentoAtualizado.getBandeira());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getIluminacaoPublica(), acompanhamentoAtualizado.getIluminacaoPublica());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getDesconto(), acompanhamentoAtualizado.getDesconto());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getValorTotal(), acompanhamentoAtualizado.getValorTotal());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getCriado(), acompanhamentoAtualizado.getCriado());
        Assertions.assertEquals(EntityFactory.acompanhamento2.getAtualizado(), acompanhamentoAtualizado.getAtualizado());

    }

    @Test
    @DisplayName("Deve apagar um acompanhamento quando passar id existente")
    public void deve_apagar_um_acompanhamento_quando_passar_id_existente() {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

        acompanhamentoRepository.deleteById(acompanhamento.getId());

        Optional<Acompanhamento> acompanhamentoApagado = acompanhamentoRepository.findById(acompanhamento.getId());

        Assertions.assertFalse(acompanhamentoApagado.isPresent());

    }

    @Test
    @DisplayName("Deve lançar uma exceção quando tentar apagar acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_quando_tentar_apagar_acompanhamento_com_id_inexistente() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
           acompanhamentoRepository.deleteById(0L);
        });

    }

}
