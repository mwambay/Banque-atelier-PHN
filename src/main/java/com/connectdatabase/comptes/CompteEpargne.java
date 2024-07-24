package com.connectdatabase.comptes;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    @Override
    public void sauvegarder(Connection conn) throws SQLException {
        String query = "INSERT INTO CompteBancaire (numeroCompte, titulaire, solde, type, tauxInteret) VALUES (?, ?, ?, 'CompteEpargne', ?) " +
                       "ON DUPLICATE KEY UPDATE titulaire = VALUES(titulaire), solde = VALUES(solde), tauxInteret = VALUES(tauxInteret)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.setString(2, titulaire);
            stmt.setDouble(3, solde);
            stmt.setDouble(4, tauxInteret);
            stmt.executeUpdate();
        }
    }
}
