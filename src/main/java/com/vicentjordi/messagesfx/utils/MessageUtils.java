package com.vicentjordi.messagesfx.utils;

import javafx.scene.control.Alert;

public class MessageUtils {
    public static void fileError(){
        Alert dialog = new Alert(Alert.AlertType.ERROR);

        dialog.setTitle("Error Selecting Image File");
        dialog.setHeaderText("Not selected File.");
        dialog.setContentText("There isn't any file selected.");
        dialog.showAndWait();
    }
    public static void registrationSuccess(){
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);

        dialog.setTitle("Register Complete");
        dialog.setHeaderText("You have successfully registered.");
        dialog.setContentText("You will be redirected to the login page.");
        dialog.showAndWait();
    }
    public static void registrationFail(String problem){
        Alert dialog = new Alert(Alert.AlertType.ERROR);

        dialog.setTitle("Register Fails");
        dialog.setHeaderText("Find a problem in the registration.");
        dialog.setContentText(problem);
        dialog.showAndWait();
    }

}
