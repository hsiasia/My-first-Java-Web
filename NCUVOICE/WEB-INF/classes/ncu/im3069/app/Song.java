package ncu.im3069.app;

import org.json.*;

public class Song {

    /** id，會員編號 */
    private int id;
    
    /** id，會員編號 */
    private String name;

    /** id，會員編號 */
    private String cover;

    /** id，會員編號 */
    private String artist;

    /** id，會員編號 */
	private String album;
	
	/** id，會員編號 */
	private int duration;
	
	/** id，會員編號 */
	private String genre;
	
	private int mem_id;
	
    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param id 產品編號
     */
	public Song(int id) {
		this.id = id;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於新增產品時
     *
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     */
	public Song(int id, String name, String artist, int duration, String genre) {
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.duration = duration;
		this.genre = genre;
	}

    /**
     * 實例化（Instantiates）一個新的（new）Product 物件<br>
     * 採用多載（overload）方法進行，此建構子用於修改產品時
     *
     * @param id 產品編號
     * @param name 產品名稱
     * @param price 產品價格
     * @param image 產品圖片
     * @param describe 產品敘述
     */
	
	/*post*/
	public Song(String name, String cover, String artist, String album, String genre, int mem_id) {
		this.name = name;
		this.cover = cover;
		this.artist = artist;
		this.album = album;
		this.genre = genre;
		this.mem_id = mem_id;
	}
	
	/*get*/
	public Song(int id,String name,String cover,String artist,String album,int duration,String genre,int mem_id) {
		this.id = id;
		this.name = name;
		this.cover = cover;
		this.artist = artist;
		this.album = album;
		this.duration = duration;
		this.genre = genre;
		this.mem_id = mem_id;
	}
	

    /**
     * 取得歌曲編號
     *
     * @return int 回傳歌曲編號
     */
	public int getID() {
		return this.id;
	}

    /**
     * 取得歌曲名稱
     *
     * @return String 回傳歌曲名稱
     */
	public String getName() {
		return this.name;
	}

    /**
     * 取得歌曲上傳者
     *
     * @return String 回傳歌曲上傳者
     */
	public String getCover() {
		return this.cover;
	}

    /**
     * 取得歌曲歌手
     *
     * @return String 回傳歌曲歌手
     */
	public String getArtist() {
		return this.artist;
	}

    /**
     * 取得歌曲專輯
     *
     * @return String 回傳歌曲專輯
     */
	public String getAlbum() {
		return this.album;
	}
    /**
     * 取得歌曲時間
     *
     * @return int 回傳歌曲時間
     */
	public int getDuration() {
		return this.duration;
	}
    /**
     * 取得歌曲風格
     *
     * @return String 回傳歌曲風格
     */
	public String getGenre() {
		return this.genre;
	}
	
	public int getMemid() {
		return this.mem_id;
	}

	
    /**
     * 取得歌曲資訊
     *
     * @return JSONObject 回傳歌曲資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項產品所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("song_id", getID());
        jso.put("name", getName());
        jso.put("cover", getCover());
    	jso.put("artist", getArtist());
    	jso.put("album", getAlbum());
    	jso.put("duration", getDuration());
    	jso.put("genre", getGenre());
    	jso.put("mem_id", getMemid());

        return jso;
    }
}
