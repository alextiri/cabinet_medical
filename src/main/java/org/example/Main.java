package org.example;

import org.example.model.CabinetMedicalModel;
import org.example.controller.CabinetMedicalController;
import org.example.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        CabinetMedicalModel model = new CabinetMedicalModel();
        MainFrame view = new MainFrame();
        CabinetMedicalController controller = new CabinetMedicalController(view, model);

        view.setController(controller);
    }
}