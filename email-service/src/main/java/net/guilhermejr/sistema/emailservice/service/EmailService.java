package net.guilhermejr.sistema.emailservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.guilhermejr.sistema.emailservice.dto.EsqueciMinhaSenhaDTO;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public void enviar(EsqueciMinhaSenhaDTO esqueciMinhaSenhaDTO) {

        String texto = "Olá "+ esqueciMinhaSenhaDTO.getNome() +"\n\nSua nova senha é: "+ esqueciMinhaSenhaDTO.getSenha();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(esqueciMinhaSenhaDTO.getEmail());
            message.setSubject("Nova senha");
            message.setText(texto);
            emailSender.send(message);
            log.info("E-mail enviado com sucesso para {}", esqueciMinhaSenhaDTO.getEmail());

        } catch (MailException e) {
            log.error("Erro ao enviar e-mail para {} - {}",esqueciMinhaSenhaDTO.getEmail(), e.getMessage());
        }

    }

}
