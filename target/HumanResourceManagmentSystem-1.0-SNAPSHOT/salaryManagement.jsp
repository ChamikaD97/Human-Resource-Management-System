<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import ="java.util.Date" %>
<%@ page import="DBconnection.DBconn" %>
<%@ page import="java.sql.*" %>
<%@ page import="salary.SalaryDao" %>
<%@ page import="salary.SalaryBean" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Deshan-UCSC
  Date: 10/18/2020
  Time: 6:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/salaryManagement.css">
</head>
<body>
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

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd");
    String d= formatter.format(date);
    int today=Integer.parseInt(d);
    int salaryCalculation = 0;
    String s=null;
    int check=0;
    try {
        Connection con = DBconn.getConnection();
        Statement statement = con.createStatement();
        ResultSet rs1 = null;

        rs1 = statement.executeQuery("SELECT * FROM `monthlysalarydetails` WHERE totalSalary ='"+-404+"' ORDER BY salaryId ASC");

        if (rs1.next()) {
           check=1;
        }
    }catch (SQLException e) {
        e.printStackTrace();
    }
%>
<div class="content">

    <div class="heading">
        <h3> Salary Management </h3>
    </div>
    <%if (check==1){%>
    <br>
    <div class="errorRow">
                <a class="erros"  href="manageSalaryErrors.jsp">Resolve Conflicts</a>
        </table>
    </div>

    <%}%>
    <form action="salaryManage" method="POST">
        <%
            SalaryDao error = new SalaryDao();
            List<SalaryBean> salaryDetails = error.getBasicDetails();
        %>
        <div class="main">
            <%                  
                try {
                    Connection con1 = DBconn.getConnection();
                    Statement statement = con1.createStatement();
                    ResultSet rs1 = null;
                   
                    rs1 = statement.executeQuery("SELECT * FROM `customizeddata` ORDER BY`ID` DESC LIMIT 1");

                    if (rs1.next()) {
                        salaryCalculation= rs1.getInt("salaryCalculationDate");
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
                if (salaryCalculation==11 ||salaryCalculation==12||salaryCalculation==13){
                    s="th";
                }else if (salaryCalculation%10==1 ) {
                    s="st";
                }else if(salaryCalculation%10==2){
                    s="nd";
                }else if(salaryCalculation%10==3){
                    s="rd";
                }else {
                    s = "th";
                }
                if (salaryCalculation==today){
            %>
            <div class="salarydetails">
                <br>

                <table>
                    <tr class="row">
                        <td class="TD">
                            <label class="label">Enter Number Of Extra Holidays</label>
                        </td>
                        <th class="TH1">
                            <input class="input" type="number" name="extraHolidays" value="1" placeholder="Enter Number Of Extra Holidays Without Weekends">
                        </th>
                        <th class="TH2">
                            <input class="send" type="submit" value="Calculate">
                        </th>
                    </tr>
                </table>
            </div>
    </form>

            <%}else {%>
                <h2 class="notify">Salaries Can Be Finalized And Send To Employees Only On Next <b><%=salaryCalculation%><%=s%></b></h2>
            <%}%>

</div>

    <hr>
    <div class="result">
        <table id="table">
            <tr>
                <th>
                    Employee ID
                </th>
                <th>
                   Name
                </th>
                <th>
                    Basic Salary
                </th>
                <th>
                    OT rate
                </th>
            </tr>
            <%
                for(SalaryBean salary:salaryDetails){
            %>
            <tr>
                <td class="empName"><%=salary.getEmpId()%></td>
                <td class="office"><%=salary.getFName() +" "+ salary.getLName()%></td>
                <td class="nopay"><%=salary.getBasic()%></td>
                <td class="absence"><%=salary.getOtRate()%></td>
            </tr>
            <%}%>
        </table>
        <br>
    </div>

</div>
</div>

<%@include file="mainDashboard.jsp" %>
</body>
</html>
