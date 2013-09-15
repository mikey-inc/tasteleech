(function(){
  //Really feel like im reinventing something here...
  var UIFrontedOrderedList = this.UIFrontedOrderedList = function(aJqListHandle, aRefreshFn){
    var embeddedList = [];

    this.sort = function(sortFn){
      embeddedList.sort(sortFn);
    };

    this.currentLength = function(){
      return embeddedList.length;
    };

    this.getAt = function(index){
      if(index >= embeddedList.length) return null;
      return embeddedList[index];
    };

    this.push = function(addMe){
      //console.log("Push getting called");
      embeddedList.push(addMe);
      this.refresh();
    };

    this.pushDeferred = function(addMe){
      embeddedList.push(addMe);
    };

    this.splice = function(ndx, offset, value){
      //#FIXME this is so inefficient it hurts my face and brain
      if(value){
        embeddedList.splice(ndx, offset, value);
      } else {
        embeddedList.splice(ndx, offset);
      }
      this.refresh();
    };

    this.spliceDeferred = function(ndx, offset, value){
      if(value){
        embeddedList.splice(ndx, offset, value);
      } else {
        embeddedList.splice(ndx, offset);
      }
    };

    this.slice = function(start, end){
      return embeddedList.slice(start, end);
    };

    //Should either change the name of this to UIFrontedList or make this take advantage of sort to find faster
    this.indexOf = function(key){
      //console.log("indexOf UIFrontedOrderedList called");
      //console.log("Looking for index in list: " + key + " / " + JSON.stringify(embeddedList));
      var foundNdx = embeddedList.indexOf(key);
      //#FIXME this is DISGUSTING
      if(foundNdx == -1){
        for(var i in embeddedList){
          if(embeddedList[i] == key)return i;
        }
      }
      //console.log("Found: " + foundNdx);
      return foundNdx;
    };

    this.refresh = function(){
      aRefreshFn(aJqListHandle, embeddedList);
    };

    this.fullyErase = function(){
      embeddedList = [];
      this.refresh();
    }

  };
  if (this.Type) new Type('UIFrontedOrderedList', UIFrontedOrderedList);

})();


