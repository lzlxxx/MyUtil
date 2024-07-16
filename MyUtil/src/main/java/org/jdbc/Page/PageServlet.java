package org.jdbc.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class PageServlet  extends HttpServlet {
        private StudentService s=new StudentService();
    public void getPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String path=WebUtil.getPath(request);
        String pageNumber=request.getParameter("pageNumber");   //获取页码
        Page<Student> page=new Page<Student>();
        page=s.getPage(pageNumber,5);  //获取页面信息
        page.setPath(path);   //设置访问路径
        request.setAttribute("page", page);  //将页面信息放入域中
        request.setAttribute("list", page.getList());
        request.getRequestDispatcher("/page/managerstu.jsp").forward(request, response);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            getPage(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
