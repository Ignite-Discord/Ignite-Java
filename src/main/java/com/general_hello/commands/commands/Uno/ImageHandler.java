package com.general_hello.commands.commands.Uno;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageHandler {
    private static final String PATH = "src/main/resources/uno_cards";
    private static final int height = 362;
    private static final int width = 242;


    public static InputStream getCardsImage(ArrayList<UnoCard> cards){
        try {
            BufferedImage img = new BufferedImage(cards.size() * width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < cards.size(); i++){
                BufferedImage card = ImageIO.read(getCardImage(cards.get(i), true));
                img.createGraphics().drawImage(card, i * width, 0,null);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (Exception err){
            err.printStackTrace();
            return null;
        }
    }

    public static File getCardImage(UnoCard card, boolean ignorecolor){
        String url = card.getColor().getToken() + card.getValue().getToken();
        if (ignorecolor && card.getValue() == UnoCard.Value.WILD || card.getValue() == UnoCard.Value.PLUSFOUR){
            url = card.getValue().getToken();
        }
        url += ".png";
        return new File(PATH, url);
    }
}
