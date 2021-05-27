package Customize;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import DBconnection.DBconn;
import java.util.List;
import java.sql.*;
import java.text.SimpleDateFormat;


public class CustomizeDao {
    public String addData(CustomizeBean cb) {
        Connection con = null;
        String result;
        String startTime, endTime, empId;
        int salaryCalculation;
        String resetDate;

        startTime = cb.getstartTime();
        endTime = cb.getendTime();
        salaryCalculation = cb.getsalaryCalculation();
        resetDate = cb.getreset();
        empId = cb.getEmpId();

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
        String today = formatter.format(date);

        try {

            con = DBconn.getConnection();

            PreparedStatement st1 = con.prepareStatement("INSERT INTO customizeddata (dateUpdated,startTime,endTime,salaryCalculationDate,resetDate,changedBy,flag) VALUES(?,?,?,?,?,?,?)");

            st1.setString(1, today);
            st1.setString(2, startTime);
            st1.setString(3, endTime);
            st1.setInt(4, salaryCalculation);
            st1.setString(5, resetDate);
            st1.setString(6, empId);
            st1.setInt(7, 0);

            st1.executeUpdate();

            st1.close();
            con.close();
            result = "Successful";

        } catch (SQLException throwables) {
            System.out.println(throwables);
            throwables.printStackTrace();
            result = "Unsuccessful";
        }
        return result;
    }

