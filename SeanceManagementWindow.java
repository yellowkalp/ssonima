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

public class SeanceManagementWindow extends JFrame {

    private JTable seanceTable;
    private JButton addButton, editButton, deleteButton, refreshButton;

    public SeanceManagementWindow() {
        setTitle("Gestion des Séances");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        seanceTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(seanceTable);

        // Boutons
        addButton = new JButton("Ajouter");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");
        refreshButton = new JButton("Rafraîchir");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Layout
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Charger données
        loadSeanceData();

        // Actions
        addButton.addActionListener(e -> addSeance());
        editButton.addActionListener(e -> editSeance());
        deleteButton.addActionListener(e -> deleteSeance());
        refreshButton.addActionListener(e -> loadSeanceData());
    }

    private void loadSeanceData() {
        try (Connection conn = DatabaseConnection.getConnexion();) {
            String query = "SELECT * FROM seance";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "ID Film", "Date Heure", "ID Salle"}, 0);
            while (rs.next()) {
                int id = rs.getInt("id");
                int filmId = rs.getInt("film_id");
                Timestamp dateHeure = rs.getTimestamp("dateheure");
                int salleId = rs.getInt("salle_id");
                model.addRow(new Object[]{id, filmId, dateHeure, salleId});
            }
            seanceTable.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des séances : " + e.getMessage());
        }
    }

    private void addSeance() {
        String filmId = JOptionPane.showInputDialog(this, "ID du film :");
        String dateHeure = JOptionPane.showInputDialog(this, "Date et heure (YYYY-MM-DD HH:MM:SS) :");
        String salleId = JOptionPane.showInputDialog(this, "ID de la salle :");

        try (Connection conn = DatabaseConnection.getConnexion();) {
            String query = "INSERT INTO seance (film_id, dateheure, salle_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(filmId));
            stmt.setTimestamp(2, Timestamp.valueOf(dateHeure));
            stmt.setInt(3, Integer.parseInt(salleId));
            stmt.executeUpdate();
            loadSeanceData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la séance : " + e.getMessage());
        }
    }

    private void editSeance() {
        int selectedRow = seanceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une séance à modifier.");
            return;
        }

        int seanceId = (int) seanceTable.getValueAt(selectedRow, 0);
        String newDateHeure = JOptionPane.showInputDialog(this, "Nouvelle date et heure (YYYY-MM-DD HH:MM:SS) :");
        if (newDateHeure == null || newDateHeure.trim().isEmpty()) return;

        try (Connection conn = DatabaseConnection.getConnexion();) {
            String query = "UPDATE seance SET dateheure = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, Timestamp.valueOf(newDateHeure));
            stmt.setInt(2, seanceId);
            stmt.executeUpdate();
            loadSeanceData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de la séance : " + e.getMessage());
        }
    }

    private void deleteSeance() {
        int selectedRow = seanceTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une séance à supprimer.");
            return;
        }

        int seanceId = (int) seanceTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cette séance ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DatabaseConnection.getConnexion()) {
            String query = "DELETE FROM seance WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, seanceId);
            stmt.executeUpdate();
            loadSeanceData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de la séance : " + e.getMessage());
        }
    }
}
