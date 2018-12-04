package dell.example.com.duan1android3354tv.model;

public class User {
    public static final String TABLE_NAME="users";
    public static final String COLUMN_USER_NAME="user_name";
    public static final String COLUMN_PASSWORD="password";
    String user_name;
    String password;

    public User(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    public User() {

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_USER_NAME + " TEXT PRIMARY KEY,"
                    + COLUMN_PASSWORD + " TEXT"
                    + ")";
}
