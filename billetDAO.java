/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import util.DatabaseConnection;
import modele.billet;
import dao.seanceDAO;
import modele.seance;
import java.sql.*;
import java.util.*;

public class billetDAO {
    public static void create(billet b) throws SQLException {
        String sql = "INSERT INTO billet (seance_id, numeroPlace, prix) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getSeance().getId());
            ps.setInt(2, b.getNumeroPlace());
            ps.setDouble(3, b.getPrix());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) b.setId(rs.getInt(1));
        }
    }

    public static billet findById(int id) throws SQLException {
        String sql = "SELECT * FROM billet WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    seance s = seanceDAO.findById(rs.getInt("seance_id"));
                    return new billet(
                        rs.getInt("id"), s,
                        rs.getInt("numeroPlace"), rs.getDouble("prix")
                    );
                }
            }
        }
        return null;
    }

    public static List<billet> lister() throws SQLException {
        List<billet> list = new ArrayList<>();
        String sql = "SELECT * FROM billet";
        try (Connection conn = DatabaseConnection.getConnexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                seance s = seanceDAO.findById(rs.getInt("seance_id"));
                list.add(new billet(
                    rs.getInt("id"), s,
                    rs.getInt("numeroPlace"), rs.getDouble("prix")
                ));
            }
        }
        return list;
    }

    public static void update(billet b) throws SQLException {
        String sql = "UPDATE billet SET seance_id=?, numeroPlace=?, prix=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, b.getSeance().getId());
            ps.setInt(2, b.getNumeroPlace());
            ps.setDouble(3, b.getPrix());
            ps.setInt(4, b.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM billet WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
public List<billet> findBySeance(int seanceId) {
    List<billet> billets = new ArrayList<>();
    String query = "SELECT * FROM billet WHERE seance_id = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, seanceId);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
               // Récupérer la séance correspondante
               seance seance = seanceDAO.findById(rs.getInt("seance_id"));
    
    // Créer un objet Billet
               billet billet;
                billet = new billet(
                        rs.getInt("id"),
                        seance,  // Ici, nous passons l'objet Seance
                        rs.getInt("numeroPlace"),
                        rs.getDouble("prix") 
                );
                billets.add(billet);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return billets;
}
public int countBySeance(int seanceId) {
    String query = "SELECT COUNT(*) FROM billet WHERE seance_id = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, seanceId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
public boolean isPlaceTaken(int seanceId, int numeroPlace) {
    String query = "SELECT COUNT(*) FROM billet WHERE seance_id = ? AND numeroPlace = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, seanceId);
        stmt.setInt(2, numeroPlace);
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
// BilletDAO.java

public double totalSalesBySeance(int seanceId) {
    double total = 0;
    String sql = "SELECT SUM(prix) FROM billet WHERE seance_id = ?";
    
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, seanceId);
        
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
// BilletDAO.java

public int countTicketsBySeance(int seanceId) {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM billet WHERE seance_id = ?";
    
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, seanceId);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return count;
}

}
