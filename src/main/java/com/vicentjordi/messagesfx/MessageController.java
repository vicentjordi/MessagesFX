package com.vicentjordi.messagesfx;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Messages;
import com.vicentjordi.messagesfx.models.Users;
import com.vicentjordi.messagesfx.responses.*;
import com.vicentjordi.messagesfx.services.*;
import com.vicentjordi.messagesfx.utils.MessageUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;


public class MessageController implements Initializable {
    @FXML public AnchorPane ap;
    @FXML private static Label lblUsername;
    @FXML private TableColumn tcAvatar;
    @FXML private TableColumn tcNickname;
    @FXML private TableView tvUsers;
    @FXML private TableColumn tcMessage;
    @FXML private TableColumn tcImage;
    @FXML private TableColumn tcSent;
    @FXML private TableView tvMessages;
    @FXML private TextField txtMessage;
    @FXML private ImageView ivSendImage;
    @FXML private ImageView ivUsernamePhoto;

    private getUsers allUsers;
    private getMessages allMessages;
    private postMessage sendMessage;
    private putUser updateUser;
    private deleteMessage delMessage;

    private ObservableList<Users> users;
    private ObservableList<Messages> messages;

    private String data;
    private String name;

    public MessageController() {
    }

    @FXML public void loadData(){
        Stage stage = (Stage) this.ap.getScene().getWindow();
        Users user = (Users) stage.getUserData();
        lblUsername.setText(user.getName());
    }

    public void changeImage(ActionEvent actionEvent) throws IOException {
        UpdateImage();
        Users user = new Users(data);
        updateUser = new putUser(user);
        updateUser.start();

        updateUser.setOnSucceeded( e-> {
            Gson gson = new Gson();
            GetPutResponse putResponse = gson.fromJson(updateUser.getValue(), GetPutResponse.class);
           if(putResponse.isOk()){
               System.out.println("ok");
               data="";
           }else{
               System.out.println(putResponse.getError());
           }
        });
    }

    public void refreshData(ActionEvent actionEvent) {
        getAllMessages();
        getAllUsers();
    }

    public void deleteMessage(ActionEvent actionEvent) {
        //Se comprueba que hay un mensaje seleccionado para eliminar
        if (tvMessages.getSelectionModel().isEmpty()){
            MessageUtils.deleteMessageFail();
        }else {
            //Se obtiene el index del mensaje seleccionado en la tabla
            int index = tvMessages.getSelectionModel().getSelectedIndex();
            //Se guarda el mensaje usando el index
            Messages message = (Messages) tvMessages.getItems().get(index);
            //Obtenemos el id del mensaje a eliminar
            System.out.println(message.getId());

            delMessage = new deleteMessage(message.getId());
            delMessage.start();
            delMessage.setOnSucceeded( e -> {
                Gson gson = new Gson();
                GetDeleteResponse deleteResponse = gson.fromJson(delMessage.getValue(), GetDeleteResponse.class);
                if(deleteResponse.isOk()){
                    //Si no hay ningún error actualizaremos la lista de mensajes
                    getAllMessages();
                }
            });
        }
    }

    public void sendMessage(ActionEvent actionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("d/MMM/yyyy");

        //Se comprueba que hay un usuario seleccionado para enviar el mensaje y que el campo del mensaje no esté vacio
        if (tvUsers.getSelectionModel().isEmpty() || txtMessage.getText().isEmpty()){
            MessageUtils.sendMessageFail();
        }else{
            //Se obtiene el index del usuario seleccionado en la tabla
            int index = tvUsers.getSelectionModel().getSelectedIndex();
            //Se guarda el usuario usando el index
            Users user = (Users) tvUsers.getItems().get(index);
            //Obtenemos el id del usuario para enviar el mensaje
            System.out.println(user.getId());

            String date = dateFormat.format(new Date());
            System.out.println(date);

            Messages message = new Messages(txtMessage.getText().toString(), data, date);
            message.setTo(user.getId());

            sendMessage = new postMessage(message);
            sendMessage.start();
            sendMessage.setOnSucceeded(e -> {
                MessageUtils.messageSendSuccess();
            });

        }

    }

    public void selectImg(ActionEvent actionEvent) throws IOException{
        byte[] bytes;
        data = "";
        name="";
        FileChooser fc = new FileChooser();
        //Filtro extension
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png");
        fc.setTitle("Select a Image");
        fc.getExtensionFilters().add(ex1);
        File selectedFile = fc.showOpenDialog(new Stage());
        //Comprobar que se ha selecionado una imagen
        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toURL().toString());
            ivSendImage.setImage(image);
            name = selectedFile.getName();
            try {
                bytes = Files.readAllBytes(selectedFile.toPath());
                data = Base64.getEncoder().encodeToString(bytes);
            } catch (IOException error){
                System.out.println(error);
            }
        }else{
            MessageUtils.fileError();
        }
    }
    public void UpdateImage() throws IOException{
        byte[] bytes;
        data = "";
        name="";
        FileChooser fc = new FileChooser();
        //Filtro extension
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png");
        fc.setTitle("Select a Image");
        fc.getExtensionFilters().add(ex1);
        File selectedFile = fc.showOpenDialog(new Stage());
        //Comprobar que se ha selecionado una imagen
        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toURL().toString());
            ivUsernamePhoto.setImage(image);
            name = selectedFile.getName();
            try {
                bytes = Files.readAllBytes(selectedFile.toPath());
                data = Base64.getEncoder().encodeToString(bytes);
            } catch (IOException error){
                System.out.println(error);
            }
        }else{
            MessageUtils.fileError();
        }
    }

    public void getAllMessages(){
        allMessages = new getMessages();
        allMessages.start();
        allMessages.setOnSucceeded( i -> {
            //Obtenemos los registros de node y los guardamos en una ObsevableList
            messages = FXCollections.observableList(allMessages.getValue());

            //Asignamos a cada columna de la tabla sus valores
            tcMessage.setCellValueFactory(new PropertyValueFactory<Messages, String>("message"));
            tcImage.setCellValueFactory(new PropertyValueFactory<Messages, ImageView>("image"));
            tcSent.setCellValueFactory(new PropertyValueFactory<Messages, String>("sent"));

            //Añadimos los items de la lista a la tabla
            tvMessages.setItems(messages);
        });
    }
    public void getAllUsers(){
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllMessages();
        getAllUsers();
    }

}