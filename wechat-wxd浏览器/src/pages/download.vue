<template>
	<div id="download">
		<div class="head-box">
			<img src="../../static/img/bg@2x.png">
		</div>
		<div class="logo-box">
			<img src="../../static/img/180.png">
			<p>立得钱包</p>
		</div>
		<div class="button-box">
			<a @click="handleDownload">点击下载APP</a>
		</div>
	</div>
</template>

<script>
export default {
	name:'',
	components:{

	},
	data(){
		return{

		}
	},
	methods:{
		handleDownload(){
      var u = navigator.userAgent;
      if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1){
        location.href = 'https://yqdgj.oss-cn-shanghai.aliyuncs.com/yqdgj.apk';
      }else  if (u.indexOf('iPhone') > -1){
        location.href = 'https://itunes.apple.com/cn/app/%E7%94%A8%E9%92%B1%E5%A4%A7%E7%AE%A1%E5%AE%B6/id1371816134?mt=8';
      }
    },
	},
	created(){},
	mounted(){
		document.title="下载APP";
		document.getElementById('download').style.minHeight=document.documentElement.clientHeight+'px'
	},
}
</script>

<style lang='less' scoped>
#download{
	background:#fff;
	.head-box{
		img{
			display:block;
			width:100%;
		}
	}
	.logo-box{
		text-align:center;
		margin-top:80px;
		img{
			width:120px;
			border-radius:15px;
		}
	}
	.button-box{
		text-align:center;
		margin-top:60px;
		a{
			background:rgb(0,174,255);
			color:#fff;
			padding:8px 20px;
			border-radius:20px;
		}
	}
}

</style>