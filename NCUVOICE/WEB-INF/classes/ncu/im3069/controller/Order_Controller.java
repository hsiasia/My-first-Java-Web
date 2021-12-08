package ncu.im3069.controller;

import java.io.IOException;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import org.json.*;

import ncu.im3069.app.Order_;
import ncu.im3069.app.MemberMusic;
import ncu.im3069.app.MemberMusicHelper;
import ncu.im3069.app.OrderHelper_;
import ncu.im3069.tools.JsonReader;

import javax.servlet.annotation.WebServlet;

@WebServlet("/api/order_.do")
public class Order_Controller extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** oh，OrderHelper 之物件與 order 相關之資料庫方法（Sigleton） */
	private OrderHelper_ oh =  OrderHelper_.getHelper();
	private MemberMusicHelper mmh =  MemberMusicHelper.getHelper();

    public Order_Controller() {
        super();
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
		
		HttpSession session =request.getSession();
		Cookie cookies[] = request.getCookies();
		
		String str=null;
		
		for(Cookie c : cookies) {
			if(c.getName().equals("user_id")){
				str=c.getValue();
			}
		}
		
		int mem_id = Integer.parseInt(str);
		
	    /** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();

        /** 取出經解析到 JSONObject 之 Request 參數 */
        //String username = jso.getString("username");
        String approach = jso.getString("paymentMethod");

//        Member m = mh.getByName(username);
//        int mem_id = m.getID();
        
        /** 建立一個新的訂單物件 */
        Order_ od = new Order_(mem_id, approach);
        MemberMusic m = new MemberMusic(mem_id);
        
        
        JSONObject dataa = m.updateStatus();
        /** 透過 orderHelper 物件的 create() 方法新建一筆訂單至資料庫 */
        JSONObject data = oh.create(od);

        /** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "訂單新增成功！");
        resp.put("response", data);
        resp.put("response", dataa);

        /** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
        jsr.response(resp, response);
	}

}
