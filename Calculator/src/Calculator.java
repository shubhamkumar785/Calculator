import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator();
        });
    }

    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(82, 81, 80);
    Color customDarkGray = new Color(41, 40, 40);
    Color customBlack = new Color(0, 0, 0);
    Color customOrange = new Color(242, 135, 53);

    String[] buttonsValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };
    String[] rightSymbols = { "÷", "x", "-", "+", "=" };
    String[] topSymbols = { "AC", "+/-", "%" };

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonsValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonsValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            button.addActionListener(e -> {
                JButton btn = (JButton) e.getSource();
                String val = btn.getText();
                if (Arrays.asList(rightSymbols).contains(val)) {
                    if (val.equals("=")) {
                        if (A != null && operator != null && B != null) {
                            double numA = Double.parseDouble(A);
                            double numB = Double.parseDouble(B);
                            double result = 0;
                            switch (operator) {
                                case "+":
                                    result = numA + numB;
                                    break;
                                case "-":
                                    result = numA - numB;
                                    break;
                                case "x":
                                    result = numA * numB;
                                    break;
                                case "÷":
                                    if (numB != 0) {
                                        result = numA / numB;
                                    } else {
                                        displayLabel.setText("Error");
                                        clearAll();
                                        return;
                                    }
                                    break;
                            }
                            displayLabel.setText(removeZeroDecimal(result));
                            clearAll();
                            A = displayLabel.getText();
                        }
                    } else if ("+-x÷".contains(val)) {
                        operator = val;
                        A = displayLabel.getText();
                        displayLabel.setText("0");
                        B = null;
                    }
                } else if (Arrays.asList(topSymbols).contains(val)) {
                    if (val.equals("AC")) {
                        clearAll();
                        displayLabel.setText("0");
                        A = "0";
                    } else if (val.equals("+/-")) {
                        double numDisplay = Double.parseDouble(displayLabel.getText());
                        numDisplay *= -1;
                        displayLabel.setText(removeZeroDecimal(numDisplay));
                    } else if (val.equals("%")) {
                        double numDisplay = Double.parseDouble(displayLabel.getText());
                        numDisplay /= 100;
                        displayLabel.setText(removeZeroDecimal(numDisplay));
                    }
                } else if (val.equals("√")) {
                    double numDisplay = Double.parseDouble(displayLabel.getText());
                    if (numDisplay >= 0) {
                        numDisplay = Math.sqrt(numDisplay);
                        displayLabel.setText(removeZeroDecimal(numDisplay));
                    } else {
                        displayLabel.setText("Error");
                    }
                } else {
                    if (val.equals(".")) {
                        if (!displayLabel.getText().contains(".")) {
                            displayLabel.setText(displayLabel.getText() + ".");
                        }
                    } else if ("0123456789".contains(val)) {
                        if (displayLabel.getText().equals("0") || (operator != null && B == null)) {
                            displayLabel.setText(val);
                            if (operator != null) {
                                B = val;
                            }
                        } else {
                            displayLabel.setText(displayLabel.getText() + val);
                            if (operator != null) {
                                B = displayLabel.getText();
                            }
                        }
                        if (operator != null) {
                            B = displayLabel.getText();
                        }
                    }
                }
            });
        }
        frame.setVisible(true);

    }

    void clearAll() {
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

}