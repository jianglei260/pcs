<html>
	<head>
		
	</head>
	<body>
		<?php
			//require_once "cookies.php";
			header("Content-Type:text/html; charset=utf-8");
			  /*替换为你自己的数据库名*/
			$dbname = 'HbKXeTInbqAdjynhOkvH';
			/*填入数据库连接信息*/
			$host = 'sqld.duapp.com';
			$port = 4050;
			$user = 'hXC3HL55GN8Gj371oqmul3XL';//用户名(api key)
			$pwd = 'I8anF2uhdjuaPXEO92yKtt3dlVAFXAUi';//密码(secret key)
			 /*以上信息都可以在数据库详情页查找到*/
			 $name = @$_POST[playerName];
			 $grade = @$_POST[gameGrade];
			 $id = getenv('REMOTE_ADDR');
			 $i = 0;
			 setcookie('playName',$name,time()+3600*24*7);
			/*接着调用mysql_connect()连接服务器*/
			$link = @mysql_connect("{$host}:{$port}",$user,$pwd,true);
			if(!$link) {
				die("Connect Server Failed: " . mysql_error());
			}
			/*连接成功后立即调用mysql_select_db()选中需要连接的数据库*/
			if(!mysql_select_db($dbname,$link)) {
				die("Select Database Failed: " . mysql_error($link));
			}
			// setcookie('playName',$name,time()+3600*24*7);

			 //echo "<script type='text/javascript'>test();</script>";
			 //echo "<script>document.cookie = escape('playName = ".playName.";espire='+edate.setFullYear(edate.getFullYear() + 1));</script>"
			/*至此连接已完全建立，就可对当前数据库进行相应的操作了*/
			//创建一个数据库表
			$sql = "create table if not exists lookRank(
					id varchar(1024), 
					name varchar(1024),
					grade varchar(1024))";
			$ret = mysql_query($sql, $link);
			if ($ret === false) {
				die("Create Table Failed: " . mysql_error($link));
			} else {
				$sql = "delete from lookRank where 1";
				$res = mysql_query($sql, $link) or die("数据删除失败");
				echo "数据删除成功";
					 //mysql_free_result($res) or die("资源释放失败");
					 }
			/*显式关闭连接，非必须*/
			mysql_close($link);
		?>
		<script>
			 //var playName = echo $name;
			 setTimeout(function()
		            {
		                location = 'index.php';
		            },4000)
		        
		</script>
		</body>
</html>