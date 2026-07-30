/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities_package;

/**
 *
 * @author MIKEL
 */

import java.sql.Timestamp;

public class Presence {
    private int idPresence;
    private LabUser user;
    private Labor labor;
    private String motif;
    private Timestamp dateHeureIn;
    private Timestamp dateHeureOut;

    public Presence() {}

    public Presence(int idPresence, LabUser user, Labor labor, String motif, Timestamp dateHeureIn, Timestamp dateHeureOut) {
        this.idPresence = idPresence;
        this.user = user;
        this.labor = labor;
        this.motif = motif;
        this.dateHeureIn = dateHeureIn;
        this.dateHeureOut = dateHeureOut;
    }

    public int getIdPresence() { return idPresence; }
    public void setIdPresence(int idPresence) { this.idPresence = idPresence; }

    public LabUser getUser() { return user; }
    public void setUser(LabUser user) { this.user = user; }

    public Labor getLabor() { return labor; }
    public void setLabor(Labor labor) { this.labor = labor; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public Timestamp getDateHeureIn() { return dateHeureIn; }
    public void setDateHeureIn(Timestamp dateHeureIn) { this.dateHeureIn = dateHeureIn; }

    public Timestamp getDateHeureOut() { return dateHeureOut; }
    public void setDateHeureOut(Timestamp dateHeureOut) { this.dateHeureOut = dateHeureOut; }
}