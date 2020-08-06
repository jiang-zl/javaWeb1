package indexServlet;

import models.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author 23293
 */
@WebServlet(name = "ServletLogin")
public class ServletLogin extends HttpServlet {

    /**
     * 三方用户登陆页面后端代码
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LoginModels models = null;
        String tableName = null;

        switch (Integer.parseInt(request.getParameter("statu")))
        {
            case 0:
                models = new PatientModels(request.getParameter("userId"),
                        request.getParameter("userPassword"));
                tableName = "patientlogin";
                break;
            case 2:
                models = new DoctorModels(request.getParameter("userId"),
                        request.getParameter("userPassword"));
                tableName = "doctorlogin";
                break;
            default:
                models = new ThirdModels(request.getParameter("userId"),
                        request.getParameter("userPassword"));
                tableName = "third";
                break;
        }
        try {
            if(null == models || tableName == null){
                throw new Exception("models is null");
            }
            String userId = models.getName();
            HashMap<String, String> ret = models.selectFromTable(new String[]{"userPassword"}, tableName,
                    new HashMap<String, String>(1){
                {
                    put("userId", userId);
                }
            });
            if(models.getPass().equals(ret.get("userPassword"))){
                Writer out = response.getWriter();
                out.write("suc0");
                out.flush();
                out.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

// CCF_NCSC_war_exploded