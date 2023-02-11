package com.vicentjordi.messagesfx;

import com.vicentjordi.messagesfx.models.Messages;
import com.vicentjordi.messagesfx.models.Users;
import com.vicentjordi.messagesfx.services.getUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class MessageController implements Initializable {
    @FXML public AnchorPane ap;
    @FXML private static Label lblUsername;
    @FXML private TableColumn tcAvatar;
    @FXML private TableColumn tcNickname;
    @FXML private TableView tvUsers;

    private getUsers allUsers;

    private ObservableList<Users> users;
    private ObservableList<Messages> messages;

    @FXML public void loadData(){
        Stage stage = (Stage) this.ap.getScene().getWindow();
        Users user = (Users) stage.getUserData();
        lblUsername.setText(user.getName());
    }

    public void changeImage(ActionEvent actionEvent) {
    }

    public void refreshData(ActionEvent actionEvent) {

        allUsers = new getUsers();
        allUsers.start();
        allUsers.setOnSucceeded( e -> {
            users = FXCollections.observableList(allUsers.getValue());

            tcAvatar.setCellValueFactory(new PropertyValueFactory<Users, ImageView>("image"));
            tcNickname.setCellValueFactory(new PropertyValueFactory<Users, String>("name"));

            tvUsers.setItems(users);
        });
        allUsers.setOnFailed( ex -> {
            System.out.println("Error");
        });

    }

    public void deleteMessage(ActionEvent actionEvent) {
    }

    public void sendMessage(ActionEvent actionEvent) {
        //Se obtiene el index del usuario seleccionado en la tabla
        int index = tvUsers.getSelectionModel().getSelectedIndex();
        //Se guarda el usuario usando el index
        Users user = (Users) tvUsers.getItems().get(index);
        //Obtenemos el id del usuario para enviar el mensaje
        System.out.println(user.getId());


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void selectImg(ActionEvent actionEvent) {
    }

}