/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class MyDB {

    static String findRecordByPKSQL = "SELECT * FROM MYUSER WHERE USERID = ?";
    static String DeleteThis = "DELETE FROM MYUSER WHERE USERID = ?";
    static String EditThis = "UPDATE MYUSER "
            + "SET Name = ?, Password = ?, Email = ?, Phone = ?, Address = ?, SecQn = ?, SecAns = ? WHERE Userid = ?";

    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");
        String url = "jdbc:derby://localhost/sun-appserv-samples;create=true";

        String username = "APP";
        String password = "APP";
        return DriverManager.getConnection(url, username, password);
    }

    public void createMyuserTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("CREATE TABLE MYUSER ( "
                    + " UserId CHAR(6) CONSTRAINT PK_CUSTOMER PRIMARY KEY, "
                    + " Name CHAR(30), Password CHAR(6), Email CHAR(30), "
                    + " Phone CHAR(10), Address CHAR(60), "
                    + " SecQn CHAR(60), SecAns CHAR(60))");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    public void dropMyuserTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("DROP TABLE MYUSER");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    public void addRecords(ArrayList<Myuser> myUsers) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement
                    = "INSERT INTO MYUSER VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            for (Myuser myuser : myUsers) {
                pStmnt.setString(1, myuser.getUserid());
                pStmnt.setString(2, myuser.getName());
                pStmnt.setString(3, myuser.getPassword());
                pStmnt.setString(4, myuser.getEmail());
                pStmnt.setString(5, myuser.getPhone());
                pStmnt.setString(6, myuser.getAddress());
                pStmnt.setString(7, myuser.getSecQn());
                pStmnt.setString(8, myuser.getSecAns());
                int rowCount = pStmnt.executeUpdate();
                if (rowCount == 0) {
                    throw new SQLException("Cannot insert records!");
                }
            }
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
    }

    public boolean createRecord(Myuser _myuser) {
        System.out.println("starting");
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();

            pStmnt = cnnct.prepareStatement("INSERT INTO MYUSER VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            pStmnt.setString(1, _myuser.getUserid());
            pStmnt.setString(2, _myuser.getName());
            pStmnt.setString(3, _myuser.getPassword());
            pStmnt.setString(4, _myuser.getEmail());
            pStmnt.setString(5, _myuser.getPhone());
            pStmnt.setString(6, _myuser.getAddress());
            pStmnt.setString(7, _myuser.getSecQn());
            pStmnt.setString(8, _myuser.getSecAns());

            int rowCount = pStmnt.executeUpdate();

            System.out.println("rowCount = " + rowCount);

            if (rowCount == 0) {
                System.out.println("Cannot insert record with id " + _myuser.getUserid());
                return false;
            }
            return true;

        } catch (SQLException ex) {
            return false;
//            while (ex != null) {
//                ex.printStackTrace();
//                ex = ex.getNextException();
//            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        return false;
    }

    public Myuser getRecord(String userId) {
        Myuser MYUSER_Find = null;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ResultSet rs = null;

        try {
            cnnct = getConnection();
            pStmnt = cnnct.prepareStatement(findRecordByPKSQL); //cnnct.prepareStatement("SELECT * FROM MYUSER WHERE USERID = 'userId'");

            // set individual parameter
            pStmnt.setString(1, userId);

            rs = pStmnt.executeQuery();
            int i = 0;
            while (rs.next()) {
                if (i == 0) {
                    System.out.println("Num\tUserID\tName\t\tpassword\temail\tphone\taddress\tsecQn\tsecAns");
                }
                i = i + 1;
                String UserID = rs.getString("userid");//
                String name = rs.getString("name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String secQn = rs.getString("secQn");
                String secAns = rs.getString("secAns");
                System.out.println(i + "\t" + UserID + "\t\t" + name
                        + "\t" + password + "\t" + email + "\t" + phone + "\t" + address + "\t" + secQn + "\t" + secAns);

                MYUSER_Find = new Myuser(UserID, name, password, email, phone, address, secQn, secAns);
                MYUSER_Find.setName("ABC");
                System.out.println(userId);
                System.out.println(UserID);
                if (userId.equals(UserID)) {
                    System.out.println("great success");
                    return MYUSER_Find;
                }

            }

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }

        return null;
    }

    public boolean updateRecord(Myuser _myuser) {
        System.out.println("starting");
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();

            pStmnt = cnnct.prepareStatement(EditThis);

            pStmnt.setString(1, _myuser.getName());
            pStmnt.setString(2, _myuser.getPassword());
            pStmnt.setString(3, _myuser.getEmail());
            pStmnt.setString(4, _myuser.getPhone());
            pStmnt.setString(5, _myuser.getAddress());
            pStmnt.setString(6, _myuser.getSecQn());
            pStmnt.setString(7, _myuser.getSecAns());
            pStmnt.setString(8, _myuser.getUserid());

            int rowCount = pStmnt.executeUpdate();

            if (rowCount == 0) {
                return false;
            }
            return true;

        } catch (SQLException ex) {
            //return false;
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        return false;
    }

    public boolean deleteRecord(String userID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ResultSet rs = null;
        try {
            cnnct = getConnection();
            // set individual parameter
            pStmnt = cnnct.prepareStatement(DeleteThis);
            pStmnt.setString(1, userID);

            int rowCount = pStmnt.executeUpdate();
            if (rowCount == 0) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        System.out.println("no delete 2");
        return false;
    }
}
