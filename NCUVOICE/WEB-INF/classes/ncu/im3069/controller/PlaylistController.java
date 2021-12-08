package ncu.im3069.controller;


import java.io.IOException;
import org.json.JSONObject;

import ncu.im3069.app.*;
import ncu.im3069.tools.JsonReader;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/api/playlist.do")
public class PlaylistController extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	private PlaylistHelper plh =  PlaylistHelper.getHelper();

	private SongHelper sh =  SongHelper.getHelper();
	
	public PlaylistController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//新增歌曲
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
            Cookie cookies[] = request.getCookies();
		
		    String str=null;
		
		    for(Cookie c : cookies) {
			if(c.getName().equals("user_id")){
				response.addCookie(c);
				str=c.getValue();
			  }
		    }
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */

	        int songid = jso.getInt("song_id");
	        int memId = Integer.parseInt(str);
	        
	        /** 建立一個新的歌單物件 */
	        Playlist pl = new Playlist(songid, memId);
	        
	        JSONObject data = plh.create(pl);
	            
	            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	            JSONObject resp = new JSONObject();
	            resp.put("status", "200");
	            resp.put("message", "成功! 增加歌曲...");
	            resp.put("response", data);
	            
	            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	            jsr.response(resp, response);
	}
	
	//取得歌單內容
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
		/** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
		JsonReader jsr = new JsonReader(request);

        /** 取出經解析到 JsonReader 之 Request 參數 */
        
        String searchID = jsr.getParameter("search");
        
       
        //如果有進行搜尋，取得搜尋的歌曲資料
       // if (searchID.isEmpty()) {

            JSONObject resp = new JSONObject();
            
            JSONObject query = plh.getPlaylistByMemId (str);
              resp.put("status", "200");
              resp.put("message", "所有歌單內容取得成功");
              resp.put("response", query);
         


             jsr.response(resp, response);
        //}
        
        //else {
        	
       // }
        
      	}
	//刪除歌曲
	 public void doDelete(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {
		        Cookie cookies[] = request.getCookies();
			
			    String str=null;
			
			    for(Cookie c : cookies) {
				if(c.getName().equals("user_id")){
					response.addCookie(c);
					str=c.getValue();
				  }
			    }
			    System.out.print(str);
		 
		 
		        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		        JsonReader jsr = new JsonReader(request);
		        JSONObject jso = jsr.getObject();
		        
		        /** 取出經解析到JSONObject之Request參數 */
		        int song_id = jso.getInt("id");
		        
		        
		        JSONObject query = plh.deleteByID(song_id,str);
		        
		        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
		        JSONObject resp = new JSONObject();
		        resp.put("status", "200");
		        resp.put("message", "歌曲移除成功！");
		        resp.put("response", query);

		        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
		        jsr.response(resp, response);
		    }
}
