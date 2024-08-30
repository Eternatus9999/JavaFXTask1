package controller;

import com.jfoenix.controls.JFXTextField;
import db.DatabaseConnector;
import dto.Item;
import dto.Task;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public class DoListFormController {

    public List<Task> templist = DatabaseConnector.getList();
    public List<Item> item = DatabaseConnector.getItem();
    public int value =1;

    @FXML
    private TableColumn<?,?> coltask;

    @FXML
    private ListView<Item> list;

    @FXML
    private TableView<Task> table;

    @FXML
    private JFXTextField txttask;

    @FXML
    void OnAddAction(ActionEvent event) {
            addTask();
    }

    @FXML
    void OnHistoryAction(ActionEvent event) {
        viewHistory();
    }

    private void addTask(){
        if(!txttask.getText().equals("")){
            int x = generate();
            templist.add(new Task(txttask.getText(), x,LocalDate.now()));
            item.add(new Item(txttask.getText(), false,x));
            list.getItems().clear();
            for(Item a: item){
                a.stateProperty().addListener(((observableValue, aBoolean, t1) -> {
                    check(t1, a.getName());
                }));
                list.getItems().add(a);
            }
            loadList(list);
            txttask.setText(null);
        }
    }

    private int generate(){
        List<Task> list = DatabaseConnector.getFinishedList();
        if(list.isEmpty()){
            return value++;
        }else if(value==1){
            value =  list.get(list.size()-1).getId()+1;
        }
        System.out.println(value);
        return value++;
    }

    private void loadList(ListView<Item> list){
        list.setCellFactory(CheckBoxListCell.forListView(new Callback<Item, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Item item) {
                return item.stateProperty();
            }
        }));
    }

    private void check(Boolean t1, String name){
        if(t1){
            for (int i = 0; i <item.size(); i++) {
                if(item.get(i).getName().equals(name)){
                    remove(i);
                    addTable();
                    break;
                }
            }
        }
    }

    private void addTable(){
        coltask.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getItems().clear();
        table.refresh();
        table.setItems(FXCollections.observableArrayList(DatabaseConnector.getFinishedList()));

    }

    private void remove(int index){
        item.remove(index);
        DatabaseConnector.storedata(templist.remove(index));
        list.getItems().remove(index);
    }

    private void viewHistory(){
        try {
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/historylistform.fxml"))));
            stage1.show();
            stage1.setResizable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
