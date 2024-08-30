package db;

import dto.Item;
import dto.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabaseConnector {
    private static DatabaseConnector connector;

    private static List<Task> list = new ArrayList<>();
    private static List<Item> item = new ArrayList<>();


    public DatabaseConnector getInstant(){
        if(connector==null){
            connector = new DatabaseConnector();
        }
        return connector;
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/todolist","root","12345");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Task> getList(){
        return list;
    }

    public static List<Item> getItem(){
        return item;
    }

    public static List<Task> getFinishedList(){
        String SQL="SELECT * FROM completed";
        List<Task> finishedlist = new ArrayList<>();
        try {
            Statement stm = getConnection().prepareStatement(SQL);
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()){
                finishedlist.add(new Task(rst.getString("task_title"),rst.getInt("task_id"), rst.getDate("completion_date").toLocalDate()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return finishedlist;
    }

    public static List<Task> getFinishedList(String temp){
        String SQL="SELECT * FROM completed ORDER BY task_id";
        List<Task> finishedlist = new ArrayList<>();
        try {
            Statement stm = getConnection().prepareStatement(SQL);
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()){
                finishedlist.add(new Task(rst.getString("task_title"),rst.getInt("task_id"), rst.getDate("completion_date").toLocalDate()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return finishedlist;
    }

    public static List<Task> getFinishedList(LocalDate date){
        String SQL="SELECT * FROM completed WHERE completion_date ='"+date+"'";
        List<Task> finishedlist = new ArrayList<>();
        try {
            Statement stm = getConnection().prepareStatement(SQL);
            ResultSet rst = stm.executeQuery(SQL);
            while (rst.next()){
                finishedlist.add(new Task(rst.getString("task_title"),rst.getInt("task_id"), rst.getDate("completion_date").toLocalDate()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return finishedlist;
    }

    public static void storedata(Task finished){
        try {
            String SQL = "INSERT INTO completed VALUES('"
                    +finished.getId()+"','"
                    +finished.getName()+"','"
                    +finished.getDate()+"')";
            Statement stm = getConnection().prepareStatement(SQL);
            stm.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
