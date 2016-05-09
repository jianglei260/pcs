
		var startX = 0;
		var startY = 0;
		var endX = 0;
		var endY = 0;
		var key = 0;
        function handleTouchEvent(event) {
    		//只跟踪一次触摸
			//alert(event.touches.length);
            if(event.touches.length == 0 || event.touches.length == 1)
            {
                var output = document.getElementById("grid-container");
                switch (event.type) 
                {
                    case "touchstart":
                        //output.innerHTML = "Touch started (" + event.touches[0].clientX + "," + event.touches[0].clientY + ")";
                         startX = event.touches[0].clientX;
                         startY = event.touches[0].clientY;
                        break;
                    case "touchmove":
                        event.preventDefault(); //阻止滚动
                        //output.innerHTML += "<br>Touch moved (" + event.changedTouches[0].clientX + "," + event.changedTouches[0].clientY + ")";
                        break;
                    case "touchend":
                        //output.innerHTML += "<br>Touch ended (" + event.changedTouches[0].clientX + "," + event.changeTouches[0].clientY + ")";
                         endX = event.changedTouches[0].clientX;
                         endY = event.changedTouches[0].clientY;
                        var lastX = endX -   startX; 
                		var lastY = endY -   startY;
                		if( lastX > 10)
                		{
                			if(Math.abs(lastX) > Math.abs(lastY))
                			{
                				if(moveRight())//向右
								{
									setTimeout("generateOneNumber()",300);
										//判断游戏是否结束
										setTimeout("isgameover()",400);
								}
                			}else
                			{
                				if(lastY < 10)
                				{
                					if(moveUp())
									{
										setTimeout("generateOneNumber()",300);
										//判断游戏是否结束
										setTimeout("isgameover()",400);
									}
                				}else{
                					if(moveDown())
									{
										setTimeout("generateOneNumber()",300);
										//判断游戏是否结束
										setTimeout("isgameover()",400);
									}
                				}
                			}
                		}
                		if( lastX < -10)
                		{
                			if(Math.abs(lastX) > Math.abs(lastY))
                			{
                				if(moveLeft())//向右
								{
									setTimeout("generateOneNumber()",300);
										//判断游戏是否结束
										setTimeout("isgameover()",400);
								}
                			}else
                			{
                				if(lastY < 10)
                				{
                					if(moveUp())
									{
										setTimeout("generateOneNumber()",300);
										//判断游戏是否结束
										setTimeout("isgameover()",400);
									}
                				}else{
                					if(moveDown())
									{
										setTimeout("generateOneNumber()",300);
										//判断游戏是否结束
										setTimeout("isgameover()",400);
									}
                				}
                			}
                		}
                		
                        //alert(endX);
                        break;   
                }
            }
        }
        document.addEventListener("touchstart", handleTouchEvent, false);
        document.addEventListener("touchend", handleTouchEvent, false);
        document.addEventListener("touchmove", handleTouchEvent, false);


$(document).keydown(function(event)
{
	//监听按键
	switch(event.keyCode)
	{
		case 37 || 68://left
			//moveLeft()用来判断是否能够向左移动
			if(moveLeft())
			{
				setTimeout("generateOneNumber()",300);
				//判断游戏是否结束
				setTimeout("isgameover()",400);
			}
		break;
		case 38 || 87://up
		if(moveUp())
			{
				setTimeout("generateOneNumber()",300);
				//判断游戏是否结束
				setTimeout("isgameover()",400);
			}
		break;
		case 39 || 65://right
		if(moveRight())
			{
				setTimeout("generateOneNumber()",300);
				//判断游戏是否结束
				setTimeout("isgameover()",400);
			}
		break;
		case 40 || 83://down
		if(moveDown())
			{
				setTimeout("generateOneNumber()",300);
				//判断游戏是否结束
				setTimeout("isgameover()",400);
			}
		break;
	}
});

