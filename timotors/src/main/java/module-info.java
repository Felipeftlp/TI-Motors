module com.br {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;

    opens com.br to javafx.fxml;
    exports com.br;

    opens com.br.controller to javafx.fxml;
    exports com.br.controller;
}
