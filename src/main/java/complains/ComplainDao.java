package complains;
import DBconnection.DBconn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplainDao {
    public String addData(ComplainBean cb){

        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String empId,comId,date,description,result;
        empId=cb.getEmpId();
        comId=cb.getcomId();
        date=cb.getDate();
        description=cb.getDescription();


        try {
            con = DBconn.getConnection();

            PreparedStatement st1 = con.prepareStatement("INSERT INTO complainsuggestion VALUES(?,?,?,?)");

            st1.setString(1, comId);
            st1.setString(2,empId);
            st1.setString(3, description);
            st1.setString(4,date);

            PreparedStatement st2= con.prepareStatement("UPDATE notification SET complainSuggestionFlag=?  " + "WHERE receiverId != ?");

            st2.setInt(1, 1);
            st2.setString(2, empId);

            st1.executeUpdate();
            st2.executeUpdate();


            st1.close();

            con.close();

            result="Successful";

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            result = "Unsuccessful";
        }
        return result;
    }
    public String removeComplain(ComplainBean com){
        String result,comId;
        comId = com.getcomId();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        if(comId == ""){
            return "Unsuccessful";
        }
            try{
            con = DBconn.getConnection();
            PreparedStatement st1 = con.prepareStatement("DELETE From complainsuggestion WHERE ID=?");
            st1.setString(1,comId );
            st1.executeUpdate();
            result = "Successful";
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            result = "Unsuccessful";
            return result;
        }
    }
    public List<ComplainBean> allComplains(String myId){
        List<ComplainBean> complains = new ArrayList<ComplainBean>();

        Connection con = null;
        Statement statement = null;
        ResultSet rs1,rs2 = null;


        String empId,comId,date,description,result;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs1 = statement.executeQuery("SELECT * FROM `complainsuggestion`  GROUP BY ID DESC");
            while (rs1.next())
            {
                ComplainBean cb = new ComplainBean();
                comId= rs1.getString("ID");
                empId = rs1.getString("empId");
                description=rs1.getString("description");
                date=rs1.getString("Date");

                cb.setcomId(comId);
                cb.setEmpId(empId);
                cb.setDate(date);
                cb.setDescription(description);


                complains.add(cb);
                PreparedStatement st2= con.prepareStatement("UPDATE notification SET complainSuggestionFlag=?  " + "WHERE receiverId = ?");

                st2.setInt(1, 0);
                st2.setString(2, myId);
                st2.executeUpdate();
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return complains;
    }
}
