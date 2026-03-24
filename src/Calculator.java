import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {

    int borderWidth = 360;
    int borderHeight = 540;

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel  = new JPanel();
    JPanel buttonPanel = new JPanel();

    // Logic Variables
    String currentInput = "";
    String expression = "";
    double num1 = 0;
    String operator = "";
    boolean isOperatorClicked = false;

    // Custom Colors
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "DEL", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };

    Calculator() {

        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Display
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel, BorderLayout.CENTER);

        // Buttons Panel
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(customBlack);

        for (int i = 0; i < buttonValues.length; i++) {

            JButton button = new JButton(buttonValues[i]);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setFocusPainted(false);
            button.setBorder(new LineBorder(customBlack));

            String value = buttonValues[i];

            // Styling
            if ("0123456789.√".contains(value)) {
                button.setBackground(customDarkGray);
                button.setForeground(Color.WHITE);
            } 
            else if ("+-×÷=".contains(value)) {
                button.setBackground(customLightGray);
                button.setForeground(Color.BLACK);
            } 
            else {
                button.setBackground(customOrange);
                button.setForeground(Color.WHITE);
            }

            // Action
            button.addActionListener(e -> handleButtonClick(value));

            buttonPanel.add(button);
        }

        frame.add(displayPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // 🔥 LOGIC
    void handleButtonClick(String value) {

        // Numbers
        if ("0123456789".contains(value)) {
            currentInput += value;
            expression += value;
            displayLabel.setText(expression);
        }

        // Decimal
        else if (value.equals(".")) {
            if (!currentInput.contains(".")) {
                currentInput += ".";
                expression += ".";
                displayLabel.setText(expression);
            }
        }

        // AC
        else if (value.equals("AC")) {
            currentInput = "";
            expression = "";
            num1 = 0;
            operator = "";
            displayLabel.setText("0");
        }

        // Square Root
        else if (value.equals("√")) {
            try {
                double num = Double.parseDouble(displayLabel.getText());
                if (num < 0) {
                    displayLabel.setText("Error");
                } else {
                    double result = Math.sqrt(num);
                    displayLabel.setText(removeDotZero(result));
                    currentInput = String.valueOf(result);
                    expression = currentInput;
                }
            } catch (Exception e) {
                displayLabel.setText("Error");
            }
        }

        // Operators
        else if (value.equals("+") || value.equals("-") || value.equals("×") || value.equals("÷")) {

            if (!currentInput.isEmpty()) {
                num1 = Double.parseDouble(currentInput);
                operator = value;
                expression += value;
                displayLabel.setText(expression);
                currentInput = "";
                isOperatorClicked = true;
            }
        }

        // Equals
        else if (value.equals("=")) {
            try {
                double num2 = Double.parseDouble(currentInput);
                double result = 0;

                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "×": result = num1 * num2; break;
                    case "÷":
                        if (num2 == 0) {
                            displayLabel.setText("Error");
                            return;
                        }
                        result = num1 / num2;
                        break;
                }

                displayLabel.setText(removeDotZero(result));
                currentInput = String.valueOf(result);
                expression = currentInput;

            } catch (Exception e) {
                displayLabel.setText("Error");
            }
        }

        // DEL (remove last character)
else if (value.equals("DEL")) {

    if (!expression.isEmpty()) {
        // Remove last char from expression
        char lastChar = expression.charAt(expression.length() - 1);
        expression = expression.substring(0, expression.length() - 1);

        // If last char was a number or '.', remove from currentInput
        if (Character.isDigit(lastChar) || lastChar == '.') {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
            }
        } else {
            // If it was an operator, reset currentInput so next number can start fresh
            currentInput = "";
        }

        // Update display
        if (expression.isEmpty()) {
            displayLabel.setText("0");
        } else {
            displayLabel.setText(expression);
        }
    }
}
        // %
        else if (value.equals("%")) {
            try {
                double num = Double.parseDouble(currentInput.isEmpty() ? "0" : currentInput);
                num = num / 100;
                currentInput = String.valueOf(num);
                expression = currentInput;
                displayLabel.setText(removeDotZero(num));
            } catch (Exception e) {
                displayLabel.setText("Error");
            }
        }
    }

    // Remove .0
    String removeDotZero(double value) {
        if (value == (long) value)
            return String.valueOf((long) value);
        else
            return String.valueOf(value);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}