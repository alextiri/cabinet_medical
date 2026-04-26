package org.example.export;

import org.example.model.Pacient;

import java.io.PrintWriter;
import java.util.List;

public class XMLExporter implements Exporter {
    @Override
    public void export(List<Pacient> pacienti, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("<pacienti>");
            for (Pacient p : pacienti) {
                writer.println("  <pacient>");
                writer.println("    <id>" + p.getId() + "</id>");
                writer.println("    <nume>" + p.getNume() + "</nume>");
                writer.println("    <prenume>" + p.getPrenume() + "</prenume>");
                writer.println("    <sex>" + p.getSex() + "</sex>");
                writer.println("    <inaltime>" + p.getInaltime() + "</inaltime>");
                writer.println("    <greutate>" + p.getGreutate() + "</greutate>");
                writer.println("    <cnp>" + p.getCnp() + "</cnp>");
                writer.println("    <dataNasterii>" + p.getDataNasterii() + "</dataNasterii>");
                writer.println("    <telefon>" + p.getTelefon() + "</telefon>");
                writer.println("    <adresa>" + p.getAdresa() + "</adresa>");
                writer.println("  </pacient>");
            }
            writer.println("</pacienti>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
