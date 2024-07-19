package Comptes;

public class CompteCourant extends CompteBancaire {
    private double decouvertAutorise;

    public CompteCourant(String numeroCompte, String titulaire, double solde, double decouvertAutorise) {
        super(numeroCompte, titulaire, solde);
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public void retirer(double montant) {
        if (montant > 0 && solde + decouvertAutorise >= montant) {
            solde -= montant;
        }
    }

    @Override
    public void afficherDetails() {
        System.out.println("Compte Courant:");
        System.out.println("Numéro de compte: " + numeroCompte);
        System.out.println("Titulaire: " + titulaire);
        System.out.println("Solde: " + solde);
        System.out.println("Découvert autorisé: " + decouvertAutorise);
    }
}
