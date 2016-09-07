package database;

/**
 * Instantiates Statements for Database
 */
public class DBStatementBuilder {

    public static String selectStatement(String selectVar){
        return "SELECT "+selectVar;
    }
    public static String updateStatement(String updateVar){
        return "UPDATE "+updateVar;
    }
    public static String insertStatement(String tableName, String insertVar){
        return "INSERT INTO "+tableName+" ("+insertVar+")";
    }
    public static String whereStatement(String whereVar){
        return " WHERE "+whereVar;
    }
    public static String fromStatement(String fromVar){
        return " FROM "+fromVar;
    }
    public static String valueStatement(String valueVar){
        return " VALUES "+"("+valueVar+")";
    }
    public static String andStatement(String andVar){
        return " AND "+andVar;
    }
    public static String setStatement(String setVar){
        return " SET "+setVar;
    }
    public static String groupByStatement(String groupByVar){
        return " GROUP BY "+groupByVar;
    }
    public static String createTableStatement(String tableName){
        return "CREATE TABLE IF NOT EXISTS '"+tableName+"'";
    }
    public static String createTableTextColumn(String column){
        return "\""+column+"\" TEXT";
    }

}
