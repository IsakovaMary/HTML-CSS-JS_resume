package com.example.diplom_fx2;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class LinkController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label logo_name, info;

    @FXML
    private Button btn_add;

    ObservableList<String> short_links = FXCollections.observableArrayList();
    @FXML
    private ListView<String> listLink = new ListView<String>();

    @FXML
    private TextField long_link;

    @FXML
    private TextField short_link;

    private final DB db = new DB();
    Map<String, String> links = new HashMap<>();
    ArrayList<String> arrayLink = new ArrayList<>();


    @FXML
    void initialize() throws SQLException, IOException {
        viewLinkList();
        btn_add.setOnAction(event -> {
            try {
                addLinks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }
    private void addLinks() throws SQLException {

        String longLink = long_link.getCharacters().toString();
        String shortLink = short_link.getCharacters().toString();

        long_link.setStyle("-fx-border-color: #fafafa");
        short_link.setStyle("-fx-border-color: #fafafa");

        if(longLink.length() <= 4 || !longLink.contains(".")){
            long_link.setStyle("-fx-border-color: #e06249");
            info.setText("Неправильная основная ссылка!");
        }
        else if (shortLink.length() <= 2 ){
            short_link.setStyle("-fx-border-color: #e06249");
            info.setText("Слишком короткое сокращение");}
        else if (db.isExistsLink(shortLink)) {
            info.setText("Укажите другое сокращение");
        } else {
            db.addLink(longLink, shortLink);
            if(!short_link.getText().isEmpty() && !long_link.getText().isEmpty()){
                listLink.getItems().clear();
                viewLinkList();
                arrayLink.add(shortLink);
                }

            long_link.setText("");
            short_link.setText("");
            info.setText("Всё готово :)");
        }
    }

    private void viewLinkList() throws SQLException {
        ResultSet resultSet = db.getShort();
        while (resultSet.next()) {
            short_links.add(resultSet.getString("short_link"));
            }
            listLink.setItems(short_links);
            }

}
//
//db.addLink(longLink, shortLink);
//        links.put(shortLink, longLink);
//        if(!short_link.getText().isEmpty() && !long_link.getText().isEmpty()){
//        listLink.getItems().clear();
//
//        for(Map.Entry<String, String> entry : links.entrySet()) {
//        String short_link = entry.getKey();
//        String long_link = entry.getValue();
//        viewLinkList();
//        arrayLink.add(short_link);
//        }
//        }
