package sample.model;

import sample.controller.Controller;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class Model {
    private String lastBuferText = "";
    private String lastTranslatedText;
   private Controller controller;
   private Translater translater = new Translater();


    public Model(Controller controller) {
        this.controller = controller;
    }


    private String readSelectedString() throws IOException, UnsupportedFlavorException {
        Clipboard clipboard1 = Toolkit.getDefaultToolkit().getSystemClipboard();

        String textToTranslate = (String) clipboard1.getData(DataFlavor.stringFlavor);
        System.out.println("string from buffer Reade" + textToTranslate);
        return textToTranslate.trim();
    }


    public String translateText() throws Exception {
        String selectedString = readSelectedString();

        if(lastBuferText.equals(selectedString)){
            System.out.println(lastTranslatedText);
            return lastTranslatedText;
        }
        lastBuferText = selectedString;

        return lastTranslatedText = translater.tanslate(selectedString);

    }

}
