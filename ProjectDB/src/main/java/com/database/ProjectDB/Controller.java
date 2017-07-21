package com.database.ProjectDB;

import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.Status;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import com.database.ProjectDB.ui.components.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lombok.Getter;

import java.net.URL;
import java.text.ParseException;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * Created by seba on 2017-05-08.
 */
@Getter
public class Controller implements Initializable {

    private static Controller controllerInstance = null;

    private Controller() {};

    public static Controller getControllerInstance() {
        if (controllerInstance == null) {
            controllerInstance = new Controller();
        }
        return controllerInstance;
    }

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label employeeLastNameLabel;
    @FXML
    private Label employeeFirstNameLabel;
    @FXML
    private TextField employeeLastNameInput;
    @FXML
    private TextField employeeEmailInput;
    @FXML
    private Menu helpButton;
    @FXML
    private TextField orderClientIDInput;
    @FXML
    private Label repairDefectLabel;
    @FXML
    private TextField addressPinCodeInput;
    @FXML
    private Label employeePhoneNumberLabel;
    @FXML
    private ToggleGroup orderGroup;
    @FXML
    private DatePicker orderDateOfReturn;
    @FXML
    private TextField repairDefectInput;
    @FXML
    private Label addressEmployeeIDLabel;
    @FXML
    private Label orderIdLabel;
    @FXML
    private TextField orderIdInput;
    @FXML
    private Label addressClientIDLabel;
    @FXML
    private RadioButton selectRepairButton;
    @FXML
    private RadioButton insertRepairButton;
    @FXML
    private RadioButton insertAddressButton;
    @FXML
    private TextField repairPriceInput;
    @FXML
    private ToggleGroup clientGroup;
    @FXML
    private TableView addressTable;
    @FXML
    private TextField addressClientIDInput;
    @FXML
    private RadioButton updateAddressButton;
    @FXML
    private TextField addressEmployeeIDInput;
    @FXML
    private Label orderRepairIDLabel;
    @FXML
    private ToggleGroup addressGroup;
    @FXML
    private RadioButton selectOrderButton;
    @FXML
    private Label addressStreetLabel;
    @FXML
    private Tab repairTab;
    @FXML
    private TextField repairOrderIDInput;
    @FXML
    private Tab clientTab;
    @FXML
    private TextField clientAddressIDInput;
    @FXML
    private RadioButton insertOrderButton;
    @FXML
    private Label employeeAddressLabel;
    @FXML
    private TableView orderTable;
    @FXML
    private Label repairOrderIDLabel;
    @FXML
    private RadioButton deleteOrderButton;
    @FXML
    private TextField clientFirstNameInput;
    @FXML
    private Label orderStatusLabel;
    @FXML
    private TextField clientPhoneNumberInput;
    @FXML
    private Menu editButton;
    @FXML
    private Label addressIDLabel;
    @FXML
    private Tab employeeTab;
    @FXML
    private TextField repairIDInput;
    @FXML
    private Label addressCityLabel;
    @FXML
    private RadioButton updateClientButton;
    @FXML
    private TextField orderPriceInput;
    @FXML
    private Label dateOfReturn;
    @FXML
    private ToggleButton connectButton;
    @FXML
    private Label clientIDLabel;
    @FXML
    private TextField clientLastNameInput;
    @FXML
    private TableView repairTable;
    @FXML
    private RadioButton deleteAddressButton;
    @FXML
    private RadioButton deleteEmployeeButton;
    @FXML
    private TextField addressCityInput;
    @FXML
    private CheckBox orderDoneInput;
    @FXML
    private TextArea messageConsole;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField employeeIdInput;
    @FXML
    private RadioButton selectAddressButton;
    @FXML
    private Menu fileButton;
    @FXML
    private RadioButton selectEmployeeButton;
    @FXML
    private Label employeeIdLabel;
    @FXML
    private TableView employeeTable;
    @FXML
    private ToggleGroup employeeGroup;
    @FXML
    private RadioButton insertClientButton;
    @FXML
    private Label employeePeselLabel;
    @FXML
    private Label clientLastNameLabel;
    @FXML
    private TextField employeeAddresssIDInput;
    @FXML
    private TextField addressIDInput;
    @FXML
    private TextField employeeSalaryInput;
    @FXML
    private Label clientAddressIDLabel;
    @FXML
    private TableView clientTable;
    @FXML
    private CheckBox orderProgressInput;
    @FXML
    private RadioButton updateEmployeeButton;
    @FXML
    private RadioButton selectClientButton;
    @FXML
    private Label employeeSalaryLabel;
    @FXML
    private TextField addressStreetInput;
    @FXML
    private Label addressHomeNumberLabel;
    @FXML
    private Label clientFirstNameLabel;
    @FXML
    private RadioButton updateOrderButton;
    @FXML
    private Label repairIDLabel;
    @FXML
    private ToggleGroup repairGroup;
    @FXML
    private DatePicker orderDateOfReceipt;
    @FXML
    private TextField addressHomeNumberInput;
    @FXML
    private RadioButton updateRepairButton;
    @FXML
    private Label clientPhoneNumberLabel;
    @FXML
    private RadioButton deleteRepairButton;
    @FXML
    private Label orderEmployeeLabel;
    @FXML
    private Label orderClientLabel;
    @FXML
    private RadioButton deleteClientButton;
    @FXML
    private Tab addressTab;
    @FXML
    private TextField orderRepairIDInput;
    @FXML
    private TextField employeePeselInput;
    @FXML
    private RadioButton insertEmployeeButton;
    @FXML
    private Label dateOfReceiptLabel;
    @FXML
    private Tab orderTab;
    @FXML
    private Label repairPriceLabel;
    @FXML
    private TextField clientIDInput;
    @FXML
    private TextField orderEmployeeIDInput;
    @FXML
    private TextField employeeFirstNameInput;
    @FXML
    private TextField employeePhoneNumberInput;
    @FXML
    private Label addressPinCodeLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Pane orderPane;
    @FXML
    private Pane repairPane;
    @FXML
    private Pane employeePane;
    @FXML
    private Pane clientPane;
    @FXML
    private Pane addressPane;
    @FXML
    private TextField orderPhoneModelInput;
    @FXML
    private Pane orderRadioPane;
    @FXML
    private Pane employeeRadioPane;
    @FXML
    private Pane repairRadioPane;
    @FXML
    private Pane clientRadioPane;
    @FXML
    private Pane addressRadioPane;
    @FXML
    private MenuItem menuCloseButton;
    @FXML
    private MenuItem menuClearButton;
    @FXML
    private MenuItem menuAboutButton;
    @FXML
    private Circle connectDiode;
    @FXML
    private Circle disconnectDiode;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem menuOrderContextDelete;
    @FXML
    private MenuItem menuRepairContextDelete;
    @FXML
    private MenuItem menuEmployeeContextDelete;
    @FXML
    private MenuItem menuContextClientDelete;
    @FXML
    private MenuItem menuContextAddressDelete;
    @FXML
    private Tab functionTab;
    @FXML
    private TextField functionFirstEmployeeIDInput;
    @FXML
    private TextField functionFirstYearInput;
    @FXML
    private TextField functionFirstResult;
    @FXML
    private TextField functionSecondResult;
    @FXML
    private TextField functionThirdResult;
    @FXML
    private Button functionFirstSubmit;
    @FXML
    private Button functionSecondSubmit;
    @FXML
    private Button functionThirdSubmit;
    @FXML
    private ComboBox<Status> functionSecondBoxStatus;
    @FXML
    private ComboBox<Status> functionThirdBoxStatus;
    @FXML
    private ComboBox<Month> functionThirdBoxMonth;
    @FXML
    private MenuItem menuUserGuide;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FunctionComponent.injectDateToBoxes();
        FunctionComponent.managerFunctions();
        functionTab.setOnSelectionChanged(event -> {
            if (submitButton.isVisible()) submitButton.setVisible(false);
            else submitButton.setVisible(true);
        });
        menuUserGuide.setOnAction(event -> {
            WindowComponents windowComponents = new WindowComponents();
            windowComponents.openUserGuide();
        });
        menuOrderContextDelete.setOnAction(event -> OrderComponent.deleteSelected());
        menuRepairContextDelete.setOnAction(event -> RepairComponent.deleteSelected());
        menuEmployeeContextDelete.setOnAction(event -> EmployeeComponent.deleteSelected());
        menuContextClientDelete.setOnAction(event -> ClientComponent.deleteSelected());
        menuContextAddressDelete.setOnAction(event -> AddressComponent.deleteSelected());
        messageConsole.setOnInputMethodTextChanged(event -> messageConsole.setScrollTop(Double.MIN_VALUE));
        orderRadioPane.setOnMouseMoved(event -> OrderComponent.orderPaneTools());
        repairRadioPane.setOnMouseMoved(event -> RepairComponent.repairPaneTools());
        employeeRadioPane.setOnMouseMoved(event -> EmployeeComponent.employeePaneTools());
        clientRadioPane.setOnMouseMoved(event -> ClientComponent.clientPaneTools());
        addressRadioPane.setOnMouseMoved(event -> AddressComponent.addressPaneTools());

