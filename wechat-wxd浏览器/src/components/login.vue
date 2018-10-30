<template>
	<div class="login" style="padding-top:45px;background: white;padding-bottom: 10px;">
		<div class="title">
	      <p>登录</p>
	      <div class="icon-back" @click="goBack">
	        <img src="../../static/images/nav_back@2x.png">
	      </div>
	    </div>
	    <div>
	    	<tab :line-width="2" custom-bar-width="120px" bar-active-color="#F28703" v-model="loginWay" style="z-index:50;width:80%;margin:40px auto;">
		      <tab-item selected active-class="active-6-3" style="background: white!important;color: #BCBDBE;font-size: 16px">验证码登录</tab-item>
		      <tab-item active-class="active-6-3" style="background: white!important;color: #BCBDBE;font-size: 16px">密码登录</tab-item>
		    </tab>
        <div v-show="loginWay==0">
          <group style="width: 80%;margin: 0px auto;">
            <x-input title="请输入手机号" :max="11" type="tel" placeholder="请输入手机号" v-model="form2.phoneNum" style="padding: 10px 0;border-bottom: 1px solid #ccc">
              <img slot="label" style="padding-right:10px;display:block;" src="../../static/images/icon_phone@2x.png" width="20" height="24">
            </x-input>
          </group>
          <div style="width: 80%;margin: 17px auto;overflow: hidden">
            <group style="width: 40%;margin: 0px auto;float: left">
              <x-input placeholder="请输入验证码" v-model="form2.checkCode" style="padding: 10px 0;border-bottom: 1px solid #ccc;">
                <!--<img slot="label" style="padding-right:10px;display:block;" src="../../static/images/yzm@2x.png" width="20" height="24">-->
              </x-input>
            </group>
            <x-button slot="right" plain style="border:none;float:left;color:white;width:50%;line-height:1.1rem;font-size:1rem;background:#F88A03;padding: 10px 20px;margin: 20px 0 0 10%;" @click.native="getCode"
                      v-show="clockVisible==false">获取验证码</x-button>
            <a slot="right" class="clock" v-show="clockVisible==true">{{clock}} s</a>
          </div>

          <box gap="25px 15px">
            <x-button style="color:#fff;background:#F28703;border-radius: 30px;padding:5px;" @click.native="checkCodeLogin">登录</x-button>
          </box>
          <!-- <div style="text-align:center;font-size:13px;">
                <span class="unchecked-icon" v-show="agreeCheck==0" @click="agreeCheck=1"></span>
                <span class="checked-icon" v-show="agreeCheck==1" @click="agreeCheck=0"></span>
                <span style="line-height:25px;">我已阅读并同意</span>
                <span style="color:#00a8ff;line-height:25px;" @click="userAgreement">《用钱管家注册协议》</span>
            </div> -->
        </div>
		    <div v-show="loginWay==1">
		      <group style="width: 80%;margin: 0px auto;">
	        	<x-input title="请输入手机号" :max="11" type="tel" placeholder="请输入手机号" v-model="form.phoneNum" style="padding: 10px 0;border-bottom: 1px solid #ccc">
		          <img slot="label" style="padding-right:10px;display:block;" src="../../static/images/icon_phone@2x.png" width="20" height="24">
		        </x-input>
		        <x-input title="请输入密码" placeholder="请输入密码" type="password" v-model="form.password" style="padding: 10px 0;margin-top: 30px;border-bottom: 1px solid #ccc">
		          <img slot="label" style="padding-right:10px;display:block;" src="../../static/images/icon_password@2x.png" width="20" height="24">
		        </x-input>
		      </group>
        	  <box gap="25px 15px">
        		  <x-button style="color:#fff;border-radius:30px;background:#F28703;padding:5px;" @click.native="psdLogin">登录</x-button>
        	  </box>
        	  <flexbox style="padding:0 15px;box-sizing:border-box;position: fixed;bottom: 10px;">
		        <flexbox-item><div class="flex-demo">
              <a style="text-decoration: none;" @click="forgetPsd">忘记密码？</a>
		        </div></flexbox-item>
		        <flexbox-item>
		        	<div class="flex-demo" style="text-align:right;">
              <a style="text-decoration: none;">立即</a><a style="color:#F28703;text-decoration: none;" @click="regist">注册</a>
			    </div></flexbox-item>
		      </flexbox>
		    </div>
	    </div>
	</div>
