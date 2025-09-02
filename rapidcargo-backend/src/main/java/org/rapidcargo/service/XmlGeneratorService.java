package org.rapidcargo.service;

import org.rapidcargo.domain.EntryMovement;
import org.rapidcargo.domain.ExitMovement;
import org.rapidcargo.domain.Goods;
import org.rapidcargo.domain.Movement;
import org.rapidcargo.domain.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class XmlGeneratorService {

    private static final String SENDER = "RAPIDCARGO";
    private static final String RECIPIENT = "CARGOINFO";
    private static final DateTimeFormatter XML_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     *
     * @param movement
     * @return
     */
    public String generateCargoMessage(Movement movement) {
        if (movement == null) {
            throw new BusinessException("Le mouvement ne peut pas être null pour la génération XML");
        }

        StringBuilder xml = new StringBuilder();
        String messageId = generateMessageId();
        String messageTime = formatDateTime(LocalDateTime.now());

        switch (movement.getType()) {
            case ENTRY -> {
                xml.append(generateEntryMovementXml((EntryMovement) movement, messageId, messageTime));
            }
            case EXIT -> {
                xml.append(generateExitMovementXml((ExitMovement) movement, messageId, messageTime));
            }
            default -> throw new BusinessException(
                    "Type de mouvement non supporté pour la génération XML: " + movement.getType()
            );
        }

        return xml.toString();
    }

    /**
     *
     * @param movement
     * @param messageId
     * @param messageTime
     * @return
     */
    private String generateEntryMovementXml(EntryMovement movement, String messageId, String messageTime) {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<CargoMessage type=\"WarehouseMovement-In\">\n");

        // Header
        xml.append(generateHeader(messageId, messageTime));

        xml.append("  <WarehouseMovementIn>\n");
        xml.append("    <movementTime>").append(formatDateTime(movement.getMovementTime())).append("</movementTime>\n");
        xml.append("    <declaredIn code=\"").append(movement.getDeclaredInCode())
                .append("\" label=\"").append(movement.getDeclaredInLabel()).append("\"/>\n");
        xml.append("    <from code=\"").append(movement.getFromWarehouseCode())
                .append("\" label=\"").append(movement.getFromWarehouseLabel()).append("\"/>\n");

        xml.append(generateGoodsXml(movement.getGoods()));

        xml.append("    <customsStatus>").append(movement.getCustomsStatus().name()).append("</customsStatus>\n");

        xml.append("  </WarehouseMovementIn>\n");
        xml.append("</CargoMessage>");

        return xml.toString();
    }

    /**
     *
     * @param movement
     * @param messageId
     * @param messageTime
     * @return
     */
    private String generateExitMovementXml(ExitMovement movement, String messageId, String messageTime) {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<CargoMessage type=\"WarehouseMovement-Out\">\n");

        xml.append(generateHeader(messageId, messageTime));

        xml.append("  <WarehouseMovementOut>\n");
        xml.append("    <movementTime>").append(formatDateTime(movement.getMovementTime())).append("</movementTime>\n");
        xml.append("    <declaredIn code=\"").append(movement.getDeclaredInCode())
                .append("\" label=\"").append(movement.getDeclaredInLabel()).append("\"/>\n");
        xml.append("    <to code=\"").append(movement.getToWarehouseCode())
                .append("\" label=\"").append(movement.getToWarehouseLabel()).append("\"/>\n");

        xml.append(generateGoodsXml(movement.getGoods()));

        xml.append("    <customsStatus>").append(movement.getCustomsStatus().name()).append("</customsStatus>\n");

        xml.append("    <customsDocument type=\"").append(movement.getCustomsDocumentType())
                .append("\" ref=\"").append(movement.getCustomsDocumentRef()).append("\" />\n");

        xml.append("  </WarehouseMovementOut>\n");
        xml.append("</CargoMessage>");

        return xml.toString();
    }

    /**
     *
     * @param messageId
     * @param messageTime
     * @return
     */
    private String generateHeader(String messageId, String messageTime) {
        return String.format("  <Header from=\"%s\" to=\"%s\" messageTime=\"%s\" messageId=\"%s\" />\n",
                SENDER, RECIPIENT, messageTime, messageId);
    }

    /**
     *
     * @param goods
     * @return
     */
    private String generateGoodsXml(Goods goods) {
        StringBuilder xml = new StringBuilder();

        xml.append("    <goods>\n");
        xml.append("      <ref type=\"").append(goods.getReferenceType().name())
                .append("\" code=\"").append(goods.getReferenceCode()).append("\"/>\n");
        xml.append("      <amount quantity=\"").append(goods.getQuantity())
                .append("\" weight=\"").append(goods.getWeight()).append("\"/>\n");
        xml.append("      <description>").append(escapeXml(goods.getDescription())).append("</description>\n");
        xml.append("      <totalRefAmount quantity=\"").append(goods.getTotalRefQuantity())
                .append("\" weight=\"").append(goods.getTotalRefWeight()).append("\"/>\n");
        xml.append("    </goods>\n");

        return xml.toString();
    }

    private String generateMessageId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(XML_DATE_FORMAT);
    }

    private String escapeXml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
