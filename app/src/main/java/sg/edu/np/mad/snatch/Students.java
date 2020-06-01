package sg.edu.np.mad.snatch;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Students {

    private String StudentID;
    private String StudentPW;
    private String StudentName;


    private Students(){};

    public Students(String ID, String PW, String NAME){
        this.StudentID = ID;
        this.StudentPW = PW;
        this.StudentName = NAME;

    }


    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getStudentPW() {
        return StudentPW;
    }

    public void setStudentPW(String studentPW) {
        StudentPW = studentPW;
    }

    public String getStudentName(){return  StudentName;}

    public void setStudentName(String studentName){StudentName = studentName;}

    @Override
    public String toString(){
        return StudentID + " " + StudentPW;
    }
}


