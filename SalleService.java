/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.SalleDAO;
import java.sql.SQLException;
import modele.salle;
import java.util.List;

public class SalleService {
    private SalleDAO salleDAO = new SalleDAO();

    // CRUD
    public void addSalle(salle salle) throws SQLException {
        salleDAO.create(salle);
    }

    public salle getSalleById(int id) throws SQLException {
        return salleDAO.findById(id);
    }

    public void updateSalle(salle salle) throws SQLException {
        salleDAO.update(salle);
    }

    public void deleteSalle(int id) throws SQLException {
        salleDAO.delete(id);
    }

    // Méthodes supplémentaires
    public List<salle> findByCapaciteMin(int minPlaces) {
        return salleDAO.findByCapaciteMin(minPlaces);
    }

    public int countSalles() {
        return salleDAO.count();
    }
}

