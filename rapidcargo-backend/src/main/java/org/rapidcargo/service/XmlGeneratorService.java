package org.rapidcargo.service;

import org.rapidcargo.EntryMovement;
import org.rapidcargo.ExitMovement;
import org.rapidcargo.Goods;
import org.rapidcargo.Movement;
import org.rapidcargo.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class XmlGeneratorService {

    private static final DateTimeFormatter XML_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public String generateCargoMessage(Movement movement) {
        if (movement == null) {
            throw new BusinessException("Mouvement null, impossible de générer le XML");
        }

        String messageId = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        String now = LocalDateTime.now().format(XML_FORMAT);

        return switch (movement.getType()) {
            case ENTRY -> buildEntryXml((EntryMovement) movement, messageId, now);
            case EXIT -> buildExitXml((ExitMovement) movement, messageId, now);
            default -> throw new BusinessException("Type mouvement non supporté: " + movement.getType());
        };
    }

    private String buildEntryXml(EntryMovement movement, String messageId, String messageTime) {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<CargoMessage type=\"WarehouseMovement-In\">\n");
        xml.append(String.format("  <Header from=\"RAPIDCARGO\" to=\"CARGOINFO\" messageTime=\"%s\" messageId=\"%s\" />\n",
                messageTime, messageId));

        xml.append("  <WarehouseMovementIn>\n");
        xml.append("    <movementTime>").append(movement.getMovementTime().format(XML_FORMAT)).append("</movementTime>\n");
        xml.append("    <declaredIn code=\"").append(movement.getDeclaredInCode())
                .append("\" label=\"").append(movement.getDeclaredInLabel()).append("\"/>\n");
        xml.append("    <from code=\"").append(movement.getFromWarehouseCode())
                .append("\" label=\"").append(movement.getFromWarehouseLabel()).append("\"/>\n");

        xml.append(buildGoodsXml(movement.getGoods()));
        xml.append("    <customsStatus>").append(movement.getCustomsStatus().name()).append("</customsStatus>\n");
        xml.append("  </WarehouseMovementIn>\n");
        xml.append("</CargoMessage>");

        return xml.toString();
    }

    private String buildExitXml(ExitMovement movement, String messageId, String messageTime) {
        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<CargoMessage type=\"WarehouseMovement-Out\">\n");
        xml.append(String.format("  <Header from=\"RAPIDCARGO\" to=\"CARGOINFO\" messageTime=\"%s\" messageId=\"%s\" />\n",
                messageTime, messageId));

        xml.append("  <WarehouseMovementOut>\n");
        xml.append("    <movementTime>").append(movement.getMovementTime().format(XML_FORMAT)).append("</movementTime>\n");
        xml.append("    <declaredIn code=\"").append(movement.getDeclaredInCode())
                .append("\" label=\"").append(movement.getDeclaredInLabel()).append("\"/>\n");
        xml.append("    <to code=\"").append(movement.getToWarehouseCode())
                .append("\" label=\"").append(movement.getToWarehouseLabel()).append("\"/>\n");

        xml.append(buildGoodsXml(movement.getGoods()));
        xml.append("    <customsStatus>").append(movement.getCustomsStatus().name()).append("</customsStatus>\n");
        xml.append("    <customsDocument type=\"").append(movement.getCustomsDocumentType())
                .append("\" ref=\"").append(movement.getCustomsDocumentRef()).append("\" />\n");
        xml.append("  </WarehouseMovementOut>\n");
        xml.append("</CargoMessage>");

        return xml.toString();
    }

    private String buildGoodsXml(Goods goods) {
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

    private String escapeXml(String text) {
        if (text == null) return "";

        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
