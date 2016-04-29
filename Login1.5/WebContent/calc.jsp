<%
    if ((session.getAttribute("ingame_1") == 0) {
%>
<B><I><%=session.getAttribute("userid_1")%></I></B> currently is not playing<br/>
<a href="index.jsp">Go Back</a>
<%} else {
	String userid_2 = (String) session.getAttribute("userid_2");
	Connection con = null;
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDatabase",
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
			int user2_2 = rs.getString("PLY2uname");
			
			session.setAttribute("userid_2", user);
			session.setAttribute("info_2", currentsc);
			session.setAttribute("levelnum_2", leveln);
			session.setAttribute("hiscore_2", hscore);
			session.setAttribute("numW_2", numWin);
			session.setAttribute("totGame_2", totalGame);
			
			double rate = ((int) session.getAttribute("numW_1"))/((int) session.getAttribute("totGame_1"));
			double rate_2 = numWin_2/totalGame_2;
			
			int prob = rate/(rate + rate_1) * 100;
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
<B><I><%=session.getAttribute("userid")%></I></B> is playing against <%=session.getAttribute("userid_2")%><br/>
<br/>
<a href='live_beta.jsp'>Watching Their Game</a>
<br/>
<a href='index.jsp'>Exit</a>
<%
    }
%>