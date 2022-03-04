package com.example.demo.Controller;

import com.example.demo.Connection.ConnectionClass;
import com.example.demo.Model.Department;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.DepartmentRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DepartmentController {
    final DepartmentRepository departmentRepository = new DepartmentRepository();

    @FXML
    public TableView<Department> departmentTable;
    @FXML
    public TableColumn<Department, String> dnameColumn;
    @FXML
    public TableColumn<Department, String> dnumberColumn;
    @FXML
    public TableColumn<Department, String> mgr_ssnColumn;

    @FXML
    public TableColumn<Department, Date> mgr_start_dateColumn;

    @FXML
    public TableColumn<Department, Department> actionColumn;


    @FXML
    public TextField Dname;
    @FXML
    public TextField Dnumber;
    @FXML
    public TextField Mgr_Ssn;
    @FXML
    public DatePicker Mgr_start_date;

    @FXML
    private void initialize() {
        dnameColumn.setCellValueFactory(cellData -> cellData.getValue().dnameProperty());
        dnumberColumn.setCellValueFactory(cellData -> cellData.getValue().dnumberProperty());
        mgr_ssnColumn.setCellValueFactory(cellData -> cellData.getValue().mgr_SsnProperty());
        mgr_start_dateColumn.setCellValueFactory(cellData -> cellData.getValue().mgr_start_dateProperty());
        actionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));

        initializeTableValues();

        actionColumn.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Department department, boolean empty) {
                super.updateItem(department, empty);

                if (department == null) {
                    setGraphic(null);
                    return;
                }


                setGraphic(deleteButton);

                deleteButton.setOnAction(event -> removeDepartment(department.getDname()));
            }
        });
    }

    public void removeDepartment(String Dname) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM department WHERE dname = '" + Dname + "'";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeTableValues();
    }

    /**
     * Следующие строки предназначены для создания объекта класса -ConnectionClass-
     * и вызова его метода -getConnection()-
     * Переменная -connection- далее используется для отправки запросов на базу данных
     */
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public ObservableList<Department> departmentList = FXCollections.observableArrayList();

    /**
     * Метод -insertPerson(ActionEvent actionEvent)- привязан к кнопке -Создать- на главной странице
     * при нажатии которой из текста полей -Fname, Lname, Ssn...- формируется
     * запрос добавления(INSERT) в базу данных.
     * <p>
     * Далее значения полей опустошаются и вызывается метод
     * -initializeTableValues();- для заполнения таблицы новыми данными из Базы данных.
     *
     * @param actionEvent
     */
    public void insertDepartment(ActionEvent actionEvent) {
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " +
                    "department(Dname, Dnumber, Mgr_Ssn, Mgr_start_date) " +
                    "VALUES ('" + Dname.getText() + "','" +
                    "" + Dnumber.getText() + "','" +
                    "" + Mgr_Ssn.getText() + "','" +
                    "" + Mgr_start_date.getValue() + "',)";
            statement.executeUpdate(sql);

            System.out.println("Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Dname.setText("");
        Dnumber.setText("");
        Mgr_Ssn.setText("");

        initializeTableValues();
    }



        /**
         * Данный метод делает запрос -SELECT- в базу данных и из полученных данных формирует список
         * типа -ObservableList<Employee>-, с помощью которого заполняет таблицу -personTable-
         */
        public void initializeTableValues(){
            ObservableList<Department> departmentsList = departmentRepository.getList();
            if(departmentsList.size() > 0){
                departmentTable.setItems(departmentList);
            }
        }
}
