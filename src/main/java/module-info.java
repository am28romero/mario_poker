module com.oblivion {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.oblivion to javafx.fxml;
    exports com.oblivion;
}
