<template>
	<div id="signUp">
		<div class="head-box">
			<img src="../../static/img/hbg@2x.png" class="bg">
			<img src="../../static/img/29.png" class="logo">
			<span>用钱大管家</span>
		</div>
		<div class="regist">
	    <div>
	    	<group>
			    <x-input placeholder="请输入手机号" v-model="form.phoneNum" :max="11" is-type="china-mobile"></x-input>
			  </group>
			  <flexbox>
		      <flexbox-item :span="7">
		      	<group>
		      		<x-input placeholder="请输入验证码" v-model="form.checkCode">
			        </x-input>
		      	</group>
		      </flexbox-item>
		      <flexbox-item :span="5">
		      	<x-button style="color:#fff;font-size:1rem;margin-top:3px;background:#fe8622;" id="button" 
		      	@click.native="getCode"
	          v-show="clockVisible==false">获取验证码</x-button>
	          <a class="clock" v-show="clockVisible==true">{{clock}} s</a>
		      </flexbox-item>
		    </flexbox>
		    <group>
		    	<x-input :min="8" :max="20" placeholder="请输入密码" type="password" v-model="form.password">
	        </x-input>
		    </group>
	      <box gap="25px 0">
	        <x-button style="color:#fff;background:#fe8622;padding:5px;" @click.native="regist">注册</x-button>
	      </box>
	      <!-- <div style="text-align:center;font-size:13px;">
	        <span class="unchecked-icon" v-show="agreeCheck==0" @click="agreeCheck=1"></span>
	        <span class="checked-icon" v-show="agreeCheck==1" @click="agreeCheck=0"></span>
	        <span style="line-height:25px;">我已阅读并同意</span>
	        <span style="color:#00a8ff;line-height:25px;" @click="userAgreement">《用钱管家注册协议》</span>
	      </div> -->
	    </div>
	    <div v-transfer-dom id="checkCode">
	      <alert v-model="show" title="提示">验证码已发送，注意查收</alert>
	    </div>
		</div>
	</div>
</template>

<script>
import { dumbWrapper } from '@/Ajax/vars'
import {signUpByPassword,getCode} from '@/Ajax/post'
import { XInput, Group,XButton,Box,Flexbox, FlexboxItem,TransferDomDirective as TransferDom, Alert} from 'vux'
export default {
	name:'',
	directives: {
    TransferDom
  },
  components: {
    XInput, Group,
    XButton,Box,
    Flexbox, FlexboxItem,
    Alert
  },
	data(){
		return{
			clock:60,
      clockVisible:false,
      form:{
        phoneNum:null,
        checkCode:null,
        password:null,
        source:'网页'
      },
      agreeCheck:0,
      show:false
		}
	},
	methods:{
		userAgreement(){
      router.push({path:'/agreement'})
    },
    regist(){
      let data=[];
      if(!this.form.phoneNum||!this.form.checkCode||!this.form.password){
        alert('请确认输入的内容不为空！');
      }else{
        data=this.form;
        dumbWrapper({
          promise:signUpByPassword(data),
          successCB:(e)=>{
            if(e.state==0){
              setTimeout(function(){
                router.push({path:'/download'})
              },500)
            }else if(e.state==30002){
              alert('手机号或验证码错误！')
            }else if(e.state==10000){
              alert("密码少于八位！")
            }
          }
        })
      }
    },
    getCode(){
      var self=this;
      if(!this.form.phoneNum){
        alert('请填写手机号！')
      }else{
        dumbWrapper({
          promise:getCode({
            phone:this.form.phoneNum
          }),
          successCB:(e)=>{
            if(e.state==0){
              this.show=true;
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
	},
	created(){},
	mounted(){
		document.title="用钱大管家";
		document.getElementById('signUp').style.minHeight=document.documentElement.clientHeight+'px'
	},
}
</script>

<style lang='less'>
#signUp{
	background:rgb(252,236,184);
	.head-box{
		position:relative;
		img.bg{
			display:block;
			width:100%;
		}
		img.logo{
			width:30px;
			border-radius:5px;
			position:absolute;
			top:10px;
			left:10px;
			border:1px solid rgba(255,255,255,0.3);
		}
		span{
			color:#fff;
			position:absolute;
			top:10px;
			left:50px;
			line-height:30px;
		}
	}
	.regist{
		padding:15px 20px;
	}
	span.checked-icon{
    display:inline-block;
    width:22px;
    height:22px;
    background:url("../../static/img/Group 5@2x.png");
    background-size:100% 100%;
    margin-bottom:-5px;
  }
  span.unchecked-icon{
    display:inline-block;
    width:20px;
    height:20px;
    border:1px solid #fe8622;
    border-radius:10px;
    background-size:100% 100%;
    margin-bottom:-5px;
  }
  a.clock{
  	color:#fff;
    text-decoration:none;
    padding:5px 15px;
    background:#fe8622;
    border-radius:10px;
  }
}
</style>