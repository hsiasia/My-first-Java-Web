package ncu.im3069.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;
import ncu.im3069.util.DBMgr;

public class PlaylistHelper {
	
	private PlaylistHelper() {}
	
	private static PlaylistHelper plh;
	private Connection conn = null;
    private PreparedStatement pres = null;

public static PlaylistHelper getHelper() {
        
        if(plh == null) plh = new PlaylistHelper();
        
        return plh;
    }
    
public JSONObject deleteByID(int id,String memID) {
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
            String sql = "DELETE FROM `sa_hw`.`playlist` WHERE playlist_MemID = "+memID+" AND playlist_SongID = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
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
            DBMgr.close(rs, pres, conn);
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

        return response;
    }
    
public JSONObject getPlaylistByMemId (String Mem_id){
        /** 新建一個 Playlist 物件之 pl 變數*/
        Playlist pl = null;
        /** 用於儲存所有檢索回之歌單內容，以JSONArray方式儲存 */
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
            String sql = "SELECT * FROM `sa_hw`.`playlist` WHERE playlist_MemID = " + Mem_id;
            
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
                String playlist_name = rs.getString("playlist_name");
                int playlist_SongID = rs.getInt("playlist_SongID");
                int playlist_MemID = rs.getInt("playlist_MemID");
                
                /** 將每一筆會員資料產生一名新Member物件 */
                pl = new Playlist(playlist_name, playlist_SongID, playlist_MemID);
                /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
                jsa.put(pl.getplaylistData());
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

public JSONObject getPlaylist (){
    /** 新建一個 Playlist 物件之 pl 變數*/
    Playlist pl = null;
    /** 用於儲存所有檢索回之歌單內容，以JSONArray方式儲存 */
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
        String sql = "SELECT * FROM `sa_hw`.`playlist`";
        
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
            String playlist_name = rs.getString("playlist_name");
            int playlist_SongID = rs.getInt("playlist_SongID");
            int playlist_MemID = rs.getInt("playlist_MemID");
            
            /** 將每一筆會員資料產生一名新Member物件 */
            pl = new Playlist(playlist_name, playlist_SongID, playlist_MemID);
            /** 取出該名會員之資料並封裝至 JSONsonArray 內 */
            jsa.put(pl.getplaylistData());
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


public JSONObject create(Playlist pl) {
    /** 記錄實際執行之SQL指令 */
    String exexcute_sql = "";
    /** 紀錄程式開始執行時間 */
    long start_time = System.nanoTime();
    /** 紀錄SQL總行數 */
    int row = 0;
    
    try {
        /** 取得資料庫之連線 */
        conn = DBMgr.getConnection();
        int songid = pl.getSongID();
        int memid  = pl.getMemID();
        /** SQL指令 */
        String sql = "INSERT INTO `playlist`(`playlist_name`, `playlist_SongID`, `playlist_MemID`) VALUES ('','"+songid+"','"+memid+"')";
        
        /** 取得所需之參數 */

        
        /** 將參數回填至SQL指令當中 */
        pres = conn.prepareStatement(sql);

        
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
}
