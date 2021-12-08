package ncu.im3069.controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import org.json.*;

import ncu.im3069.app.MemberMusicHelper;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/cookie.do")
public class CookieController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private MemberMusicHelper mmh =  MemberMusicHelper.getHelper();
	
    public CookieController() {
        super();
    }

    /**
     * 處理 Http Method 請求 GET 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		HttpSession session =request.getSession();
		Cookie cookies[] = request.getCookies();
		
		String str=null;
		
		for(Cookie c : cookies) {
			if(c.getName().equals("user_id")){
				response.addCookie(c);
				str=c.getValue();
			}
		}
		
		int mem_id = Integer.parseInt(str);
		
		/** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        
        JSONObject query = mmh.getByID(mem_id);
        
        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "Cookie取得成功");
        resp.put("response", query);

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}

    /**
     * 處理 Http Method 請求 POST 方法（新增資料）
     *
     * @param request Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
     * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到 JSONObject 之 Request 參數 */
        String song_id = jso.getString("song_id");
        
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "歌曲Cookie新增成功！");
        
        Cookie ck = new Cookie("song_id",String.valueOf(song_id));
        ck.setMaxAge(24*24*365);
        response.addCookie(ck);

        jsr.response(resp, response);
	}

}
