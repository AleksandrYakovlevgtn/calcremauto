package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class CalculatorPanel extends JPanel implements ActionListener, KeyListener {
    private JTextField inputField;
    private JTextArea historyArea;
    boolean results = false;

    public CalculatorPanel() {
        setLayout(new GridBagLayout());
        JPanel buttonPanel = new JPanel();

        inputField = new JTextField(20);
        inputField.setEditable(false); // Только для отображения
        inputField.setFocusable(true); // Установка фокуса
        inputField.addKeyListener(this);
        inputField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inputField.setBackground(Color.WHITE);

        historyArea = new JTextArea(6, 20);
        historyArea.setEditable(false);
        historyArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton[] digitButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            digitButtons[i] = new JButton(Integer.toString(i));
            digitButtons[i].addActionListener(this);
        }

        JButton[] operationButtons = {
                new JButton("+"),
                new JButton("-"),
                new JButton("*"),
                new JButton("/"),
                new JButton("="),
                new JButton("C"),
                new JButton("←"),
                new JButton(","),
                new JButton("%")
        };
        for (JButton button : operationButtons) {
            button.addActionListener(this);
        }

        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(digitButtons[7], createGridBagConstraints(0, 1, 1, 1));
        buttonPanel.add(digitButtons[8], createGridBagConstraints(1, 1, 1, 1));
        buttonPanel.add(digitButtons[9], createGridBagConstraints(2, 1, 1, 1));
        buttonPanel.add(digitButtons[4], createGridBagConstraints(0, 2, 1, 1));
        buttonPanel.add(digitButtons[5], createGridBagConstraints(1, 2, 1, 1));
        buttonPanel.add(digitButtons[6], createGridBagConstraints(2, 2, 1, 1));
        buttonPanel.add(digitButtons[1], createGridBagConstraints(0, 3, 1, 1));
        buttonPanel.add(digitButtons[2], createGridBagConstraints(1, 3, 1, 1));
        buttonPanel.add(digitButtons[3], createGridBagConstraints(2, 3, 1, 1));
        buttonPanel.add(digitButtons[0], new GridBagConstraints(0, 4, 2, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 1, 1));
        buttonPanel.add(operationButtons[5], createGridBagConstraints(0, 0, 1, 1));
        buttonPanel.add(operationButtons[6], createGridBagConstraints(1, 0, 1, 1));
        buttonPanel.add(operationButtons[8], createGridBagConstraints(2, 0, 1, 1));
        buttonPanel.add(operationButtons[3], createGridBagConstraints(3, 0, 1, 1));
        buttonPanel.add(operationButtons[2], createGridBagConstraints(3, 1, 1, 1));
        buttonPanel.add(operationButtons[1], createGridBagConstraints(3, 2, 1, 1));
        buttonPanel.add(operationButtons[0], createGridBagConstraints(3, 3, 1, 1));
        buttonPanel.add(operationButtons[4], createGridBagConstraints(3, 4, 1, 1));
        buttonPanel.add(operationButtons[7], createGridBagConstraints(2, 4, 1, 1));

        add(historyArea, new GridBagConstraints(0, 0, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(1, 7, 1, 8), 1, 1));
        add(inputField, new GridBagConstraints(0, 1, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(3, 7, 1, 8), 1, 1));
        add(buttonPanel, new GridBagConstraints(0, 2, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 1, 5), 1, 1));
        JLabel label = new JLabel("<html><font color='red'>Все цены материалов указывать <br>в килограммах и шт.</font></html>");
        label.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK,1,true),"<html><font color='black'>Внимание!!!</font></html>"));
        add(label, new GridBagConstraints(0, 3, 1, 1, 1, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 1, 5), 1, 1));
    }

    private double evaluateExpression(String expression) {
        // Разбиваем выражение на операнды и операторы
        String[] tokens = expression.split("\\s*(?<=[-+*/%])|(?=[-+*/%])\\s*");
        if (tokens.length == 3) { // Если есть два числа и один знак
            double operand1 = Double.parseDouble(tokens[0]);
            char operator = tokens[1].charAt(0);
            double operand2 = Double.parseDouble(tokens[2]);

            switch (operator) {
                case '+':
                    return operand1 + operand2;
                case '-':
                    return operand1 - operand2;
                case '*':
                    return operand1 * operand2;
                case '/':
                    if (operand2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return operand1 / operand2;
                case '%':
                    return operand1 * (operand2 / 100);
                default:
                    throw new ArithmeticException("Invalid operator");
            }
        } else if (tokens.length == 5 && tokens[3].endsWith("%")) {
            double operand1 = Double.parseDouble(tokens[0]);
            char operator = tokens[1].charAt(0);
            double operand2 = Double.parseDouble(tokens[2]);
            operand2 = (operand1 / 100) * operand2;

            switch (operator) {
                case '+':
                    return operand1 + operand2;
                case '-':
                    return operand1 - operand2;
                case '*':
                    return operand1 * operand2;
                case '/':
                    if (operand2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return operand1 / operand2;
                default:
                    throw new ArithmeticException("Invalid operator");
            }
        } else { // В остальных случаях формат некорректен
            throw new ArithmeticException("Invalid expression format");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();
        if ("0123456789".contains(text)) {
            inputField.setText(inputField.getText() + text);
        } else if ("+-*/".contains(text)) {
            String currentText = inputField.getText();
            if (!currentText.isEmpty() && Character.isDigit(currentText.charAt(currentText.length() - 1))) {
                try {
                    if (results) {
                        double result = evaluateExpression(currentText);
                        inputField.setText(Double.toString(result));
                        appendToHistory(currentText + " = " + result);
                    } else {
                        results = true;
                    }
                } catch (ArithmeticException ex) {
                    appendToHistory("Error: " + ex.getMessage());
                }
            }
            inputField.setText(inputField.getText() + " " + text + " ");
        } else if ("=".equals(text)) {
            String expression = inputField.getText();
            try {
                double result = evaluateExpression(expression);
                appendToHistory(expression + " = " + result);
                results = false;
            } catch (ArithmeticException ex) {
                appendToHistory("Error: " + ex.getMessage());
            }
            inputField.setText("");
        } else if ("C".equals(text)) {
            inputField.setText("");
        } else if ("←".equals(text)) {
            String currentText = inputField.getText();
            if (!currentText.isEmpty()) {
                inputField.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if ("%".equals(text)) {
            String expression = inputField.getText() + " % ";
            try {
                double result = evaluateExpression(expression);
                inputField.setText(Double.toString(result));
                appendToHistory(expression + " = " + result);
                results = false;
            } catch (ArithmeticException ex) {
                appendToHistory("Error: " + ex.getMessage());
            }
        } else if (",".equals(text)) {
            String currentText = inputField.getText();
            if (currentText.isEmpty() || !Character.isDigit(currentText.charAt(currentText.length() - 1))) {
                inputField.setText(inputField.getText() + "0.");
            } else {
                inputField.setText(inputField.getText() + ".");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (Character.isDigit(keyChar) || "+-*/".indexOf(keyChar) != -1) {
            inputField.setText(inputField.getText() + keyChar);
        } else if (keyChar == KeyEvent.VK_ENTER) {
            String expression = inputField.getText();
            try {
                double result = evaluateExpression(expression);
                appendToHistory(expression + " = " + result + "\n");
            } catch (ArithmeticException ex) {
                appendToHistory("Error: " + ex.getMessage() + "\n");
            }
            inputField.setText("");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { // Обработка клавиши Backspace
            String currentText = inputField.getText();
            if (!currentText.isEmpty()) {
                inputField.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Нет необходимости в реализации этого метода
    }

    private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        return new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 1, 1);
    } // Устроение повторного кода конструктора

    private void appendToHistory(String text) {
        String currentText = historyArea.getText();
        String[] lines = currentText.split("\n");
        if (lines.length >= 5) {
            StringBuilder newText = new StringBuilder();
            for (int i = 1; i < lines.length; i++) {
                newText.append(lines[i]).append("\n");
            }
            newText.append(text).append("\n");
            historyArea.setText(newText.toString());
        } else {
            historyArea.append(text + "\n");
        }
    }
}