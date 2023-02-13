package com.vicentjordi.messagesfx;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Users;
import com.vicentjordi.messagesfx.responses.GetLoginResponse;
import com.vicentjordi.messagesfx.responses.GetRegistrationResponse;
import com.vicentjordi.messagesfx.services.ServiceUtils;
import com.vicentjordi.messagesfx.services.loginUser;
import com.vicentjordi.messagesfx.services.nodeServer;
import com.vicentjordi.messagesfx.utils.MessageUtils;
import com.vicentjordi.messagesfx.utils.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController{
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private Label lblErrorLogin;

    private loginUser login;

    public void hyperLinkRegister(ActionEvent actionEvent) throws IOException {
        //Mostar la vista de RegisterView
        ScreenLoader.loadScreen("RegisterView.fxml", (Stage)
                ((Node) actionEvent.getSource()).getScene().getWindow());
    }
    public void logIn(ActionEvent actionEvent) throws IOException{
        //Se comprueba que el nombre de usuario y la contraseña no esten vacias
        if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
            lblErrorLogin.setText("There are one or more fields empty");
        }else{
            //Se hace el post al login

            Users logUser = new Users(txtUsername.getText().trim(), txtPassword.getText().trim());
            login = new loginUser(logUser);
            login.start();

            login.setOnSucceeded(e -> {
                Gson gson = new Gson();
                GetLoginResponse loginResp = gson.fromJson(login.getValue(), GetLoginResponse.class);
                if(loginResp.isOk()){
                    try {
                        ServiceUtils.setToken(loginResp.getToken());

                        ScreenLoader.loadScreen("MessagesView.fxml", (Stage)
                                ((Node) actionEvent.getSource()).getScene().getWindow());

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    lblErrorLogin.setText(loginResp.getError());
                }
            });
        }
    }

}
