package org.example.model.repository;

import org.example.model.repository.database.DatabaseConnection;
import org.example.model.Consultatie;
import org.example.model.FisaMedicala;
import org.example.model.Pacient;
import org.example.model.Sex;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FisaMedicalaRepository {
    public void addFisaMedicala(FisaMedicala fisaMedicala) {
        if (fisaMedicala == null || fisaMedicala.getPacient() == null || fisaMedicala.getPacient().getId() <= 0) {
            throw new IllegalArgumentException("Fisa medicala invalida.");
        }

        String sql = "INSERT INTO fisa_medicala (pacient_id) VALUES(?)";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, fisaMedicala.getPacient().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FisaMedicala findFisaMedicalaByPacientId(int pacientId) {
        String sql = """
            SELECT fm.id AS fisa_id,
                   p.id AS pacient_id,
                   p.nume,
                   p.prenume,
                   p.sex,
                   p.inaltime,
                   p.greutate,
                   p.cnp,
                   p.data_nasterii,
                   p.telefon,
                   p.adresa
            FROM fisa_medicala fm
            JOIN pacient p ON fm.pacient_id = p.id
            WHERE p.id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, pacientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pacient pacient = new Pacient();
                    pacient.setId(resultSet.getInt("pacient_id"));
                    pacient.setNume(resultSet.getString("nume"));
                    pacient.setPrenume(resultSet.getString("prenume"));

                    String sexValue = resultSet.getString("sex");
                    if (sexValue != null) {
                        pacient.setSex(Sex.valueOf(sexValue));
                    }

                    pacient.setInaltime(resultSet.getDouble("inaltime"));
                    pacient.setGreutate(resultSet.getDouble("greutate"));
                    pacient.setCnp(resultSet.getString("cnp"));

                    Date data = resultSet.getDate("data_nasterii");
                    if (data != null) {
                        pacient.setDataNasterii(data.toLocalDate());
                    }

                    pacient.setTelefon(resultSet.getString("telefon"));
                    pacient.setAdresa(resultSet.getString("adresa"));

                    FisaMedicala fisaMedicala = new FisaMedicala();
                    fisaMedicala.setId(resultSet.getInt("fisa_id"));
                    fisaMedicala.setPacient(pacient);
                    fisaMedicala.setListaConsultatii(getConsultatiiByFisaId(fisaMedicala.getId()));

                    return fisaMedicala;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Consultatie> getConsultatiiByFisaId(int fisaMedicalaId) {
        List<Consultatie> consultatii = new ArrayList<>();

        String sql = """
            SELECT id, data_consultatiei, simptome, diagnostic, tratament, observatii
            FROM consultatie
            WHERE fisa_medicala_id = ?
            ORDER BY data_consultatiei DESC
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, fisaMedicalaId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Consultatie consultatie = new Consultatie();
                    consultatie.setId(resultSet.getInt("id"));

                    Date data = resultSet.getDate("data_consultatiei");
                    if (data != null) {
                        consultatie.setDataConsultatiei(data.toLocalDate());
                    }

                    consultatie.setSimptome(resultSet.getString("simptome"));
                    consultatie.setDiagnostic(resultSet.getString("diagnostic"));
                    consultatie.setTratament(resultSet.getString("tratament"));
                    consultatie.setObservatii(resultSet.getString("observatii"));
                    consultatie.setFisaMedicala(new FisaMedicala());
                    consultatie.getFisaMedicala().setId(fisaMedicalaId);

                    consultatii.add(consultatie);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultatii;
    }

    public List<FisaMedicala> findByDiagnostic(String diagnostic) {
        List<FisaMedicala> fiseMedicale = new ArrayList<>();

        if (diagnostic == null || diagnostic.trim().isEmpty()) {
            return fiseMedicale;
        }

        String sql = """
        SELECT DISTINCT fm.id AS fisa_id, fm.pacient_id
        FROM fisa_medicala fm
        JOIN consultatie c ON fm.id = c.fisa_medicala_id
        WHERE LOWER(c.diagnostic) LIKE LOWER(?)
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String pattern = "%" + diagnostic.trim() + "%";
            statement.setString(1, pattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int pacientId = resultSet.getInt("pacient_id");
                    FisaMedicala fisaMedicala = findFisaMedicalaByPacientId(pacientId);

                    if (fisaMedicala != null) {
                        fiseMedicale.add(fisaMedicala);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fiseMedicale;
    }

    public List<FisaMedicala> findByTratament(String tratament) {
        List<FisaMedicala> fiseMedicale = new ArrayList<>();

        if (tratament == null || tratament.trim().isEmpty()) {
            return fiseMedicale;
        }

        String sql = """
        SELECT DISTINCT fm.id AS fisa_id, fm.pacient_id
        FROM fisa_medicala fm
        JOIN consultatie c ON fm.id = c.fisa_medicala_id
        WHERE LOWER(c.tratament) LIKE LOWER(?)
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String pattern = "%" + tratament.trim() + "%";
            statement.setString(1, pattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int pacientId = resultSet.getInt("pacient_id");
                    FisaMedicala fisaMedicala = findFisaMedicalaByPacientId(pacientId);

                    if (fisaMedicala != null) {
                        fiseMedicale.add(fisaMedicala);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fiseMedicale;
    }

    public void addConsultatie(Consultatie consultatie, int fisaMedicalaId) {
        if (consultatie == null || fisaMedicalaId <= 0) {
            throw new IllegalArgumentException("Consultatie invalida.");
        }

        if (consultatie.getDataConsultatiei() == null) {
            throw new IllegalArgumentException("Data consultatiei este obligatorie.");
        }

        String sql = """
        INSERT INTO consultatie (fisa_medicala_id, data_consultatiei, simptome, diagnostic, tratament, observatii)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, fisaMedicalaId);

            if (consultatie.getDataConsultatiei() != null) {
                statement.setDate(2, Date.valueOf(consultatie.getDataConsultatiei()));
            } else {
                statement.setNull(2, Types.DATE);
            }

            statement.setString(3, consultatie.getSimptome());
            statement.setString(4, consultatie.getDiagnostic());
            statement.setString(5, consultatie.getTratament());
            statement.setString(6, consultatie.getObservatii());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteConsultatie(int id) {
        String sql = "DELETE FROM consultatie WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("Nu s-a sters nicio consultatie.");
            } else {
                System.out.println("Consultatie stearsa cu succes.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FisaMedicala> findByDiagnosticAndTratament(String diagnostic, String tratament) {
        List<FisaMedicala> fiseMedicale = new ArrayList<>();

        if (diagnostic == null || diagnostic.trim().isEmpty() ||
                tratament == null || tratament.trim().isEmpty()) {
            return fiseMedicale;
        }

        String sql = """
        SELECT DISTINCT fm.id AS fisa_id, fm.pacient_id
        FROM fisa_medicala fm
        WHERE EXISTS (
            SELECT 1
            FROM consultatie c1
            WHERE c1.fisa_medicala_id = fm.id
              AND LOWER(c1.diagnostic) LIKE LOWER(?)
        )
        AND EXISTS (
            SELECT 1
            FROM consultatie c2
            WHERE c2.fisa_medicala_id = fm.id
              AND LOWER(c2.tratament) LIKE LOWER(?)
        )
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + diagnostic.trim() + "%");
            statement.setString(2, "%" + tratament.trim() + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int pacientId = resultSet.getInt("pacient_id");
                    FisaMedicala fisaMedicala = findFisaMedicalaByPacientId(pacientId);

                    if (fisaMedicala != null) {
                        fiseMedicale.add(fisaMedicala);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fiseMedicale;
    }
}
