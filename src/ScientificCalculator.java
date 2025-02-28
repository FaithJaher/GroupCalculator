import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Math;

// Abstract class defining a calculator
abstract class Calculator {
    public abstract double calculate(double a, double b);
}

// Concrete classes implementing basic operations
class Addition extends Calculator {
    public double calculate(double a, double b) {
        return a + b;
    }
}

class Subtraction extends Calculator {
    public double calculate(double a, double b) {
        return a - b;
    }
}

class Multiplication extends Calculator {
    public double calculate(double a, double b) {
        return a * b;
    }
}

class Division extends Calculator {
    public double calculate(double a, double b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return a / b;
    }
}

// Scientific Calculator GUI
public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator = "";
    private double num1, num2;
    private boolean operatorClicked = false;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 100));
        display.setBackground(Color.BLACK); // Sets background to black
        display.setForeground(Color.WHITE);// Sets text color to white for contrast
        display.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 10, 1, 1));
        panel.setBackground(Color.BLACK);

        String[] buttons = {" "," "," "," "," "," ","DEL","AC","%","÷","+/-","x²","x³","xʸ","e^x","10ˣ","7","8","9",
                "x","1/x", "²√x","³√x","ʸ√x","ln","log","4","5","6","-",
                "x!","sin","cos","tan","e","EE","1","2","3","+",
                "Inv","sinh","cosh","tanh","π"," ","","0",".","="};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 40));
            button.addActionListener(this);
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            if (text.matches("[0-9]") || text.equals(".")) {
                button.setBackground(new Color(166, 88, 246));
            } else if (text.matches("[+\\-x÷^%=]") || text.equals("=")) {
                button.setBackground(new Color(134, 3, 227));
            } else if (text.equals("AC")) {
                button.setBackground(new Color(160, 121, 202));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(new Color(160, 121, 202));
            }
            panel.add(button);
        }
        add(panel, BorderLayout.CENTER);
    }
    private String formatScientificNotation(double value) {
        return String.valueOf(value).replace("E", " × 10^");
    }
    private boolean inverseMode = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            switch (command) {
                case "AC":
                    display.setText("");
                    operatorClicked = false;
                    break;
                case "=":
                    try {
                        String expression = display.getText().replace(" × 10^", "E"); // Handle scientific notation
                        if (operator.equals("e^x") && expression.startsWith("e^")) {
                            num1 = Double.parseDouble(expression.substring(2)); // Extract number after "e^"
                            display.setText(String.valueOf(Math.exp(num1))); // Compute e^x
                        }else if (operator.equals("sin") && expression.startsWith("sin")) {
                            num1 = Double.parseDouble(expression.substring(3));
                            display.setText(String.valueOf(Math.sin(Math.toRadians(num1))));
                        }else if (operator.equals("cos") && expression.startsWith("cos")) {
                            num1 = Double.parseDouble(expression.substring(3));
                            display.setText(String.valueOf(Math.cos(Math.toRadians(num1))));
                        }else if (operator.equals("tan") && expression.startsWith("tan")) {
                            num1 = Double.parseDouble(expression.substring(3));
                            display.setText(String.valueOf(Math.tan(Math.toRadians(num1))));
                        }else if (operator.equals("sin⁻¹") && display.getText().startsWith("sin⁻¹")) {
                            num1 = Double.parseDouble(display.getText().substring(5));
                            display.setText(String.valueOf(Math.toDegrees(Math.asin(num1))));
                        } else if (operator.equals("cos⁻¹") && display.getText().startsWith("cos⁻¹")) {
                            num1 = Double.parseDouble(display.getText().substring(5));
                            display.setText(String.valueOf(Math.toDegrees(Math.acos(num1))));
                        } else if (operator.equals("tan⁻¹") && display.getText().startsWith("tan⁻¹")) {
                            num1 = Double.parseDouble(display.getText().substring(5));
                            display.setText(String.valueOf(Math.toDegrees(Math.atan(num1))));
                        }else if (operator.equals("sinh") && expression.startsWith("sinh")) {
                            num1 = Double.parseDouble(expression.substring(4));
                            double sinhValue = Math.sinh(num1);
                            display.setText(formatScientificNotation(sinhValue));
                        }else if (operator.equals("cosh") && expression.startsWith("cosh")) {
                            num1 = Double.parseDouble(expression.substring(4));
                            double coshValue = Math.cosh(num1);
                            display.setText(formatScientificNotation(coshValue));
                        }else if (operator.equals("tanh") && expression.startsWith("tanh")) {
                            num1 = Double.parseDouble(expression.substring(4));
                            double tanhValue = Math.tanh(num1);
                            display.setText(formatScientificNotation(tanhValue));
                        }else if (operator.equals("sinh⁻¹") && display.getText().startsWith("sinh⁻¹")) {
                            num1 = Double.parseDouble(display.getText().substring(6));
                            display.setText(String.valueOf(Math.log(num1 + Math.sqrt(num1 * num1 + 1))));
                        }else if (operator.equals("cosh⁻¹") && display.getText().startsWith("cosh⁻¹")) {
                            num1 = Double.parseDouble(display.getText().substring(6));
                            if (num1 < 1) {
                                display.setText("Error");
                            } else {
                                display.setText(String.valueOf(Math.log(num1 + Math.sqrt(num1 * num1 - 1))));
                            }
                        }else if (operator.equals("tanh⁻¹") && display.getText().startsWith("tanh⁻¹")) {
                            num1 = Double.parseDouble(display.getText().substring(6));
                            if (num1 <= -1 || num1 >= 1) {
                                display.setText("Error");
                            } else {
                                display.setText(String.valueOf(0.5 * Math.log((1 + num1) / (1 - num1))));
                            }
                        }else if (operator.equals("log") && expression.startsWith("log")) {
                            num1 = Double.parseDouble(expression.substring(3));
                            display.setText(String.valueOf(Math.log10(num1)));
                        }else if (operator.equals("ln") && expression.startsWith("ln")) {
                            num1 = Double.parseDouble(expression.substring(2));
                            display.setText(String.valueOf(Math.log(num1)));
                        }else if (operator.equals("10ˣ") && expression.startsWith("10^")) {
                            num1 = Double.parseDouble(expression.substring(3));
                            double powerValue = Math.pow(10, num1);
                            display.setText(formatScientificNotation(powerValue));
                        }else if(operator.equals("xʸ")) {
                            String[] parts = display.getText().split("\\^");
                            if (parts.length == 2) {
                                num1 = Double.parseDouble(parts[0]); // Base
                                num2 = Double.parseDouble(parts[1]); // Exponent
                                display.setText(String.valueOf(Math.pow(num1, num2))); // Calculate xʸ
                            } else {
                                display.setText("Error");
                            }
                        }else if (operator.equals("ʸ√x")) {
                            String[] parts = display.getText().split(" ʸ√ ");
                            if (parts.length == 2) {
                                num1 = Double.parseDouble(parts[0]); // Root value (y)
                                num2 = Double.parseDouble(parts[1]); // Base value (x)
                                display.setText(String.valueOf(Math.pow(num2, 1.0 / num1))); // Compute y-th root of x
                            } else {
                                display.setText("Error");
                            }
                        }else if (operator.equals("1/x") && expression.startsWith("1/")) {
                            num1 = Double.parseDouble(expression.substring(2)); // Extract number after "1/"
                            if (num1 == 0) {
                                display.setText("Error"); // Prevent division by zero
                            } else {
                                display.setText(String.valueOf(1 / num1));
                            }
                        } else {
                            String[] parts = expression.split(" ");
                            if (parts.length == 3) {
                                num1 = Double.parseDouble(parts[0]);
                                operator = parts[1];
                                num2 = Double.parseDouble(parts[2]);
                                display.setText(String.valueOf(calculateResult()));
                            } else {
                                display.setText(String.valueOf(Double.parseDouble(expression))); // Direct calculation
                            }
                        }
                    } catch (Exception ex) {
                        display.setText("Error");
                    }
                    operatorClicked = false;
                    break;

                case "+": case "-": case "x": case "÷": case "^": case "%":
                    if (!operatorClicked) {
                        display.setText(display.getText() + " " + command + " ");
                        operatorClicked = true;
                    }
                    break;
                case "²√x":
                    num1 = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(Math.sqrt(num1)));
                    break;
                case "log":
                    display.setText("log");
                    operator = "log";
                    operatorClicked = true;
                    break;
                case "ln":
                    display.setText("ln");
                    operator = "ln";
                    operatorClicked = true;
                    break;
                case "INV":
                    inverseMode = !inverseMode; // Toggle inverse mode
                    break;
                case "sin":
                    display.setText("sin");
                    operator = "sin";
                    display.setText(!inverseMode ? "sin⁻¹" : "sin");
                    operator = !inverseMode ? "sin⁻¹" : "sin";
                    operatorClicked = true;
                    break;
                case "cos":
                    display.setText("cos");
                    operator = "cos";
                    display.setText(!inverseMode ? "cos⁻¹" : "cos");
                    operator = !inverseMode ? "cos⁻¹" : "cos";
                    operatorClicked = true;
                    break;
                case "tan":
                    display.setText("tan");
                    operator = "tan";
                    display.setText(!inverseMode ? "tan⁻¹" : "tan");
                    operator = !inverseMode ? "tan⁻¹" : "tan";
                    operatorClicked = true;
                    break;
                case "cosh":
                    display.setText("cosh");
                    operator = "cosh";
                    display.setText(!inverseMode ? "cosh⁻¹" : "cosh");
                    operator = !inverseMode ? "cosh⁻¹" : "cosh";
                    operatorClicked = true;
                    break;
                case "sinh":
                    display.setText("sinh");
                    operator = "sinh";
                    display.setText(!inverseMode ? "sinh⁻¹" : "sinh");
                    operator = !inverseMode ? "sinh⁻¹" : "sinh";
                    operatorClicked = true;
                    break;
                case "tanh":
                    display.setText("tanh");
                    operator = "tanh";
                    display.setText(!inverseMode ? "tanh⁻¹" : "tanh");
                    operator = !inverseMode ? "tanh⁻¹" : "tanh";
                    operatorClicked = true;
                    break;
                case "π":
                    display.setText(display.getText() + Math.PI);
                    break;
                case "e":
                    display.setText(display.getText() + Math.E);
                    break;
                case "x!":
                    num1 = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(factorial((int) num1)));
                    break;
                case "DEL":
                    String text = display.getText();
                    if (!text.isEmpty()) {
                        display.setText(text.substring(0, text.length() - 1));
                    }
                    break;
                case "e^x":
                    display.setText("e^"); // Indicate that "e^" is active and waiting for input
                    operator = "e^x"; // Store the operation for when "=" is pressed
                    operatorClicked = true;
                    break;
                case "EE":
                    display.setText(display.getText() + " × 10^");
                    break;
                case "x²":
                    if (!display.getText().isEmpty()) {
                        num1 = Double.parseDouble(display.getText());
                        display.setText(String.valueOf(Math.pow(num1, 2))); // Compute x²
                    }
                    break;
                case "x³":
                    if (!display.getText().isEmpty()) {
                        num1 = Double.parseDouble(display.getText());
                        display.setText(String.valueOf(Math.pow(num1, 3))); // Compute x³
                    }
                    break;
                case "xʸ":
                    if (!display.getText().isEmpty()) {
                        num1 = Double.parseDouble(display.getText());
                        display.setText(display.getText() + "^"); // Indicate waiting for exponent
                        operator = "xʸ"; // Store operation
                        operatorClicked = true;
                    }
                    break;
                case "10ˣ":
                    display.setText("10^");
                    operator = "10ˣ";
                    operatorClicked = true;
                    break;
                case "³√x":
                    if (!display.getText().isEmpty()) {
                        num1 = Double.parseDouble(display.getText());
                        display.setText(String.valueOf(Math.cbrt(num1)));
                    }
                    break;
                case "1/x":
                    display.setText("1/");
                    operator = "1/x";
                    operatorClicked = true;
                    break;
                case "ʸ√x":
                    if (!display.getText().isEmpty()) {
                        num1 = Double.parseDouble(display.getText());
                        display.setText(display.getText() + " ʸ√ ");
                        operator = "ʸ√x";
                        operatorClicked = true;
                    }
                    break;
                case ".":
                    if (!display.getText().contains(".")) {
                        display.setText(display.getText() + ".");
                    }
                    break;
                case "+/-":
                    try {
                        if (!display.getText().isEmpty()) { // Ensure there's a number to toggle
                            if (display.getText().startsWith("-")) {
                                display.setText(display.getText().substring(1)); // Remove negative sign
                            } else {
                                display.setText("-" + display.getText()); // Add negative sign
                            }
                        }
                    } catch (Exception ex) {
                        display.setText("Error");
                    }
                    break;
                default:
                    display.setText(display.getText() + command);
                    operatorClicked = false;
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private double calculateResult() {
        switch (operator) {
            case "+": return new Addition().calculate(num1, num2);
            case "-": return new Subtraction().calculate(num1, num2);
            case "x": return new Multiplication().calculate(num1, num2);
            case "÷": return new Division().calculate(num1, num2);
            case "^": return Math.pow(num1, num2);
            case "%": return (num1/ 100)*num2;
            default: return 0;
        }
    }

    private int factorial(int n) {
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ScientificCalculator().setVisible(true);
        });
    }
}


