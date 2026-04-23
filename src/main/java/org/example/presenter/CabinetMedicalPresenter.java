package org.example.presenter;

import org.example.model.Consultatie;
import org.example.model.FisaMedicala;
import org.example.model.Pacient;
import org.example.model.repository.FisaMedicalaRepository;
import org.example.model.repository.PacientRepository;

import java.time.LocalDate;
import java.util.List;

public class CabinetMedicalPresenter {
    private final ICabinetMedical view;
    private final PacientRepository pacientRepository;
    private final FisaMedicalaRepository fisaMedicalaRepository;

    public CabinetMedicalPresenter(ICabinetMedical view) {
        this.view = view;
        this.pacientRepository = new PacientRepository();
        this.fisaMedicalaRepository = new FisaMedicalaRepository();
    }

    public void loadAllPacienti() {
        List<Pacient> pacienti = pacientRepository.getAllPacienti();
        view.showPacienti(pacienti);
    }

    public void addPacient() {
        Pacient pacient = new Pacient();

        pacient.setNume(view.getNume());
        pacient.setPrenume(view.getPrenume());
        pacient.setSex(view.getSex());

        String inaltime = view.getInaltime();
        if (!inaltime.isEmpty()) {
            pacient.setInaltime(Double.parseDouble(inaltime));
        }

        String greutate = view.getGreutate();
        if (!greutate.isEmpty()) {
            pacient.setGreutate(Double.parseDouble(greutate));
        }

        String data = view.getDataNasterii();
        if (!data.isEmpty()) {
            pacient.setDataNasterii(LocalDate.parse(data));
        }

        pacient.setCnp(view.getCnp());
        pacient.setTelefon(view.getTelefon());
        pacient.setAdresa(view.getAdresa());

        int pacientId = pacientRepository.addPacient(pacient);
        if (pacientId == -1) {
            view.showMessage("Pacientul nu a putut fi adaugat.");
            return;
        }
        pacient.setId(pacientId);

        FisaMedicala fisaMedicala = new FisaMedicala();
        fisaMedicala.setPacient(pacient);
        fisaMedicalaRepository.addFisaMedicala(fisaMedicala);

        view.showMessage("Pacient adaugat cu succes.");
        view.clearFields();
        loadAllPacienti();
    }

    public void searchPacientiByName() {
        String text = view.getSearchText();
        List<Pacient> pacienti = pacientRepository.findPacientiByName(text);
        view.showPacienti(pacienti);
    }

    public void updatePacient() {
        int id = view.getSelectedPacientId();
        if (id == -1) {
            view.showMessage("Selectati un pacient.");
            return;
        }

        Pacient pacient = new Pacient();

        pacient.setNume(view.getNume());
        pacient.setPrenume(view.getPrenume());
        pacient.setSex(view.getSex());

        String inaltime = view.getInaltime();
        if (!inaltime.isEmpty()) {
            pacient.setInaltime(Double.parseDouble(inaltime));
        }

        String greutate = view.getGreutate();
        if (!greutate.isEmpty()) {
            pacient.setGreutate(Double.parseDouble(greutate));
        }

        String data = view.getDataNasterii();
        if (!data.isEmpty()) {
            pacient.setDataNasterii(LocalDate.parse(data));
        }

        pacient.setCnp(view.getCnp());
        pacient.setTelefon(view.getTelefon());
        pacient.setAdresa(view.getAdresa());

        pacient.setId(id);

        pacientRepository.updatePacient(pacient);
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
        pacientRepository.deletePacient(id);
        view.showMessage("Pacient sters cu succes.");
        view.clearFields();
        loadAllPacienti();
    }

    public void loadFisaMedicalaForSelectedPacient() {
        int pacientId = view.getSelectedPacientId();
        if (pacientId == -1) {
            view.showMessage("Selectati un pacient.");
            return;
        }

        FisaMedicala fisaMedicala = fisaMedicalaRepository.findFisaMedicalaByPacientId(pacientId);

        if (fisaMedicala == null) {
            view.showConsultatii(new java.util.ArrayList<>());
            return;
        }

        view.showConsultatii(fisaMedicala.getListaConsultatii());
    }

    public void addConsultatie() {
        int pacientId = view.getSelectedPacientId();
        if (pacientId == -1) {
            view.showMessage("Selectati un pacient.");
            return;
        }

        FisaMedicala fisaMedicala = fisaMedicalaRepository.findFisaMedicalaByPacientId(pacientId);
        if (fisaMedicala == null) {
            view.showMessage("Pacientul nu are fisa medicala.");
            return;
        }

        Consultatie c = new Consultatie();

        String data = view.getDataConsultatiei();
        if (!data.isEmpty()) {
            c.setDataConsultatiei(LocalDate.parse(data));
        }

        c.setSimptome(view.getSimptome());
        c.setDiagnostic(view.getDiagnostic());
        c.setTratament(view.getTratament());
        c.setObservatii(view.getObservatii());
        fisaMedicalaRepository.addConsultatie(c, fisaMedicala.getId());

        FisaMedicala updatedFisa = fisaMedicalaRepository.findFisaMedicalaByPacientId(pacientId);
        if (updatedFisa != null) {
            view.showConsultatii(updatedFisa.getListaConsultatii());
        }

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

        fisaMedicalaRepository.deleteConsultatie(consultatieId);

        FisaMedicala updatedFisa = fisaMedicalaRepository.findFisaMedicalaByPacientId(pacientId);
        if (updatedFisa != null) {
            view.showConsultatii(updatedFisa.getListaConsultatii());
        } else {
            view.showConsultatii(new java.util.ArrayList<>());
        }

        view.showMessage("Consultatie stearsa cu succes.");
        view.clearConsultatieFields();
    }

    public Pacient getPacientById(int id) {
        return pacientRepository.findPacientById(id);
    }

    public void filterPacienti() {
        String diagnostic = view.getDiagnosticFilterText();
        String tratament = view.getTratamentFilterText();

        boolean hasDiagnostic = diagnostic != null && !diagnostic.trim().isEmpty();
        boolean hasTratament = tratament != null && !tratament.trim().isEmpty();

        if (!hasDiagnostic && !hasTratament) {
            loadAllPacienti();
            return;
        }

        List<FisaMedicala> fiseMedicale;

        if (hasDiagnostic && hasTratament) {
            fiseMedicale = fisaMedicalaRepository.findByDiagnosticAndTratament(diagnostic, tratament);
        } else if (hasDiagnostic) {
            fiseMedicale = fisaMedicalaRepository.findByDiagnostic(diagnostic);
        } else {
            fiseMedicale = fisaMedicalaRepository.findByTratament(tratament);
        }

        List<Pacient> pacienti = new java.util.ArrayList<>();

        for (FisaMedicala fisa : fiseMedicale) {
            if (fisa.getPacient() != null) {
                pacienti.add(fisa.getPacient());
            }
        }

        view.showPacienti(pacienti);
    }
}
