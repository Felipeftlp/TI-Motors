module com.br {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires javafx.base;
    requires transitive javafx.graphics;

    opens com.br to javafx.fxml;
    exports com.br;

    opens com.br.controller to javafx.fxml;
    exports com.br.controller;

    opens com.br.model to javafx.base; 
}
