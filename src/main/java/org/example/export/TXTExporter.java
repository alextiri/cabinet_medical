package org.example.export;

import org.example.model.Pacient;

import java.io.PrintWriter;
import java.util.List;

public class TXTExporter implements Exporter {
    @Override
    public void export(List<Pacient> pacienti, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Pacient p : pacienti) {
                writer.println("ID: " + p.getId());
                writer.println("Nume: " + p.getNume());
                writer.println("Prenume: " + p.getPrenume());
                writer.println("Sex: " + p.getSex());
                writer.println("Inaltime: " + p.getInaltime());
                writer.println("Greutate: " + p.getGreutate());
                writer.println("CNP: " + p.getCnp());
                writer.println("Data nasterii: " + p.getDataNasterii());
                writer.println("Telefon: " + p.getTelefon());
                writer.println("Adresa: " + p.getAdresa());
                writer.println("----------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
