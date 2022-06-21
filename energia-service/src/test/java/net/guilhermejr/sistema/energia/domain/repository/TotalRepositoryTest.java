package net.guilhermejr.sistema.energia.domain.repository;

import net.guilhermejr.sistema.energia.domain.entity.Total;
import net.guilhermejr.sistema.energia.util.EntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TotalRepositoryTest {
    
    @Autowired
    private TotalRepository totalRepository;

    @Test
    @DisplayName("Deve retornar um total quando passar id existente")
    public void deve_retornar_um_total_quando_passar_id_existente() {

        Optional<Total> total = totalRepository.findById(1L);

        Assertions.assertTrue(total.isPresent());
        Assertions.assertEquals(new BigDecimal("0.00"), total.get().getEnergiaGerada());
        Assertions.assertEquals(0, total.get().getEnergiaInjetada());
        Assertions.assertEquals(0, total.get().getEnergiaConsumida());
        Assertions.assertEquals(0, total.get().getSaldoMes());
        Assertions.assertEquals(new BigDecimal("0.00"), total.get().getTusd());
        Assertions.assertEquals(new BigDecimal("0.00"), total.get().getTe());
        Assertions.assertEquals(new BigDecimal("0.00"), total.get().getBandeira());
        Assertions.assertEquals(new BigDecimal("0.00"), total.get().getIluminacaoPublica());
        Assertions.assertEquals(new BigDecimal("0.00"), total.get().getDesconto());
        Assertions.assertEquals(new BigDecimal("0.00"), total.get().getValorTotal());

    }

    @Test
    @DisplayName("Deve atualizar total")
    public void deve_atualizar_total() {

        Total total = totalRepository.save(EntityFactory.total);

        Assertions.assertEquals(1L, total.getId());
        Assertions.assertEquals(EntityFactory.total.getEnergiaGerada(), total.getEnergiaGerada());
        Assertions.assertEquals(EntityFactory.total.getEnergiaInjetada(), total.getEnergiaInjetada());
        Assertions.assertEquals(EntityFactory.total.getEnergiaConsumida(), total.getEnergiaConsumida());
        Assertions.assertEquals(EntityFactory.total.getSaldoMes(), total.getSaldoMes());
        Assertions.assertEquals(EntityFactory.total.getTusd(), total.getTusd());
        Assertions.assertEquals(EntityFactory.total.getTe(), total.getTe());
        Assertions.assertEquals(EntityFactory.total.getBandeira(), total.getBandeira());
        Assertions.assertEquals(EntityFactory.total.getIluminacaoPublica(), total.getIluminacaoPublica());
        Assertions.assertEquals(EntityFactory.total.getDesconto(), total.getDesconto());
        Assertions.assertEquals(EntityFactory.total.getValorTotal(), total.getValorTotal());

    }
    
}
