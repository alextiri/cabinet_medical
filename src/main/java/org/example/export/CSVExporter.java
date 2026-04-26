package org.example.export;

import org.example.model.Pacient;

import java.io.PrintWriter;
import java.util.List;

public class CSVExporter implements Exporter {
    @Override
    public void export(List<Pacient> pacienti, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("id,nume,prenume,sex,inaltime,greutate,cnp,dataNasterii,telefon,adresa");
            for (Pacient p : pacienti) {
                writer.println(
                        p.getId() + "," +
                                p.getNume() + "," +
                                p.getPrenume() + "," +
                                (p.getSex() != null ? p.getSex() : "") + "," +
                                p.getInaltime() + "," +
                                p.getGreutate() + "," +
                                p.getCnp() + "," +
                                (p.getDataNasterii() != null ? p.getDataNasterii() : "") + "," +
                                p.getTelefon() + "," +
                                p.getAdresa()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
