<template>
	<div class="mine">
		<div class="mine-header">
			<div class="header-box" v-if="islogin" @click="handleRoute">
				<img v-if="userData.avatar" :src="userData.avatar">
			</div>
      <div class="header-text" v-if="islogin==false"  @click="handleRoute">
        <span>Hi，等你好久了！</span>
      </div>
			<a @click="login" v-if="islogin==false">登录</a>
      <a @click="regist" v-if="islogin==false">注册</a>
      <p style="color:#fff;" v-if="islogin==true">{{userData.userName}}</p>
		</div>
		<div class="mine-sec">
			<group>
				<cell title="个人信息" @click.native="gotoPersonalInfo">
					<img slot="icon" width="20" style="display:block;margin-right:10px;" src="../../static/images/icon_grxx@2x.png">
				</cell>
				<!-- <cell title="下载APP" is-link @click.native="handleDownLoad">
					<img slot="icon" width="20" style="display:block;margin-right:10px;" src="../../static/images/设置@2x.png">
				</cell> -->
				<cell title="退出登录" is-link @click.native="logout" v-if="islogin==true">
					<img slot="icon" width="20" style="display:block;margin-right:10px;" src="../../static/images/icon_exit@2x.png">
				</cell>
			</group>
		</div>
		<div class="footer">
	      <a @click="home">
	        <div class="index">
	          <img src="../../static/images/tab_icon_home_nor@2x.png">
	          <p>首页</p>
	        </div>
	      </a>
	      <a @click="loan">
	        <div class="discover">
	          <img src="../../static/images/tab_icon_daikuan_nor@2x.png">
	          <p>贷款</p>
	        </div>
	      </a>
        <a @click="shequ">
          <div class="discover">
            <img src="../../static/images/shequ_icon.png">
            <p>攻略</p>
          </div>
        </a>
	        <div class="mine">
	          <img src="../../static/images/tab_icon_mine_sel@2x.png">
	          <p>我的</p>
	        </div>
	    </div>
	</div>
</template>

<script>
import { dumbWrapper } from '@/Ajax/vars'
import {logout,getUserInfo} from '@/Ajax/post'
import { Cell, CellBox, CellFormPreview, Group, Badge } from 'vux'
export default {
  name: '',
  components: {
  	 Group,
    Cell,
    CellFormPreview,
    CellBox,
    Badge
  },
  data () {
    return {
    	islogin:false,
    	userData:[],
      query:''
    }
  },
  methods:{
    home(){
      router.push({path:'/Homepage?channel='+this.query})
    },
    shequ(){
      router.push({path:'/consultation?channel='+this.query})
    },
    loan(){
      router.push({path:'/loanProducts?channel='+this.query})
    },
    handleRoute(){
      router.push({path:'/changePhoto?channel='+this.query})
    },
    login(){
      router.push({path:'/login?channel='+this.query})
    },
    regist(){
      router.push({path:'/regist?channel='+this.query})
    },
  	logout(){
      promise:logout({})
  		dumbWrapper({
  			promise:logout({}),
  			successCB:(e)=>{
  				setTimeout(function(){location.reload();},1000)
  			}
  		})
  	},
  	gotoPersonalInfo(){
  		router.push({path:'/personalInfo?channel='+this.query})
  	},
  },
  mounted(){
  	dumbWrapper({
  		promise:getUserInfo({}),
  		successCB:(e)=>{
  			if(e.state==0){
	  			this.islogin=true;
	  			this.userData=e.data;
  			}else if(e.state==10005){
  				this.islogin=false;
	  			this.userData=[];
  			}
  		},
  		failCB:(e)=>{
  			this.islogin=false;
	  		this.userData=[];
  		}
  	});
    this.query = this.$route.query.channel;
  	// if(sessionStorage.user){
  	// 	this.islogin=true;
  	// 	this.userData=JSON.parse(sessionStorage.user);
  	// 	console.log(this.userData)
  	// }
  },
}
</script>

<style lang='less' scoped>
.mine{
	background:#f5f5f5;
}
.mine-sec{
	margin-top:-10px;
}
.mine-header{
	background:url("../../static/images/bg@2x.png");
	background-size:100% 100%;
	padding-top:60px;
	padding-bottom:30px;
	text-align:center;
	.header-box{
		width:100px;
		height:100px;
		margin-left:50%;
		transform: translate(-50px,0);
		border-radius:50%;
		overflow:hidden;
		background:url("../../static/images/me_selected@2x.png");
		background-size:cover;
		margin-bottom:15px;
		img{height:100%;}
	}
  .header-text{
    height: 100px;
    line-height: 100px;
    font-size: 18px;
    color: white;
  }
	a{
		color:#fff;
    background: #FDBB6C;
    padding: 10px 30px;
    border-radius: 20px;
    margin: 0 10px;
	}
}
  div.footer{
    background:#fff;
    box-sizing:border-box;
    padding-top:5px;
    text-align:center;
    width:100%;
    height:50px;
    border-top:1px solid #ddd;
    position:fixed;
    bottom:0;
    color:#999;
    .index{
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;}
      img{width:25px;}
    }
    .discover{
      margin-left:60px;
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;}
      img{width:25px;}
    }
    .mine{
    	background:#fff;
      margin-left:60px;
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;color:#F98902;}
      img{width:25px;}
    }
  }
</style>
