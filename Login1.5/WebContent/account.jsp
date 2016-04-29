<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account Summary</title>
    </head>
    <body>
        <form method="post" action="refresh.jsp">
            <center>
            <table border="1" width="30%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Account Summary</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><%=session.getAttribute("userid")%></td>
                    </tr>
                    <tr>
                        <td>Highest Score</td>
                        <td><%=session.getAttribute("hiscore")%></td>
                    </tr>
                    <tr>
                        <td>Wins</td>
                        <td><%=session.getAttribute("numW")%></td>
                    </tr>
                    <tr>
                        <td>Total Games</td>
                        <td><%=session.getAttribute("totGame")%></td>
                    </tr>
                    <tr>
                        <td>Level</td>
                        <td><%=session.getAttribute("levelnum")%></td>
                    </tr>
                    <tr>
                        <td>Current Game Score</td>
                        <td><%=session.getAttribute("info")%></td>
                    </tr>
                    <tr>
                        	<th colspan="2"><input type="submit" value="Refresh"/></th>
                    </tr>
                   	<tr>
                        <td colspan="2"><a href='changePW.jsp'>Change Your Password</a></td>
                    </tr>
                    <tr>
                    	<td colspan="2"><a href='accountRM.jsp'>Remove Your Account From The System</a></td>
                    </tr>
                    
                    <tr>
                        <td colspan="2"><a href="tutorial.jsp">Watch Game Tutorial Here</a></td>
                    </tr>
                    <tr>
                        <td colspan="2"><a href="search.jsp">Search Player</a></td>
                    </tr>
                    <tr>
                        <td colspan="2"> <a href='logout.jsp'>Log out</a></td>
                    </tr>
                </tbody>
            </table>
            </center>
        </form>
    </body>
</html>