import complains.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveComplain extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String comId;

        comId = request.getParameter("comId");
        ComplainBean com = new ComplainBean();
        com.setcomId(comId);

        ComplainDao empDao = new ComplainDao();
        String result= empDao.removeComplain(com);

        request.setAttribute("result",result);
        request.getRequestDispatcher("/viewComplains.jsp").forward(request, response);
    }
}
