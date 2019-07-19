package view;


import controller.Controller;


import javax.swing.*;
import java.awt.*;


public class View extends JFrame {
   private Controller controller;
   static  String text;
   private JTextArea textArea;


    public View(Controller controller) {
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        JPanel panel = new JPanel();
        textArea = new JTextArea(3,35);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        setDefaultLookAndFeelDecorated(true);
        panel.add(scrollPane);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        getContentPane().add(panel);
        this.controller = controller;
    }


    public void initializeView(String text) {
        //setSize(300, 200);
        textArea.append(text);
        pack();
        setCoordinatesCursor();
        setVisible(true);
    }

    public void deInitialize(){

        if (isVisible()) {
            clearTextArea();
            setVisible(false);
        }

    }

    public void clearTextArea(){
        textArea.setText(null);
    }


     private void setCoordinatesCursor(){
        int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int y = (int) MouseInfo.getPointerInfo().getLocation().getY();
        setLocation(x,y);//тут нужны координаты мыши

    }

    public static void main(String[] args) {
        text = "A JTextArea object represents a multiline area for displaying text. "
                + "You can change the number of lines that can be displayed at a time, "
                + "as well as the number of columns. You can wrap lines and words too. "
                + "You can also put your JTextArea in a JScrollPane to make it scrollable.";
        Controller controller = new Controller();
        View view = new View(controller);
        view.initializeView(text);


    }
}
