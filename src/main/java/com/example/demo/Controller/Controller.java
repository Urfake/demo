package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Controller {

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
    private void initialize(){
        fNameColumn.setCellValueFactory(cellData -> cellData.getValue().fnameProperty());
        lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lnameProperty());
        ssnColumn.setCellValueFactory(cellData -> cellData.getValue().ssnProperty());
        bdateColumn.setCellValueFactory(cellData -> cellData.getValue().bdateProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());

        initializeTableValues();
    }

    /**
     * Следующие строки предназначены для создания объекта класса -ConnectionClass-
     * и вызова его метода -getConnection()-
     * Переменная -connection- далее используется для отправки запросов на базу данных
     */
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    /**
     * Метод -insertPerson(ActionEvent actionEvent)- привязан к кнопке -Создать- на главной странице
     * при нажатии которой из текста полей -nameField, surnameField, phoneNumberField- формируется
     * запрос добавления(INSERT) в базу данных.
     *
     * Далее значения полей -nameField, surnameField, phoneNumberField- опустошаются и вызывается метод
     * -initializeTableValues();- для заполнения таблицы новыми данными из Базы данных.
     *
     * @param actionEvent
     */
    public void insertPerson(ActionEvent actionEvent) {
        try {
            Statement statement=connection.createStatement();
            //String sql="INSERT INTO person VALUES ('"+Fname.getText() + "', '" + Lname.getText() + "', '" + phoneNumberField.getText() + "');";

            String sql = "INSERT INTO employee(Fname, Lname, Ssn, Bdate, Address, Sex, Salary, Dnumber) VALUES ('"+Fname.getText()+"','"+Lname.getText()+"','"+Ssn.getText()+"','"+Bdate.getValue()+"','"+Address.getText()+"','"+Sex.getText()+"','"+Salary.getText()+"', '1')";
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


    /**
     * Данный метод делает запрос -SELECT- в базу данных и из полученных данных формирует список
     * типа -ObservableList<Person>-, с помощью которого заполняет таблицу -personTable-
     */
    public void initializeTableValues(){
        try {
            Statement statement=connection.createStatement();

            String sql="SELECT * FROM employee;";

            ResultSet resultSet=statement.executeQuery(sql);

            ObservableList<Employee> personList = FXCollections.observableArrayList();

            if (resultSet.next()){
                while (resultSet.next()) {
                    Employee employee = new Employee(
                            resultSet.getString("Fname"),
                            resultSet.getString("Lname"),
                            resultSet.getString("Ssn"),
                            resultSet.getDate("Bdate"),
                            resultSet.getString("Address"),
                            resultSet.getString("Sex"),
                            resultSet.getDouble("Salary"),
                            resultSet.getString("Super_ssn"),
                            resultSet.getInt("Dnumber")
                    );
                    personList.add(employee);
                }
                employeeTable.setItems(personList);
            }else {
                System.out.println("no data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

