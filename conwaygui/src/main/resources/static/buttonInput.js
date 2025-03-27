/*
buttoninput.js
*/

document.getElementById('clear').addEventListener('click', function(event) {
  //alert(event)
  event.preventDefault() // Impedisce l'invio del modulo
  event.stopPropagation() 
  sendCmdToServer('clear')
});



function handleOwnerChkbox(){
	const noowner = document.getElementById('owner');
	if (noowner.checked) {
		sendCmdToServer("owneroff")
	}else{
		sendCmdToServer("owneron")
	}	
}	
 
///C:/Didattica2025/qak25/conwaygui/src/main/resources/templates/guipage.html