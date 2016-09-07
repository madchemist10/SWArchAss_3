package database;

import java.sql.*;
import java.util.ArrayList;


/**
 * Database class responsible for database connection establishment.
 * http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */
public class DatabaseConn {
    private Connection conn = null;
    private String database = null;

    public DatabaseConn(String db){
        this.database = db;
        this.conn = this.makeConnection(this.database);
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    //make connection
    public Connection makeConnection(String database){
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:"+database);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //attempt to close connection
    public void closeConnection(){
        try{
            this.conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //verify connection
    public Integer verifyConnection(){
        if (this.conn == null){
            this.conn = this.makeConnection(this.database);
            if (this.conn != null)
                return 1;
            else
                return 0;
        }
        return 1;
    }
    //make table
    public void makeTable(String table){
        Connection conn = this.conn;
        if (this.verifyConnection() == 1){
            try{
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(table);
                stmt.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            this.closeConnection();
        }
    }

    //insert into table
    public void insertIntoTable(String insert){
        Connection conn = this.conn;
        if (this.verifyConnection() == 1){
            try{
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(insert);
                stmt.close();
                conn.commit();
            }catch (Exception e){
                e.printStackTrace();
            }
            this.closeConnection();
        }
    }

    //select from table
    public ArrayList<String[]> selectFromTable(String selectStatement){
        ArrayList<String[]> returnData = new ArrayList<>();
        Connection conn = this.conn;
        if (this.verifyConnection() == 1) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectStatement);
                ResultSetMetaData rsmd = rs.getMetaData();
                Integer numCols = rsmd.getColumnCount();
                while( rs.next()){
                    String[] row_data = new String[numCols];
                    for(int i = 1;i<=numCols;i++){
                        row_data[i-1]=(rs.getString(i));
                    }
                    returnData.add(row_data);
                }
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.closeConnection();
            return returnData;
        }
        return returnData;
    }

    //update table entry
    public void updateTableEntry(String updateStatement){
        Connection conn = this.conn;
        if (this.verifyConnection() == 1) {
            try {
                conn.setAutoCommit(false);
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(updateStatement);
                stmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.closeConnection();
        }
    }
}
