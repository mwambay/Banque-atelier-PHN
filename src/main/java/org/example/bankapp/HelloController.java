package org.example.bankapp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.bankapp.banques.Banque;
import org.example.bankapp.banques.CompteBancaire;
import org.example.bankapp.banques.CompteCourant;
import org.example.bankapp.banques.CompteEpargne;
import org.example.bankapp.connection.ConnectDatabase;
import org.example.bankapp.connection.ManageDatabase;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static java.lang.Double.parseDouble;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private Button btn_quit,cont;

    @FXML
    private TableView <Enregistrement>table;

    @FXML
    private TableColumn<Enregistrement, String> numCol;
    @FXML
    private TableColumn<Enregistrement, String> tituCol;
    @FXML
    private TableColumn<Enregistrement, Double> soldCol;
    @FXML
    private TableColumn<Enregistrement, String> typCol;
    @FXML
    private TableColumn<Enregistrement, Double> decouvCol;
    @FXML
    private TableColumn<Enregistrement, Double> tauxCol;

    @FXML
      private Pane command;

    Enregistrement compte;
    static ConnectDatabase conn=new ConnectDatabase();
    static Connection connection=conn.connected();


    @FXML
    public void ajouterCompte(ActionEvent mouseEvent) throws IOException {

        command.getChildren().setAll(pane());

    }
    @FXML
    public void rechercherCompteType(MouseEvent mouseEvent) {
    }
    @FXML
    public void fermer(ActionEvent actionEvent) {
        Platform.exit();
    }
    @FXML
    public  TableView afficherElements() {

        chargerDonnees();
        return null;
    }


    private void chargerDonnees() {
        ObservableList<Enregistrement>compte = FXCollections.observableArrayList();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM CompteBancaire")) {

            while (resultSet.next()) {
                String num = resultSet.getString(1);
                String titulaire = resultSet.getString(2);
                Double solde= resultSet.getDouble(3);
                String type = resultSet.getString(4);
                Double decouverte = resultSet.getDouble(5);
                Double taux = resultSet.getDouble(6);

                compte.add(new Enregistrement(num,titulaire,solde,type,decouverte,taux));
            }


            numCol.setCellValueFactory(new PropertyValueFactory<Enregistrement,String>("numeroCompte"));
            tituCol.setCellValueFactory(new PropertyValueFactory<Enregistrement,String>("titulaire"));
            soldCol.setCellValueFactory(new PropertyValueFactory<Enregistrement,Double>("solde"));
            typCol.setCellValueFactory(new PropertyValueFactory<Enregistrement,String>("TYPE"));
            decouvCol.setCellValueFactory(new PropertyValueFactory<Enregistrement,Double>("decouvertAutorise"));
            tauxCol.setCellValueFactory(new PropertyValueFactory<Enregistrement,Double>("taux"));

            table.setItems(compte);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void acceuil(ActionEvent ev) throws IOException {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage.setTitle("AROBANK");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private static TextField numCh;
    @FXML
    private static TextField nomch;
    @FXML
    private static TextField typech;
    @FXML
    private static TextField montantch;
    @FXML
    private static TextField tauxch;
    @FXML
    private static TextField decouvertech;
    @FXML
    private Button ajoutbtn;
    @FXML
            private  Button adbtn;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chargerDonnees();
    }

    public static VBox pane() {
        // Créer un VBox pour contenir tous les éléments
        VBox vbox = new VBox(10); // 10 est l'espacement entre les éléments

        // Créer les champs de texte
        TextField numCh = createTextField("numCh");
        TextField nomCh = createTextField("nomCh");
        TextField montantCh = createTextField("montantCh");
        TextField typeCh = createTextField("typeCh");
        TextField decouvertCh = createTextField("decouvertCh");
        TextField tauxCh = createTextField("tauxCh");

        // Ajouter les éléments au VBox
        vbox.getChildren().addAll(
                new Label("Numero compte"), numCh,
                new Label("Titulaire"), nomCh,
                new Label("Solde"), montantCh,
                new Label("Type"), typeCh,
                new Label("Découvert"), decouvertCh,
                new Label("Taux"), tauxCh,
                createButton("Ajouter", "ajoutBtn", numCh, nomCh, montantCh, typeCh, decouvertCh, tauxCh)
        );

        return vbox;
    }


    private static TextField createTextField(String id) {
        TextField textField = new TextField();
        textField.setId(id);
        textField.getStyleClass().add("text-field");
        return textField;
    }

    private static Button createButton(String text, String id, TextField numCh, TextField nomCh, TextField montantCh, TextField typeCh, TextField decouvertCh, TextField tauxCh) {
        Button button = new Button(text);
        button.setId(id);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                CompteBancaire enregistrement = null;
                try {
                    Banque compte = new Banque();
                    if (typeCh.getText().equals("CC")) {
                        CompteEpargne cc = new CompteEpargne(numCh.getText(), nomCh.getText(), Double.parseDouble(montantCh.getText()), Double.parseDouble(decouvertCh.getText()));
                        enregistrement = cc;
                    } else if (typeCh.getText().equals("CE")) {
                        CompteCourant ce = new CompteCourant(numCh.getText(), nomCh.getText(), Double.parseDouble(montantCh.getText()), Double.parseDouble(tauxCh.getText()));
                        enregistrement = ce;
                    }
                    compte.ajouterCompte(enregistrement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        return button;
    }


}