    public List<CustomizeBean> searchAll() {
        List<CustomizeBean> list = new ArrayList<CustomizeBean>();

        Connection con = null;
        Statement statement = null;
        ResultSet rs1 = null;

        String startTime, endTime, changeDate, changeBy;
        int salaryCalculation;
        String resetDate;

        try {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs1 = statement.executeQuery("SELECT * FROM `customizeddata` ORDER BY`ID` DESC");
            rs1.next();
            while (rs1.next()) {
                startTime = rs1.getString("startTime");
                endTime = rs1.getString("endTime");
                resetDate = rs1.getString("resetDate");
                salaryCalculation = rs1.getInt("salaryCalculationDate");
                changeDate = rs1.getString("dateUpdated");
                changeBy = rs1.getString("changedBy");


                con = DBconn.getConnection();
                CustomizeBean cb = new CustomizeBean();

                cb.setstartTime(startTime);
                cb.setendTime(endTime);
                cb.setsalaryCalculation(salaryCalculation);
                cb.setreset(resetDate);
                cb.setDate(changeDate);
                cb.setEmpId(changeBy);

                list.add(cb);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public CustomizeBean searchCus() {
        CustomizeBean cb = new CustomizeBean();
        String result = null;
        String startTime, endTime, changeDate, changeBy;
        int salaryCalculation;
        String resetDate;
        Connection con = null;
        Statement statement = null;
        ResultSet rs1 = null;

        con = DBconn.getConnection();
        try {
            statement = con.createStatement();
            rs1 = statement.executeQuery("SELECT * FROM `customizeddata` ORDER BY`ID` DESC LIMIT 1");

            while (rs1.next()) {
                startTime = rs1.getString("startTime");
                endTime = rs1.getString("endTime");
                resetDate = rs1.getString("resetDate");
                salaryCalculation = rs1.getInt("salaryCalculationDate");
                changeDate = rs1.getString("dateUpdated");
                changeBy = rs1.getString("changedBy");

                cb.setstartTime(startTime);
                cb.setendTime(endTime);
                cb.setsalaryCalculation(salaryCalculation);
                cb.setreset(resetDate);
                cb.setDate(changeDate);
                cb.setEmpId(changeBy);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cb;
    }
    public String checker() {
        Connection con = null;
        Statement statement = null;
        ResultSet rs1 = null;
        String result=null;
        con = DBconn.getConnection();
        try {
            statement = con.createStatement();
            rs1 = statement.executeQuery("SELECT * FROM `customizeddata` ORDER BY`ID` DESC LIMIT 1");

           if (rs1.next()){
               result= "Yes";
           }else{

               result=  "No";
           }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    public String resetSystem() {
        Connection con = null;
        Statement statement = null;
        ResultSet rs,rs1 = null;

        String  empId,ID = null;
        int totalNoPayLeaves;
        int totalMedicalLeaves;
        float totalPayedLeaves;

        try {
            con = DBconn.getConnection();
            statement = con.createStatement();

            rs = statement.executeQuery("SELECT * FROM employeeleavedetails");

            while (rs.next()) {
                empId=rs.getString("empId");
                totalPayedLeaves = rs.getFloat("totalPayedLeaves");
                totalNoPayLeaves	 = rs.getInt("totalNoPayLeaves");
                totalMedicalLeaves = rs.getInt("totalMedicalLeaves");

                PreparedStatement st2 = con.prepareStatement("UPDATE employeeleavedetails SET remainingPayedLeaves=? , takenPayedLeaves=? , remainingNoPayLeaves=? , tackenNoPayLeaves=? , remainingMedicalLeaves=? , tackenMedicalLeaves=? WHERE empId=?");
                st2.setFloat(1,totalPayedLeaves);
                st2.setFloat(2,0);
                st2.setInt(3, totalNoPayLeaves);
                st2.setInt(4,0);
                st2.setInt(5,totalMedicalLeaves);
                st2.setInt(6, 0);
                st2.setString(7, empId);
                st2.executeUpdate();
            }
            rs1 = statement.executeQuery("SELECT ID FROM `customizeddata` ORDER BY`ID` DESC LIMIT 1");
            if (rs1.next()){
                ID=rs1.getString("ID");

            }
            PreparedStatement st2= con.prepareStatement("UPDATE customizeddata SET flag=?  " + "WHERE ID = ?");

            st2.setInt(1, 1);
            st2.setString(2, ID);

            st2.executeUpdate();
            return "1";
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public CustomizeBean getResetData() {
        String resetDate;
        int flag = 0;
        CustomizeBean cb=new CustomizeBean();
        try {
            Connection con = null;
            Statement statement = null;
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet customize = statement.executeQuery("SELECT resetDate,flag FROM `customizeddata` ORDER BY`ID` DESC LIMIT 1");
            String month = null,day = null;

            if (customize.next()){
                    flag = customize.getInt("flag");
                    resetDate = customize.getString("resetDate");


                    String[] arrOfStr = resetDate.split("-", 0);
                    int d = Integer.parseInt(arrOfStr[0]);
                    if (d<=9){
                        month=arrOfStr[1];
                        day="0" + d;
                        resetDate=month+"-"+day;
                    }else{
                        month=arrOfStr[1];
                        day= String.valueOf(d);
                        resetDate=month+"-"+day;
                    }
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                    String currentYear= formatter.format(date);
                    resetDate=currentYear+"-"+resetDate;

                    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                    String today= formatter2.format(date);

                    Date reset=new SimpleDateFormat("yyyy-MM-dd").parse(resetDate);

                    if (resetDate.equals(today)){
                        cb.setreset("equal");
                    }else{
                        cb.setreset("notEqual");

                    }
                    cb.setflag(flag);
                }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return cb;

    }
    public void setTheCustomFlgs() {
        String resetDate;
        int flag = 0;
        CustomizeBean cb=new CustomizeBean();

        try {
            Connection con = null;
            Statement statement = null;
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet customize = statement.executeQuery("SELECT resetDate,flag FROM `customizeddata` ORDER BY`ID` DESC LIMIT 1");
            String month = null,day = null;
            if (customize.next()){
                flag = customize.getInt("flag");
                resetDate = customize.getString("resetDate");
                String[] arrOfStr = resetDate.split("-", 0);
                int d = Integer.parseInt(arrOfStr[0]);
                if (d<=9){
                    month=arrOfStr[1];
                    day="0" + d;
                    resetDate=month+"-"+day;
                }else{
                    month=arrOfStr[1];
                    day= String.valueOf(d);
                    resetDate=month+"-"+day;
                }

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");

                String currentYear= formatter.format(date);
                String today= formatter2.format(date);
                resetDate=currentYear+"-"+resetDate;
                if (resetDate.equals(today)){
                }
                else{
                    String ID = null;
                    ResultSet rs1 = statement.executeQuery("SELECT ID FROM `customizeddata` ORDER BY`ID` DESC LIMIT 1");
                    if (rs1.next()){
                        ID=rs1.getString("ID");

                    }
                    PreparedStatement st2= con.prepareStatement("UPDATE customizeddata SET flag=?  " + "WHERE ID = ?");
                    st2.setInt(1, 0);
                    st2.setString(2, ID);
                    st2.executeUpdate();
                }
                cb.setflag(flag);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}