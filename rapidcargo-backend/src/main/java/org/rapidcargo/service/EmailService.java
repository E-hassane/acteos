package org.rapidcargo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.rapidcargo.domain.EntryMovement;
import org.rapidcargo.domain.ExitMovement;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.domain.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;

    @Value("${rapidcargo.email.cargoinfo.address}")
    private String cargoInfoEmailAddress;

    @Value("${rapidcargo.email.from.address}")
    private String fromEmailAddress;

    @Value("${rapidcargo.email.from.name}")
    private String fromEmailName;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     *
     * @param movement
     * @param xmlContent
     */
    public void sendMovementNotification(Movement movement, String xmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

            helper.setTo(cargoInfoEmailAddress);
            helper.setFrom(fromEmailAddress, fromEmailName);

            String subject = generateEmailSubject(movement);
            String body = generateEmailBody(movement);

            helper.setSubject(subject);
            helper.setText(body, false);

            String filename = generateXmlFilename(movement);
            ByteArrayResource attachment = new ByteArrayResource(xmlContent.getBytes(StandardCharsets.UTF_8));
            helper.addAttachment(filename, attachment);

            mailSender.send(mimeMessage);

            logger.info("Email envoyé avec succès pour le mouvement {} de type {}",
                    movement.getId(), movement.getType().getLabel());

        } catch (MessagingException e) {
            logger.error("Erreur lors de l'envoi de l'email pour le mouvement {}: {}",
                    movement.getId(), e.getMessage(), e);
            throw new BusinessException("Échec de l'envoi de la notification email: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de l'envoi de l'email: {}", e.getMessage(), e);
            throw new BusinessException("Erreur système lors de l'envoi de la notification", e);
        }
    }

    /**
     *
     * @param movement
     * @return
     */
    private String generateEmailSubject(Movement movement) {
        String movementType = movement.getType().getLabel();
        String referenceCode = movement.getGoods().getReferenceCode();
        String movementTime = movement.getMovementTime().format(DATE_FORMAT);

        return String.format("[RapidCargo] Déclaration %s - %s - %s",
                movementType, referenceCode, movementTime);
    }

    /**
     *
     * @param movement
     * @return
     */
    private String generateEmailBody(Movement movement) {
        StringBuilder body = new StringBuilder();

        body.append("Bonjour,\n\n");
        body.append("Vous trouverez en pièce jointe la déclaration de mouvement suivante :\n\n");
        body.append("Type de mouvement : ").append(movement.getType().getLabel()).append("\n");
        body.append("Référence : ").append(movement.getGoods().getReferenceCode()).append("\n");
        body.append("Date/heure du mouvement : ").append(movement.getMovementTime().format(DATE_FORMAT)).append("\n");
        body.append("Quantité : ").append(movement.getGoods().getQuantity()).append("\n");
        body.append("Poids : ").append(movement.getGoods().getWeight()).append(" kg\n");
        body.append("Statut douanier : ").append(movement.getCustomsStatus().getLabel()).append("\n");

        if (movement instanceof EntryMovement entry) {
            body.append("Provenance : ").append(entry.getFromWarehouseLabel()).append("\n");
        } else if (movement instanceof ExitMovement exit) {
            body.append("Destination : ").append(exit.getToWarehouseLabel()).append("\n");
            body.append("Document douanier : ").append(exit.getCustomsDocumentType())
                    .append(" - ").append(exit.getCustomsDocumentRef()).append("\n");
        }

        body.append("\nDéclaré par : ").append(movement.getCreatedBy()).append("\n");
        body.append("Date de déclaration : ").append(movement.getCreatedAt().format(DATE_FORMAT)).append("\n\n");
        body.append("Cordialement,\n");
        body.append("L'équipe RapidCargo CDG\n");
        body.append("Système automatisé de déclaration de mouvements");

        return body.toString();
    }

    /**
     *
     * @param movement
     * @return
     */
    private String generateXmlFilename(Movement movement) {
        String timestamp = movement.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String movementType = movement.getType().name().toLowerCase();
        String referenceCode = movement.getGoods().getReferenceCode().replaceAll("[^a-zA-Z0-9]", "");

        return String.format("rapidcargo_%s_%s_%s.xml", movementType, referenceCode, timestamp);
    }

    private String generateMessageId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    /**
     *
     * @param dateTime
     * @return
     */
    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter XML_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return dateTime.format(XML_DATE_FORMAT);
    }

    public boolean isEmailConfigurationValid() {
        return cargoInfoEmailAddress != null && !cargoInfoEmailAddress.trim().isEmpty() &&
                fromEmailAddress != null && !fromEmailAddress.trim().isEmpty();
    }

    public String getCargoInfoEmailAddress() {
        return cargoInfoEmailAddress;
    }
}