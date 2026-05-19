package cs112.ud3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
/**
 * Completed GUI with integration of all classes (Transaction, Expense & Income) for UD3
 *
 * @author Rohith Mekala
 * @version 1.1
 */
public class HelloApplication extends Application {
    private double currentRunningBalance = 0.00;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Personal Finance Tracker");

        // 1. Title Header Region
        Label titleLabel = new Label("Personal Finance Tracker");

        // 2. Input Form Formations (Left Region)
        Label formTitle = new Label("Add New Transaction:");
        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("Description (e.g., Target)");

        TextField amountInput = new TextField();
        amountInput.setPromptText("Amount (e.g., 45.00)");

        ComboBox<String> categorySelector = new ComboBox<>();
        categorySelector.getItems().addAll("Food", "Rent", "Utilities", "Entertainment", "Salary");
        categorySelector.setValue("Food"); // Safe fallback default

        CheckBox taxDeductibleCheck = new CheckBox("Tax Deductible / Passive");
        Button submitButton = new Button("Submit Transaction");

        // Collect inputs into a single vertical column
        VBox leftFormContainer = new VBox();
        leftFormContainer.setPadding(new Insets(10));
        leftFormContainer.setSpacing(10);
        leftFormContainer.getChildren().addAll(formTitle, descriptionInput, amountInput, categorySelector, taxDeductibleCheck, submitButton);

        // 3. Ledger History View Formations (Center Region)
        Label ledgerTitle = new Label("Transaction History Stream:");
        ListView<String> transactionLogDisplay = new ListView<>();
        transactionLogDisplay.getItems().add("System initial log: No entries recorded yet.");

        Label netBalanceLabel = new Label("Current Account Net Balance: $0.00");

        // Collect ledger items into a single vertical column
        VBox centerLedgerContainer = new VBox();
        centerLedgerContainer.setPadding(new Insets(10));
        centerLedgerContainer.setSpacing(10);
        centerLedgerContainer.getChildren().addAll(ledgerTitle, transactionLogDisplay, netBalanceLabel);

        categorySelector.setOnAction(event -> {
            System.out.println("Console Alert: Category dropdown changed to: " + categorySelector.getValue());
        });

        //INTEGRATE THE UD1 EXPENSE & INCOME CLASSES VIA BUTTON CLICK
        submitButton.setOnAction(event -> {
            System.out.println("\nHello! Submit button clicked.");

            //Pull the text values directly from UI input boxes
            String rawDescription = descriptionInput.getText();
            String rawAmountText = amountInput.getText();
            String chosenCategory = categorySelector.getValue();
            boolean checkState = taxDeductibleCheck.isSelected();

            // Guard rails to make sure user didn't leave inputs blank
            if (rawDescription.trim().isEmpty() || rawAmountText.trim().isEmpty()) {
                return;
            }

            try {
                double parsedAmount = Double.parseDouble(rawAmountText);

                // Wipe out the initial system placeholder text message before inserting real data rows
                if (!transactionLogDisplay.getItems().isEmpty() &&
                        transactionLogDisplay.getItems().get(0).equals("System initial log: No entries recorded yet.")) {
                    transactionLogDisplay.getItems().clear();
                }

                Transaction genericTransaction;

                // Branch logic path based on user dropdown choices
                if (chosenCategory.equalsIgnoreCase("Salary")) {
                    // Instantiate the new Income model object class (checkState tracks if it's Passive)
                    genericTransaction = new Income(rawDescription, parsedAmount, chosenCategory, checkState);
                    currentRunningBalance += parsedAmount;
                    System.out.println("SUCCESSFULLY INSTANTIATED INCOME OBJECT MODULE:");
                } else {
                    // Instantiate the Expense model object class (checkState tracks if it's Tax Deductible)
                    genericTransaction = new Expense(rawDescription, parsedAmount, chosenCategory, checkState);
                    currentRunningBalance -= parsedAmount;
                    System.out.println("SUCCESSFULLY INSTANTIATED EXPENSE OBJECT MODULE:");
                }

                // Verify structural data state inside the IDE compilation console log streams
                System.out.println(genericTransaction);

                // Dynamically feed the item's customized toString format right onto the screen display list
                transactionLogDisplay.getItems().add(genericTransaction.toString());

                // Refresh our live account tracker output string metrics
                netBalanceLabel.setText(String.format("Current Account Net Balance: $%.2f", currentRunningBalance));

                // Clear out input fields so they are clean for the next entry
                descriptionInput.clear();
                amountInput.clear();
                taxDeductibleCheck.setSelected(false);

            } catch (NumberFormatException nfe) {
                System.out.println("Console Error: Unable to parse amount field string into numerical double format.");
            }
        });

        //ASSEMBLE WINDOW ARCHITECTURE (BORDERPANE)
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(titleLabel);
        mainLayout.setLeft(leftFormContainer);
        mainLayout.setCenter(centerLedgerContainer);
        BorderPane.setMargin(titleLabel, new Insets(10));

        //Display the window scene
        Scene primaryWindowScene = new Scene(mainLayout, 650, 350);
        primaryStage.setScene(primaryWindowScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}