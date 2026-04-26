package org.example.export;

import org.example.model.Pacient;

import java.io.PrintWriter;
import java.util.List;

public class JSONExporter implements Exporter {
    @Override
    public void export(List<Pacient> pacienti, String filePath) {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println("[");
            for (int i = 0; i < pacienti.size(); i++) {
                Pacient p = pacienti.get(i);

                writer.println("  {");
                writer.println("    \"id\": " + p.getId() + ",");
                writer.println("    \"nume\": \"" + p.getNume() + "\",");
                writer.println("    \"prenume\": \"" + p.getPrenume() + "\",");
                writer.println("    \"sex\": \"" + p.getSex() + "\",");
                writer.println("    \"inaltime\": " + p.getInaltime() + ",");
                writer.println("    \"greutate\": " + p.getGreutate() + ",");
                writer.println("    \"cnp\": \"" + p.getCnp() + "\",");
                writer.println("    \"dataNasterii\": \"" + p.getDataNasterii() + "\",");
                writer.println("    \"telefon\": \"" + p.getTelefon() + "\",");
                writer.println("    \"adresa\": \"" + p.getAdresa() + "\"");
                writer.print("  }");

                if (i < pacienti.size() - 1) writer.println(",");
                else writer.println();
            }
            writer.println("]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
