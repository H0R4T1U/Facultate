module org.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.httpserver;


    opens org.example.lab6 to javafx.fxml;
   // exports org.example.lab6;
    exports org.example.lab6.service;
    exports org.example.lab6.controller;
    exports org.example.lab6.domain;
    exports org.example.lab6.repository;
}