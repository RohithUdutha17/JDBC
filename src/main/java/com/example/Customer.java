package com.example;

import java.sql.*;

public class Customer {
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

    public void createTables(){
        try{
            stat = con.createStatement();
            con.setAutoCommit(false);   
            
            String query1 = "create table customer(customerId int primary key,firstname varchar(20),lastName varchar(20),phoneNumber varchar(11),billAmount int,billDue int,customerAddress varchar(30),packageId int, foreign key(packageId) references package(packageId))";
            String query2 = "create table package(packageId int primary key,packageName varchar(20),packageRate int,totalDays int)";

            stat.addBatch(query2);
            stat.addBatch(query1);

            int [] results = stat.executeBatch();
            con.commit();
            for(int i:results){
                System.out.println(i);
            }
        }catch(Exception e){
            System.out.println(e);
            try{
                con.rollback();
            }catch(Exception e1){
                System.out.println(e1);
            }
            
        }
    }

    public void insertData(){
        String q1 = "insert into package values(2,'TV',20000,30)";
        String q2 = "insert into customer values(2,'Ram','Kumar','9756789054',15000,5000,'Hyd',1)";
        try{
            con.setAutoCommit(false);
             stat = con.createStatement();    
             
             stat.addBatch(q1);
             stat.addBatch(q2);

             stat.executeBatch();

             con.commit();

        }catch(Exception e){
            System.out.println(e);
            try{
                con.rollback();
            }catch(Exception e1){
                System.out.println(e1);
            }
        }
        
    }

    public void getRecords(){
        String query1 = "select * from customer";
        String query2 = "select * from package";
        try{
            con.setAutoCommit(false);
             stat = con.createStatement();    
             
             stat.addBatch(query1);
             stat.addBatch(query2);

           // ResultSet result =  stat.executeBatch();

             con.commit();

        }catch(Exception e){
            System.out.println(e);
            try{
                con.rollback();
            }catch(Exception e1){
                System.out.println(e1);
            }
        }
    }

    public void dropTable(){
        String query1 = "drop table package";
        String query2 = "drop table customer";
        try{
            con.setAutoCommit(false);
            stat = con.createStatement();
            stat.addBatch(query1);
            stat.addBatch(query2);
            int[] results = stat.executeBatch();
            con.commit();


        }catch(Exception e){
            System.out.println(e);

            try{
                con.rollback();
            }catch(Exception e1){
                System.out.println(e1);
            }
        }
    }

    public void parameterizedDemo(){
        String query = "select * from customer where billAmount > ?";
        try{
            PreparedStatement statq = con.prepareStatement(query);
            statq.setInt(1, 500);
            ResultSet result = statq.executeQuery(query);
            while(result.next()){
                System.out.println(result.getInt(1));
                System.out.println(result.getString(2));
                System.out.println(result.getString(3));
                System.out.println(result.getString(4));
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String args[]){
        Customer c = new Customer();
        c.doConnect();
       // c.dropTable();
        c.createTables();
        c.insertData();
        c.parameterizedDemo();
    }
}
