/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao_packages;

import config_packages.Database; 
import entities_package.LabUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MIKEL
 */
public class LabUserDAO {
    /**
     * Recherche un étudiant par son matricule unique.
     * @param matricule Le matricule saisi (ex: "ETU001")
     * @return L'objet LabUser s'il existe, sinon null.
     */
    public LabUser findByMatricule(String matricule) throws SQLException {
        String sql = "SELECT * FROM lab_user WHERE matricule = ?";
        Connection conn = Database.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricule);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new LabUser(
                        rs.getInt("id_user"),
                        rs.getString("matricule"),
                        rs.getString("nom"),
                        rs.getString("post_nom"),
                        rs.getString("prenom"),
                        rs.getString("departement"),
                        rs.getString("faculte")
                    );
                }
            }
        }
        
        // Aucun étudiant trouvé avec ce matricule
        return null; 
    }
    
    
    /**
    * Insère un nouvel étudiant dans la base de données.
    */
   public boolean ajouterEtudiant(LabUser user) throws SQLException {
       String sql = "INSERT INTO lab_user (matricule, nom, post_nom, prenom, departement, faculte) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

       Connection conn = Database.getConnection();

       try (PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setString(1, user.getMatricule());
           stmt.setString(2, user.getNom());
           stmt.setString(3, user.getPostNom());
           stmt.setString(4, user.getPrenom());
           stmt.setString(5, user.getDepartement());
           stmt.setString(6, user.getFaculte());

           int rows = stmt.executeUpdate();
           return rows > 0;
       }
   }    
}
