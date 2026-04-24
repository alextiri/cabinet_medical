package org.example.model.dto;

public class ConsultatieFormData {
    public final String dataConsultatiei;
    public final String simptome;
    public final String diagnostic;
    public final String tratament;
    public final String observatii;

    public ConsultatieFormData(String dataConsultatiei,
                               String simptome,
                               String diagnostic,
                               String tratament,
                               String observatii) {
        this.dataConsultatiei = dataConsultatiei;
        this.simptome = simptome;
        this.diagnostic = diagnostic;
        this.tratament = tratament;
        this.observatii = observatii;
    }
}