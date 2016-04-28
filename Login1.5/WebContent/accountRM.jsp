<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Account</title>
    </head>
    <body>
        <form method="post" action="delete.jsp">
            <center>
            <table border="1" width="30%" cellpadding="3">
                <thead>
                    <tr>
                        <th colspan="2">Are You Sure To Remove Your Account?</th>
                    </tr>
                </thead>
                <tbody>
                   <tr>
                        <td>Enter Your Email Again</td>
                        <td><input type="text" name="email" value="" /></td>
                    </tr>
                    <tr>
                        <td>Enter Your Password Again</td>
                        <td><input type="password" name="pass" value="" /></td>
                    </tr>
               
                    <tr>
                        <th colspan="2"><input type="submit" value="Submit" /></th>
                    </tr>
                    <tr>
                    	<td colspan="2"> <a href='account.jsp'>View Your Account Summary</a></td>
                    </tr>
                    <tr>
                        <td colspan="2"><a href='changePW.jsp'>Change Your Password</a></td>
                    </tr>
                    <tr>
                        <td colspan="2"><a href="tutorial.jsp">Game Tutorial Here</a></td>
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