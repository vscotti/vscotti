function setTrackCookie(e){ 
	if (window.event) 
		e = window.event; 
	var srcEl = e.srcElement? e.srcElement : e.target;
        if(srcEl.id==null||srcEl.id=='') 
		srcEl = (srcEl.parentElement?srcEl.parentElement:srcEl.parentNode);
        var track = srcEl.id;
        setTrackCookieByID(track);
}

function getTrackCookieHme(){
  if(getCookieValue("clicked")== null || getCookieValue("clicked")=="0"){	
  	var value = getCookieValue("track_info");
  	if(value!=null){
  		arrayKeys = value.split(":");   	
  		var out = "/jm/ml.track.me?save_ck=N";
  		for (i=1; i < arrayKeys.length; i++)
    		out += "&k"+i+"="+arrayKeys[i];
		var img = new Image().src = out;
	  	setCookie("clicked","1",null);
  	}
  }
}

function setTrackCookieByID(track){
	if (track != null && track.indexOf(":")!=-1){
	   arr = track.split("$");
  	   trackID = arr[0];
  	   if(document.home_track_id && document.home_track_id!='')
  	       trackID=document.home_track_id+':'+trackID;
   	   setCookie("track_info",document.track_site_id+":"+trackID,null);
    	   setCookie("clicked","0",null);
        }
}

function keyDownSearch(e) {
  k = (document.all) ? window.event.keyCode : e.which;
  var asWordInput;
  if (document.fheader && document.fheader.as_word) {
  	asWordInput = document.fheader.as_word;
  } else {
  	asWordInput = document.getElementById("as_word");
  }
  if (k == 13 && asWordInput.value.length > 0) {
    setTrackCookieByID("MENU:BUSCAD");
  }
}

function keyDownSearch2(e) {
  k = (document.all) ? window.event.keyCode : e.which;
  if (k == 13 && document.fheader2.as_word.value.length > 0) {
    setTrackCookieByID("CATEG:BUSCAD");
  }
}
