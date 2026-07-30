package dao_packages;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MIKEL
 */
import config_packages.Database;
import entities_package.Labor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LaborDAO {
    public List<Labor> getTousLesLaboratoires() throws SQLException {
        List<Labor> liste = new ArrayList<>();
        
        String sql = "SELECT * FROM labor";

        Connection conn = Database.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_labo");
                String nom = rs.getString("nom_labo");
                int cap = rs.getInt("capacite_max");

                Labor labo = new Labor(id, nom, cap);

                liste.add(labo);
            }
        }

        return liste;
    }  
    
    public int getPresentsActuelsCount(int idLabo) throws SQLException {
    String sql = "SELECT COUNT(*) FROM presence WHERE id_labo = ? AND date_heure_out IS NULL";
    Connection conn = Database.getConnection();

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idLabo);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }
    return 0;
    }
}
