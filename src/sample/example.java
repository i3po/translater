//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package sample;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public class example extends JFrame implements ActionListener, ItemListener, NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener, WindowListener {
    private static final long serialVersionUID = 1541183202160543102L;
    private JMenu menuSubListeners;
    private JMenuItem menuItemQuit;
    private JMenuItem menuItemClear;
    private JCheckBoxMenuItem menuItemEnable;
    private JCheckBoxMenuItem menuItemKeyboardEvents;
    private JCheckBoxMenuItem menuItemButtonEvents;
    private JCheckBoxMenuItem menuItemMotionEvents;
    private JCheckBoxMenuItem menuItemWheelEvents;
    private JTextArea txtEventInfo;
    private static final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

    public example() {
        this.setTitle("JNativeHook Demo");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(2);
        this.setSize(600, 300);
        this.addWindowListener(this);
        JMenuBar var1 = new JMenuBar();
        JMenu var2 = new JMenu("File");
        var2.setMnemonic(70);
        var1.add(var2);
        this.menuItemQuit = new JMenuItem("Quit", 81);
        this.menuItemQuit.addActionListener(this);
        this.menuItemQuit.setAccelerator(KeyStroke.getKeyStroke(115, 8));
        this.menuItemQuit.getAccessibleContext().setAccessibleDescription("Exit the program");
        var2.add(this.menuItemQuit);
        JMenu var3 = new JMenu("View");
        var3.setMnemonic(86);
        var1.add(var3);
        this.menuItemClear = new JMenuItem("Clear", 67);
        this.menuItemClear.addActionListener(this);
        this.menuItemClear.setAccelerator(KeyStroke.getKeyStroke(67, 3));
        this.menuItemClear.getAccessibleContext().setAccessibleDescription("Clear the screen");
        var3.add(this.menuItemClear);
        var3.addSeparator();
        this.menuItemEnable = new JCheckBoxMenuItem("Enable Native Hook");
        this.menuItemEnable.addItemListener(this);
        this.menuItemEnable.setMnemonic(72);
        this.menuItemEnable.setAccelerator(KeyStroke.getKeyStroke(72, 3));
        var3.add(this.menuItemEnable);
        this.menuSubListeners = new JMenu("Listeners");
        this.menuSubListeners.setMnemonic(76);
        var3.add(this.menuSubListeners);
        this.menuItemKeyboardEvents = new JCheckBoxMenuItem("Keyboard Events");
        this.menuItemKeyboardEvents.addItemListener(this);
        this.menuItemKeyboardEvents.setMnemonic(75);
        this.menuItemKeyboardEvents.setAccelerator(KeyStroke.getKeyStroke(75, 3));
        this.menuSubListeners.add(this.menuItemKeyboardEvents);
        this.menuItemButtonEvents = new JCheckBoxMenuItem("Button Events");
        this.menuItemButtonEvents.addItemListener(this);
        this.menuItemButtonEvents.setMnemonic(66);
        this.menuItemButtonEvents.setAccelerator(KeyStroke.getKeyStroke(66, 3));
        this.menuSubListeners.add(this.menuItemButtonEvents);
        this.menuItemMotionEvents = new JCheckBoxMenuItem("Motion Events");
        this.menuItemMotionEvents.addItemListener(this);
        this.menuItemMotionEvents.setMnemonic(77);
        this.menuItemMotionEvents.setAccelerator(KeyStroke.getKeyStroke(77, 3));
        this.menuSubListeners.add(this.menuItemMotionEvents);
        this.menuItemWheelEvents = new JCheckBoxMenuItem("Wheel Events");
        this.menuItemWheelEvents.addItemListener(this);
        this.menuItemWheelEvents.setMnemonic(87);
        this.menuItemWheelEvents.setAccelerator(KeyStroke.getKeyStroke(87, 3));
        this.menuSubListeners.add(this.menuItemWheelEvents);
        this.setJMenuBar(var1);
        this.txtEventInfo = new JTextArea();
        this.txtEventInfo.setEditable(false);
        this.txtEventInfo.setBackground(new Color(255, 255, 255));
        this.txtEventInfo.setForeground(new Color(0, 0, 0));
        this.txtEventInfo.setText("");
        JScrollPane var4 = new JScrollPane(this.txtEventInfo);
        var4.setPreferredSize(new Dimension(375, 125));
        this.add(var4, "Center");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);
        ConsoleHandler var5 = new ConsoleHandler();
        var5.setFormatter(new example.LogFormatter());
        var5.setLevel(Level.WARNING);
        logger.addHandler(var5);
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.menuItemQuit) {
            this.dispose();
        } else if (var1.getSource() == this.menuItemClear) {
            this.txtEventInfo.setText("");
        }

    }

    public void itemStateChanged(ItemEvent var1) {
        ItemSelectable var2 = var1.getItemSelectable();
        if (var2 == this.menuItemEnable) {
            try {
                if (var1.getStateChange() == 1) {
                    GlobalScreen.registerNativeHook();
                } else {
                    GlobalScreen.unregisterNativeHook();
                }
            } catch (NativeHookException var4) {
                this.txtEventInfo.append("Error: " + var4.getMessage() + "\n");
            }

            this.menuItemEnable.setState(GlobalScreen.isNativeHookRegistered());
            this.menuSubListeners.setEnabled(this.menuItemEnable.getState());
        } else if (var2 == this.menuItemKeyboardEvents) {
            if (var1.getStateChange() == 1) {
                GlobalScreen.addNativeKeyListener(this);
            } else {
                GlobalScreen.removeNativeKeyListener(this);
            }
        } else if (var2 == this.menuItemButtonEvents) {
            if (var1.getStateChange() == 1) {
                GlobalScreen.addNativeMouseListener(this);
            } else {
                GlobalScreen.removeNativeMouseListener(this);
            }
        } else if (var2 == this.menuItemMotionEvents) {
            if (var1.getStateChange() == 1) {
                GlobalScreen.addNativeMouseMotionListener(this);
            } else {
                GlobalScreen.removeNativeMouseMotionListener(this);
            }
        } else if (var2 == this.menuItemWheelEvents) {
            if (var1.getStateChange() == 1) {
                GlobalScreen.addNativeMouseWheelListener(this);
            } else {
                GlobalScreen.removeNativeMouseWheelListener(this);
            }
        }

    }

    public void nativeKeyPressed(NativeKeyEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeKeyReleased(NativeKeyEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeKeyTyped(NativeKeyEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeMouseClicked(NativeMouseEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeMousePressed(NativeMouseEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeMouseReleased(NativeMouseEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeMouseMoved(NativeMouseEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeMouseDragged(NativeMouseEvent var1) {
        this.displayEventInfo(var1);
    }

    public void nativeMouseWheelMoved(NativeMouseWheelEvent var1) {
        this.displayEventInfo(var1);
    }

    private void displayEventInfo(NativeInputEvent var1) {
        this.txtEventInfo.append("\n" + var1.paramString());

        try {
            if (this.txtEventInfo.getLineCount() > 100) {
                this.txtEventInfo.replaceRange("", 0, this.txtEventInfo.getLineEndOffset(this.txtEventInfo.getLineCount() - 1 - 100));
            }

            this.txtEventInfo.setCaretPosition(this.txtEventInfo.getLineStartOffset(this.txtEventInfo.getLineCount() - 1));
        } catch (BadLocationException var3) {
            this.txtEventInfo.setCaretPosition(this.txtEventInfo.getDocument().getLength());
        }

    }

    public void windowActivated(WindowEvent var1) {
    }

    public void windowClosing(WindowEvent var1) {
    }

    public void windowDeactivated(WindowEvent var1) {
    }

    public void windowDeiconified(WindowEvent var1) {
    }

    public void windowIconified(WindowEvent var1) {
    }

    public void windowOpened(WindowEvent var1) {
        this.requestFocusInWindow();
        this.menuItemEnable.setSelected(true);
        this.txtEventInfo.append("JNativeHook Version " + System.getProperty("jnativehook.lib.version"));
        this.txtEventInfo.append("\nAuto Repeat Rate: " + System.getProperty("jnativehook.key.repeat.rate"));
        this.txtEventInfo.append("\nAuto Repeat Delay: " + System.getProperty("jnativehook.key.repeat.delay"));
        this.txtEventInfo.append("\nDouble Click Time: " + System.getProperty("jnativehook.button.multiclick.iterval"));
        this.txtEventInfo.append("\nPointer Sensitivity: " + System.getProperty("jnativehook.pointer.sensitivity"));
        this.txtEventInfo.append("\nPointer Acceleration Multiplier: " + System.getProperty("jnativehook.pointer.acceleration.multiplier"));
        this.txtEventInfo.append("\nPointer Acceleration Threshold: " + System.getProperty("jnativehook.pointer.acceleration.threshold"));

        try {
            this.txtEventInfo.setCaretPosition(this.txtEventInfo.getLineStartOffset(this.txtEventInfo.getLineCount() - 1));
        } catch (BadLocationException var3) {
            this.txtEventInfo.setCaretPosition(this.txtEventInfo.getDocument().getLength());
        }

        this.menuItemKeyboardEvents.setSelected(true);
        this.menuItemButtonEvents.setSelected(true);
        this.menuItemMotionEvents.setSelected(true);
        this.menuItemWheelEvents.setSelected(true);
    }

    public void windowClosed(WindowEvent var1) {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException var3) {
            var3.printStackTrace();
        }

        System.runFinalization();
        System.exit(0);
    }

    public static void main(String[] var0) {
        StringBuffer var1 = (new StringBuffer("\n")).append("JNativeHook: Global keyboard and mouse hooking for Java.\n").append("Copyright (C) 2006-2016 Alexander Barker.  All Rights Received.\n").append("https://github.com/kwhat/jnativehook/\n").append("\n").append("JNativeHook is free software: you can redistribute it and/or modify\n").append("it under the terms of the GNU Lesser General Public License as published\n").append("by the Free Software Foundation, either version 3 of the License, or\n").append("(at your option) any later version.\n").append("\n").append("JNativeHook is distributed in the hope that it will be useful,\n").append("but WITHOUT ANY WARRANTY; without even the implied warranty of\n").append("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n").append("GNU General Public License for more details.\n").append("\n").append("You should have received a copy of the GNU Lesser General Public License\n").append("along with this program.  If not, see <http://www.gnu.org/licenses/>.\n");
        System.out.println(var1);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new example();
            }
        });
    }

    private final class LogFormatter extends Formatter {
        private LogFormatter() {
        }

        public String format(LogRecord var1) {
            StringBuilder var2 = new StringBuilder();
            var2.append(new Date(var1.getMillis())).append(" ").append(var1.getLevel().getLocalizedName()).append(":\t").append(this.formatMessage(var1));
            if (var1.getThrown() != null) {
                try {
                    StringWriter var3 = new StringWriter();
                    PrintWriter var4 = new PrintWriter(var3);
                    var1.getThrown().printStackTrace(var4);
                    var4.close();
                    var2.append(var3.toString());
                    var3.close();
                } catch (Exception var5) {
                    ;
                }
            }

            return var2.toString();
        }
    }
}
