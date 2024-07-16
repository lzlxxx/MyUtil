package org.jdbc.Page;

import java.util.List;

public class StudentService {
  private  StudentDao studentDao=new StudentDao();
    public Page<Student> getPage(String pageNumber,int pageSize) throws Exception {
        int pageNo=1;      //设置默认页码，当pageNumber类型转换出错时，会起作用，否则值被覆盖
        Page<Student> page=null;

        try {
            //servlet层获取的参数类型为string，需要转换为整型
            pageNo=Integer.parseInt(pageNumber);
        } catch (Exception e) {
            System.out.println("字符串转换出错");
        }
        //1.获取总记录数
        int totalRecord=studentDao.getRecordsCount();
        //2.封装page对象
        page=new Page<Student>(pageNo, totalRecord, pageSize);
        //3.查询当前页对应的数据列表并封装到page对象中
        List<Student> list=studentDao.getPageList(page.getIndex(),pageSize);
        page.setList(list);
        return page;
    }
}
