package net.guilhermejr.sistema.emailservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.emailservice.dto.EsqueciMinhaSenhaDTO;
import net.guilhermejr.sistema.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class JsonListener {

    private final EmailService emailService;

    @SneakyThrows
    @KafkaListener(topics = "#{'${kafka.topico.esqueci-minha-senha}'}", groupId = "enviar", containerFactory = "jsonContainerFactory")
    public void enviar(@Payload EsqueciMinhaSenhaDTO esqueciMinhaSenhaDTO) {
        emailService.enviar(esqueciMinhaSenhaDTO);
    }

}
