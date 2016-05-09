<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="2048.css"/>
        <!--下面是引入jquery文件和js文件-->
        <script src="jquery-1.11.1.js"></script>
        <script src="support.js"></script>
        <script src="animation.js"></script>
        <script src="main.js"></script>
        <script src="game.js"></script>
        
        
    </head>

    <body>
        <header>
            <h1>2048</h1>
            <a href="javascript:newgame()" id="newgamebutton">New Game</a>
            
            <p>score: <span id="score">0</span> &nbsp; &nbsp; &nbsp; &nbsp;<span id="word">消除的最少数字:</span><span id="removeNum">4</span></p>
            
        </header>
  
        <div id="grid-container">
            <div class="grid-cell" id="grid-cell-0-0"></div>
            <div class="grid-cell" id="grid-cell-0-1"></div>
            <div class="grid-cell" id="grid-cell-0-2"></div>
            <div class="grid-cell" id="grid-cell-0-3"></div>
            <div class="grid-cell" id="grid-cell-1-0"></div>
            <div class="grid-cell" id="grid-cell-1-1"></div>
            <div class="grid-cell" id="grid-cell-1-2"></div>
            <div class="grid-cell" id="grid-cell-1-3"></div>
            <div class="grid-cell" id="grid-cell-2-0"></div>
            <div class="grid-cell" id="grid-cell-2-1"></div>
            <div class="grid-cell" id="grid-cell-2-2"></div>
            <div class="grid-cell" id="grid-cell-2-3"></div>
            <div class="grid-cell" id="grid-cell-3-0"></div>
            <div class="grid-cell" id="grid-cell-3-1"></div>
            <div class="grid-cell" id="grid-cell-3-2"></div>
            <div class="grid-cell" id="grid-cell-3-3"></div> 
            <div id="123">
                
            </div>   
        </div>
    </body>
</html>