</template>
<script>
import { dumbWrapper } from '@/Ajax/vars'
import {loginByPassword,getCode,login} from '@/Ajax/post'
import { Tab, TabItem,XInput, Group,XButton,Box,Flexbox, FlexboxItem} from 'vux'
export default {
  name: '',
  components: {
  	Tab,
    TabItem,
    XInput, Group,
    XButton,Box,
    Flexbox, FlexboxItem,
  },
  data () {
    return {
      clock:60,
      clockVisible:false,
    	form:{
    		phoneNum:null,
    		password:null
    	},
      form2:{
        phoneNum:null,
        checkCode:null,
        source:'网页'
      },
    	loginWay:0,
    	agreeCheck:0,
      query:''
    }
  },
  methods:{
    regist(){
      router.push({path:'/regist?channel='+this.query})
    },
    forgetPsd(){
      router.push({path:'/forgetPsd?channel='+this.query})
    },
    userAgreement(){
      router.push({path:'/agreement?channel='+this.query})
    },
    checkCodeLogin(){
      if(!this.form2.phoneNum||!this.form2.checkCode){
          alert('请确认输入的内容不为空！');
      }else{
        let data=[];
        if (this.$route.query.channel) {
          this.form2.channelTag = this.$route.query.channel
        }
        data=this.form2;
        let self = this;
        dumbWrapper({
          promise:login(data),
          successCB:(e)=>{
            if(e.state==0){
              router.push({path:'/Homepage?channel='+self.query})
              //   setTimeout(function(){
              //
              // },500)
            }else if(e.state==30002){
              alert('手机号或验证码错误！')
            }
          }
        })
      }
    },
    getCode(){
      var self=this;
      if(!this.form2.phoneNum){
        alert('请填写手机号！')
      }else{
        dumbWrapper({
          promise:getCode({
            phone:this.form2.phoneNum
          }),
          successCB:(e)=>{
            if(e.state==0){
              this.clockVisible=true;
              this.clock=60;
              var timer=setInterval(function(){
                if(self.clock>0){
                  self.clock--;
                }else{
                  clearInterval(timer);
                  self.clockVisible=false;
                }

              },1000)
            }
          }
        })
      }
    },
  	psdLogin(){
      if(!this.form.phoneNum||!this.form.password){
          alert('请确认输入的内容不为空！');
      }else{
    		let data=[];
        data=this.form;
        let self = this;
    		dumbWrapper({
    			promise:loginByPassword(data),
    			successCB:(e)=>{
            if(e.state==0){
      				setTimeout(function(){
      					router.push({path:'/Homepage?channel='+self.query})
      				},500)
            }else if(e.state==10003){
              alert('密码错误！')
            }else{
              alert('请检查手机号或者密码是否正确！')
            }
    			}
    		})
      }
  	},
  	goBack(){
      router.push({path:'/mine?channel='+this.query})
    },
  },
  mounted(){
    document.getElementsByClassName("login")[0].style.height = window.innerHeight-56+"px";
    this.query = this.$route.query.channel;
  },
}
</script>

<style lang='less' scoped>
.login{
  height: 100%;
  background:white;
  a.clock{
    display: inline-block;
    text-decoration:none;
    padding:3px 10px;
    background:#ddd;
    border-radius:10px;
    height: 100%;
    margin-top: 22px;
    margin-left: 10%;
  }
	a{
		text-decoration:underline;
	}
	span.checked-icon{
		display:inline-block;
		width:22px;
		height:22px;
		background:url("../../static/images/check@2x.png");
		background-size:100% 100%;
		margin-bottom:-5px;
	}
	span.unchecked-icon{
		display:inline-block;
		width:22px;
		height:22px;
		background:url("../../static/images/uncheck@2x.png");
		background-size:100% 100%;
		margin-bottom:-5px;
	}
}
.active-6-3 {
  color: #F28703 !important;
}
.title{
	padding:5px 0;
  position:fixed;
  top:0;
  width:100%;
  text-align:center;
  background:#fff;
  z-index:100;
  border-bottom:1px solid #eee;
  p{
    font-size:14px;
    padding:6px 0;
  }
  .icon-back{
    margin:5px 0;
    height:24px;
    position:absolute;
    left:5px;
    top:0;
    padding:5px;
    img{height:100%;}
  }
}
</style>
