import SocialIntranet.PostDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MultipartConfig(maxFileSize = 16177215)
public class EditPost extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        int res,delImgCount,tot,maxId,count,newImg=0;
        String pId,text,pDate,imgId;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        pId = request.getParameter("pId");
        pDate = dtf.format(now);
        text = request.getParameter("description");
        tot = Integer.parseInt(request.getParameter("tot"));
        maxId = Integer.parseInt(request.getParameter("maxId"));
        count = Integer.parseInt(request.getParameter("count"));

        imgId = request.getParameter("imgId");
        String[] postArr = imgId.split(",");
        if(imgId.equals(""))
        {
            delImgCount = 0;
        }
        else
        {
            delImgCount = postArr.length;
        }

        Part filePart[] = new Part[tot];

        for(int i=0;i<tot;i++)
        {
            filePart[i] = request.getPart("imgFile"+(i+1));
        }
        InputStream imgFile[] = new InputStream[tot];
        for(int j=0;j<tot;j++)
        {
            if(filePart[j]!=null && filePart[j].getSize()!=0)
            {
                imgFile[newImg] = filePart[j].getInputStream();
                newImg++;
            }
        }
        PostDao empPost = new PostDao();
        String result = empPost.updatePost(pId,pDate,text,imgFile,newImg,postArr,delImgCount,maxId,count);
        request.setAttribute("result", result);


        request.getRequestDispatcher("/editPost.jsp").forward(request, response);
    }
}
