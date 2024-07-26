package org.example.bankapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.bankapp.banques.Banque;
import org.example.bankapp.banques.CompteBancaire;
import org.example.bankapp.banques.CompteCourant;
import org.example.bankapp.banques.CompteEpargne;
import org.example.bankapp.connection.ManageDatabase;

import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Double.parseDouble;
import static org.example.bankapp.banques.Banque.manage;

public class BanqueControl {

    @FXML
    private TextField numCh, nomch,typech, montantch, tauxch,decouvertech;
    @FXML
    private Button ajoutbtn;

    ManageDatabase manege=new ManageDatabase();
    public void ajouter(ActionEvent actionEvent) throws SQLException {
        CompteBancaire enregistrement = null;
        try {
            Banque compte=new Banque();
            if(typech.getText().equals("CC")){
            CompteEpargne cc=new CompteEpargne(numCh.getText(),nomch.getText(),parseDouble(montantch.getText()),parseDouble(decouvertech.getText()));
            enregistrement=cc;
            } else if (typech.getText().equals("CE")) {
                CompteCourant ce=new CompteCourant(numCh.getText(),nomch.getText(),parseDouble(montantch.getText()),parseDouble(tauxch.getText()));
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

    public void retourAcceuil(ActionEvent actionEvent) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage.setTitle("AROBANK");
        stage.setScene(scene);
        stage.show();

    }
}
