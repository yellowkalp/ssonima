/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele;
public class VenteSnack {
    private int id;
    private snack snack;
    private int quantite;
    private double total;
    private java.time.LocalDateTime dateVente;

    public VenteSnack() {}

    public VenteSnack(int id, snack snack, int quantite, double total, java.time.LocalDateTime dateVente) {
        this.id = id;
        this.snack = snack;
        this.quantite = quantite;
        this.total = total;
        this.dateVente = dateVente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public snack getSnack() {
        return snack;
    }

    public void setSnack(snack snack) {
        this.snack = snack;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public java.time.LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(java.time.LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

    @Override
    public String toString() {
        return "VenteSnack{" +
                "id=" + id +
                ", snack=" + snack +
                ", quantite=" + quantite +
                ", total=" + total +
                ", dateVente=" + dateVente +
                '}';
    }
}