(function(){
//This Ordering thing originally jacked from: https://github.com/trentrichardson/Ordering/blob/master/ordering.js
/*
* Ordering Object
* By: Trent Richardson [http://trentrichardson.com]
* Version 0.1
* Last Modified: 02/02/2011
* 
* Copyright 2011 Trent Richardson
* Dual licensed under the MIT and GPL licenses.
* http://trentrichardson.com/Impromptu/GPL-LICENSE.txt
* http://trentrichardson.com/Impromptu/MIT-LICENSE.txt
*/
//HAS BEEN VERY MODIFIED
  var Ordering = this.Ordering = function(obj,sf,listObject,backingObject){

    var keys, values = {};
    this.length = 0;
    this.sortfn = null;

    this.alertKeys = function(){
      alert(JSON.stringify(keys));
    };

    this.setBackingObject = function(newBackingObject){
      backingObject = newBackingObject;
      console.log("Set backingObject: " + backingObject);
    };

    this.initialSave = function(){
      console.log("Initial save...");
      if(backingObject){
        for(k in values){
          if(values.hasOwnProperty(k)){
            var newRecord = backingObject.insert(values[k]);
            newRecord.set("indexKey", k);
          }
        }
      } else {
        throw "No backingObject"
      }
    }

    this.feed = function(obj){
      for(k in obj){
        if(obj.hasOwnProperty(k)){
          this.set(k, obj[k]);
          if(backingObject)backingObject.set(k, obj[k]);
        }
      }
      return this;
    };

    this.get = function(key){
      return values[key];
    };

    this.getAt = function(index){
      if(index >= this.length) return null;
      return values[keys.getAt(index)];
    };
    //Note this can return -1 if its at the front of the list.
    this.indexOfOrBeforeByBinarySearch = function(key){
      if(this.length == 0){
        return -1;
      } 
      var startPoint = 0, endPoint = this.length - 1;
      var midPoint = parseInt(endPoint / 2);
      var firstKey = keys.getAt(startPoint);
      var startCompare = this.sortfn(firstKey,values[firstKey], key, values[key]);
      if(startCompare!=-1){
        return -1;
      }
      var lastKey = keys.getAt(endPoint);
      var endCompare = this.sortfn(lastKey,values[lastKey], key, values[key]);
      if(endCompare!=1){
        return endPoint;
      }
      while(startPoint < endPoint){
        //console.log("Key1: " keys.getAt(1));
        //console.log("S/E/M: " + startPoint + "/" + endPoint + "/" + midPoint);
        var midPointKey = keys.getAt(midPoint);
        //console.log("midPoint val: " + JSON.stringify(values[midPointKey]));
        var compResult = this.sortfn(midPointKey,values[midPointKey], key, values[key]);
        //console.log("Got compResult: " + compResult);
        if(compResult == 0){
          return midPoint;
        } else {
          //console.log("Compresult: " + compResult);
          if (compResult > 0){
            if(midPoint-1==startPoint){
              return startPoint;
            }
            endPoint = midPoint;
          } else {
            if(midPoint+1==endPoint){
              return midPoint;
            }
            startPoint = midPoint;
          }
          midPoint = parseInt( ( endPoint - startPoint ) / 2 ) + startPoint;
        }
      }
      return startPoint;
    }

    //#FIXME seems like this should use the fact its sorted
    this.indexOf = function(key){
      if(values[key]){
        return this.indexOfOrBeforeByBinarySearch(key);
      } else {
        return -1;
      }
    };

    this.set = function(key, value, deferRefresh){
      if(values[key]){
        //console.log("Found key");
        this.erase(key);
        //console.log("Ok where is inject");
      }
      values[key] = value;
      if(backingObject){
        var savedObject = backingObject.insert(value);
        savedObject.set("indexKey", key);
      }
      //console.log("Finding index for: " + JSON.stringify(value));
      var insertAt = this.indexOfOrBeforeByBinarySearch(key) + 1;
      //console.log("Supposed to insertAt: " + insertAt);
      this.length++;
      if(this.sortfn === null){
        keys.push(key);
      } else {
        if(deferRefresh){
          keys.spliceDeferred(insertAt, 0, key);
        } else {
          keys.splice(insertAt, 0, key);
        }
      }
      return this;
    };

    //#FIXME This dosnt work if you passed a listObject
    this.clone = function(){
      return new Ordering(values, this.sortfn);
    };

    this.fullyErase = function(){
      keys.fullyErase();
      values = {};
      this.length = 0;
    }

    this.keys = function(){
      return keys.slice(0);
    };

    this.erase = function(key){
      var index = keys.indexOf(key);
      //console.log("Here is erase and index is:" + index);
      if(index !== -1){
        //console.log("splicing...");
        keys.splice(index,1);
        //console.log("did splice");
        delete values[key];
        if(backingObject) {
          var deleteMe = backingObject.query({"indexKey": key})[0];
          deleteMe.deleteRecord();
        }
        //console.log("Did delete");
        this.length--;
      }
      //console.log("did erase");
      return this;
    };

    this.each = function(fn, bind){
      for (var i=0, l=this.length; i < l; i++){
        if(fn.call(bind, keys.getAt(i), values[keys.getAt(i)], this) === false) break;
      }
      return this;
    };

    this.sort = function(fn, bind){
      if(fn === undefined && this.sortfn === undefined){
        keys.sort();
      }
      else {
        if(fn === undefined) 
          fn = this.sortfn;

        keys.sort(function(a, b){
          return fn.call(bind, a, values[a], b, values[b], this);
        });
      }
      return this;
    };

    this.collect = function(fn){
      var returnList = [];
      for(var i in values){
        returnList.push(fn(values[i]));
      }
      return returnList;
    }

    this.filter = function(fn, bind){
      var c = this.clone();
      for (var i=0,l=this.length; i<l; i++){
        if(!fn.call(bind, keys.getAt(i), values[keys.getAt(i)], c)) c.erase(k);
      }
      return c;
    };

    this.subset = function(arr){
      var c = new Ordering();
      for (var i=0,l=this.length; i<l; i++){
        if(arr.indexOf(keys.getAt(i)) >= 0) c.set(keys.getAt(i), values[keys.getAt(i)]);
      }
      return c;
    };

    this.slice = function(start, end){
      var c = new Ordering(),
        ks = this.keys().slice(start, end);
      for (var i=0,l=ks.length; i<l; i++){
        c.set(ks[i], values[ks[i]]);
      }
      return c;
    };

    this.some = function(fn, bind){
      for (var i=0,l=this.length; i<l; i++){
        if(fn.call(bind, keys.getAt(i), values[keys.getAt(i)], this)) return true;
      }
      return false;
    };

    this.every = function(fn, bind){
      for (var i=0,l=this.length; i<l; i++){
        if(!fn.call(bind, keys.getAt(i), values[keys.getAt(i)], this)) return false;
      }
      return true;
    };

    this.map = function(fn, bind){
      var c = this.clone();
      for (var i=0,l=this.length; i<l; i++){
        c.set(keys.getAt(i), fn.call(bind, keys.getAt(i), values[keys.getAt(i)], c));
      }
      return c;
    };

    this.getValues = function(){
      return values;
    }

    this.refresh = function(){
      this.sort();
      keys.refresh();
    }

    if(listObject !== undefined){
      keys = listObject; 
    } 
    if(obj !== undefined)
      this.feed(obj);
    if(sf !== undefined)
      this.sortfn = sf;
    if(this.length > 1 && this.sortfn !== null)
      this.sort(this.sortfn);

  };

  if (this.Type) new Type('Ordering', Ordering);

})();

function takeUpToNFromString(takeThisMany, string){
  var smallerOfTheTwo = string.length;
  if(takeThisMany < smallerOfTheTwo){
    smallerOfTheTwo = takeThisMany;
  }
  return string.slice(0, ( takeThisMany - 1 ))
}

var prefListSort = function(ak,av,bk,bv){
    var v1 = av.prefScore,v2 = bv.prefScore;       
    if(v1 < v2) return 1;
    if(v1 > v2) return -1;
    if(bv.trackName > av.trackName) return -1;
    if(av.trackName > bv.trackName) return 1;
    return 0;
};

var tasteListSort = function(ak,av,bk,bv){
    var v1 = av.tasteScore,v2 = bv.tasteScore; 
    if(av.dirty == true && bv.dirty == false) return -1;
    if(bv.dirty == true && av.dirty == false) return 1;  
    if(v1 < v2) return 1;
    if(v1 > v2) return -1;
    if(bv.userId > av.userId) return -1;
    if(av.userId > bv.userId) return 1;
    return 0;
};

