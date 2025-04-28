/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import util.DatabaseConnection;
import modele.seance;
import modele.film;
import modele.salle;
import dao.FilmDao;
import dao.SalleDAO;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class seanceDAO {
    public static void create(seance seance) throws SQLException {
        String sql = "INSERT INTO seance (film_id, salle_id, dateHeure) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, seance.getFilm().getId());
            ps.setInt(2, seance.getsalle().getId());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDateHeure()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) seance.setId(rs.getInt(1));
        }
    }

    public static seance findById(int id) throws SQLException {
        String sql = "SELECT * FROM seance WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    film film = FilmDao.findById(rs.getInt("film_id"));
                    salle salle = SalleDAO.findById(rs.getInt("salle_id"));
                    return new seance(
                        rs.getInt("id"),
                        film,
                        salle,
                        rs.getTimestamp("dateHeure").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    public static List<seance> lister() throws SQLException {
        List<seance> list = new ArrayList<>();
        String sql = "SELECT * FROM seance";
        try (Connection conn = DatabaseConnection.getConnexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                film film = FilmDao.findById(rs.getInt("film_id"));
                salle salle = SalleDAO.findById(rs.getInt("salle_id"));
                list.add(new seance(
                    rs.getInt("id"), film, salle,
                    rs.getTimestamp("dateHeure").toLocalDateTime()
                ));
            }
        }
        return list;
    }

    public static void update(seance seance) throws SQLException {
        String sql = "UPDATE seance SET film_id=?, salle_id=?, dateHeure=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, seance.getFilm().getId());
            ps.setInt(2, seance.getsalle().getId());
            ps.setTimestamp(3, Timestamp.valueOf(seance.getDateHeure()));
            ps.setInt(4, seance.getId());
            ps.executeUpdate();
        }
    }

    public static void delete(int id) throws SQLException {
        String sql = "DELETE FROM seance WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
public List<seance> findByFilm(int filmId) {
    List<seance> seances = new ArrayList<>();
    String sql = "SELECT * FROM seance WHERE film_id = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, filmId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // Récupérer l'objet Film et Salle
                film film = FilmDao.findById(rs.getInt("film_id"));
                salle salle = SalleDAO.findById(rs.getInt("salle_id"));
                // Passer ces objets au constructeur
                seance seance = new seance(
                    rs.getInt("id"),
                    film,
                    salle,
                    rs.getTimestamp("dateHeure").toLocalDateTime()
                );
                seances.add(seance);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return seances;
}

public List<seance> findByDate(LocalDate date) {
    List<seance> seances = new ArrayList<>();
    String sql = "SELECT * FROM seance WHERE DATE(dateHeure) = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setDate(1, java.sql.Date.valueOf(date));
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                film film = FilmDao.findById(rs.getInt("film_id"));
                salle salle = SalleDAO.findById(rs.getInt("salle_id"));
                seance seance = new seance(
                    rs.getInt("id"),
                    film,
                    salle,
                    rs.getTimestamp("dateHeure").toLocalDateTime()
                );
                seances.add(seance);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return seances;
}
public List<seance> findBySalle(int salleId) {
    List<seance> seances = new ArrayList<>();
    String query = "SELECT * FROM seance WHERE salle_id = ?";
    
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, salleId);
        
       try (ResultSet rs = stmt.executeQuery()) {
    while (rs.next()) {
        film film = new film(rs.getInt("film_id"));    // on crée un Film minimal avec juste l'id
        salle salle = new salle(rs.getInt("salle_id")); // pareil pour la Salle
        
        seance seance = new seance(
            rs.getInt("id"),
            film,
            salle,
            rs.getTimestamp("dateHeure").toLocalDateTime()
        );
        seances.add(seance);
    }
}

        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return seances;
}
public int countByFilm(int filmId) {
    int count = 0;
    String query = "SELECT COUNT(*) FROM seance WHERE film_id = ?";
    
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, filmId);
        
        try (ResultSet rs = stmt.executeQuery()) {
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


