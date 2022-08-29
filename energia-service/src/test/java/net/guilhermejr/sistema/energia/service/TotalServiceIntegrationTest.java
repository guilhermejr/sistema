package net.guilhermejr.sistema.energia.service;

import net.guilhermejr.sistema.energia.api.response.TotalResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TotalServiceIntegrationTest {

    @Autowired
    private TotalService totalService;

    @Test
    @DisplayName("Deve retornar um total")
    public void deve_retornar_um_total() {

        TotalResponse total = totalService.retornar();

        Assertions.assertEquals(1L, total.getId());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getEnergiaGerada());
        Assertions.assertEquals(0, total.getEnergiaInjetada());
        Assertions.assertEquals(0, total.getEnergiaConsumidaConcessionaria());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getEnergiaConsumidaTotal());
        Assertions.assertEquals(0, total.getSaldoMes());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getTusd());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getTe());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getBandeira());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getIluminacaoPublica());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getDesconto());
        Assertions.assertEquals(new BigDecimal("0.00"), total.getValorTotal());

    }
}
