<html>
	<head>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
		<meta charset="UTF-8" />
	</head>
</html>
<center>排行榜</center>
<?php
    header("Content-Type:text/html; charset=utf-8");
    /*替换为你自己的数据库名*/
	$dbname = 'HbKXeTInbqAdjynhOkvH';
			/*填入数据库连接信息*/
			$host = 'sqld.duapp.com';
			$port = 4050;
			$user = 'hXC3HL55GN8Gj371oqmul3XL';//用户名(api key)
			$pwd = 'I8anF2uhdjuaPXEO92yKtt3dlVAFXAUi';//密码(secret key)
			 /*以上信息都可以在数据库详情页查找到*/
			 
			/* $name = $_POST[playerName];
			 $grade = $_POST[gameGrade];
			 $id = getenv('REMOTE_ADDR');
			 $i = 0;*/
			 
			/*接着调用mysql_connect()连接服务器*/
			$link = @mysql_connect("{$host}:{$port}",$user,$pwd,true);
			if(!$link) {
				die("Connect Server Failed: " . mysql_error());
			}
			/*连接成功后立即调用mysql_select_db()选中需要连接的数据库*/
			if(!mysql_select_db($dbname,$link)) {
				die("Select Database Failed: " . mysql_error($link));
			}
	/*至此连接已完全建立，就可对当前数据库进行相应的操作了*/
	
	$i = 0;
	//创建一个数据库表
	$sql = "create table if not exists lookRank(
			id varchar(1024), 
			name varchar(1024),
			grade varchar(1024))";
	$ret = mysql_query($sql, $link);
	if ($ret === false) {
		die("Create Table Failed: " . mysql_error($link));
	} else {
		$sql = "select * from lookRank";
		$res = mysql_query($sql, $link) or die("<h1>目前没有排行<h1>");
		$grade = array();
		while($row = mysql_fetch_row($res))
		{	
			 $i++;
			 $grade[$row[1]] = $row[2];
			// echo $row[1]."----------".$row[2];
			// /*var_dump($row);*/
			// echo "</br>";	
		}
		arsort($grade);
		// print_r($grade);
		echo "<br>";
		echo "<br>";
		$num = 0;
		foreach($grade as $x => $x_value)
	    {
	    	$num++;
	    	echo "第".$num."名:"." ^_^ ". $x ." ^_^ ". "---------" . $x_value;
	    	echo "<br>";
	    }
		
		if($i == 0)
		{
			echo "<h1>目前没有排行<h1>";
		}
		
	}
	 mysql_free_result($res);
	 
	/*显式关闭连接，非必须*/
	mysql_close($link);
?>

