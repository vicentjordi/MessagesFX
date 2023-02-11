package com.vicentjordi.messagesfx;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Users;
import com.vicentjordi.messagesfx.responses.GetRegistrationResponse;
import com.vicentjordi.messagesfx.services.ServiceUtils;
import com.vicentjordi.messagesfx.services.nodeServer;
import com.vicentjordi.messagesfx.services.registerUser;
import com.vicentjordi.messagesfx.utils.MessageUtils;
import com.vicentjordi.messagesfx.utils.ScreenLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class RegisterController {
    @FXML private TextField txtUsernameRegister;
    @FXML private TextField txtPasswordRegister;
    @FXML private TextField txtRepeatPass;
    @FXML private Label lblError;
    @FXML private ImageView ivRegisterPhoto;

    private registerUser register;
    private String data;
    private String name;
    public void cancelRegistration(ActionEvent actionEvent) throws IOException {
        cleanReagistration();
        //Mostar la vista de RegisterView
        ScreenLoader.loadScreen("LoginView.fxml", (Stage)
                ((Node) actionEvent.getSource()).getScene().getWindow());
    }
    public void registration(ActionEvent actionEvent){
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
                    register = new registerUser(newUser);
                    register.start();
                    register.setOnSucceeded(e -> {
                        Gson gson = new Gson();
                        GetRegistrationResponse regResponse = gson.fromJson(register.getValue(), GetRegistrationResponse.class);
                        if(regResponse.isOk()){
                            MessageUtils.registrationSuccess();
                            try {
                                ScreenLoader.loadScreen("LoginView.fxml", (Stage)
                                        ((Node) actionEvent.getSource()).getScene().getWindow());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }else{
                            MessageUtils.registrationFail(regResponse.getError());
                        }
                    });
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

    private void cleanReagistration(){
        //Vaciar los TextFields y el ImageView
        txtUsernameRegister.clear();
        txtPasswordRegister.clear();
        txtRepeatPass.clear();
        ivRegisterPhoto.setImage(null);
    }
}
