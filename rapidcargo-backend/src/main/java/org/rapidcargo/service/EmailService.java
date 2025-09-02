package org.rapidcargo.service;

import org.rapidcargo.domain.Movement;
import org.rapidcargo.domain.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;

    @Value("${rapidcargo.email.cargoinfo.address}")
    private String cargoInfoEmail;

    @Value("${rapidcargo.email.from.address}")
    private String fromEmail;

    @Value("${rapidcargo.email.from.name}")
    private String fromName;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMovementNotification(Movement movement, String xmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, fromName);
            helper.setTo(cargoInfoEmail);
            helper.setSubject(buildSubject(movement));
            helper.setText(buildEmailBody(movement), false);

            String filename = String.format("movement_%s_%s.xml",
                    movement.getType().name().toLowerCase(),
                    movement.getId());

            helper.addAttachment(filename,
                    new ByteArrayResource(xmlContent.getBytes(StandardCharsets.UTF_8)));

            mailSender.send(message);
            logger.info("Email envoyé pour mouvement {} vers {}", movement.getId(), cargoInfoEmail);

        } catch (Exception e) {
            logger.error("Échec envoi email mouvement {}: {}", movement.getId(), e.getMessage());
            throw new BusinessException("Erreur envoi notification: " + e.getMessage(), e);
        }
    }

    private String buildSubject(Movement movement) {
        return String.format("RapidCargo - Déclaration %s - AWB %s",
                movement.getType().getLabel(),
                movement.getGoods().getReferenceCode());
    }

    private String buildEmailBody(Movement movement) {
        return String.format("""
                Bonjour,
                
                Veuillez trouver en pièce jointe la déclaration de mouvement suivante :
                
                - Type : %s
                - Référence : %s
                - Date mouvement : %s
                - Entrepôt : %s
                
                Cordialement,
                L'équipe RapidCargo CDG
                """,
                movement.getType().getLabel(),
                movement.getGoods().getReferenceCode(),
                movement.getMovementTime().format(DATE_FORMAT),
                movement.getDeclaredInLabel()
        );
    }
}

