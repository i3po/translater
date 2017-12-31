package sample.model;

import sample.controller.Controller;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class Model {
    private String lastBuferText = "";
    private String lastTranslatedText;
    private Controller controller;
    private Translater translater = new Translater();
    private static final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();


    public Model(Controller controller) {
        this.controller = controller;
    }


    private String readSelectedString() throws IOException, UnsupportedFlavorException {
        // get text from clipboard or selection:
        Clipboard clipboard = defaultToolkit.getSystemSelection();
        if (clipboard == null) { // on Windows
            System.out.println("INSIDE +++++++++++++++++++++++++++++++++++++++");
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        }
        String s;
        final Transferable data = clipboard.getContents(this);
        try {
            if (data != null
                    && data.isDataFlavorSupported(DataFlavor.getTextPlainUnicodeFlavor())) {
                final DataFlavor df = DataFlavor.getTextPlainUnicodeFlavor();
                final Reader sr = df.getReaderForText(data);
                BufferedReader bufferedReader = new BufferedReader(sr);
                String line = bufferedReader.readLine();
                StringBuilder stringBuffer = new StringBuilder();
                while (line != null){
                    stringBuffer.append(line).append('\n');
                    line = bufferedReader.readLine();
                }
                bufferedReader.close();
                s = stringBuffer.toString().trim();
                System.out.println(s);
            } else {
                s = null;
            }
        } catch (Exception ex) {
            if (data != null) {
                s = data.toString();
            } else {
                s = null;
            }
        }
        return s;
    }


    public String translateText() throws Exception {
        String selectedString = readSelectedString();
        System.out.println("Selected String: " + selectedString);
        if(lastBuferText.equals(selectedString)){
            return lastTranslatedText;
        }
        lastBuferText = selectedString;
        return lastTranslatedText = translater.tanslate(selectedString);
    }

}