var playListSort = function(ak,av,bk,bv){
    var v1 = av.tasteScore,v2 = bv.tasteScore;
    if(av.listened == true && bv.listened == false) return 1;
    if(bv.listened == true && av.listened == false) return -1;        
    if(v1 < v2) return 1;
    if(v1 > v2) return -1;
    return 0;
};

var tasteList;  //has users, with scores computed from their likes
var prefList;
var playList;

var scInit = false;
var limitToForty = false;
var myId;

//Gonna be a function after $ready
var killStep1;  
var killSyncSetup;

//gonna be a function after scInit
var getMyId;  
var addToFavoritesById;  

var loggedInToPersistence = false;
var tasteLeechPersistence = false;
var dropboxClient;
var dropboxTable;

function showDropboxError(error) {
  console.log("Error status: " + error.status);
  switch (error.status) {
  case Dropbox.ApiError.INVALID_TOKEN:
  console.log("1");
    // If you're using dropbox.js, the only cause behind this error is that
    // the user token expired.
    // Get the user through the authentication flow again.
    break;

  case Dropbox.ApiError.NOT_FOUND:
    console.log("2");
    // The file or folder you tried to access is not in the user's Dropbox.
    // Handling this error is specific to your application.
    break;

  case Dropbox.ApiError.OVER_QUOTA:
    console.log("3");
    // The user is over their Dropbox quota.
    // Tell them their Dropbox is full. Refreshing the page won't help.
    break;

  case Dropbox.ApiError.RATE_LIMITED:
    console.log("4");
    // Too many API requests. Tell the user to try again later.
    // Long-term, optimize your code to use fewer API calls.
    break;

  case Dropbox.ApiError.NETWORK_ERROR:
    console.log("5");
    // An error occurred at the XMLHttpRequest layer.
    // Most likely, the user's network connection is down.
    // API calls will not succeed until the user gets back online.
    break;

  case Dropbox.ApiError.INVALID_PARAM:
    console.log("6");
    break;

  case Dropbox.ApiError.OAUTH_ERROR:
    console.log("7");
    break;

  case Dropbox.ApiError.INVALID_METHOD:
    console.log("8");
    break;
  default:
    console.log("9");
    // Caused by a bug in dropbox.js, in your application, or in Dropbox.
    // Tell the user an error occurred, ask them to refresh the page.
  }
};

var deniedFunction = function(){alert("You need to log in.")};
var errorFunction = function(xhr, textStatus, textError){
  alert("Error: " + textStatus);
  alert(textError);
};

function flushAllPrefs(){
  doIfLogged(function(){
    killSyncSetup();
    prefList.each(function(k, v){
      if(!v.hidden){
        savePreference(k);
      }
    });
  }, deniedFunction);
}

function tryToLoadPrefs(){
  var loadPrefSuccessFunction = function(responseData){
    //console.log("success");
    console.log(responseData);
  };

  var doThis = function(){
    var doThisUrl = "/mypreflist";
    //console.log("Should get my tracks");
    $.ajax({
      type: "GET",
      url: doThisUrl,
      xhrFields: {
         withCredentials: true
      },
      success: loadPrefSuccessFunction,
      error: errorFunction
    }); 
  };
  doIfLogged(doThis, deniedFunction);
}

function doIfLogged(cb, cbIfNot){
  var url = "/checklogged";

  var successFunction = function(returnData){
    loggedInToPersistence = returnData.indexOf("yes") != -1;
    //console.log("Logged in:" + loggedInToPersistence);
    if(loggedInToPersistence){
      if(cb){
        //console.log("Should do cb");
        cb();
      }
    } else {
      if(cbIfNot)cbIfNot();
    }
  };

  $.ajax({
    type: "GET",
    url: url,
    xhrFields: {
       withCredentials: true
    },
    success: successFunction,
    error: errorFunction
  }); 
}

function savePayloadForTrack(track){
  var hidden = track.hidden ? true : false;
  return {score: track.prefScore, hidden: hidden};
}

function savePreference(trackId){
  var prefTrack = prefList.get(trackId);
  var prefPayload = {};
  prefPayload[trackId] = savePayloadForTrack(prefTrack);
  savePrefs(prefPayload);
}

function loadFromDropbox(){
  getDropboxTable();
}

function syncToDropbox(){
  console.log("Trying to sync to Dropbox");
  getDropboxTable();
  prefList.setBackingObject(dropboxTable);
  prefList.initialSave();
}

