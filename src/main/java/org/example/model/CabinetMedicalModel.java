package org.example.model;

import org.example.observer.Observer;
import org.example.observer.Subject;
import org.example.model.repository.PacientRepository;
import org.example.model.repository.FisaMedicalaRepository;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;

public class CabinetMedicalModel implements Subject {
    private final PacientRepository pacientRepository = new PacientRepository();
    private final FisaMedicalaRepository fisaMedicalaRepository = new FisaMedicalaRepository();
    private final List<Observer> observers = new ArrayList<>();
    private List<Pacient> pacienti = new ArrayList<>();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public List<Pacient> getPacienti() {
        return new ArrayList<>(pacienti);
    }

    public void setPacienti(List<Pacient> pacienti) {
        this.pacienti = pacienti;
        notifyObservers();
    }

    public void addPacient(Pacient p) {
        int id = pacientRepository.addPacient(p);
        p.setId(id);

        FisaMedicala fisa = new FisaMedicala();
        fisa.setPacient(p);
        fisaMedicalaRepository.addFisaMedicala(fisa);

        notifyObservers();
    }

    public void updatePacient(Pacient pacient) {
        pacientRepository.updatePacient(pacient);
        notifyObservers();
    }

    public void deletePacient(int id) {
        pacientRepository.deletePacient(id);
        notifyObservers();
    }

    public Pacient getPacientById(int id) {
        return pacientRepository.findPacientById(id);
    }

    public void loadAllPacienti() {
        List<Pacient> pacienti = pacientRepository.getAllPacienti();
        pacienti.sort(Comparator.comparingInt(Pacient::getId));
        setPacienti(pacienti);
    }

    public void addConsultatie(int pacientId, Consultatie consultatie) {
        FisaMedicala fisa = fisaMedicalaRepository.findFisaMedicalaByPacientId(pacientId);

        if (fisa == null) {
            throw new RuntimeException("Pacientul nu are fisa medicala.");
        }

        fisaMedicalaRepository.addConsultatie(consultatie, fisa.getId());

        notifyObservers();
    }

    public void deleteConsultatie(int pacientId, int consultatieId) {
        FisaMedicala fisa = fisaMedicalaRepository.findFisaMedicalaByPacientId(pacientId);

        if (fisa == null) return;

        fisaMedicalaRepository.deleteConsultatie(consultatieId);

        notifyObservers();
    }

    public List<Consultatie> getConsultatiiForPacient(int pacientId) {
        FisaMedicala fisa = fisaMedicalaRepository.findFisaMedicalaByPacientId(pacientId);

        if (fisa == null) return new ArrayList<>();

        return fisa.getListaConsultatii();
    }

    public List<Pacient> filterPacienti(String diagnostic, String tratament) {

        boolean hasDiagnostic = diagnostic != null && !diagnostic.trim().isEmpty();
        boolean hasTratament = tratament != null && !tratament.trim().isEmpty();

        List<FisaMedicala> fiseMedicale;

        if (!hasDiagnostic && !hasTratament) {
            return pacientRepository.getAllPacienti();
        }

        if (hasDiagnostic && hasTratament) {
            fiseMedicale = fisaMedicalaRepository.findByDiagnosticAndTratament(diagnostic, tratament);
        } else if (hasDiagnostic) {
            fiseMedicale = fisaMedicalaRepository.findByDiagnostic(diagnostic);
        } else {
            fiseMedicale = fisaMedicalaRepository.findByTratament(tratament);
        }

        List<Pacient> pacienti = new ArrayList<>();

        for (FisaMedicala fisa : fiseMedicale) {
            if (fisa.getPacient() != null) {
                pacienti.add(fisa.getPacient());
            }
        }

        return pacienti;
    }

    public List<Pacient> searchPacientiByName(String text) {
        return pacientRepository.findPacientiByName(text);
    }
}