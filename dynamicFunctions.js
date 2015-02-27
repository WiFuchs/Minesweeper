var markingMode = false;
var canvas = document.getElementbyID("Minesweeper");
	canvas.width = window.innerWidth;
	canvas.height = window.innerHeight;
var setMarkingMode = function(){
	markingMode = !markingMode;
}
