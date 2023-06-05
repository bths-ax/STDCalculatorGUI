import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class MainGUI extends JFrame implements ActionListener, KeyListener {
    private JPanel mainPanel;
    private JList dataList;
    private JTextField dataField;
    private JButton addButton;
    private JButton removeButton;
    private JLabel meanLabel;
    private JTextField lowerField;
    private JLabel withinLabel1;
    private JLabel withinLabel2;
    private JLabel withinLabel;
    private JTextField upperField;
    private JLabel medianLabel;
    private JLabel stdLabel;
    private JScrollPane dataScroller;
    private JButton clearButton;
    private JPanel inputPanel;
    private JButton importButton;
    private JButton exportButton;
    private JFileChooser chooser;

    private Calculator calculator;

    public MainGUI() {
        this.calculator = new Calculator();

        createUIComponents();
        updateUIComponents();
    }

    public void createUIComponents() {
        this.chooser = new JFileChooser();
        this.chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                String name = f.getName();
                String ext = "";
                int extIdx = name.lastIndexOf(".");
                if (extIdx != -1) {
                    ext = name.substring(extIdx + 1);
                }
                return ext.equals("std");
            }

            @Override
            public String getDescription() {
                return "STD Files (.std)";
            }
        });

        setContentPane(mainPanel);
        setTitle("Standard Deviation Calculator");
        setSize(600, 500);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        dataList.setModel(calculator);
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        clearButton.addActionListener(this);

        importButton.addActionListener(this);
        exportButton.addActionListener(this);

        lowerField.addKeyListener(this);
        upperField.addKeyListener(this);
    }

    public void updateUIComponents() {
        dataList.updateUI();
        medianLabel.setText(String.format("Median: %.3f", calculator.calculateMedian()));
        meanLabel.setText(String.format("Mean: %.3f", calculator.calculateMean()));
        stdLabel.setText(String.format("STD: %.3f", calculator.calculateStd()));

        double within;
        try {
            double left = Double.parseDouble(lowerField.getText().trim());
            double right = Double.parseDouble(upperField.getText().trim());
            within = 100 * calculator.calculatePercentWithinStd(left, right);
        } catch (Exception ex) {
            within = 0;
        }
        withinLabel.setText(String.format("STDs: %.3f%%", within));
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == addButton) {
            double value;
            try {
                value = Double.parseDouble(dataField.getText().trim());
                calculator.add(value);
            } catch (Exception ex) { }
            dataField.setText("");
            dataField.grabFocus();
        }

        else if (obj == removeButton) {
            int idx = dataList.getSelectedIndex();
            if (idx != -1) {
                calculator.remove(idx);
            }
        }

        else if (obj == clearButton) {
            calculator.clear();
        }

        else if (obj == importButton) {
            int chooserStatus = chooser.showOpenDialog(this);
            if (chooserStatus == JFileChooser.APPROVE_OPTION) {
                File chosen = chooser.getSelectedFile();
                try {
                    String serialized = Files.readString(chosen.toPath());
                    calculator.clear();
                    calculator.deserialize(serialized);
                } catch (Exception ex) { }
            }
        }

        else if (obj == exportButton) {
            int chooserStatus = chooser.showOpenDialog(this);
            if (chooserStatus == JFileChooser.APPROVE_OPTION) {
                File chosen = chooser.getSelectedFile();
                if (!chosen.getName().endsWith(".std")) {
                    chosen = new File(chosen.getPath() + ".std");
                }
                try {
                    Files.writeString(chosen.toPath(), calculator.serialize(), StandardOpenOption.CREATE);
                } catch (Exception ex) { }
            }
        }

        updateUIComponents();
    }

    public void keyTyped(KeyEvent e) { updateUIComponents(); }
    public void keyPressed(KeyEvent e) { updateUIComponents(); }
    public void keyReleased(KeyEvent e) { updateUIComponents(); }
}
