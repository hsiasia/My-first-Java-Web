package ncu.im3069.app;

import java.sql.*;

import java.util.*;

import org.json.*;

import ncu.im3069.util.DBMgr;

public class OrderHelper_ {
    
    private static OrderHelper_ oh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    private OrderHelper_() {
    }
    
    public static OrderHelper_ getHelper() {
        if(oh == null) oh = new OrderHelper_();
        
        return oh;
    }
    
    public JSONObject create(Order_ order) {
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        long start_time = System.nanoTime();
        int row = 0;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "INSERT INTO `sa_hw`.`order`(`order_id`, `mem_id`, `approach`, `orderTime`)"
                    + " VALUES(?, ?, ?, ?)";
            
            /** 取得所需之參數 */
            int order_id = order.getOrderId();
            int mem_id = order.getMemId();
            String approch = order.getApproch();
            Timestamp order_time = order.getOrderTime();
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pres.setInt(1, order_id);
            pres.setInt(2, mem_id);
            pres.setString(3, approch);
            pres.setTimestamp(4, order_time);
            
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
