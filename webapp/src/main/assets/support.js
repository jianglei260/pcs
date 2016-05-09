function getPosTop(i,j){
	return 30 + i * 260;
}
function getPosLeft(i,j){
	return 30 + j * 260;
}
function getNumberBackgroundColor(number){
	switch (number) {
        case 1:
        return "#eee4da";
        break;
        
        case 2:
        return "#ede0c8";
        break;
        
        case 4:
        return "#f2b179";
        break;
        
        case 8:
        return "#f59563";
        break;
        
        case 16:
        return "#f67c5f";
        break;
        
        case 32:
        return "#f65e3b";
        break;
        
        case 64:
        return "#edcf72";
        break;
        
        case 128:
        return "#edcc61";
        break;
        case 256:
        return "#9c0";
        break;
        case 512:
        return "#33b5e5";
        break;
        case 1024:
        return "#09c";
        break;
        case 2048:
        return "#a6c";g
        break;
        case 4096:
        return "#93c";
        break;
    }
}
function getNumberColor(number){
	if(number<=4)
	{
		return "#776e65";
	}
	return "white";
}
function canMoveLeft(board){
	for(var i = 0; i < 4; i++)
	{
		for(var j = 1; j < 4; j++)
		{
			if(board[i][j] != 0)
			{
				if(board[i][j - 1] == 0 || board[i][j - 1] == board[i][j] )
				{
					return true;
				}
			}
		}
	}
	return false;
}
function canMoveRight(board){
	for(var i = 0; i < 4; i++)
	{
		for(var j = 2; j >= 0; j--)
		{
			if(board[i][j] != 0)
			{
				if(board[i][j + 1] == 0 || board[i][j + 1] == board[i][j] )
				{
					return true;
				}
			}
		}
	}
	return false;
}
function canMoveUp(board){
	for(var j = 0; j < 4; j++)
	{
		for(var i = 1; i < 4; i++)
		{
			if(board[i][j] != 0)
			{
				if(board[i-1][j] == 0 || board[i-1][j] == board[i][j] )
				{
					return true;
				}
			}
		}
	}
	return false;
}
function canMoveDown(board){
	for(var j = 0; j < 4; j++)
	{
		for(var i = 2; i >= 0; i--)
		{
			if(board[i][j] != 0)
			{
				if(board[i+1][j] == 0 || board[i+1][j] == board[i][j] )
				{
					return true;
				}
			}
		}
	}
	return false;
}
function noBlokHorizontalCol(x, y1, y2, board){
	for(var i = y1 + 1; i < y2; i++)
	{
		if(board[x][i] != 0)
		{
			return false;
		}
	}
	return true;
}
function noBlokHorizontalRow(y, y1, y2, board){
	for(var i = y1 + 1; i < y2; i++)
	{
		if(board[i][y] != 0)
		{
			return false;
		}
	}
	return true;
}

function restartgame(){
	$("#gameover").remove();
	$("#score").text(0);
	newgame();
}

function sendgrade()
{
	//$("#123").append("<form action='sendRank.php' method='post' javascript:onsubmit()><input name='gameGrade' value="+score+" /><input name='playerName' value="+document.getElementById('name').value+" /></font>");
	/*$("#123").append("<form id = 'form1' name='form1' action='sendRank.php' method='post'><span name='grade'>score</span></form>");
	var form1 = document.getElementById("form1");
	from1.submit();*/
	$("#123").remove();
	$("#score").text(0);
	newgame();
}

//<form action='sendRank' method='post'>


/*function haveNullCell(board)
{
	for(var i = 0; i < 4; i++)
	{
		for(var j = 0; j < 4; j++)
		{
			if(board[i][j] == 0)
			{
				return true;
			}
		}
	}
	return false;
}*/
function nospace(board)
{
	for(var i = 0; i < 4; i++)
	{
		for(var j = 0; j < 4; j++)
		{
			if(board[i][j] == 0)
			{
				return false;
			}
		}
	}
	return true;
}

function nomove(board)
{
	if (canMoveDown(board) || canMoveLeft(board) || canMoveRight(board) || canMoveUp(board)) {
        return false;
    }
    return true;
}

function lookRanking()
{
	//window.navigate("lookRank.php");
	location = "lookRank.php";
}
