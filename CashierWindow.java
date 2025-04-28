/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CashierWindow extends JFrame {

    public CashierWindow() {
        setTitle("Espace Caissier");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem gestionFilms = new JMenuItem("Gestion des films");
        JMenuItem gestionSeances = new JMenuItem("Gestion des séances");
        JMenuItem venteBillets = new JMenuItem("Vente de billets");
        JMenuItem venteSnacks = new JMenuItem("Vente de snacks");

        menu.add(gestionFilms);
        menu.add(gestionSeances);
        menu.add(venteBillets);
        menu.add(venteSnacks);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JLabel label = new JLabel("Bienvenue, Caissier!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        gestionFilms.addActionListener(e -> new FilmManagementWindow());
        gestionSeances.addActionListener(e -> new SeanceManagementWindow());
        venteBillets.addActionListener(e -> new TicketPurchaseWindow());
        venteSnacks.addActionListener(e -> new SnackPurchaseWindow());

        setVisible(true);
    }

    public static void main(String[] args) {
        new CashierWindow();
    }
}
