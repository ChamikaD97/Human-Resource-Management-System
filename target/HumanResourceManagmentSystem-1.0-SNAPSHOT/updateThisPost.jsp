<%--
  Created by IntelliJ IDEA.
  User: SupunN
  Date: 11/14/2020
  Time: 3:40 PM
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
    <link rel="stylesheet" href="style/updatePost.css">
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<div class="content">
    <div class="heading">
        <h3>Update Post</h3>
    </div>
    <form name="editPost" method="POST" action="editpost" enctype="multipart/form-data" onsubmit="return validateForm()">
        <input type="text" name="imgId" readonly hidden/>
        <div class="left" id="m">
            <table id="post_table">
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

                    String pId = request.getParameter("post");
                    ResultSet rs= null;
                    try
                    {
                        Connection con = DBconn.getConnection();
                        Statement statement = con.createStatement();
                        rs = statement.executeQuery("SELECT post.*,user.firstName,user.lastName,MAX(postimgs.imageId) AS maxId FROM ((post INNER JOIN user ON user.empId = post.empId) INNER JOIN postimgs ON postimgs.postId=post.postId) WHERE post.postId = '"+pId+"'");

                        if(rs.next()) {
                            String postId = rs.getString("postId");
                            int count = rs.getInt("postImage");
                %>
                <tr>
                <tr>
                    <input type="text" name="pId" value="<%=pId%>" readonly hidden/>
                    <th class="name"><%=rs.getString("firstName")%> <%=rs.getString("lastName")%></th>
                    <input type="text" name="maxId" value="<%=rs.getInt("maxId")+1%>" readonly hidden>
                    <input type="text" name="count" value="<%=count%>" readonly hidden>
                </tr>
                <tr>
                    <td class="date"><%=rs.getString("dateTime")%></td>
                </tr>
                <tr>
                    <td ><textarea class="des" rows="5" cols="50" name="description"><%=rs.getString("postText")%></textarea></td>

                </tr >
            </table>
            <table>
                <tr>
                    <th>
                    <th class="thSend"></th>
                    <th class="thSend"></th>
                    <input type="button" class="addImg" name="imgbtn" value="Add Images" onclick="addImage()">
                    <input type="text" name="tot" id="tot" value="0" readonly hidden>
                    </th>
                </tr>
            </table>
            <table>
                <tr>
                    <th class="thSend"></th>
                    <th class="thSend"></th>
                    <th>
                        <input class="send" type="submit" value="Send"/>
                    </th>
                </tr>
            </table>
        </div>
        <div class="right">
            <h3 class="help">Click on Image to Remove the Image</h3>
            <table id="ppp">
                <tr>
                        <%
                String imgs[] = new String[count];
                int imgIds[] = new int[count];
                Blob img;
                int a=0,n=0;
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
                    imgIds[n] = rsImg.getInt("imageId");
                    n++;
                }
                if(count%2==0){
                    for(int j=0;j<count;j=j+2){%>
                <tr>
                    <th class="image">
                        <input type="checkbox" id="<%=j%>" value="<%=imgIds[j]%>" hidden /><br>
                        <label for="<%=j%>" class="limg"><img name="img<%=j%>" src="data:image/jpg;base64,<%=imgs[j]%>" width="400" height="300" onclick="remImage(this)"/></label><br>
                        <input type="checkbox" id="<%=j+1%>" value="<%=imgIds[j+1]%>" hidden/><br>
                        <label for="<%=j+1%>" class="limg"><img name="img<%=j+1%>" src="data:image/jpg;base64,<%=imgs[j+1]%>" width="400" height="300" onclick="remImage(this)"/></label><br>
                    </th>
                </tr>
                <%}}
                else{%>
                <tr>
                    <th class="image">
                        <input type="checkbox" id="0" value="<%=imgIds[0]%>" hidden/><br>
                        <label for="0" class="limg"><img name="img0" src="data:image/jpg;base64,<%=imgs[0]%>" width="400" height="300" onclick="remImage(this)"/></label><br>
                    </th>
                </tr>
                <%for(int j=1;j<count;j=j+2){%>
                <tr>
                    <th class="image">
                        <input type="checkbox" id="<%=j%>" value="<%=imgIds[j]%>"  hidden/><br>
                        <label for="<%=j%>" class="limg"><img name="img<%=j%>" src="data:image/jpg;base64,<%=imgs[j]%>" width="400" height="300" onclick="remImage(this)"/></label><br>
                        <input type="checkbox" id="<%=j+1%>" value="<%=imgIds[j+1]%>" hidden/><br>
                        <label for="<%=j+1%>" class="limg"><img name="img<%=j+1%>" src="data:image/jpg;base64,<%=imgs[j+1]%>" width="400" height="300" onclick="remImage(this)"/></label><br>
                    </th>
                </tr>

                <%}}}}catch (SQLException e){e.printStackTrace();}%>
                </tr>
                </tr>
            </table>
        </div>
    </form>

</div>
<%@include file="mainDashboard.jsp" %>
<script type="text/javascript" src="js/updateThisPost.js"></script>

</body>
</html>
