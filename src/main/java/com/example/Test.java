package com.example;
import java.sql.*;
public class Test {
    public static final String URL = "jdbc:mysql://localhost:3306/world";
    public static final String USERNAME ="root";
    public static final String PASSWORD ="1234";
    Statement stat;
    Connection con;
    public void doConnect(){
        try{
             con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
                System.out.println("Connection done  "+con.getMetaData().getDatabaseProductName());

        }catch(Exception e){
            System.out.println("failed"+e);
        }
    }
    public void executeStatements(){
        String query = "create table items(itemId int primary key,itemName varchar(20))";
        try{
             stat = con.createStatement();
             boolean result = stat.execute(query);
             if(result){
                System.out.println("Table not created");
             }else{
                System.out.println("Table created");
             }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void insertRecords(){
        String query = "insert into items values(1,'Coffee')";
        try{
            int recordsAffected = stat.executeUpdate(query);
            System.out.println(recordsAffected);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void getRecords(){
        String query = "select * from items";
        try{
            ResultSet result = stat.executeQuery(query);
            while(result.next()){
                System.out.println("ItemId : "+result.getInt("itemId"));
                System.out.println("Item Name :"+result.getString("itemName"));
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void updateRecords(){
        String query="update items set itemName='Coffee' where itemId=1";
        try{
            int recordsAffected = stat.executeUpdate(query);
            System.out.println(recordsAffected);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void insertPreparedStatementDemo(){
        String query = "insert into items(itemId,itemName) values(?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, 3);
            ps.setString(2, "Latte");

            int recordsAffected = ps.executeUpdate();

            System.out.println(recordsAffected);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void updatePreparedStatementDemo(){
        String query = "update items set itemName=? where itemId=?";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(2, 2);
            ps.setString(1,"tea");

            int recordsAffected = ps.executeUpdate();
            System.out.println(recordsAffected);
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
    public static void main(String args[])
    {
        Test t =new Test();
        t.doConnect();
        t.executeStatements();
        t.insertRecords();
        t.getRecords();
        t.updateRecords();
        t.getRecords();
       // t.insertPreparedStatementDemo();
        t.getRecords();
        t.updatePreparedStatementDemo();
        t.getRecords();
    }
}
