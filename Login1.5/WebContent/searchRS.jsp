<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Live</title>
    </head>
    <body>

            <center>
            <table border="1" width="30%" cellpadding="5">
                <thead>
                    <tr>
                        <th colspan="3"><%=session.getAttribute("userid_1")%></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>Win Rate</th>
                      <td><%=session.getAttribute("numW_1")%> / <%=session.getAttribute("totGame_1")%></td>
                        
                  </tr>
                    <tr>
                        <th>Highest Score</th>
                      <td><%=session.getAttribute("hiscore_1")%></td>
                        
                  </tr>
                  <tr>
                        <th colspan="3"><div align="center"><a href="calc.jsp">Watch LIVE?</a></div></th>
                    </tr>
                    <tr>
                        <th colspan="3"><div align="center"><a href="index.jsp">Exit</a></div></th>
                    </tr>
                </tbody>
            </table>
        </center>
     
    </body>
</html>