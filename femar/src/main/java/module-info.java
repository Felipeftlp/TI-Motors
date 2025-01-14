module com.br {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.br to javafx.fxml;
    exports com.br;
}
