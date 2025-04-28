/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cinema"; // change le port ou le nom de base si besoin
    private static final String USER = "root"; // ton utilisateur MySQL
    private static final String PASSWORD = "123456789"; // ton mot de passe MySQL (vide si tu n'en as pas mis)

    private static Connection connexion;

    public static Connection getConnexion() {
        if (connexion == null) {
            try {
                connexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion réussie !");
            } catch (SQLException e) {
                System.out.println("Erreur de connexion : " + e.getMessage());
            }
        }
        return connexion;
    }

public static void main(String[] args) {
    getConnexion();
}
}

