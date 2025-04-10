package Model.DAO;

import java.sql.Connection;

/** 
* Super class of DAO classes that stores the database information 
*  
*/
public abstract class DB {   
    protected String URL = "jdbc:sqlite:";
    protected String db = "store.db";//name of the database   
    protected String dbuser = "";//db root user   
    protected String dbpass = ""; //db root password   
    protected Connection conn; //connection null-instance to be initialized in sub-classes
}