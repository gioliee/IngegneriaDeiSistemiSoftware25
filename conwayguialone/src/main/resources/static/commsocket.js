/*
commsocket.js
*/

var socketToGui = null;
var serviceUrl  = null;
var opened      = false

//Chiamata al caricamento
function discoverServiceUrl(){
	fetch('/getserverip')	
	 .then(response => {
		if (!response.ok) {
			alert("Errore nella richiesta al server")		
			throw new Error('Errore nella richiesta: ' + response.status)	
		}
		return response.json()
	 })
	 .then(config => {
	   serviceUrl = `${config.host}`
	   createSocketForGui(serviceUrl);
	 })
	 .catch(error => console.log('Errore:', error));
}

function createSocketForGui(serviceUrl){
    //CREAZIONE DELLA CONNESSIONE WEBSOCKET DELLA GUI
	    wsAddr = "ws://localhost:7110/wsupdates".replace("localhost",serviceUrl)
		console.log("wsAddr="+wsAddr)
   		socketToGui = new WebSocket(wsAddr);  
        console.log("socketToGui="+socketToGui.url)
	//FUNZIONI DI CALLBACK 	
		socketToGui.onopen = function(event) {
		  console.log("socketToGui onopen event="+event)
		  opened = true;
		};
		socketToGui.onmessage = function(event) {  
		//event generato da WSServer broadcastToWebSocket
			//console.log("--- socketToGui ONMESSAGE "+event.data)
			handleWsMessage(event) 
		};
		socketToGui.onclose = function(event) {
		  // Handle connection close
		  console.log("--- socketToGui CLOSEEEE "+event)
		};		 
		socketToGui.addEventListener("error", (event) => {
		  console.log("socketToGui error: ", event);
		  
		});
}

function handleWsMessage(event){
    const message = event.data;
	
	if (message === "enable") {
	    enableButtons();
	} else if (message === "disable") {
	    disableButtons();
	}

    console.log("handleWsMessage " + message)     
        if (message.startsWith("cell(")) {
           // Estrae la posizione e il colore
            const [_, x, y, v] = message.match(/cell\((\d+),(\d+),([^)]+)\)/);
            updateCellColor(parseInt(x), parseInt(y), parseInt(v));
        }else if (message.startsWith("clear")) {
			clearOutArea() //in outarea.js
        }else {
            addItem(message); //in outarea.js
        }
}



function sendCmdToServer(cmd) {
	 console.log("sendCmdToServer:" + cmd)
	 if( opened )
     socketToGui.send(cmd);
}


 
discoverServiceUrl()