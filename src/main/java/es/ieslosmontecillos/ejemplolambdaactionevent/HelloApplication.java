package es.ieslosmontecillos.ejemplolambdaactionevent;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    private double interestRate;
    private int nYears;
    private double loan;

    @Override
    public void start(Stage stage)
    {
        GridPane root = new GridPane();

        //Labels para los botones
        String[] inputs =
                {
                        "Annual Interest Rate: ",
                        "Number of Years: ",
                        "Loan Amount: ",
                        "Monthly Payment: ",
                        "Total payment: "
                };

        //Textfields del programa
        TextField tfInterestRate = new TextField();
        TextField tfNYears = new TextField();
        TextField tfLoan = new TextField();
        TextField tfMonthlyPayment = new TextField();
        TextField tfTotalPayment = new TextField();

        //Desactivamos los textfields de salida
        tfMonthlyPayment.setDisable(true);
        tfTotalPayment.setDisable(true);

        //Texto de error en rojo
        Text errorText = new Text();
        errorText.setFill(Color.RED);

        //Creamos el boton para interactuar con el usuario y lo configuramos
        Button calculateButton = new Button("Calculate");

        calculateButton.setOnAction(_ -> {

            //Comprobamos si los datos que nos han introducido son validos
            if (validateInput(tfInterestRate, tfNYears, tfLoan))
            {
                errorText.setText("");
                calculate(tfMonthlyPayment, tfTotalPayment);
            }
            else
            {
                errorText.setText("Invalid input data");
            }
        });

        //Configuramos el pane
        root.setAlignment(Pos.CENTER);
        root.setHgap(5.5);
        root.setVgap(5.5);

        //Añadimos las labels
        for (int i = 0; i < inputs.length; i++)
        {
            root.add(new Label(inputs[i]), 0, i);
        }

        //Añadimos los textfields
        root.add(tfInterestRate, 1, 0);
        root.add(tfNYears, 1, 1);
        root.add(tfLoan, 1, 2);
        root.add(tfMonthlyPayment, 1, 3);
        root.add(tfTotalPayment, 1, 4);

        //Añadimos el boton
        root.add(calculateButton, 1, 5);
        GridPane.setHalignment(calculateButton, HPos.RIGHT);

        //Añadimos el texto de error
        root.add(errorText, 0, 5);

        //Mostramos la escena
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Loan Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo que comprueba si los datos que nos ha introducido el usuario son validos
     * @param tfInterestRate textfield con el ratio de interes
     * @param tfNYears textfield con los numeros de años
     * @param tfLoan textfield con la cantidad del prestamo
     * @return True: Los datos son validos, False: hay algun dato que no es valido
     */
    private boolean validateInput(TextField tfInterestRate,
                               TextField tfNYears,
                               TextField tfLoan)
    {
        if (!tfInterestRate.getText().trim().matches("^[0-9]+$")) return false;
        if (!tfNYears.getText().trim().matches("^[0-9]+$")) return false;
        if (!tfLoan.getText().trim().matches("^[0-9]+$")) return false;

        try
        {
            interestRate = Double.parseDouble(tfInterestRate.getText().trim());
            nYears = Integer.parseInt(tfNYears.getText().trim());
            loan = Double.parseDouble(tfLoan.getText().trim());

        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    /**
     * Metodo que calcula el pago total y mensual segun los datos introducidos
     * @param tfMonthlyPayment textfield de salida donde se mostrara el pago mensual
     * @param tfTotalPayment textfield de salida donde se mostrara el pago total
     */
    private void calculate(TextField tfMonthlyPayment, TextField tfTotalPayment)
    {
        double r = interestRate / 100 * 12;
        double m = loan * r / (1 - Math.pow(1 + r, -12 * nYears));

        tfMonthlyPayment.setText(String.format("%.2f €", m));
        tfTotalPayment.setText(String.format("%.2f €", m * 12 * nYears));
    }

    public static void main(String[] args) {
        launch();
    }
}