package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class FisaMedicala {
    private int id;
    private Pacient pacient;
    private List<Consultatie> listaConsultatii;

    public FisaMedicala() {
        this.listaConsultatii = new ArrayList<>();
    }

    public FisaMedicala(int id, Pacient pacient, List<Consultatie> listaConsultatii) {
        this.id = id;
        this.pacient = pacient;
        this.listaConsultatii = listaConsultatii != null ? listaConsultatii : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public List<Consultatie> getListaConsultatii() {
        return listaConsultatii;
    }

    public void setListaConsultatii(List<Consultatie> listaConsultatii) {
        this.listaConsultatii = listaConsultatii != null ? listaConsultatii : new ArrayList<>();
        for(Consultatie c : this.listaConsultatii) {
            c.setFisaMedicala(this);
        }
    }

    @Override
    public String toString() {
        return "Fisa medicala a pacientului " + pacient;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof FisaMedicala)) return false;
        FisaMedicala fisaMedicala = (FisaMedicala) o;
        return id != 0 && id == fisaMedicala.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
