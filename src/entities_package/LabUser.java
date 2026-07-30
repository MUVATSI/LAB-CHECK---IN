/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities_package;

/**
 *
 * @author MIKEL
 */

public class LabUser {
    private int idUser;
    private String matricule;
    private String nom;
    private String postNom;
    private String prenom;
    private String departement;
    private String faculte;

    public LabUser() {}

    // Constructeur mis à jour
    public LabUser(int idUser, String matricule, String nom, String postNom, String prenom, String departement, String faculte) {
        this.idUser = idUser;
        this.matricule = matricule;
        this.nom = nom;
        this.postNom = postNom;
        this.prenom = prenom;
        this.departement = departement;
        this.faculte = faculte;
    }

    // Getters & Setters
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPostNom() { return postNom; }
    public void setPostNom(String postNom) { this.postNom = postNom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }

    public String getFaculte() { return faculte; }
    public void setFaculte(String faculte) { this.faculte = faculte; }

    public String getNomComplet() {
        return nom + " " + postNom + " " + prenom;
    }

    @Override
    public String toString() {
        return getNomComplet() + " (" + matricule + ")";
    }
}