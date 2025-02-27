package com.connectdatabase;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.connectdatabase.banques.*;
import com.connectdatabase.comptes.*;
import com.connectdatabase.database.*;;



public class Main {
    public static void main(String[] args) throws SQLException {

        ConnectDatabase con=new ConnectDatabase();
        Banque banque;
        Connection connected= con.connected();
        banque = new Banque();
        
        Scanner saisie = new Scanner(System.in);

        System.out.println("Bienvenue dans notre banque\n");
        while (true) {
            System.out.println("Menu\n"+
                "1. Ajouter un compte\n"+
                "2. Supprimer un compte\n"+
                "3. Modifier un compte\n"+
                "4. Rechercher un compte par nom\n"+
                "5. Lister les comptes par lettre\n"+
                "6. Rechercher un compte par type\n"+
                "7. Afficher les comptes par type\n"+
                "8. Afficher les détails d'un compte\n"+
                "9. Transférer des fonds\n"+
                "10. Générer un relevé\n"+
                "0. Quitter\n");

            //System.out.print("Choix : ");
            int choix = entrer();

            switch (choix) {
                case 1 -> {
                    System.out.println("Ajouter un compte\n");
                    System.out.println("1. Compte Courant\n2. Compte Epargne\n");
                    int choixCompte = entrer();
                    System.out.print("Numéro de compte: ");
                    String numeroCompte = saisie.next();
                    System.out.print("Titulaire: ");
                    String titulaire = saisie.next();
                    System.out.print("Solde: ");
                    double solde = saisie.nextDouble();
                    if (choixCompte == 1) {
                        System.out.print("Découvert autorisé: ");
                        double decouvertAutorise = saisie.nextDouble();
                        CompteCourant cc = new CompteCourant(numeroCompte, titulaire, solde, decouvertAutorise);
                        banque.ajouterCompte(cc);

                    } else if (choixCompte == 2) {
                        System.out.print("Taux d'intérêt: ");
                        double tauxInteret = saisie.nextDouble();
                        CompteEpargne ce = new CompteEpargne(numeroCompte, titulaire, solde, tauxInteret);
                        banque.ajouterCompte(ce);

                    }
                }
                case 2 -> {
                    System.out.println("Supprimer un compte\n");
                    System.out.print("Numéro de compte: ");
                    String numeroCompte = saisie.next();
                    banque.supprimerCompte(numeroCompte);
                }
                case 3 -> {
                    System.out.println("Modifier un compte\n");
                    System.out.print("Numéro de compte: ");
                    String numeroCompte = saisie.next();
                    if (banque.verifierSiCompteExiste(numeroCompte)) {
                        System.out.print("Entrez le nouveau nom titulaire : ");
                        String newName = saisie.next();
                        System.out.print("Entrez le nouveau solde : ");
                        double newSolde = saisie.nextDouble();
                        banque.modifierCompte(numeroCompte, newName, newSolde);
                    }
                }
                case 4 -> {
                    System.out.print("Entrez le nom du titulaire : ");
                    String nom = saisie.next();
                    if (banque.rechercherCompteParNom(nom) != null) {
                        System.out.println(banque.rechercherCompteParNom(nom).toString());
                    } else {
                        System.out.println("Compte non trouvé");
                    }
                }
                case 5 -> {
                    System.out.print("Saisissez une lettre correspondant au compte : ");
                    char lettre = saisie.next().charAt(0);
                    banque.listerComptesParLettre(lettre);
                }
                case 6, 7 -> {
                    System.out.print("Type de Compte\n1. Compte Courant\n2. Compte Epargne\n");
                    int choixType =  entrer();
                    if (choixType == 1) {
                        if (choix == 6) {
                            int nombre = banque.compterComptesParType(CompteCourant.class);
                            System.out.println("Nombre de compte courant : " + nombre);
                        } else {
                            banque.afficherComptesParType(CompteCourant.class);
                        }
                    } else if (choixType == 2) {
                        if (choix == 6) {
                            int nombre = banque.compterComptesParType(CompteEpargne.class);
                            System.out.println("Nombre de compte épargne : " + nombre);
                        } else {
                            banque.afficherComptesParType(CompteEpargne.class);
                        }
                    } else {
                        System.out.println("Choix invalide !");
                    }
                }
                case 8 -> {
                    System.out.print("Numéro de compte: ");
                    String numeroCompte = saisie.next();
                    banque.afficherDetailsCompte(numeroCompte);
                }
                case 9 -> {
                    System.out.print("Numéro de compte source : ");
                    String numeroDeCompteSource = saisie.next();
                    System.out.print("Numéro de compte de destination : ");
                    String numeroDeCompteDestination = saisie.next();
                    System.out.print("Montant : ");
                    double montant = saisie.nextDouble();
                    banque.transfererFonds(numeroDeCompteSource, numeroDeCompteDestination, montant);
                }
                case 10 -> {
                    System.out.print("Numéro de compte: ");
                    String numeroCompte = saisie.next();
                    banque.genererReleve(numeroCompte);
                }
                case 0 ->System.exit(0);
                default -> System.out.println("Choix invalide !");
            }
        }

//        if(connected==null){
//            System.err.println("echec de la connexion à la base de donnees");
//        }
    }
    static Scanner saisie=new Scanner(System.in);
    public static int entrer(){
        while (true) {
            
        
            int choix;
            try {
                System.out.print("choix : ");
                choix=saisie.nextInt();

            
            } catch (Exception e) {
                System.out.println("saisi invalide ! recommencez");
                saisie.nextLine();
                continue;
            }
        return choix;

    }
    }


}
