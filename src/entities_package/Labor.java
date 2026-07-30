package entities_package;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MIKEL
 */

public class Labor {
    private int idLabo;
    private String nomLabo;
    private int capaciteMax;

    public Labor() {}

    public Labor(int idLabo, String nomLabo, int capaciteMax) {
        this.idLabo = idLabo;
        this.nomLabo = nomLabo;
        this.capaciteMax = capaciteMax;
    }

    public int getIdLabo() { return idLabo; }
    public void setIdLabo(int idLabo) { this.idLabo = idLabo; }

    public String getNomLabo() { return nomLabo; }
    public void setNomLabo(String nomLabo) { this.nomLabo = nomLabo; }

    public int getCapaciteMax() { return capaciteMax; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }

    @Override
    public String toString() {
        return nomLabo; // Utile pour l'affichage automatique dans un JComboBox NetBeans
    }
}
