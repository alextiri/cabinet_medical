package org.example.presenter;

import org.example.model.CabinetMedicalModel;
import org.example.model.Consultatie;
import org.example.model.Pacient;
import org.example.model.dto.PacientFormData;
import org.example.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class CabinetMedicalController implements ICabinetMedicalController {
    private final CabinetMedicalModel model;
    private final ICabinetMedicalView view;

    public CabinetMedicalController(ICabinetMedicalView view, CabinetMedicalModel model) {
        this.view = view;
        this.model = model;
        model.addObserver((Observer) view);
    }

    public void loadAllPacienti() {
        model.loadAllPacienti();
    }

    public void addPacient() {
        PacientFormData data = view.getPacientFormData();
        model.addPacient(Pacient.fromFormData(data, null));
        view.showMessage("Pacient adaugat cu succes.");
        view.clearFields();

        loadAllPacienti();
    }

    public void searchPacientiByName() {
        List<Pacient> pacienti = model.searchPacientiByName(view.getSearchText());
        view.showPacienti(pacienti);
    }

    public void updatePacient() {
        int id = view.getSelectedPacientId();
        if (id == -1) {
            view.showMessage("Selectati un pacient.");
            return;
        }

        Pacient pacient = Pacient.fromFormData(view.getPacientFormData(), id);

        model.updatePacient(pacient);
        view.showMessage("Pacient actualizat cu succes.");
        view.clearFields();

        loadAllPacienti();
    }

    public void deletePacient() {
        int id = view.getSelectedPacientId();
        if (id == -1) {
            view.showMessage("Selectati un pacient.");
            return;
        }

        model.deletePacient(id);
        view.showMessage("Pacient sters cu succes.");
        view.clearFields();

        loadAllPacienti();
    }

    private void refreshConsultatii(int pacientId) {
        view.showConsultatii(model.getConsultatiiForPacient(pacientId));
    }

    public void addConsultatie() {
        int pacientId = view.getSelectedPacientId();

        if (pacientId == -1) {
            view.showMessage("Selectati un pacient.");
            return;
        }

        Consultatie consultatie = Consultatie.fromFormData(view.getConsultatieFormData());

        model.addConsultatie(pacientId, consultatie);

        refreshConsultatii(pacientId);

        view.showMessage("Consultatie adaugata cu succes.");
        view.clearConsultatieFields();
    }

    public void deleteConsultatie() {
        int pacientId = view.getSelectedPacientId();
        if (pacientId == -1) {
            view.showMessage("Selectati un pacient.");
            return;
        }

        int consultatieId = view.getSelectedConsultatieId();
        if (consultatieId == -1) {
            view.showMessage("Selectati o consultatie.");
            return;
        }

        model.deleteConsultatie(pacientId, consultatieId);

        refreshConsultatii(pacientId);

        view.showMessage("Consultatie stearsa cu succes.");
        view.clearConsultatieFields();
    }

    public void filterPacienti() {
        String diagnostic = view.getDiagnosticFilterText();
        String tratament = view.getTratamentFilterText();

        List<Pacient> pacienti = model.filterPacienti(diagnostic, tratament);

        view.showPacienti(pacienti);
    }

    @Override
    public void onPacientSelected(int id) {
        Pacient pacient = model.getPacientById(id);

        if (pacient == null) {
            view.showPacientDetails(null);
            view.showConsultatii(new ArrayList<>());
            return;
        }

        view.populatePacientForm(pacient.toFormData());
        view.showPacientDetails(pacient);

        refreshConsultatii(id);
    }
}
