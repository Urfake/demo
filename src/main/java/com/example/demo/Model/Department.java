package com.example.demo.Model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Department {
    private StringProperty Dname;
    private StringProperty Dnumber;
    private StringProperty Mgr_Ssn;
    private ObjectProperty<Date> Mgr_start_date;

    public Department(
            String dname,
            String dnumber,
            String mgr_ssn,
            Date mgr_start_date
    ){
        Dname = new SimpleStringProperty(dname);
        Dnumber = new SimpleStringProperty(dnumber);
        Mgr_Ssn = new SimpleStringProperty(mgr_ssn);
        Mgr_start_date = new SimpleObjectProperty<Date>(mgr_start_date);
    }

    public String getDname() {
        return Dname.get();
    }

    public StringProperty dnameProperty() {
        return Dname;
    }

    public void setDname(String dname) {
        this.Dname.set(dname);
    }

    public String getDnumber() {
        return Dnumber.get();
    }

    public StringProperty dnumberProperty() {
        return Dnumber;
    }

    public void setDnumber(String dnumber) {
        this.Dnumber.set(dnumber);
    }

    public String getMgr_Ssn() {
        return Mgr_Ssn.get();
    }

    public StringProperty mgr_SsnProperty() {
        return Mgr_Ssn;
    }

    public void setMgr_Ssn(String mgr_Ssn) {
        this.Mgr_Ssn.set(mgr_Ssn);
    }

    public Date getMgr_start_date() {
        return Mgr_start_date.get();
    }

    public ObjectProperty<Date> mgr_start_dateProperty() {
        return Mgr_start_date;
    }

    public void setMgr_start_date(Date mgr_start_date) {
        this.Mgr_start_date.set(mgr_start_date);
    }
}
