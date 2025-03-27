/*
iomap.js
*/
 
const mapContainer = document.getElementById("map");  
mapContainer.innerHTML = '';
    
 	
function createMapRep(){
 for( let i=1; i<=20; i++ ){
     for( let j=1; j<=20; j++ ){
        const cellElement = document.createElement("div");
        cellElement.classList.add("cell");
        cellElement.classList.add("live");
        cellElement.id = `cell-${i}-${j}`;
        cellElement.addEventListener('click', function() {
            sendCmdToServer(""+cellElement.id);  //cell-i-j in commsocket.js
            //alert(""+cellElement.id );
        });
        mapContainer.appendChild(cellElement);
        //console.log("created cell" );
}//for
}//for
}//createMapRep




    function updateCellColor(newX, newY,color) {
       const newRobotCell = document.getElementById(`cell-${newY}-${newX}`);
      if( newRobotCell.classList.contains("live") ){
        newRobotCell.classList.remove("live");
      }
      else if( newRobotCell.classList.contains("dead") ){
        newRobotCell.classList.remove("dead");
      }
      if (color == 1) {
        newRobotCell.classList.add("dead");
      }else if (color == 0) {
        newRobotCell.classList.add("live");
      }

    }
    
    
createMapRep()
