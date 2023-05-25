import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class MainGUI extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JList dataList;
    private JTextField dataField;
    private JButton addButton;
    private JButton removeButton;
    private JLabel meanLabel;
    private JPanel statPanel;
    private JTextField lowerField;
    private JLabel withinLabel1;
    private JLabel withinLabel2;
    private JLabel withinLabel;
    private JTextField upperField;
    private JLabel medianLabel;
    private JLabel stdLabel;

    private Calculator calculator;

    public MainGUI() {
        this.calculator = new Calculator();

        createUIComponents();
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
    }

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
    }
}
