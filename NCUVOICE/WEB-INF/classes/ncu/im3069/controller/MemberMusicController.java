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
import ncu.im3069.tools.JsonReader;


@WebServlet("/api/membermusic.do")
public class MemberMusicController extends HttpServlet{
	
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
        String email = jso.getString("email");
        String password = jso.getString("pwd");
        String name = jso.getString("name");
        
        /** 建立一個新的會員物件 */
        MemberMusic m = new MemberMusic(email, password, name);
        
        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
        if(email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
        /** 透過MemberHelper物件的checkDuplicate()檢查該會員電子郵件信箱是否有重複 */
        else if (!mmh.checkDuplicate(m)) {
            /** 透過MemberHelper物件的create()方法新建一個會員至資料庫 */
            JSONObject data = mmh.create(m);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 註冊會員資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'新增帳號失敗，此E-Mail帳號重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
    }
    
    
    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     * 
     */
//    public void doPut(HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException {
//        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
//        JsonReader jsr = new JsonReader(request);
//        JSONObject jso = jsr.getObject();
//        
//        /** 取出經解析到JSONObject之Request參數 */
//        int id = jso.getInt("id");
//        String email = jso.getString("email");
//        String password = jso.getString("password");
//        String name = jso.getString("name");
//
//        /** 透過傳入之參數，新建一個以這些參數之會員Member物件 */
//        Member m = new Member(id, email, password, name);
//        
//        /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
//        JSONObject data = m.update();
//        
//        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
//        JSONObject resp = new JSONObject();
//        resp.put("status", "200");
//        resp.put("message", "成功! 更新會員資料...");
//        resp.put("response", data);
//        
//        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
//        jsr.response(resp, response);
//    }

    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
            JsonReader jsr = new JsonReader(request);
            JSONObject jso = jsr.getObject();
          
            /** 取出經解析到JSONObject之Request參數 */
            int id = jso.getInt("member_id");
            MemberMusic m = new MemberMusic(id);

            /** 透過傳入之參數，新建一個以這些參數之會員Member物件 *        
            /** 透過Member物件的update()方法至資料庫更新該名會員資料，回傳之資料為JSONObject物件 */
            JSONObject data = m.updateStatus();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 更新會員資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }

    
	

}
