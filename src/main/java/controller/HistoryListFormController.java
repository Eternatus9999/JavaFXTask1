package controller;

import db.DatabaseConnector;
import dto.Task;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HistoryListFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> coltask;

    @FXML
    private TableView<Task> table;

    @FXML
    private DatePicker txt;

    @FXML
    void OnSearchAction(ActionEvent event) {
        search();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        load();
    }

    private void load(){
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltask.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.setItems(FXCollections.observableArrayList(DatabaseConnector.getFinishedList("Order")));
    }

    private void load(LocalDate date){
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltask.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.setItems(FXCollections.observableArrayList(DatabaseConnector.getFinishedList(date)));
    }

    private void search(){
        load(txt.getValue());
    }
}
