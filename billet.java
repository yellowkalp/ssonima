/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;
public class billet {
    private int id;
    private seance seance;
    private int numeroPlace;
    private double prix;

    public billet() {}

    public billet(int id, seance seance, int numeroPlace, double prix) {
        this.id = id;
        this.seance = seance;
        this.numeroPlace = numeroPlace;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public seance getSeance() {
        return seance;
    }

    public void setSeance(seance seance) {
        this.seance = seance;
    }

    public int getNumeroPlace() {
        return numeroPlace;
    }

    public void setNumeroPlace(int numeroPlace) {
        this.numeroPlace = numeroPlace;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "id=" + id +
                ", seance=" + seance +
                ", numeroPlace=" + numeroPlace +
                ", prix=" + prix +
                '}';
    }
}