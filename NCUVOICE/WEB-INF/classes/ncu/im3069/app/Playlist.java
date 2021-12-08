package ncu.im3069.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

    public class Playlist {
	
	//歌單內有的資料 變數宣告
	private String name;
	
	private int songID;
	
	private int MemID;
	
	private ArrayList<Song> playlist = new ArrayList<Song>();
	
	private PlaylistHelper plh = PlaylistHelper.getHelper();
	
	public Playlist(String name , int songid , int memid) {
		this.name = name;
		this.songID = songid;
		this.MemID = memid;
	}
	public Playlist(int songid , int memid) {
		this.songID = songid;
		this.MemID = memid;
	}
	
	
	
	//取得歌單名字
	public String getPlaylistName() {
		return this.name;
	}
	
	//取得歌曲ID
	public int getSongID() {
		return this.songID;
	}
	
	//取得該會員ID
	public int getMemID() {
		return this.MemID;
	}
	
	//增加歌曲
	public void addSong(Song song) {
		this.playlist.add(song);
	}
	
	//取得歌單內容
	public ArrayList<Song> getplaylist(){
		return this.playlist;
	}
	
	
	//取得歌單基本資料
	public JSONObject getplaylistData() {
		JSONObject jso = new JSONObject();
		jso.put("playlist_name", getPlaylistName());
		jso.put("playlist_SongID", getSongID());
		jso.put("playlist_MemID", getMemID());
		return jso;
	}
	
	

}
