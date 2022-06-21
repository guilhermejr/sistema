package net.guilhermejr.sistema.energia.api.controller;

import net.guilhermejr.sistema.energia.api.request.AcompanhamentoRequest;
import net.guilhermejr.sistema.energia.api.response.AcompanhamentoResponse;
import net.guilhermejr.sistema.energia.exception.ExceptionNotFound;
import net.guilhermejr.sistema.energia.service.AcompanhamentoService;
import net.guilhermejr.sistema.energia.util.DTOFactory;
import net.guilhermejr.sistema.energia.util.LeJSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(AcompanhamentoController.class)
@ActiveProfiles("test")
public class AcompanhamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcompanhamentoService acompanhamentoService;

    private final Long idExistente = 1L;
    private final Long idInexistente = 2L;

    @Test
    @DisplayName("Deve retornar lista ordenada de acompanhamentos")
    public void deve_retornar_lista_ordenada_de_acompanhamentos() throws Exception {

        List<AcompanhamentoResponse> acompanhamentoResponses = new ArrayList<>();
        acompanhamentoResponses.add(DTOFactory.acompanhamentoResponse);

        Mockito.when(acompanhamentoService.retornar()).thenReturn(acompanhamentoResponses);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/acompanhamentos")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].inicio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].fim").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].dias").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].energiaGerada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].energiaInjetada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].energiaConsumida").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].saldoMes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tusd").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].te").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].bandeira").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].iluminacaoPublica").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].desconto").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].valorTotal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].criado").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].atualizado").exists())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(acompanhamentoService).retornar();

    }

    @Test
    @DisplayName("Deve retornar um acompanhamento quando id existente")
    public void deve_retornar_um_acompanhamento_quando_id_existente() throws Exception {

        Mockito.when(acompanhamentoService.retornarUm(idExistente)).thenReturn(DTOFactory.acompanhamentoResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/acompanhamentos/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.inicio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fim").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dias").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaGerada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaInjetada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumida").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldoMes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tusd").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.te").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bandeira").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.iluminacaoPublica").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.desconto").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.criado").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.atualizado").exists())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(acompanhamentoService).retornarUm(idExistente);

    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar retornar um acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_ao_tentar_retornar_um_acompanhamento_com_id_inexistente() throws Exception {

        Mockito.when(acompanhamentoService.retornarUm(idInexistente)).thenThrow(ExceptionNotFound.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/acompanhamentos/{id}", idInexistente)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(acompanhamentoService).retornarUm(idInexistente);

    }

    @Test
    @DisplayName("Deve inserir um acompanhamento")
    public void deve_inserir_um_acompanhamento() throws Exception {

        Mockito.when(acompanhamentoService.inserir(ArgumentMatchers.any(AcompanhamentoRequest.class))).thenReturn(DTOFactory.acompanhamentoResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/acompanhamentos")
                .content(LeJSON.conteudo("/json/correto/acompanhamento.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.inicio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fim").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dias").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaGerada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaInjetada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumida").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldoMes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tusd").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.te").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bandeira").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.iluminacaoPublica").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.desconto").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.criado").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.atualizado").exists())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(acompanhamentoService).inserir(ArgumentMatchers.any(AcompanhamentoRequest.class));

    }

    @Test
    @DisplayName("Deve atualizar um acompanhamento quando id existente")
    public void deve_atualizar_um_acompanhamento_quando_id_existente() throws Exception {

        Mockito.when(acompanhamentoService.atualizar(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenReturn(DTOFactory.acompanhamentoResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/acompanhamentos/{id}", idExistente)
                .content(LeJSON.conteudo("/json/correto/acompanhamento.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.inicio").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fim").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dias").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaGerada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaInjetada").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumida").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldoMes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tusd").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.te").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bandeira").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.iluminacaoPublica").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.desconto").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.criado").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.atualizado").exists())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(acompanhamentoService).atualizar(ArgumentMatchers.anyLong(), ArgumentMatchers.any());


    }

    @Test
    @DisplayName("Deve lançar uma exceção quando tentar atualizar um acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_quanto_tentar_atualizar_um_acompanhamento_com_id_inexistente() throws Exception {

        Mockito.when(acompanhamentoService.atualizar(ArgumentMatchers.anyLong(), ArgumentMatchers.any())).thenThrow(ExceptionNotFound.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/acompanhamentos/{id}", idInexistente)
                .content(LeJSON.conteudo("/json/correto/acompanhamento.json"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(acompanhamentoService).atualizar(ArgumentMatchers.anyLong(), ArgumentMatchers.any());

    }

    @Test
    @DisplayName("Não deve fazer nada ao apagar quando id existente")
    public void nao_deve_fazer_nada_ao_apagar_quando_id_existente() throws Exception {

        Mockito.doNothing().when(acompanhamentoService).apagar(idExistente);

        mockMvc.perform(MockMvcRequestBuilders.delete("/acompanhamentos/{id}", idExistente)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        Mockito.verify(acompanhamentoService).apagar(idExistente);
    }

    @Test
    @DisplayName("Deve lançar uma exceção quando tentar apagar acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_quando_tentar_apagar_acompanhamento_com_id_inexistente() throws Exception {

        Mockito.doThrow(ExceptionNotFound.class).when(acompanhamentoService).apagar(idInexistente);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/acompanhamentos/{id}", idInexistente)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        Mockito.verify(acompanhamentoService).apagar(idInexistente);

    }

}
