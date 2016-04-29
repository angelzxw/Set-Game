<%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
%>
You are not logged in<br/>
<a href="index.jsp">Please Login</a>
<%} else {
%>
Welcome, <B><I> <%=session.getAttribute("userid")%></I></B>
<br/>
<a href='account.jsp'>View Your Account Summary</a>
<br/>
<a href='changePW.jsp'>Change Your Password</a>
<br/>
<a href='accountRM.jsp'>Remove Your Account From The System</a>
<br/>
<a href="tutorial.jsp">Watch Game Tutorial Here</a>
<br/>
<a href="search.jsp">Search Player</a>
<br/>
<a href='logout.jsp'>Log out</a>
<%
    }
%>