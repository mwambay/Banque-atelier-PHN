package org.example.bankapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.bankapp.banques.Banque;
import org.example.bankapp.banques.CompteBancaire;
import org.example.bankapp.banques.CompteCourant;
import org.example.bankapp.banques.CompteEpargne;
import org.example.bankapp.connection.ManageDatabase;

import java.sql.SQLException;

import static java.lang.Double.parseDouble;
import static org.example.bankapp.banques.Banque.manage;

public class BanqueControl {

    @FXML
    private TextField numch, nomch,typech, montantch, tauxch,decouvertech;
    @FXML
    private Button ajoutbtn;

    ManageDatabase manege=new ManageDatabase();
    public void ajouter(ActionEvent actionEvent) throws SQLException {
        CompteBancaire enregistrement = null;
        try {
            Banque compte=new Banque();
            if(typech.getText().equals("CC")){
            CompteEpargne cc=new CompteEpargne(numch.getText(),nomch.getText(),parseDouble(montantch.getText()),parseDouble(decouvertech.getText()));
            enregistrement=cc;
            } else if (typech.getText().equals("CE")) {
                CompteCourant ce=new CompteCourant(numch.getText(),nomch.getText(),parseDouble(montantch.getText()),parseDouble(tauxch.getText()));
                enregistrement=ce;
            }

            compte.ajouterCompte(enregistrement);

        //manege.inserer(numch.getText(),nomch.getText(),parseDouble(montantch.getText()),parseDouble(tauxch.getText()));
    } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
