package org.example.export;

import org.example.model.Pacient;

import java.util.List;

public interface Exporter {
    void export(List<Pacient> pacienti, String filePath);
}