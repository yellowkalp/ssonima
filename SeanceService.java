/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.seanceDAO;
import java.sql.SQLException;
import modele.seance;
import java.time.LocalDate;
import java.util.List;

public class SeanceService {
    private seanceDAO seanceDAO = new seanceDAO();

    // CRUD
    public void addSeance(seance seance) throws SQLException {
        seanceDAO.create(seance);
    }

    public seance getSeanceById(int id) throws SQLException {
        return seanceDAO.findById(id);
    }

    public void updateSeance(seance seance) throws SQLException {
        seanceDAO.update(seance);
    }

    public void deleteSeance(int id) throws SQLException {
        seanceDAO.delete(id);
    }

    // Méthodes supplémentaires
    public List<seance> findByFilm(int filmId) {
        return seanceDAO.findByFilm(filmId);
    }

    public List<seance> findByDate(LocalDate date) {
        return seanceDAO.findByDate(date);
    }

    public List<seance> findBySalle(int salleId) {
        return seanceDAO.findBySalle(salleId);
    }

    public int countByFilm(int filmId) {
        return seanceDAO.countByFilm(filmId);
    }
}


