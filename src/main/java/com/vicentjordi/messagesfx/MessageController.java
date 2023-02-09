package com.vicentjordi.messagesfx;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Users;
import com.vicentjordi.messagesfx.responses.GetLoginResponse;
import com.vicentjordi.messagesfx.responses.GetRegistrationResponse;
import com.vicentjordi.messagesfx.services.ServiceUtils;
import com.vicentjordi.messagesfx.services.nodeServer;
import com.vicentjordi.messagesfx.utils.MessageUtils;
import com.vicentjordi.messagesfx.utils.ScreenLoader;
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
    @FXML private Label lblUsername;



}