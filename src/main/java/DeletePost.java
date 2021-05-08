import SocialIntranet.PostDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePost extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pId;
        PostDao post = new PostDao();
        pId = request.getParameter("pId");
        String[] postArr = pId.split(",");
        String result = null;

        for(int i=0;i<postArr.length;i++)
        {
            result=post.delPost(postArr[i]);
        }
        request.setAttribute("result", result);

        request.getRequestDispatcher("/deleteMyPost.jsp").forward(request, response);
    }
}
