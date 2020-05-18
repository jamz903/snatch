package sg.edu.np.mad.snatch;

public class Students {

    private String StudentID;
    private String StudentPW;

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

    @Override
    public String toString(){
        return StudentID + " " + StudentPW;
    }
}