function getDropboxTable(){
  if (!(dropboxClient.isAuthenticated())) {
    console.log("Yeah... where is auth popup?");
    dropboxClient.authenticate({redirect_uri: "https://tasteleech.neocities.org/authlanding.html"}, function (error, newClient){
      console.log("Attempted to authenticate...");
      if(error){
        console.log(error);
        showDropboxError(error);
        console.log("Is this getting called twice?");
        throw "Dropbox error"
      } else {
        console.log("Supposedly authenticated.");
        console.log(newClient);
        console.log(dropboxClient);
        console.log(newClient.isAuthenticated());
        console.log(dropboxClient.isAuthenticated());
        if(newClient.isAuthenticated()){
          var datastoreManager = dropboxClient.getDatastoreManager();
          datastoreManager.openDefaultDatastore(function (error, datastore) {
            if (error) {
                alert('Error opening default datastore: ' + error);
            }

            dropboxTable = datastore.getTable('tasteleech');
          });
        } else {
          throw "Auth Error Dropbox :("
        }
      }
    });
  }
  /*if(dropboxClient.isAuthenticated()){
    var datastoreManager = dropboxClient.getDatastoreManager();
    datastoreManager.openDefaultDatastore(function (error, datastore) {
      if (error) {
          alert('Error opening default datastore: ' + error);
      }

      dropboxTable = datastore.getTable('tasteleech');
    });
  } else {
    throw "Auth Error Dropbox :("
  }*/
  
}

function saveAllPrefs(tracks){
  console.log("Going to try to save :" + JSON.stringify(tracks));
  var sendThis = {};
  for(var i in tracks){
    var thisTrack = tracks[i];
    if(thisTrack.prefScore != 0){
      sendThis[thisTrack.trackId] = savePayloadForTrack(thisTrack);
    }
  }
  savePrefs(sendThis);
}

function savePrefs(prefPayload){
  var url = "/stateprefs";
  console.log("This is the payload: " + JSON.stringify(prefPayload));
  $.ajax({
    type: "POST",
    url: url,
    data: JSON.stringify(prefPayload),
    contentType: "application/json",
    xhrFields: {
       withCredentials: true
    },
    success: function(returnData){
      console.log(returnData);
    }
  });
}

function prefSaveText(prefTrack){
  var saveObject = {s:prefTrack.prefScore, t:prefTrack.trackName, fc:prefTrack.favoriterCount};
  return saveObject;
}

function clickFavorites(){
  console.log("Clikc favs");
  authSoundcloud(function(){
    getMyId(function(){
      killStep1();
      getMyFavorites();
    });
  });
}

function authSoundcloud(authCb){
  SC.initialize({
    client_id: 'de48fff7bd6d162cb6d33f9e71f57953',
    redirect_uri: 'https://tasteleech.neocities.org/authlanding.html'
  });
  console.log("I got clicked...");
  SC.connect(function() {
    console.log("getting here...");
    getMyId = function(getMyIdCb){
      SC.get('/me', function(me) { 
        console.log('Hello, ' + me.username); 
        myId = me.id;
        if(getMyIdCb)getMyIdCb();
      });
    };
    addToFavoritesById = function(trackId){
      SC.put('/me/favorites/' + trackId);
    }
    scInit = true;
    if(authCb)authCb();
  });    
}

function getMyFavorites(){
  SC.get('/users/' + myId + '/favorites', function(favs){
    //console.log("Looks like you're into: " + JSON.stringify(favs));
    //console.log("This is one of your miserable favorites: " + JSON.stringify(favs[0]));
    for (var favnum in favs){
      var aFavorite = favs[favnum];
      //console.log("Here is one of my favorites: " + JSON.stringify(aFavorite));
      stateSongPreference(aFavorite, 8);
    }
    //console.log("Favorites are now: " + JSON.stringify(prefList));
  });
}

function stateSongPreference(songObject, prefInt){
  //console.log("Attermpting to say I like a song with id: " + trackId);
  var trackId = songObject.id;

  var addSongForPreflist = {"trackId":trackId, "prefScore": prefInt, 
    favoriterCount:songObject.favoritings_count,
    trackName:songObject.title,
    doneFavoriterCount:0,
    favoriters:{}};
  var addSongForPlaylist = {"trackId": trackId,
    listened:true, 
    tasteScore:0,
    trackName:songObject.title,
    genre: songObject.genre,
    scoreRecord:{}};
  prefList.set(trackId, addSongForPreflist);
  playList.set(trackId, addSongForPlaylist);

}

function updateSongScorePopup(trackId){
  var prefScore = prompt("New Score");
  var prefScoreInt = parseInt(prefScore);
  if(prefScoreInt){
    updateSongPreference(trackId, prefScoreInt);
  } else {
    alert("Could not change score to: " + prefScore);
  }
}

function recomputeDudeScore(dude){
  var runningSum = 0;
  var runningCount = 0;
  var runningBonus = 0;
  var record = dude.scoreRecord;
  for(trackId in record){
    var thisScore = record[trackId];
    runningSum  = runningSum + thisScore;
    runningCount++;
    if(thisScore > 5)runningBonus++;
    if(thisScore < -2)runningBonus--;
  }
  var average = runningSum / runningCount;
  dude.tasteScore = average + runningBonus;
}

function recomputeSongScore(song){
  var runningSum = 0;
  var runningCount = 0;
  var runningBonus = 0;
  var songBonus = 0;
  var record = song.scoreRecord;
  for(dudeId in record){
    if(dudeId == "songBonus"){
      songBonus = record[dudeId];
    } else {
      var thisScore = record[dudeId];
      runningSum  = runningSum + thisScore;
      runningCount++;
      if(thisScore >= 5)runningBonus+=3;
      if(thisScore < -2)runningBonus--;
    }
  }
  //var average = runningSum / runningCount;
  song.tasteScore = runningSum + runningBonus + songBonus;
}

