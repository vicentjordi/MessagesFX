module com.vicentjordi.messagesfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.vicentjordi.messagesfx to javafx.fxml;
    exports com.vicentjordi.messagesfx;
}