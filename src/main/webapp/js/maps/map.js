/**
 * Script de carregamento do mapa padrão.
 */

var map;
var jsonObj;
function initialize(jsonObj) {
	var mapOptions = {
		zoom : 4,
		center : new google.maps.LatLng(-14.2350040, -51.00000)
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

	setMarkers(jsonObj);

}

function setMarkers(jsonObj) {
	var teste = jsonObj.lat;
	alert(teste);
}

google.maps.event.addDomListener(window, 'load', initialize);
