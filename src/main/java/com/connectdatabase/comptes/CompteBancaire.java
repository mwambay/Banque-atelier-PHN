package com.connectdatabase.comptes;

public abstract class CompteBancaire {
    protected String numeroCompte;
    protected String titulaire;
    protected double solde;


    //public abstract String toJSON();

    public CompteBancaire(String numeroCompte, String titulaire, double solde) {
        this.numeroCompte = numeroCompte;
        this.titulaire = titulaire;
        this.solde = solde;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public double getSolde() {
        return solde;
    }

    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
        }
    }

    public void retirer(double montant) {
        if (montant > 0 && solde >= montant) {
            solde -= montant;
        }
    }

    public abstract void afficherDetails();

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public void setSolde(double solde){
        this.solde = solde;

    }

    
    @Override
    public String toString() {
        return "{" +
            " numeroCompte='" + getNumeroCompte() + "'" +
            ", titulaire='" + getTitulaire() + "'" +
            ", solde='" + getSolde() + "'" +
            "}";
    }


    public String toText() {
        return numeroCompte + "," + titulaire + "," + solde;
    }

    public static CompteBancaire fromText(String text) {
        String[] parts = text.split(",");
        String type = parts[0];
        String numeroCompte = parts[1];
        String titulaire = parts[2];
        double solde = Double.parseDouble(parts[3]);

        if (type.equals("CompteCourant")) {
            double decouvertAutorise = Double.parseDouble(parts[4]);
            return new CompteCourant(numeroCompte, titulaire, solde, decouvertAutorise);
        } else if (type.equals("CompteEpargne")) {
            double tauxInteret = Double.parseDouble(parts[4]);
            return new CompteEpargne(numeroCompte, titulaire, solde, tauxInteret);
        }
        return null;
    }
}
