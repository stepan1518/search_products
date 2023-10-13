module com.example.searchproduct {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.desktop;

    opens com.example.searchproduct to javafx.fxml;
    exports com.example.searchproduct;
}