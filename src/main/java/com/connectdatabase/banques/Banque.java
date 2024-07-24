package com.connectdatabase.banques;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.connectdatabase.comptes.CompteBancaire;


public class Banque {
    private Map<String, CompteBancaire> comptes;
    private Connection conn;

    public Banque() {
    }

    public Banque(String url, String user, String password) throws SQLException {
        comptes = new HashMap<>();
        conn = DriverManager.getConnection(url, user, password);
    }

    public void ajouterCompte(CompteBancaire compte) throws SQLException {
        compte.sauvegarder(conn);
        comptes.put(compte.getNumeroCompte(), compte);
    }

    public void supprimerCompte(String numeroCompte) throws SQLException {
        String query = "DELETE FROM CompteBancaire WHERE numeroCompte = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.executeUpdate();
        }
        comptes.remove(numeroCompte);
    }

    public boolean verifierSiCompteExiste(String numeroCompte) throws SQLException {
        String query = "SELECT 1 FROM CompteBancaire WHERE numeroCompte = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void modifierCompte(String numeroCompte, String newName, double newSolde) throws SQLException {
        String query = "UPDATE CompteBancaire SET titulaire = ?, solde = ? WHERE numeroCompte = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setDouble(2, newSolde);
            stmt.setString(3, numeroCompte);
            stmt.executeUpdate();
        }
        CompteBancaire compte = comptes.get(numeroCompte);
        if (compte != null) {
            compte.setTitulaire(newName);
            compte.setSolde(newSolde);
        }
    }

    public CompteBancaire rechercherCompteParNom(String nom) throws SQLException {
        String query = "SELECT * FROM CompteBancaire WHERE titulaire = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return CompteBancaire.charger(conn, rs.getString("numeroCompte"));
                }
            }
        }
        return null;
    }

    public void listerComptesParLettre(char lettre) throws SQLException {
        String query = "SELECT * FROM CompteBancaire WHERE titulaire LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lettre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CompteBancaire compte = CompteBancaire.charger(conn, rs.getString("numeroCompte"));
                    if (compte != null) {
                        compte.afficherDetails();
                    }
                }
            }
        }
    }

    public int compterComptesParType(Class<? extends CompteBancaire> type) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM CompteBancaire WHERE type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type.getSimpleName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        }
        return 0;
    }

    public void afficherComptesParType(Class<? extends CompteBancaire> type) throws SQLException {
        String query = "SELECT * FROM CompteBancaire WHERE type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type.getSimpleName());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CompteBancaire compte = CompteBancaire.charger(conn, rs.getString("numeroCompte"));
                    if (compte != null) {
                        compte.afficherDetails();
                    }
                }
            }
        }
    }

    public void afficherDetailsCompte(String numeroCompte) throws SQLException {
        CompteBancaire compte = CompteBancaire.charger(conn, numeroCompte);
        if (compte != null) {
            compte.afficherDetails();
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    public void transfererFonds(String numeroDeCompteSource, String numeroDeCompteDestination, double montant) throws SQLException {
        String query = "UPDATE CompteBancaire SET solde = solde - ? WHERE numeroCompte = ?";
        try (PreparedStatement stmtSource = conn.prepareStatement(query)) {
            stmtSource.setDouble(1, montant);
            stmtSource.setString(2, numeroDeCompteSource);
            stmtSource.executeUpdate();
        }

        query = "UPDATE CompteBancaire SET solde = solde + ? WHERE numeroCompte = ?";
        try (PreparedStatement stmtDestination = conn.prepareStatement(query)) {
            stmtDestination.setDouble(1, montant);
            stmtDestination.setString(2, numeroDeCompteDestination);
            stmtDestination.executeUpdate();
        }

        CompteBancaire source = comptes.get(numeroDeCompteSource);
        CompteBancaire destination = comptes.get(numeroDeCompteDestination);
        if (source != null && destination != null) {
            source.setSolde(source.getSolde() - montant);
            destination.setSolde(destination.getSolde() + montant);
        }
    }

    public void genererReleve(String numeroCompte) throws SQLException {
        CompteBancaire compte = CompteBancaire.charger(conn, numeroCompte);
        if (compte != null) {
            // Exemple simple d'affichage d'un relevé
            System.out.println("Relevé de compte pour le compte numéro : " + numeroCompte);
            System.out.println("Titulaire : " + compte.getTitulaire());
            System.out.println("Solde : " + compte.getSolde());
            // Ajouter d'autres détails si nécessaire
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
