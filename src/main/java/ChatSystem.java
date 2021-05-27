import chat.MessagesBean;
import chat.MessagesDao;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig(maxFileSize = 16177215)
public class ChatSystem extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String myId,guestName,guestId,msgText,msgFileName,pDate;
        int fId;
        Part filePart;
        InputStream File = null;

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        pDate = dtf.format(now);

        myId = request.getParameter("sId");
        guestId = request.getParameter("gId");
        guestName = request.getParameter("gName");
        msgText = request.getParameter("msg");
        msgFileName = request.getParameter("fileName");
        filePart = request.getPart("fbtn");

        fId = Integer.parseInt(request.getParameter("fId"));

        if(filePart != null && filePart.getSize()!=0)
        {
            File = filePart.getInputStream();
        }

        if(fId == 0 && (!msgText.trim().equals("") || File != null))
        {
            MessagesBean newMsg = new MessagesBean();
            newMsg.setSenderId(myId);
            newMsg.setReceiverId(guestId);
            newMsg.setMsgText(msgText);
            newMsg.setMsgDateTime(pDate);
            newMsg.setMsgFileName(msgFileName);
            newMsg.setMsgFile(File);


            MessagesDao sendMsg = new MessagesDao();
            sendMsg.sendMessage(newMsg);
            msgText = null;
        }
        MessagesDao msgDao = new MessagesDao();
        msgDao.updateMsgSeenSt(guestId,myId);
        request.setAttribute("msg",null);
        request.setAttribute("fileName",null);
        request.setAttribute("guestId",guestId);
        request.setAttribute("guestName",guestName);
        request.setAttribute("fId",0);
        request.getRequestDispatcher("/chatSystem.jsp").forward(request, response);



    }
}
