package ncu.im3069.app;

//import java.util.Calendar;

import org.json.*;

public class MemberMusic {
	
	private int mem_id;
	
	private String email;
	
	private String name;
	
	private String password;
	
	private int login_times;
	
	private int status;
	
	private MemberMusicHelper mmh =  MemberMusicHelper.getHelper();
//	要改member helper
	
	
    public MemberMusic(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        update();
    }
    
    public MemberMusic(int id, String email, String password, String name) {
        this.mem_id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        /** 取回原有資料庫內該名會員之更新時間分鐘數與組別 */
        getLoginTimesStatus();
        /** 計算會員之組別 */
  //      calcAccName();
    }
    public MemberMusic(int id, String email, String password, String name, int login_times, int status) {
        this.mem_id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.login_times = login_times;
        this.status = status;
    }
    
    public MemberMusic(int id) {
        this.mem_id = id;

   }
    
    public int getID() {
    	return this.mem_id;
    }
    
    public String getEmail() {
    	return this.email;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public int getLoginTimes() {
        return this.login_times;
    }
    
    
    public int getStatus() {
    	return this.status;
    }
    
    public JSONObject update() {
        /** 新建一個JSONObject用以儲存更新後之資料 */
        JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間）之分鐘數 */
      //  Calendar calendar = Calendar.getInstance();
//        this.login_times = calendar.get(Calendar.MINUTE);
        /** 計算帳戶所屬之組別 */
 //       calcAccName();
        
        /** 檢查該名會員是否已經在資料庫 */
        if(this.mem_id != 0) {
            /** 若有則將目前更新後之資料更新至資料庫中 */
            mmh.updateLoginTimes(this);
            /** 透過MemberHelper物件，更新目前之會員資料置資料庫中 */
            data = mmh.update(this);
        }
        
        return data;
    }
    
    public JSONObject getData() {
        /** 透過JSONObject將該名會員所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("mem_id", getID());
        jso.put("name", getName());
        jso.put("email", getEmail());
        jso.put("pwd", getPassword());
        jso.put("login_times", getLoginTimes());
        jso.put("status", getStatus());
        
        return jso;
    }
    
// TODO:   MemberHelper那裏要改型態
    private void getLoginTimesStatus() {
        /** 透過MemberHelper物件，取得儲存於資料庫的更新時間分鐘數與會員組別 */
        JSONObject data = mmh.getLoginTimesStatus(this);
        /** 將資料庫所儲存該名會員之相關資料指派至Member物件之屬性 */
        this.login_times = data.getInt("login_times");
        this.status = data.getInt("status");
    }
    
    public JSONObject updateStatus() {
        JSONObject data = new JSONObject();
        /** 取得更新資料時間（即現在之時間）之分鐘數 */
        
       if(this.status == 0) {
        data = mmh.updateStatus(this);
       }
        
        return data;
 }

    
    
//    private void calcAccName() {
//        /** 計算目前分鐘數為偶數或奇數 */
//        String curr_status = (this.login_times % 2 == 0) ? "偶數會員" : "奇數會員";
//        /** 將新的會員組別指派至Member之屬性 */
//        this.status = curr_status;
//        /** 檢查該名會員是否已經在資料庫，若有則透過MemberHelper物件，更新目前之組別狀態 */
//        if(this.id != 0) mh.updateStatus(this, curr_status);
//    }
//}

//    
//    
    
    
    
}
