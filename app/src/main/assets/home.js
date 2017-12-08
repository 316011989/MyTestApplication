
//判断IOS还是Android
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

	alert("4444");
	// 城市列表信息
	function showInfos(fname){
		alert("message")
		if(isAndroid){
			alert("android")
			window.android.fname();
		}else{
			alert("ios")
			fname();
		}
	}


	function appRequest(  ){




	}
