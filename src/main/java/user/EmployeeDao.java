package user;

import DBconnection.DBconn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao
{
    public  String removeEmployee(UserBean userBean){

        String result,empId;
        empId = userBean.getEmpId();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        if(empId == ""){
            return "Unsuccessful";
        }
        con = DBconn.getConnection();
        try {
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT status from leavedetails where authorizedPerson="+empId+" and status=1");
            if(rs.next()){
                return "usedInLeave";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            con = DBconn.getConnection();
            PreparedStatement st1 = con.prepareStatement("DELETE From leavedetails WHERE empId=?");
            PreparedStatement st2 = con.prepareStatement("DELETE From leavedetails WHERE authorizedPerson=?");
            PreparedStatement st3 = con.prepareStatement("DELETE From complainsuggestion WHERE empId=?");
            PreparedStatement st4 = con.prepareStatement("DELETE From monthlysalarydetails WHERE empId=?");
            PreparedStatement st5 = con.prepareStatement("DELETE From post WHERE empId=?");
            PreparedStatement st6 = con.prepareStatement("DELETE From user WHERE empId=?");

            st1.setString(1,empId );
            st2.setString(1,empId );
            st3.setString(1,empId );
            st4.setString(1,empId );
            st5.setString(1,empId );
            st6.setString(1,empId );

            st1.executeUpdate();
            st2.executeUpdate();
            st3.executeUpdate();
            st4.executeUpdate();
            st5.executeUpdate();
            st6.executeUpdate();

            result = "Successful";
            return result;

        } catch (SQLException e){
            e.printStackTrace();
            result = "Unsuccessful";
            return result;
        }

    }
    public UserBean searchEmployee(UserBean userBean)
    {
        String empId = userBean.getEmpId();
        UserBean employee = new UserBean();

        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String fName,lName,NIC,dob,address,email,password,contact;
        int empAddDB,empDelDB,postAddDB,postDelDB,postViewDB,chatSysDB,applyLeaveDB,decisionLeaveDB,salaryManageDB,customizeDataDB,editPersonalDetailsDB,giveComSugDB,viewComSugDB,viewMyAttendDB,viewAllAttendDB,viewMyLeavesDB,viewAllLeavesDB,viewMySalaryDB,viewAllSalaryDB,genReportDB;
        float basicSal,otRate;
        int totPayedLeaves, remPayedLeaves ,takenPayedLeaves;
        int totNoPayedLeaves, remNoPayedLeaves,takenNoPayLeaves;
        int totMedicalLeaves, remMedicalLeaves ,takenMedicalLeaves;
        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT user.*,employeesalarydetails.*,employeeleavedetails.*,userprivilege.* FROM ((user INNER JOIN employeesalarydetails ON user.empId = employeesalarydetails.empId) INNER JOIN employeeleavedetails ON user.empId = employeeleavedetails.empId)INNER JOIN userprivilege ON user.empId = userprivilege.empId WHERE user.empId = '"+empId+"'");

            if(rs.next())
            {
                fName = rs.getString("firstName");
                lName = rs.getString("lastName");
                NIC = rs.getString("NIC");
                dob = rs.getString("DOB");
                address = rs.getString("address");
                email = rs.getString("email");
                password = rs.getString("password");
                contact = rs.getString("contactNo");

                totPayedLeaves = rs.getInt("totalPayedLeaves");
                remPayedLeaves = rs.getInt("remainingPayedLeaves");
                takenPayedLeaves = rs.getInt("takenPayedLeaves");

                totNoPayedLeaves = rs.getInt("totalNoPayLeaves");
                remNoPayedLeaves = rs.getInt("remainingNoPayLeaves");
                takenNoPayLeaves = rs.getInt("tackenNoPayLeaves");

                totMedicalLeaves = rs.getInt("totalMedicalLeaves");
                remMedicalLeaves = rs.getInt("remainingMedicalLeaves");
                takenMedicalLeaves = rs.getInt("tackenMedicalLeaves");


                basicSal = rs.getFloat("basicSalary");
                otRate = rs.getFloat("otRate");

                empAddDB = rs.getInt("addEmployee");
                empDelDB = rs.getInt("deleteEmployee");
                postAddDB = rs.getInt("addPost");
                postDelDB = rs.getInt("deletePost");
                postViewDB = rs.getInt("viewPost");
                chatSysDB = rs.getInt("chatSystem");
                applyLeaveDB = rs.getInt("applyLeave");
                decisionLeaveDB = rs.getInt("leavesApprovalRejection");
                salaryManageDB = rs.getInt("salaryManagement");
                customizeDataDB = rs.getInt("customizeData");
                editPersonalDetailsDB = rs.getInt("editPersonalDetails");
                giveComSugDB = rs.getInt("giveComplainSuggestion");
                viewComSugDB = rs.getInt("viewComplainSuggestion");
                viewMyAttendDB = rs.getInt("viewMyAttendance");
                viewAllAttendDB = rs.getInt("viewAllAttendance");
                viewMyLeavesDB = rs.getInt("viewMyLeaves");
                viewAllLeavesDB = rs.getInt("viewAllLeaves");
                viewMySalaryDB = rs.getInt("viewMySalary");
                viewAllSalaryDB = rs.getInt("viewAllSalary");
                genReportDB = rs.getInt("generateReport");

                employee.setEmpId(empId);
                employee.setFName(fName);
                employee.setLName(lName);
                employee.setNIC(NIC);
                employee.setDOB(dob);
                employee.setAddress(address);
                employee.setContact(contact);
                employee.setEmail(email);
                employee.setPassword(password);

                employee.settotPayedLeaves(totPayedLeaves);
                employee.setremPayedLeaves(remPayedLeaves);
                employee.settackenPayedLeaves(takenPayedLeaves);

                employee.settotNoPayedLeaves(totNoPayedLeaves);
                employee.setremNoPayedLeaves(remNoPayedLeaves);
                employee.settackenNoPayLeaves(takenNoPayLeaves);

                employee.settotMedicalLeaves(totMedicalLeaves);
                employee.setremMedicalLeaves(remMedicalLeaves);
                employee.settackenMedicalLeaves(takenMedicalLeaves);

                employee.setBasicSal(basicSal);
                employee.setOtRate(otRate);

                employee.setEmpId(empId);
                employee.setEmpAdd(empAddDB);
                employee.setEmpDel(empDelDB);
                employee.setPostAdd(postAddDB);
                employee.setPostDel(postDelDB);
                employee.setPostView(postViewDB);
                employee.setChatSys(chatSysDB);
                employee.setApplyLeave(applyLeaveDB);
                employee.setDecisionLeave(decisionLeaveDB);
                employee.setSalaryManage(salaryManageDB);
                employee.setCustomizeData(customizeDataDB);
                employee.setEditPersonalDetails(editPersonalDetailsDB);
                employee.setGiveComSug(giveComSugDB);
                employee.setViewComSug(viewComSugDB);
                employee.setViewMyAttend(viewMyAttendDB);
                employee.setViewAllAttend(viewAllAttendDB);
                employee.setViewMyLeaves(viewMyLeavesDB);
                employee.setViewAllLeaves(viewAllLeavesDB);
                employee.setViewMySalary(viewMySalaryDB);
                employee.setViewAllSalary(viewAllSalaryDB);
                employee.setGenReport(genReportDB);
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employee;
    }
    public UserBean searchMyDetails(UserBean userBean)
    {
        String empId = userBean.getEmpId();

        UserBean employee = new UserBean();

        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String fName,lName,NIC,dob,address,email,password,contact;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT * from USER WHERE empId = '"+empId+"'");

            if(rs.next())
            {
                fName = rs.getString("firstName");
                lName = rs.getString("lastName");
                NIC = rs.getString("NIC");
                dob = rs.getString("DOB");
                address = rs.getString("address");
                email = rs.getString("email");
                password = rs.getString("password");
                contact = rs.getString("contactNo");


                employee.setEmpId(empId);
                employee.setFName(fName);
                employee.setLName(lName);
                employee.setNIC(NIC);
                employee.setDOB(dob);
                employee.setAddress(address);
                employee.setContact(contact);
                employee.setEmail(email);
                employee.setPassword(password);

            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employee;
    }

    public List<UserBean> searchAllEmployees()
    {
        List<UserBean> empList = new ArrayList<UserBean>();

        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String empId,fName,lName,NIC;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT empId,firstName,lastName,NIC FROM user");
            while(rs.next())
            {
                UserBean employee = new UserBean();
                empId = rs.getString("empId");
                fName = rs.getString("firstName");
                lName = rs.getString("lastName");
                NIC = rs.getString("NIC");

                employee.setEmpId(empId);
                employee.setFName(fName);
                employee.setLName(lName);
                employee.setNIC(NIC);

                empList.add(employee);
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return empList;
    }
    public List<UserBean> searchChatEmployees(Object uId)
    {
        List<UserBean> empList = new ArrayList<UserBean>();

        Connection con = null;
        Statement statement = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;

        String empId,fName,lName;

        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            //rs = statement.executeQuery("SELECT user.empId,user.firstName,user.lastName FROM user INNER JOIN userprivilege ON user.empId = userprivilege.empId WHERE userprivilege.chatSystem = 1");
            rs1 = statement.executeQuery("SELECT user.empId, user.firstName, user.lastName FROM user INNER JOIN chat ON user.empId = chat.receiverId OR user.empId = chat.senderId WHERE (chat.senderId = '"+uId+"' && chat.msgId = (SELECT MAX(chat.msgId) FROM chat WHERE senderId = '"+uId+"' && receiverId = user.empId)) OR (chat.receiverId = '"+uId+"' && chat.msgId = (SELECT MAX(chat.msgId) FROM chat WHERE receiverId = '"+uId+"' && senderId = user.empId)) GROUP BY user.empId ORDER BY MAX(chat.msgId) DESC");

            while(rs1.next())
            {
                UserBean employee = new UserBean();
                empId = rs1.getString("user.empId");
                fName = rs1.getString("user.firstName");
                lName = rs1.getString("user.lastName");

                employee.setEmpId(empId);
                employee.setFName(fName);
                employee.setLName(lName);

                empList.add(employee);
            }

            rs2 = statement.executeQuery("SELECT user.empId, user.firstName, user.lastName FROM user INNER join userprivilege on user.empId = userprivilege.empId WHERE userprivilege.chatSystem = 1 AND user.empId Not IN( SELECT user.empId FROM user INNER JOIN chat ON user.empId = chat.receiverId OR user.empId = chat.senderId WHERE (chat.senderId = '" + uId + "' && chat.msgId = (SELECT MAX(chat.msgId) FROM chat WHERE senderId = '" + uId + "' && receiverId = user.empId)) OR (chat.receiverId = '" + uId + "' && chat.msgId = (SELECT MAX(chat.msgId) FROM chat WHERE receiverId = '" + uId + "' && senderId = user.empId)) GROUP BY user.empId ORDER BY MAX(chat.msgId) DESC) ORDER BY `user`.`empId` ASC");
            //rs2 = statement.executeQuery("SELECT user.empId, user.firstName, user.lastName FROM user INNER join userprivilege on user.empId = userprivilege.empId WHERE userprivilege.chatSystem = 1 AND (user.empId NOT IN (SELECT chat.receiverId FROM chat) AND user.empId NOT IN (SELECT chat.senderId  FROM chat))");

            while(rs2.next())
            {
                UserBean employee = new UserBean();
                empId = rs2.getString("user.empId");
                fName = rs2.getString("user.firstName");
                lName = rs2.getString("user.lastName");

                employee.setEmpId(empId);
                employee.setFName(fName);
                employee.setLName(lName);

                empList.add(employee);
            }

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return empList;
    }
    public List<UserBean> searchChatEmployeesdef(Object uId) {
        List<UserBean> empList = new ArrayList<UserBean>();

        Connection con = null;
        Statement statement = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        ResultSet rs4 = null;

        String empId, fName, lName;

        try {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs4 = statement.executeQuery("SELECT user.empId, user.firstName, user.lastName FROM user INNER join userprivilege on user.empId = userprivilege.empId WHERE userprivilege.chatSystem = 1 ");
            while (rs4.next()) {
                UserBean employee = new UserBean();
                empId = rs4.getString("user.empId");
                fName = rs4.getString("user.firstName");
                lName = rs4.getString("user.lastName");
                employee.setEmpId(empId);
                employee.setFName(fName);
                employee.setLName(lName);

                empList.add(employee);
            }


        }catch(SQLException e)
                {
                    e.printStackTrace();
                }
                return empList;


    }
    public String addEmployee(UserBean newEmp) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String fName, dob, lName, NIC, address, email, password, confirmPassword, contact;
        String res = null;
        int  takenLeaves, empAddDB, empDelDB, postAddDB, postDelDB, postViewDB, chatSysDB, applyLeaveDB, decisionLeaveDB, salaryManageDB, customizeDataDB, editPersonalDetailsDB, giveComSugDB, viewComSugDB, viewMyAttendDB, viewAllAttendDB, viewMyLeavesDB, viewAllLeavesDB, viewMySalaryDB, viewAllSalaryDB, genReportDB;
        float basicSal, otRate;
        int totPayedLeaves, remPayedLeaves,tackenPayedLeaves;
        int totNoPayedLeaves, remNoPayedLeaves,tackenNoPayLeaves;
        int totMedicalLeaves, remMedicalLeaves ,tackenMedicalLeaves;

        int empId = Integer.parseInt(newEmp.getEmpId());
        int fingerPrint = Integer.parseInt(newEmp.getEmpId());
        fingerPrint= fingerPrint-10000;
        fName = newEmp.getFName();
        lName = newEmp.getLName();
        NIC = newEmp.getNIC();
        dob = newEmp.getDOB();
        address = newEmp.getAddress();
        contact = newEmp.getContact();
        email = newEmp.getEmail();
        password = newEmp.getPassword();
        confirmPassword = newEmp.getConfirmPassword();

        totPayedLeaves = newEmp.gettotPayedLeaves();
        remPayedLeaves = newEmp.getremPayedLeaves();

        totNoPayedLeaves = newEmp.gettotNoPayedLeaves();
        remNoPayedLeaves = newEmp.getremNoPayedLeaves();

        totMedicalLeaves = newEmp.gettotMedicalLeaves();
        remMedicalLeaves = newEmp.getremMedicalLeaves();

        tackenPayedLeaves=tackenNoPayLeaves=tackenMedicalLeaves=0;

        basicSal = newEmp.getBasicSal();
        otRate = newEmp.getOtRate();

        empAddDB = newEmp.getEmpAdd();
        empDelDB = newEmp.getEmpDel();
        postAddDB = newEmp.getPostAdd();
        postDelDB = newEmp.getPostDel();
        postViewDB = newEmp.getPostView();
        chatSysDB = newEmp.getChatSys();
        applyLeaveDB = newEmp.getApplyLeave();
        decisionLeaveDB = newEmp.getDecisionLeave();
        salaryManageDB = newEmp.getSalaryManage();
        customizeDataDB = newEmp.getCustomizeData();
        editPersonalDetailsDB = newEmp.getEditPersonalDetails();
        giveComSugDB = newEmp.getGiveComSug();
        viewComSugDB = newEmp.getViewComSug();
        viewMyAttendDB = newEmp.getViewMyAttend();
        viewAllAttendDB = newEmp.getViewAllAttend();
        viewMyLeavesDB = newEmp.getViewMyLeaves();
        viewAllLeavesDB = newEmp.getViewAllLeaves();
        viewMySalaryDB = newEmp.getViewMySalary();
        viewAllSalaryDB = newEmp.getViewAllSalary();
        genReportDB = newEmp.getGenReport();
        if (! password .equals(confirmPassword)) {
            res = "ErrPass";

        } else if (password.length() < 8) {

            res = "ErrLength";


        } else {
            try {
                con = DBconn.getConnection();
                PreparedStatement st1 = con.prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?,?,?,?,?)");

                st1.setInt(1, empId);
                st1.setString(2, fName);
                st1.setString(3, lName);
                st1.setString(4, NIC);
                st1.setString(5, dob);
                st1.setString(6, address);
                st1.setString(7, contact);
                st1.setString(8, email);
                st1.setString(9, password);
                st1.setInt(10,fingerPrint );

                st1.executeUpdate();

                PreparedStatement st2 = con.prepareStatement("INSERT INTO employeeleavedetails VALUES(?,?,?,?,?,?,?,?,?,?)");

                st2.setInt(1, empId);

                st2.setInt(2, totPayedLeaves);
                st2.setInt(3, remPayedLeaves);
                st2.setInt(4, tackenPayedLeaves);

                st2.setInt(5, totNoPayedLeaves);
                st2.setInt(6, remNoPayedLeaves);
                st2.setInt(7, tackenNoPayLeaves);

                st2.setInt(8, totMedicalLeaves);
                st2.setInt(9, remMedicalLeaves);
                st2.setInt(10,tackenMedicalLeaves);

                st2.executeUpdate();

                PreparedStatement st3 = con.prepareStatement("INSERT INTO employeesalarydetails VALUES(?,?,?)");

                st3.setInt(1, empId);
                st3.setFloat(2, basicSal);
                st3.setFloat(3, otRate);

                st3.executeUpdate();

                PreparedStatement st4 = con.prepareStatement("INSERT INTO userprivilege VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                st4.setInt(1, empId);
                st4.setInt(2, empAddDB);
                st4.setInt(3, empDelDB);
                st4.setInt(4, postAddDB);
                st4.setInt(5, postDelDB);
                st4.setInt(6, postViewDB);
                st4.setInt(7, chatSysDB);
                st4.setInt(8, applyLeaveDB);
                st4.setInt(9, decisionLeaveDB);
                st4.setInt(10, salaryManageDB);
                st4.setInt(11, customizeDataDB);
                st4.setInt(12, editPersonalDetailsDB);
                st4.setInt(13, giveComSugDB);
                st4.setInt(14, viewComSugDB);
                st4.setInt(15, viewMyAttendDB);
                st4.setInt(16, viewAllAttendDB);
                st4.setInt(17, viewMyLeavesDB);
                st4.setInt(18, viewAllLeavesDB);
                st4.setInt(19, viewMySalaryDB);
                st4.setInt(20, viewAllSalaryDB);
                st4.setInt(21, genReportDB);

                st4.executeUpdate();

                PreparedStatement st5 = con.prepareStatement("INSERT  into notification VALUES (?,?,?,?,?,?) ");

                st5.setInt(1, empId);
                st5.setInt(2, 0);
                st5.setInt(3, 0);
                st5.setInt(4, 0);
                st5.setInt(5, 0);
                st5.setInt(6, 0);

                st5.executeUpdate();

                st1.close();
                st2.close();
                st3.close();
                st4.close();
                st5.close();

                con.close();

                res = "Successful";
            } catch (SQLException e) {
                e.printStackTrace();
                res = "Unsuccessful";
            }
        }
        return res;
    }
    public String updateMyDetails(UserBean Emp)
    {

        String fName,dob,lName,NIC,address,email,contact, res;

        int empId = Integer.parseInt(Emp.getEmpId());
        fName = Emp.getFName();
        lName = Emp.getLName();
        NIC = Emp.getNIC();
        dob = Emp.getDOB();
        address = Emp.getAddress();
        contact = Emp.getContact();
        email = Emp.getEmail();

        String id=Emp.getEmpId();

        System.out.println(id);


        try
        {

            Connection con = null;
            con = DBconn.getConnection();
            PreparedStatement st1 = con.prepareStatement("UPDATE user SET firstName=?,lastName=?,NIC=?,DOB=?,address=?,contactNo=?,email=? WHERE empId=?");

            st1.setString(1,fName );
            st1.setString(2,lName);
            st1.setString(3,NIC);
            st1.setString(4,dob);
            st1.setString(5,address);
            st1.setString(6,contact);
            st1.setString(7, email);
            st1.setInt(8, empId);
            st1.executeUpdate();
            st1.close();
           // PreparedStatement st1 = con.prepareStatement("UPDATE user SET firstName=?,lastName=?,NIC=?,DOB=?,address=?,contactNo=?,email=? WHERE empId=?");

            /*st1.setString(1,fName);
            st1.setString(2,lName);
            st1.setString(3,NIC);
            st1.setString(4,dob);
            st1.setString(5,address);
            st1.setString(6,contact);
            st1.setString(7, email);

            st1.setInt(8, empId);

            st1.executeUpdate();
            st1.close();*/
            con.close();
            res = "Successful";
            return res;
        }
        catch (SQLException e) {
            e.printStackTrace();
            res = "Unsuccessful";
            return res;
        }

    }
    public String updatePass(UserBean Emp)
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String password,confirmPassword, res,currentPassword;

        int empId = Integer.parseInt(Emp.getEmpId());

        currentPassword=Emp.getCurrentpassword();
        //System.out.println("Cur_Pass="+currentPassword);
        password = Emp.getPassword();
        //System.out.println("New_Pass="+password);
        confirmPassword = Emp.getConfirmPassword();
        //System.out.println("Con_Pass="+confirmPassword);

        String id=Emp.getEmpId();
        //System.out.println("ID="+id);

        if (! password .equals(confirmPassword)) {
            res = "ErrPass";
            return res;
        } else if (password.length() < 8) {
            res = "ErrLength";
            return res;

        }
        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT password FROM user WHERE empId = '"+id+"'");
            //System.out.println("SELECT password FROM user WHERE empId = '"+id+"'");
            if(rs.next()){
                String pass=rs.getString("password");
                //System.out.println("Pass="+pass);
                if (!pass.equals(currentPassword)){
                    //System.out.println(pass   +"----------- "+ currentPassword);
                    return "ErrCurrent";
                }
            }
            PreparedStatement st1 = con.prepareStatement("UPDATE user SET password=? WHERE empId=?");

            st1.setString(1,password);
            st1.setInt(2, empId);

            st1.executeUpdate();
            st1.close();
            con.close();
            res = "Successful";
            return res;
        }
        catch (SQLException e) {
            e.printStackTrace();
            res = "Unsuccessful";
            return res;
        }

    }
    public String updateEmployee(UserBean Emp)
    {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        String fName,dob,lName,NIC,address,email,password,confirmPassword,contact, res;
        int empAddDB,empDelDB,postAddDB,postDelDB,postViewDB,chatSysDB,applyLeaveDB,decisionLeaveDB,salaryManageDB,customizeDataDB,editPersonalDetailsDB,giveComSugDB,viewComSugDB,viewMyAttendDB,viewAllAttendDB,viewMyLeavesDB,viewAllLeavesDB,viewMySalaryDB,viewAllSalaryDB,genReportDB;
        float basicSal,otRate;
        int totPayedLeaves, remPayedLeaves ,tackenPayedLeaves;
        int totNoPayedLeaves, remNoPayedLeaves,tackenNoPayLeaves;
        int totMedicalLeaves, remMedicalLeaves ,tackenMedicalLeaves;
        int empId = Integer.parseInt(Emp.getEmpId());

        fName = Emp.getFName();
        lName = Emp.getLName();
        NIC = Emp.getNIC();
        dob = Emp.getDOB();
        address = Emp.getAddress();
        contact = Emp.getContact();
        email = Emp.getEmail();
        try
        {
            con = DBconn.getConnection();
            statement = con.createStatement();
            rs = statement.executeQuery("SELECT * from employeeleavedetails WHERE empId = '"+empId+"'");

            if(rs.next())
            {
                tackenPayedLeaves = rs.getInt("takenPayedLeaves");
                tackenNoPayLeaves = rs.getInt("tackenNoPayLeaves");
                tackenMedicalLeaves = rs.getInt("tackenMedicalLeaves");

                Emp.settackenPayedLeaves(tackenPayedLeaves);
                Emp.settackenNoPayLeaves(tackenNoPayLeaves);
                Emp.settackenMedicalLeaves(tackenMedicalLeaves);


            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        password = Emp.getPassword();
        confirmPassword = Emp.getConfirmPassword();

        totPayedLeaves = Emp.gettotPayedLeaves();


        totNoPayedLeaves = Emp.gettotNoPayedLeaves();


        totMedicalLeaves = Emp.gettotMedicalLeaves();

        remPayedLeaves = totPayedLeaves - Emp.gettackenPayedLeaves();
        remNoPayedLeaves=totNoPayedLeaves-Emp.gettackenNoPayLeaves();
        remMedicalLeaves=totMedicalLeaves-Emp.gettackenMedicalLeaves();

        if(remPayedLeaves<0)
        {
            remPayedLeaves=0;
        }
        if(remNoPayedLeaves<0)
        {
            remNoPayedLeaves=0;
        }if(remMedicalLeaves<0)
    {
        remMedicalLeaves=0;
    }
        basicSal = Emp.getBasicSal();
        otRate = Emp.getOtRate();

        empAddDB = Emp.getEmpAdd();
        empDelDB = Emp.getEmpDel();
        postAddDB = Emp.getPostAdd();
        postDelDB = Emp.getPostDel();
        postViewDB = Emp.getPostView();
        chatSysDB = Emp.getChatSys();
        applyLeaveDB = Emp.getApplyLeave();
        decisionLeaveDB = Emp.getDecisionLeave();
        salaryManageDB = Emp.getSalaryManage();
        customizeDataDB = Emp.getCustomizeData();
        editPersonalDetailsDB = Emp.getEditPersonalDetails();
        giveComSugDB = Emp.getGiveComSug();
        viewComSugDB = Emp.getViewComSug();
        viewMyAttendDB = Emp.getViewMyAttend();
        viewAllAttendDB = Emp.getViewAllAttend();
        viewMyLeavesDB = Emp.getViewMyLeaves();
        viewAllLeavesDB = Emp.getViewAllLeaves();
        viewMySalaryDB = Emp.getViewMySalary();
        viewAllSalaryDB = Emp.getViewAllSalary();
        genReportDB = Emp.getGenReport();
        String emp = Emp.getEmpId();


        if (! password .equals(confirmPassword)) {
            res = "ErrPass";
            return res;
        } else if (password.length() < 8) {
            res = "ErrLength";
            return res;

        } else {
            try
            {
                con = DBconn.getConnection();
                PreparedStatement st1 = con.prepareStatement("UPDATE user SET firstName=?,lastName=?,NIC=?,DOB=?,address=?,contactNo=?,email=?,password=? WHERE empId=?");

                st1.setString(1,fName );
                st1.setString(2,lName);
                st1.setString(3,NIC);
                st1.setString(4,dob);
                st1.setString(5,address);
                st1.setString(6,contact);
                st1.setString(7, email);
                st1.setString(8,password);
                st1.setInt(9, empId);

                st1.executeUpdate();
                PreparedStatement st2 = con.prepareStatement("UPDATE employeeleavedetails SET totalPayedLeaves=?,remainingPayedLeaves=? ,totalNoPayLeaves=?,remainingNoPayLeaves=?, totalMedicalLeaves=?,remainingMedicalLeaves=? WHERE empId=?");


                st2.setInt(1, totPayedLeaves);
                st2.setInt(2, remPayedLeaves);
                st2.setInt(3, totNoPayedLeaves);
                st2.setInt(4, remNoPayedLeaves);
                st2.setInt(5, totMedicalLeaves);
                st2.setInt(6, remMedicalLeaves);
                st2.setInt(7, empId);

                st2.executeUpdate();

                PreparedStatement st3 = con.prepareStatement("UPDATE employeesalarydetails SET basicSalary=?,otRate=? WHERE empId=?");

                st3.setFloat(1, basicSal);
                st3.setFloat(2, otRate);
                st3.setInt(3, empId);

                st3.executeUpdate();

                PreparedStatement st4 = con.prepareStatement("UPDATE userprivilege SET addEmployee=?, deleteEmployee=?, addPost=?, deletePost=?, viewPost=?, chatSystem=?, applyLeave=?, leavesApprovalRejection=?, salaryManagement=?, customizeData=?, editPersonalDetails=?, giveComplainSuggestion=?, viewComplainSuggestion=?, viewMyAttendance=?, viewAllAttendance=?, viewMyLeaves=?, viewAllLeaves=?, viewMySalary=?, viewAllSalary=?, generateReport=? WHERE empId=?");

                st4.setInt(1, empAddDB);
                st4.setInt(2, empDelDB);
                st4.setInt(3, postAddDB);
                st4.setInt(4, postDelDB);
                st4.setInt(5, postViewDB);
                st4.setInt(6, chatSysDB);
                st4.setInt(7, applyLeaveDB);
                st4.setInt(8, decisionLeaveDB);
                st4.setInt(9, salaryManageDB);
                st4.setInt(10, customizeDataDB);
                st4.setInt(11, editPersonalDetailsDB);
                st4.setInt(12, giveComSugDB);
                st4.setInt(13, viewComSugDB);
                st4.setInt(14, viewMyAttendDB);
                st4.setInt(15, viewAllAttendDB);
                st4.setInt(16, viewMyLeavesDB);
                st4.setInt(17, viewAllLeavesDB);
                st4.setInt(18, viewMySalaryDB);
                st4.setInt(19, viewAllSalaryDB);
                st4.setInt(20, genReportDB);
                st4.setInt(21, empId);

                st4.executeUpdate();

                st1.close();
                st2.close();
                st3.close();
                st4.close();
                con.close();

                res = "Successful";
                return res;
            }
            catch (SQLException e) {
                e.printStackTrace();
                res = "Unsuccessful";
                return res;
            }

        }
    }

}

