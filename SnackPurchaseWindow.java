/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import service.SnackService;
import service.VenteSnackService;
import modele.snack;
import modele.VenteSnack;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SnackPurchaseWindow extends JFrame {

    private JTable snackTable;
    private JButton purchaseButton;

    private SnackService snackService = new SnackService();
    private VenteSnackService venteSnackService = new VenteSnackService();

    public SnackPurchaseWindow() {
        setTitle("Achat de Snacks");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        snackTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(snackTable);

        // Bouton
        purchaseButton = new JButton("Acheter");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(purchaseButton);

        // Layout
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Charger snacks
        loadSnacks();

        // Action d'achat

        purchaseButton.addActionListener(e -> {
            try {
                purchaseSnack();
            } catch (SQLException ex) {
                Logger.getLogger(SnackPurchaseWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        setVisible(true);
    }

    private void loadSnacks() {
        List<snack> list = snackService.searchByName("");
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nom", "Prix"}, 0);
        for (snack s : list) {
            model.addRow(new Object[]{s.getId(), s.getNom(), s.getPrix()});
        }
        snackTable.setModel(model);
    }

    private void purchaseSnack() throws SQLException {
        int row = snackTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un snack.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int snackId = (int) snackTable.getValueAt(row, 0);
        snack snack = snackService.getSnackById(snackId); // suppose que tu as cette méthode
        String qtyStr = JOptionPane.showInputDialog(this, "Quantité :", "1");
        if (qtyStr == null) return;
        try {
            int quantite = Integer.parseInt(qtyStr);
            double total = quantite * snack.getPrix();
            VenteSnack vente = new VenteSnack(0, snack, quantite, total, LocalDateTime.now());
            venteSnackService.addVenteSnack(vente);
            JOptionPane.showMessageDialog(this, "Snack acheté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantité invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
