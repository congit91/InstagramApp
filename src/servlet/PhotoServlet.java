package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class PhotoServlet
 */
@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String accessToken = request.getParameter("accessToken");
		// String accessToken =
		// "2316458769.1fb234f.42d1aea0c4364f99a51b632abbe1f155";
		if(username != null && accessToken != null) {
			String userId = getUserIdByUsername(username, accessToken);
			request.setAttribute("userId", userId);
			request.setAttribute("username", username);
			request.setAttribute("accessToken", accessToken);
			request.getRequestDispatcher("page/index.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings({ "resource" })
	public String getUserIdByUsername(String username, String accessToken) throws IOException {
		String url = "https://api.instagram.com/v1/users/search?q=" + username + "&access_token=" + accessToken;
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject jsonObject = new JSONObject(jsonText);
			JSONArray users = jsonObject.getJSONArray("data");
			for (int i = 0; i < users.length(); i++) {
				String usernameData = users.getJSONObject(i).getString("username");
				if (username.equals(usernameData)) {
					return users.getJSONObject(i).getString("id");
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			is.close();
		}
		return null;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
