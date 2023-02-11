module com.vicentjordi.messagesfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;

    opens com.vicentjordi.messagesfx to javafx.fxml;
    opens com.vicentjordi.messagesfx.models to com.google.gson, javafx.base;
    opens com.vicentjordi.messagesfx.responses to com.google.gson;
    exports com.vicentjordi.messagesfx;
}