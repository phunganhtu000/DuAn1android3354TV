package dell.example.com.duan1android3354tv.model;

public class Favorite {
    public static final String TABLE_NAME = "favorite";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_NAME = "name";
    String link;
    String name;

    public Favorite(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public Favorite() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static final String CREATE_TABLE_FAVORITE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_NAME + " TEXT PRIMARY KEY,"
                    + COLUMN_LINK + " TEXT"
                    + ")";
}