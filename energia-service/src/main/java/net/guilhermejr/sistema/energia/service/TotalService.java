package net.guilhermejr.sistema.energia.service;

import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.energia.api.response.TotalResponse;
import net.guilhermejr.sistema.energia.domain.entity.Total;
import net.guilhermejr.sistema.energia.exception.ExceptionDefault;
import net.guilhermejr.sistema.energia.api.mapper.TotalMapper;
import net.guilhermejr.sistema.energia.domain.repository.TotalRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class TotalService {

    private TotalRepository totalRepository;

    private TotalMapper totalMapper;

    public TotalService(TotalRepository totalRepository, TotalMapper totalMapper) {
        this.totalRepository = totalRepository;
        this.totalMapper = totalMapper;
    }

    // --- Retornar -----------------------------------------------------------
    public TotalResponse retornar() {

        Optional<Total> totalOptional = totalRepository.findById(1L);
        if (totalOptional.isEmpty()) {
            log.error("Não existe registro na tabela TOTAL");
            throw new ExceptionDefault("Não existe registro na tabela TOTAL");
        }

        return totalMapper.mapObject(totalOptional.get());

    }
}
