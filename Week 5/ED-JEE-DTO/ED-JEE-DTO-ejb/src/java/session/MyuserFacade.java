/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.MyAuthenticator;
import entity.Myuser;
import entity.MyuserDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class MyuserFacade implements MyuserFacadeRemote {

    @PersistenceContext(unitName = "ED-JEE-DTO-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    private void create(Myuser myuser) {
        em.persist(myuser);
    }

    private void edit(Myuser myuser) {
        em.merge(myuser);
    }

    private void remove(Myuser myuser) {
        em.remove(em.merge(myuser));
    }

    private Myuser find(Object id) {
        return em.find(Myuser.class, id);
    }

    @Override
    public boolean SendEmail(String to) 
    {
        String smtpServer = "smtp.gmail.com";
        String from = "One of my gmails was here";
        String subject = "Company Name Here, Your Details have been Changed.";
        String body = "Hi ,\nYour details have been changed. If you did not do this, contact us immediately!\nRegards";
        String emailUser = from;
        String password = "My password was here originally.";
        try {
            Properties props = System.getProperties();
            // --Attaching to default Session, or we could start a new one --
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            // --prepare a password authenticator --
            MyAuthenticator myPA = new MyAuthenticator(emailUser, password);
            // see MyAuthenticator class
            // get a session
            Session session = Session.getInstance(props, myPA);
            // --Create a newmessage --
            Message msg = new MimeMessage(session);
            // --Set the FROM and TO fields --
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            // --Set the subject and body text --
            msg.setSubject(subject);
            msg.setText(body);
            // --Set some other header information --
            msg.setHeader("X-Mailer", "Gmail");
            msg.setSentDate(new Date());
            // --Send the message --
            Transport.send(msg);
            //Transport.send(msg, emailUser, password);
            System.out.println("Message sent OK.");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
        return false;
    }
    
    @Override
    public boolean createRecord(MyuserDTO myuserDTO) {
        if (find(myuserDTO.getUserid()) != null) {
            // user whose userid can be found 
            return false;
        }
        // user whose userid could not be found
        try {
            Myuser myuser = this.myDTO2DAO(myuserDTO);
            this.create(myuser);// add to database
            return true;
        } catch (Exception ex) {
            return false; // something is wrong, should not be here though
        }
    }

    private Myuser myDTO2DAO(MyuserDTO myuserDTO) {
        Myuser myuser = new Myuser();
        myuser.setUserid(myuserDTO.getUserid());
        myuser.setName(myuserDTO.getName());
        myuser.setPassword(myuserDTO.getPassword());
        myuser.setEmail(myuserDTO.getEmail());
        myuser.setPhone(myuserDTO.getPhone());
        myuser.setAddress(myuserDTO.getAddress());
        myuser.setSecqn(myuserDTO.getSecQn());
        myuser.setSecans(myuserDTO.getSecAns());
        return myuser;
    }

    private MyuserDTO myDAO2DTO(Myuser myuser) {
        MyuserDTO newDTO = new MyuserDTO(
                myuser.getUserid(),
                myuser.getName(),
                myuser.getPassword(),
                myuser.getEmail(),
                myuser.getPhone(),
                myuser.getAddress(),
                myuser.getSecqn(),
                myuser.getSecans());
        return newDTO;
    }

    @Override
    public MyuserDTO getRecord(String userId) {

        //Create Dummy object to search
        Myuser temp = new Myuser();

        //assign it the string of the id to search for.
        temp.setUserid(userId);
        try {
            //See if the Dummy object's ID exists in Database
            if (find(temp.getUserid()) == null) {
                return null;
            } else {
                //Retrieve the ID to a new object
                //Myuser returnedRecord = find(temp.getUserid());

                //convert the MyUser object into a MyuserDTO object
                return myDAO2DTO(find(temp.getUserid()));
            }
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public boolean updateRecord(MyuserDTO myuserDTO) {
        //Does this record exist?
        if (find(myuserDTO.getUserid()) == null) {
            // user whose userid can be found 
            System.out.println("Does not exist");
            System.out.println(myuserDTO.getUserid());
            return false;
        }
        try {
            System.out.println("Does exist");
            //Get original record
            Myuser originalRecord = find(myuserDTO.getUserid());

            //Apply the new changes to that record
            originalRecord.setName(myuserDTO.getName());
            originalRecord.setPassword(myuserDTO.getPassword());
            originalRecord.setAddress(myuserDTO.getAddress());
            originalRecord.setPhone(myuserDTO.getPhone());
            originalRecord.setEmail(myuserDTO.getEmail());
            originalRecord.setSecans(myuserDTO.getSecAns());
            originalRecord.setSecqn(myuserDTO.getSecQn());

            //merge new record over original
            edit(originalRecord);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean deleteRecord(String userID) {
        //Create Dummy object to search
        Myuser temp = new Myuser();
        //assign it the string of the id to search for.
        temp.setUserid(userID);

        if (find(temp.getUserid()) == null) {
            return false;
        }
        try {
            //Retrieve the ID to a new object
            Myuser deletethis = find(temp.getUserid());
            remove(deletethis);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public ArrayList<MyuserDTO> getRecordsByAddress(String Address) {
        MyuserDTO a = new MyuserDTO("", "", "", "", "", Address, "", "");
        javax.persistence.Query query;
        query = em.createNamedQuery("Myuser.findByAddress").setParameter("address", Address);
        List<Myuser> daoList = (List<Myuser>) query.getResultList();

        System.out.println("Number of records:" + daoList.size());

        ArrayList<MyuserDTO> DTOList = new ArrayList<>();
        if (find(a.getAddress()) == null) {
            // user whose userid can be found 
            System.out.println("Does not exist");
            System.out.println(a.getUserid());
            return null;
        }
        try {
            for (Myuser record : daoList) {
                DTOList.add(myDAO2DTO(record));
            }

            return DTOList;
        } catch (Exception ex) {
            return null;
        }
    }

}
