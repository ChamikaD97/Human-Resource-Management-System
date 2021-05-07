<%@ page import="attendance.attendanceBean" %>
<%@ page import="attendance.attendanceDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.Date" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/attendanceHistory.css">
</head>
<body>
<div class="content">
    <div class="heading">
        <h3> My Attendance History </h3>
    </div>
    <br>
    <form name="myAttendHist" action="searchMyAttendances" method="POST" onsubmit="return validateMyForm()">
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
            <table class="searchSal" hidden>
                <tr>
                    <td>
                        <label class="label">Worked Hours</label>
                    </td>
                    <th >
                        <input  type="text" id="workedHoursFrom" name="workedHoursFrom" placeholder="HH:MM">
                    </th>
                    <th >
                        <input  type="text" id="workedHoursTo" name="workedHoursTo" placeholder="HH:MM">
                    </th>
                </tr>
                <tr>
                    <td>
                        <label class="label">OT Hours</label>
                    </td>
                    <th >
                        <input  type="number" id="otHoursFrom" name="otHoursFrom" value="0">
                    </th>
                    <th >
                        <input  type="number" id="otHoursTo" name="otHoursTo" value="24">
                    </th>
                </tr>
            </table>
            <%
                Date date;
                date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                String currentYear= formatter.format(date);%>
            <table>
                <tr>
                    <td>
                        <label class="label">From</label>
                    </td>
                    <th>
                        <div class="year">
                            <input class="input" type="date" id="fromDate" name="fromDate">
                            <lable id="lblFromDate" class="valLabel"></lable>
                        </div>
                    </th>
                </tr>
                <tr>
                    <td>
                        <label class="label">To</label>
                    </td>
                    <th>
                        <div class="year">
                            <input class="input" type="date" id="toDate" name="toDate">
                            <lable id="lblToDate" class="valLabel"></lable>
                        </div>

                    </th>
                </tr>
                <tr>
                    <th></th>
                    <th>
                        <input class="show" type="submit" value="Show"/>
                    </th>
                </tr>
            </table>
        </div>
        <%
            attendanceBean results= (attendanceBean) request.getAttribute("results");

            if (results != null) {
                //System.out.println(results.getResultGreater() +results.getResultLess() + results.getFromDate() + results.getToDate());

        %>
        <div>
            <table>
                <tr class="searchResults">
                    <td>
                        <%=results.getFromDate() +results.getToDate() + results.getWorkedHoursFrom() + results.getWorkedHoursTo() +results.getOtHoursFrom()+results.getOtHoursTo()%>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <%}
        String empId= (String) session.getAttribute("empId");
        String workedHoursFrom= request.getParameter("workedHoursFrom");
        String workedHoursTo= request.getParameter("workedHoursTo");
        String otHoursFrom= request.getParameter("otHoursFrom");
        String otHoursTo= request.getParameter("otHoursTo");
        String from= request.getParameter("fromDate");
        String to= request.getParameter("toDate");

        attendanceDao attendance = new attendanceDao();
        List<attendanceBean> attendanceListDefault = attendance.myAttendances(empId,from,to,workedHoursFrom,workedHoursTo,otHoursFrom,otHoursTo);
    %>

    <div class="result">
        <br>
        <table id="table">
            <tr>
                <th>
                    Attendance Id
                </th>
                <th>
                    Date
                </th>
                <th>
                    Attend Time
                </th>
                <th>
                    Leave Time
                </th>
                <th>
                    Worked Hours
                </th>
                <th>
                    Ot Hours
                </th>
            </tr>

            <%

                    for(attendanceBean attend:attendanceListDefault){
            %>
            <tr>
                <td class="attendData"><%=attend.getAttendanceId()%></td>
                <td class="attendData" ><%=attend.getDate()%></td>
                <td class="attendData"><%=attend.getAttendTime()%></td>
                <td class="attendData"><%=attend.getLeaveTime()%></td>
                <td class="attendData" style="color:forestgreen"><%=attend.getWorkedHrs()%></td>
                <td class="attendData" style="color:dodgerblue"><%=attend.getotHours()%></td>
            </tr>
            <%}
                String search= request.getParameter("totSalary");
                if (search != null) {
                    List<attendanceBean> attendancesBySearch = (List<attendanceBean>) request.getAttribute("attendances");
                    for(attendanceBean attend:attendancesBySearch){
                        if(session.getAttribute("empId").equals(attend.getEmpId())){}
                        else{
            %>
            <tr>
                <td class="attendData"><%=attend.getAttendanceId()%></td>
                <td class="attendData"><%=attend.getDate()%></td>
                <td class="attendData"><%=attend.getAttendTime()%></td>
                <td class="attendData"><%=attend.getLeaveTime()%></td>
                <td class="attendData" style="color:forestgreen"><%=attend.getWorkedHrs()%></td>
                <td class="attendData" style="color:dodgerblue"><%=attend.getotHours()%></td>
            </tr>
            <%}}}%>
        </table>
    </div>

</div>
</div>
<%@include file="mainDashboard.jsp" %>

<script type="text/javascript" src="js/attendanceHistory.js"></script>
</body>
</html>
