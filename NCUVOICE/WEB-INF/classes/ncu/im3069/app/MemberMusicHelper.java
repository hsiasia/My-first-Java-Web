package ncu.im3069.app;

import java.sql.*;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.util.DBMgr;

public class MemberMusicHelper {

	//TODO: sql指令那邊要重寫
	
	private MemberMusicHelper() {
		
	}
	
	/** 靜態變數，儲存MemberMusicHelper物件 */
	private static MemberMusicHelper mmh;
	
    /** 儲存JDBC資料庫連線 */
    private Connection conn = null;
    
    /** 儲存JDBC預準備之SQL指令 */
    private PreparedStatement pres = null;
	
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個MemberHelper物件
     *
     * @return the helper 回傳MemberHelper物件
     */
    public static MemberMusicHelper getHelper() {
        /** Singleton檢查是否已經有MemberHelper物件，若無則new一個，若有則直接回傳 */
        if(mmh == null) mmh = new MemberMusicHelper();
        
        return mmh;
    }
    /**
     * 透過會員編號（ID）刪除會員
     *
     * @param id 會員編號
     * @return the JSONObject 回傳SQL執行結果
     */
//    public JSONObject deleteByID(int id) {
//        /** 記錄實際執行之SQL指令 */
//        String exexcute_sql = "";
//        /** 紀錄程式開始執行時間 */
//        long start_time = System.nanoTime();
//        /** 紀錄SQL總行數 */
//        int row = 0;
//        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
//        ResultSet rs = null;
//        
//        try {
//            /** 取得資料庫之連線 */
//            conn = DBMgr.getConnection();
//            
//            /** SQL指令 */
//            String sql = "DELETE FROM `missa`.`members` WHERE `id` = ? LIMIT 1";
//            
//            /** 將參數回填至SQL指令當中 */
//            pres = conn.prepareStatement(sql);
//            pres.setInt(1, id);
//            /** 執行刪除之SQL指令並記錄影響之行數 */
//            row = pres.executeUpdate();
//
//            /** 紀錄真實執行的SQL指令，並印出 **/
//            exexcute_sql = pres.toString();
//            System.out.println(exexcute_sql);
//            
//        } catch (SQLException e) {
//            /** 印出JDBC SQL指令錯誤 **/
//            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
//        } catch (Exception e) {
//            /** 若錯誤則印出錯誤訊息 */
//            e.printStackTrace();
//        } finally {
//            /** 關閉連線並釋放所有資料庫相關之資源 **/
//            DBMgr.close(rs, pres, conn);
//        }
//
//        /** 紀錄程式結束執行時間 */
//        long end_time = System.nanoTime();
//        /** 紀錄程式執行時間 */
//        long duration = (end_time - start_time);
//        
//        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
//        JSONObject response = new JSONObject();
//        response.put("sql", exexcute_sql);
//        response.put("row", row);
//        response.put("time", duration);
//
//        return response;
//    }
    
