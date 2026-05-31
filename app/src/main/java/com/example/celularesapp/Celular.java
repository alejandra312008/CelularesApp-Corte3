package com.example.celularesapp;

public class Celular {

    private String marca;
    private String email;
    private String pulgadas;
    private String ram;

    public Celular(String marca, String email, String pulgadas, String ram) {
        this.marca = marca;
        this.email = email;
        this.pulgadas = pulgadas;
        this.ram = ram;
    }

    public String getMarca() {
        return marca;
    }

    public String getEmail() {
        return email;
    }

    public String getPulgadas() {
        return pulgadas;
    }

    public String getRam() {
        return ram;
    }
}