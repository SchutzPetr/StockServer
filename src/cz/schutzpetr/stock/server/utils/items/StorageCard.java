package cz.schutzpetr.stock.server.utils.items;

import cz.schutzpetr.stock.core.stockcard.ConnectionStockCard;
import cz.schutzpetr.stock.core.stockcard.SimpleStockCard;
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
public class StorageCard extends SimpleStockCard {
    private final InputStream imgInputStream;

    public StorageCard(int cardNumber, String itemName, EuropeanArticleNumber ean, String itemNumber, double pricePerUnit, String producer,
                       double weight, int numberOfUnitInPackage, InputStream imgInputStream) {
        super(cardNumber, itemName, ean, itemNumber, pricePerUnit, producer, weight, numberOfUnitInPackage);
        this.imgInputStream = imgInputStream;
    }


    public StorageCard(ConnectionStockCard connectionStockCard) {
        this(connectionStockCard.getCardNumber(), connectionStockCard.getItemName(), connectionStockCard.getEuropeanArticleNumber(),
                connectionStockCard.getItemNumber(), connectionStockCard.getPricePerUnit(), connectionStockCard.getProducer(), connectionStockCard.getWeight(),
                connectionStockCard.getNumberOfUnitInPackage(), new ByteArrayInputStream(connectionStockCard.getImgByteArray()));
    }

    public StorageCard(SimpleStockCard simpleStockCard, InputStream imgInputStream) {
        this(simpleStockCard.getCardNumber(), simpleStockCard.getItemName(), simpleStockCard.getEuropeanArticleNumber(),
                simpleStockCard.getItemNumber(), simpleStockCard.getPricePerUnit(), simpleStockCard.getProducer(), simpleStockCard.getWeight(),
                simpleStockCard.getNumberOfUnitInPackage(), imgInputStream);
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

    public ConnectionStockCard getConnectionStockCard() {
        return new ConnectionStockCard(this, getByteArrayOutputStream(imgInputStream).toByteArray());
    }


    public InputStream getImageInputStream() {
        return imgInputStream;
    }

}

