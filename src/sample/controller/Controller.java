package sample.controller;



import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

import org.json.JSONObject;
import sample.model.Model;
import sample.view.View;


import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Files.readAllLines;


/**
 Start method main here
 Need to insert Yandex Translate API
 **/


public class Controller implements NativeKeyListener, NativeMouseInputListener{
    private View view;
    private Model model;
    final static int NUM = 8448;
    final static int CAPSNUM = 24832;


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
        } catch (Exception e) {
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
            case 3653: {
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
       /**
        System.out.println("Method Pressed");
        System.out.println(nativeMouseEvent.getModifiers() + " " + nativeMouseEvent.getButton() + " " + nativeMouseEvent.getClickCount());
        //System.out.println(nativeMouseEvent.paramString());
        if(isConditionPassed(nativeMouseEvent)) {
            doAction();
            return;
        } **/
        if(view.isVisible())view.deInitialize();

    }

    public  static String readFromFileSettings(){
        File file = new File("./src/settings.json");
        try {
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
        System.out.println(nativeMouseEvent.paramString() + " " + nativeMouseEvent.getButton() + " " + nativeMouseEvent.getPoint().toString());
        return  ((nativeMouseEvent.getButton() == NativeMouseEvent.BUTTON1)
                && (nativeMouseEvent.getClickCount() == 2)
                && (modifiers == NUM) || (modifiers == CAPSNUM));
    }
    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        //System.out.println(nativeMouseEvent);
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
