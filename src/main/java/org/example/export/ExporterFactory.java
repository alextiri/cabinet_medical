package org.example.export;

public class ExporterFactory {
    public static Exporter getExporter(String type) {
        return switch (type.toLowerCase()) {
            case "csv" -> new CSVExporter();
            case "json" -> new JSONExporter();
            case "xml" -> new XMLExporter();
            case "txt" -> new TXTExporter();
            default -> throw new IllegalArgumentException("Format invalid");
        };
    }
}
