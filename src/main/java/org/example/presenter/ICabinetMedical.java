package org.example.presenter;

import org.example.model.Consultatie;
import org.example.model.FisaMedicala;
import org.example.model.Pacient;
import org.example.model.Sex;

import java.util.List;

public interface ICabinetMedical {
    Pacient getPacientFromFields();
    String getNume();
    String getPrenume();
    Sex getSex();
    String getInaltime();
    String getGreutate();
    String getCnp();
    String getDataNasterii();
    String getTelefon();
    String getAdresa();

    Consultatie getConsultatieFromFields();

    String getSearchText();
    String getDiagnosticFilterText();
    String getTratamentFilterText();

    int getSelectedPacientId();
    int getSelectedConsultatieId();

    void showPacienti(List<Pacient> pacienti);
    void showConsultatii(List<Consultatie> consultatii);

    void showMessage(String message);

    void clearFields();
    void clearConsultatieFields();
}
