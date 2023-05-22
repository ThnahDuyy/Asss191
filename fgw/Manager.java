package ASM.fgw;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Manager implements Serializable {
    private String ID;
    private String Name;

    private boolean Gender;

    private String Position;
    private String Email;

    private Date Birthday;

    // getter and setter
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {Name = name;}

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {Position = position;}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {Birthday = Birthday;}

    public Manager(String ID, String Name, boolean Gender,
                   String Position, String Email, String Birthday){
        this.ID = ID;
        this.Name = Name;
        this.Gender = Gender;
        this.Position = Position;
        this.Email = Email;
        convert(Birthday);
    }

    public void convert(String Bday){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            this.Birthday = sdf.parse(Bday);
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

}
