<%--
  Created by IntelliJ IDEA.
  User: Deshan-UCSC
  Date: 10/19/2020
  Time: 1:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="DBconnection.DBconn" %>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.util.Base64" %>

<html>
<head>
    <title>Human Resource Management System</title>
    <link rel="stylesheet" href="style/mainStyle.css">
    <link rel="icon" href="img/logo.png" sizes="25x25" type="image/png">
    <link rel="stylesheet" href="style/home.css">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>

</head>
<body>
<div class="content">
    <div class="heading">
        <h3>InfoCorner</h3>
    </div>
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

    <%if(session.getAttribute("postView").equals(0)){%>

    <img class="noPostImg" src="img/noPost.jpg" />

    <%}else{%>
    <form method="POST" action="home.jsp">
        <meta http-equiv="refresh" content="30">
        <div class="post" id="m">

            <table id="post_table">
                <%
                    ResultSet rs= null;
                    int c=0;
                    try
                    {
                        Connection con = DBconn.getConnection();
                        Statement statement = con.createStatement();
                        rs = statement.executeQuery("SELECT post.*,user.firstName,user.lastName FROM post INNER JOIN user ON user.empId = post.empId GROUP BY post.dateTime DESC");

                        while(rs.next()){
                            String pId = rs.getString("postId");
                            int count = rs.getInt("postImage");
                            c++;
                %>
                <tr>
                <tr>
                    <th class="name"><%=rs.getString("firstName")%> <%=rs.getString("lastName")%></th>
                </tr>
                <tr>
                    <td class="date"><%=rs.getString("dateTime")%></td>
                </tr>
                <tr>
                    <td class="des"><%=rs.getString("postText")%></td>
                </tr >
                <tr>
                        <%
            String imgs[] = new String[count];
            Blob img;
            int a=0;
            ResultSet rsImg = null;
            Statement getImg = con.createStatement();
            rsImg = getImg.executeQuery("SELECT * FROM postimgs WHERE postId = '"+pId+"'");
            while(rsImg.next())
            {
                img = rsImg.getBlob("image");
                InputStream inputStream = img.getBinaryStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1)
                {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = outputStream.toByteArray();
                imgs[a] = Base64.getEncoder().encodeToString(imageBytes);
                a++;
            }
            if(count%2==0){
                for(int j=0;j<count;j=j+2){%>
                <tr>
                    <th class="image">
                        <img id="img<%=c%><%=j%>" class="imgL" src="data:image/jpg;base64,<%=imgs[j]%>" onclick="zoomImg(this.id)"/>
                        <img id="img<%=c%><%=(j+1)%>" class="imgR" src="data:image/jpg;base64,<%=imgs[j+1]%>" onclick="zoomImg(this.id)"/>
                    </th>
                </tr>
                <%}}
                else{%>
                <tr>
                    <th class="image"><img id="img<%=c%><%=0%>" class="singleImg" src="data:image/jpg;base64,<%=imgs[0]%>" onclick="zoomImg(this.id)"/></th>
                </tr>
                <%for(int j=1;j<count;j=j+2){%>
                <tr>
                    <th class="image">
                        <img id="img<%=c%><%=j%>" class="imgL" src="data:image/jpg;base64,<%=imgs[j]%>" onclick="zoomImg(this.id)"/>
                        <img id="img<%=c%><%=(j+1)%>" class="imgR" src="data:image/jpg;base64,<%=imgs[j+1]%>" onclick="zoomImg(this.id)"/>
                    </th>
                </tr>
                <%}}}}catch (SQLException e){e.printStackTrace();}%>
                </tr>

                </tr>
            </table>
        </div>
        <%}%>
    </form>
    <div id="imgModal" class="modal">
        <span class="close">x</span>
        <img class="modal-content" id="imgZoom">
    </div>
</div>

<%@include file="mainDashboard.jsp" %>
<script>
    function zoomImg(imgId)
    {
        var modal = document.getElementById("imgModal");
        var img = document.getElementById(imgId);
        var modalImg = document.getElementById("imgZoom");
        modal.style.display = "block";
        modalImg.src = img.src;

        var closeBtn = document.getElementsByClassName("close")[0];

        closeBtn.onclick = function()
        {
            console.log("close");
            modal.style.display = "none";
        }
    }

    function redirectHome()
    {
        console.log("Redirect")
        document.getElementById('sessionPage').action = "home.jsp";
        document.getElementById('sessionPage').method = "POST"
        document.getElementById('sessionPage').submit();
    }
</script>
</body>
</html>