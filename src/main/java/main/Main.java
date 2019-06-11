package main;

import com.bulenkov.darcula.DarculaLaf;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;

public class Main  {


    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        BasicLookAndFeel darcula = new DarculaLaf();
        UIManager.setLookAndFeel(darcula);
        if (args.length > 0) {
            Controller controller = new Controller();
            controller.initialize();
            controller.process(args[0]);
        }else {
            Controller controller = new Controller();
            controller.initialize();
        }
    }
}
