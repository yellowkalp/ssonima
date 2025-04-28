/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

import java.sql.*;
import java.util.*;
import modele.film;
import util.DatabaseConnection;

public class FilmDao {

    public static void create(film film) {
        String sql = "INSERT INTO film (titre, description, genre, duree) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, film.getTitre());
            stmt.setString(2, film.getDescription());
            stmt.setString(3, film.getGenre());
            stmt.setInt(4, film.getDureeMinutes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(film film) {
        String sql = "UPDATE film SET titre=?, description=?, genre=?, duree=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, film.getTitre());
            stmt.setString(2, film.getDescription());
            stmt.setString(3, film.getGenre());
            stmt.setInt(4, film.getDureeMinutes());
            stmt.setInt(5, film.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM film WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static film findById(int id) {
        String sql = "SELECT * FROM film WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new film(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("genre"),
                    rs.getInt("duree")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<film> lister() {
        List<film> films = new ArrayList<>();
        String sql = "SELECT * FROM film";
        try (Connection conn = DatabaseConnection.getConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                films.add(new film(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("genre"),
                    rs.getInt("duree")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }
    public film findByTitle(String title) {
    film film = null;
    String query = "SELECT * FROM film WHERE titre = ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, title);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                film = new film(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("genre"),
                    rs.getInt("duree")
                );
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return film;
}
    public List<film> lister(int offset, int limit) {
    List<film> films = new ArrayList<>();
    String query = "SELECT * FROM film LIMIT ? OFFSET ?";
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, limit);
        stmt.setInt(2, offset);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                film film = new film(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("description"),
                    rs.getString("genre"),
                    rs.getInt("duree")
                );
                films.add(film);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return films;
}
    public int count() {
    int total = 0;
    String sql = "SELECT COUNT(*) FROM film";
    
    try (Connection conn = DatabaseConnection.getConnexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return total;
}
}
