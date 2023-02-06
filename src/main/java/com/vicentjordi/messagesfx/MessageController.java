package com.vicentjordi.messagesfx;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Users;
import com.vicentjordi.messagesfx.responses.GetLoginResponse;
import com.vicentjordi.messagesfx.responses.GetRegistrationResponse;
import com.vicentjordi.messagesfx.services.ServiceUtils;
import com.vicentjordi.messagesfx.services.nodeServer;
import com.vicentjordi.messagesfx.utils.MessageUtils;
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

public class MessageController {
    //LoginView
    @FXML private Hyperlink hlinkRegister;
    @FXML private Button btnLogin;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private Label lblErrorLogin;
    //RegisterView
    @FXML private Button btnCancelRegister;
    @FXML private Button btnRegister;
    @FXML private Button btnSelectImgRegister;
    @FXML private TextField txtUsernameRegister;
    @FXML private TextField txtPasswordRegister;
    @FXML private TextField txtRepeatPass;
    @FXML private Label lblError;
    @FXML private ImageView ivRegisterPhoto;
    //MessageView

    //WebService

    //Var
    private String data;
    private String name;
    private String loginName;
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
        //Se comprueba que el nombre de usuario y la contraseña no esten vacias
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            lblErrorLogin.setText("There are one or more fields empty");
        }else{
            //Se hace el post al login
            Users logUser = new Users(txtUsername.getText().trim(), txtPassword.getText().trim());
            Gson gson = new Gson();
            String resp = ServiceUtils.getResponse(nodeServer.getServer() + "/login",
                    gson.toJson(logUser), "POST");
            GetLoginResponse loginResp = gson.fromJson(resp, GetLoginResponse.class);
            if(loginResp.isOk()){
                ServiceUtils.setToken(loginResp.getToken());
                loginName = loginResp.getName();
                data = loginResp.getImage();
            }else{
               lblErrorLogin.setText(loginResp.getError());
            }
        }
    }
    public void cancelRegistration(ActionEvent actionEvent) throws IOException {
        cleanReagistration();
        //Mostar la vista de RegisterView
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene loginScene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
    }
    public void registration(ActionEvent actionEvent) throws  IOException{
        //Se comprueba que el nombre de usuario y las contraseñas no esten vacias
        if(txtUsernameRegister.getText().isEmpty() || txtPasswordRegister.getText().isEmpty() || txtRepeatPass.getText().isEmpty()){
            lblError.setText("There are one or more fields empty");
        }else{
            //Comparar las dos contraseñas para ver si son las mismas
            if(!txtPasswordRegister.getText().equals(txtRepeatPass.getText())){
                lblError.setText("Passwords don't match up");
            }else{
                //ver que hay una foto de usuario
                if(ivRegisterPhoto.getImage() == null){
                    lblError.setText("Set a profile photo.");
                }else{
                    Users newUser = new Users(txtUsernameRegister.getText().trim(), txtPasswordRegister.getText().trim(), data);
                    Gson gson = new Gson();
                    String resp = ServiceUtils.getResponse(nodeServer.getServer() + "/register",
                                        gson.toJson(newUser), "POST");
                    GetRegistrationResponse regResponse = gson.fromJson(resp, GetRegistrationResponse.class);
                    if(regResponse.isOk()){
                        MessageUtils.registrationSuccess();
                        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
                        Scene loginScene = new Scene(root);
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setTitle("Login");
                        stage.setScene(loginScene);
                        stage.show();
                    }else{
                        MessageUtils.registrationFail(regResponse.getError());
                    }
                }
            }
        }
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
            MessageUtils.fileError();
        }
    }
    public void cleanReagistration(){
        //Vaciar los TextFields y el ImageView
        txtUsernameRegister.clear();
        txtPasswordRegister.clear();
        txtRepeatPass.clear();
        ivRegisterPhoto.setImage(null);
    }


}