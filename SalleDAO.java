/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import modele.salle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class SalleDAO {

    public static void create(salle salle) throws SQLException {
        Connection conn = DatabaseConnection.getConnexion();
        String sql = "INSERT INTO salle (nom, capacite) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, salle.getNom());
        ps.setInt(2, salle.getCapacite());
        ps.executeUpdate();
    }

    public static salle findById(int id) throws SQLException {
        Connection conn = DatabaseConnection.getConnexion();
        String sql = "SELECT * FROM salle WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new salle(rs.getInt("id"), rs.getString("nom"), rs.getInt("capacite"));
        }
        return null;
    }

    public static List<salle> lister() throws SQLException {
        Connection conn = DatabaseConnection.getConnexion();
        String sql = "SELECT * FROM salle";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<salle> salles = new ArrayList<>();
        while (rs.next()) {
            salles.add(new salle(rs.getInt("id"), rs.getString("nom"), rs.getInt("capacite")));
        }
        return salles;
    }

    public static void update(salle salle) throws SQLException {
        Connection conn = DatabaseConnection.getConnexion();
        String sql = "UPDATE salle SET nom = ?, capacite = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, salle.getNom());
        ps.setInt(2, salle.getCapacite());
        ps.setInt(3, salle.getId());
        ps.executeUpdate();
    }

    public static void delete(int id) throws SQLException {
        Connection conn = DatabaseConnection.getConnexion();
        String sql = "DELETE FROM salle WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    public List<salle> findByCapaciteMin(int minPlaces) {
    List<salle> salles = new ArrayList<>();
    String sql = "SELECT * FROM salle WHERE capacite >= ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, minPlaces);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                salle salle = new salle(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getInt("capacite")
                );
                salles.add(salle);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return salles;
}
    public int count() {
    String sql = "SELECT COUNT(*) FROM salle";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1); // Première colonne : le COUNT(*)
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
}

