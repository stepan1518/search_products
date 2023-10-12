module com.example.searchproduct {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.jsoup;


    opens com.example.searchproduct to javafx.fxml;
    exports com.example.searchproduct;
}