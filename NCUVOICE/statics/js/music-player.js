let previous = document.querySelector('#pre');
let play = document.querySelector('#play');
let next = document.querySelector('#next');
let title = document.querySelector('#title');
let recent_volume= document.querySelector('#volume');
let slider = document.querySelector('#duration_slider');
let show_duration = document.querySelector('#show_duration');
let track_image = document.querySelector('#track_image');
//let auto_play = document.querySelector('#auto');
let present = document.querySelector('#present');
let total = document.querySelector('#total');
let artist = document.querySelector('#artist');
let info = document.querySelector('#info');
let add = document.querySelector('#add');


let song_id;
let timer;
//let autoplay = 0;

let no =parseInt(localStorage.getItem("nowplay"));
let index_no = no - 1;
let Playing_song = false;

//create a audio Element
let track = document.createElement('audio');


//All songs list
let All_song = [
	{
		song_id :0,
		name: "Memories",
		path: "music/Memories.mp3",
		img: "statics/img/Memories.jpg",
		singer: "Maroon 5",
		info: "2019  |  你好  |  3:15"
	  },
	  
	{
		song_id :1,
		name: "Hymn For The Weekend",
		path: "music/Hymn.mp3",
		img: "statics/img/Hymn.jpg",
		singer: "ColdPlay",
		info: "2016  | hi |  3:41"
	  },
	  {
		song_id :2,
		name: "See You Again",
		path: "music/SYA.mp3",
		img: "statics/img/SYA.jpg",
		singer: "Wiz Khalifa ft. Charlie Puth",
		info: "2000  |  你好  |  我是男的"
	  },
	
	{
		song_id :3,
		name: "Downtown",
		path: "music/Downtown.mp3",
		img: "statics/img/Downtown.jpg",
		singer: "MACKLEMORE & RYAN LEWIS",
		info: "2000  |  你好  |  我是男的"
	  },
	  {
		song_id :4,
		name: "Black and Yellow",
		path: "music/BAY.mp3",
		img: "statics/img/BAY.jpg",
		singer: "Wiz Khalifa ft. Snoop Dogg, Juicy J",
		info: "2000  |  你好  |  我是男的"
	  },

  


  

];


// All functions


// function load the track
function load_track(index_no){
	clearInterval(timer);
	reset_slider();



	track.src = All_song[index_no].path;
	 title.innerHTML = All_song[index_no].name;	
	track_image.src = All_song[index_no].img;
	 artist.innerHTML = All_song[index_no].singer;
	 info.innerHTML =  All_song[index_no].info;
     track.load();

	timer = setInterval(range_slider ,1000);
    console.log(index_no);

}

load_track(index_no);


//mute sound function
function mute_sound(){
	track.volume = 0;
	volume.value = 0;
	//volume_icon.innerHTML = '<i class="fas fa-volume-mute" aria-hidden="true">></i>';
}


// checking.. the song is playing or not
 function justplay(){
 	if(Playing_song==false){
 		playsong();

 	}else{
 		pausesong();
 	}
 }


// reset song slider
 function reset_slider(){
 	slider.value = 0;
 }

// play song
function playsong(){
  Playing_song = true;
  track.play();
  play.innerHTML = '<i class="fa fa-pause" aria-hidden="true"></i>';
}

//pause song
function pausesong(){
	Playing_song = false;
	track.pause();
	//Playing_song = false;
	play.innerHTML = '<i class="fa fa-play" aria-hidden="true"></i>';
}


// next song
function next_song(){
	if(index_no < All_song.length - 1){
		index_no += 1;
		load_track(index_no);
		playsong();
	}else{
		index_no = 0;
		load_track(index_no);
		playsong();

	}
}


// previous song
function previous_song(){
	if(index_no > 0){
		index_no -= 1;
		load_track(index_no);
		playsong();

	}else{
		index_no = All_song.length-1;
		load_track(index_no);
		playsong();
	}
}


// change volume
function volume_change(){
	//volume_show.innerHTML = recent_volume.value;
	track.volume = recent_volume.value / 100;
}

// change slider position 
function change_duration(){
	slider_position = track.duration * (slider.value / 100);
	track.currentTime = slider_position;
}

// autoplay function
function autoplay_switch(){
	if (autoplay==1){
       autoplay = 0;
       auto_play.style.background = "rgba(255,255,255,0.2)";
	}else{
       autoplay = 1;
       auto_play.style.background = "#FF8A65";
	}
}


// function range_slider(){
// 	let position = 0;
        
//         // update slider position
// 		if(!isNaN(track.duration)){
// 		   position = track.currentTime * (100 / track.duration);
// 		   slider.value =  position;
// 	      }

       
//        // function will run when the song is over
//        if(track.ended){
//        	 play.innerHTML = '<i class="fa fa-play" aria-hidden="true"></i>';
//            if(autoplay==1){
// 		       index_no += 1;
// 		       load_track(index_no);
// 		       playsong();
//            }
// 	    }
// 	 }
	 