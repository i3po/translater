package sample.controller;



import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.*;

import sample.model.Model;
import sample.view.View;



import java.util.logging.Level;
import java.util.logging.Logger;


public class Controller implements NativeKeyListener, NativeMouseInputListener{
    private View view;
    private Model model;


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

        String translatedTExt = null;
        try {
            translatedTExt = model.translateText();
        } catch (Exception e) {
            e.printStackTrace();
        }
       if(translatedTExt != null) view.initializeView(translatedTExt);
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
      switch (nativeKeyEvent.getKeyCode()){
          case NativeMouseEvent.BUTTON2 :
          case NativeKeyEvent.VC_CONTROL :
              view.clearTextArea();
              translate();
              break;


          default: {
              if (view.isVisible())
                  view.deInitialize();
          }

      }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        System.out.println("keyrealesed");
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        System.out.println("mouse clicked");
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        if(view.isVisible())view.deInitialize();
        System.out.println(nativeMouseEvent.paramString());
    }
    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
        public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
    }

}
