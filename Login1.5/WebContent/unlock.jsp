<%@ page import ="java.sql.*" %>
<%
    String user = (String) session.getAttribute("userid"); //request.getParameter("uname");    
    //String pwd = request.getParameter("pass");
    //String newpwd = request.getParameter("new_password");
    //String newpwd2 = request.getParameter("new_password2");
	
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://199.98.20.121:3306/TestDatabase",
				"TDguest", "TDpass");
		
		Statement st = con.createStatement();
		ResultSet rs;
		rs = st.executeQuery("select * from members where uname='" + user + "'");
		//out.println(rs.getString("uname") + "  " +rs.getInt("pass"));
		
		if (rs.next()) {
			out.println(rs.getString("uname") + "  " +rs.getString("pass"));
			
				st.executeUpdate("update members set LoggedIn='0' where uname='" + user + "'");
				//session.setAttribute("userid", user);
				//out.println("welcome " + userid);
				//out.println("<a href='logout.jsp'>Log out</a>");
				response.sendRedirect("success2.jsp");
			
			//else {
				//out.println("Invalid new password <a href='changePW.jsp'>try again</a>");
			//}
		
		} //else {
			//out.println("Invalid password <a href='changePW.jsp'>try again</a>");
		//}
		rs.close();
		st.close();
	} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
	} finally{
			try {
				if (con != null)
					con.close();
			} catch (SQLException e){}
	}
%>
