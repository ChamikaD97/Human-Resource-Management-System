package SocialIntranet;

import DBconnection.DBconn;
import java.io.InputStream;
import java.sql.*;

public class PostDao
{
    public String addPost(String eId, String pDate, String text, InputStream image[],int tot)
    {
        int empId,pId=0;
        String result;
        Statement statement = null;
        ResultSet rs = null;
        empId = Integer.parseInt(eId);
        Connection con = null;
        try
        {
            con = DBconn.getConnection();

            PreparedStatement st1 = con.prepareStatement("INSERT INTO post (empId,dateTime,postText,postImage) VALUES(?,?,?,?)");
            st1.setInt(1, empId);
            st1.setString(2, pDate);
            st1.setString(3, text);
            st1.setInt(4, tot);
            st1.executeUpdate();
            st1.close();

            statement = con.createStatement();
            rs = statement.executeQuery("SELECT postId FROM post ORDER BY postId DESC LIMIT 1");
            if(rs.next())
            {
                pId = rs.getInt("postId");
                for(int i=0;i<tot;i++)
                {
                    if(image[i]!=null)
                    {
                        PreparedStatement st2 = con.prepareStatement("INSERT INTO postimgs VALUES(?,?,?)");
                        st2.setInt(1,pId);
                        st2.setInt(2,i+1);
                        st2.setBlob(3, image[i]);
                        st2.executeUpdate();
                        st2.close();
                    }
                }
            }

            con.close();
            result = "Successful";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            result = "Unsuccessful";

        }
        return result;
    }

    public String updatePost(String pId, String pDate, String text, InputStream newImage[],int newImg,  String delImage[], int delImg, int maxId, int count)
    {
        int postId, totImg;
        String result;
        totImg = (count-delImg)+newImg;
        Statement statement = null;
        ResultSet rs = null;
        postId = Integer.parseInt(pId);
        Connection con = null;
        try
        {
            con = DBconn.getConnection();

            PreparedStatement st1 = con.prepareStatement("UPDATE post SET dateTime=?,postText=?,postImage=?  WHERE postId=?");
            st1.setString(1, pDate);
            st1.setString(2, text);
            st1.setInt(3, totImg);
            st1.setInt(4, postId);
            st1.executeUpdate();
            st1.close();

            for(int i=0;i<delImg;i++)
            {
                PreparedStatement st2 = con.prepareStatement("DELETE From postimgs WHERE postId=? AND imageId=?");
                st2.setInt(1, postId);
                st2.setInt(2,Integer.parseInt(delImage[i]));
                st2.executeUpdate();
                st2.close();
            }

            for(int i=0;i<newImg;i++)
            {
                if(newImage[i]!=null)
                {
                    PreparedStatement st3 = con.prepareStatement("INSERT INTO postimgs VALUES(?,?,?)");
                    st3.setInt(1,postId);
                    st3.setInt(2,maxId);
                    st3.setBlob(3, newImage[i]);
                    st3.executeUpdate();
                    st3.close();
                    maxId++;
                }
            }


            con.close();
            result = "Successful";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            result = "Unsuccessful";
        }
        return result;
    }

    public  String delPost(String pId)
    {

        String result,empId;
        Connection con = null;
        ResultSet rs = null;

        try
        {
            con = DBconn.getConnection();
            PreparedStatement st = con.prepareStatement("DELETE From post WHERE postId=?");

            st.setString(1,pId );
            st.executeUpdate();

            result = "Successful";

            st.close();
            con.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            result = "Unsuccessful";
        }
        return result;
    }
}
