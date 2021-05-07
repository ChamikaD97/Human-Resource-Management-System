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
<%@ page import="leave.LeaveBean" %>
<%@ page import="leave.LeaveDao" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/approveOrReject.css">
</head>
<body>
<div class="content">
    <input class="input" type="number" name="empId" value="<%=session.getAttribute("empId")%>" hidden>
    <div class="heading">
        <h3>Approve or Reject Leave</h3>
    </div>
    <form  name="leaveApproveReject" action="approveOrRejectLeave" method="POST" onsubmit="return validateForm()">
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
           Response is Send To the Employee!
        </h4><%

        }request.setAttribute("result",null);
    %><%
        if(result == "Unsuccessful"){%>
        <h4 class="response" style="color: #DC143C;">
            Unable To Send the Response!Try Again
        </h4>
        <%} request.setAttribute("result",null);}
            request.setAttribute("result",null);
        %>

        <input class="input" type="number" name="empId" value="<%=session.getAttribute("empId")%>" hidden>
            <%
                String empId= (String) session.getAttribute("empId");
        LeaveDao empDao = new LeaveDao();
        List<LeaveBean> leaveRequestList = empDao.employeeLeaveRequests(empId);
    %>
    <br>
        <div class="leaves">
            <table>
                <tr>
                    <td>
                        <label class="label">Remaining Payed Leaves</label>
                    </td>
                    <td>
                        <label class="label">Remaining NoPayed Leaves</label>
                    </td>
                    <td>
                        <label class="label">Remaining Medical Leaves</label>
                    </td>

                </tr>
                <tr>
                    <th>
                        <input class="input" type="text" name="leavesPayed" id="RemainingPayed" readonly>
                    </th>

                    <th>
                        <input class="input" type="text" name="leavesNoPay" id="RemainingNoPayed" readonly>
                    </th>

                    <th>
                        <input class="input" type="text" name="leavesMedical" id="RemainingMedical" readonly>
                    </th>

                </tr>


            </table>
<br>
        </div>
    <div class="main">
        <table class="top">

            <tr>
                <td>
                    <label hidden class="label">Leave Id</label>
                </td>
                <th>
                    <input hidden class="input" type="text" name="leaveId" id="leaveId" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Name</label>
                </td>
                <th>
                    <input class="input" type="text" name="name" id="name" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">From Date</label>
                </td>
                <th>
                    <input class="input" type="text" name="from" id="from" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">To Date</label>
                </td>
                <th>
                    <input class="input" type="text" name="to" id="to" readonly>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Leave Type</label>
                </td>
                <th>
                    <input class="input" type="text" name="to" id="type" readonly>
                </th>
            </tr>
        </table>

        </table>
        <table id="reasonA">
            <tr>
                <td>
                    <label class="label">Reason for Leave Applying...</label>
                </td>

            </tr>
            <tr>
                <th>
                    <textarea class="textarea" rows="5" cols="100" name="reason" id="reason"></textarea>
                </th>
            </tr>
        </table>
        <table class="apprej">
            <tr>
                <th>
                    <select class="status" name="status"  id="sat">
                        <option  class="AppRej" value="2">Approve</option>
                        <option  class="AppRej" value="0">Reject</option>
                    </select>
                </th>
            </tr>
            <tr>
                <th class="thBtn">
                    <input class="apply" type="submit" value="Send"/>
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
                    Leave Id
                </th>
                <th>
                    Employee
                </th>

                <th>
                    From
                </th>
                <th>
                    To
                </th>
                <th>
                    Reason
                </th>
                <th>
                    Type
                </th>
                <th>
                    Remaining Payed
                </th>
                <th>
                    Remaining NoPay
                </th>
                <th>
                    Remaining Medical
                </th>
            </tr>
            <%
                for(LeaveBean leave:leaveRequestList){
            %>
            <tr>
                <td class="empIdd"><%=leave.getLeaveId()%></td>
                <td class="empName"><%=leave.getEmpId()%></td>
                <td class="fromDate"><%=leave.getfromDate()%></td>
                <td class="toDate"><%=leave.gettoDate()%></td>
                <td class="reason"><%=leave.getReason()%></td>
                <td class="type"<%if(leave.getType().equals("Payed")){%>style="color: dodgerblue" <%}
                else if(leave.getType().equals("NoPay")){%>style="color:crimson"<%}
                else if(leave.getType().equals("Medical")){%>style="color:forestgreen"<%}%>><%=leave.getType()%></td>
                <td class="annualLeaves"><%=leave.getremPayedLeaves()%></td>
                <td class="remainLeaves"><%=leave.getremNoPayedLeaves()%></td>
                <td class="takenLeaves"><%=leave.getremMedicalLeaves()%></td>
            </tr>
            <%}%>
        </table>
    </div>
</div>
<%@include file="mainDashboard.jsp" %>

<script type="text/javascript" src="js/loadLeaveDetails.js"></script>

</body>
</html>
