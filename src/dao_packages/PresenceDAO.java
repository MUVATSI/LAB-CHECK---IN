/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao_packages;
import config_packages.Database;
import entities_package.Labor;
import entities_package.LabUser;
import entities_package.Presence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author MIKEL
 */
public class PresenceDAO {

    private LaborDAO laborDAO = new LaborDAO();

    /**
     * Effectue le pointage (Entrée ou Sortie automatique)
     */
    public String pointer(LabUser user, Labor labo, String motif) throws SQLException {
        Connection conn = Database.getConnection();

        // 1. Recherche si l'utilisateur est déjà présent dans ce labo (date_heure_out est NULL)
        String sqlCheck = "SELECT id_presence FROM presence WHERE id_user = ? AND id_labo = ? AND date_heure_out IS NULL";

        try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setInt(1, user.getIdUser());
            stmtCheck.setInt(2, labo.getIdLabo());

            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next()) {
                    // --- CAS 1 : SORTIE ---
                    int idPresence = rs.getInt("id_presence");
                    String sqlOut = "UPDATE presence SET date_heure_out = NOW() WHERE id_presence = ?";
                    
                    try (PreparedStatement stmtOut = conn.prepareStatement(sqlOut)) {
                        stmtOut.setInt(1, idPresence);
                        stmtOut.executeUpdate();
                    }
                    return "SORTIE : Au revoir " + user.getNomComplet() + " !";
                    
                } else {
                    // --- CAS 2 : ENTRÉE ---
                    // Vérifier si la capacité maximale est atteinte
                    int actuels = laborDAO.getPresentsActuelsCount(labo.getIdLabo());
                    if (actuels >= labo.getCapaciteMax()) {
                        return "SATURE : Capacité maximale atteinte (" + labo.getCapaciteMax() + " places) !";
                    }

                    String sqlIn = "INSERT INTO presence (id_user, id_labo, motif, date_heure_in) VALUES (?, ?, ?, NOW())";
                    try (PreparedStatement stmtIn = conn.prepareStatement(sqlIn)) {
                        stmtIn.setInt(1, user.getIdUser());
                        stmtIn.setInt(2, labo.getIdLabo());
                        stmtIn.setString(3, motif);
                        stmtIn.executeUpdate();
                    }
                    return "ENTRÉE : Bienvenue " + user.getNomComplet() + " !";
                }
            }
        }
    }
    
    
    /**
    * Récupère les présences du jour OU celles des jours précédents non encore clôturées (sans heure de sortie).
    */
   public List<Presence> getPresencesAImprimer(int idLabo) throws SQLException {
       List<Presence> liste = new ArrayList<>();

       // Filtre : Soit le pointage date d'aujourd'hui (CURDATE), soit la sortie n'a jamais été effectuée
       String sql = "SELECT p.id_presence, p.motif, p.date_heure_in, p.date_heure_out, " +
                    "u.id_user, u.matricule, u.nom, u.post_nom, u.prenom, u.departement, u.faculte " +
                    "FROM presence p " +
                    "JOIN lab_user u ON p.id_user = u.id_user " +
                    "WHERE p.id_labo = ? " +
                    "  AND (DATE(p.date_heure_in) = CURDATE() OR p.date_heure_out IS NULL) " +
                    "ORDER BY p.date_heure_in DESC";

       Connection conn = Database.getConnection();
       try (PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, idLabo);
           try (ResultSet rs = stmt.executeQuery()) {
               while (rs.next()) {
                   LabUser u = new LabUser(
                       rs.getInt("id_user"),
                       rs.getString("matricule"),
                       rs.getString("nom"),
                       rs.getString("post_nom"),
                       rs.getString("prenom"),
                       rs.getString("departement"),
                       rs.getString("faculte")
                   );
                   Presence p = new Presence(
                       rs.getInt("id_presence"),
                       u,
                       null,
                       rs.getString("motif"),
                       rs.getTimestamp("date_heure_in"),
                       rs.getTimestamp("date_heure_out")
                   );
                   liste.add(p);
               }
           }
       }
       return liste;
    }
   
      
    
    /**
    * Récupère l'historique complet des présences pour un labo donné
    * (Présents actuels + Étudiants déjà sortis)
    */
    public List<Presence> getHistoriqueComplet(int idLabo) throws SQLException {
        List<Presence> liste = new ArrayList<>();
        String sql = "SELECT p.id_presence, p.motif, p.date_heure_in, p.date_heure_out, " +
                    "u.id_user, u.matricule, u.nom, u.post_nom, u.prenom, u.departement, u.faculte " +
                    "FROM presence p " +
                    "JOIN lab_user u ON p.id_user = u.id_user " +
                    "WHERE p.id_labo = ? " +
                    "ORDER BY p.date_heure_in DESC";

        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, idLabo);
           try (ResultSet rs = stmt.executeQuery()) {
               while (rs.next()) {
                   LabUser u = new LabUser(
                       rs.getInt("id_user"),
                       rs.getString("matricule"),
                       rs.getString("nom"),
                       rs.getString("post_nom"),
                       rs.getString("prenom"),
                       rs.getString("departement"),
                       rs.getString("faculte")
                   );
                   Presence p = new Presence(
                       rs.getInt("id_presence"),
                       u,
                       null,
                       rs.getString("motif"),
                       rs.getTimestamp("date_heure_in"),
                       rs.getTimestamp("date_heure_out") // Récupère aussi l'heure de sortie
                    );
                   liste.add(p);
                }
            }
        }
       return liste;
    }
    

    /**
     * Récupère la liste des personnes actuellement dans le laboratoire
     */
    public List<Presence> getPresentsActuels(int idLabo) throws SQLException {
        List<Presence> liste = new ArrayList<>();
        String sql = "SELECT p.id_presence, p.motif, p.date_heure_in, " +
                     "u.id_user, u.matricule, u.nom, u.post_nom, u.prenom, u.departement, u.faculte " +
                     "FROM presence p " +
                     "JOIN lab_user u ON p.id_user = u.id_user " +
                     "WHERE p.id_labo = ? AND p.date_heure_out IS NULL " +
                     "ORDER BY p.date_heure_in DESC";

        Connection conn = Database.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLabo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LabUser u = new LabUser(
                        rs.getInt("id_user"),
                        rs.getString("matricule"),
                        rs.getString("nom"),
                        rs.getString("post_nom"),
                        rs.getString("prenom"),
                        rs.getString("departement"),
                        rs.getString("faculte")
                    );
                    Presence p = new Presence(
                        rs.getInt("id_presence"),
                        u,
                        null,
                        rs.getString("motif"),
                        rs.getTimestamp("date_heure_in"),
                        null
                    );
                    liste.add(p);
                }
            }
        }
        return liste;
    }
}