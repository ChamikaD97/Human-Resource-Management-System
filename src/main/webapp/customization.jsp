<%@ page import="Customize.CustomizeDao" %>
<%@ page import="DBconnection.DBconn" %>
<%@ page import="Customize.CustomizeBean" %>
<%@ page import="java.util.List" %>
<%@ page import="leave.LeaveBean" %>
<%--
  Created by IntelliJ IDEA.
  User: Deshan-UCSC
  Date: 10/18/2020
  Time: 6:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/customization.css">
</head>
<body>
<div class="content">
    <div class="heading">
        <h3>Customize The System Data</h3>
    </div>
    <br>
    <form name="customize" action="addCustomizeData" method="POST" onsubmit="return validateForm()">
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
        %>
        <input class="input" type="number" name="empId" value="<%=session.getAttribute("empId")%>" hidden>

        <div>
        <table class="selection" >
            <tr>
                <td>
                    <label class="label">Start Time</label>
                </td>
                <th>
                    <input type="time" id="startTime" name="startTime" value= "08:00">
                    <br>
                    <label id="lblStTime" class="valLabel"></label>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">End Time</label>
                </td>
                <th>

                    <input type="time" id="endTime" name="endTime" value="16:00">
                    <br>
                    <label id="lblEndTime" class="valLabel"></label>
                </th>
            </tr>

            <tr>
                <td>
                    <label class="label">Salary Calculation Date</label>
                </td>
                <th>
                    <input type="number" id="salaryCalculation" name="salaryCalculation" min="1" max="27" value="1">
                    <br>
                    <label id="lblSalDate" class="valLabel"></label>
                </th>

            </tr>
        </table>
        <table class="reset">
            <tr>
                <td>
                    <label class="label">Date To Reset System Data</label>
                </td>
                <th>
                    <div class="year">
                        <input type="number" id="fromDate" name="fromDate" max="28" min="1" value="1">
                    </div>

                </th>
                <th>
                    <div class="date">
                        <select name="fromMonth" id="fromMonth">
                            <option value="">Month</option>
                            <option value="01">January</option>
                            <option value="02">February</option>
                            <option value="03">March</option>
                            <option value="04">April</option>
                            <option value="05">May</option>
                            <option value="06">June</option>
                            <option value="07">July</option>
                            <option value="08">August</option>
                            <option value="09">September</option>
                            <option value="10">October</option>
                            <option value="11">November</option>
                            <option value="12">December</option>
                        </select>
                    </div>
                    <!--<label id="lblReset" class="valLabel"></label>-->
                </th>
            </tr>
            <tr>
                <td>

                </td>
                <th></th>
                <th><label id="lblReset" class="valLabel"></label></th>
            </tr>

        </table>
        <table class="btn">
            <tr>
                <th>
                    <input class="save" type="submit" value="Save"/>
                </th>
            </tr>
        </table>
    </div>

    </form>
        <%
            CustomizeDao cusDao = new CustomizeDao();
            CustomizeBean empList = cusDao.searchCus();

            CustomizeDao cusDaoAll = new CustomizeDao();
            List<CustomizeBean> list = cusDaoAll.searchAll();
        %>

    <hr>
<br>
    <h4>Currently Customized Data</h4>
        <table id="result">
            <tr>
                <th>
                    Start Time
                </th>
                <th >
                    End Time
                </th>
                <th>
                    Calculation Date
                </th>
                <th>
                    Reset Date
                </th>
                <th>
                    Updated Date
                </th>
                <th>
                    Change By
                </th>
            </tr>
            <tr>
                <td><%=empList.getstartTime()%></td>
                <td><%=empList.getendTime()%></td>
                <td><%=empList.getsalaryCalculation()%></td>
                <td><%=empList.getreset()%></td>
                <td><%=empList.getDate()%></td>
                <td><%=empList.getEmpId()%></td>
            </tr>
        </table>
    <br>
    <hr>
    <br>
  <h4>Last Customized Data</h4>
    <table id="result">

        <%
        for(CustomizeBean leave:list){
        %>
        <tr>
            <td><%=leave.getstartTime()%></td>
            <td><%=leave.getendTime()%></td>
            <td><%=leave.getsalaryCalculation()%></td>
            <td><%=leave.getreset()%></td>
            <td><%=leave.getDate()%></td>
            <td><%=leave.getEmpId()%></td><%}%>
        </tr>

    </table>
</div>

</div>
</div>
<%@include file="mainDashboard.jsp" %>

<script type="text/javascript" src="js/customization.js"></script>
</body>
</html>
