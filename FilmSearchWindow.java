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

public class FilmSearchWindow extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JTable filmsTable;

    public FilmSearchWindow() {
        setTitle("Recherche de Films");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Barre de recherche
        searchField = new JTextField(30);
        searchButton = new JButton("Rechercher");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Titre du film: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Table des films
        filmsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(filmsTable);

        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Action
        searchButton.addActionListener(e -> searchFilms());

        // Charger tous les films au démarrage
        searchFilms();
    }

    private void searchFilms() {
        String keyword = searchField.getText().trim();

        try (Connection conn = DatabaseConnection.getConnexion()) {
            String sql = "SELECT * FROM film WHERE title LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Titre", "Durée", "Description"}, 0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("duration"),
                        rs.getString("description")
                });
            }

            filmsTable.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche : " + e.getMessage());
        }
    }
}
