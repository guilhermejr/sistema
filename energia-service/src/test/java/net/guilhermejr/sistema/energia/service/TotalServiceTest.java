package net.guilhermejr.sistema.energia.service;

import net.guilhermejr.sistema.energia.api.mapper.TotalMapper;
import net.guilhermejr.sistema.energia.api.response.TotalResponse;
import net.guilhermejr.sistema.energia.domain.entity.Total;
import net.guilhermejr.sistema.energia.domain.repository.TotalRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TotalServiceTest {

    @InjectMocks
    private TotalService totalService;

    @Mock
    private TotalRepository totalRepository;

    @Mock
    private TotalMapper totalMapper;

    @Test
    @DisplayName("Deve retornar um total")
    public void deve_retornar_um_total() {

        Mockito.when(totalRepository.findById(1L)).thenReturn(Optional.of(EntityFactory.total));
        Mockito.when(totalMapper.mapObject(ArgumentMatchers.any(Total.class))).thenReturn(DTOFactory.totalResponse);

        TotalResponse total = totalService.retornar();

        Assertions.assertNotNull(total);

        Mockito.verify(totalRepository).findById(1L);
        Mockito.verify(totalMapper).mapObject(ArgumentMatchers.any(Total.class));

    }

}
