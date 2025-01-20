module com.br {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires io.github.cdimascio.dotenv.java;

    opens com.br to javafx.fxml;
    exports com.br;

    opens com.br.controller to javafx.fxml;
    exports com.br.controller;
    exports com.br.model;

    opens com.br.model to javafx.base; 
}
