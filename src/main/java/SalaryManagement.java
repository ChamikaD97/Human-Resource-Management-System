
import salary.*;


import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class
SalaryManagement extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empId,comId,date,description;
        String extraDays;
        extraDays= request.getParameter("extraHolidays");
        int extraHolidays= Integer.parseInt(extraDays);

        if (extraDays==null){
            extraHolidays=0;
        }
            SalaryBean salary = new SalaryBean();
            salary.setExtraHolidays(extraHolidays);


            SalaryDao sd=new SalaryDao();
            String result = null;
            try {
                result = sd.calculatePayment(salary);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            request.setAttribute("result", result);
            if(result.equals("errors")){
                request.getRequestDispatcher("/manageSalaryErrors.jsp").forward(request, response);

            }else  if(result.equals("noerrors")){
                request.getRequestDispatcher("/staffSalaryOverview.jsp").forward(request, response);

            }
        }

}