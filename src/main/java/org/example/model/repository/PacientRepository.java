package org.example.model.repository;

import org.example.model.repository.database.DatabaseConnection;
import org.example.model.Sex;
import org.example.model.Pacient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientRepository {
    public int addPacient(Pacient pacient) {
        String sql = "INSERT INTO pacient (nume, prenume, sex, inaltime, " +
                "greutate, cnp, data_nasterii, telefon, adresa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, pacient.getNume());
            statement.setString(2, pacient.getPrenume());

            if (pacient.getSex() != null) {
                statement.setString(3, pacient.getSex().name());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }

            statement.setDouble(4, pacient.getInaltime());
            statement.setDouble(5, pacient.getGreutate());
            statement.setString(6, pacient.getCnp());

            if (pacient.getDataNasterii() != null) {
                statement.setDate(7, Date.valueOf(pacient.getDataNasterii()));
            } else {
                statement.setNull(7, Types.DATE);
            }

            statement.setString(8, pacient.getTelefon());
            statement.setString(9, pacient.getAdresa());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Pacient> getAllPacienti() {
        List<Pacient> pacienti = new ArrayList<>();
        String sql = "SELECT * FROM pacient";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Pacient pacient = new Pacient();
                pacient.setId(resultSet.getInt("id"));
                pacient.setNume(resultSet.getString("nume"));
                pacient.setPrenume(resultSet.getString("prenume"));
                pacient.setSex(Sex.valueOf(resultSet.getString("sex")));
                pacient.setInaltime(resultSet.getDouble("inaltime"));
                pacient.setGreutate(resultSet.getDouble("greutate"));
                pacient.setCnp(resultSet.getString("cnp"));

                Date data = resultSet.getDate("data_nasterii");
                if (data != null) {
                    pacient.setDataNasterii(data.toLocalDate());
                }

                pacient.setTelefon(resultSet.getString("telefon"));
                pacient.setAdresa(resultSet.getString("adresa"));

                pacienti.add(pacient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacienti;
    }

    public List<Pacient> findPacientiByName(String textCautat) {
        List<Pacient> pacienti = new ArrayList<>();

        if (textCautat == null || textCautat.trim().isEmpty()) {
            return pacienti;
        }

        String sql = "SELECT * FROM pacient " +
                "WHERE LOWER(nume) LIKE LOWER(?) " +
                "OR LOWER(prenume) LIKE LOWER(?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String pattern = "%" + textCautat.trim() + "%";
            statement.setString(1, pattern);
            statement.setString(2, pattern);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Pacient pacient = new Pacient();
                    pacient.setId(resultSet.getInt("id"));
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

                    pacienti.add(pacient);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacienti;
    }

    public Pacient findPacientById(int id) {
        String sql = "SELECT * FROM pacient WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pacient pacient = new Pacient();
                    pacient.setId(resultSet.getInt("id"));
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

                    return pacient;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updatePacient(Pacient pacient) {
        String sql = "UPDATE pacient SET nume = ?, prenume = ?, sex = ?, inaltime = ?, " +
                "greutate = ?, cnp = ?, data_nasterii = ?, telefon = ?, adresa = ? " +
                "WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pacient.getNume());
            statement.setString(2, pacient.getPrenume());

            if (pacient.getSex() != null) {
                statement.setString(3, pacient.getSex().name());
            } else {
                statement.setNull(3, Types.VARCHAR);
            }

            statement.setDouble(4, pacient.getInaltime());
            statement.setDouble(5, pacient.getGreutate());
            statement.setString(6, pacient.getCnp());

            if (pacient.getDataNasterii() != null) {
                statement.setDate(7, Date.valueOf(pacient.getDataNasterii()));
            } else {
                statement.setNull(7, Types.DATE);
            }

            statement.setString(8, pacient.getTelefon());
            statement.setString(9, pacient.getAdresa());
            statement.setInt(10, pacient.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("Nu s-a actualizat niciun pacient.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePacient(int id) {
        String sql = "DELETE FROM pacient WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted == 0) {
                System.out.println("Nu s-a sters niciun pacient.");
            } else {
                System.out.println("Pacient sters cu succes.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
