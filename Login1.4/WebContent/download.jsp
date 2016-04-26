<%@ page  import="java.io.FileInputStream" %>
<%@ page  import="java.io.BufferedInputStream"  %>
<%@ page  import="java.io.File"  %>
<%@ page import="java.io.IOException" %>


<%

   // you  can get your base and parent from the database
   String base="e1";
   String parent="e2";   
   String filename="Login.zip";
// you can  write http://localhost
   String filepath="http://localhost/Downloads/";

BufferedInputStream buf=null;
   ServletOutputStream myOut=null;

try{

myOut = response.getOutputStream( );
     File myfile = new File(filepath+filename);
     
     //set response headers
     response.setContentType("text/plain");
     
     response.addHeader(
        "Content-Disposition","attachment; filename="+filename );

     response.setContentLength( (int) myfile.length( ) );
     
     FileInputStream input = new FileInputStream(myfile);
     buf = new BufferedInputStream(input);
     int readBytes = 0;

     //read from the file; write to the ServletOutputStream
     while((readBytes = buf.read( )) != -1)
       myOut.write(readBytes);

} catch (IOException ioe){
     
        throw new ServletException(ioe.getMessage( ));
         
     } finally {
         
     //close the input/output streams
         if (myOut != null)
             myOut.close( );
          if (buf != null)
          buf.close( );
         
     }

   
   
%> - See more at: http://www.codemiles.com/servlets-jsp/jsp-to-download-file-t17.html#sthash.EBOJ8SSL.dpuf
Start downloading ...
<br/>
<a href='index.jsp'> Go to Main Page</a>
<%
%>