        menuAboutButton.setOnAction(event -> WindowComponents.createAlert(AlertType.INFORMATION,
                "The Phone Service.\n" +
                        "Created by: Sebastian Powroźnik" +
                        "\nVersion: ProjectDB v2"));
        menuClearButton.setOnAction(event -> WindowComponents.clearInputs());
        connectButton.setOnAction(event -> {
            if (connectButton.isSelected()) {
                ServiceDAO service = ServiceDAO.getInstance();
                service.connectToDatabase();
//                InjectData.injectData();
                try {
                    InjectData.injectData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (!connectButton.isSelected()) {
                ServiceDAO service = ServiceDAO.getInstance();
                service.closeConnection();
            }
        });
        menuCloseButton.setOnAction(event -> {
            ServiceDAO service = ServiceDAO.getInstance();
            if (service.isConnected) service.closeConnection();
            System.exit(1);
        });
        submitButton.setOnAction(event -> {
            if (!ServiceDAO.isConnected) {
                WindowComponents.createAlert(AlertType.WARNING, "Database is disconnected!");
                WindowComponents.sendConsoleMessage("Database is disconnected!");
            }
            OrderComponent.submitButtonTools();
            EmployeeComponent.sumbitButtonTools();
            RepairComponent.submitButtonTools();
            ClientComponent.sumbitButtonTools();
            AddressComponent.submitButtonTools();
        });
        menuClearButton.setOnAction(event -> WindowComponents.clearInputs());
        orderTable.setOnMouseClicked(event -> OrderComponent.injectDataToComponents());
        employeeTable.setOnMouseClicked(event -> EmployeeComponent.injectDataToComponents());
        repairTable.setOnMouseClicked(event -> RepairComponent.injectDataToComponents());
        clientTable.setOnMouseClicked(event -> ClientComponent.injectDataToComponents());
        addressTable.setOnMouseClicked(event -> AddressComponent.injectDataToComponents());
    }
}

