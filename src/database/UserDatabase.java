package database;

import constants.AppConstants;
import user.User;

/**
 */
public class UserDatabase {

    private DatabaseConn userDBConn;

    public UserDatabase(){
        this.userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
    }

    public void addUser(User user){

    }

    public User getUser(String username){
        return null;
    }
}
