package org.example.model;

import org.example.model.dto.ConsultatieFormData;

import java.time.LocalDate;

public class Consultatie {
    private int id;
    private FisaMedicala fisaMedicala;
    private LocalDate dataConsultatiei;
    private String simptome;
    private String diagnostic;
    private String tratament;
    private String observatii;

    public Consultatie () {}

    public Consultatie(int id, FisaMedicala fisaMedicala, LocalDate dataConsultatiei,
                       String simptome, String diagnostic, String tratament, String observatii) {
        this.id = id;
        this.fisaMedicala = fisaMedicala;
        this.dataConsultatiei = dataConsultatiei;
        this.simptome = simptome;
        this.diagnostic = diagnostic;
        this.tratament = tratament;
        this.observatii = observatii;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FisaMedicala getFisaMedicala() {
        return fisaMedicala;
    }

    public void setFisaMedicala(FisaMedicala fisaMedicala) {
        this.fisaMedicala = fisaMedicala;
    }

    public LocalDate getDataConsultatiei() {
        return dataConsultatiei;
    }

    public void setDataConsultatiei(LocalDate dataConsultatiei) {
        this.dataConsultatiei = dataConsultatiei;
    }

    public String getSimptome() {
        return simptome;
    }

    public void setSimptome(String simptome) {
        this.simptome = simptome;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        if (diagnostic == null || diagnostic.isEmpty()) {
            throw new IllegalArgumentException("Diagnostic invalid");
        }
        this.diagnostic = diagnostic;
    }

    public String getTratament() {
        return tratament;
    }

    public void setTratament(String tratament) {
        this.tratament = tratament;
    }

    public String getObservatii() {
        return observatii;
    }

    public void setObservatii(String observatii) {
        this.observatii = observatii;
    }

    @Override
    public String toString() {
        return "Consultatie din " + dataConsultatiei + " - " + diagnostic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consultatie)) return false;
        Consultatie consultatie = (Consultatie) o;
        return id != 0 && id == consultatie.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public static Consultatie fromFormData(ConsultatieFormData data) {
        Consultatie c = new Consultatie();

        if (!data.dataConsultatiei.isBlank())
            c.setDataConsultatiei(LocalDate.parse(data.dataConsultatiei));

        c.setSimptome(data.simptome);
        c.setDiagnostic(data.diagnostic);
        c.setTratament(data.tratament);
        c.setObservatii(data.observatii);

        return c;
    }
}