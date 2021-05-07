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
  <link rel="stylesheet" href="style/editPost.css">

</head>
<body>
<div class="content">
  <div class="heading">
    <h3>Update Post</h3>
  </div>
  <form method="POST" id="editPost" action="updateThisPost.jsp">
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
      Post Updated Successfully!
    </h4><%

    }request.setAttribute("result",null);
  %>
    <%
      if(result == "Unsuccessful"){%>
    <h4 class="response" style="color: #DC143C;">
      Unable To Update Your Post! , Try Again.
    </h4>
    <%} request.setAttribute("result",null);}
      request.setAttribute("result",null);
    %>
    <div class="post">
      <table id="post_table">
        <%
          Object empId = session.getAttribute("empId");
          int p=0;
          ResultSet rs= null;
          try
          {
            Connection con = DBconn.getConnection();
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT post.*,user.firstName,user.lastName FROM post INNER JOIN user ON user.empId = post.empId WHERE post.empId = '"+empId+"' GROUP BY post.dateTime DESC");

            while(rs.next()){
              String pId = rs.getString("postId");
              int count = rs.getInt("postImage");
        %>
        <tr>
        <tr>
          <th align="left"><input type="radio" id="post<%=p%>" name="post" value="<%=pId%>" onclick="executeForm()" hidden><label for="post<%=p%>" class="name"><%=rs.getString("firstName")%> <%=rs.getString("lastName")%></label></th>
        </tr>
        <tr>
          <td class="date"><%=rs.getString("dateTime")%></td>
        </tr>
        <tr>
          <td class="des"><%=rs.getString("postText")%></td>
        </tr >
        <tr>
            <%
            p++;
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
            <img class="imgL" src="data:image/jpg;base64,<%=imgs[j]%>" />
            <img class="imgR" src="data:image/jpg;base64,<%=imgs[j+1]%>"/>
          </th>
        </tr>
        <%}}
        else{%>
        <tr>
          <th class="image">
            <img class="singleImg" src="data:image/jpg;base64,<%=imgs[0]%>" width="400" height="400"/>
          </th>
        </tr>
        <%for(int j=1;j<count;j=j+2){%>
        <tr>
          <th class="image">
            <img class="imgL" src="data:image/jpg;base64,<%=imgs[j]%>"/>
            <img class="imgR" src="data:image/jpg;base64,<%=imgs[j+1]%>"/>
          </th>
        </tr>
        <%}}}}catch (SQLException e){e.printStackTrace();}%>
        </tr>
        </tr>
      </table>
    </div>
  </form>
  <script>
    function executeForm()
    {
      document.getElementById('editPost').submit();
    }
  </script>
</div>
<%@include file="mainDashboard.jsp" %>


</body>
</html>
