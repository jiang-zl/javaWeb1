package indexServlet;

import models.DoctorModels;
import models.LoginModels;
import models.PatientModels;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * @author jiangzl
 */
@WebServlet(name = "ServletRegister")
public class ServletRegister extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LoginModels models = null;
        String statusOfRegister = request.getParameter("rClick");
        String otherEle = null, tableName = null, otherReq = null;
        String userId = request.getParameter("userId"), userPassword = request.getParameter("userPassword");
        if ("0".equals(statusOfRegister)){
            otherReq = request.getParameter("phonenumber");
            models = new PatientModels(request.getParameter("userId"),
                    request.getParameter("userPassword"), otherReq);
            otherEle = "phonenumber";
            tableName = "patientlogin";
        }
        else if ("2".equals(statusOfRegister)){
            otherReq = request.getParameter("email");
            models = new DoctorModels(request.getParameter("userId"),
                    request.getParameter("userPassword"), otherReq);
            otherEle = "email";
            tableName = "doctorlogin";
        }
        try {
            if (null != models){
                String finalOtherReq = otherReq;
                boolean ret = models.insertIntoTable(new String[]{"userId", "userPassword", otherEle}, tableName, new ArrayList<String>(){{
                        add(userId);
                        add(userPassword);
                        add(finalOtherReq);
                    }});
                Writer out = response.getWriter();
                if(ret){
                    out.write("suc1");
                }
                else{
                    out.write("error4");
                }
                if(out != null){
                    out.flush();
                    out.close();
                }
            }
        } catch (SQLException thenables) {
            thenables.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
