module htl.steyr.taschenrechner {
    requires javafx.controls;
    requires javafx.fxml;


    opens htl.steyr.taschenrechner to javafx.fxml;
    exports htl.steyr.taschenrechner;
}