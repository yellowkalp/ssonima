/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


 import dao.snackDAO;
import java.sql.SQLException;
import modele.snack;
import java.util.List;

public class SnackService {
    private snackDAO snackDAO = new snackDAO();

    // CRUD
    public void addSnack(snack snack) throws SQLException {
        snackDAO.create(snack);
    }

    public snack getSnackById(int id) throws SQLException {
        return snackDAO.findById(id);
    }

    public void updateSnack(snack snack) throws SQLException {
        snackDAO.update(snack);
    }

    public void deleteSnack(int id) throws SQLException {
        snackDAO.delete(id);
    }
   public List<snack> searchByName(String keyword) {
        return snackDAO.searchByName(keyword);
    }
    // Méthodes supplémentaires
    

    public List<snack> findUnderPrice(double maxPrice) {
        return snackDAO.findUnderPrice(maxPrice);
    }
}   

