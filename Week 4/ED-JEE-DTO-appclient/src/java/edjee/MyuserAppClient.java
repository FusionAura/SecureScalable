/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edjee;

import entity.MyuserDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import session.MyuserFacadeRemote;

/**
 *
 * @author PC
 */
public class MyuserAppClient {

    @EJB
    private static MyuserFacadeRemote myuserFacade;

    public static void main(String[] args) {
        Boolean running = true;

        MyuserAppClient client = new MyuserAppClient();
        // assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO = new MyuserDTO("000001", "Peter Smith", "123456", "psmith@swin.edu.au", "9876543210", "Swinburne EN510f", "What is my name?", "Peter");
        boolean result = client.createRecord(myuserDTO);
        client.showCreateResult(result, myuserDTO);
        // assuming inputs from keyboard or any GUI
        MyuserDTO myuserDTO2 = new MyuserDTO("000007", "David Lee", "654321", "dlee@swin.edu.au", "0123456789", "Swinburne EN510f", "What is my name?", "David");
        result = client.createRecord(myuserDTO2);
        client.showCreateResult(result, myuserDTO2);

        while (running) {
            String typeCode = "";

            System.out.println("\nTest new CRUD operations"
                    + "\n1: Get Record Test"
                    + "\n2: Update Record Test"
                    + "\n3: Delete Record Test"
                    + "\n4: Find Records by Address"
                    + "\n5: Exit");
            System.out.println("\n\nPlease Select an option (1-5):");
            Scanner scanner = new Scanner(System.in);
            String select = scanner.nextLine();
            switch (select.toString()) {
                case "1": {
                    String testString = "000007";
                    if (client.getRecord(testString) != null)
                    {
                    System.out.println(client.getRecord("000007").getUserid());
                    System.out.println(client.getRecord("000007").getName());
                    System.out.println(client.getRecord("000007").getEmail());
                    System.out.println(client.getRecord("000007").getPassword());
                    System.out.println(client.getRecord("000007").getPhone());
                    System.out.println(client.getRecord("000007").getSecAns());
                    System.out.println(client.getRecord("000007").getSecQn());
                    }
                    else
                    {
                        System.out.println("Record doesn't exist."); 
                    }
                    break;
                }
                case "2": {
                    myuserDTO2 = new MyuserDTO("000007", "David Lee Jr", "123456", "dleeJr@swin.edu.au", "0123456789", "Swinburne EN510f", "What is my name?", "David");
                    boolean Testresult = client.updateRecord(myuserDTO2);
                    if (Testresult == true){
                    System.out.println(client.getRecord("000007").getName());
                    System.out.println(client.getRecord("000007").getPassword());
                    System.out.println(client.getRecord("000007").getEmail());
                    }
                    else
                    {
                       System.out.println("Record doesn't exist."); 
                    }
                    
                    break;
                    
                }
                case "3": {

                    boolean Testresult = client.deleteRecord("000007");
                    if (Testresult) {
                        System.out.println("Record delete");
                    }
                    else
                    {
                        System.out.println("Record doesn't exist");
                    }

                }

                break;

                case "4": {
                    ArrayList<MyuserDTO> DTOList = client.getRecordsByAddress("Swinburne EN510f");
                    System.out.println("Found "+DTOList.size()+ " entries with the same address:");
                    for (MyuserDTO record : DTOList) {
                        System.out.println(record.getUserid());
                        System.out.println(record.getName());
                        System.out.println(record.getAddress()+"\n");
                        
                    }
                    break;
                }
                case "5": {
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
            System.out.println("Record with primary key " + myuserDTO.getUserid() + " has been created in the database table.");
        } else {
            System.out.println("Record with primarykey " + myuserDTO.getUserid() + " could not be created in thedatabase table!");
        }
    }

    public boolean createRecord(MyuserDTO myuserDTO) {
        return myuserFacade.createRecord(myuserDTO);
    }

    public MyuserDTO getRecord(String userID) {
        return myuserFacade.getRecord(userID);
    }

    public boolean updateRecord(MyuserDTO myuserDTO) {
        return myuserFacade.updateRecord(myuserDTO);
    }

    public boolean deleteRecord(String userID) {
        return myuserFacade.deleteRecord(userID);
    }

    public ArrayList<MyuserDTO> getRecordsByAddress(String Address) {
        return myuserFacade.getRecordsByAddress(Address);
    }
}
