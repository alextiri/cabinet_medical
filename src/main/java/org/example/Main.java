package org.example;

import org.example.model.CabinetMedicalModel;
import org.example.presenter.CabinetMedicalController;
import org.example.view.MainFrame;

public class Main {
    public static void main(String[] args) {

        CabinetMedicalModel model = new CabinetMedicalModel();

        MainFrame view = new MainFrame(model);

        CabinetMedicalController controller =
                new CabinetMedicalController(view, model);

        view.setController(controller);

        model.addObserver(view);
    }
}