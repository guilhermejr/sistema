package net.guilhermejr.sistema.energia.service;

import net.guilhermejr.sistema.energia.api.mapper.AcompanhamentoMapper;
import net.guilhermejr.sistema.energia.api.request.AcompanhamentoRequest;
import net.guilhermejr.sistema.energia.api.response.AcompanhamentoResponse;
import net.guilhermejr.sistema.energia.domain.entity.Acompanhamento;
import net.guilhermejr.sistema.energia.domain.entity.Total;
import net.guilhermejr.sistema.energia.domain.repository.AcompanhamentoRepository;
import net.guilhermejr.sistema.energia.domain.repository.TotalRepository;
import net.guilhermejr.sistema.energia.exception.ExceptionDefault;
import net.guilhermejr.sistema.energia.exception.ExceptionNotFound;
import net.guilhermejr.sistema.energia.util.DTOFactory;
import net.guilhermejr.sistema.energia.util.EntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AcompanhamentoServiceTest {

    @InjectMocks
    private AcompanhamentoService acompanhamentoService;
    
    @Mock
    private AcompanhamentoRepository acompanhamentoRepository;

    @Mock
    private TotalRepository totalRepository;

    @Mock
    private AcompanhamentoMapper acompanhamentoMapper;

    private final Long idExistente = 1L;
    private final Long idInexistente = 2L;

    @Test
    @DisplayName("Deve retornar lista ordenada de acompanhamentos")
    public void deve_retornar_lista_ordenada_de_acompanhamentos() {

        List<Acompanhamento> acompanhamentos = new ArrayList<>();
        acompanhamentos.add(EntityFactory.acompanhamento1);

        List<AcompanhamentoResponse> acompanhamentoResponses = new ArrayList<>();
        acompanhamentoResponses.add(DTOFactory.acompanhamentoResponse);

        Mockito.when(acompanhamentoRepository.findAllByOrderByInicioAsc()).thenReturn(acompanhamentos);
        Mockito.when(acompanhamentoMapper.mapList(ArgumentMatchers.anyList())).thenReturn(acompanhamentoResponses);

        List<AcompanhamentoResponse> acompanhamentoResponsesRetorno = acompanhamentoService.retornar();

        Assertions.assertNotNull(acompanhamentoResponsesRetorno);

        Mockito.verify(acompanhamentoRepository).findAllByOrderByInicioAsc();
        Mockito.verify(acompanhamentoMapper).mapList(ArgumentMatchers.anyList());

    }

    @Test
    @DisplayName("Deve retornar um acompanhamento quando id existente")
    public void deve_retornar_um_acompanhamento_quando_id_existente() {

        Mockito.when(acompanhamentoRepository.findById(idExistente)).thenReturn(Optional.of(EntityFactory.acompanhamento1));
        Mockito.when(acompanhamentoMapper.mapObject(ArgumentMatchers.any(Acompanhamento.class))).thenReturn(DTOFactory.acompanhamentoResponse);

        AcompanhamentoResponse acompanhamentoResponse = acompanhamentoService.retornarUm(idExistente);

        Assertions.assertNotNull(acompanhamentoResponse);

        Mockito.verify(acompanhamentoRepository).findById(idExistente);
        Mockito.verify(acompanhamentoMapper).mapObject(ArgumentMatchers.any(Acompanhamento.class));

    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar retornar um acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_ao_tentar_retornar_um_acompanhamento_com_id_inexistente() {

        Mockito.when(acompanhamentoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        Assertions.assertThrows(ExceptionNotFound.class, () -> {
            acompanhamentoService.retornarUm(idInexistente);
        });

        Mockito.verify(acompanhamentoRepository).findById(idInexistente);

    }

    @Test
    @DisplayName("Deve inserir um acompanhamento")
    public void deve_inserir_um_acompanhamento() {

        Mockito.when(acompanhamentoMapper.mapObject(ArgumentMatchers.any(AcompanhamentoRequest.class))).thenReturn(EntityFactory.acompanhamento1);
        Mockito.when(totalRepository.findById(idExistente)).thenReturn(Optional.of(EntityFactory.total));
        Mockito.when(totalRepository.save(ArgumentMatchers.any(Total.class))).thenReturn(EntityFactory.total);
        Mockito.when(acompanhamentoRepository.save(ArgumentMatchers.any(Acompanhamento.class))).thenReturn(EntityFactory.acompanhamento1);
        Mockito.when(acompanhamentoMapper.mapObject(ArgumentMatchers.any(Acompanhamento.class))).thenReturn(DTOFactory.acompanhamentoResponse);

        AcompanhamentoResponse acompanhamentoResponse = acompanhamentoService.inserir(DTOFactory.acompanhamentoRequest1);

        Assertions.assertNotNull(acompanhamentoResponse);
        Assertions.assertEquals(32, acompanhamentoResponse.getDias());
        Assertions.assertEquals(new BigDecimal("444.2"), acompanhamentoResponse.getEnergiaConsumidaTotal());
        Assertions.assertEquals(146, acompanhamentoResponse.getSaldoMes());
        Assertions.assertEquals(new BigDecimal("57.17"), acompanhamentoResponse.getValorTotal());

        Mockito.verify(acompanhamentoMapper).mapObject(ArgumentMatchers.any(AcompanhamentoRequest.class));
        Mockito.verify(totalRepository).findById(idExistente);
        Mockito.verify(totalRepository).save(ArgumentMatchers.any(Total.class));
        Mockito.verify(acompanhamentoRepository).save(ArgumentMatchers.any(Acompanhamento.class));
        Mockito.verify(acompanhamentoMapper).mapObject(ArgumentMatchers.any(Acompanhamento.class));

    }
    
    @Test
    @DisplayName("Deve lançar uma exceção quando data início for maior que data fim")
    public void deve_lancar_uma_excecao_quando_data_inicio_for_maior_que_data_fim() {

        Mockito.when(acompanhamentoMapper.mapObject(ArgumentMatchers.any(AcompanhamentoRequest.class))).thenReturn(EntityFactory.acompanhamento1);

        Assertions.assertThrows(ExceptionDefault.class, () -> {
            acompanhamentoService.inserir(DTOFactory.acompanhamentoRequest2);
        });

        Mockito.verify(acompanhamentoMapper).mapObject(ArgumentMatchers.any(AcompanhamentoRequest.class));

    }

    @Test
    @DisplayName("Deve atualizar um acompanhamento")
    public void deve_atualizar_um_acompanhamento() {

        Mockito.when(totalRepository.save(ArgumentMatchers.any(Total.class))).thenReturn(EntityFactory.total);
        Mockito.when(acompanhamentoRepository.save(ArgumentMatchers.any(Acompanhamento.class))).thenReturn(EntityFactory.acompanhamento3);
        Mockito.when(acompanhamentoMapper.mapObject(ArgumentMatchers.any(AcompanhamentoRequest.class))).thenReturn(EntityFactory.acompanhamento3);
        Mockito.when(acompanhamentoMapper.mapObject(ArgumentMatchers.any(Acompanhamento.class))).thenReturn(DTOFactory.acompanhamentoResponseAtualizar);
        Mockito.when(totalRepository.findById(idExistente)).thenReturn(Optional.of(EntityFactory.total));
        Mockito.when(acompanhamentoRepository.findById(idExistente)).thenReturn(Optional.of(EntityFactory.acompanhamento1));

        AcompanhamentoResponse acompanhamentoResponse = acompanhamentoService.atualizar(idExistente, DTOFactory.acompanhamentoRequest1);

        Assertions.assertNotNull(acompanhamentoResponse);
        Assertions.assertEquals(27, acompanhamentoResponse.getDias());
        Assertions.assertEquals(new BigDecimal("438.4"), acompanhamentoResponse.getEnergiaConsumidaTotal());
        Assertions.assertEquals(240, acompanhamentoResponse.getSaldoMes());
        Assertions.assertEquals(new BigDecimal("54.89"), acompanhamentoResponse.getValorTotal());

        Mockito.verify(totalRepository, Mockito.times(2)).findById(idExistente);
        Mockito.verify(acompanhamentoMapper).mapObject(ArgumentMatchers.any(AcompanhamentoRequest.class));
        Mockito.verify(totalRepository, Mockito.times(2)).save(ArgumentMatchers.any(Total.class));
        Mockito.verify(acompanhamentoRepository).save(ArgumentMatchers.any(Acompanhamento.class));
        Mockito.verify(acompanhamentoRepository, Mockito.times(2)).findById(idExistente);

    }

    @Test
    @DisplayName("Não deve fazer nada ao apagar quando id existente")
    public void nao_deve_fazer_nada_ao_apagar_quando_id_existente() {

        Mockito.when(totalRepository.save(ArgumentMatchers.any(Total.class))).thenReturn(EntityFactory.total);
        Mockito.when(totalRepository.findById(idExistente)).thenReturn(Optional.of(EntityFactory.total));
        Mockito.when(acompanhamentoRepository.findById(idExistente)).thenReturn(Optional.of(EntityFactory.acompanhamento1));
        Mockito.doNothing().when(acompanhamentoRepository).deleteById(idExistente);

        Assertions.assertDoesNotThrow(() -> {
            acompanhamentoService.apagar(idExistente);
        });

        Mockito.verify(totalRepository).findById(idExistente);
        Mockito.verify(totalRepository).save(ArgumentMatchers.any());
        Mockito.verify(acompanhamentoRepository).findById(idExistente);
        Mockito.verify(acompanhamentoRepository).deleteById(idExistente);

    }

    @Test
    @DisplayName("Deve lançar uma exceção quando tentar apagar acompanhamento com id inexistente")
    public void deve_lancar_uma_excecao_quando_tentar_apagar_acompanhamento_com_id_inexistente() {

        Mockito.when(acompanhamentoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        Mockito.doThrow(EmptyResultDataAccessException.class).when(acompanhamentoRepository).deleteById(idInexistente);

        Assertions.assertThrows(ExceptionNotFound.class, () -> {
            acompanhamentoService.apagar(idInexistente);
        });

        Mockito.verify(acompanhamentoRepository).findById(idInexistente);

    }

}
