<%@ page import ="java.sql.*" %>
<%
    String user = request.getParameter("uname_1");    
    //String pwd = request.getParameter("pass");
    
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://199.98.20.121:3306/TestDatabase",
				"TDguest", "TDpass");
		Statement st = con.createStatement();
		ResultSet rs; 	
		rs = st.executeQuery("select * from members where uname='" + user + "'");
		if (rs.next()) {
			
			int hscore = rs.getInt("highscore");
			String leveln = rs.getString("level");
			int currentsc = rs.getInt("score");
			int numWin = rs.getInt("numWins");
			int totalGame = rs.getInt("totalGames");
			int InGame = rs.getInt("InGame");
			String user2 = rs.getString("PLY2uname");
			
			session.setAttribute("userid_1", user);
			session.setAttribute("info_1", currentsc);
			session.setAttribute("levelnum_1", leveln);
			session.setAttribute("hiscore_1", hscore);
			session.setAttribute("numW_1", numWin);
			session.setAttribute("totGame_1", totalGame);
			session.setAttribute("ingame_1", InGame);
			session.setAttribute("userid_2", user2);
			
			response.sendRedirect("searchRS.jsp");
			
		} else {
			out.println("NOT FOUND <a href='index.jsp'>try again</a>");
		}
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