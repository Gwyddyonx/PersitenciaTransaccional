package com.mycompany.persitenciatransaccional;

import java.util.Date;

public class Empleado {
    private int ID;
    private String primerNombre;
    private String segundoNombre;
    private String email;
    private Date fechaNacimiento;
    private double sueldo;
    private int comision;
    private int cargoID;
    private int gerenteID;
    private int dptoID;

    public Empleado(int ID, String primerNombre, String segundoNombre, String email, Date fechaNacimiento,
            double sueldo, int comision, int cargoID, int gerenteID, int dptoID) {
        this.ID = ID;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.sueldo = sueldo;
        this.comision = comision;
        this.cargoID = cargoID;
        this.gerenteID = gerenteID;
        this.dptoID = dptoID;
    }

    public Empleado() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public int getComision() {
        return comision;
    }

    public void setComision(int comision) {
        this.comision = comision;
    }

    public int getCargoID() {
        return cargoID;
    }

    public void setCargoID(int cargoID) {
        this.cargoID = cargoID;
    }

    public int getGerenteID() {
        return gerenteID;
    }

    public void setGerenteID(int gerenteID) {
        this.gerenteID = gerenteID;
    }

    public int getDptoID() {
        return dptoID;
    }

    public void setDptoID(int dptoID) {
        this.dptoID = dptoID;
    }

}