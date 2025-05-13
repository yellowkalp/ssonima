/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import modele.utilisateur;
import java.sql.*;
import java.util.*;
import util.DatabaseConnection;

public class utilisateurDAO {
    public static void create(utilisateur u) throws SQLException {
        String sql = "INSERT INTO utilisateur (nom_utilisateur, mot_de_passe, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNomUtilisateur());
            ps.setString(2, u.getMotDePasse());
            ps.setString(3, u.getRole());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) u.setId(rs.getInt(1));
        }
    }

    public static utilisateur findById(int id) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                    );
                }
            }
        }
        return null;
    }

    public static utilisateur findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM utilisateur WHERE nom_utilisateur = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                    );
                }
            }
        }
        return null;
    }

    public static List<utilisateur> lister() throws SQLException {
        List<utilisateur> list = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (Connection conn = DatabaseConnection.getConnexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom_utilisateur"),
                    rs.getString("mot_de_passe"),
                    rs.getString("role")
                ));
            }
        }
        return list;
    }

    public static void update(utilisateur u) throws SQLException {
        String sql = "UPDATE utilisateur SET nom_utilisateur=?, mot_de_passe=?, role=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNomUtilisateur());
            ps.setString(2, u.getMotDePasse());
            ps.setString(3, u.getRole());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM utilisateur WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public utilisateur authenticate(String username, String password) {
    String query = "SELECT * FROM utilisateur WHERE nom_utilisateur = ? AND mot_de_passe = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new utilisateur(rs.getInt("id"), rs.getString("nom_utilisateur"), rs.getString("mot_de_passe"), rs.getString("role"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
   public List<utilisateur> findByRole(String role) {
    List<utilisateur> utilisateurs = new ArrayList<>();
    String query = "SELECT * FROM utilisateur WHERE role = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, role);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                utilisateur utilisateur = new utilisateur(rs.getInt("id"), rs.getString("nom_utilisateur"), rs.getString("mot_de_passe"), rs.getString("role"));
                utilisateurs.add(utilisateur);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return utilisateurs;
}
   public boolean existsByUsername(String username) {
    String query = "SELECT COUNT(*) FROM utilisateur WHERE nom_utilisateur = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, username);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}
