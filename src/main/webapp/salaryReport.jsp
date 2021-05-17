<%@ page import="salary.SalaryBean" %>
<%@ page import="salary.SalaryDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.Date" %>
<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="stylesheet" href="style/staffSalaryOverview.css">
</head>
<body>
<div class="content">
    <div class="heading">
        <h3>Salary Reports </h3>
    </div>
    <br>
    <form name="salReport" action="searchStaffSalariesToPDF" method="POST" onsubmit="return validateForm()">
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
        <div class="selection">
            <table class="searchSal">
                <tr>
                    <td>
                        <label class="label">Emp Id</label>
                    </td>
                    <th >
                        <input  type="number" id="empId" name="empId" min="10000" placeholder="Select Employee ID">
                    </th>
                </tr>
                <tr>
                    <td>
                        <label class="label">Salaries Greater Than</label>
                    </td>
                    <th >
                        <input  type="number" id="totSalary" name="totSalary" min="0" value="0">
                        <lable id="lblGrSal" class="valLabel"></lable>
                    </th>
                </tr>
                <tr>
                    <td>
                        <label class="label">Salaries Less Than</label>
                    </td>
                    <th >
                        <input  type="number" id="totSalaryLess" name="totSalaryLess" min="0" value="999999999">
                        <lable id="lblLeSal" class="valLabel"></lable>
                    </th>
                </tr>
            </table>
            <%
                Date date;
                date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                String currentYear= formatter.format(date);
                int year= Integer.parseInt(currentYear)-1;
            %>
            <table>
                <tr>
                    <td>
                        <label class="label">From</label>
                    </td>
                    <th>
                        <div class="year">
                            <input type="number" id="fromYear" name="fromYear" min="2020" value="<%=year%>">
                        </div>

                    </th>
                    <th>
                        <div class="date">
                            <select name="fromMonth" id="fromMonth">
                                <option value="0">All</option>
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
                        <lable id="lblFromDate" class="valLabel"></lable>
                    </th>

                </tr>
                <tr>
                    <td>
                        <label class="label">To</label>
                    </td>
                    <th>
                        <div class="year">
                            <input type="number" id="toYear" name="toYear" min="2020" value="<%=currentYear%>">
                        </div>

                    </th>
                    <th>
                        <div class="date">
                            <select name="toMonth" id="toMonth">
                                <option value="0" selected>All</option>
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
                                <option value="12" >December</option>
                            </select>
                        </div>
                        <lable id="lblToDate" class="valLabel"></lable>
                    </th>
                </tr>

                <tr>
                    <td>

                    </td>
                    <th></th>
                    <th>
                        <input class="show" type="submit" value="Show"/>
                    </th>
                </tr>
            </table>
        </div>
        <%
            SalaryBean results= (SalaryBean) request.getAttribute("results");
            String display;
            if (results != null) {
                System.out.println(results.getResultGreater() + results.getResultLess() + results.getFromDate() + results.getToDate());
                 display = results.getResultGreater() + results.getResultLess() + results.getFromDate() + results.getToDate();
            } else {
                    display ="All Salary Details";
                }
        %>
        <div>
            <table id="result">
                <tr class="searchResults">
                    <td id="detail">
                        <%=display%>
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <%
        String empId= request.getParameter("empId");
        String fromMonth= request.getParameter("fromMonth");
        String toMonth= request.getParameter("toMonth");
        String totSalary= request.getParameter("totSalary");
        String totSalaryLess= request.getParameter("totSalaryLess");
        SalaryDao empDao = new SalaryDao();

        List<SalaryBean> salaryListDefault = empDao.staffSalaries(empId,fromMonth,toMonth,totSalary,totSalaryLess);
    %>

    <div class="result">
        <br>
        <table id="myTable">
            <%

                String check= request.getParameter("totSalary");

                if (check== null) {
                    for(SalaryBean salary:salaryListDefault){

            %>
            <tr>
                <td class="salaryData"><%=salary.getEmpId()%></td>
                <td class="salaryData"><%=salary.getFromDate()%></td>
                <td class="salaryData"><%=salary.getToDate()%></td>
                <td class="salaryData"><%=salary.getNoPayes()%></td>
                <td class="salaryData"><%=salary.getAbsences()%></td>
                <td class="basic"><%=salary.getBasic()%></td>
                <td class="daily"><%=salary.getDailyPayment()%></td>
                <td class="otrate"><%=salary.getOtRate()%></td>
                <td class="salaryData"><%=salary.getOtHours()%></td>
                <td class="totalSalary"><%=salary.getTotalSalary()%></td><%}}%>

            </tr>
            <%
                String search= request.getParameter("totSalary");
                if (search != null) {
                    List<SalaryBean> salariesBySearch = (List<SalaryBean>) request.getAttribute("salaries");
                    for(SalaryBean salary:salariesBySearch){
            %>
            <tr>
                <td class="salaryData"><%=salary.getEmpId()%></td>
                <td class="salaryData"><%=salary.getFromDate()%></td>
                <td class="salaryData"><%=salary.getToDate()%></td>
                <td class="salaryData"><%=salary.getNoPayes()%></td>
                <td class="salaryData"><%=salary.getAbsences()%></td>
                <td class="basic"><%=salary.getBasic()%></td>
                <td class="daily"><%=salary.getDailyPayment()%></td>
                <td class="otrate"><%=salary.getOtRate()%></td>
                <td class="salaryData"><%=salary.getOtHours()%></td>
                <td class="totalSalary"><%=salary.getTotalSalary()%></td><%}}%>

            </tr>
        </table>
    </div>
    <div>
        <input class="show" type="submit" onclick="SalariestoPDF()" value="Download PDF"/>
    </div>
</div>
</div>
<%@include file="mainDashboard.jsp" %>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
<script src="js/printSalaries.js"></script>
</html>