function updateSongPreference(trackId, prefInt){
  //console.log("trying to update song with prefInt: " + prefInt);
  var track = prefList.get(trackId);
  //console.log("Found track: " + JSON.stringify(track));
  track.prefScore = prefInt;
  if(prefInt != 0 && tasteLeechPersistence){
    //console.log("Should save this!!");
    savePreference(trackId);
  }
  for(var dudeId in track.favoriters){
    var dude = tasteList.get(dudeId);
    dude.scoreRecord[trackId] = prefInt;
    recomputeDudeScore(dude);
    //console.log("New taste score: " + dude.tasteScore); 
    for(var favIdNdx in dude.favorites){
      var favId = dude.favorites[favIdNdx];
      var favTrack = playList.get(favId);
      if(favTrack.listened == false){
        //console.log("Updating a song for dude: " + JSON.stringify(favTrack));
        var oldDudeScore = favTrack.scoreRecord[dudeId];
        favTrack.scoreRecord[dudeId] = dude.tasteScore;
        recomputeSongScore(favTrack);
        //playList.set(favId, favTrack);
      }
    }
    //tasteList.set(dudeId, dude);
    //console.log("Should have just set dude in the tasteList");
  }
  playList.refresh();
  tasteList.refresh();
  prefList.set(trackId, track);
}

function doPrefFromTrackId(trackId){
  //console.log("Doing a pref");
  var selectionString = "#tdForButtonForTrack" + trackId;
  $(selectionString).html("");
  var prefEntry = prefList.get(trackId);
  //console.log("Found prefEntry: " + JSON.stringify(prefEntry));
  tasteFromPref(prefEntry); 
}

function tasteFromPref(prefObject){
  //console.log("Dealing with " + JSON.stringify(prefObject));
  SC.get("/tracks/" + prefObject.trackId + "/favoriters" + "?offset=" + prefObject.doneFavoriterCount, function(favoriters){
    //console.log("All these dudes like a song: " + JSON.stringify(favoriters));
    //console.log("Here is a dude close up: " + JSON.stringify(favoriters[0]));
    //updateDudeWithPref(favoriters[0], prefObject);
    for(var favInt in favoriters){
      var fFavoriter = favoriters[favInt];
      //console.log("Here is a dude:: " + JSON.stringify(fFavoriter));
      if(fFavoriter.id == myId){
        //This is me: don't update the tastelist, but do increment count
      } else {
        if(prefObject.favoriters[fFavoriter.id]){
          //We already got this guy
        } else {
          updateDudeWithPref(fFavoriter, prefObject);
          //console.log("dude updated");
          prefObject.favoriters[fFavoriter.id] = true;
        }
      }
      prefObject.doneFavoriterCount++;
    }  
    tasteList.refresh();
    playList.refresh();
    prefList.set(prefObject.trackId, prefObject);
    //console.log("Here is tasteList: " + JSON.stringify(tasteList)); 
  });
}

//Does not trigger screen refresh on its own anymore
function updateDudeWithPref(aFavoriter, prefObject){
  //console.log("Called updateDudeWithPref");
  if(tasteList.get(aFavoriter.id)){
    //console.log("Already have this guy");
    var dude = tasteList.get(aFavoriter.id);
    
    dude.scoreRecord[prefObject.trackId] = prefObject.prefScore;
    dude.favorites.push(prefObject.trackId);
    recomputeDudeScore(dude);
    //tasteList.set(aFavoriter.id, dude, true);  
  } else {
    //console.log("Adding a new guy.");
    var scoreRecord = {};
    scoreRecord[prefObject.trackId] = prefObject.prefScore;
    tasteList.set(aFavoriter.id, {"tasteScore" : prefObject.prefScore, 
      "dirty": true, "userId": aFavoriter.id, scoreRecord:scoreRecord,
      favorites:[prefObject.trackId]}, true); 
    //console.log("Should have just added a dude to the list");
  }
}

function songsFromDude(dude, cb){
  //console.log("Getting songs for dude: " + JSON.stringify(dude));
  SC.get("/users/" + dude.userId + "/favorites", function(favorites){
    //console.log("Ok the hit went");
    //console.log("This guy likes: " + JSON.stringify(favorites));
    //var trackOne = favorites[0];
    //console.log("Here is a track close up: " + JSON.stringify(trackOne));
    //updatePlaylistWithTaste(dude, trackOne);
    for(var favInt in favorites){
      var thisAwesomeTrack = favorites[favInt];
      dude.favorites.push(thisAwesomeTrack.id);
      //console.log("Trying to update the playlist with the awesome track: " + JSON.stringify(thisAwesomeTrack));
      updatePlaylistWithTaste(dude, thisAwesomeTrack);
      //console.log("Here is a dude:: " + JSON.stringify(fFavoriter));
    } 
    playList.refresh();  
    if(cb)cb();
    //console.log("Here is playList: " + JSON.stringify(playList)); 
  });
}

var genreBump = {};

function songBonusHeurisitc(track){
  var returnScore = 0;
  //add/subtract points for view/like ratio
  var viewCount = track.playback_count;
  var logPart = Math.log(viewCount) / Math.log(10);
  var viewMultiplier = logPart - 2;
  var viewLikeRatioPct = (track.favoritings_count / viewCount) * 100.0 ;
  var bumpGenre = genreBump[track.genre];
  if(bumpGenre){
    returnScore += bumpGenre;
  }
  if(viewLikeRatioPct && viewMultiplier){
    var finalAddMe = viewLikeRatioPct * viewMultiplier;
    returnScore += finalAddMe;
  }
  return returnScore;
}

