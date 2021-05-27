package chat;

import chat.MessagesBean;
import DBconnection.DBconn;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesDao
{
    public List<MessagesBean> getMessages(Object myId, String guestId)
    {
        List<MessagesBean> msgList = new ArrayList<MessagesBean>();

        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String msgId,senderId,receiverId,msgText,msgFileName,msgDateTime;
        Blob msgFile;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM chat WHERE (((senderId = '"+myId+"' AND receiverId = '"+guestId+"') OR (senderId = '"+guestId+"' AND receiverId = '"+myId+"')) AND (NOT(delEmpId = '"+myId+"') OR delEmpId IS NULL ))");
            while(rs.next())
            {
                MessagesBean msg = new MessagesBean();
                msgId = rs.getString("msgId");
                senderId = rs.getString("senderId");
                receiverId = rs.getString("receiverId");
                msgText = rs.getString("messageText");
                msgDateTime = rs.getString("dateTime");
                msgFileName = rs.getString("fileName");
                //msgFile = rs.getBlob("msgFile");


                msg.setMsgId(msgId);
                msg.setSenderId(senderId);
                msg.setReceiverId(receiverId);
                msg.setMsgText(msgText);
                msg.setMsgDateTime(msgDateTime);
                msg.setMsgFileName(msgFileName);
                //msg.setMsgFile(msgFile);

                msgList.add(msg);
            }
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return msgList;
    }

    public int getUnseenMsgCount(String sId, Object rId)
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        int msgCount = 0;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT COUNT(seenSt) FROM chat WHERE receiverId = '"+rId+"' && senderId = '"+sId+"' && seenSt = 0");
            if(rs.next())
            {
                msgCount = rs.getInt("COUNT(seenSt)");
            }
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return msgCount;
    }

    public void updateMsgSeenSt(String sId, String rId)
    {
        Connection con = null;
        try
        {
            con = DBconn.getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE chat SET seenSt = 1 WHERE senderId = '"+sId+"' && receiverId = '"+rId+"' && seenSt = 0");

            st.executeUpdate();
            st.close();
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMessage(MessagesBean newMsg)
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        int senderId,receiverId;
        String msgText,msgFileName,res,msgDateTime;
        InputStream msgFile;

        senderId = Integer.parseInt(newMsg.getSenderId());
        receiverId = Integer.parseInt(newMsg.getReceiverId());
        msgText = newMsg.getMsgText();
        msgDateTime = newMsg.getMsgDateTime();
        msgFileName = newMsg.getMsgFileName();
        msgFile = newMsg.getMsgFile();

        try
        {
            con = DBconn.getConnection();
            PreparedStatement st1 = con.prepareStatement("INSERT INTO chat (senderId,receiverId,messageText,dateTime,seenSt,fileName,msgFile) VALUES(?,?,?,?,?,?,?)");
            st1.setInt(1,senderId);
            st1.setInt(2,receiverId);
            st1.setString(3,msgText);
            st1.setString(4,msgDateTime);
            st1.setInt(5,0);
            st1.setString(6,msgFileName);
            st1.setBlob(7,msgFile);
            st1.executeUpdate();
            st1.close();
            con.close();
            res = "success";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            res = "failed";
        }
        return;
    }
    public String[] getMsgStatus(String msgId)
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String senderId,receiverId,delEmpId,msgDateTime;
        String [] status = new String[4];
        try
        {
            status[0] = "delete";
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT senderId,receiverId,delEmpId,dateTime FROM chat WHERE msgId = '"+msgId+"'");
            while(rs.next())
            {
                delEmpId = rs.getString("delEmpId");
                senderId = rs.getString("senderId");
                receiverId = rs.getString("receiverId");
                msgDateTime = rs.getString("dateTime");
                if(delEmpId == null)
                {
                    status[0] = "notDelete";
                }
                status[1] = senderId;
                status[2] = receiverId;
                status[3] = msgDateTime;
            }
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            status[0] = "Error";
            status[1] = "Error";
            status[2] = "Error";
            status[3] = "Error";
        }
        return status;
    }

    public void delMessageEveryone(String mId)
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        int msgId = Integer.parseInt(mId);
        try
        {
            con = DBconn.getConnection();
            PreparedStatement st = con.prepareStatement("DELETE From chat WHERE msgId=?");
            st.setInt(1,msgId);
            st.executeUpdate();
            st.close();
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delMessageFromUser(MessagesBean delMsg)
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        int msgId,delEmpId;
        msgId = Integer.parseInt(delMsg.getMsgId());
        delEmpId = Integer.parseInt(delMsg.getDelEmpId());
        try
        {
            con = DBconn.getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE chat SET delEmpId=? WHERE msgId=?");
            st.setInt(1,delEmpId);
            st.setInt(2,msgId);
            st.executeUpdate();
            st.close();
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

}
