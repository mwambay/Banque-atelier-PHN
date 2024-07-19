import java.util.Scanner;

import Banque.Banque;
import Comptes.CompteCourant;
import Comptes.CompteEpargne;

public class Main {
    public static void main(String[] args) {
        Banque banque = new Banque();
        Scanner saisie = new Scanner(System.in);
        CompteCourant cc1 = new CompteCourant("123", "Alice", 1000, 500);
        CompteEpargne ce1 = new CompteEpargne("456", "Bob", 2000, 2.5);


        System.out.println("Bienvenue dans notre banque\n");
    while (true) {
        int choix = 0;
            System.out.println("Menu\n"+
            "1. Ajouter un compte\n"+
            "2. Supprimer un compte\n"+
            "3. Modifier un compte\n"+
            "4. Rechercher un compte par nom\n"+
            "5.Lister les comptes par lettre\n"+
            "6. Rechercher un compte par type\n"+
            "7. Afficher les comptes par type\n"+
            "8. Afficher les détails d'un compte\n"+
            "9. Transferer des fonds\n"+
            "10. Générer un relevé\n"+
            "0. Quitter\n");

            System.out.print("Choix : ");
            choix = saisie.nextInt();

            if (choix == 1) {
                System.out.println("Ajouter un compte\n");
                System.out.println("1. Compte Courant\n"+
                "2. Compte Epargne\n");
                System.out.print("Choix : ");

                int choixCompte = saisie.nextInt();
                if (choixCompte == 1) {
                    System.out.print("Numéro de compte: ");
                    String numeroCompte = saisie.next();
                    System.out.print("Titulaire: ");
                    String titulaire = saisie.next();
                    System.out.print("Solde: ");
                    double solde = saisie.nextDouble();
                    System.out.print("Découvert autorisé: ");
                    double decouvertAutorise = saisie.nextDouble();
                    CompteCourant cc = new CompteCourant(numeroCompte, titulaire, solde, decouvertAutorise);
                    banque.ajouterCompte(cc);
                } else if (choixCompte == 2) {
                    System.out.print("Numéro de compte: ");
                    String numeroCompte = saisie.next();
                    System.out.print("Titulaire: ");
                    String titulaire = saisie.next();
                    System.out.print("Solde: ");
                    double solde = saisie.nextDouble();
                    System.out.print("Taux d'intérêt: ");
                    double tauxInteret = saisie.nextDouble();
                    CompteEpargne ce = new CompteEpargne(numeroCompte, titulaire, solde, tauxInteret);
                    banque.ajouterCompte(ce);
                }
            } else if (choix == 2) {
                System.out.println("Supprimer un compte\n");
                System.out.print("Numéro de compte: ");
                String numeroCompte = saisie.next();
                banque.supprimerCompte(numeroCompte);
            } else if (choix == 3) {
                System.out.println("Modifier un compte\n");
                System.out.print("Numéro de compte: ");
                String numeroCompte = saisie.next();

                boolean trouve = banque.verifierSiCompteExiste(numeroCompte);
                if(trouve){

                    System.out.print("Entrez le nouveau nom titulaire : ");
                    String newName = saisie.next();
                    System.out.print("Entrez le nouveau solde : ");
                    double newSolde = saisie.nextDouble();
                    
                    banque.modifierCompte( numeroCompte,newName, newSolde);

                }

                else{continue;}

                    
                
            }

            else if(choix == 4){
                System.out.print("Entrez le nom du titulaire : ");
                String nom = saisie.next();
                banque.rechercherCompteParNom(nom);
                if(banque.rechercherCompteParNom(nom) != null){
                    System.out.println(banque.rechercherCompteParNom(nom).toString());
                }
                else{
                    System.out.println("Compte non trouve");
                }

            }

            else if(choix == 5){
                System.out.print("saisissez une lettre conrrespondant au compte : ");
                String stg =  saisie.next();
                char lettre = stg.charAt(0);
                banque.listerComptesParLettre(lettre);
                
            }

            else if(choix == 6){
                System.out.print("Type de Compte\n 1.Compte Courant\n2.Compte Epargne\n");
                System.out.print("type : ");
                int choix_ = saisie.nextInt();
                if (choix_ == 1){
                    int nombre = banque.compterComptesParType(CompteCourant.class);
                    System.out.println("Nombre de compte courant : " + nombre);
                }

                else if(choix_ == 2){
                    int nombre = banque.compterComptesParType(CompteEpargne.class);
                    System.out.println("Nombre de compte epargne : " + nombre);

                }
                else{
                    System.out.println("Choix invalide !");
                    continue;
                }
            }

            else if(choix == 7){
                System.out.print("Type de Compte\n 1.Compte Courant\n2.Compte Epargne\n");
                System.out.print("type : ");
                int choix_ = saisie.nextInt();
                if (choix_ == 1){
                    banque.afficherComptesParType(CompteCourant.class);
                }

                else if(choix_ == 2){
                    banque.afficherComptesParType(CompteEpargne.class);

                }
                else{
                    System.out.println("Choix invalide !");
                    continue;
                }  
            }

            else if(choix == 8){
                System.out.print("Numéro de compte: ");
                String numeroCompte = saisie.next();
                banque.afficherDetailsCompte(numeroCompte);
            }

            else if(choix == 9){
                System.out.print("Numero de compte source : ");
                String numeroDeCompteSource = saisie.next();
                System.out.print("Numero de compte de destination : ");
                String numeroDeCompteDestination = saisie.next();
                System.out.print("Numero de compte source : ");
                double montant = saisie.nextDouble();
                banque.transfererFonds(numeroDeCompteSource, numeroDeCompteDestination, montant);
                
            }

            else if(choix == 10){
                System.out.print("Numéro de compte: ");
                String numeroCompte = saisie.next();
                banque.genererReleve(numeroCompte);  
            }

            else if(choix == 0){
                System.exit(0);
            }

            else{
                System.out.println("Choix invalide ! ");
            }



    }
    }
}