function reapplySongBonusHeuristic(){
  playList.each(function(trackId, track){
    track.scoreRecord.songBonus = songBonusHeurisitc(track);
    recomputeSongScore(track);
  })
}

function bumpGenre(bumpAmount, genreName, alsoSkip){
  //console.log("Bumping gnere: " + genreName);
  var amount = genreBump[genreName] ? genreBump[genreName] : 0;
  amount += bumpAmount;
  genreBump[genreName] = amount;
  reapplySongBonusHeuristic();
  playList.refresh();
  updateCurrentTrackInfo();
  if(alsoSkip)nextTrack();
}

function updatePlaylistWithTaste(dude, track){
  //console.log("Down here trying to update playlist with track: " + JSON.stringify(track));
  //console.log("This is playList: " + JSON.stringify(playList));
  if(playList.get(track.id)){
    //console.log("Found the track in the playlist, updating tasteScore");
    var trackExists = playList.get(track.id);
    trackExists.scoreRecord[dude.userId] = dude.tasteScore;
    recomputeSongScore(trackExists);
    //playList.set(track.id, trackExists);  //triggers screen refresh, not sure if otherwise necessary
  } else {
    var songBonus = songBonusHeurisitc(track);
    //console.log("Adding track to playList with bonus: " + songBonus);
    var tasteScore = dude.tasteScore + songBonus;
    var scoreRecord = {};
    scoreRecord[dude.userId] = dude.tasteScore;
    scoreRecord["songBonus"] = songBonus;
    var genreString = track["genre"];
    //console.log("genreString: " + genreString);
    playList.set(track.id, {"tasteScore" : tasteScore, "trackId": track.id,
      scoreRecord: scoreRecord, "listened": false, "trackName": track.title,
      "genre":genreString, "favoritings_count": track.favoritings_count}, true); 
    //console.log("Should have just updated playList");
  }
}

function alertPlayList(){
  console.log("Here is playlist: " + JSON.stringify(playList));
}

function alertPrefTrack(trackId){
  var track =  prefList.get(trackId);
  console.log(JSON.stringify(track));
}

function listRefreshFunction(aRenderFn, aJqListHandle, embeddedList){
  var pileUpHtmls = "";
  for(i in embeddedList){
    pileUpHtmls += aRenderFn(embeddedList[i]);
  }
  aJqListHandle.html(pileUpHtmls);
}

function boxPlotRefreshFunction(aCollectFunction, aJqListHandle, embeddedList, first40Only){
  var collectEntries = [];
  var scoreSum = 0;
  var counter = 0;
  var superCounter = 0;
  var maxScore = 0;
  //console.log("boxPlotRefreshFunction called with: " +  JSON.stringify(embeddedList));
  for(var i in embeddedList){
    var collected = aCollectFunction(embeddedList[i]);
    if(collected === null){

    } else {
      scoreSum += collected;
      if(collected > maxScore)maxScore = collected;
      collectEntries[counter] = collected;
      counter++;
    }
    superCounter++;
  }
  //console.log("boxPlotRefreshFunction got: " + JSON.stringify(collectEntries));
  //console.log("Its this isnt it?");
  var topInfoSpan = aJqListHandle.find("#avgSpan");

  topInfoSpan.html("Average Score: " + (scoreSum / counter));
  var maxSpan = aJqListHandle.find("#maxSpan");

  maxSpan.html("Max Score: " + maxScore);
  var countSpan = aJqListHandle.find("#countSpan");

  countSpan.html("Taster Count: " + counter + '(' + superCounter +')');
  var buttonHandle = aJqListHandle.find("#moreTracksButton");
  buttonHandle.removeClass("btn-danger btn-warning btn-success");
  var buttonString = "btn-danger";
  //console.log("Here is maxScore: " + maxScore);
  var hintHandle = aJqListHandle.find("#stepThreeHint");
  var instructionsHandle = aJqListHandle.find("#stepThreeInstructions");
  if(maxScore >= 8.2){
    hintHandle.hide();
    instructionsHandle.show();
    buttonString = "btn-success";
  } else {
    hintHandle.show();
    instructionsHandle.hide();
    if(maxScore >= 6.1){
      buttonString = "btn-warning";
    } else {
      //console.log("Do I actually think the max is less than 7?");
    }
  }
  buttonHandle.addClass(buttonString);
  //console.log("Set html");
}

var showHiddenPrefs = false;

function renderPreflistEntry(trackId){
  //console.log("renderPreflistEntry Looking for track with ID: " + trackId);
  var trackInList = prefList.get(trackId);
  if(!showHiddenPrefs && trackInList.hidden){
    return "";
  } else {
    var unDoneRatio = (1 - (trackInList.doneFavoriterCount / trackInList.favoriterCount)) * 100;
    var stringForBootstrap = parseInt(unDoneRatio).toString();
    //console.log("This is string for ratio: " + stringForBootstrap);
    var trackTd = '<td>' + '<a onClick="alertPrefTrack(' + trackId + ');">' + trackInList.trackName + '</a>' + '</td>';
    var hideString = "Hide";
    if(trackInList.hidden == true){
      hideString = "Unhide";
    }
    var hideTd = '<td>' + '<a onClick="hideUnhidePrefTrack(' + trackId + ');">' + hideString + '</a>' + '</td>';

    var buttonTd = '<td id="tdForButtonForTrack' + trackId + 
      '"><a onClick="doPrefFromTrackId(' + trackId + 
      ');"><div class="progress progress-info"><div class="bar" style="width: ' + 
      stringForBootstrap + '%"><font color="#FFF">' + trackInList.doneFavoriterCount +
      "/" + trackInList.favoriterCount + '</font></div></div></a></td>';

    var prefTd = '<td>' + '<p class="text-center"><a onClick="updateSongScorePopup(' + trackId + ');">' + trackInList.prefScore + '</a></p>' + '</td>';
   
    return "<tr>" + trackTd + hideTd + prefTd + buttonTd + "</tr>";
  }
}

