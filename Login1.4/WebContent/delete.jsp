<%@ page import ="java.sql.*" %>
<%
    String user = (String) session.getAttribute("userid");    
    String pwd = request.getParameter("pass");
    String email = request.getParameter("email");
    
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://localhost/TestDatabase",
				"TDguest", "TDpass");
		Statement st = con.createStatement();
		ResultSet rs; 	
		rs = st.executeQuery("select * from members where uname='" + user + "' and email = '"+email+"' and pass='" + pwd + "'");
		if (rs.next()) {
			
			st.executeUpdate("delete from members where uname='" + user + "'");
			//session.setAttribute("userid", user);
			//out.println("welcome " + userid);
			//out.println("<a href='logout.jsp'>Log out</a>");
			response.sendRedirect("logout.jsp");
			//response.sendRedirect("successRM.jsp");
		} else {
			out.println("Invalid password <a href='accountRM.jsp'>try again</a>");
		}
		rs.close();
		st.close();
		con.close();
	} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
	} finally{
			try {
				if (con != null)
					con.close();
			} catch (SQLException e){}
	}
	
%>