package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Servlet implementation class AccessTokenServlet
 */
@SuppressWarnings("deprecation")
@WebServlet("/AccessTokenServlet")
public class AccessTokenServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");
		String clientId = "7e9e9ddfeca347c5867ef1d0dd8bdbd5";
		String redirectURI = "http://localhost:8080/InstagramApp/";
		
		if(code != null) {
			String clientSecret = "e18348362e3f4248aa6c66a651f12ab7";
			JSONObject profile = getTokenContent(clientId, clientSecret, redirectURI, code);
			String accessToken = profile.getString("access_token");
			request.setAttribute("accessToken", accessToken);
			request.getRequestDispatcher("page/index.jsp").forward(request, response);
		} else {
			String url = "https://api.instagram.com/oauth/authorize/"
					+ "?client_id="+clientId+""
					+ "&redirect_uri="+redirectURI+"&response_type=code&scope=basic+likes+comments+follower_list+relationships+public_content";
			response.sendRedirect(url);
		}
	}
	
	@SuppressWarnings("resource")
	public JSONObject getTokenContent(String clientID, String clientSecret, String redirectURI, String code) throws IOException {
		HttpClient httpclient;
	    HttpPost httppost;
	    ArrayList<NameValuePair> postParameters;
	    httpclient = new DefaultHttpClient();
	    httppost = new HttpPost("https://api.instagram.com/oauth/access_token");

	    postParameters = new ArrayList<NameValuePair>();
	    postParameters.add(new BasicNameValuePair("client_id", clientID));
	    postParameters.add(new BasicNameValuePair("client_secret", clientSecret));
	    postParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
	    postParameters.add(new BasicNameValuePair("redirect_uri", redirectURI));
	    postParameters.add(new BasicNameValuePair("code", code));

	    httppost.setEntity(new UrlEncodedFormEntity(postParameters));

	    HttpResponse response = httpclient.execute(httppost);
	    
	    String json_string = EntityUtils.toString(response.getEntity());
	    JSONObject jsonObject = new JSONObject(json_string);
	    return jsonObject;
	}
}
