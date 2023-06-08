module mx.uv.fei.gui.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires mysql.connector.j;
    requires java.sql;
    
    opens mx.uv.fei.logic to javafx.base;
    opens mx.uv.fei.gui.controller to javafx.fxml;
    exports mx.uv.fei.gui.controller;
}
