<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
    <form method="post" action="searchUser.jsp">
            <center>
            <table border="1" width="30%" cellpadding="5">
                <thead>
                    <tr>
                        <th colspan="3">Search Player</th>
                    </tr>
                </thead>
                <tbody>
                   
                   <tr>
                        <td><input type="text" name="uname_1" value="" /></td>
                   </tr>
                    
                   <tr>
                    	<th colspan="3"><div align="center">
                    	  <input type="submit" value="Search" />
                  	  	</div></th>
                    </tr>
                    
                    <tr>
                        <th colspan="3"><div align="center"><a href="index.jsp">Exit</a></div></th>
                    </tr>
                    
                </tbody>
            </table>
        </center>
        </form>
    </body>
</html>