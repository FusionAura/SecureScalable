/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jpa;

import java.util.Scanner;

/**
 *
 * @author PC
 */
public class MyuserApp {

    /**
     * @param args the command line arguments
     */
    private MyuserDB mydb;

    public MyuserApp() {
        mydb = new MyuserDB();
    }

    public static void main(String[] args) {
        Boolean running = true;

        MyuserApp client = new MyuserApp();
// assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO = new MyuserDTO("000001", "Peter Smith", "123456",
                "psmith@swin.edu.au", "9876543210", "Swinburne EN510f",
                "What is my name?", "Peter");
        boolean result = client.createRecord(myuserDTO);
//        client.showCreateResult(result, myuserDTO);
// assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO2 = new MyuserDTO("000006", "David Lee", "123456",
                "dlee@swin.edu.au", "987654321", "Swinburne EN510g",
                "What is my name?", "David");
        result = client.createRecord(myuserDTO2);
//        client.showCreateResult(result, myuserDTO2);  

        while (running) {
            String typeCode = "";

            System.out.println("\nTest new CRUD operations"
                    + "\n1: Get Record Test"
                    + "\n2: Update Record Test"
                    + "\n3: Delete Record Test"
                    + "\n4: Exit");
            System.out.println("\n\nPlease Select an option (1-4):");
            Scanner scanner = new Scanner(System.in);
            String select = scanner.nextLine();
            switch (select.toString()) {
                case "1": {
                    MyuserDTO testDTO = client.mydb.getRecord("000001");
                    System.out.println(testDTO.getUserid()
                            + "\n" + testDTO.getName()
                            + "\n" + testDTO.getAddress()
                            + "\n" + testDTO.getEmail()
                            + "\n" + testDTO.getPassword()
                            + "\n" + testDTO.getPhone()
                            + "\n" + testDTO.getSecQn()
                            + "\n" + testDTO.getSecAns());
                    break;
                }
                case "2": {
                    System.out.println("\nHere is the original Record" + "\n" + myuserDTO2.getUserid()
                            + "\n" + myuserDTO2.getName()
                            + "\n" + myuserDTO2.getAddress()
                            + "\n" + myuserDTO2.getEmail()
                            + "\n" + myuserDTO2.getPassword()
                            + "\n" + myuserDTO2.getPhone()
                            + "\n" + myuserDTO2.getSecQn()
                            + "\n" + myuserDTO2.getSecAns());

                    MyuserDTO myuserDTO3 = new MyuserDTO("000006", "David Lee", "123333",
                            "dlee@swin.edu.aa", "000000000", "Swinburne EN512g",
                            "What is my name?", "Mike");
                    MyuserDTO testDTO = client.mydb.getRecord("000006");
                    System.out.println("\nNow it is this." + "\n" + myuserDTO2.getUserid()
                            + "\n" + testDTO.getName()
                            + "\n" + testDTO.getAddress()
                            + "\n" + testDTO.getEmail()
                            + "\n" + testDTO.getPassword()
                            + "\n" + testDTO.getPhone()
                            + "\n" + testDTO.getSecQn()
                            + "\n" + testDTO.getSecAns());

                    boolean a = client.mydb.updateRecord(myuserDTO3);
                    System.out.println(a);
                    break;
                }
                case "3": {
                    boolean b = client.mydb.deleteRecord("000001");
                    if (b == true) {
                        System.out.println("\nNow it is this." + "\n" + myuserDTO.getUserid()
                                + "\n" + myuserDTO.getName()
                                + "\n" + myuserDTO.getAddress()
                                + "\n" + myuserDTO.getEmail()
                                + "\n" + myuserDTO.getPassword()
                                + "\n" + myuserDTO.getPhone()
                                + "\n" + myuserDTO.getSecQn()
                                + "\n" + myuserDTO.getSecAns());
                        MyuserDTO testDTO = client.mydb.getRecord("000001");
                        System.out.println("Record has been deleted");
                        System.out.println(testDTO + ". Record has been deleted.");
                    } else {
                        System.out.println("Record doesn't exist or has already been deleted");
                    }

                    break;
                }
                case "4": {
                    running = false;
                    break;
                }
                default: {

                    break;
                }
            }
        }
    }

    public void showCreateResult(boolean result, MyuserDTO myuserDTO) {
        if (result) {
            System.out.println("Record with primary key " + myuserDTO.getUserid()
                    + " has been created in the database table.");
        } else {
            System.out.println("Record with primary key " + myuserDTO.getUserid()
                    + " could not be created in the database table!");
        }
    }

    public boolean createRecord(MyuserDTO myuserDTO) {
        return mydb.createRecord(myuserDTO);
    }

}
