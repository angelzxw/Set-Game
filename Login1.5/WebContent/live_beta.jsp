<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Live</title>
    </head>
    <body>
    <form method="post" action="../../../../../Documents/Unnamed Site 2/refresh.jsp">
            <center>
            <table border="1" width="30%" cellpadding="5">
                <thead>
                    <tr>
                        <th colspan="3">LIVE!!! SCORE BOARD</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th width="40%"></th>
                      <th width="29%"><%=session.getAttribute("userid_1")%></th>
                        <th width="31%"><%=session.getAttribute("userid_2")%></th>
                        
                  </tr>
                    <tr>
                        <th>Current Score</th>
                      <td><%=session.getAttribute("score_1")%></td>
                        <td><%=session.getAttribute("score_1")%></td>
                  </tr>
                    <tr>
                        <th>History Wins</th>
                      <td><%=session.getAttribute("numW_1")%> / <%=session.getAttribute("totGame_1")%></td>
                        <td><%=session.getAttribute("numW_2")%> / <%=session.getAttribute("totGame_2")%></td>
                  </tr>
                    <tr>
                        <th>Highest Score</th>
                      <td><%=session.getAttribute("hiscore_1")%></td>
                        <td><%=session.getAttribute("hiscore_2")%></td>
                  </tr>
                    <tr>
                        <th>Win Chance</th>
                      <td><%=session.getAttribute("winchan_1")%></td>
                        <td><%=session.getAttribute("winchan_2")%></td>
                  </tr>
                    <tr>
                    	<th colspan="3"><div align="center">
                    	  <input type="submit" value="Refresh" />
                  	  	</div></th>
                    </tr>
                    <tr>
                        <th colspan="3"><div align="center"><a href="../../../../../Documents/Unnamed Site 2/index.jsp">Exit</a></div></th>
                    </tr>
                </tbody>
            </table>
        </center>
        </form>
    </body>
</html>