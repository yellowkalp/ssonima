/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import util.DatabaseConnection;
import modele.snack;
import java.sql.*;
import java.util.*;

public class snackDAO {
    public static void create(snack s) throws SQLException {
        String sql = "INSERT INTO snack (nom, prix) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNom());
            ps.setDouble(2, s.getPrix());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) s.setId(rs.getInt(1));
        }
    }

    public static snack findById(int id) throws SQLException {
        String sql = "SELECT * FROM snack WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new snack(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"));
            }
        }
        return null;
    }

    public static List<snack> lister() throws SQLException {
        List<snack> list = new ArrayList<>();
        String sql = "SELECT * FROM snack";
        try (Connection conn = DatabaseConnection.getConnexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(new snack(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix")));
        }
        return list;
    }

    public static void update(snack s) throws SQLException {
        String sql = "UPDATE snack SET nom=?, prix=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNom());
            ps.setDouble(2, s.getPrix());
            ps.setInt(3, s.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM snack WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public List<snack> findUnderPrice(double maxPrice) {
    List<snack> snacks = new ArrayList<>();
    String query = "SELECT * FROM snack WHERE prix <= ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setDouble(1, maxPrice);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                snack snack = new snack(rs.getInt("id"), rs.getString("nom"), rs.getDouble("prix"));
                snacks.add(snack);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return snacks;
}
public List<snack> searchByName(String keyword) {
    List<snack> list = new ArrayList<>();
    String sql = "SELECT * FROM snack WHERE nom LIKE ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%");
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new snack(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getDouble("prix")
                ));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
}
