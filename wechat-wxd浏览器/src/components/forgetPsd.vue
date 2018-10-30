<template>
  <div class="forgetPsd" style="padding-top:45px;">
    <div class="title">
        <p>找回密码</p>
        <div class="icon-back" @click="goBack">
          <img src="../../static/images/nav_back@2x.png">
        </div>
      </div>
      <group style="width: 80%; margin:30% auto 0;">
        <x-input title="请输入手机号" :max="11" is-type="china-mobile" placeholder="请输入手机号" v-model="form.phoneNum" style="padding: 10px 0;border-bottom: 1px solid #ccc">
          <img slot="label" style="padding-right:10px;display:block;" src="../../static/images/icon_phone@2x.png" width="20" height="24">
        </x-input>

        <x-input title="请输入密码" :min="8" :max="20" placeholder="请输入密码" type="password" v-model="form.password" style="margin-top: 30px;padding: 10px 0;border-bottom: 1px solid #ccc">
          <img slot="label" style="padding-right:10px;display:block;" src="../../static/images/icon_password@2x.png" width="20" height="24">
        </x-input>
      </group>
      <div style="width: 80%;margin: 17px auto;overflow: hidden">
        <group style="width: 40%;margin: 0px auto;float: left">
          <x-input placeholder="请输入验证码" v-model="form.checkCode" style="padding: 10px 0;border-bottom: 1px solid #ccc;"></x-input>
        </group>
        <x-button slot="right" plain style="border:none;float:left;color:white;width:40%;line-height:1.1rem;font-size:1rem;background:#F88A03;padding: 10px 20px;margin: 20px 0 0 10%;" @click.native="getCode"
                  v-show="clockVisible==false">获取验证码</x-button>
        <a slot="right" class="clock" v-show="clockVisible==true">{{clock}} s</a>
      </div>
      <box gap="25px 0">
        <x-button style="width:80%;border-radius:30px;color:#fff;background:#F88A03;padding:5px;" @click.native="changePassword">提交</x-button>
      </box>
  </div>
</template>

<script>
import { dumbWrapper } from '@/Ajax/vars'
import {getCode,signUpByPassword} from '@/Ajax/post'
import { XInput, Group,XButton,Box} from 'vux'
export default {
  name:'',
  components:{
    XInput, Group,
      XButton,Box,
  },
  data(){
    return{
      clockVisible:false,
      form:{
        phoneNum:null,
        checkCode:null,
        password:null,
        source:'网页'
      },
      clock:60,
      query:""
    }
  },
  methods:{
    changePassword(){
      let data=[];
      if(!this.form.phoneNum||!this.form.checkCode||!this.form.password){
        alert('请确认输入的内容不为空！');
      }else{
        data=this.form;
        let self = this;
        dumbWrapper({
          promise:signUpByPassword(data),
          successCB:(e)=>{
            if(e.state==0){
              setTimeout(function(){
                router.push({path:'/login?channel='+self.query})
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
    goBack(){
      window.history.go(-1);
    },
  },
  created(){},
  mounted(){
    document.getElementsByClassName("forgetPsd")[0].style.height = window.innerHeight-46+"px"
    this.query = this.$route.query.channel;
  },
}
</script>

<style lang='less' scoped>
  .forgetPsd{
    height: 100%;
    background:white;
  }
  a.clock{
    display: inline-block;
    text-decoration:none;
    padding:3px 10px;
    background:#ddd;
    border-radius:10px;
    margin-top: 22px;
    margin-left: 10%;
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
      font-size:16px;
      padding:4px 0;
    }
    .icon-back{
      margin:6px 0;
      height:22px;
      position:absolute;
      left:5px;
      top:0;
      padding:5px;
      img{height:100%;}
    }
  }
</style>