    /**
     * 取回所有會員資料
     *
     * @return the JSONObject 回傳SQL執行結果與自資料庫取回之所有資料
     */
    public JSONObject getAll() {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
        MemberMusic m = null;
        /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_hw`.`member`";
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int member_id = rs.getInt("mem_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("pwd");
                int login_times = rs.getInt("login_times");
                int status = rs.getInt("status");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                m = new MemberMusic(member_id, email, password, name, login_times, status);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(m.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    /**
     * 透過會員編號（ID）取得會員資料
     *
     * @param id 會員編號
     * @return the JSON object 回傳SQL執行結果與該會員編號之會員資料
     */
    public JSONObject getByID(int id) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
    	MemberMusic m = null;
        /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_hw`.`member` WHERE `mem_id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                int member_id = rs.getInt("mem_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("pwd");
                int login_times = rs.getInt("login_times");
                int status = rs.getInt("status");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                m = new MemberMusic(member_id, email, password, name, login_times, status);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(m.getData());
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    public int getEmailById(String in_email) {
        /** 新建一個 Member 物件之 m 變數，用於紀錄每一位查詢回之會員資料 */
    	MemberMusic m = null;
        /** 用於儲存所有檢索回之會員，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        int member_id =0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_hw`.`member` WHERE `email` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, in_email);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該會員編號之資料，因此其實可以不用使用 while 迴圈 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                 member_id = rs.getInt("mem_id");

     
            }

            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */


        return member_id;

    }
    
    public boolean canLogin(String in_email, String in_password) {
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
    	try {
    		conn = DBMgr.getConnection();
    	//	String sql = "SELECT count(*) FROM `sa_hw`.`member` WHERE `email` = ?";
    		String sql = "SELECT count(*) FROM `sa_hw`.`member` WHERE `email` =? AND `pwd`=?";
    		/** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, in_email);
            pres.setString(2, in_password);
            rs = pres.executeQuery();
            rs.next();
            row = rs.getInt("count(*)");
            System.out.print(row);

            
    	}catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) { 
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
    	
    	return (row == 0) ? false : true;
    }
    /**
     * 取得該名會員之更新時間與所屬之會員組別
     *
     * @param m 一名會員之MemberMusic物件
     * @return the JSON object 回傳該名會員之更新時間與所屬組別（以JSONObject進行封裝）
     */
    
    public JSONObject getLoginTimesStatus(MemberMusic m) {
        /** 用於儲存該名會員所檢索之更新時間分鐘數與會員組別之資料 */
        JSONObject jso = new JSONObject();
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_hw`.`members` WHERE `id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, m.getID());
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該電子郵件之資料，因此其實可以不用使用 while迴圈 */
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
                int login_times = rs.getInt("login_times");
                int status = rs.getInt("status");
                /** 將其封裝至JSONObject資料 */
                jso.put("login_times", login_times);
                jso.put("status", status);
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        return jso;
    }
    
    //自己寫的
    public JSONObject getStatus(MemberMusic m) {
        /** 用於儲存該名會員所檢索之更新時間分鐘數與會員組別之資料 */
        JSONObject jso = new JSONObject();
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;

        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_hw`.`members` WHERE `id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, m.getID());
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            /** 正確來說資料庫只會有一筆該電子郵件之資料，因此其實可以不用使用 while迴圈 */
            while(rs.next()) {
                /** 將 ResultSet 之資料取出 */
                int status = rs.getInt("status");
                /** 將其封裝至JSONObject資料 */
                jso.put("status", status);
            }
            
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }

        return jso;
    }
    
    
    /**
     * 檢查該名會員之電子郵件信箱是否重複註冊
     *
     * @param m 一名會員之Member物件
     * @return boolean 若重複註冊回傳False，若該信箱不存在則回傳True
     */
    public boolean checkDuplicate(MemberMusic m){
        /** 紀錄SQL總行數，若為「-1」代表資料庫檢索尚未完成 */
        int row = -1;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT count(*) FROM `sa_hw`.`member` WHERE `email` = ?";
            
            /** 取得所需之參數 */
            String email = m.getEmail();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, email);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 讓指標移往最後一列，取得目前有幾行在資料庫內 */
            rs.next();
            row = rs.getInt("count(*)");
            System.out.print(row);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 
         * 判斷是否已經有一筆該電子郵件信箱之資料
         * 若無一筆則回傳False，否則回傳True 
         */
        return (row == 0) ? false : true;
    }
    /**
     * 建立該名會員至資料庫
     *
     * @param m 一名會員之Member物件
     * @return the JSON object 回傳SQL指令執行之結果
     */
    public JSONObject create(MemberMusic m) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `sa_hw`.`member`(`name`, `pwd`, `email`, `modified`, `created`, `login_times`, `status`)"
                    + " VALUES(?, ?, ?, ?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            String name = m.getName();
            String email = m.getEmail();
            String password = m.getPassword();
            int login_times = m.getLoginTimes();
            int status = m.getStatus();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, password);
            pres.setString(3, email);
            pres.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pres.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pres.setInt(6, login_times);
            pres.setInt(7, status);
            
            /** 執行新增之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();
            
            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }

        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("time", duration);
        response.put("row", row);

        return response;
    }
    
    /**
     * 更新一名會員之會員資料
     *
     * @param m 一名會員之Member物件
     * @return the JSONObject 回傳SQL指令執行結果與執行之資料
     */
    public JSONObject update(MemberMusic m) {
        /** 紀錄回傳之資料 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `sa_hw`.`member` SET `name` = ? ,`password` = ? , `modified` = ? WHERE `email` = ?";
            /** 取得所需之參數 */
            String name = m.getName();
            String email = m.getEmail();
            String password = m.getPassword();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1, name);
            pres.setString(2, password);
            pres.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            pres.setString(4, email);
            /** 執行更新之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    }
    /**
     * 更新會員更新資料之分鐘數
     *
     * @param m 一名會員之Member物件
     */
    public void updateLoginTimes(MemberMusic m) {
        /** 更新時間之分鐘數 */
        int new_times = m.getLoginTimes();
        
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `sa_hw`.`member` SET `login_times` = ? WHERE `id` = ?";
            /** 取得會員編號 */
            int id = m.getID();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, new_times);
            pres.setInt(2, id);
            /** 執行更新之SQL指令 */
            pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
    }
    
    /**
     * 更新會員之會員組別
     *
     * @param m 一名會員之Member物件
     * @param status 會員組別之字串（String）
     */
    public JSONObject updateStatus(MemberMusic m) {      
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "Update `sa_hw`.`member` SET status = ? WHERE mem_id = ?";
            /** 取得會員編號 */
            int id = m.getID();
            //如果0 改1
            int sts = m.getStatus();
            
            sts = 1;
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, sts);
            pres.setInt(2, id);
            /** 執行更新之SQL指令 */
            pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(pres, conn);
        }
        return null;
    }
    
    
}
