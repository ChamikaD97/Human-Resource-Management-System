<%--
  Created by IntelliJ IDEA.
  User: Deshan-UCSC
  Date: 10/18/2020
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="user.UserBean" %>
<%@ page import="user.UserDao" %>
<%@ page import="user.EmployeeDao" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/updateMyDetails.css">
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<div class="content">
    <div class="heading">
        <h3>Change My Details</h3>
    </div>
    <form name="changePassword" action="updatePass" method="POST" autocomplete="off" onsubmit="return validateForm3()">
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
                if(result=="ErrCurrent"){%>
        <h4 class="response" style="color: #DC143C;">
            Current Password is invalid, Please Input Your Details Again!
        </h4>
        <%}
            request.setAttribute("result",null);
                if(result=="ErrPass"){%>
        <h4 class="response" style="color: #DC143C;">
            Password and Confirm Password is not Same, Please Input Your Details Again!
        </h4>
        <%}
            request.setAttribute("result",null);

            if(result=="ErrLength"){%>
        <h4 class="response" style="color: #DC143C;">
            Your Password is not Strong!
        </h4><%

        }
        request.setAttribute("result",null);
        if(result=="Successful"){%>

        <h4 class="response" style="color: #4bbe19;">
            Your Account is Updated Successfully!
        </h4>
        <%}
            request.setAttribute("result",null);
            if(result == "Unsuccessful"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Update Your Account, Please Check The Details And Try Again.
        </h4>
        <%} request.setAttribute("result",null);}
            request.setAttribute("result",null);
        %>
        <%
            UserBean empDetails = new UserBean();
            String empId = (String) session.getAttribute("empId");
            empDetails.setEmpId(empId);
            EmployeeDao detail = new EmployeeDao();
            empDetails= detail.searchMyDetails(empDetails);
        %>

        <br>
        <div class="main">
            <table class="data">
            <tr>
                <td>
                    <label class="label">Emp ID</label>
                </td>
                <th>
                    <input class="input" type="number" name="empId" value="<%=empDetails.getEmpId()%>" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Current Password</label>
                </td>
                <th>
                    <input class="input" type="password" name="current_password">
                    <label id="lblCurPass" class="valLabel"></label>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Password</label>
                </td>
                <th>
                    <input class="input" type="password" name="password" >
                    <label id="lblPassword" class="valLabel"></label>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Confirm Password</label>
                </td>
                <th>
                    <input class="input" type="password" name="confirm_password">
                    <label id="lblConPass" class="valLabel"></label>
                    <br>
                </th>
            </tr>
        </table>

            <table>
                <tr>
                    <td></td>

                    <td></td>
                    <td>
                        <input type="submit" class="update" value="CHANGE">
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
</div>
<%@include file="mainDashboard.jsp" %>

<script type="text/javascript" src="js/editEmployee.js"></script>
<!--<script type="text/javascript" src="js/loadDataToTable.js"></script>-->
</body>
</html>
