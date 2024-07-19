package Comptes;

public abstract class CompteBancaire {
    protected String numeroCompte;
    protected String titulaire;
    protected double solde;

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

}
