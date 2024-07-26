package org.example.bankapp;

import javafx.beans.value.ObservableValue;

public class Enregistrement {
    
    public  String num, titulaire, type;
    public Double solde,decouverte, taux;

    public Enregistrement(String num, String titulaire, Double solde, String type, Double decouverte,Double taux) {
        this.num = num;
        this.titulaire = titulaire;
        this.solde = solde;
        this.type = type;
        this.decouverte = decouverte;
        this.taux = taux;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDecouverte() {
        return decouverte;
    }

    public void setDecouverte(Double decouverte) {
        this.decouverte = decouverte;
    }

    public Double getTaux() {
        return taux;
    }

    public void setTaux(Double taux) {
    this.taux=taux;
    }


}
