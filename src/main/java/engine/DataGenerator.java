package engine;

import com.opencsv.CSVReader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataGenerator {
    private String fileName;
    private CSVReader csvReader;
    private JTable csvTable;
    private DefaultTableModel tblModel = new DefaultTableModel();
    private int position = 1;
    private boolean lock;

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setCsvTable(JTable csvTable) throws FileNotFoundException {
        this.csvTable = csvTable;
        csvReader = new CSVReader(new FileReader(fileName), ';', '"','\n', 0);
        String[] headers = read();
        tblModel.setColumnIdentifiers(headers);
        csvTable.setModel(tblModel);
        IntStream.range(0, csvTable.getColumnCount()).forEach(i -> {
            csvTable.getColumnModel().getColumn(i).setMinWidth(headers[i].length() * 9);
        });
    }

    private String[] read(){
        try {
            String[] record;
            if((record = csvReader.readNext()) != null)
            return record;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateItems() throws FileNotFoundException {
        unlock();
        csvReader = new CSVReader(new FileReader(fileName), ';', '"','\n', position);
        for(int i = 0; i < 500; i++){
            String[] s = read();
            if(s != null) tblModel.addRow(s);
            else {
                lock();
                break;
            }
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DefaultTableModel getTblModel() {
        return tblModel;
    }

    private void lock() {
        this.lock = true;
    }

    private void unlock() {
        this.lock = false;
    }

    public boolean isLock() {
        return lock;
    }
}
