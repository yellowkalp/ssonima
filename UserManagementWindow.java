/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import util.DatabaseConnection;

public class UserManagementWindow extends JFrame {

    private JTable userTable;
    private JButton addButton, editButton, deleteButton, refreshButton;

    public UserManagementWindow() {
        setTitle("Gestion des Utilisateurs");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);

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
        loadUserData();

        // Actions
        addButton.addActionListener(e -> addUser());
        editButton.addActionListener(e -> editUser());
        deleteButton.addActionListener(e -> deleteUser());
        refreshButton.addActionListener(e -> loadUserData());
    }

    private void loadUserData() {
        try (Connection conn = DatabaseConnection.getConnexion()) {
            String query = "SELECT * FROM user";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nom", "Mot de passe", "Rôle"}, 0);
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                model.addRow(new Object[]{id, username, password, role});
            }
            userTable.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    private void addUser() {
        String username = JOptionPane.showInputDialog(this, "Nom d'utilisateur :");
        String password = JOptionPane.showInputDialog(this, "Mot de passe :");
        String role = JOptionPane.showInputDialog(this, "Rôle (admin/cashier/client) :");

        if (username == null || password == null || role == null) return;

        try (Connection conn = DatabaseConnection.getConnexion()) {
            String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();
            loadUserData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    private void editUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à modifier.");
            return;
        }

        int userId = (int) userTable.getValueAt(selectedRow, 0);
        String newPassword = JOptionPane.showInputDialog(this, "Nouveau mot de passe :");

        if (newPassword == null || newPassword.trim().isEmpty()) return;

        try (Connection conn = DatabaseConnection.getConnexion()) {
            String query = "UPDATE user SET password = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            loadUserData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un utilisateur à supprimer.");
            return;
        }

        int userId = (int) userTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet utilisateur ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DatabaseConnection.getConnexion()) {
            String query = "DELETE FROM user WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            loadUserData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }
}
