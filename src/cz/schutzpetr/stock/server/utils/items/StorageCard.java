package cz.schutzpetr.stock.server.utils.items;

import cz.schutzpetr.stock.core.storagecard.ConnectionStorageCard;
import cz.schutzpetr.stock.core.storagecard.SimpleStorageCard;
import cz.schutzpetr.stock.core.utils.EuropeanArticleNumber;
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
public class StorageCard extends SimpleStorageCard {
    private final InputStream imgInputStream;

    public StorageCard(int cardNumber, String itemName, EuropeanArticleNumber ean, String itemNumber, double pricePerUnit, String producer,
                       double weight, int numberOfUnitInPackage, InputStream imgInputStream) {
        super(cardNumber, itemName, ean, itemNumber, pricePerUnit, producer, weight, numberOfUnitInPackage);
        this.imgInputStream = imgInputStream;
    }


    public StorageCard(ConnectionStorageCard connectionStorageCard) {
        this(connectionStorageCard.getCardNumber(), connectionStorageCard.getItemName(), connectionStorageCard.getEuropeanArticleNumber(),
                connectionStorageCard.getItemNumber(), connectionStorageCard.getPricePerUnit(), connectionStorageCard.getProducer(), connectionStorageCard.getWeight(),
                connectionStorageCard.getNumberOfUnitInPackage(), new ByteArrayInputStream(connectionStorageCard.getImgByteArray()));
    }

    public StorageCard(SimpleStorageCard simpleStorageCard, InputStream imgInputStream) {
        this(simpleStorageCard.getCardNumber(), simpleStorageCard.getItemName(), simpleStorageCard.getEuropeanArticleNumber(),
                simpleStorageCard.getItemNumber(), simpleStorageCard.getPricePerUnit(), simpleStorageCard.getProducer(), simpleStorageCard.getWeight(),
                simpleStorageCard.getNumberOfUnitInPackage(), imgInputStream);
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
        return new ConnectionStorageCard(this, getByteArrayOutputStream(imgInputStream).toByteArray());
    }


    public InputStream getImageInputStream() {
        return imgInputStream;
    }

}

