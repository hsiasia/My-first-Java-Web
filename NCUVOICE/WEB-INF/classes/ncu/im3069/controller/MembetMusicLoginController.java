package ncu.im3069.controller;


import java.io.*;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

import org.json.*;

import ncu.im3069.tools.JsonReader;

import org.json.JSONObject;

import ncu.im3069.app.MemberMusic;
import ncu.im3069.app.MemberMusicHelper;
import ncu.im3069.util.DBMgr;
import ncu.im3069.tools.JsonReader;


@WebServlet("/api/membermusiclogin.do")
public class MembetMusicLoginController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private MemberMusicHelper mmh =  MemberMusicHelper.getHelper();
	
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        String user_email = jso.getString("email");
        String user_password = jso.getString("password");
        
        
        if(mmh.canLogin(user_email, user_password)) {
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        	int member_id = 0;
            JSONObject resp = new JSONObject();
            member_id = mmh.getEmailById(user_email);
            resp.put("status", "200");
            resp.put("message", "成功! 帳號密碼皆正確 memberid = " + member_id);
            
 
            Cookie ck = new Cookie("user_id",String.valueOf(member_id));
            ck.setMaxAge(24*24*365);
            response.addCookie(ck);
         //   resp.put("response", query);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        	}else {
                String resp = "{\"status\": \'400\', \"message\": \'帳號或是密碼錯了！\', \'response\': \'\'}";
                /** 透過JsonReader物件回傳到前端（以字串方式） */
                jsr.response(resp, response);
        	}
        	
        }
        

        
        /** 建立一個新的會員物件 */
       // MemberMusic m = new MemberMusic(email, password, name);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */

    
}
