/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jdbc;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class SetUpMyUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Boolean running = true;
        MyDB mydb = new MyDB();
        /*
        * drop table first for a clean start
        * may cause error if table does not exist
         */
        //mydb.dropMyuserTable();
        //mydb.createMyuserTable();
        //ArrayList<Myuser> aList = prepareMyuserData();
        //mydb.addRecords(aList);

        /**/
        Myuser test = new Myuser("000009", "John Smith", "123456",
                "psmith@swin.edu.au", "9876543210", "Swinburne EN510f",
                "What is my name?", "John");
        Myuser test3 = new Myuser("000001", "Matt Smith", "333333",
                "psmith@swin.edu.au", "9876543210", "Swinburne EN510f",
                "What is my name?", "John");
        while (running) {
            String typeCode = "";

            System.out.println("\nWhat would you like to do?"
                    + "\n1: Test Create Record"
                    + "\n2: Test Get Record"
                    + "\n3: Test Modify Record"
                    + "\n4: Test Record Delete"
                    + "\n5: Exit");
            System.out.println("\n\nPlease Select an option (1-4):");
            Scanner scanner = new Scanner(System.in);
            String select = scanner.nextLine();
            switch (select.toString()) {
                case "1": {

                    boolean insert = mydb.createRecord(test);
                    if (insert) {
                        System.out.println("New Record Added");
                    } else {
                        System.out.println("Record already exists");
                    }
                    break;
                }
                case "2": {

                    Myuser test2 = mydb.getRecord("000005");
                    break;
                }
                case "3": {
                    boolean asd = mydb.updateRecord(test3);
                    if (asd) {
                        System.out.println(" Record Edited");
                    } else {
                        System.out.println("Record doesn't exists");
                    }
                    break;
                }
                case "4": {
                    boolean delete = mydb.deleteRecord("000001");
                    if (delete) {
                        System.out.println("Delet this");
                    } else {
                        System.out.println("you can't not do it");
                    }
                    break;
                }
                case "5": {
                    running = false;
                    break;
                }
                default: {
                    Myuser test2 = mydb.getRecord("000005");
                    break;
                }
            }
        }
    }

    public static ArrayList<Myuser> prepareMyuserData() {
        ArrayList<Myuser> myList = new ArrayList<Myuser>();
        Myuser myuser1 = new Myuser("000001", "Peter Smith", "123456",
                "psmith@swin.edu.au", "9876543210", "Swinburne EN510f",
                "What is my name?", "Peter");
        Myuser myuser2 = new Myuser("000002", "James T. Kirk", "234567",
                "jkirk@swin.edu.au", "8765432109", "Swinburne EN511a",
                "What is my name?", "James");
        Myuser myuser3 = new Myuser("000003", "Sheldon Cooper", "345678",
                "scooper@swin.edu.au", "7654321098", "Swinburne EN512a",
                "What is my last name?", "Cooper");
        Myuser myuser4 = new Myuser("000004", "Clark Kent", "456789",
                "ckent@swin.edu.au", "6543210987", "Swinburne EN513a",
                "What is my last name?", "Kent");
        Myuser myuser5 = new Myuser("000005", "Harry Potter", "567890",
                "hpotter@swin.edu.au", "6543210987", "Swinburne EN514a",
                "What is my last name?", "Potter");
        myList.add(myuser1);
        myList.add(myuser2);
        myList.add(myuser3);
        myList.add(myuser4);
        myList.add(myuser5);
        return myList;
    }

}