function moveLeft()
{
	if(!canMoveLeft(board))
	{
		return false;
	}
	//遍历方格信息
	for(var i = 0; i < 4; i++)
	{
		for(var j = 1; j < 4; j++)
		{
			if(board[i][j] != 0)
			{
				for(var k = 0; k < j; k++)
				{
					if(board[i][k] == 0 && noBlokHorizontalCol(i, k, j, board))
					{
						//才能移动
						showMoveAnimation(i,j,i,k);
						board[i][k] = board[i][j];
						board[i][j] = 0;
						//judge[i][j] = true;
						continue;
					}else if((board[i][k] == board[i][j] && noBlokHorizontalCol(i, k, j, board)) && !judge[i][j])
					{
						//才能移动
						showMoveAnimation(i,j,i,k);
						//$("#removeNum").text(removeNum(board));
						if(board[i][j] < removeNum(board))
						{
							board[i][k] += board[i][j];
							score += board[i][j] * 2;
						}else
						{
							board[i][k] -= board[i][j];
							score += board[i][j] * 2;
						}
						board[i][j] = 0;
						$("#score").text(score);
						$("#removeNum").text(removeNum(board));
						//judge[i][k] = true;
						if(j != 3)
						{
							judge[i][j+1] = true;
						}
						continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()", 200);
	//updateBoardView();
	return true;
}

function moveRight(){
	if(!canMoveRight(board))
	{
		return false;
	}
	//遍历方格信息
	for(var i = 0; i < 4; i++)
	{
		for(var j = 3; j >= 0; j--)
		{
			if(board[i][j] != 0)
			{
				for(var k =3; k > j; k--)
				{
					if(board[i][k] == 0 && noBlokHorizontalCol(i, j, k, board))
					{
						//才能移动
						showMoveAnimation(i,j,i,k);
						board[i][k] = board[i][j];
						//judge[i][j] = true;
						board[i][j] = 0;
						continue;
					}else if((board[i][k] == board[i][j] && noBlokHorizontalCol(i, j, k, board)) && !judge[i][j])
					{
						//才能移动
						showMoveAnimation(i,j,i,k);
						if(board[i][j] < removeNum(board))
						{
							board[i][k] += board[i][j];
							score += board[i][j] * 2;
						}else
						{
							board[i][k] -= board[i][j];
							score += board[i][j] * 2;
						}
						board[i][j] = 0;
						$("#score").text(score);
						$("#removeNum").text(removeNum(board));
						$("#removeNum").text(removeNum(board));
						//judge[i][k] = true;
						if(j != 0)
						{
							judge[i][j-1] = true;
						}
						continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()", 200);
	//updateBoardView();
	return true;
}
function moveUp(){
	if(!canMoveUp(board))
	{
		return false;
	}
	//遍历方格信息
	for(var j = 0; j < 4; j++)
	{
		for(var i = 1; i < 4; i++)
		{
			if(board[i][j] != 0)
			{
				for(var k =0; k < i; k++)
				{
					if(board[k][j] == 0 && noBlokHorizontalRow(j, k, i, board))
					{
						//才能移动
						showMoveAnimation(i,j,k,j);
						board[k][j] = board[i][j];
						board[i][j] = 0;
						//judge[i][j] = true;
						continue;
					}else if((board[k][j] == board[i][j] && noBlokHorizontalRow(j, k, i, board)) && !judge[i][j])
					{
						//才能移动
						showMoveAnimation(i,j,k,j);
						//$("#removeNum").text(removeNum(board));
						//在後面的那個updateBoardView()的時候進行了樣式的設置；
						if(board[i][j] < removeNum(board))
						{
							board[k][j] += board[i][j];
							score += board[i][j] * 2;
						}else
						{
							board[k][j] -= board[i][j];
							score += board[i][j] * 2;
						}
						board[i][j] = 0;
						$("#score").text(score);
						$("#removeNum").text(removeNum(board));
						//judge[k][j] = true;
						if(i != 3)
						{
							judge[i+1][j] = true;
						}
						continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()", 200);
	//updateBoardView();
	return true;
}
function moveDown(){
	if(!canMoveDown(board))
	{
		return false;
	}
	//遍历方格信息
	for(var j = 0; j < 4; j++)
	{
		for(var i = 3; i >= 0; i--)
		{
			if(board[i][j] != 0)
			{
				for(var k = 3; k > i; k--)
				{
					if(board[k][j] == 0 && noBlokHorizontalRow(j, i, k, board))
					{
						//才能移动
						showMoveAnimation(i,j,k,j);
						board[k][j] = board[i][j];
						board[i][j] = 0;
						//judge[i][j] = true;
						continue;
					}else if((board[k][j] == board[i][j] && noBlokHorizontalRow(j, i, k, board)) && !judge[i][j])
					{
						//才能移动
						showMoveAnimation(i,j,k,j);
						 
						if(board[i][j] < removeNum(board))
						{
							board[k][j] += board[i][j];
							score += board[i][j] * 2;
						}else
						{
							board[k][j] -= board[i][j];
							score += board[i][j] * 2;
						}
							board[i][j] = 0;
							$("#score").text(score);
							//judge[k][j] = true;
							if(i!=0)
							{
								judge[i-1][j] = true;
							}
							continue;
					}
				}
			}
		}
	}
	setTimeout("updateBoardView()", 200);
	//updateBoardView();
	return true;
}
function isgameover()
{
	
	if(nomove(board)&& nospace(board))
	{
		var allCookies = unescape(document.cookie);
		//allCookies = '<?php echo $_COOKIE["playName"];?>';
		//alert(allCookies);
		 //var the_cookie = document.cookie;
   		 var broken_cookies = allCookies.split(";");
   		 var nowvalue;
   		 for(var i = 0; i < broken_cookies.length; i++)
   		 {
   		 	nowvalue = broken_cookies[i];
   		 	if(nowvalue.substring(0, nowvalue.indexOf("=")) == "playName")
   		 	{
   		 		var the_value = nowvalue.substring(nowvalue.indexOf("=") + 1, nowvalue.length);
   		 		break;
   		 	}
   		 }
	    /*var the_first = broken_cookies[0];
	    var the_name = the_first.split("=")[0];
	    var the_value = the_first.split("=")[1];
		var edate = new Date();*/
		
		
		/*alert(broken_cookies);
		alert(the_first);
		alert(the_name);
		alert(the_value);*/
		/*if(broken_cookies.length == 0)
		{
			//alert("1234");
			document.cookie = escape("playName = playName;espire="+edate.setFullYear(edate.getFullYear() + 1));
			//the_value = unescape(document.cookie);
			var allCookies = unescape(document.cookie);
			//allCookies = '<?php echo $_COOKIE["playName"];?>';
			 var the_cookie = document.cookie;
			 var broken_cookies = the_cookie.split(":");
			var the_first = broken_cookies[0];
			var the_name=the_first.split("=")[0];
	    var the_value = the_first.split("=")[1];
		}*/
		    /*$("#123").append("<div id='gameover' class='gameover'><p>本次得分</p><span>"+score+"</br></span> <input style='text' id='name' name='name' value="+'<?php echo $uerCookies; ?>'+"> <a href='javascript:restartgame();' id='restartgamebutton'>Restart</a> <a href='javascript:sendgrade();' id ='send'>上传分数</a></div>");*/
			//$("#123").append("<form id = 'form1' name='form1' action='sendRank.php' method='post'><span name='grade'>score</span></form>");
			/*$("#123").append("<div id='gameover' class='gameover'><p>本次得分</p><form id = 'form1' name='form1' action='sendRank.php' onsubmit(sendgrade()) method='post'><span>"+score+"</br></span><input style='text' id='gameGrade' name = 'gameGrade' value="+score+"><input style='text' id='name' name='playerName' value="+the_value+"> <a href='javascript:restartgame();' id='restartgamebutton'>Restart</a> <input type='submit' id ='send' value='上传分数'></form></div>");
			
			var playName;
			//uerCookies = document.getElementById("uerCookies").value;
			playName = document.getElementById("name").value;
			//payName.value = uerCookies;
			document.cookie = escape("playName = "+playName+";espire="+edate.setFullYear(edate.getFullYear() + 1));
		    var gameover = $("#gameover");
		    gameover.css("width", "460px");
		    gameover.css("height", "460px");
		    //RGBA  R：红色值。正整数 | 百分数     G：绿色值。正整数 | 百分数    B：蓝色值。正整数| 百分数     A：透明度。取值0~1之间
		    gameover.css("background-color", "rgba(0, 0, 0, 0.6)");
		    //gameover.css("background-color", "red");*/
		   $("#123").append("<div id='gameover' class='gameover'><p>本次得分</p><form id = 'form1' name='form1' action='sendRank.php' onsubmit(sendgrade()) method='post'><span>"+score+"</br></span><input style='text' id='gameGrade' name = 'gameGrade' value="+score+"><input style='text' id='name' name='playerName' value="+the_value+"> <a href='javascript:restartgame();' id='restartgamebutton'>Restart</a> <input type='submit' id ='send' value='上传分数'></form></div>");
			
			var playName;
			//uerCookies = document.getElementById("uerCookies").value;
			playName = document.getElementById("name").value;
			//payName.value = uerCookies;
			document.cookie = escape("playName = "+playName+";espire="+edate.setFullYear(edate.getFullYear() + 1));
		    var gameover = $("#gameover");
		    gameover.css("width", "460px");
		    gameover.css("height", "460px");
		    //RGBA  R：红色值。正整数 | 百分数     G：绿色值。正整数 | 百分数    B：蓝色值。正整数| 百分数     A：透明度。取值0~1之间
		   gameover.css("background-color", "rgba(0, 0, 0, 0.6)");
	}
	else{
		return;
	}
}

function removeNum(board)
{
	switch(true){
		case(score < 100):
			return 4;
			break;
		case(score < 300):
			return 8;
			break;
		case(score < 600):
			return 16;
			break;
		case(score < 1000):
			return 32;
			break;
		case(score < 1500):
			return 64;
			break;
		case(score < 2100):
			return 128;
			break;
		case(score < 3000):
			return 256;
			break;
		case(score < 4400):
			return 512;
			break;
		case(score < 6200):
			return 1204;
			break;
			case(score < 10000):
			return 2048;
			break;
		case(score < 18000):
			return 4096;
			break;
	}
}
