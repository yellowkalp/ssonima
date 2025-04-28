/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.utilisateurDAO;
import java.sql.SQLException;
import modele.utilisateur;
import java.util.List;

public class UtilisateurService {
    private utilisateurDAO utilisateurDAO = new utilisateurDAO();

    // CRUD
    public void addUtilisateur(utilisateur utilisateur) throws SQLException {
        utilisateurDAO.create(utilisateur);
    }

    public utilisateur getUtilisateurById(int id) throws SQLException {
        return utilisateurDAO.findById(id);
    }

    public void updateUtilisateur(utilisateur utilisateur) throws SQLException {
        utilisateurDAO.update(utilisateur);
    }

    public void deleteUtilisateur(int id) throws SQLException {
        utilisateurDAO.delete(id);
    }

    // Méthodes supplémentaires
    public utilisateur authenticate(String username, String password) {
        return utilisateurDAO.authenticate(username, password);
    }

    public List<utilisateur> findByRole(String role) {
        return utilisateurDAO.findByRole(role);
    }

    public boolean existsByUsername(String username) {
        return utilisateurDAO.existsByUsername(username);
    }
}
 