function prefListRefresh(aJqListHandle, embeddedList){
  listRefreshFunction(renderPreflistEntry, aJqListHandle, embeddedList);
}

function hideUnhidePrefTrack(trackId){
  var track = prefList.get(trackId);
  track.hidden = !(track.hidden);
  prefList.set(trackId, track);
}

var beforeThing = '<li>';
var afterThing = '</li>';

function collectTasteListScore(dudeId){
  var dude = tasteList.get(dudeId);
  if(dude.dirty){
    return dude.tasteScore;
  } else {
    return null;
  }
  
}

function tasteListRefresh(aJqListHandle, embeddedList){
  //console.log("Called tasteListRefresh");
  boxPlotRefreshFunction(collectTasteListScore, aJqListHandle, embeddedList);
}

function alertScoreRecord(trackId){
  var track = playList.get(trackId);
  console.log(JSON.stringify(track.scoreRecord));
}

function alertPlayListTrack(trackId){
  var track = playList.get(trackId);
  console.log(JSON.stringify(track));
}

function renderPlaylistEntry(trackId){

  var trackInList = playList.get(trackId);
  //console.log("renderPlaylistEntry Found track: " + JSON.stringify(trackInList));
  var rawScore = trackInList['tasteScore'];
  //console.log("score is: " + rawScore);
  var scoreString = new String(rawScore);
  var shorterScoreString = takeUpToNFromString(5, scoreString);
  //console.log("scoreString is: " + scoreString);
  var scoreTd = '<td>' + '<a onClick="alertScoreRecord(' + trackId + ');">' + shorterScoreString + '</a>' + '</td>';
  var gString = trackInList["genre"];
  var innerString = '';
  if(gString && gString != ''){
    var stringSingleQuoted = "'" + gString + "'";
    innerString = gString + '<p style="display: inline-block">&nbsp;' + genreWeightString(gString) + 
    '&nbsp;&nbsp;<a class="btn btn-mini btn-success" onClick="bumpGenre(10, ' + stringSingleQuoted + ');">+</a>' + "&nbsp;&nbsp;" +
    '<a class="btn btn-mini btn-danger" onClick="bumpGenre(-10, ' + stringSingleQuoted + ');">-</a></p>';
  }
  var genreTd = '<td style="word-break: break-all;">' + innerString + '</td>';
  //console.log("scoreTd: " + scoreTd);
  //console.log("point 1");
  var titleTd = '<td style="vertical-align: middle;word-break: break-all;">' + '<a onClick="alertPlayListTrack(' + trackId + ');">' + trackInList['trackName'] + '</a>' + '</td>';
  //console.log("point 2");
  var playTd = '<td style="vertical-align: middle;">' + '<a class="btn btn-mini btn-primary" onClick="playTrackById(' + trackId + ');">Play</a>' + '</td>';

  var skipTd = '<td style="white-space: nowrap; vertical-align: middle;">' + '<a class="btn btn-mini btn-success" onClick="skipTrack(' + trackId + ', 5);">' + "+" + '</a>&nbsp;' +
  '<a  class="btn btn-mini btn-warning" onClick="skipTrack(' + trackId + ', 0);">' + "0" + '</a>&nbsp;' +
  '<a  class="btn btn-mini btn-danger" onClick="skipTrack(' + trackId + ', -4);">' + "-" + '</a>' + '</td>';
  //console.log("point3");
  var rowClassString = "warning";
  //console.log("point4");
  if(trackInList.listened){
    //console.log("point5.1");
    rowClassString = "info";
    //console.log("point 6.1");
  } else if(trackInList['tasteScore'] && trackInList['tasteScore'] > 9.8){
    //console.log("point5.2");
    rowClassString = "success";
    //console.log("point 6.2");
  }
  //console.log("point 7");
  var firstPart = '<tr class="' + rowClassString + '">' + scoreTd + genreTd;
  //console.log("point 8, firstPart: " + firstPart);
  //console.log("titleTd: " + titleTd);
  //console.log("playTd: " +  playTd);
  var secondPart = titleTd + skipTd + playTd;
  //console.log("point 9, secondPart: " + secondPart);
  var returnThis = firstPart + secondPart + '</tr>';
  //console.log("Trying to return: " + returnThis);
  return returnThis;
}

function playListRefresh(aJqListHandle, embeddedList){
  var passList = embeddedList;
  //console.log("Should render play list");
  if(limitToForty){
    //console.log("should be limited list");
    passList = embeddedList.slice(0, 39);
  }
  //console.log("render part2");
  listRefreshFunction(renderPlaylistEntry, aJqListHandle, passList);
}

