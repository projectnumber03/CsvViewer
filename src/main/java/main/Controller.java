package main;

import engine.DataGenerator;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.IntStream;

public class Controller {

    final DataGenerator dg = new DataGenerator();
    Interface myInterface = new Interface();
    JFrame jFrame = new JFrame("CsvViewer");

    public void initialize() {
        myInterface.getNextButton().addActionListener(new NextActionListener());
        myInterface.getBackButton().addActionListener(new BackActionListener());
        myInterface.getOpenButton().addActionListener(new ChooseActionListener());
        jFrame.setContentPane(myInterface.$$$getRootComponent$$$());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setSize(720, 480);
        jFrame.setVisible(true);
    }

    public void process(final String fileName){
        try {
            dg.setFileName(fileName);
            dg.setCsvTable(myInterface.getCsvTable());
            dg.generateItems();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class NextActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!dg.isLock()){
                dg.setPosition(dg.getPosition() + 500);
                dg.getTblModel().getDataVector().removeAllElements();
                try {
                    dg.generateItems();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    public class BackActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(dg.getPosition() > 1){
                dg.setPosition(dg.getPosition() - 500);
                dg.getTblModel().getDataVector().removeAllElements();
                try {
                    dg.generateItems();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public class ChooseActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileopen = new JFileChooser();
            fileopen.setFileFilter(new FileNameExtensionFilter("Таблицы CSV", "csv"));
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                dg.setPosition(1);
                dg.getTblModel().getDataVector().removeAllElements();
                process(file.getPath());
            }
        }
    }
}
