/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminWindow extends JFrame {

    public AdminWindow() {
        setTitle("Espace Administrateur");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout de la fenêtre admin
        setLayout(new BorderLayout());

        // Menu de navigation pour l'admin
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem gestionFilms = new JMenuItem("Gestion des films");
        JMenuItem gestionSeances = new JMenuItem("Gestion des séances");
        JMenuItem gestionUtilisateurs = new JMenuItem("Gestion des utilisateurs");
        JMenuItem suiviVentes = new JMenuItem("Suivi des ventes");

        // Ajouter les éléments de menu
        menu.add(gestionFilms);
        menu.add(gestionSeances);
        menu.add(gestionUtilisateurs);
        menu.add(suiviVentes);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Panneau central avec un label pour l'exemple
        JLabel label = new JLabel("Bienvenue, Administrateur!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        // Action des items de menu
        gestionFilms.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre de gestion des films
                new FilmManagementWindow();
            }
        });

        gestionSeances.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre de gestion des séances
                new SeanceManagementWindow();
            }
        });

        gestionUtilisateurs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre de gestion des utilisateurs
                new UserManagementWindow();
            }
        });

        suiviVentes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ouvrir une fenêtre de suivi des ventes
                new SalesTrackingWindow();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminWindow();
    }
}

