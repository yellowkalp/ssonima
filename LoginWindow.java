/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import util.DatabaseConnection;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginWindow() {
        // Titre de la fenêtre
        setTitle("Connexion");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout de la fenêtre
        setLayout(new GridLayout(3, 2));

        // Champs de saisie
        JLabel usernameLabel = new JLabel("Identifiant:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordField = new JPasswordField();
        
        loginButton = new JButton("Se connecter");

        // Ajouter les composants
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());  // Cellule vide
        add(loginButton);

        // Action du bouton "Se connecter"
       
       loginButton.addActionListener(e -> handleLogin());
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Connexion à la base de données pour vérifier les identifiants
        try {
            Connection conn = DatabaseConnection.getConnexion();
            String sql = "SELECT role FROM user WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                // Rediriger selon le rôle
                if ("admin".equals(role)) {
                    // Afficher la fenêtre Admin
                    new AdminWindow();
                } else if ("caissier".equals(role)) {
                    // Afficher la fenêtre Caissier
                    new CashierWindow();
                } else if ("client".equals(role)) {
                    // Afficher la fenêtre Client
                    new ClientWindow();
                }
                dispose(); // Fermer la fenêtre de connexion
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginWindow();
    }
}
