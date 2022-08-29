package net.guilhermejr.sistema.energia.api.controller;

import net.guilhermejr.sistema.energia.domain.entity.Acompanhamento;
import net.guilhermejr.sistema.energia.domain.repository.AcompanhamentoRepository;
import net.guilhermejr.sistema.energia.util.EntityFactory;
import net.guilhermejr.sistema.energia.util.LeJSON;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class AcompanhamentoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Test
    @DisplayName("Deve retornar lista ordenada de acompanhamentos")
    public void deve_retornar_lista_ordenada_de_acompanhamentos() throws Exception {

        List<Acompanhamento> acompanhamentos = new ArrayList<>();
        acompanhamentos.add(EntityFactory.acompanhamento2);
        acompanhamentos.add(EntityFactory.acompanhamento3);
        acompanhamentos.add(EntityFactory.acompanhamento1);
        acompanhamentoRepository.saveAll(acompanhamentos);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/acompanhamentos")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].inicio").value("2021-11-18"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].inicio").value("2021-12-21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].inicio").value("2022-01-19"))
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());

    }

    @Test
    @DisplayName("Deve retornar um acompanhamento quando id existente")
    public void deve_retornar_um_acompanhamento_quando_id_existente() throws Exception {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/acompanhamentos/{id}", acompanhamento.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.inicio").value(EntityFactory.acompanhamento1.getInicio().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fim").value(EntityFactory.acompanhamento1.getFim().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dias").value(EntityFactory.acompanhamento1.getDias().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaGerada").value(EntityFactory.acompanhamento1.getEnergiaGerada().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaInjetada").value(EntityFactory.acompanhamento1.getEnergiaInjetada().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumidaConcessionaria").value(EntityFactory.acompanhamento1.getEnergiaConsumidaConcessionaria().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumidaTotal").value(EntityFactory.acompanhamento1.getEnergiaConsumidaTotal().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldoMes").value(EntityFactory.acompanhamento1.getSaldoMes().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tusd").value(EntityFactory.acompanhamento1.getTusd().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.te").value(EntityFactory.acompanhamento1.getTe().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bandeira").value(EntityFactory.acompanhamento1.getBandeira().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iluminacaoPublica").value(EntityFactory.acompanhamento1.getIluminacaoPublica().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.desconto").value(EntityFactory.acompanhamento1.getDesconto().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").value(EntityFactory.acompanhamento1.getValorTotal().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.criado").value(EntityFactory.acompanhamento1.getCriado().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.atualizado").value(EntityFactory.acompanhamento1.getAtualizado().toString()))
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());

    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar retornar um acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_ao_tentar_retornar_um_acompanhamento_com_id_inexistente() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/acompanhamentos/{id}", 0L)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());

    }

    @Test
    @DisplayName("Deve inserir um acompanhamento")
    public void deve_inserir_um_acompanhamento() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/acompanhamentos")
                        .content(LeJSON.conteudo("/json/correto/acompanhamento.json"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.inicio").value("2022-04-19"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fim").value("2022-05-18"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dias").value("29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaGerada").value("637.5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaInjetada").value("498"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumidaConcessionaria").value("297"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.energiaConsumidaTotal").value("436.5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldoMes").value("201"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tusd").value("33.64"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.te").value("19.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bandeira").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.iluminacaoPublica").value("1.74"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.desconto").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").value("54.48"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.criado").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.atualizado").exists())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());

    }

    @Test
    @DisplayName("Deve dar erro ao tentar inserir um acompanhamento sem os campos preenchidos")
    public void deve_dar_erro_ao_tentar_inserir_um_acompanhamento_sem_os_campos_preenchidos() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/acompanhamentos")
                        .content(LeJSON.conteudo("/json/correto/acompanhamento_sem_campos_preenchidos.json"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].campo").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem").exists())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());

    }

    @Test
    @DisplayName("Não deve fazer nada ao apagar quando id existente")
    public void nao_deve_fazer_nada_ao_apagar_quando_id_existente() throws Exception {

        Acompanhamento acompanhamento = acompanhamentoRepository.save(EntityFactory.acompanhamento1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/acompanhamentos/{id}", acompanhamento.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

    }

    @Test
    @DisplayName("Deve lançar uma exceção quando tentar apagar acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_quando_tentar_apagar_acompanhamento_com_id_inexistente() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/acompanhamentos/{id}", 0L)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+ TokenFactory.TOKEN_VALIDO)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());

    }
}
