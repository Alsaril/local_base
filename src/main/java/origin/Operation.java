package origin;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.util.List;

public class Operation {

    @SerializedName("id")
    @Expose
    @DatabaseField(id = true)
    private int id;
    @SerializedName("user")
    @Expose
    @DatabaseField
    private String user;
    @SerializedName("date")
    @Expose
    @DatabaseField
    private String date;
    @SerializedName("type")
    @Expose
    @DatabaseField
    private int type;
    @SerializedName("positions")
    @Expose
    private List<Position> positions = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public Operation setPositions(List<Position> positions) {
        this.positions = positions;
        return this;
    }

}
