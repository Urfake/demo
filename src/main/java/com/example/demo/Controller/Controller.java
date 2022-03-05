package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Main;
import com.example.demo.Model.Department;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Controller {
    final EmployeeRepository repository = new EmployeeRepository();

    @FXML
    public TableView<Employee> employeeTable;
    @FXML
    public TableColumn<Employee, String> fNameColumn;
    @FXML
    public TableColumn<Employee, String> lNameColumn;
    @FXML
    public TableColumn<Employee, String> ssnColumn;
    @FXML
    public TableColumn<Employee, Date> bdateColumn;
    @FXML
    public TableColumn<Employee, String> addressColumn;
    @FXML
    public TableColumn<Employee, String> sexColumn;
    @FXML
    public TableColumn<Employee, Employee> actionColumn;


    @FXML
    public TextField Fname;
    @FXML
    public TextField Lname;
    @FXML
    public TextField Ssn;
    @FXML
    public DatePicker Bdate;
    @FXML
    public TextField Address;
    @FXML
    public TextField Sex;
    @FXML
    public TextField Salary;
    @FXML
    public ComboBox<Department> Dnumber;
    @FXML
    private void initialize(){
        fNameColumn.setCellValueFactory(cellData -> cellData.getValue().fnameProperty());
        lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lnameProperty());
        ssnColumn.setCellValueFactory(cellData -> cellData.getValue().ssnProperty());
        bdateColumn.setCellValueFactory(cellData -> cellData.getValue().bdateProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
        actionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));

        initializeTableValues();

        actionColumn.setCellFactory(param -> new TableCell<Employee, Employee>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);

                if (employee == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);

                deleteButton.setOnAction(event -> removePerson(employee.getSsn()));
            }
        });
    }

    public void removePerson(String ssn) {
        try {
            Statement statement=connection.createStatement();
            String sql = "DELETE FROM employee WHERE ssn = '" + ssn + "'";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeTableValues();
    }
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    public void insertPerson(ActionEvent actionEvent) {
        try {
            Statement statement=connection.createStatement();
            String sql = "INSERT INTO " +
                    "employee(Fname, Lname, Ssn, Bdate, Address, Sex, Salary, Dnumber) " +
                    "VALUES ('"+Fname.getText()+"','" +
                    ""+Lname.getText()+"','" +
                    ""+Ssn.getText()+"','" +
                    ""+Bdate.getValue()+"','" +
                    ""+Address.getText()+"','" +
                    ""+Sex.getText()+"','" +
                    ""+Salary.getText()+"', '1')";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Fname.setText("");
        Lname.setText("");
        Ssn.setText("");
        Address.setText("");
        Sex.setText("");
        Salary.setText("");

        initializeTableValues();
    }

    public void changePerson(String ssn){
        try {
            Statement statement=connection.createStatement();
            String sql = "DELETE FROM employee WHERE ssn = '" + ssn + "'";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeTableValues();
    }

    public void updatePerson(ActionEvent actionEvent) {
        repository.updateEmployee(
                Fname.getText(),
                Lname.getText(),
                Ssn.getText(),
                String.valueOf(Bdate.getValue()),
                Address.getText(),
                Sex.getText(),
                Salary.getText(),
                Dnumber.getValue().getDnumber());
        openEmployeePage(actionEvent);
    }



    /**
     * Данный метод делает запрос -SELECT- в базу данных и из полученных данных формирует список
     * типа -ObservableList<Employee>-, с помощью которого заполняет таблицу -personTable-
     */
    public void initializeTableValues(){
        ObservableList<Employee> personList = repository.getList();
        if(personList.size() > 0){
            employeeTable.setItems(personList);
        }
    }
    public void openEmployeePage(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("employeeTable.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 450);
            stage.setTitle("База сотрудников");
            stage.setScene(scene);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

