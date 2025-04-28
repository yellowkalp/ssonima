/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import util.DatabaseConnection;

public class SalesTrackingWindow extends JFrame {

    private JTable salesTable;
    private JButton refreshButton;

    public SalesTrackingWindow() {
        setTitle("Suivi des Ventes");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        salesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(salesTable);

        // Bouton
        refreshButton = new JButton("Rafraîchir");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);

        // Layout
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Charger données
        loadSalesData();

        // Action
        refreshButton.addActionListener(e -> loadSalesData());
    }

    private void loadSalesData() {
        try (Connection conn = DatabaseConnection.getConnexion()) {
            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Type", "Nom", "Quantité", "Prix total", "Date", "Client"
            }, 0);

            // Charger ventes de billets
            String ticketSalesQuery = "SELECT 'Billet' AS type, f.title AS name, b.quantity, b.total_price, b.sale_date, u.username " +
                                      "FROM billetsales b " +
                                      "JOIN seance s ON b.seance_id = s.id " +
                                      "JOIN film f ON s.film_id = f.id " +
                                      "JOIN user u ON b.client_id = u.id";
            PreparedStatement stmt1 = conn.prepareStatement(ticketSalesQuery);
            ResultSet rs1 = stmt1.executeQuery();

            while (rs1.next()) {
                model.addRow(new Object[]{
                        rs1.getString("type"),
                        rs1.getString("name"),
                        rs1.getInt("quantity"),
                        rs1.getDouble("total_price"),
                        rs1.getDate("sale_date"),
                        rs1.getString("username")
                });
            }

            // Charger ventes de snacks
            String snackSalesQuery = "SELECT 'Snack' AS type, s.name AS name, vs.quantity, vs.total_price, vs.sale_date, u.username " +
                                     "FROM ventesnack vs " +
                                     "JOIN snack s ON vs.snack_id = s.id " +
                                     "JOIN user u ON vs.client_id = u.id";
            PreparedStatement stmt2 = conn.prepareStatement(snackSalesQuery);
            ResultSet rs2 = stmt2.executeQuery();

            while (rs2.next()) {
                model.addRow(new Object[]{
                        rs2.getString("type"),
                        rs2.getString("name"),
                        rs2.getInt("quantity"),
                        rs2.getDouble("total_price"),
                        rs2.getDate("sale_date"),
                        rs2.getString("username")
                });
            }

            salesTable.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des ventes : " + e.getMessage());
        }
    }
}
