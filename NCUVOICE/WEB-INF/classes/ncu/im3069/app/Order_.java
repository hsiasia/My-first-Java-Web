package ncu.im3069.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Order_ {

    /** id，訂單編號 */
    private int order_id;

    /** first_name，會員姓名 */
    private int mem_id;

    /** last_name，會員姓 */
    private String approch;

    /** modify，訂單修改時間 */
    private Timestamp order_time;

    /**
     * 實例化（Instantiates）一個新的（new）Order 物件<br>
     * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
     *
     * @param first_name 會員名
     * @param last_name 會員姓
     * @param email 會員電子信箱
     * @param address 會員地址
     * @param phone 會員姓名
     */
    public Order_(int mem_id, String approch) {
        this.mem_id = mem_id;
        this.approch = approch;
        this.order_time = Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * 取得訂單編號
     *
     * @return int 回傳訂單編號
     */
    public int getOrderId() {
        return this.order_id;
    }

    /**
     * 取得訂單會員的名
     *
     * @return String 回傳訂單會員的名
     */
    public int getMemId() {
        return this.mem_id;
    }

    /**
     * 取得訂單會員的姓
     *
     * @return String 回傳訂單會員的姓
     */
    public String getApproch() {
        return this.approch;
    }

    /**
     * 取得訂單創建時間
     *
     * @return Timestamp 回傳訂單創建時間
     */
    public Timestamp getOrderTime() {
        return this.order_time;
    }

    /**
     * 取得訂單基本資料
     *
     * @return JSONObject 取得訂單基本資料
     */
    public JSONObject getOrderData() {
        JSONObject jso = new JSONObject();
        jso.put("order_id", getOrderId());
        jso.put("mem_id", getMemId());
        jso.put("approch", getApproch());
        jso.put("order_time", getOrderTime());

        return jso;
    }
}
