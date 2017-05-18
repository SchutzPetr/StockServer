package cz.schutzpetr.stock.server.utils.items;

import cz.schutzpetr.stock.core.connection.ConnectionStorageCard;
import cz.schutzpetr.stock.core.utils.EAN;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Petr Schutz on 03.04.2017
 *
 * @author Petr Schutz
 * @version 1.0
 */
public class StorageCard {
    private final int cardNumber;
    private final String itemName;
    private final EAN ean;
    private final String itemNumber;
    private final double pricePerUnit;
    private final String producer;
    private final double weight;
    private final int numberOfUnitInPackage;
    private final InputStream imgInputStream;

    public StorageCard(int cardNumber, String itemName, EAN ean, String itemNumber, double pricePerUnit, String producer, double weight, int numberOfUnitInPackage, InputStream imgInputStream) {
        this.cardNumber = cardNumber;
        this.itemName = itemName;
        this.ean = ean;
        this.itemNumber = itemNumber;
        this.pricePerUnit = pricePerUnit;
        this.producer = producer;
        this.weight = weight;
        this.numberOfUnitInPackage = numberOfUnitInPackage;
        this.imgInputStream = imgInputStream;
    }

    public StorageCard(ConnectionStorageCard object) {
        this(object.getCardNumber(), object.getItemName(), object.getEan(), object.getItemNumber(), object.getPricePerUnit(), object.getProducer(), object.getWeight(), object.getNumberOfUnitInPackage(), new ByteArrayInputStream(object.getImgByteArray()));
    }

    private static ByteArrayOutputStream getByteArrayOutputStream(InputStream imgInputStream) {
        BufferedImage image = SwingFXUtils.fromFXImage(new Image(imgInputStream), null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    public ConnectionStorageCard getConnectionStorageCard() {
        return new ConnectionStorageCard(cardNumber, itemName, ean, itemNumber, pricePerUnit, producer, weight, numberOfUnitInPackage, getByteArrayOutputStream(imgInputStream).toByteArray());
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public EAN getEan() {
        return ean;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public String getProducer() {
        return producer;
    }

    public double getWeight() {
        return weight;
    }

    public int getNumberOfUnitInPackage() {
        return numberOfUnitInPackage;
    }

    public InputStream getImageInputStream() {
        return imgInputStream;
    }

}

