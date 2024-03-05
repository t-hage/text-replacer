module com.tom.textreplacer {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.tom.textreplacer to javafx.fxml;
    exports com.tom.textreplacer;
}