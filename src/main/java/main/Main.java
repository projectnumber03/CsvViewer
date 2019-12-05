package main;

import com.bulenkov.darcula.DarculaLaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class Main  {


    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        BasicLookAndFeel darcula = new DarculaLaf();
        UIManager.setLookAndFeel(darcula);
        Controller controller = new Controller();
        controller.initialize();
        if (args.length > 0) {
            controller.process(args[0]);
        }
    }
}

