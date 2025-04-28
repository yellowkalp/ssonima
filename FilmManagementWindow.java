/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
import util.DatabaseConnection;

public class FilmManagementWindow extends JFrame {

    private JTable filmTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, directorField, genreField;
    private JButton addButton, updateButton, deleteButton;

    public FilmManagementWindow() {
        setTitle("Gestion des Films");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout de la fenêtre
        setLayout(new BorderLayout());

        // Créer le modèle de table pour afficher les films
        String[] columns = {"ID", "Titre", "Réalisateur", "Genre"};
        tableModel = new DefaultTableModel(columns, 0);
        filmTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(filmTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panneau pour ajouter/modifier des films
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Titre :"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Réalisateur :"));
        directorField = new JTextField();
        inputPanel.add(directorField);

        inputPanel.add(new JLabel("Genre :"));
        genreField = new JTextField();
        inputPanel.add(genreField);

        add(inputPanel, BorderLayout.NORTH);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Ajouter");
        updateButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ajouter des actions aux boutons
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFilm();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFilm();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFilm();
            }
        });

        loadFilms();  // Charger les films existants de la base de données
        setVisible(true);
    }

    // Ajouter un film à la base de données
    private void addFilm() {
        String title = titleField.getText();
        String director = directorField.getText();
        String genre = genreField.getText();

        try {
            Connection conn = DatabaseConnection.getConnexion();
            String sql = "INSERT INTO film (titre, realisateur, genre) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, director);
            stmt.setString(3, genre);
            stmt.executeUpdate();
            loadFilms();  // Recharger les films
            JOptionPane.showMessageDialog(this, "Film ajouté avec succès!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du film", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Modifier un film dans la base de données
    private void updateFilm() {
        int selectedRow = filmTable.getSelectedRow();
        if (selectedRow != -1) {
            int filmId = (int) tableModel.getValueAt(selectedRow, 0);
            String title = titleField.getText();
            String director = directorField.getText();
            String genre = genreField.getText();

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nom_de_ta_base", "username", "password");
                String sql = "UPDATE film SET titre = ?, realisateur = ?, genre = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, director);
                stmt.setString(3, genre);
                stmt.setInt(4, filmId);
                stmt.executeUpdate();
                loadFilms();  // Recharger les films
                JOptionPane.showMessageDialog(this, "Film mis à jour avec succès!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du film", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Supprimer un film de la base de données
    private void deleteFilm() {
        int selectedRow = filmTable.getSelectedRow();
        if (selectedRow != -1) {
            int filmId = (int) tableModel.getValueAt(selectedRow, 0);

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nom_de_ta_base", "username", "password");
                String sql = "DELETE FROM film WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, filmId);
                stmt.executeUpdate();
                loadFilms();  // Recharger les films
                JOptionPane.showMessageDialog(this, "Film supprimé avec succès!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du film", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Charger les films de la base de données et afficher dans le tableau
    private void loadFilms() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nom_de_ta_base", "username", "password");
            String sql = "SELECT * FROM film";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            tableModel.setRowCount(0);  // Vider le tableau avant de le remplir

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("titre");
                String director = rs.getString("realisateur");
                String genre = rs.getString("genre");
                tableModel.addRow(new Object[]{id, title, director, genre});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new FilmManagementWindow();
    }
}
  

