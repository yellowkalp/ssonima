/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import util.DatabaseConnection;

public class FilmListWindow extends JFrame {

    private JTable filmTable;
    private DefaultTableModel tableModel;

    public FilmListWindow() {
        setTitle("Liste des Films");
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

        loadFilms();  // Charger les films de la base de données
        setVisible(true);
    }

    // Charger les films de la base de données et afficher dans le tableau
    private void loadFilms() {
        try {
            Connection conn = DatabaseConnection.getConnexion();
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
        new FilmListWindow();
    }
}

