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

    public static void sendMessageFail(){
        Alert dialog = new Alert(Alert.AlertType.ERROR);

        dialog.setTitle("Error Sending Message");
        dialog.setHeaderText("We found an error in the message.");
        dialog.setContentText("You don't select a User or the body of Message is empty");
        dialog.showAndWait();
    }
    public static void messageSendSuccess(){
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);

        dialog.setTitle("Message  Send");
        dialog.setHeaderText("It was sent correctly.");
        dialog.setContentText("The message arrived with any problem");
        dialog.showAndWait();
    }

    public static void deleteMessageFail(){
        Alert dialog = new Alert(Alert.AlertType.ERROR);

        dialog.setTitle("Error Deleting Message");
        dialog.setHeaderText("We found an error deleting the message.");
        dialog.setContentText("You don't select Message");
        dialog.showAndWait();
    }
}
