import salary.SalaryBean;
import salary.SalaryDao;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class searchMySalaries extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String empId= request.getParameter("empId");
        String totSalary= request.getParameter("totSalary");
        String totSalaryLess= request.getParameter("totSalaryLess");
        String fromYear= request.getParameter("fromYear");
        String fromMonth= request.getParameter("fromMonth");
        String toYear= request.getParameter("toYear");
        String toMonth= request.getParameter("toMonth");
        String from,to;
        SalaryBean result=new SalaryBean();

        if(fromMonth.equals("0")){
            from=fromYear+"-01-00";
            result.setFromDate(" ");
        }else{
             from=fromYear+"-"+fromMonth+"-00";
             result.setFromDate(" From : "+from);
        }
        if(toMonth.equals("0")){
            to=toYear+"-12-28";
            result.setToDate(" ");
        }else{
             to=toYear+"-"+toMonth+"-28";
             result.setToDate(" To : "+to);
        }

        if (totSalary == null){
            totSalary = "0";
            result.setResultGreater("Salaries ");
        }else{
            result.setResultGreater(" Salaries Greater than : "+totSalary);
        }


        if (totSalaryLess.equals("999999999")){
            totSalaryLess = "999999999";
            result.setResultLess(" And Less than the Highest Salary");
        }else{
            result.setResultLess(" And Less than : "+totSalaryLess);
        }
        System.out.println(from+  " = "  + to+  " = " +totSalary +  " = "  + totSalaryLess) ;
        System.out.println(result.getResultGreater()  + " = "  + result.getResultLess() +   " = " +result.getFromDate() +  " = "  +result.getToDate()) ;

        if (fromMonth.equals("0") && toMonth.equals("0") && totSalary.equals("0")  && totSalaryLess.equals("999999999")){
            result.setResultGreater("All Salaries");
            result.setResultLess("  ");
        }

        if (!fromMonth.equals("0") && !toMonth.equals("0") && totSalary.equals("0")  && totSalaryLess.equals("999999999")){
            result.setResultGreater(" ");
            result.setResultLess("  ");
        }
        if (!fromMonth.equals("0") && toMonth.equals("0") && totSalary.equals("0")  && totSalaryLess.equals("999999999")){
            result.setResultGreater(" ");
            result.setResultLess("  ");
            result.setToDate(" ");
        }
        if (fromMonth.equals("0") && !toMonth.equals("0") && totSalary.equals("0")  && totSalaryLess.equals("999999999")){
            result.setResultGreater(" ");
            result.setResultLess("  ");
            result.setFromDate(" ");
        }

        SalaryDao empDao = new SalaryDao();

        List<SalaryBean> salaryList = empDao.mySalaries(empId,from,to,totSalary,totSalaryLess);
        request.setAttribute("salaries",salaryList);
        request.setAttribute("results",result);
        //System.out.println(result.getResult());

        request.getRequestDispatcher("/mySalaryOverview.jsp").forward(request, response);
    }
}
