package attendance;

import DBconnection.DBconn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class attendanceDao {
    public List<attendanceBean> myAttendances(String empId ,String from ,String to,String workedHoursFrom ,String workedHoursTo ,String otHoursFrom ,String otHoursTo ) {
        List<attendanceBean> myAttendances = new ArrayList<attendanceBean>();

    Connection con = null;
    Statement statement = null;
    ResultSet rs1, rs2 = null;
        if (from == null || from.equals(" ") || from.isEmpty()) {
        from = "0000-00-0";
    }
        if (to == null || to.equals(" ") || to.isEmpty()) {
        to = "9999-12-31";
    }
        if (otHoursFrom == null) {
        otHoursFrom = "0.0";
    }
        if (otHoursTo == null) {
        otHoursTo = "24.0";
    }
        if (workedHoursFrom == null || workedHoursFrom.length()!=5) {
        workedHoursFrom = "00:00:00";
    }
        if (workedHoursTo == null || workedHoursTo.length()!=5) {
        workedHoursTo = "23:59:59";
    }
        try {
        con = DBconn.getConnection();
        statement = con.createStatement();
        rs1 = statement.executeQuery("SELECT * FROM `attendance` WHERE  date between '" + from + "' and '" + to + "' and workedHr between '" + workedHoursFrom + "' and '" + workedHoursTo + "' and otHr between '" + otHoursFrom + "' and '" + otHoursTo + "' and  empId=" + empId + " ORDER BY attendanceId  ASC");
        while (rs1.next()) {

            attendanceBean aB = new attendanceBean();
            aB.setEmpId(rs1.getString("empId"));
            aB.setAttendId(rs1.getString("attendanceId"));
            aB.setDate(rs1.getString("date"));
            aB.setAttendTime(rs1.getString("attendTime"));
            aB.setLeaveTime(rs1.getString("leaveTime"));
            aB.setWorkedHrs(rs1.getString("workedHr"));
            aB.setOtHours(rs1.getFloat("otHr"));
            myAttendances.add(aB);
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
        return myAttendances;
}

    public List<attendanceBean> staffAttendance(String empId, String from, String to, String workedHoursFrom, String workedHoursTo, String otHoursFrom, String otHoursTo) {
        List<attendanceBean> staffAttendances = new ArrayList<attendanceBean>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs1, rs2 = null;
        if (from == null || from.equals(" ") || from.isEmpty()) {
            from = "0000-00-0";
        }
        if (to == null || to.equals(" ") || to.isEmpty()) {
            to = "9999-12-31";
        }
        //No Need Start
        if (otHoursFrom == null) {
            otHoursFrom = "0.0";
        }
        if (otHoursTo == null) {
            otHoursTo = "24.0";
        }
        if (workedHoursFrom == null || workedHoursFrom.length()!=5) {
            workedHoursFrom = "00:00:00";
        }
        if (workedHoursTo == null || workedHoursTo.length()!=5) {
            workedHoursTo = "23:59:59";
        }
        //No Need End
        if (empId == null || empId.isEmpty()){
            empId = "10000";
        }
        try {
            con = DBconn.getConnection();
            statement = con.createStatement();
            if (empId.equals("10000") || empId == null ){
                //System.out.println("def - "+empId);
                //System.out.println("SELECT * FROM `attendance` WHERE  date between '" + from + "' and '" + to + "' and workedHr between '" + workedHoursFrom + "' and '" + workedHoursTo + "' and otHr between '" + otHoursFrom + "' and '" + otHoursTo + "' ORDER BY attendanceId  ASC");
                rs1 = statement.executeQuery("SELECT * FROM `attendance` WHERE  date between '" + from + "' and '" + to + "' and workedHr between '" + workedHoursFrom + "' and '" + workedHoursTo + "' and otHr between '" + otHoursFrom + "' and '" + otHoursTo + "'  ORDER BY attendanceId  ASC");

            }else{
                //System.out.println("else - "+empId);
                //System.out.println("SELECT * FROM `attendance` WHERE  date between '" + from + "' and '" + to + "' and workedHr between '" + workedHoursFrom + "' and '" + workedHoursTo + "' and otHr between '" + otHoursFrom + "' and '" + otHoursTo + "' and  empId=" + empId + " ORDER BY attendanceId  ASC");
                rs1 = statement.executeQuery("SELECT * FROM `attendance` WHERE  date between '" + from + "' and '" + to + "' and workedHr between '" + workedHoursFrom + "' and '" + workedHoursTo + "' and otHr between '" + otHoursFrom + "' and '" + otHoursTo + "' and  empId=" + empId + " ORDER BY attendanceId  ASC");

            }
            while(rs1.next()) {

                attendanceBean aB = new attendanceBean();

                aB.setEmpId(rs1.getString("empId"));
                aB.setAttendId(rs1.getString("attendanceId"));
                aB.setDate(rs1.getString("date"));
                aB.setAttendTime(rs1.getString("attendTime"));
                aB.setLeaveTime(rs1.getString("leaveTime"));
                aB.setWorkedHrs(rs1.getString("workedHr"));
                aB.setOtHours(rs1.getFloat("otHr"));

                staffAttendances.add(aB);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return staffAttendances;
    }
}