package com.vicentjordi.messagesfx.utils;

import javafx.scene.control.Alert;

public class errorMessages {
    public static void errorFichero(){
        Alert dialog = new Alert(Alert.AlertType.ERROR);

        dialog.setTitle("Fichero");
        dialog.setHeaderText("Sin fichero seleccionado");
        dialog.setContentText("No se ha seleccionado ningún archivo.");
        dialog.showAndWait();
    }//end_errorFichero
}
