/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import util.DatabaseConnection;
import modele.VenteSnack;
import dao.snackDAO;
import modele.snack;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class VenteSnackDAO {
    public static void create(VenteSnack v) throws SQLException {
        String sql = "INSERT INTO vente_snack (snack_id, quantite, total, date_vente) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, v.getSnack().getId());
            ps.setInt(2, v.getQuantite());
            ps.setDouble(3, v.getTotal());
            ps.setTimestamp(4, Timestamp.valueOf(v.getDateVente()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) v.setId(rs.getInt(1));
        }
    }

    public static VenteSnack findById(int id) throws SQLException {
        String sql = "SELECT * FROM vente_snack WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    snack s = snackDAO.findById(rs.getInt("snack_id"));
                    return new VenteSnack(
                        rs.getInt("id"), s,
                        rs.getInt("quantite"),
                        rs.getDouble("total"),
                        rs.getTimestamp("date_vente").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public static List<VenteSnack> lister() throws SQLException {
        List<VenteSnack> list = new ArrayList<>();
        String sql = "SELECT * FROM vente_snack";
        try (Connection conn = DatabaseConnection.getConnexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                snack s = snackDAO.findById(rs.getInt("snack_id"));
                list.add(new VenteSnack(
                    rs.getInt("id"), s,
                    rs.getInt("quantite"), rs.getDouble("total"),
                    rs.getTimestamp("date_vente").toLocalDateTime()
                ));
            }
        }
        return list;
    }

    public static void update(VenteSnack v) throws SQLException {
        String sql = "UPDATE vente_snack SET snack_id=?, quantite=?, total=?, date_vente=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, v.getSnack().getId());
            ps.setInt(2, v.getQuantite());
            ps.setDouble(3, v.getTotal());
            ps.setTimestamp(4, Timestamp.valueOf(v.getDateVente()));
            ps.setInt(5, v.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM vente_snack WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public List<VenteSnack> findByDate(LocalDate date) {
    List<VenteSnack> ventes = new ArrayList<>();
    String query = "SELECT * FROM vente_snack WHERE DATE(date_vente) = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setDate(1, java.sql.Date.valueOf(date));

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // 1) Récupérer l'objet Snack associé
                snack snack = snackDAO.findById(rs.getInt("snack_id"));

                // 2) Construire l'objet VenteSnack avec le Snack
                VenteSnack vente = new VenteSnack(
                    rs.getInt("id"),
                    snack,
                    rs.getInt("quantite"),
                    rs.getDouble("total"),
                    rs.getTimestamp("date_vente").toLocalDateTime()
                );
                ventes.add(vente);
            }
            
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return ventes;
}
public double totalSalesBySnack(int snackId) {
    String query = "SELECT SUM(total) FROM vente_snack WHERE snack_id = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, snackId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
// VenteSnackDAO.java

public double totalSalesByDate(LocalDate date) {
    double total = 0;
    String sql = "SELECT SUM(total) FROM vente_snack WHERE DATE(date_vente) = ?";
    
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setDate(1, java.sql.Date.valueOf(date));
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return total;
}

}
