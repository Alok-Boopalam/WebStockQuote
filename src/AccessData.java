

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 *Partial credit to this part of the code 
 * http://www.devx.com/Java/Article/27559/0/page/2
 */
public class AccessData
{
   
    private static final String Url = "http://www.webservicex.net/stockquote.asmx";
    private String sym;

   
    public AccessData(String sym)
    {
        this.sym = sym;
    }

  
    public String getSymbol()
    {
        return sym;
    }

    
    public void setSymbol(String sym)
    {
        this.sym = sym;
    }

    
    public String getQuote()
    {
        final String resp = getSOAPQuote(sym);

       
        int start = resp.indexOf("&lt;Last&gt;") + "&lt;Last&gt;".length();
        int end = resp.indexOf("&lt;/Last&gt;");

        if (start < "&lt;Last&gt;".length())
        {
            return "(unknown)";
        }
        String result = resp.substring(start, end);
        return result.equals("0.00") ? "(unknown)" : result;
    }

 
    public String getSOAPQuote(String sym)
    {
        String response = "";

        try
        {
            final URL url = new URL(Url);
            final String msg = createMessage(sym);
            HttpURLConnection http = setUphttpection(url, msg.length());
            writeRequest(msg, http);
             response = readResult(http);
          
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(response);
        return response;
    }

    
    private void writeRequest(String msg, HttpURLConnection http) throws IOException
    {
        OutputStream out = http.getOutputStream();
        out.write(msg.getBytes());
        out.close();
    }

    
    private HttpURLConnection setUphttpection(URL url, int length) throws IOException,
        ProtocolException
    {
        URLConnection connection = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)connection;
        http.setRequestProperty("Content-Length", String.valueOf(length));
        http.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        http.setRequestProperty("SOAPAction", "\"http://www.webserviceX.NET/GetQuote\"");
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setDoInput(true);
        return http;
    }

    
    private String readResult(HttpURLConnection con) throws IOException
    {
        InputStream ins = con.getInputStream();
        InputStreamReader isr123 = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr123);

     StringBuilder sb = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            sb.append(inputLine);
        }

        in.close();
        return sb.toString();
    }

   
    private String createMessage(String symbol)
    {
     StringBuilder message = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        message.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        message.append("  <soap:Body>");
        message.append("    <GetQuote xmlns=\"http://www.webserviceX.NET/\">");
        message.append("      <symbol>").append(symbol).append("</symbol>");
        message.append("    </GetQuote>");
        message.append("  </soap:Body>");
        message.append("</soap:Envelope>");
        return message.toString();
    }
}