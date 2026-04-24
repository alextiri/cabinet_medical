package org.example.model.dto;

import org.example.model.Sex;

public class PacientFormData {
    public final String nume;
    public final String prenume;
    public final Sex sex;
    public final String inaltime;
    public final String greutate;
    public final String cnp;
    public final String dataNasterii;
    public final String telefon;
    public final String adresa;

    public PacientFormData(String nume, String prenume, Sex sex,
                           String inaltime, String greutate, String cnp,
                           String dataNasterii, String telefon, String adresa) {
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
}