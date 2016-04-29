<%@ page import ="java.sql.*" %>
<%
    String user = request.getParameter("uname_1");    
    //String pwd = request.getParameter("pass");
    
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://199.98.20.118:3306/TestDatabase",
				"TDguest", "TDpass");
		Statement st = con.createStatement();
		ResultSet rs; 	
		rs = st.executeQuery("select * from members where uname='" + user + "'");
		if (rs.next()) {
			
			int hscore = rs.getInt("highscore");
			String leveln = rs.getString("level");
			int currentsc = rs.getInt("info");
			int numWin = rs.getInt("numWins");
			int totalGame = rs.getInt("totalGames");
			int InGame = rs.getInt("InGame");
			int user2 = rs.getString("PLY2uname");
			
			session.setAttribute("userid", user);
			session.setAttribute("info", currentsc);
			session.setAttribute("levelnum", leveln);
			session.setAttribute("hiscore", hscore);
			session.setAttribute("numW", numWin);
			session.setAttribute("totGame", totalGame);
			session.setAttribute("ingame", InGame);
			session.setAttribute("userid_2", user2);
			
			response.sendRedirect("searchRS.jsp");
			
		} else {
			out.println("Invalid password <a href='index.jsp'>try again</a>");
		}
		rs.close();
		s.close();
	} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
	} finally{
			try {
				if (con != null)
					con.close();
			} catch (SQLException e){}
	}
	
%>