import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

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

    private Calculator calculator;

    public MainGUI() {
        this.calculator = new Calculator();

        createUIComponents();
        updateUIComponents();
    }

    public void createUIComponents() {
        setContentPane(mainPanel);
        setTitle("Standard Deviation Calculator");
        setSize(750, 300);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        dataList.setModel(calculator);
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        clearButton.addActionListener(this);

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
        } else if (obj == removeButton) {
            int idx = dataList.getSelectedIndex();
            if (idx != -1) {
                calculator.remove(idx);
            }
        } else if (obj == clearButton) {
            calculator.clear();
        }

        updateUIComponents();
    }

    public void keyTyped(KeyEvent e) { updateUIComponents(); }
    public void keyPressed(KeyEvent e) { updateUIComponents(); }
    public void keyReleased(KeyEvent e) { updateUIComponents(); }
}
