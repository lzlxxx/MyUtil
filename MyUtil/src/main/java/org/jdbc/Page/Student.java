package org.jdbc.Page;

public class Student {
    private String username="";
    private String password;
    private String sname;
    private String ssex;
    private String classs;
    private String head_teacher;

   public Student(String username, String password, String sname, String ssex, String classs, String head_teacher){
       this.username=username;
       this.password=password;
       this.sname=sname;
       this.ssex=ssex;
       this.classs=classs;
       this.head_teacher=head_teacher;
   }
    public Student(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getHead_teacher() {
        return head_teacher;
    }

    public void setHead_teacher(String head_teacher) {
        this.head_teacher = head_teacher;
    }
}
