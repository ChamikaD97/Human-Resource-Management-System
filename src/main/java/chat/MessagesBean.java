package chat;

import java.io.InputStream;
import java.sql.*;
public class MessagesBean
{
    private String msgId,senderId,receiverId,delEmpId,msgText,msgFileName,msgDateTime;
    private InputStream msgFile;
    //Blob msgFile;
    public String getMsgId()
    {
        return msgId;
    }
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSenderId()
    {
        return senderId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId()
    {
        return receiverId;
    }
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getDelEmpId()
    {
        return delEmpId;
    }
    public void setDelEmpId(String delEmpId) {
        this.delEmpId = delEmpId;
    }

    public String getMsgText()
    {
        return msgText;
    }
    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgFileName()
    {
        return msgFileName;
    }
    public void setMsgFileName(String msgFileName)
    {
        this.msgFileName = msgFileName;
    }

    public InputStream getMsgFile()
    {
        return msgFile;
    }
    public void setMsgFile(InputStream msgFile)
    {
        this.msgFile = msgFile;
    }

    public String getMsgDateTime()
    {
        return msgDateTime;
    }
    public void setMsgDateTime(String msgDateTime) {
        this.msgDateTime = msgDateTime;
    }
}
