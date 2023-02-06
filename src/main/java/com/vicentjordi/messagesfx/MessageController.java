package com.vicentjordi.messagesfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Base64;

import static com.vicentjordi.messagesfx.utils.errorMessages.errorFichero;

public class MessageController {
    //LoginView
    @FXML private Hyperlink hlinkRegister;
    @FXML private Button btnLogin;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    //RegisterView
    @FXML private Button btnCancelRegister;
    @FXML private Button btnRegister;
    @FXML private Button btnSelectImgRegister;
    @FXML private TextField txtUsernameRegister;
    @FXML private TextField txtPasswordRegister;
    @FXML private TextField txtRepeatPass;
    @FXML private ImageView ivRegisterPhoto;
    //MessageView

    //WebService

    //Var
    private String data;
    private String name;
    public void hyperLinkRegister(ActionEvent actionEvent) throws IOException {
        //Mostar la vista de RegisterView
        Parent root = FXMLLoader.load(getClass().getResource("RegisterView.fxml"));
        Scene registerScene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Register");
        stage.setScene(registerScene);
        stage.show();
    }
    public void logIn(ActionEvent actionEvent) {
    }
    public void cancelRegistration(ActionEvent actionEvent) throws IOException {
        //Vaciar los TextFields y el ImageView
        txtUsernameRegister.clear();
        txtPasswordRegister.clear();
        txtRepeatPass.clear();
        ivRegisterPhoto.setImage(null);
        //Mostar la vista de RegisterView
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene loginScene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Register");
        stage.setScene(loginScene);
        stage.show();
    }
    public void registration(ActionEvent actionEvent) {
    }
    public void selectImage(ActionEvent actionEvent) throws MalformedURLException {
        byte[] bytes;
        data = "";
        FileChooser fc = new FileChooser();
        //Filtro extension
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png");
        fc.setTitle("Select a Image");
        fc.getExtensionFilters().add(ex1);
        File selectedFile = fc.showOpenDialog(new Stage());
        //Comprobar que se ha selecionado una imagen
        if(selectedFile != null){
            Image image = new Image(selectedFile.toURI().toURL().toString());
            ivRegisterPhoto.setImage(image);
            name = selectedFile.getName();
            try {
                bytes = Files.readAllBytes(selectedFile.toPath());
                data = Base64.getEncoder().encodeToString(bytes);
                System.out.println(data);
            } catch (IOException error){
                System.out.println(error);
            }
        }else{
            errorFichero();
        }
    }
}