package salary;
import DBconnection.DBconn;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

// Daily Payment = Basic Salary /(office days-payed leaves - extra holidays)

public class SalaryDao {
    //2021/03/31 01:46 Change Start
    public String calculatePayment(SalaryBean salaryObj) throws ParseException {
        Date start;
        Date end;
        Connection con = null;
        Statement statement = null;
        float totalSalary = 0;
        List<SalaryBean> salaryList = new ArrayList<SalaryBean>();
        List<SalaryBean> allleavesDetails = new ArrayList<SalaryBean>();
        List<SalaryBean> salaryList3 = new ArrayList<SalaryBean>();

        List<SalaryBean> salaryListFinal = new ArrayList<SalaryBean>();
        List<SalaryBean> allUsers = new ArrayList<SalaryBean>();

        int officeWorkingDays, erros = 0;

        int extraHolidays = salaryObj.getExtraHolidays();
        String salaryId;
        String empId;
        int noPayleaves = 0, leaves;
        float basicSalary = 0, otRate = 0, dailySalary = 0;
        //  System.out.println("extraHolidays = "+extraHolidays);


        Date calculationDate = new Date();//today
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat yearEnd = new SimpleDateFormat("yyyy");
        String yEnd = yearEnd.format(calculationDate);

        SimpleDateFormat monthEnd = new SimpleDateFormat("MM");
        String mEnd = monthEnd.format(calculationDate);

        SimpleDateFormat dayEnd = new SimpleDateFormat("dd");
        String dEnd = dayEnd.format(calculationDate);


        String toDate = yEnd + "-" + mEnd + "-" + String.valueOf(Integer.parseInt(dEnd));

        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String yStart = year.format(calculationDate);

        SimpleDateFormat month = new SimpleDateFormat("MM");
        String mStart = month.format(calculationDate);

        SimpleDateFormat day = new SimpleDateFormat("dd");
        String dStart = day.format(calculationDate);


        int y = 0, m = 0, d = 0;
        if (mStart.equals("01")) {
            y = Integer.parseInt(yStart) - 1;
            m = 12;
        } else {

            y = Integer.parseInt(yStart);
            m = Integer.parseInt(mStart) - 1;
        }

        d = Integer.parseInt(dStart) + 1;

        String fromDate = String.valueOf(y) + '-' + String.valueOf(m) + "-" + String.valueOf(d) + " ";

        start = formatter.parse(fromDate);
        end = formatter.parse(toDate);

        System.out.println("fromDate = " + fromDate);
        System.out.println("todate = " + toDate);


        SalaryBean defaultData = new SalaryBean();
        defaultData.setToDate(toDate);
        defaultData.setFromDate(fromDate);

        officeWorkingDays = workingDays(start, end) - extraHolidays;

        System.out.println("officeWorkingDays = " + officeWorkingDays);

        try {
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `employeesalarydetails` ORDER BY `employeesalarydetails`.`empId` ASC");
            while (rs.next()) {
                empId = rs.getString("empId");
                basicSalary = rs.getFloat("basicSalary");
                otRate = rs.getFloat("otRate");
                salaryId = yStart + "/" + mStart + "/" + empId;
                PreparedStatement st1 = con.prepareStatement("INSERT INTO monthlysalarydetails VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");


                st1.setString(1, salaryId);
                st1.setString(2, empId);
                st1.setFloat(3, basicSalary);
                st1.setFloat(4, 0);
                st1.setInt(5, 0);
                st1.setInt(6, officeWorkingDays);
                st1.setInt(7, 0);
                st1.setFloat(8, otRate);
                st1.setString(9, fromDate);
                st1.setString(10, toDate);
                st1.setFloat(11, 0);
                st1.setFloat(12, 0);
                st1.executeUpdate();
            }
            //start to count number of no pays
            ResultSet nopays = statement.executeQuery("SELECT SUM(`affectedLeaveCount`) as leaves ,LeaveType,empId,status  FROM `leavedetails` WHERE LeaveType='NoPay' and  status=2 and leaveFrom >= '" + fromDate + "' and leaveTo <= '" + toDate + "' GROUP BY empId ");
            while (nopays.next()) {

                SalaryBean salaryBean = new SalaryBean();
                empId = nopays.getString("empId");
                noPayleaves = Integer.parseInt(nopays.getString("leaves"));
                salaryId = yStart + "/" + mStart + "/" + empId;

                salaryBean.setEmpId(empId);
                salaryBean.setNoPayes(noPayleaves);
                salaryBean.setSalaryId(salaryId);
                salaryBean.setOfficeWorkingDays(officeWorkingDays);
                salaryList.add(salaryBean);

                PreparedStatement st1 = con.prepareStatement("UPDATE monthlysalarydetails SET NoPayLeaves=?  WHERE salaryId=?");

                st1.setInt(1, noPayleaves);
                st1.setString(2, salaryId);

                st1.executeUpdate();
            }
            //counting all leaves
            ResultSet allleaves = statement.executeQuery("SELECT SUM(`affectedLeaveCount`) as leaves ,LeaveType,empId,status  FROM `leavedetails` WHERE  status=2 and leaveFrom >= '" + fromDate + "' and leaveTo <= '" + toDate + "' GROUP BY empId ");
            while (allleaves.next()) {
                SalaryBean salaryBean = new SalaryBean();
                empId = allleaves.getString("empId");
                int allLeavesCount = Integer.parseInt(allleaves.getString("leaves"));
                salaryId = yStart + "/" + mStart + "/" + empId;

                salaryBean.setEmpId(empId);
                salaryBean.setTotalLeaves(allLeavesCount);
                allleavesDetails.add(salaryBean);
                PreparedStatement st1 = con.prepareStatement("UPDATE monthlysalarydetails SET absentDays=?  WHERE salaryId=?");

                st1.setInt(1, allLeavesCount);//first save the all tacken leaves for the absentDays in db.
                st1.setString(2, salaryId);
                st1.executeUpdate();
            }

            //calculate daily salary
            ResultSet salryDetails = statement.executeQuery("SELECT * FROM `employeesalarydetails` ");
            while (salryDetails.next()) {
                empId = salryDetails.getString("empId");
                salaryId = yStart + "/" + mStart + "/" + empId;
                basicSalary = salryDetails.getFloat("basicSalary");

                dailySalary = basicSalary / officeWorkingDays;

                PreparedStatement st3 = con.prepareStatement("UPDATE monthlysalarydetails SET dailyPayment=? WHERE salaryId=?");

                st3.setFloat(1, dailySalary);
                st3.setString(2, salaryId);
                st3.executeUpdate();

                SalaryBean user = new SalaryBean();
                user.setEmpId(empId);
                allUsers.add(user);
            }

            // update the table with final absent days
            for (SalaryBean users : allUsers) {
                int totalLeaves = 0, attendances = 0, finalAbsantDays = 0;
                float ot = 0;
                empId = users.getEmpId();
                salaryId = yStart + "/" + mStart + "/" + empId;
                System.out.println("------------------------------------------ " + empId);
                ResultSet getAttendances = statement.executeQuery("SELECT COUNT(date) as attendances,sum(otHr) as oT from attendance WHERE empId = " + empId + " and date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                System.out.println("SELECT COUNT(date) as attendances from attendance WHERE empId = " + empId + " and date BETWEEN '" + fromDate + "' AND '" + toDate + "'");
                while (getAttendances.next()) {
                    //get attendances from attendance table
                    attendances = getAttendances.getInt("attendances");
                    ot = getAttendances.getFloat("oT");
                }
                ResultSet allLeaveCount = statement.executeQuery("SELECT officeDays,absentDays FROM `monthlysalarydetails` where salaryId='" + salaryId + "'");

                while (allLeaveCount.next()) {
                    //get the approved leave count for that period
                    totalLeaves = allLeaveCount.getInt("absentDays");
                }
                System.out.println("officeWorking days = " + officeWorkingDays);
                System.out.println("attendances = " + attendances);
                System.out.println("totalLeaves = " + totalLeaves);
                finalAbsantDays = officeWorkingDays - attendances - totalLeaves;
                System.out.println("Absent Days = " + finalAbsantDays);
                PreparedStatement st1 = con.prepareStatement("UPDATE monthlysalarydetails SET absentDays=?,totalOThr=? WHERE salaryId=?");
                st1.setInt(1, finalAbsantDays);
                st1.setFloat(2, ot);
                st1.setString(3, salaryId);
                st1.executeUpdate();


                if (finalAbsantDays != 0) {
                    ResultSet remainingNopaySet = statement.executeQuery("SELECT remainingNoPayLeaves FROM employeeleavedetails WHERE empId = '" + empId + "'");
                    if (remainingNopaySet.next()) {
                        int remaingNopay = remainingNopaySet.getInt("remainingNoPayLeaves");
                        System.out.println("remaining leaves = " + remaingNopay);
                        if (finalAbsantDays <= remaingNopay) {
                            int newNopay = remaingNopay - finalAbsantDays;
                            System.out.println("newNopay " + newNopay);

                            PreparedStatement st5 = con.prepareStatement("UPDATE employeeleavedetails SET remainingNoPayLeaves=?,tackenNoPayLeaves=? WHERE empId=?");
                            st5.setInt(1, newNopay);
                            st5.setInt(2, finalAbsantDays);
                            st5.setString(3, empId);
                            st5.executeUpdate();
                            totalSalary = (basicSalary + (ot * otRate) - dailySalary * (noPayleaves + finalAbsantDays));
                        }else{
                            totalSalary = -404;
                            erros = erros + 1;
                        }
                    }
                } else {
                    ResultSet setFullSalary = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE salaryId = '" + salaryId + "'");
                    while (setFullSalary.next()) {

                        basicSalary = setFullSalary.getFloat("basicSalary");
                        dailySalary = setFullSalary.getFloat("dailyPayment");
                        noPayleaves = setFullSalary.getInt("NoPayLeaves");
                        otRate = setFullSalary.getInt("otRate");
                        finalAbsantDays = setFullSalary.getInt("absentDays");
                        ot = setFullSalary.getFloat("totalOThr");
                        totalSalary = (basicSalary + (ot * otRate) - dailySalary * (noPayleaves + finalAbsantDays));

                    }
                }
                PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET totalSalary=? WHERE salaryId=?");
                st4.setFloat(1, totalSalary);
                st4.setString(2, salaryId);
                st4.executeUpdate();


                PreparedStatement st3= con.prepareStatement("UPDATE notification SET salaryFlag=? WHERE receiverId=?");
                st3.setInt(1, 1);
                st3.setString(2, empId);
                st3.executeUpdate();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return "unsucessful";
        }
        System.out.println("totalSalary errors = " +erros);
        if (erros>0){
            return "errors";
        }else{
            return "noerrors";
        }
    }
    //2021/03/31 01:46 Change End
    //Previous Function
    /*public String calculatePayment(SalaryBean salaryObj) throws ParseException {
        Date start;
        Date end;
        Connection con = null;
        Statement statement = null;

        List<SalaryBean> salaryList = new ArrayList<SalaryBean>();
        List<SalaryBean> salaryListFinal = new ArrayList<SalaryBean>();
        List<SalaryBean> allUsers = new ArrayList<SalaryBean>();

        int officeWorkingDays,erros = 0;

        int extraHolidays=salaryObj.getExtraHolidays();
        String salaryId;
        String empId;
        int noPayleaves,leaves;
        float basicSalary,otRate, dailySalary;
      //  System.out.println("extraHolidays = "+extraHolidays);


        Date calculationDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat yearEnd = new SimpleDateFormat("yyyy");
        String yEnd= yearEnd.format(calculationDate);

        SimpleDateFormat monthEnd = new SimpleDateFormat("MM");
        String mEnd= monthEnd.format(calculationDate);

        SimpleDateFormat dayEnd = new SimpleDateFormat("dd");
        String dEnd= dayEnd.format(calculationDate);


        String toDate=yEnd+"-"+mEnd+"-"+String.valueOf(Integer.parseInt(dEnd));

        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        String yStart= year.format(calculationDate);

        SimpleDateFormat month = new SimpleDateFormat("MM");
        String mStart= month.format(calculationDate);

        SimpleDateFormat day = new SimpleDateFormat("dd");
        String dStart= day.format(calculationDate);

        System.out.println(mStart  +"- "+dStart);

        int y=0,m=0,d=0;
        if (mStart.equals("01")){
             y= Integer.parseInt(yStart)-1;
             m=12;
        }else {

            y= Integer.parseInt(yStart);
            m= Integer.parseInt(mStart)-1;
        }

        d= Integer.parseInt(dStart)+1;

        String fromDate= String.valueOf(y)+ '-' + String.valueOf(m) +"-" +String.valueOf(d)+" ";

        start=formatter.parse(fromDate);
        end= formatter.parse(toDate);

        System.out.println("fromDate = "+start);
        System.out.println("todate = "+end);
        SalaryBean defaultData = new SalaryBean();

        defaultData.setToDate(toDate);
        defaultData.setFromDate(fromDate);

        officeWorkingDays=workingDays(start,end)-extraHolidays;

        System.out.println(workingDays(start,end));

        try {
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `employeesalarydetails` ORDER BY `employeesalarydetails`.`empId` ASC");
            while(rs.next()){
                empId=rs.getString("empId");
                basicSalary = rs.getFloat("basicSalary");
                otRate = rs.getFloat("otRate");
                salaryId=yStart+"/"+mStart +"/"+empId;
                PreparedStatement st1 = con.prepareStatement("INSERT INTO monthlysalarydetails VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");


                st1.setString(1, salaryId);
                st1.setString(2, empId);
                st1.setFloat(3, basicSalary);
                st1.setFloat(4, 0);
                st1.setInt(5, 0);
                st1.setInt(6, officeWorkingDays);
                st1.setInt(7, 0);
                st1.setFloat(8, otRate);
                st1.setString(9, fromDate);
                st1.setString(10, toDate);
                st1.setFloat(11, 0);
                st1.setFloat(12, 0);
                st1.executeUpdate();
            }

            ResultSet rs1 = statement.executeQuery("SELECT SUM(`affectedLeaveCount`) as leaves ,LeaveType,empId,status  FROM `leavedetails` WHERE LeaveType='NoPay' and  status=2 and leaveFrom >= '" + fromDate + "' and leaveTo <= '" + toDate + "' GROUP BY empId ");
            System.out.println("SELECT SUM(`affectedLeaveCount`) as leaves ,LeaveType,empId,status  FROM `leavedetails` WHERE LeaveType='NoPay' and  status=2 and leaveFrom >= '" + fromDate + "' and leaveTo <= '" + toDate + "' GROUP BY empId ");
            while (rs1.next()) {

                SalaryBean salaryBean = new SalaryBean();
                empId = rs1.getString("empId");
                noPayleaves = Integer.parseInt(rs1.getString("leaves"));
                salaryId=yStart+"/"+mStart +"/"+empId;

                salaryBean.setEmpId(empId);
                salaryBean.setNoPayes(noPayleaves);
                salaryBean.setSalaryId(salaryId);
                salaryBean.setOfficeWorkingDays(officeWorkingDays);
                salaryList.add(salaryBean);


                PreparedStatement st1 = con.prepareStatement("UPDATE monthlysalarydetails SET NoPayLeaves=?  WHERE salaryId=?");

                st1.setInt(1, noPayleaves);
                st1.setString(2, salaryId);

                st1.executeUpdate();
            }
            ResultSet salryDetails = statement.executeQuery("SELECT * FROM `monthlysalarydetails` ");
            while (salryDetails.next()) {
                empId = salryDetails.getString("empId");
                salaryId=yStart+"/"+mStart +"/"+empId;
                basicSalary = salryDetails.getFloat("basicSalary");

                dailySalary=basicSalary/officeWorkingDays;

                PreparedStatement st3 = con.prepareStatement("UPDATE monthlysalarydetails SET dailyPayment=? WHERE salaryId=?");

                st3.setFloat(1, dailySalary);
                st3.setString(2, salaryId);
                st3.executeUpdate();
            }

            for (SalaryBean sal : salaryList) {
                empId = sal.getEmpId();
                salaryId = sal.getSalarId();
                noPayleaves=sal.getNoPayes();
                int crossedLeaves = 0;
                ResultSet crossed = statement.executeQuery("SELECT LeaveType,empId,leaveFrom,leaveTo FROM `leavedetails` WHERE LeaveType='NoPay' and status=2 and leaveFrom < '" + fromDate + "' and '" + fromDate + "' < leaveTo and empId=" + empId + " ORDER BY `leavedetails`.`empId` ASC");

                while (crossed.next()) {
                    String toDateLeave = crossed.getString("leaveTo");
                    start = formatter.parse(fromDate);
                    end = formatter.parse(toDateLeave);


                    crossedLeaves = crossedLeaves + workingDays(start, end);
                }

                PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET NoPayLeaves=? WHERE salaryId=?");
                st4.setInt(1,noPayleaves+crossedLeaves);
                st4.setString(2, salaryId);
                st4.executeUpdate();
                sal.setNoPayes(noPayleaves+crossedLeaves);

            }

            for (SalaryBean sal : salaryList) {
                empId = sal.getEmpId();
                salaryId = sal.getSalarId();
                noPayleaves=sal.getNoPayes();
                ResultSet crossed2 = statement.executeQuery("SELECT LeaveType,empId,leaveFrom,leaveTo FROM `leavedetails` WHERE LeaveType='NoPay' and status=2 and leaveFrom < '" + toDate + "' and '" + toDate + "' < leaveTo and empId=" + empId + " ORDER BY `leavedetails`.`empId` ASC");
                int crossedLeaves = 0;
                while (crossed2.next()){
                    fromDate = crossed2.getString("leaveFrom");
                    start = formatter.parse(fromDate);
                    end= formatter.parse(toDate);


                    crossedLeaves=crossedLeaves+workingDays(start,end);
                }

                PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET NoPayLeaves=? WHERE salaryId=?");
                st4.setInt(1,noPayleaves+crossedLeaves);
                st4.setString(2, salaryId);
                st4.executeUpdate();
                sal.setNoPayes(noPayleaves+crossedLeaves);
                noPayleaves=sal.getNoPayes();
              // System.out.println("Final No payes =" +noPayleaves);
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        toDate=defaultData.getToDate();
        fromDate=defaultData.getFromDate();
        try {
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet rs1 = statement.executeQuery("SELECT SUM(`affectedLeaveCount`) as leaves ,LeaveType,empId,status  FROM `leavedetails` WHERE  status=2 and leaveFrom >= '" + fromDate + "' and leaveTo <= '" + toDate + "' GROUP BY empId ");

            while (rs1.next()) {

                empId = rs1.getString("empId");
                leaves = Integer.parseInt(rs1.getString("leaves"));
              //  System.out.println("leaves =  =   = " +leaves);
                salaryId=yStart+"/"+mStart +"/"+empId;

                PreparedStatement st1 = con.prepareStatement("UPDATE monthlysalarydetails SET absentDays=? WHERE salaryId=?");
                st1.setInt(1,leaves);
                st1.setString(2, salaryId);
                st1.executeUpdate();
            }
            for (SalaryBean sal : salaryList) {
                empId = sal.getEmpId();
                salaryId = sal.getSalarId();
                leaves=sal.getTotalLeaves();
                int crossedLeaves = 0;
                int currentCount = 0;
                ResultSet crossed = statement.executeQuery("SELECT LeaveType,empId,leaveFrom,leaveTo FROM `leavedetails` WHERE status=2 and leaveFrom <= '" + fromDate + "' and '" + fromDate + "' <= leaveTo and empId=" + empId + " ORDER BY `leavedetails`.`empId` ASC");

                while (crossed.next()) {

                    String toDateLeave = crossed.getString("leaveTo");
                    start = formatter.parse(fromDate);
                    end = formatter.parse(toDateLeave);
                    crossedLeaves = crossedLeaves + workingDays(start, end);
                }
                ResultSet setAbsences = statement.executeQuery("SELECT officeDays,absentDays FROM `monthlysalarydetails` where salaryId='" + salaryId + "'");
                while (setAbsences.next()){
                     currentCount=setAbsences.getInt("absentDays");
                }

                PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET absentDays=? WHERE salaryId=?");
                st4.setInt(1,currentCount+crossedLeaves);
                st4.setString(2, salaryId);
                st4.executeUpdate();

            }


            for (SalaryBean sal : salaryList) {
                empId = sal.getEmpId();
                salaryId = sal.getSalarId();

                ResultSet crossed2 = statement.executeQuery("SELECT LeaveType,empId,leaveFrom,leaveTo FROM `leavedetails` WHERE  status=2 and leaveFrom <= '" + toDate + "' and '" + toDate + "' <= leaveTo and empId=" + empId + " ORDER BY `leavedetails`.`empId` ASC");
                int crossedLeaves = 0,currentCount=0;
                while (crossed2.next()){

                    fromDate = crossed2.getString("leaveFrom");
                    start = formatter.parse(fromDate);
                    end= formatter.parse(toDate);
                    crossedLeaves=crossedLeaves+workingDays(start,end);
                }
                ResultSet setAbsences = statement.executeQuery("SELECT officeDays,absentDays FROM `monthlysalarydetails` where salaryId='" + salaryId + "'");
                while (setAbsences.next()){
                    currentCount=setAbsences.getInt("absentDays");
                }
                PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET absentDays=? WHERE salaryId=?");
                st4.setInt(1,currentCount+crossedLeaves);
                st4.setString(2, salaryId);
                st4.executeUpdate();
              //  System.out.println("All Leaves Days = "+currentCount+crossedLeaves + "  emp - "+empId);

            }
            for (SalaryBean sal : salaryList) {
                empId = sal.getEmpId();
                salaryId=sal.getSalarId();

                ResultSet setAbsences = statement.executeQuery("SELECT officeDays,absentDays FROM `monthlysalarydetails` where salaryId='" + salaryId + "'");
                while (setAbsences.next()) {
                    int workingDays = setAbsences.getInt("officeDays");
                    int totalLeaves = setAbsences.getInt("absentDays");
                    PreparedStatement st3 = con.prepareStatement("UPDATE monthlysalarydetails SET absentDays=? WHERE salaryId=?");
                    st3.setFloat(1, workingDays-totalLeaves);
                    System.out.println("absent days = "+ workingDays +" - "+ totalLeaves);

                    st3.setString(2, salaryId);
                    st3.executeUpdate();
                }
            }for (SalaryBean sal : salaryList) {
                empId = sal.getEmpId();
                salaryId = sal.getSalarId();
                toDate=defaultData.getToDate();
                fromDate=defaultData.getFromDate();
                int crossedLeaves = 0,currentCount=0,attendances=0;

                ResultSet setAbsences = statement.executeQuery("SELECT officeDays,absentDays FROM `monthlysalarydetails` where salaryId='" + salaryId + "'");
                while (setAbsences.next()){
                    currentCount=setAbsences.getInt("absentDays");
                    System.out.println("currentCount=setAbsences.getInt(absentDays); = " + currentCount);
                }
                ResultSet getAttendances = statement.executeQuery("SELECT COUNT(date) as attendances from attendance WHERE empId = "+empId+" and date BETWEEN '"+fromDate+"' AND '"+toDate+"'");
                System.out.println("SELECT COUNT(date) as attendances from attendance WHERE empId = "+empId+" and date BETWEEN '"+fromDate+"' AND '"+toDate+"'");
                while (getAttendances.next()){
                    attendances=getAttendances.getInt("attendances");
                }
                System.out.println("currentCount-attendances = " + currentCount + "-" + attendances);
                int finalAbsentDays=currentCount-attendances;
                System.out.println("(OfficeDays - All Leaves) - Attendances = " + currentCount + " - " +attendances+ "  emp - "+empId);

                PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET absentDays=? WHERE salaryId=?");
                st4.setInt(1,finalAbsentDays);
                st4.setString(2, salaryId);
                st4.executeUpdate();

            }

            ResultSet setTotal = statement.executeQuery("SELECT empId,sum(otHr) as tot FROM `attendance` WHERE date BETWEEN '"+fromDate+"' and '"+toDate+"' GROUP by empId");
            while (setTotal.next()){
                SalaryBean s = new SalaryBean();
                empId = setTotal.getString("empId");
                float otHours = setTotal.getFloat("tot");

                s.setEmpId(empId);
                s.setTotalOtHours(otHours);

                salaryListFinal.add(s);
            }
            for (SalaryBean salary : salaryListFinal) {
                empId=salary.getEmpId();
                float otHours=salary.getTotalOtHours();
                salaryId=yStart+"/"+mStart +"/"+empId;
                System.out.println("??????????");
                PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET totalOThr=? WHERE salaryId=?");
                st4.setFloat(1,otHours);
                st4.setString(2, salaryId);
                st4.executeUpdate();
            }
            ResultSet getAllEmpIds = statement.executeQuery("SELECT empId FROM `monthlysalarydetails`");
            while (getAllEmpIds.next()){
                SalaryBean user = new SalaryBean();
                empId = getAllEmpIds.getString("empId");
                user.setEmpId(empId);
                allUsers.add(user);
            }

            for (SalaryBean user : allUsers) {
                float totalSalary;
                int absentDays;
                empId=user.getEmpId();
                float otHours;
                salaryId=yStart+"/"+mStart +"/"+empId;

                System.out.println("SELECT * FROM `monthlysalarydetails` WHERE salaryId = '"+salaryId+"'");
                ResultSet setFullSalary = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE salaryId = '"+salaryId+"'");
                while (setFullSalary.next()) {

                    basicSalary = setFullSalary.getFloat("basicSalary");
                    dailySalary = setFullSalary.getFloat("dailyPayment");
                    noPayleaves = setFullSalary.getInt("NoPayLeaves");
                    otRate = setFullSalary.getInt("otRate");
                    absentDays = setFullSalary.getInt("absentDays");
                    System.out.println("absentDays = "+absentDays);
                    otHours = setFullSalary.getInt("totalOThr");

                    if ((noPayleaves == absentDays) || absentDays == 0) {
                        totalSalary = (basicSalary + (otHours * otRate) - dailySalary * (noPayleaves + absentDays));
                        PreparedStatement st3= con.prepareStatement("UPDATE notification SET salaryFlag=? WHERE receiverId=?");
                        st3.setInt(1, 1);
                        st3.setString(2, empId);
                        st3.executeUpdate();
                    } else {
                        totalSalary = -404;
                        erros = erros + 1;
                    }

                    PreparedStatement st4 = con.prepareStatement("UPDATE monthlysalarydetails SET totalSalary=? WHERE salaryId=?");
                    st4.setFloat(1, totalSalary);
                    st4.setString(2, salaryId);
                    st4.executeUpdate();



                }
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "unsucessful";
        }
        System.out.println("totalSalary errors = " +erros);
        if (erros>0){
            return "errors";
        }else{
            return "noerrors";
        }

    }*/
    public List<SalaryBean> mySalaries(String empId,String from,String to,String totSalary,String totSalaryLess) {
          List<SalaryBean> mySalaries = new ArrayList<SalaryBean>();


        Connection con = null;
        Statement statement = null;
        ResultSet rs1,rs2 = null;
        String salaryId;
        float basic;
        float dailyPayment;
        int noPays;
        int officeDays;
        int absences;
        float otRate;
        String otHours;
        String start;
        String end;
        float totalSalary;

        if (from==null){
            from="0000-00-0";
        }
        if (to==null){
            to="9999-12-28";
        }
        if (totSalary == null){
            totSalary = "0";

        }
        if (totSalaryLess == null || totSalaryLess.equals("999999999")){
            totSalaryLess = "999999999";
        }

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            System.out.println("SELECT * FROM `monthlysalarydetails` WHERE startDate >= '"+from+"' and  endDate <= '"+to+"' and totalSalary between '"+totSalary+"' and '"+totSalaryLess+"' and  empId="+empId+" ORDER BY salaryId ASC");
            rs1 = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE startDate >= '"+from+"' and  endDate <= '"+to+"' and totalSalary between '"+totSalary+"' and '"+totSalaryLess+"' and  empId="+empId+" ORDER BY salaryId ASC");
            while (rs1.next()) {
                SalaryBean salary = new SalaryBean();

                salaryId = rs1.getString("salaryId");
                basic = rs1.getFloat("basicSalary");
                dailyPayment = rs1.getFloat("dailyPayment");
                noPays = rs1.getInt("NoPayLeaves");
                officeDays = rs1.getInt("officeDays");
                absences = rs1.getInt("absentDays");
                otRate = rs1.getFloat("otRate");
                start = rs1.getString("startDate");
                end = rs1.getString("endDate");
                otHours = rs1.getString("totalOThr");
                totalSalary = rs1.getFloat("totalSalary");

                salary.setSalaryId(salaryId);
                salary.setBasic(basic);
                salary.setDailyPayment(dailyPayment);
                salary.setNoPayes(noPays);
                salary.setAbsences(absences);
                salary.setOfficeWorkingDays(officeDays);
                salary.setOtRate(otRate);
                salary.setFromDate(start);
                salary.setToDate(end);
                salary.setOtHours(otHours);
                salary.setTotalSalary(totalSalary);

                mySalaries.add(salary);

            }

            PreparedStatement st3= con.prepareStatement("UPDATE notification SET salaryFlag=? WHERE receiverId=?");
            st3.setInt(1, 0);
            st3.setString(2, empId);
            st3.executeUpdate();
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

          return mySalaries;
    }
    public List<SalaryBean> staffSalaries(String empId,String from,String to,String totSalary,String totSalaryLess) {
        List<SalaryBean> mySalaries = new ArrayList<SalaryBean>();


        Connection con = null;
        Statement statement = null;

        String salaryId;
        float basic;
        float dailyPayment;
        int noPays;
        int officeDays;
        int absences;
        float otRate;
        String otHours;

        String start;
        String end;
        float totalSalary;

        if (from==null){
            from="0000-00-0";
        }
        if (to==null){
            to="9999-12-28";
        }
        if (totSalary == null){
            totSalary = "0";

        }
        if (totSalaryLess == null || totSalaryLess.equals("999999999")){
            totSalaryLess = "999999999";
        }


        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet rs1=null,rs2 = null;
            if (empId == null || empId.isEmpty()){
                rs1 = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE startDate >= '"+from+"' and  endDate <= '"+to+"' and totalSalary between '"+totSalary+"' and '"+totSalaryLess+"'  ORDER BY salaryId ASC");
            }else if(empId.equals("10000")){
                rs1 = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE startDate >= '"+from+"' and  endDate <= '"+to+"' and totalSalary between '"+totSalary+"' and '"+totSalaryLess+"'  ORDER BY salaryId ASC");

            }else{
                rs1 = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE startDate >= '"+from+"' and  endDate <= '"+to+"' and totalSalary between '"+totSalary+"' and '"+totSalaryLess+"' and  empId="+empId+" ORDER BY salaryId ASC");

            }
            while (rs1.next())
            {
                SalaryBean salary = new SalaryBean();
                empId=rs1.getString("empId");
                salaryId= rs1.getString("salaryId");
                basic = rs1.getFloat("basicSalary");
                dailyPayment=rs1.getFloat("dailyPayment");
                noPays=rs1.getInt("NoPayLeaves");
                officeDays=rs1.getInt("officeDays");
                absences=rs1.getInt("absentDays");
                otRate=rs1.getFloat("otRate");
                start=rs1.getString("startDate");
                end=rs1.getString("endDate");
                otHours=rs1.getString("totalOThr");
                totalSalary=rs1.getFloat("totalSalary");

                salary.setEmpId(empId);
                salary.setSalaryId(salaryId);
                salary.setBasic(basic);
                salary.setDailyPayment(dailyPayment);
                salary.setNoPayes(noPays);
                salary.setAbsences(absences);
                salary.setOfficeWorkingDays(officeDays);
                salary.setOtRate(otRate);
                salary.setFromDate(start);
                salary.setToDate(end);
                salary.setOtHours(otHours);
                salary.setTotalSalary(totalSalary);

                mySalaries.add(salary);

            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return mySalaries;
    }
    static int workingDays(Date start, Date end) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String from=formatter.format(start);
        String to=formatter.format(end);;
        int workingDays=0;
        Calendar c2 = Calendar.getInstance();
        if(from.equals(to)){

            c2.setTime(formatter.parse(from));
            int dayOfWeek2 = c2.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek2!=1 && dayOfWeek2 !=7){
                workingDays=workingDays+1;
            }


        }else{

            while(!from.equals(to)){

                Calendar c = Calendar.getInstance();
                c.setTime(start);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                if(dayOfWeek!=1 && dayOfWeek !=7){
                    workingDays=workingDays+1;
                }

                String nextDate = "";

                Calendar today = Calendar.getInstance();
                String salaryDate= formatter.format( start);

                Date next = formatter.parse(salaryDate);
                today.setTime(next);
                today.add(Calendar.DAY_OF_YEAR, 1);
                nextDate = formatter.format(today.getTime());

                start=formatter.parse(nextDate);

                if(nextDate.equals(to)){

                    c2.setTime(formatter.parse(nextDate));
                    int dayOfWeek2 = c2.get(Calendar.DAY_OF_WEEK);

                    if(dayOfWeek2!=1 && dayOfWeek2 !=7){
                        workingDays=workingDays+1;
                    }
                    break;
                }

            }

        }
        System.out.println(workingDays  + "-- workingDays ");
        return  workingDays;


    }
    public List<SalaryBean> getErrors() {

        List<SalaryBean> salaries = new ArrayList<SalaryBean>();

        Connection con = null;
        Statement statement = null;

        String salaryId;
        float basic;
        float dailyPayment;
        int noPays;
        int officeDays;
        int absences;
        float otRate;
        String otHours,empId;

        String start;
        String end;
        float totalSalary;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet rs1=null,rs2 = null;

            rs1 = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE totalSalary ='"+-404+"' ORDER BY salaryId ASC");

            while (rs1.next())
            {
                SalaryBean salary = new SalaryBean();
                empId=rs1.getString("empId");
                salaryId= rs1.getString("salaryId");
                basic = rs1.getFloat("basicSalary");
                dailyPayment=rs1.getFloat("dailyPayment");
                noPays=rs1.getInt("NoPayLeaves");
                officeDays=rs1.getInt("officeDays");
                absences=rs1.getInt("absentDays");
                otRate=rs1.getFloat("otRate");
                start=rs1.getString("startDate");
                end=rs1.getString("endDate");
                otHours=rs1.getString("totalOThr");
                totalSalary=rs1.getFloat("totalSalary");

                salary.setEmpId(empId);
                salary.setSalaryId(salaryId);
                salary.setBasic(basic);
                salary.setDailyPayment(dailyPayment);
                salary.setNoPayes(noPays);
                salary.setAbsences(absences);
                salary.setOfficeWorkingDays(officeDays);
                salary.setOtRate(otRate);
                salary.setFromDate(start);
                salary.setToDate(end);
                salary.setOtHours(otHours);
                salary.setTotalSalary(totalSalary);

                salaries.add(salary);


            }

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return salaries;
    }
    public String resolveErrors(SalaryBean salary) throws ParseException{
        Connection con = null;
        Statement statement = null;

        String result;
        float totalSalary=salary.getTotalSalary();
        String salaryId=salary.getSalarId();
        String empId=salary.getEmpId();

        System.out.println("EmpIUD = " +empId);
        if(totalSalary<0){
            return "UnSuccessful";
        }
        try {
            con = DBconn.getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE monthlysalarydetails SET totalSalary=? WHERE salaryId=?");
            st.setFloat(1, totalSalary);
            st.setString(2, salaryId);
            st.executeUpdate();

            result ="Successful";

            statement = con.createStatement();
            ResultSet check = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE totalSalary ='"+-404+"' ORDER BY salaryId ASC");
            
            if(check.next()){

            }else{
                result ="SuccessfulEnd";
            }
            PreparedStatement st3= con.prepareStatement("UPDATE notification SET salaryFlag=? WHERE receiverId=?");
            st3.setInt(1, 1);
            st3.setString(2, empId);
            st3.executeUpdate();

        } catch (SQLException throwables) {
            result ="UnSuccessful";
            throwables.printStackTrace();
        }

        return result;
    }
    public List<SalaryBean> getBasicDetails() {

        List<SalaryBean> salaries = new ArrayList<SalaryBean>();

        Connection con = null;
        Statement statement = null;

        float basic;
        float otRate;
        String empId,firstName,lastName;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            ResultSet rs1=null,rs2 = null;

            rs1 = statement.executeQuery("SELECT USEr.firstName,user.lastName,user.NIC, employeesalarydetails.* FROM `employeesalarydetails` LEFT JOIN user on user.empId = employeesalarydetails.empId");

            while (rs1.next())
            {
                SalaryBean salary = new SalaryBean();

                empId=rs1.getString("empId");
                firstName= rs1.getString("firstName");
                lastName = rs1.getString("lastName");
                basic=rs1.getFloat("basicSalary");
                otRate=rs1.getFloat("otRate");


                salary.setEmpId(empId);
                salary.setBasic(basic);
                salary.setFName(firstName);
                salary.setLName(lastName);
                salary.setOtRate(otRate);



                salaries.add(salary);


            }

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return salaries;
    }
}
