package database;

/**
 * Instantiates Statements for Database
 */
class DBStatementBuilder {

    static String selectStatement(String selectVar){
        return "SELECT "+selectVar;
    }
    static String updateStatement(String updateVar){
        return "UPDATE "+updateVar;
    }
    static String insertStatement(String tableName, String insertVar){
        return "INSERT INTO "+tableName+" ("+insertVar+")";
    }
    static String whereStatement(String whereVar){
        return " WHERE "+whereVar;
    }
    static String fromStatement(String fromVar){
        return " FROM "+fromVar;
    }
    static String valueStatement(String valueVar){
        return " VALUES "+"("+valueVar+")";
    }
    static String setStatement(String setVar){
        return " SET "+setVar;
    }
    static String createTableStatement(String tableName){
        return "CREATE TABLE IF NOT EXISTS '"+tableName+"'";
    }
    static String createTableTextColumn(String column){
        return "\""+column+"\" TEXT";
    }

}
