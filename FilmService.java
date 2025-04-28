/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import dao.FilmDao;
import modele.film;
import java.util.List;

public class FilmService {
    private FilmDao filmDAO = new FilmDao();

    // CRUD
    public void addFilm(film film) {
        filmDAO.create(film);
    }

    public film getFilmById(int id) {
        return filmDAO.findById(id);
    }

    public void updateFilm(film film) {
        filmDAO.update(film);
    }

    public void deleteFilm(int id) {
        filmDAO.delete(id);
    }

    // Recherche
   

   public film getFilmByExactTitle(String title) {
    return filmDAO.findByTitle(title);
}

    // Méthodes supplémentaires
    public int count() {
        return filmDAO.count();
    }
    

    public List<film> listerFilms(int offset, int limit) {
        return filmDAO.lister(offset, limit);
    }
}
