var markingMode = false;
var canvas = document.getElementbyID("Minesweeper");

//return an obect with the dimension of the viewport of the browser
function getViewportDimension() {
var e = window, a = 'inner';
if (!( 'innerWidth' in window )) {
    a = 'client';
    e = document.documentElement || document.body;
}
return {w:e[a + 'Width'], h:e[a + 'Height']};
}

//apply the dimension to the canvas
var dim = getViewportDimension();
canvas.width = dim.w;
canvas.height = dim.h;

//toggle marking mode, used in Minesweeper.pde
var setMarkingMode = function(){
	markingMode = !markingMode;
}
