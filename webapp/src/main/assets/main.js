var board = new Array();
var  judge = new Array();
var score = 0;
$(function()
{
	newgame();
});

function newgame()
{
	init();
	//生成两个随机位置随机数字
	generateOneNumber();
	generateOneNumber();
}

function init()
{
	for(var i = 0; i < 4; i++)
	{
		board[i] = new Array();
		judge[i] = new Array();
		for(var j = 0; j < 4; j++)
		{
			//初始化格子
			judge[i][j] = false;
			board[i][j] = 0;
			var gridCell = $("#grid-cell-"+i+"-"+j);
			//下面是设置它的相对位置
			//上面已经将其转换为jquery的对象，所以没有必要再使用$("#gridCell")
			//$("#gridCell").css("left",getPosLeft(i,j));
			gridCell.css("top",getPosTop(i,j));
			gridCell.css("left",getPosLeft(i,j));
			
		}
	}
	updateBoardView();
	score = 0;
	$("#score").text(score);
}
function updateBoardView()
{
	//对重新布局的之前的数据进行删除
	$(".number-cell").remove();
	for(var i = 0; i < 4; i++)
	{
		for(var j = 0; j < 4; j++)
		{
			//$("#grid-container").append("<div id='gameover' class='gameover'></div>");
			$("#123").append("<div class='number-cell' id='number-cell-"+i+"-"+j+"'></div>");
			
			var numberCell = $("#number-cell-"+i+"-"+j);
			if(board[i][j]==0)
			{
				numberCell.css("width","0px");
				numberCell.css("height","0px");
				numberCell.css("top",getPosTop(i,j)+75);
				numberCell.css("left",getPosLeft(i,j)+75);
			}
			else
			{
				numberCell.css("width","150px");
				numberCell.css("height","150px");
				numberCell.css("top",getPosTop(i,j));
				numberCell.css("left",getPosLeft(i,j));
				numberCell.css("background-color",getNumberBackgroundColor(board[i][j]));
				numberCell.css("color",getNumberColor(board[i][j]));
				numberCell.text(board[i][j]);
			}
			judge[i][j] = false;
		}
		//$("#grid-container").append("<div id='gameover' class='gameover'></div>");
	}	
	//设置数字值的字体样式
   $(".number-cell").css("line-height", "150px");
   $(".number-cell").css("font-size", "90px");
}

function generateOneNumber()
{
	//生成一个随机位置，
	var randx = parseInt(Math.floor(Math.random() * 4));
	var randy = parseInt(Math.floor(Math.random() * 4));
	
	while(true)
	{
		if(board[randx][randy]==0)
		{
			break;
		}
		var randx = parseInt(Math.floor(Math.random() * 4));
		var randy = parseInt(Math.floor(Math.random() * 4));
	}
	//生成一个随机数
	//var randNumber = Math.random() < 0.5 ? (Math.random() < 0.5 ? (Math.random() < 0.5 ? 1 : 2) : (Math.random() < 0.5 ? 3 : 4)) : (Math.random() < 0.5 ? (Math.random() < 0.5 ? 5 : 6) : (Math.random() < 0.5 ? 7 : 8)); 
	var randNumber = Math.random() < 0.8 ? ((Math.random() < 0.7 ? (Math.random() < 0.5 ? 2 : 4) : 1)) : ((Math.random() < 0.5 ? ((Math.random() < 0.5 ? (Math.random() < 0.6 ? 32 : 64) : 16)) : 8));
	//显示这个数字
	board[randx][randy] = randNumber;
	//设置其显示的动画
	ShowNumberWithAnimation(randx,randy,randNumber);
	return true;
}


