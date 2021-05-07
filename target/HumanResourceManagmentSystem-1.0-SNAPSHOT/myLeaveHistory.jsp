
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="leave.LeaveBean" %>
<%@ page import="leave.LeaveDao" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/myLeaveHistory.css">

</head>
<body>

<div class="content">
    <div class="heading">
        <h3> My Leave History </h3>
    </div>
    <br>
    <form name="myLeaveHist" action="searchMyLeaves" method="POST" onsubmit="return validateMyForm()">
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
    <div class="selection">
        <table>
            <tr>
                <td>
                    <label class="label">From</label>
                </td>
                <th>
                    <input class="input" type="date" id="fromDate" name="fromDate">
                    <lable id="lblFromDate" class="valLabel"></lable>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">To</label>
                </td>
                <th>
                    <input class="input" type="date" id="toDate" name="toDate">
                    <lable id="lblToDate" class="valLabel"></lable>
                </th>
            </tr>
            <tr>
                <td>
                    <label class="label">Status</label>
                </td>
                <th>
                    <select class="statusInput" name="status"  id="satus">
                        <option  class="type" >All</option>
                        <option  class="type" value="2">Approved</option>
                        <option  class="type" value="1">Pending</option>
                        <option  class="type" value="0">Rejected</option>
                    </select>
                </th>

            </tr>
            <tr>
                <td>
                    <label class="label">Type</label>
                </td>
                <th>
                    <select class="typeInput" name="type"  id="type">
                        <option  class="type" value="All">All</option>
                        <option  class="type" value="Payed">Payed</option>
                        <option  class="type" value="NoPay">NoPay</option>
                        <option  class="type" value="Medical">Medical</option>
                    </select>
                </th>

            </tr>

            <tr>
                <td>

                </td>
                <th>
                    <input class="show" type="submit" value="Show"/>
                </th>
            </tr>
        </table>
    </div>
        <%
            LeaveBean results= (LeaveBean) request.getAttribute("results");

            if (results != null){
                    //System.out.println(results.getstatus() +results.getType() + results.getfromDate() + results.gettoDate());

        %>
        <div>
            <table>
                <tr class="searchResults">
                    <td>
                        <%=results.getstatus() +results.getType() + results.getfromDate() + results.gettoDate()%>
                    </td>
                </tr>
            </table>
        </div>

    <%}
        String empId= (String) session.getAttribute("empId");
        String toDate= request.getParameter("toDate");
        String fromDate= request.getParameter("fromDate");
        String status= request.getParameter("status");
        String type= request.getParameter("type");

        LeaveDao empDao = new LeaveDao();


        List<LeaveBean> leaveListDefault = empDao.myLeaves(empId,toDate,fromDate,status,type);
    %>

    <div class="result">
        <table id="table">
            <tr>
                <th>
                    Leave
                </th>
                <th>
                    Applied Date
                </th>
                <th>
                    From Date
                </th>
                <th>
                    To Date
                </th>
                <th>
                    Reason
                </th>
                <th>
                    Status
                </th>
                <th>
                    Type
                </th>
                <th>
                    Authorized
                </th>
            </tr>
            <%
                String toDateSearch= request.getParameter("toDate");
                if (toDateSearch != null) {
                    List<LeaveBean> leavesBySearch = (List<LeaveBean>) request.getAttribute("leaves");
                    for(LeaveBean leave:leavesBySearch){
                        if(session.getAttribute("empId").equals(leave.getEmpId())){}
                        else{
            %>


            <tr>

                <td class="leaveId"><%=leave.getLeaveId()%></td>
                <td class="appDate"><%=leave.getappDate()%></td>
                <td class="fromDate"><%=leave.getfromDate()%></td>
                <td class="toDate"><%=leave.gettoDate()%></td>

                <td class="reason"><%=leave.getReason()%></td>

                <td class="status" <%if(leave.getstatus().equals("Rejected")){%>style="color: crimson"<%
                }else if(leave.getstatus().equals("Pending")){%>style="color:dodgerblue"<%
                }else if(leave.getstatus().equals("Approved")){%>style="color:forestgreen"<%}%>><%=leave.getstatus()%></td>

                <td class="type"<%if(leave.getType().equals("Payed")){%>style="color: dodgerblue" <%}
                else if(leave.getType().equals("NoPay")){%>style="color:crimson"<%}
                else if(leave.getType().equals("Medical")){%>style="color:forestgreen"<%}%>><%=leave.getType()%></td>

                <td class="empNIC"><%=leave.getAuthorizedPersonId()%><%}}}%></td>

            </tr>

            <%

                String toDate1= request.getParameter("toDate");

                if (toDate1== null) {
                for(LeaveBean leave:leaveListDefault){
                    if(session.getAttribute("empId").equals(leave.getEmpId())){}
                    else{
            %>


            <tr>

                <td class="leaveId"><%=leave.getLeaveId()%></td>
                <td class="appDate"><%=leave.getappDate()%></td>
                <td class="fromDate"><%=leave.getfromDate()%></td>
                <td class="toDate"><%=leave.gettoDate()%></td>
                <td class="reason"><%=leave.getReason()%></td>

                <td class="status" <%if(leave.getstatus().equals("Rejected")){%>style="color: crimson"<%
                }else if(leave.getstatus().equals("Pending")){%>style="color:dodgerblue"<%
                }else if(leave.getstatus().equals("Approved")){%>style="color:forestgreen"<%}%>><%=leave.getstatus()%></td>

                <td class="type"<%if(leave.getType().equals("Payed")){%>style="color: dodgerblue" <%}
                else if(leave.getType().equals("NoPay")){%>style="color:crimson"<%}
                else if(leave.getType().equals("Medical")){%>style="color:forestgreen"<%}%>><%=leave.getType()%></td>

                <td class="empNIC"><%=leave.getAuthorizedPersonId()%><%}}}%></td>

            </tr>


        </table>
    </div>

    </form>
</div>
</div>
<%@include file="mainDashboard.jsp" %>

<script type="text/javascript" src="js/leaveHistory.js"></script>
</body>
</html>
