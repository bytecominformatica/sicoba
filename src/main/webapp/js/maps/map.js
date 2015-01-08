/**
 * Script de carregamento do mapa padrão.
 */


var map;
function initialize() {
  var mapOptions = {
    zoom: 4,
    center: new google.maps.LatLng(-14.2350040,-51.00000)
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
  
  setMarkers(map,positions);
  
}

function setMarkers(map,jsonObj){
	
}

google.maps.event.addDomListener(window, 'load', initialize);



