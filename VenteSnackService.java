/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;



import dao.VenteSnackDAO;
import java.sql.SQLException;
import modele.VenteSnack;
import java.time.LocalDate;
import java.util.List;

public class VenteSnackService {
    private VenteSnackDAO venteSnackDAO = new VenteSnackDAO();

    // CRUD
    public void addVenteSnack(VenteSnack venteSnack) throws SQLException {
        venteSnackDAO.create(venteSnack);
    }

    public VenteSnack getVenteSnackById(int id) throws SQLException {
        return venteSnackDAO.findById(id);
    }

    public void updateVenteSnack(VenteSnack venteSnack) throws SQLException {
        venteSnackDAO.update(venteSnack);
    }

    public void deleteVenteSnack(int id) throws SQLException {
        venteSnackDAO.delete(id);
    }

    // Méthodes supplémentaires
    public List<VenteSnack> findByDate(LocalDate date) {
        return venteSnackDAO.findByDate(date);
    }

    public double totalSalesBySnack(int snackId) {
        return venteSnackDAO.totalSalesBySnack(snackId);
    }

    public double totalSalesByDate(LocalDate date) {
        return venteSnackDAO.totalSalesByDate(date);
    }
}


