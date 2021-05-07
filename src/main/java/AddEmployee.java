import user.EmployeeDao;
import user.UserBean;
import hash.hashBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEmployee extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String empId, fName, lName, NIC, dob, address, email, password, contact, confirmPassword;
        int totPayedLeaves, remPayedLeaves;
        int totNoPayedLeaves, remNoPayedLeaves;
        int totMedicalLeaves, remMedicalLeaves;
        int  empAddDB, empDelDB, postAddDB, postDelDB, postViewDB, chatSysDB, applyLeaveDB, decisionLeaveDB, salaryManageDB, customizeDataDB, editPersonalDetailsDB, giveComSugDB, viewComSugDB, viewMyAttendDB, viewAllAttendDB, viewMyLeavesDB, viewAllLeavesDB, viewMySalaryDB, viewAllSalaryDB, genReportDB;;
        float basicSal, otRate;
        String res;

        empId = request.getParameter("empid");
        fName = request.getParameter("first_name");
        lName = request.getParameter("last_name");
        NIC = request.getParameter("nic");
        dob = request.getParameter("dob");
        address = request.getParameter("address");
        contact = request.getParameter("phone");
        email = request.getParameter("email");

        String originalPass = request.getParameter("password");
        hashFunction h =new hashFunction();

        password = h.toHash(originalPass,empId);

        String originalconfirmPassword = request.getParameter("confirm_password");
        confirmPassword = h.toHash(originalconfirmPassword,empId);



        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
        String today= formatter.format(date);

        Date Birth = null;
        Date currentDate = null;
        try {
            Birth = formatter.parse(dob);
            currentDate = formatter.parse(today);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long isValidBDay = Birth.getTime() - currentDate.getTime();

        if (fName.isEmpty() || lName.isEmpty() || NIC.isEmpty() || dob.isEmpty()|| address.isEmpty()||
                confirmPassword.isEmpty()||email.isEmpty()||password.isEmpty() || confirmPassword.isEmpty() ||
                Integer.valueOf(request.getParameter("leavesPayed")).equals(null)||
                Integer.valueOf(request.getParameter("leavesNoPay")).equals(null)||
                Integer.valueOf(request.getParameter("leavesMedical")).equals(null)||
                Float.valueOf(request.getParameter("bSalary")).equals(null) ||
                Float.valueOf(request.getParameter("otRate")).equals(null) || isValidBDay>0 ){

            String  result="Unsuccessful";
            request.setAttribute("result",result);
            request.getRequestDispatcher("/addEmployee.jsp").forward(request, response);

        }else {

            totPayedLeaves = Integer.valueOf(request.getParameter("leavesPayed"));
            remPayedLeaves = Integer.valueOf(request.getParameter("leavesPayed"));

             totNoPayedLeaves = Integer.valueOf(request.getParameter("leavesNoPay"));
            remNoPayedLeaves = Integer.valueOf(request.getParameter("leavesNoPay"));

             totMedicalLeaves = Integer.valueOf(request.getParameter("leavesMedical"));
            remMedicalLeaves = Integer.valueOf(request.getParameter("leavesMedical"));


            basicSal = Float.valueOf(request.getParameter("bSalary"));
            otRate = Float.valueOf(request.getParameter("otRate"));


            empAddDB = Integer.valueOf(request.getParameter("employeeAdd"));
            empDelDB = Integer.valueOf(request.getParameter("employeeRemove"));

            postAddDB = Integer.valueOf(request.getParameter("postAdd"));
            postDelDB = Integer.valueOf(request.getParameter("postDelete"));
            postViewDB = Integer.valueOf(request.getParameter("postView"));
            chatSysDB = Integer.valueOf(request.getParameter("chatSystemUse"));


            if (postViewDB == 0) {
                postAddDB = 0;
                postViewDB = 0;
            }


            applyLeaveDB = Integer.valueOf(request.getParameter("leaveApply"));
            viewMyLeavesDB = Integer.valueOf(request.getParameter("myLeaveHistoryView"));

            if (applyLeaveDB == 1) {
                viewMyLeavesDB = 1;
            } else if (applyLeaveDB == 0) {
                viewMyLeavesDB = 0;
            }

            decisionLeaveDB = Integer.valueOf(request.getParameter("ApproveOrRejectLeave"));
            salaryManageDB = Integer.valueOf(request.getParameter("salaryManagement"));
            customizeDataDB = Integer.valueOf(request.getParameter("dataCustomize"));
            editPersonalDetailsDB = Integer.valueOf(request.getParameter("personalDetailsEdit"));
            giveComSugDB = Integer.valueOf(request.getParameter("complain_suggestionGive"));
            viewComSugDB = Integer.valueOf(request.getParameter("complain_suggestionView"));
            viewMyAttendDB = Integer.valueOf(request.getParameter("myAttendanceHistoryView"));
            viewAllAttendDB = Integer.valueOf(request.getParameter("othersAttendanceHistoryView"));
            viewAllLeavesDB = Integer.valueOf(request.getParameter("otherLeaveHistoryView"));
            viewMySalaryDB = Integer.valueOf(request.getParameter("mySalary"));
            viewAllSalaryDB = Integer.valueOf(request.getParameter("otherSalary"));
            genReportDB = Integer.valueOf(request.getParameter("generationReport"));

            Integer allPrivileges = Integer.valueOf(request.getParameter("allPrivileges"));

            if(allPrivileges == 1){
                empAddDB=empDelDB=1;
                postAddDB=postDelDB=postViewDB=chatSysDB=1;
                applyLeaveDB=1;
                decisionLeaveDB=salaryManageDB=customizeDataDB=editPersonalDetailsDB=1;
                giveComSugDB=viewComSugDB=1;
                viewAllAttendDB=viewAllLeavesDB=viewAllSalaryDB=1;
                viewMyAttendDB=viewMyLeavesDB=viewMySalaryDB=genReportDB=1;
            }
            UserBean newEmp = new UserBean();

            newEmp.setEmpId(empId);
            newEmp.setFName(fName);
            newEmp.setLName(lName);
            newEmp.setNIC(NIC);
            newEmp.setDOB(dob);
            newEmp.setAddress(address);
            newEmp.setContact(contact);
            newEmp.setEmail(email);
            newEmp.setPassword(password);
            newEmp.setConfirmPassword(confirmPassword);

            newEmp.settotPayedLeaves(totPayedLeaves);
            newEmp.setremPayedLeaves(remPayedLeaves);

            newEmp.settotNoPayedLeaves(totNoPayedLeaves);
            newEmp.setremNoPayedLeaves(remNoPayedLeaves);

            newEmp.settotMedicalLeaves(totMedicalLeaves);
            newEmp.setremMedicalLeaves(remMedicalLeaves);

            newEmp.setBasicSal(basicSal);
            newEmp.setOtRate(otRate);

            newEmp.setEmpAdd(empAddDB);
            newEmp.setEmpDel(empDelDB);
            newEmp.setPostAdd(postAddDB);
            newEmp.setPostDel(postDelDB);
            newEmp.setPostView(postViewDB);
            newEmp.setChatSys(chatSysDB);
            newEmp.setApplyLeave(applyLeaveDB);
            newEmp.setDecisionLeave(decisionLeaveDB);
            newEmp.setSalaryManage(salaryManageDB);
            newEmp.setCustomizeData(customizeDataDB);
            newEmp.setEditPersonalDetails(editPersonalDetailsDB);
            newEmp.setGiveComSug(giveComSugDB);
            newEmp.setViewComSug(viewComSugDB);
            newEmp.setViewMyAttend(viewMyAttendDB);
            newEmp.setViewAllAttend(viewAllAttendDB);
            newEmp.setViewMyLeaves(viewMyLeavesDB);
            newEmp.setViewAllLeaves(viewAllLeavesDB);
            newEmp.setViewMySalary(viewMySalaryDB);
            newEmp.setViewAllSalary(viewAllSalaryDB);
            newEmp.setGenReport(genReportDB);

            EmployeeDao regEmp = new EmployeeDao();

            res = regEmp.addEmployee(newEmp);
            request.setAttribute("result", res);

            if (res.equals("ErrPass") || res.equals("ErrLength") || res.equals("Unsuccessful")) {
                request.getRequestDispatcher("/addEmployee.jsp").forward(request, response);
            } else if (res.equals("Successful")) {
                request.getRequestDispatcher("/addEmployeeFingerPrint.jsp").forward(request, response);

            }
        }
    }

    String toHash(String password, String empId){
        String hash = null;
        int id = Integer.parseInt(empId);
        int length = password.length();

        char[] passs = new char[length];
        int[] asc = new int[length];
        int sum =0;
        for (int i = 0; i < length; i++) {
            passs[i] = password.charAt(i);
            asc[i] = (int) passs[i];

            if (asc[i]< 47){
                asc[i] =99;
            }
            else if(asc[i] <=57){
                asc[i]= asc[i]-48;
            }

            else if(asc[i] <=91){
                asc[i]= asc[i]-64;
            }

            else if(asc[i] <=122){
                asc[i]= asc[i]-96;
            }else{
                asc[i] =99;
            }

            sum =sum + asc[i];

        }
        int r=id % 1000;
        int val = (id * r * sum * id * sum);
        if(val < 0){
            val = val *(-1);
        }
        hash = String.valueOf(val);

        return hash;
    }

    }

