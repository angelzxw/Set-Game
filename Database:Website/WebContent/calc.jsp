<%@ page import ="java.sql.*" %>
<%
    if (((int) session.getAttribute("ingame_1")) == 0) {
    	String username_1 = (String) session.getAttribute("userid_1");
		out.println(username_1 + " currently is not playing");
		out.println("Invalid <a href='index.jsp'>try again</a>");
	} else {
		String userid_2 = (String) session.getAttribute("userid_2");
		Connection con = null;
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://199.98.20.121:3306/TestDatabase",
				"TDguest", "TDpass");
		Statement st = con.createStatement();
		ResultSet rs; 	
		rs = st.executeQuery("select * from members where uname='" + userid_2 + "'");
		if (rs.next()) {
			
			int hscore_2 = rs.getInt("highscore");
			String leveln_2 = rs.getString("level");
			int currentsc_2 = rs.getInt("score");
			int numWin_2 = rs.getInt("numWins");
			int totalGame_2 = rs.getInt("totalGames");
			//int InGame_2 = rs.getInt("InGame");
			String user2_2 = rs.getString("PLY2uname");
			
			session.setAttribute("userid_2", user2_2);
			session.setAttribute("info_2", currentsc_2);
			session.setAttribute("levelnum_2", leveln_2);
			session.setAttribute("hiscore_2", hscore_2);
			session.setAttribute("numW_2", numWin_2);
			session.setAttribute("totGame_2", totalGame_2);
			
			double rate_1 = ((int) session.getAttribute("numW_1"))/((int) session.getAttribute("totGame_1"));
			double rate_2 = numWin_2/totalGame_2;
			
			int prob = (int) ((rate_1)/(rate_1 + rate_2) * 100);
			int prob_2 = 100 - prob;
			
			session.setAttribute("winchan_1", prob);
			session.setAttribute("winchan_2", prob_2);
			//session.setAttribute("ingame_2", InGame);
			//session.setAttribute("userid_2", user2);
			
			//response.sendRedirect("live_beta.jsp");
			
		} else {
			out.println("Invalid <a href='index.jsp'>try again</a>");
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

    }
%>