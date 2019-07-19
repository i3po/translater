package controller;



import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

import org.json.JSONObject;
import model.Model;
import view.View;


import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 Start method main here
 Need to insert Yandex Translate API
 **/


public class Controller implements NativeKeyListener, NativeMouseInputListener{
    private View view;
    private Model model;
    final static int NUM = 8192;
    final static int CAPSNUM = 16384;
    final static int ALT_L = 56;


    public static void main(String[] args) throws NativeHookException{
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        Controller controller = new Controller();
        controller.startProgram();


    }

    private void startProgram() throws NativeHookException {
        Controller controller = new Controller();
        model = new Model(controller);
        view = new View(controller);

        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.registerNativeHook();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);


    }

    public void translate() {
        try {
            String translatedTExt = model.translateText();
            System.out.println("Translated text: " + translatedTExt);
            if(translatedTExt != null && translatedTExt.length() > 1) view.initializeView(translatedTExt);
        } catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            e.getCause();
        }
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
      //  System.out.println(nativeKeyEvent);24832 8448
    }


      @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
          System.out.println(nativeKeyEvent.getKeyCode());
        switch (nativeKeyEvent.getKeyCode()){
            case ALT_L: {
                doAction();
                break;
            }
          default: {
              if (view.isVisible())
                  view.deInitialize();
          }

      }
    }

    private void doAction(){
        view.clearTextArea();
        translate();
    }



    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
       // System.out.println(nativeKeyEvent);
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
    }


   public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        if(view.isVisible())view.deInitialize();

    }

    public  static String readFromFileSettings(){
        File file = new File("./settings.json");
        try {
            if(!file.exists()){
                Files.createFile(file.toPath());
                System.out.println(file + " created");
            }
            List<String> strings =  Files.readAllLines(file.toPath());
            StringBuffer stringBuffer = new StringBuffer();
            for(String s : strings)stringBuffer.append(s);
            JSONObject obj = new JSONObject(stringBuffer.toString());
            return  (String) obj.get("apiKey");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isConditionPassed(NativeMouseEvent nativeMouseEvent){
        int modifiers = nativeMouseEvent.getModifiers();
        return  ((nativeMouseEvent.getClickCount() == 2) && ((modifiers == NUM) || modifiers == (NUM + CAPSNUM)));
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        if (isConditionPassed(nativeMouseEvent)) {
            doAction();
        }
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        //System.out.println(nativeMouseEvent);
    }

    @Override
        public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        //System.out.println(nativeMouseEvent);
    }

}
