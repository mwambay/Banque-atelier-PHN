package Comptes;

public class CompteEpargne extends CompteBancaire {
    private double tauxInteret;

    public CompteEpargne(String numeroCompte, String titulaire, double solde, double tauxInteret) {
        super(numeroCompte, titulaire, solde);
        this.tauxInteret = tauxInteret;
    }

    public void appliquerInteret() {
        solde += solde * tauxInteret / 100;
    }

    @Override
    public void afficherDetails() {
        System.out.println("Compte Épargne:");
        System.out.println("Numéro de compte: " + numeroCompte);
        System.out.println("Titulaire: " + titulaire);
        System.out.println("Solde: " + solde);
        System.out.println("Taux d'intérêt: " + tauxInteret + "%");
    }
}
