<%--
  Created by IntelliJ IDEA.
  User: Deshan-UCSC
  Date: 10/18/2020
  Time: 6:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DBconnection.DBconn" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.List" %>

<%@ page import="salary.SalaryBean" %>
<%@ page import="salary.SalaryDao" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/errorManage.css">
</head>
<body>
<div class="content">
    <div class="heading">
        <h3>Resolve Conflicts</h3>
    </div>
    <form action="manageErrors" method="POST">
        <%
            HttpSession sss = request.getSession(false);
            //Check Session Expired
            if (sss == null || sss.isNew()) {
                request.setAttribute("session", "Expired");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
            //Check Logout
            if(session.getAttribute("sessionSt").equals("Logout"))
            {
                //session.setAttribute("sessionSt", "Logout");
                request.setAttribute("session","Logout");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

            String result= (String) request.getAttribute("result");
            if(result != null){

                if(result=="Successful"){%>
        <h4 class="response" style="color: #4bbe19;">
           Salary is Updated Successful!
        </h4><%

        }request.setAttribute("result",null);
    %><%
        if(result == "Unsuccessful"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Update the Salary!Try Again
        </h4>
        <%} request.setAttribute("result",null);}
            request.setAttribute("result",null);
        %>
            <%
        SalaryDao error = new SalaryDao();
        List<SalaryBean> salaries = error.getErrors();
    %>
    <br>
        <div class="salaries">
            <table>
                <tr>
                    <td>
                        <label class="label">Office Days</label>
                    </td>
                    <td>
                        <label class="label">NoPayed Leaves</label>
                    </td>
                    <td>
                        <label class="label">Absent Days</label>
                    </td>
                </tr>
                <tr>
                    <th>
                        <input class="input" type="text" name="officeDays" id="officeDays" readonly>
                    </th>

                    <th>
                        <input class="input" type="text" name="noPays" id="noPays" readonly>
                    </th>

                    <th>
                        <input class="input" type="text" name="absences" id="absences" readonly>
                    </th>

                </tr>


            </table>
<br>
        </div>
    <div class="main">
        <table class="top">

            <tr>
                <td>
                    <label class="label">Salary Id</label>
                </td>
                <th>
                    <input class="input" type="text" name="salaryId" id="salaryId" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Emp Id</label>
                </td>
                <th>
                    <input class="input" type="text" name="empId" id="empId" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Basic Salary</label>
                </td>
                <th>
                    <input class="input" type="text" name="basicSalary" id="basicSalary" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Daily Payment</label>
                </td>
                <th>
                    <input class="input" type="text" name="dailySalary" id="dailySalary" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">OT Hours</label>
                </td>
                <th>
                    <input class="input" type="text" name="otHours" id="otHours" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">OT Rate</label>
                </td>
                <th>
                    <input class="input" type="text" name="otRate" id="otRate" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Total Salary</label>
                </td>
                <th>
                    <input class="input" type="text" name="total" id="total">
                </th>
            </tr>
        </table>
        <table class="apprej">
            <tr>
                <th class="thBtn">
                    <input class="apply" type="submit" value="Send Salary"/>
                </th>
            </tr>
        </table>
    </div>

    </form>
    <div class="result">
        <br>
        <table id="tresult">

            <tr>
                <th>
                    Salary Id
                </th>
                <th>
                    Employee
                </th>
                <th>
                    Office Days
                </th>
                <th>
                    NoPays
                </th>
                <th>
                    Absences
                </th>
                <th>
                    Basic
                </th>
                <th>
                    Daily
                </th>
                <th>
                    OT Hours
                </th>
                <th>
                    OT Rate
                </th>
                <th>
                    Total
                </th>
            </tr>
            <%
                for(SalaryBean salary:salaries){
                    if (session.getAttribute("empId").equals(salary.getEmpId())){}
                    else{
            %>
            <tr>
                <td class="empIdd"><%=salary.getSalarId()%></td>
                <td class="empName"><%=salary.getEmpId()%></td>
                <td class="office"><%=salary.getOfficeWorkingDays()%></td>
                <td class="nopay"><%=salary.getNoPayes()%></td>
                <td class="absence"><%=salary.getAbsences()%></td>
                <td class="basic"><%=salary.getBasic()%></td>
                <td class="daily"><%=salary.getDailyPayment()%></td>
                <td class="otTot"><%=salary.getOtHours()%></td>
                <td class="otRate"><%=salary.getOtRate()%></td>
                <td class="Total"><%=0%></td>
            </tr>
            <%}}%>
        </table>
    </div>
</div>
<%@include file="mainDashboard.jsp" %>
<script src="js/loadSalaryDetails.js"></script>

</body>
</html>
