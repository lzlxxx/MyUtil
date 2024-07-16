package org.jdbc.Page;

import org.jdbc.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    ConnectionPool pool = new ConnectionPool("com.mysql.cj.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/xxx", "test", "123456");

    public int getRecordsCount() throws Exception {
        Connection conn = pool.getConnection();
        String sql = "select count(*) as totalPage from student ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        int totalPage = 0;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalPage = rs.getInt("totalPage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs.close();
            ps.close();
            pool.returnConnection(conn);
        }
        return totalPage;
    }


    public List<Student> getPageList(int index, int pageSize) throws Exception{
        Connection  conn = pool.getConnection();
         String sql = "select username,sname,ssex,classs,head_teacher,password"
         + " from student limit ?,?";
         PreparedStatement ps = null;
         ResultSet rs = null;
         List<Student> list = new ArrayList<Student>();
         try {
             ps = conn.prepareStatement(sql);
             ps.setInt(1, index);
             ps.setInt(2, pageSize);
             rs = ps.executeQuery();
             while (rs.next()) {
                list.add(createStudent(rs));
            }

        } catch (SQLException e) {
             e.printStackTrace();

        } finally {
           rs.close();
           ps.close();
           pool.returnConnection(conn);
        }
        return list;
    }
    private Student createStudent(ResultSet rs) throws Exception {
        Student student = new Student();
        student.setUsername(rs.getString("username"));
        student.setSname(rs.getString("sname"));
        student.setSsex(rs.getString("ssex"));
        student.setHead_teacher(rs.getString("head_teacher"));
        student.setClasss(rs.getString("classs"));
        student.setPassword(rs.getString("password"));
        return student;
    }
}



