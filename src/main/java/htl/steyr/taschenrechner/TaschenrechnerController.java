package htl.steyr.taschenrechner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TaschenrechnerController implements Initializable {

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    @FXML
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnEqual, btnC, btnCE, btn2nd;
    @FXML
    private Label lblDisplay;
    @FXML
    private Pane pane;
    @FXML
    private GridPane grid, grid2;

    private boolean newNumber = true;
    private String operator = "";
    private String currentText = "";
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDisplay.setText("0");
        activateFirstMenu();
        disableSecondMenu();
    }

    private void showErrorAlert(String message) {
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        Button source = (Button) event.getSource();
        String buttonText = source.getText();

        if ("0123456789".contains(buttonText)) {
            handleNumberInput(buttonText);
        } else if ("+-*/".contains(buttonText)) {
            handleOperatorInput(buttonText);
        } else if ("C".equals(buttonText)) {
            clearAll();
        } else if ("CE".equals(buttonText)) {
            clearEntry();
        }
    }

    private void handleNumberInput(String number) {
        if (newNumber) {
            currentText = number;
            newNumber = false;
        } else {
            currentText += number;
        }
        lblDisplay.setText(currentText);
    }

    private void handleOperatorInput(String op) {
        if (!currentText.isEmpty() && operator.isEmpty()) {
            operator = op;
            currentText += operator;
            newNumber = false;
        } else {
            showErrorAlert("Ungültiger Operator!");
        }
        lblDisplay.setText(currentText);
    }

    @FXML
    public void calculateResult(ActionEvent event) {
        if (operator.isEmpty()) {
            showErrorAlert("Kein Operator vorhanden!");
            return;
        }

        try {
            double firstNumber = getFirstNumber();
            double secondNumber = getSecondNumber();

            double result = switch (operator) {
                case "+" -> firstNumber + secondNumber;
                case "-" -> firstNumber - secondNumber;
                case "*" -> firstNumber * secondNumber;
                case "/" -> (secondNumber != 0) ? firstNumber / secondNumber : Double.NaN;
                default -> 0;
            };

            lblDisplay.setText(String.valueOf(result));
            currentText = String.valueOf(result);
            operator = "";
            newNumber = true;

        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            showErrorAlert("Ungültige Eingabe!");
        }
    }

    private double getFirstNumber() throws NumberFormatException {
        int operatorIndex = currentText.indexOf(operator);
        String firstPart = currentText.substring(0, operatorIndex);
        return Double.parseDouble(firstPart);
    }

    private double getSecondNumber() throws NumberFormatException {
        int operatorIndex = currentText.indexOf(operator);
        String secondPart = currentText.substring(operatorIndex + 1);
        return Double.parseDouble(secondPart);
    }

    private void clearAll() {
        currentText = "";
        operator = "";
        newNumber = true;
        lblDisplay.setText("");
    }

    private void clearEntry() {
        currentText = "";
        newNumber = true;
        lblDisplay.setText("");
    }

    public void toggleMenu(ActionEvent actionEvent) {

        if (!grid.isDisable()){
            disableFirstMenu();
            activateSecondMenu();

        } else if (!grid2.isDisable()){
            disableSecondMenu();
            activateFirstMenu();
        }


    }


    private void activateFirstMenu() {
        grid.setVisible(true);
        grid.setDisable(false);
        btn0.setDisable(false);
        btn0.setVisible(true);
    }


    private void disableFirstMenu() {
        grid.setDisable(true);
        grid.setVisible(false);
        btn0.setDisable(true);
        btn0.setVisible(false);
    }

    private void activateSecondMenu() {
        grid2.setDisable(false);
        grid2.setVisible(true);
    }

    private void disableSecondMenu() {
        grid2.setDisable(true);
        grid2.setVisible(false);
    }

}
