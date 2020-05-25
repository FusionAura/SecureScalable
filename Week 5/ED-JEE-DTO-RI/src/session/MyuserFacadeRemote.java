/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.MyuserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author PC
 */
@Remote
public interface MyuserFacadeRemote {

    boolean createRecord(MyuserDTO myuserDTO);
    MyuserDTO getRecord(String userId);
    boolean updateRecord(MyuserDTO myuserDTO);
    boolean deleteRecord(String userID);
    boolean SendEmail(String to) ;
    ArrayList<MyuserDTO> getRecordsByAddress (String Address);
}
