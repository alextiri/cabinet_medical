package org.example.presenter;

import org.example.model.Consultatie;
import org.example.model.Pacient;
import org.example.model.dto.ConsultatieFormData;
import org.example.model.dto.PacientFormData;

import java.util.List;

public interface ICabinetMedicalView {

    PacientFormData getPacientFormData();
    ConsultatieFormData getConsultatieFormData();

    int getSelectedPacientId();
    int getSelectedConsultatieId();

    String getSearchText();
    String getDiagnosticFilterText();
    String getTratamentFilterText();

    void showPacienti(List<Pacient> pacienti);
    void showConsultatii(List<Consultatie> consultatii);

    void showMessage(String message);

    void clearFields();
    void clearPacientDetails();
    void clearConsultatieFields();

    void showPacientDetails(Pacient pacient);
    void populatePacientForm(PacientFormData data);
}
