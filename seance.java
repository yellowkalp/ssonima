/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;
import java.time.LocalDateTime;

public class seance{
  private int id;
  private film film;
  private salle salle;
  private LocalDateTime dateHeure;
    
    public seance() {}

    public seance(int id, film film, salle salle, LocalDateTime dateHeure) {
        this.id = id;
        this.film = film;
        this.salle = salle;
        this.dateHeure = dateHeure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public film getFilm() {
        return film;
    }

    public void setfilm(film film) {
        this.film = film;
    }

    public salle getsalle() {
        return salle;
    }

    public void setsalle(salle salle) {
        this.salle = salle;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", film=" + film +
                ", salle=" + salle +
                ", dateHeure=" + dateHeure +
                '}';
    }
}
