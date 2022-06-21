package net.guilhermejr.sistema.energia.api.controller;

import net.guilhermejr.sistema.energia.service.TotalService;
import net.guilhermejr.sistema.energia.util.DTOFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TotalController.class)
@ActiveProfiles("test")
public class TotalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TotalService totalService;

    @Test
    @DisplayName("Deve retornar um total")
    public void deve_retornar_um_total() throws Exception {

        Mockito.when(totalService.retornar()).thenReturn(DTOFactory.totalResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/total")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaGerada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaInjetada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumida").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldoMes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tusd").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bandeira").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.iluminacaoPublica").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.desconto").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").exists())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(totalService).retornar();

    }
}
