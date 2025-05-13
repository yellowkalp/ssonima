/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.billetDAO;
import java.sql.SQLException;
import modele.billet;
import java.util.List;

public class BilletService {
    private billetDAO billetDAO = new billetDAO();

    // CRUD
    public void addBillet(billet billet) throws SQLException {
        billetDAO.create(billet);
    }

    public billet getBilletById(int id) throws SQLException {
        return billetDAO.findById(id);
    }

    public void updateBillet(billet billet) throws SQLException {
        billetDAO.update(billet);
    }

    public void deleteBillet(int id) throws SQLException {
        billetDAO.delete(id);
    }

    // Méthodes supplémentaires
    public List<billet> findBySeance(int seanceId) {
        return billetDAO.findBySeance(seanceId);
    }

    public int countBySeance(int seanceId) {
        return billetDAO.countBySeance(seanceId);
    }

    public boolean isPlaceTaken(int seanceId, int numeroPlace) {
        return billetDAO.isPlaceTaken(seanceId, numeroPlace);
    }
    
     //  Total des ventes pour une séance
    public double calculerChiffreAffairesSeance(int seanceId) {
        return billetDAO.totalSalesBySeance(seanceId);
    }

    //  Nombre de billets vendus pour une séance
    public int nombreBilletsVendus(int seanceId) {
        return billetDAO.countTicketsBySeance(seanceId);
    }


}
  

