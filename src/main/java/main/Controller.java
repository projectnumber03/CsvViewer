package main;

import engine.DataGenerator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {

    private final DataGenerator dg = new DataGenerator();
    private Interface myInterface = new Interface();
    private JFrame jFrame = new JFrame("CsvViewer");

    void initialize() {
        myInterface.getNextButton().addActionListener(new NextActionListener());
        myInterface.getBackButton().addActionListener(new BackActionListener());
        myInterface.getOpenButton().addActionListener(new ChooseActionListener());
        myInterface.getSearchField().addKeyListener(new SearchFieldListener());
        MyDragDropListener myDragDropListener = new MyDragDropListener();
        myDragDropListener.setController(this);
        myInterface.$$$getRootComponent$$$().setDropTarget(new DropTarget(jFrame, myDragDropListener));
        jFrame.setContentPane(myInterface.$$$getRootComponent$$$());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setSize(720, 480);
        jFrame.setVisible(true);

    }

    void process(final String fileName){
        try {
            jFrame.setTitle(fileName + " - CsvViewer");
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

    public class SearchFieldListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                JTable table = myInterface.getCsvTable();
                String column = IntStream.range(0, table.getColumnCount()).boxed().map(table::getColumnName).filter(s -> s.toUpperCase().contains(myInterface.getSearchField().getText().toUpperCase())).findFirst().orElse("");
                table.requestFocus();
                table.changeSelection(0, dg.getTblModel().findColumn(column),false, false);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) { }
    }
}
