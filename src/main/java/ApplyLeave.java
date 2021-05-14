import leave.LeaveBean;
import leave.LeaveDao;


import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplyLeave extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String empId,leaveId,authorizedPersonId,toDate,fromDate,appDate,reason,type;

        empId=request.getParameter("empId");
        leaveId=request.getParameter("leaveId");
        authorizedPersonId=request.getParameter("authorizedPersonId");
        toDate=request.getParameter("toDate");
        fromDate=request.getParameter("fromDate");
        appDate=request.getParameter("appliedDate");
        reason=request.getParameter("reason");
        type=request.getParameter("leaveType");
        System.out.println(type);



        if (reason.isEmpty() || authorizedPersonId.isEmpty() || toDate.isEmpty() || fromDate.isEmpty() || appDate.isEmpty()){
            String  result="Unsuccessful";
            request.setAttribute("result",result);
            request.getRequestDispatcher("/applyForLeave.jsp").forward(request, response);
        }else {

            LeaveBean newleave = new LeaveBean();

            newleave.setEmpId(empId);
            newleave.setLeaveId(leaveId);
            newleave.settoDate(toDate);
            newleave.setfromDate(fromDate);
            newleave.setappDate(appDate);
            newleave.setAuthorizedPersonId(authorizedPersonId);
            newleave.setReason(reason);
            newleave.setType(type);

            LeaveDao appLeave = new LeaveDao();
            String res = null;
            try {
                res = appLeave.addplyLeave(newleave);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            request.setAttribute("result", res);

            request.getRequestDispatcher("/applyForLeave.jsp").forward(request, response);
        }
    }
}
