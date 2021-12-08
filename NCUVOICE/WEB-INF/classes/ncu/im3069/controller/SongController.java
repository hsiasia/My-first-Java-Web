package ncu.im3069.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import org.json.*;

import ncu.im3069.app.Song;
import ncu.im3069.app.SongHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/song.do")
public class SongController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SongHelper sh =  SongHelper.getHelper();

    public SongController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);

        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String name = jsr.getParameter("name");
        String id_list = jsr.getParameter("id_list");
        
        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回購物車內產品之資料，否則代表要取回全部資料庫內產品之資料 */
        if (!name.isEmpty()&id_list.isEmpty()) {
          JSONObject query = sh.getByName(name);
          resp.put("status", "200");
          resp.put("message", "所有指定歌曲資料取得成功1");
          resp.put("response", query);
        }
        else if (!id_list.isEmpty() & name.isEmpty()) {
            JSONObject query = sh.getByIdList(id_list);
            resp.put("status", "200");
            resp.put("message", "所有指定歌曲資料取得成功2");
            resp.put("response", query);
         }
        else if (id_list.isEmpty() & name.isEmpty()){
          JSONObject query = sh.getAll();

          resp.put("status", "200");
          resp.put("message", "所有歌曲資料取得成功");
          resp.put("response", query);
        }

        jsr.response(resp, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session =request.getSession();
		Cookie cookies[] = request.getCookies();
		
		String str=null;
		
		for(Cookie c : cookies) {
			if(c.getName().equals("user_id")){
				str=c.getValue();
			}
		}
		
		int mem_id = Integer.parseInt(str);    
		
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject jso = jsr.getObject();
	        
	        /** 取出經解析到JSONObject之Request參數 */
            String name = jso.getString("name");
            String cover = jso.getString("cover");
            String artist = jso.getString("artist");
            String album = jso.getString("album");
            String genre = jso.getString("genre");
	       
	        /** 建立一個新的會員物件 */
            Song s = new Song(name, cover, artist, album, genre, mem_id);
	        
	        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
	        if(name.isEmpty() || cover.isEmpty() ) {
	            /** 以字串組出JSON格式之資料 */
	            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
	            /** 透過JsonReader物件回傳到前端（以字串方式） */
	            jsr.response(resp, response);
	        }
	        /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
	        else {
	            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
	            JSONObject data = sh.create(s);
	            
	            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	            JSONObject resp = new JSONObject();
	            resp.put("status", "200");
	            resp.put("message", "成功上傳歌曲資料");
	            resp.put("response", data);
	            
	            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	            jsr.response(resp, response);
	        }
	    }
}
