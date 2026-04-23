package org.example.model.repository.database;

import org.example.model.Sex;
import org.example.model.Pacient;
import org.example.model.repository.PacientRepository;

import java.sql.Connection;
import java.time.LocalDate;

public class TestConnection {
    public static void main(String args[]) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Conexiune reusita!");
            PacientRepository repo = new PacientRepository();

            Pacient p = new Pacient(1, "Popescu", "Ion", Sex.Masculin,
                    180, 75, "1234567890123",
                    LocalDate.of(1990, 1, 1),
                    "0712345678", "Bucuresti");

            repo.addPacient(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
