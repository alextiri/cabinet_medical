package org.example.model;

import java.time.LocalDate;

public class Pacient {
    private int id;
    private String nume;
    private String prenume;
    private Sex sex;
    private double inaltime;
    private double greutate;
    private String cnp;
    private LocalDate dataNasterii;
    private String telefon;
    private String adresa;

    public Pacient() {}

    public Pacient(int id, String nume, String prenume, Sex sex,
                   double inaltime, double greutate, String cnp,
                   LocalDate dataNasterii, String telefon, String adresa) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.sex = sex;
        this.inaltime = inaltime;
        this.greutate = greutate;
        this.cnp = cnp;
        this.dataNasterii = dataNasterii;
        this.telefon = telefon;
        this.adresa = adresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public double getInaltime() {
        return inaltime;
    }

    public void setInaltime(double inaltime) {
        this.inaltime = inaltime;
    }

    public double getGreutate() {
        return greutate;
    }

    public void setGreutate(double greutate) {
        this.greutate = greutate;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        if (cnp == null || cnp.length() != 13) {
            throw new IllegalArgumentException("CNP invalid");
        }
        this.cnp = cnp;
    }

    public LocalDate getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(LocalDate dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return nume + " " + prenume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pacient)) return false;
        Pacient pacient = (Pacient) o;
        return id != 0 && id == pacient.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}