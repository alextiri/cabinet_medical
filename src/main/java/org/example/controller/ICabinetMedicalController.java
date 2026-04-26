package org.example.controller;

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

    void refreshView();
    void exportPacienti(String format, String path);
}
