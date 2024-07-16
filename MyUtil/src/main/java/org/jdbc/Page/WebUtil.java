package org.jdbc.Page;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
    public static String getPath(HttpServletRequest request){
        String uri=request.getRequestURI();    //返回“项目名/请求Servlet名”的字符串
        String quString=request.getQueryString(); //获取请求的参数部分
        String path=uri+"?"+quString;   //拼串，请求地址：项目名/servlet名？参数
        if(path.contains("&pageNumber")){
            //截串，将没有用的参数截去，只留下用于反射的method参数
            path=path.substring(0, path.indexOf("&pageNumber"));
        }
        return path;
    }
}
