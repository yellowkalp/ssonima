/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.billet;
import modele.film;
import modele.seance;
import service.BilletService;
import service.FilmService;
import service.SeanceService;

public class TicketPurchaseWindow extends JFrame {

    private JComboBox<film> filmComboBox;
    private JComboBox<seance> seanceComboBox;
    private JSpinner seatSpinner;
    private JTextField priceField;
    private JButton purchaseButton;

    private FilmService filmService = new FilmService();
    private SeanceService seanceService = new SeanceService();
    private BilletService billetService = new BilletService();

    public TicketPurchaseWindow() {
        setTitle("Achat de Billets");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Composants
        filmComboBox = new JComboBox<>();
        seanceComboBox = new JComboBox<>();
        seatSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        priceField = new JTextField(6);
        purchaseButton = new JButton("Acheter");

        // Layout
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Film :"));
        panel.add(filmComboBox);
        panel.add(new JLabel("Séance :"));
        panel.add(seanceComboBox);
        panel.add(new JLabel("Numéro de place :"));
        panel.add(seatSpinner);
        panel.add(new JLabel("Prix :"));
        panel.add(priceField);
        panel.add(new JLabel());
        panel.add(purchaseButton);
        setContentPane(panel);

        // Charger les films
        loadFilms();

        // Quand on sélectionne un film, on recharge les séances
        filmComboBox.addActionListener(e -> loadSeances());

        // Action d'achat
        purchaseButton.addActionListener(e -> {
            try {
                purchaseTicket();
            } catch (SQLException ex) {
                Logger.getLogger(TicketPurchaseWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        setVisible(true);
    }

    private void loadFilms() {
        filmComboBox.removeAllItems();
        List<film> films = filmService.listerFilms(0, filmService.count());
        for (film f : films) {
            filmComboBox.addItem(f);
        }
        if (!films.isEmpty()) loadSeances();
    }

    private void loadSeances() {
        seanceComboBox.removeAllItems();
        film selectedFilm = (film) filmComboBox.getSelectedItem();
        if (selectedFilm == null) return;
        List<seance> seances = seanceService.findByFilm(selectedFilm.getId());
        for (seance s : seances) {
            seanceComboBox.addItem(s);
        }
    }

    private void purchaseTicket() throws SQLException {
        seance selectedSeance = (seance) seanceComboBox.getSelectedItem();
        int seatNumber = (int) seatSpinner.getValue();
        String prixTxt = priceField.getText().trim();
        if (selectedSeance == null || prixTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une séance et renseignez un prix.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            double prix = Double.parseDouble(prixTxt);
            billet billet = new billet(0, selectedSeance, seatNumber, prix);
            billetService.addBillet(billet);
            JOptionPane.showMessageDialog(this, "Billet acheté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Prix invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
