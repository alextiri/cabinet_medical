package org.example.presenter;

public interface ICabinetMedicalController {

    void addPacient();
    void updatePacient();
    void deletePacient();

    void addConsultatie();
    void deleteConsultatie();

    void loadAllPacienti();
    void searchPacientiByName();
    void filterPacienti();

    void onPacientSelected(int pacientId);
}
