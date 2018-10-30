<template>
	<div>
		<div class="title">
      <p>{{$route.query.name}}</p>
      <div class="icon-back" @click="goBack">
        <img src="../../static/images/nav_back@2x.png">
      </div>
    </div>
		<!--<iframe :src="url" id="box" scrolling="auto" style="margin-bottom:-15px;"></iframe>-->
    <object type="text/html" :data="url" id="box"></object>
	</div>
</template>

<script>
export default {
	name:'',
	components:{

	},
	data(){
		return{
      url: ''
		}
	},
	methods:{
		goBack(){
      window.history.go(-1);
    },
	},
	created(){},
	mounted(){
    this.url = encodeURI(this.$route.query.url);
		document.getElementById('box').style.height=window.screen.height-40+'px'
    // var url = document.getElementById('box').contentWindow.location.href;
    // if(url.indexOf("bizOrderId") != -1) {
    //   console.log(url);
    // }
  },
}
</script>

<style lang='less' scoped>
#box{
	width:100%;
	margin-top:40px;
  border: 0;
  margin-bottom:-15px;
}
#box body,#box html{
  width: 100%;
}
.title{
    position:fixed;
    top:0;
    width:100%;
    text-align:center;
    background:#fff;
    z-index:100;
    border-bottom:1px solid #eee;
    p{
      font-size:16px;
      padding:8px 0;
    }
    .icon-back{
      height:22px;
      position:absolute;
      left:5px;
      top:0;
      padding:10px 5px;
      img{height:100%;}
    }
  }
</style>
