function ShowNumberWithAnimation(i, j, randNumber){
	//获取当前数字对象
	var numberCell = $("#number-cell-"+ i + "-" + j);
	//设置该格格式
	numberCell.css("background-color", getNumberBackgroundColor(randNumber));
	numberCell.css("color", getNumberColor(randNumber));
	numberCell.text(randNumber);
	
	//设置当前数个显示格的显示动画
	numberCell.animate({
		width:"150px",
		height:"150px",
		top: getPosTop(i, j),
		left: getPosLeft(i, j)
	},0.000001);//规定动画的速度
}
function showMoveAnimation(fromx,fromy,tox,toy){
	var numberCell = $("#number-cell-"+ fromx + "-" + fromy);
	numberCell.animate({
		width:"150px",
		height:"150px",
		top: getPosTop(tox,toy),
		left: getPosLeft(tox, toy)
	},200);
}
