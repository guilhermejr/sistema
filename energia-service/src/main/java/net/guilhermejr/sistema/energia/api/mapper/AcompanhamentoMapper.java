package net.guilhermejr.sistema.energia.api.mapper;

import net.guilhermejr.sistema.energia.api.request.AcompanhamentoRequest;
import net.guilhermejr.sistema.energia.api.response.AcompanhamentoResponse;
import net.guilhermejr.sistema.energia.domain.entity.Acompanhamento;
import net.guilhermejr.sistema.energia.util.ConverteStringUtil;
import net.guilhermejr.sistema.energia.config.ModelMapperConfig;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class AcompanhamentoMapper extends ModelMapperConfig {

    public AcompanhamentoMapper(ConverteStringUtil converteStringUtil) {

        Converter<String, LocalDate> converterData = ctx -> converteStringUtil.toLocalDate(ctx.getSource());

        Converter<String, BigDecimal> converterBigDecimal = ctx -> {
                if (ctx.getSource() == null) {
                    return BigDecimal.ZERO;
                }
                return converteStringUtil.toBigDecimal(ctx.getSource());
            };

        this.modelMapper.createTypeMap(AcompanhamentoRequest.class, Acompanhamento.class)
                .addMappings(mapper -> mapper.using(converterData).map(AcompanhamentoRequest::getInicio, Acompanhamento::setInicio))
                .addMappings(mapper -> mapper.using(converterData).map(AcompanhamentoRequest::getFim, Acompanhamento::setFim))
                .addMappings(mapper -> mapper.using(converterBigDecimal).map(AcompanhamentoRequest::getBandeira, Acompanhamento::setBandeira))
                .addMappings(mapper -> mapper.using(converterBigDecimal).map(AcompanhamentoRequest::getTusd, Acompanhamento::setTusd))
                .addMappings(mapper -> mapper.using(converterBigDecimal).map(AcompanhamentoRequest::getTe, Acompanhamento::setTe))
                .addMappings(mapper -> mapper.using(converterBigDecimal).map(AcompanhamentoRequest::getIluminacaoPublica, Acompanhamento::setIluminacaoPublica))
                .addMappings(mapper -> mapper.using(converterBigDecimal).map(AcompanhamentoRequest::getDesconto, Acompanhamento::setDesconto))
                .addMappings(mapper -> mapper.using(converterBigDecimal).map(AcompanhamentoRequest::getEnergiaGerada, Acompanhamento::setEnergiaGerada));

    }

    public Acompanhamento mapObject(AcompanhamentoRequest acompanhamentoRequest) {
        return this.mapObject(acompanhamentoRequest, Acompanhamento.class);
    }

    public AcompanhamentoResponse mapObject(Acompanhamento acompanhamento) {
        return this.mapObject(acompanhamento, AcompanhamentoResponse.class);
    }

    public List<AcompanhamentoResponse> mapList(List<Acompanhamento> acompanhamento) {
        return this.mapList(acompanhamento, AcompanhamentoResponse.class);
    }

}
