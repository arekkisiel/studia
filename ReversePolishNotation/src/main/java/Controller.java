import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.Stack;

public class Controller {
    HashMap<String, Boolean> isNumber = new HashMap<>();

    @FXML TextFlow OperationalResults;

    @FXML TextArea Input;

    @FXML
    public void calculate(ActionEvent actionEvent) throws InterruptedException {
        defineHashMap();
        OperationalResults.getChildren().clear();
        String expression = Input.getText();
        Stack<Double> stack = new Stack<>();
        String[] objects = expression.split(" ");
        for(String object : objects) {
            OperationalResults.getChildren().add(new Text(object));
            if (isNumber.getOrDefault(object, true))
                stack.push(Double.valueOf(object));
            else {
                if (object.contains("*")) {
                    double secondValue = stack.pop();
                    double firstValue = stack.pop();
                    stack.push(firstValue * secondValue);
                }
                if (object.contains("/")) {
                    double secondValue = stack.pop();
                    double firstValue = stack.pop();
                    stack.push(firstValue / secondValue);
                }
                if (object.contains("+")) {
                    double secondValue = stack.pop();
                    double firstValue = stack.pop();
                    stack.push(firstValue + secondValue);
                }
                if (object.contains("-")) {
                    double secondValue = stack.pop();
                    double firstValue = stack.pop();
                    stack.push(firstValue - secondValue);
                }
                if (object.contains("^")) {
                    double secondValue = stack.pop();
                    double firstValue = stack.pop();
                    stack.push(Math.pow(firstValue, secondValue));
                }
            }
        }
        OperationalResults.getChildren().add(new Text("\nResult: " + stack.pop().toString()));
    }

    private void defineHashMap() {
        isNumber.put("+", false);
        isNumber.put("-", false);
        isNumber.put("*", false);
        isNumber.put("/", false);
        isNumber.put("^", false);
    }
}
