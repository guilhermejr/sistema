package net.guilhermejr.sistema.energia.service;

import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.energia.api.mapper.AcompanhamentoMapper;
import net.guilhermejr.sistema.energia.api.request.AcompanhamentoRequest;
import net.guilhermejr.sistema.energia.api.response.AcompanhamentoResponse;
import net.guilhermejr.sistema.energia.domain.entity.Acompanhamento;
import net.guilhermejr.sistema.energia.domain.entity.Total;
import net.guilhermejr.sistema.energia.domain.repository.AcompanhamentoRepository;
import net.guilhermejr.sistema.energia.domain.repository.TotalRepository;
import net.guilhermejr.sistema.energia.exception.ExceptionDefault;
import net.guilhermejr.sistema.energia.exception.ExceptionNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AcompanhamentoService {

    private final AcompanhamentoRepository acompanhamentoRepository;

    private final TotalRepository totalRepository;

    private final AcompanhamentoMapper acompanhamentoMapper;

    public AcompanhamentoService(AcompanhamentoRepository acompanhamentoRepository, TotalRepository totalRepository, AcompanhamentoMapper acompanhamentoMapper) {
        this.acompanhamentoRepository = acompanhamentoRepository;
        this.totalRepository = totalRepository;
        this.acompanhamentoMapper = acompanhamentoMapper;
    }

    // --- Retornar -----------------------------------------------------------
    public List<AcompanhamentoResponse> retornar() {

        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAllByOrderByInicioAsc();
        return acompanhamentoMapper.mapList(acompanhamentoList);

    }

    // --- RetornarUm ---------------------------------------------------------
    public AcompanhamentoResponse retornarUm(Long id) {

        Acompanhamento acompanhamento = acompanhamentoExiste(id);
        if (acompanhamento != null) {
            return acompanhamentoMapper.mapObject(acompanhamento);
        } else {
            log.error("Acompanhamento não retornado: {}", id);
            throw new ExceptionNotFound("Não pode retornar acompanhamento. Id não encontrado: " + id);
        }

    }

    // --- Inserir ------------------------------------------------------------
    @Transactional
    public AcompanhamentoResponse inserir(AcompanhamentoRequest acompanhamentoRequest) {

        return salvar(acompanhamentoRequest, null);

    }

    // --- Atualizar ----------------------------------------------------------
    @Transactional
    public AcompanhamentoResponse atualizar(Long id, AcompanhamentoRequest acompanhamentoRequest) {

        if (acompanhamentoExiste(id) != null) {
            return salvar(acompanhamentoRequest, id);
        } else {
            log.error("Acompanhamento não atualizado: {}", id);
            throw new ExceptionNotFound("Não pode atualizar acompanhamento. Id não encontrado: " + id);
        }

    }

    // --- Apagar -------------------------------------------------------------
    @Transactional
    public void apagar(Long id) {

        Acompanhamento acompanhamento = acompanhamentoExiste(id);

        if (acompanhamento != null) {

            // --- Subtrai valores do acompanhamento do total ---
            subtrairTotal(acompanhamento);

            // --- Remove acompanhamento ---
            acompanhamentoRepository.deleteById(id);

        } else {
            log.error("Acompanhamento não apagado: {}", id);
            throw new ExceptionNotFound("Não pode apagar acompanhamento. Id não encontrado: " + id);
        }

    }

    // --- SubtrairTotal ------------------------------------------------------
    private void subtrairTotal(Acompanhamento acompanhamento) {

        // --- Recupera total ---
        Total total = recuperarTotal();

        // --- Atualiza totais ---
        total.setEnergiaGerada(total.getEnergiaGerada().subtract(acompanhamento.getEnergiaGerada()));
        total.setEnergiaInjetada(total.getEnergiaInjetada() - acompanhamento.getEnergiaInjetada());
        total.setEnergiaConsumidaConcessionaria(total.getEnergiaConsumidaConcessionaria() - acompanhamento.getEnergiaConsumidaConcessionaria());
        total.setEnergiaConsumidaTotal(total.getEnergiaConsumidaTotal().subtract(acompanhamento.getEnergiaConsumidaTotal()));
        total.setSaldoMes(total.getSaldoMes() - acompanhamento.getSaldoMes());
        total.setTusd(total.getTusd().subtract(acompanhamento.getTusd()));
        total.setTe(total.getTe().subtract(acompanhamento.getTe()));
        total.setBandeira(total.getBandeira().subtract(acompanhamento.getBandeira()));
        total.setIluminacaoPublica(total.getIluminacaoPublica().subtract(acompanhamento.getIluminacaoPublica()));
        total.setDesconto(total.getDesconto().subtract(acompanhamento.getDesconto()));
        total.setValorTotal(total.getValorTotal().subtract(acompanhamento.getValorTotal()));

        // --- Atualiza total ---
        totalRepository.save(total);

    }

    // --- AcompanhamentoExiste -----------------------------------------------
    private Acompanhamento acompanhamentoExiste(Long id) {

        Optional<Acompanhamento> acompanhamento = acompanhamentoRepository.findById(id);
        return acompanhamento.orElse(null);

    }

    // --- Salvar -------------------------------------------------------------
    private AcompanhamentoResponse salvar(AcompanhamentoRequest acompanhamentoRequest, Long id) {

        // --- Converte AcompanhamentoIN em Acompanhamento ---
        Acompanhamento acompanhamento = acompanhamentoMapper.mapObject(acompanhamentoRequest);

        // --- Verifica se inicio é menor que fim ---
        if (acompanhamento.getInicio().isAfter(acompanhamento.getFim())) {
            log.error("A data início deve ser depois da data fim");
            throw new ExceptionDefault("A data início deve ser depois da data fim");
        }

        // --- Insere id se for atualização ---
        if (id != null) {
            acompanhamento.setId(id);
        }

        // --- Calcula quantidade de dias entre as datas ---
        Long dias = ChronoUnit.DAYS.between(acompanhamento.getInicio(), acompanhamento.getFim());
        acompanhamento.setDias(dias.intValue());

        // --- Calcula consumo total ---
        BigDecimal consumoTotal = BigDecimal.valueOf(acompanhamento.getEnergiaConsumidaConcessionaria()).add(acompanhamento.getEnergiaGerada().subtract(BigDecimal.valueOf(acompanhamento.getEnergiaInjetada())));
        acompanhamento.setEnergiaConsumidaTotal(consumoTotal);

        // --- Calcula saldoMes ---
        Integer saldoMes = acompanhamento.getEnergiaInjetada() - acompanhamento.getEnergiaConsumidaConcessionaria();
        acompanhamento.setSaldoMes(saldoMes);

        // --- Calcula valorTotal ---
        BigDecimal valorTotal = acompanhamento.getTusd().add(acompanhamento.getTe().add(acompanhamento.getBandeira().add(acompanhamento.getIluminacaoPublica()))).subtract(acompanhamento.getDesconto());
        acompanhamento.setValorTotal(valorTotal);

        // --- Inclui as datas de criação e atualização ---
        if (id == null) {
            acompanhamento.setCriado(LocalDateTime.now(ZoneId.of("UTC")));
        }
        acompanhamento.setAtualizado(LocalDateTime.now(ZoneId.of("UTC")));

        // --- Recupera total ---
        Total total = recuperarTotal();

        // --- Se for atualização subtrai os valores antigos ---
        if (id != null) {

            Acompanhamento acompanhamentoAntigo = acompanhamentoExiste(id);
            subtrairTotal(acompanhamentoAntigo);

        }

        // --- Atualiza totais ---
        total.setEnergiaGerada(total.getEnergiaGerada().add(acompanhamento.getEnergiaGerada()));
        total.setEnergiaInjetada(total.getEnergiaInjetada() + acompanhamento.getEnergiaInjetada());
        total.setEnergiaConsumidaConcessionaria(total.getEnergiaConsumidaConcessionaria() + acompanhamento.getEnergiaConsumidaConcessionaria());
        total.setEnergiaConsumidaTotal(total.getEnergiaConsumidaTotal().add(acompanhamento.getEnergiaConsumidaTotal()));
        total.setSaldoMes(total.getSaldoMes() + acompanhamento.getSaldoMes());
        total.setTusd(total.getTusd().add(acompanhamento.getTusd()));
        total.setTe(total.getTe().add(acompanhamento.getTe()));
        total.setBandeira(total.getBandeira().add(acompanhamento.getBandeira()));
        total.setIluminacaoPublica(total.getIluminacaoPublica().add(acompanhamento.getIluminacaoPublica()));
        total.setDesconto(total.getDesconto().add(acompanhamento.getDesconto()));
        total.setValorTotal(total.getValorTotal().add(acompanhamento.getValorTotal()));

        // --- Atualiza total ---
        totalRepository.save(total);

        // --- Salva acompanhamento ---
        Acompanhamento acompanhamentoSave = acompanhamentoRepository.save(acompanhamento);

        // --- Retorno ---
        return acompanhamentoMapper.mapObject(acompanhamentoSave);

    }

    // --- RecuperarTotal -----------------------------------------------------
    private Total recuperarTotal() {

        Optional<Total> totalOptional = totalRepository.findById(1L);
        if (totalOptional.isEmpty()) {
            log.error("Não existe registro na tabela TOTAL");
            throw new ExceptionDefault("Não existe registro na tabela TOTAL");
        }

        return totalOptional.get();
    }

}