var currentTrackId;

var updateCurrentTrackInfo;  //becomes a function after $ready

function nextTrack(){
  var track = playList.getAt(0);
  playTrack(track);
}

function playTrackById(trackId){
  var track = playList.get(trackId);
  playTrack(track);
}

function skipTrack(trackId, prefScore){
  var track = playList.get(trackId);
  track.listened = true;
  playList.set(trackId, track);
  var haveTrackInPrefListAlready = prefList.get(trackId);
  if(haveTrackInPrefListAlready){
    haveTrackInPrefListAlready.prefScore = prefScore;
    prefList.set(trackId, haveTrackInPrefListAlready);
  } else {
    var favoriters = {};
    for(var favoriterId in track.scoreRecord){
      if(favoriterId != "songBonus"){
        favoriters[favoriterId] = true;
      }
    }
    var trackForPrefList = {"trackId":trackId, 
    favoriterCount:track.favoritings_count,
    trackName:track.trackName,
    doneFavoriterCount:0,
    favoriters:favoriters, 
    genre:track.genre,
    rawObject: "Skipped"};
    prefList.set(trackId, trackForPrefList);
    updateSongPreference(trackId, prefScore);
  }

}

function playTrack(track){
  var trackId = track.trackId;
  var widgetIframe = document.getElementById("sc-widget");
  var widget = SC.Widget(widgetIframe);
  var newWidgetUrl = "https://api.soundcloud.com/tracks/" + trackId.toString();
  widget.load(newWidgetUrl, {
    callback: function(){
      currentTrackId =trackId;
      widget.play();
      widget.getCurrentSound(function(sound){
        var haveTrackInPrefListAlready = prefList.get(trackId);
        if(haveTrackInPrefListAlready){
          haveTrackInPrefListAlready.rawObject = sound;
          prefList.set(trackId, haveTrackInPrefListAlready);
        } else {
          var favoriters = {};
          for(var favoriterId in track.scoreRecord){
            if(favoriterId != "songBonus"){
              favoriters[favoriterId] = true;
            }
          }
          var trackForPrefList = {"trackId":trackId, 
          favoriterCount:sound.favoritings_count,
          trackName:sound.title,
          doneFavoriterCount:0,
          favoriters:favoriters, 
          genre:sound.genre,
          rawObject: sound};
          prefList.set(trackId, trackForPrefList);
          updateSongPreference(trackId, 0);
        }
        updateCurrentTrackInfo();
      }); 
    }
  });
  track.listened = true;
  playList.set(trackId, track);
}


function getCurrentStatus(){
  var statusObejct = {};
  //console.log("Setting myId");
  statusObejct["myId"] = myId;
  //console.log("Getting prefList");
  statusObejct["prefList"] = prefList.getValues();
  //console.log("Getting tasteList");
  statusObejct["tasteList"] = tasteList.getValues();
  //console.log("Getting playList")
  statusObejct["playList"] = playList.getValues();
  //console.log("About to return statusObject");
  return statusObejct;
}

function saveToDropbox(){
  var fileName = prompt("Filename:", currentDateString() + ".tljson");

  var fileData = JSON.stringify(getCurrentStatus());
  //console.log("Trying to save fileData:" + fileData);
  dropboxClient.writeFile(fileName, fileData, function(error, stat) {
    if (error) {
      return showError(error);  // Something went wrong.
    } else {
       console.log("File saved successfully.");
    }   
  });
}

function loadFromDropbox(){
  dropboxClient.readdir("/", function(error, entries) {
    if (error) {
      return showError(error);  // Something went wrong.
    } else {
      var fileName = prompt("Please retype a file from this list: " + entries.join(", "), entries[0]);
      dropboxClient.readFile(fileName, function(error, data) {
        if (error) {
          return showError(error);  // Something went wrong.
        } else {
          // console.log(data);  // data has the file's contents
          killStep1();
          var savedObject = JSON.parse(data);
          loadOrderingWithSave(playList, savedObject["playList"]);
          loadOrderingWithSave(tasteList, savedObject["tasteList"]);
          loadOrderingWithSave(prefList, savedObject["prefList"]);
        }
      });
    }
  });
}

function loadOrderingWithSave(newOrdering, savedOrdering){
  //console.log("called loadORdering with: " + JSON.stringify(newOrdering));
  newOrdering.fullyErase();
  //console.log("Not making it here?" + JSON.stringify(savedOrdering))
  for(var i in savedOrdering){
    //console.log("i is: " + i + " / " + JSON.stringify(savedOrdering[i]));
    newOrdering.set(i, savedOrdering[i], true);
  }
  newOrdering.refresh();
}

function setCurrentTrackScore(prefInt){
  //console.log("updating with:" + prefInt);
  updateSongPreference(currentTrackId, prefInt);
  if(prefInt < -2)nextTrack();
  //if(prefInt > 7)addToFavoritesById(currentTrackId);
}

function genreWeightString(genreString){
  var genreWeight = genreBump[genreString];
  var weightString = "+0"
  if(genreWeight && genreWeight != 0){
    var plusMinus = "+";
    if(genreWeight < 0){
      plusMinus = "-";
    }
    var weightString = new String(genreWeight);
    weightString = plusMinus + weightString;
  }
  return "(" + weightString + ")";
}

