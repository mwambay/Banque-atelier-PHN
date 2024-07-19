package Banque;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Comptes.CompteBancaire;

public class Banque {
    private Map<String, CompteBancaire> comptes;

    public Banque() {
        comptes = new HashMap<>();
    }

    public void ajouterCompte(CompteBancaire compte) {
        comptes.put(compte.getNumeroCompte(), compte);
    }

    public void supprimerCompte(String numeroCompte) {
        comptes.remove(numeroCompte);
    }

    public boolean verifierSiCompteExiste(String numeroCompte){
        CompteBancaire compte = comptes.get(numeroCompte);
        if (compte != null) {
            System.out.println("Compte trouvé.");

            return true;
        } else {
            System.out.println("Compte non trouvé.");
            return false;
        } 


    }

    public void modifierCompte(String numeroCompte, String newString, double solde) {
        CompteBancaire compte = comptes.get(numeroCompte);
        if (compte != null) {
            compte.setTitulaire(newString);
            compte.setSolde(solde);
            comptes.put(numeroCompte, compte);
            System.out.println("Compte modifié avec succes");
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    public CompteBancaire rechercherCompteParNom(String nom) {
        for (CompteBancaire compte : comptes.values()) {
            if (compte.getTitulaire().equalsIgnoreCase(nom)) {
                return compte;
            }
        }
        return null;
    }

    public void listerComptesParLettre(char lettre) {
        for (CompteBancaire compte : comptes.values()) {
            if (compte.getTitulaire().toLowerCase().charAt(0) == lettre) {
                compte.afficherDetails();
            }
        }
    }

    public int compterComptesParType(Class<? extends CompteBancaire> type) {
        int count = 0;
        for (CompteBancaire compte : comptes.values()) {
            if (type.isInstance(compte)) {
                count++;
            }
        }
        return count;
    }

    public void afficherComptesParType(Class<? extends CompteBancaire> type) {
        for (CompteBancaire compte : comptes.values()) {
            if (type.isInstance(compte)) {
                compte.afficherDetails();
            }
        }
    }

    public void afficherDetailsCompte(String numeroCompte) {
        CompteBancaire compte = comptes.get(numeroCompte);
        if (compte != null) {
            compte.afficherDetails();
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    public void transfererFonds(String sourceNumeroCompte, String destinationNumeroCompte, double montant) {
        CompteBancaire sourceCompte = comptes.get(sourceNumeroCompte);
        CompteBancaire destinationCompte = comptes.get(destinationNumeroCompte);

        if (sourceCompte != null && destinationCompte != null && montant > 0 && sourceCompte.getSolde() >= montant) {
            sourceCompte.retirer(montant);
            destinationCompte.deposer(montant);
            System.out.println("Transfert réussi de " + montant + " de " + sourceNumeroCompte + " vers " + destinationNumeroCompte);
        } else {
            System.out.println("Transfert échoué.");
        }
    }

    public void genererReleve(String numeroCompte) {
        CompteBancaire compte = comptes.get(numeroCompte);
        if (compte != null) {
            System.out.println("Relevé de compte pour " + compte.getTitulaire());
            compte.afficherDetails();
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    public void sauvegarderComptes(String cheminFichier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (CompteBancaire compte : comptes.values()) {
                writer.write(compte.toText());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargerComptes(String cheminFichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                CompteBancaire compte = CompteBancaire.fromText(line);
                ajouterCompte(compte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
