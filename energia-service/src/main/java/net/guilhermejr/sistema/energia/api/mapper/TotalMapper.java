package net.guilhermejr.sistema.energia.api.mapper;

import net.guilhermejr.sistema.energia.api.response.TotalResponse;
import net.guilhermejr.sistema.energia.domain.entity.Total;
import net.guilhermejr.sistema.energia.config.ModelMapperConfig;
import org.springframework.stereotype.Component;

@Component
public class TotalMapper extends ModelMapperConfig {

    public TotalResponse mapObject(Total total) {
        return this.mapObject(total, TotalResponse.class);
    }

}
