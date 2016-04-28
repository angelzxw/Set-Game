<%@ page import ="java.sql.*" %>
<%
    String user = (String) session.getAttribute("userid");    
    //String pwd = request.getParameter("pass");
    
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://localhost/TestDatabase",
				"TDguest", "TDpass");
		Statement st = con.createStatement();
		ResultSet rs; 	
		rs = st.executeQuery("select * from members where uname='" + user + "'");
		if (rs.next()) {
			
			int hscore = rs.getInt("highscore");
			int currentsc = rs.getInt("score");
			int numWin = rs.getInt("numWins");
			int totalGame = rs.getInt("totalGames");
			String leveln = rs.getString("level");
			
			session.setAttribute("userid", user);
			session.setAttribute("info", currentsc);
			session.setAttribute("levelnum", leveln);
			session.setAttribute("hiscore", hscore);
			session.setAttribute("numW", numWin);
			session.setAttribute("totGame", totalGame);
			//out.println("welcome " + userid);
			//out.println("<a href='logout.jsp'>Log out</a>");
			response.sendRedirect("account.jsp");
			
		} //else {
			//out.println("Invalid password <a href='index.jsp'>try again</a>");
		//}
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