<html>
	<head>
		<meta charset = "UTF-8" />
	</head>
	<body>
		<?php
		class mobie{
			
			public function getIP() {
		        $ip=getenv('REMOTE_ADDR');
		        /*$ip_ = getenv('HTTP_X_FORWARDED_FOR');
		        if (($ip_ != "") && ($ip_ != "unknown")) {
		            $ip=$ip_;
				}*/
		        return $ip;
		    }
			
			
		 public function getPhoneNumber() {
		        if (isset($_SERVER['HTTP_X_NETWORK_INFO'])) {
		            $str1 = $_SERVER['HTTP_X_NETWORK_INFO'];
		            $getstr1 = preg_replace('/(.*,)(11[d])(,.*)/i',"",$str1);
		            Return $getstr1;
		        }
		        elseif (isset($_SERVER['HTTP_X_UP_CALLING_LINE_ID'])) {
		            $getstr2 = $_SERVER['HTTP_X_UP_CALLING_LINE_ID'];
		            Return $getstr2;
		        }
		        elseif (isset($_SERVER['HTTP_X_UP_SUBNO'])) {
		         $str3 = $_SERVER['HTTP_X_UP_SUBNO'];
		            $getstr3 = preg_replace('/(.*)(11[d])(.*)/i',"",$str3);
		            Return $getstr3;
		        }
		        elseif (isset($_SERVER['DEVICEID'])) {
		            Return $_SERVER['DEVICEID'];
		        }
		
		        else {
		
		            Return false;
		        }
		    }
			
			
		 public function getHttpHeader() {
		        $str = "";
		        foreach ($_SERVER as $key => $val) {
		            //$gstr = str_replace("&","&",$val);
		           // $str.= "$key -> ".$gstr."rn";
		           $str = $key.":".$val."</br>";
		        }
		        Return $str;
		    }
			
			
		public function getUA() {
		        if (isset($_SERVER['HTTP_USER_AGENT'])) {
		            Return $_SERVER['HTTP_USER_AGENT'];
		        }
		        else {
		            Return false;
		        }
		    }
			
		public function getPhoneType() {
		        $ua = $this->getUA();
		        if($ua!=false) {
		            $str = explode(" ",$ua);
		            Return $str[0];
		        }
		        else {
		            Return false;
		        }
		    }
		
		public function isOpera() {
		        $uainfo = $this->getUA();
		   if (preg_match('/.*Opera.*/i',$uainfo)) {
		            Return true;
		        }
		        else {
		            Return false;
		        }
		    }
		}
		$shouji = new mobie();
		echo "1".$shouji->getIP()."</br></br></br>";
		echo "2".$shouji->getPhoneType()."</br></br></br>";
		echo "3".$shouji->getUA()."</br></br></br>";
		//echo "4".$shouji->getHttpHeader()."</br></br></br>";
		echo "5".$shouji->getPhoneNumber()."</br></br></br>";
		?>
	</body>
</html>