package net.guilhermejr.sistema.energia.api.controller;

import net.guilhermejr.sistema.energia.util.TokenFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class TotalControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar um total")
    public void deve_retornar_um_total() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/total")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaGerada").value("0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaInjetada").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumidaConcessionaria").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumidaTotal").value("0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldoMes").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tusd").value("0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bandeira").value("0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iluminacaoPublica").value("0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.desconto").value("0.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").value("0.0"))
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());

    }

}
