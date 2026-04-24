package org.example.view;

import org.example.model.CabinetMedicalModel;
import org.example.observer.Observer;
import org.example.model.Consultatie;
import org.example.model.Pacient;
import org.example.model.Sex;
import org.example.model.dto.ConsultatieFormData;
import org.example.model.dto.PacientFormData;
import org.example.presenter.ICabinetMedicalController;
import org.example.presenter.ICabinetMedicalView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ICabinetMedicalView, Observer {
    private ICabinetMedicalController controller;
    private final CabinetMedicalModel model;

    private JTextField txtNume;
    private JTextField txtPrenume;
    private JComboBox<Sex> cmbSex;
    private JTextField txtInaltime;
    private JTextField txtGreutate;
    private JTextField txtCnp;
    private JTextField txtDataNasterii;
    private JTextField txtTelefon;
    private JTextField txtAdresa;
    private JTextField txtSearch;
    private JTextField txtDataConsultatiei;
    private JTextField txtSimptome;
    private JTextField txtDiagnostic;
    private JTextField txtTratament;
    private JTextField txtObservatii;
    private JTextField txtDiagnosticFilter;
    private JTextField txtTratamentFilter;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnSearch;
    private JButton btnAddConsultatie;
    private JButton btnDeleteConsultatie;
    private JButton btnFilter;
    private JButton btnShowAllPacienti;

    private JLabel lblDetNumeValue;
    private JLabel lblDetPrenumeValue;
    private JLabel lblDetSexValue;
    private JLabel lblDetInaltimeValue;
    private JLabel lblDetGreutateValue;
    private JLabel lblDetCnpValue;
    private JLabel lblDetDataNasteriiValue;
    private JLabel lblDetTelefonValue;
    private JLabel lblDetAdresaValue;

    private JTable tablePacienti;
    private DefaultTableModel tableModel;

    private JTable tableConsultatii;
    private DefaultTableModel consultatiiTableModel;

    private JSplitPane consultatiiSplit;
    private JSplitPane rightSplit;
    private JSplitPane mainSplit;

    public MainFrame(CabinetMedicalModel model) {
        this.model = model;
        model.addObserver(this);

        initializeComponents();
        btnAdd.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnSearch.setEnabled(false);
        btnAddConsultatie.setEnabled(false);
        btnDeleteConsultatie.setEnabled(false);
        btnFilter.setEnabled(false);
        btnShowAllPacienti.setEnabled(false);
        layoutComponents();
        registerListeners();

        setTitle("Cabinet Medical");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setController(ICabinetMedicalController controller) {
        this.controller = controller;
        btnAdd.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
        btnSearch.setEnabled(true);
        btnAddConsultatie.setEnabled(true);
        btnDeleteConsultatie.setEnabled(true);
        btnFilter.setEnabled(true);
        btnShowAllPacienti.setEnabled(true);

        controller.loadAllPacienti();
    }

    private void initializeComponents() {
        txtNume = new JTextField(15);
        txtPrenume = new JTextField(15);
        cmbSex = new JComboBox<>(Sex.values());
        txtInaltime = new JTextField(15);
        txtGreutate = new JTextField(15);
        txtCnp = new JTextField(15);
        txtDataNasterii = new JTextField(15);
        txtTelefon = new JTextField(15);
        txtAdresa = new JTextField(15);
        txtSearch = new JTextField(15);
        txtDiagnosticFilter = new JTextField(12);
        txtTratamentFilter = new JTextField(12);

        txtDataConsultatiei = new JTextField(15);
        txtSimptome = new JTextField(15);
        txtDiagnostic = new JTextField(15);
        txtTratament = new JTextField(15);
        txtObservatii = new JTextField(15);

        lblDetNumeValue = new JLabel("-");
        lblDetPrenumeValue = new JLabel("-");
        lblDetSexValue = new JLabel("-");
        lblDetInaltimeValue = new JLabel("-");
        lblDetGreutateValue = new JLabel("-");
        lblDetCnpValue = new JLabel("-");
        lblDetDataNasteriiValue = new JLabel("-");
        lblDetTelefonValue = new JLabel("-");
        lblDetAdresaValue = new JLabel("-");

        consultatiiTableModel = new DefaultTableModel(
                new Object[]{"ID", "Data", "Simptome", "Diagnostic", "Tratament", "Observatii"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableConsultatii = new JTable(consultatiiTableModel);
        tableConsultatii.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableConsultatii.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tableConsultatii.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableConsultatii.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableConsultatii.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableConsultatii.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableConsultatii.getColumnModel().getColumn(4).setPreferredWidth(150);
        tableConsultatii.getColumnModel().getColumn(5).setPreferredWidth(200);

        btnAdd = new JButton("Adauga Pacient");
        btnUpdate = new JButton("Actualizare Pacient");
        btnDelete = new JButton("Sterge Pacient");
        btnClear = new JButton("Reseteaza Campurile");
        btnSearch = new JButton("Cautare");
        btnFilter = new JButton("Filtreaza");
        btnShowAllPacienti = new JButton("Reseteaza filtrul");
        btnAddConsultatie = new JButton("Adauga Consultatie");
        btnDeleteConsultatie = new JButton("Sterge Consultatie");


        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Pacient"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePacienti = new JTable(tableModel);
        tablePacienti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePacienti.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn idCol = tablePacienti.getColumnModel().getColumn(0);
        idCol.setMinWidth(50);
        idCol.setMaxWidth(50);

        tablePacienti.getColumnModel().getColumn(1).setPreferredWidth(250);
    }

    private JPanel createFormRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(8, 5));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(220, 25));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return row;
    }

    private JPanel createButtonRow(JButton... buttons) {
        JPanel row = new JPanel(new GridLayout(1, buttons.length, 8, 8));
        for (JButton button : buttons) {
            row.add(button);
        }
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return row;
    }

    private void layoutComponents() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Date pacient"));

        formPanel.add(createFormRow("Nume:", txtNume));
        formPanel.add(createFormRow("Prenume:", txtPrenume));
        formPanel.add(createFormRow("Sex:", cmbSex));
        formPanel.add(createFormRow("Inaltime (cm):", txtInaltime));
        formPanel.add(createFormRow("Greutate:", txtGreutate));
        formPanel.add(createFormRow("CNP:", txtCnp));
        formPanel.add(createFormRow("Data nasterii (yyyy-MM-dd):", txtDataNasterii));
        formPanel.add(createFormRow("Telefon:", txtTelefon));
        formPanel.add(createFormRow("Adresa:", txtAdresa));

        JPanel pacientButtonPanel = new JPanel();
        pacientButtonPanel.setLayout(new BoxLayout(pacientButtonPanel, BoxLayout.Y_AXIS));
        pacientButtonPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        pacientButtonPanel.add(createButtonRow(btnAdd, btnUpdate));
        pacientButtonPanel.add(Box.createVerticalStrut(8));
        pacientButtonPanel.add(createButtonRow(btnDelete, btnClear));

        JPanel searchPanel = new JPanel(new BorderLayout(8, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Cautare pacient"));

        JPanel searchInnerPanel = new JPanel(new BorderLayout(8, 5));
        searchInnerPanel.add(new JLabel("Nume:"), BorderLayout.WEST);
        searchInnerPanel.add(txtSearch, BorderLayout.CENTER);
        searchInnerPanel.add(btnSearch, BorderLayout.EAST);

        searchPanel.add(searchInnerPanel, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtrare pacienti"));

        JPanel diagnosticPanel = new JPanel(new BorderLayout(8, 5));
        diagnosticPanel.add(new JLabel("Diagnostic:"), BorderLayout.WEST);
        diagnosticPanel.add(txtDiagnosticFilter, BorderLayout.CENTER);

        JPanel tratamentPanel = new JPanel(new BorderLayout(8, 5));
        tratamentPanel.add(new JLabel("Tratament:"), BorderLayout.WEST);
        tratamentPanel.add(txtTratamentFilter, BorderLayout.CENTER);

        JPanel filterButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterButtonsPanel.add(btnFilter);
        filterButtonsPanel.add(btnShowAllPacienti);

        diagnosticPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tratamentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        filterButtonsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        filterPanel.add(diagnosticPanel);
        filterPanel.add(Box.createVerticalStrut(6));
        filterPanel.add(tratamentPanel);
        filterPanel.add(Box.createVerticalStrut(6));
        filterPanel.add(filterButtonsPanel);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(pacientButtonPanel, BorderLayout.SOUTH);

        JScrollPane pacientiScrollPane = new JScrollPane(tablePacienti);
        pacientiScrollPane.setBorder(BorderFactory.createTitledBorder("Pacienti"));

        JPanel topCenterPanel = new JPanel();
        topCenterPanel.setLayout(new BoxLayout(topCenterPanel, BoxLayout.Y_AXIS));
        topCenterPanel.add(searchPanel);
        topCenterPanel.add(Box.createVerticalStrut(8));
        topCenterPanel.add(filterPanel);

        JPanel pacientSection = new JPanel(new BorderLayout());
        pacientSection.add(topCenterPanel, BorderLayout.NORTH);
        pacientSection.add(pacientiScrollPane, BorderLayout.CENTER);
        pacientSection.setPreferredSize(new Dimension(280, 0));
        pacientSection.setMinimumSize(new Dimension(240, 0));

        JPanel pacientDetailsPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        pacientDetailsPanel.setBorder(BorderFactory.createTitledBorder("Detalii pacient selectat"));

        pacientDetailsPanel.add(new JLabel("Nume:"));
        pacientDetailsPanel.add(lblDetNumeValue);

        pacientDetailsPanel.add(new JLabel("Prenume:"));
        pacientDetailsPanel.add(lblDetPrenumeValue);

        pacientDetailsPanel.add(new JLabel("Sex:"));
        pacientDetailsPanel.add(lblDetSexValue);

        pacientDetailsPanel.add(new JLabel("Inaltime (cm):"));
        pacientDetailsPanel.add(lblDetInaltimeValue);

        pacientDetailsPanel.add(new JLabel("Greutate:"));
        pacientDetailsPanel.add(lblDetGreutateValue);

        pacientDetailsPanel.add(new JLabel("CNP:"));
        pacientDetailsPanel.add(lblDetCnpValue);

        pacientDetailsPanel.add(new JLabel("Data nasterii:"));
        pacientDetailsPanel.add(lblDetDataNasteriiValue);

        pacientDetailsPanel.add(new JLabel("Telefon:"));
        pacientDetailsPanel.add(lblDetTelefonValue);

        pacientDetailsPanel.add(new JLabel("Adresa:"));
        pacientDetailsPanel.add(lblDetAdresaValue);

        JScrollPane consultatiiScrollPane = new JScrollPane(tableConsultatii);
        consultatiiScrollPane.setBorder(BorderFactory.createTitledBorder("Consultatii existente"));

        JPanel consultatiiTablePanel = new JPanel(new BorderLayout());
        consultatiiTablePanel.add(consultatiiScrollPane, BorderLayout.CENTER);

        JPanel consultatieFormPanel = new JPanel();
        consultatieFormPanel.setLayout(new BoxLayout(consultatieFormPanel, BoxLayout.Y_AXIS));
        consultatieFormPanel.setBorder(BorderFactory.createTitledBorder("Adauga consultatie noua"));

        consultatieFormPanel.add(createFormRow("Data consultatiei (yyyy-MM-dd):", txtDataConsultatiei));
        consultatieFormPanel.add(createFormRow("Simptome:", txtSimptome));
        consultatieFormPanel.add(createFormRow("Diagnostic:", txtDiagnostic));
        consultatieFormPanel.add(createFormRow("Tratament:", txtTratament));
        consultatieFormPanel.add(createFormRow("Observatii:", txtObservatii));

        JPanel consultatieButtonPanel = new JPanel(new FlowLayout());
        consultatieButtonPanel.add(btnAddConsultatie);
        consultatieButtonPanel.add(btnDeleteConsultatie);

        JPanel consultatieInputPanel = new JPanel(new BorderLayout());
        consultatieInputPanel.add(consultatieFormPanel, BorderLayout.CENTER);
        consultatieInputPanel.add(consultatieButtonPanel, BorderLayout.SOUTH);

        consultatiiSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, consultatiiTablePanel, consultatieInputPanel);
        consultatiiSplit.setResizeWeight(0.72);

        rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pacientDetailsPanel, consultatiiSplit);
        rightSplit.setResizeWeight(0.22);
        rightSplit.setPreferredSize(new Dimension(700, 0));
        rightSplit.setMinimumSize(new Dimension(500, 0));

        mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pacientSection, rightSplit);
        mainSplit.setDividerLocation(350);
        mainSplit.setResizeWeight(0.25);

        setLayout(new BorderLayout(10, 10));
        add(leftPanel, BorderLayout.WEST);
        add(mainSplit, BorderLayout.CENTER);
    }

    private void registerListeners() {
        btnAdd.addActionListener(e -> controller.addPacient());
        btnUpdate.addActionListener(e -> controller.updatePacient());
        btnDelete.addActionListener(e -> controller.deletePacient());
        btnClear.addActionListener(e -> {
            clearFields();
            clearPacientDetails();
            clearConsultatieFields();
            showConsultatii(new ArrayList<>());
            tablePacienti.clearSelection();
        });
        btnSearch.addActionListener(e -> controller.searchPacientiByName());

        btnFilter.addActionListener(e -> controller.filterPacienti());
        btnShowAllPacienti.addActionListener(e -> {
            txtSearch.setText("");
            txtDiagnosticFilter.setText("");
            txtTratamentFilter.setText("");
            controller.loadAllPacienti();
        });

        btnAddConsultatie.addActionListener(e -> controller.addConsultatie());
        btnDeleteConsultatie.addActionListener(e -> controller.deleteConsultatie());

        tableConsultatii.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showSelectedConsultatieDetails();
                }
            }
        });

        tablePacienti.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFieldsFromSelectedRow();
            }
        });
    }

    public void clearPacientDetails() {
        lblDetNumeValue.setText("-");
        lblDetPrenumeValue.setText("-");
        lblDetSexValue.setText("-");
        lblDetInaltimeValue.setText("-");
        lblDetGreutateValue.setText("-");
        lblDetCnpValue.setText("-");
        lblDetDataNasteriiValue.setText("-");
        lblDetTelefonValue.setText("-");
        lblDetAdresaValue.setText("-");
    }

    private String value(String s) {
        return (s == null || s.isBlank()) ? "-" : s;
    }

    @Override
    public void showPacientDetails(Pacient pacient) {
        if (pacient == null) {
            clearPacientDetails();
            return;
        }

        lblDetNumeValue.setText(value(pacient.getNume()));
        lblDetPrenumeValue.setText(value(pacient.getPrenume()));
        lblDetSexValue.setText(pacient.getSex() != null ? pacient.getSex().toString() : "-");

        lblDetInaltimeValue.setText(String.valueOf(pacient.getInaltime()));
        lblDetGreutateValue.setText(String.valueOf(pacient.getGreutate()));
        lblDetCnpValue.setText(value(pacient.getCnp()));
        lblDetDataNasteriiValue.setText(
                pacient.getDataNasterii() != null ? pacient.getDataNasterii().toString() : "-"
        );
        lblDetTelefonValue.setText(value(pacient.getTelefon()));
        lblDetAdresaValue.setText(value(pacient.getAdresa()));
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = tablePacienti.getSelectedRow();
        if (selectedRow == -1) {
            clearPacientDetails();
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        controller.onPacientSelected(id);
    }

    private JPanel createDetailArea(String labelText, String value) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 25));

        JTextArea textArea = new JTextArea(value);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setOpaque(false);

        panel.add(label, BorderLayout.NORTH);
        panel.add(textArea, BorderLayout.CENTER);

        return panel;
    }

    private String valueOrDash(Object value) {
        return value != null ? value.toString() : "-";
    }

    private void showSelectedConsultatieDetails() {
        int selectedRow = tableConsultatii.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        Object id = consultatiiTableModel.getValueAt(selectedRow, 0);
        Object data = consultatiiTableModel.getValueAt(selectedRow, 1);
        Object simptome = consultatiiTableModel.getValueAt(selectedRow, 2);
        Object diagnostic = consultatiiTableModel.getValueAt(selectedRow, 3);
        Object tratament = consultatiiTableModel.getValueAt(selectedRow, 4);
        Object observatii = consultatiiTableModel.getValueAt(selectedRow, 5);

        JDialog dialog = new JDialog(this, "Detalii consultatie", true);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailsPanel.add(createDetailArea("Data:", valueOrDash(data)));
        detailsPanel.add(createDetailArea("Simptome:", valueOrDash(simptome)));
        detailsPanel.add(createDetailArea("Diagnostic:", valueOrDash(diagnostic)));
        detailsPanel.add(createDetailArea("Tratament:", valueOrDash(tratament)));
        detailsPanel.add(createDetailArea("Observatii:", valueOrDash(observatii)));

        JButton btnClose = new JButton("Inchide");
        btnClose.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnClose);

        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setBorder(null);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    public PacientFormData getPacientFormData() {
        return new PacientFormData(
                txtNume.getText().trim(),
                txtPrenume.getText().trim(),
                (Sex) cmbSex.getSelectedItem(),
                txtInaltime.getText().trim(),
                txtGreutate.getText().trim(),
                txtCnp.getText().trim(),
                txtDataNasterii.getText().trim(),
                txtTelefon.getText().trim(),
                txtAdresa.getText().trim()
        );
    }

    @Override
    public void populatePacientForm(PacientFormData data) {
        txtNume.setText(data.nume);
        txtPrenume.setText(data.prenume);
        cmbSex.setSelectedItem(data.sex);
        txtInaltime.setText(data.inaltime);
        txtGreutate.setText(data.greutate);
        txtCnp.setText(data.cnp);
        txtDataNasterii.setText(data.dataNasterii);
        txtTelefon.setText(data.telefon);
        txtAdresa.setText(data.adresa);
    }

    @Override
    public ConsultatieFormData getConsultatieFormData() {
        return new ConsultatieFormData(
                txtDataConsultatiei.getText().trim(),
                txtSimptome.getText().trim(),
                txtDiagnostic.getText().trim(),
                txtTratament.getText().trim(),
                txtObservatii.getText().trim()
        );
    }

    @Override
    public String getSearchText() {
        return txtSearch.getText().trim();
    }

    @Override
    public int getSelectedPacientId() {
        int selectedRow = tablePacienti.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (int) tableModel.getValueAt(selectedRow, 0);
    }

    @Override
    public void showPacienti(List<Pacient> pacienti) {
        tableModel.setRowCount(0);

        for (Pacient p : pacienti) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNume() + " " + p.getPrenume()
            });
        }
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void clearFields() {
        txtNume.setText("");
        txtPrenume.setText("");
        cmbSex.setSelectedIndex(0);
        txtInaltime.setText("");
        txtGreutate.setText("");
        txtCnp.setText("");
        txtDataNasterii.setText("");
        txtTelefon.setText("");
        txtAdresa.setText("");
    }

    @Override
    public int getSelectedConsultatieId() {
        int selectedRow = tableConsultatii.getSelectedRow();
        if (selectedRow == -1) {
            return -1;
        }
        return (int) consultatiiTableModel.getValueAt(selectedRow, 0);
    }

    @Override
    public void showConsultatii(List<Consultatie> consultatii) {
        consultatiiTableModel.setRowCount(0);

        for (org.example.model.Consultatie consultatie : consultatii) {
            consultatiiTableModel.addRow(new Object[]{
                    consultatie.getId(),
                    consultatie.getDataConsultatiei(),
                    consultatie.getSimptome(),
                    consultatie.getDiagnostic(),
                    consultatie.getTratament(),
                    consultatie.getObservatii()
            });
        }
    }

    @Override
    public void clearConsultatieFields() {
        txtDataConsultatiei.setText("");
        txtSimptome.setText("");
        txtDiagnostic.setText("");
        txtTratament.setText("");
        txtObservatii.setText("");
    }

    @Override
    public String getDiagnosticFilterText() {
        return txtDiagnosticFilter.getText().trim();
    }

    @Override
    public String getTratamentFilterText() {
        return txtTratamentFilter.getText().trim();
    }

    @Override
    public void update() {
        showPacienti(model.getPacienti());
    }
}
