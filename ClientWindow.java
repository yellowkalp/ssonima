/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientWindow extends JFrame {

    public ClientWindow() {
        setTitle("Espace Client");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout de la fenêtre client
        setLayout(new BorderLayout());

        // Menu de navigation pour le client
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem consulterFilms = new JMenuItem("Consulter les films");
        JMenuItem rechercherFilm = new JMenuItem("Rechercher un film");
        JMenuItem acheterBillet = new JMenuItem("Acheter un billet");
        JMenuItem acheterSnack = new JMenuItem("Acheter un snack");

        // Ajouter les éléments de menu
        menu.add(consulterFilms);
        menu.add(rechercherFilm);
        menu.add(acheterBillet);
        menu.add(acheterSnack);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Panneau central avec un label
        JLabel label = new JLabel("Bienvenue, Client!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        // Action des items de menu
        consulterFilms.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre pour consulter les films
                new FilmListWindow();
            }
        });

        rechercherFilm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre de recherche de films
                new FilmSearchWindow();
            }
        });

        acheterBillet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre pour acheter un billet
                new TicketPurchaseWindow();
            }
        });

        acheterSnack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre pour acheter des snacks
                new SnackPurchaseWindow();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientWindow();
    }
}
