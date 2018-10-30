<template>
	<div class="regist">
    <div class="background">
      <!--<img src="../../static/images/background@3.png" style="display: block" alt="">-->
      <img src="../../static/images/WechatIMG6.png" @click="downLoad" style="display: block" alt="">
    </div>
    <!--<div style="padding: 0 10px;">-->
      <!--<div class="content">-->
        <!--<x-input :max="11" placeholder="请输入手机号" type="tel" v-model="form.phoneNum" style="padding: 10px;background: #EDF0FF;border-radius: 23px"></x-input>-->
        <!--&lt;!&ndash;<div style="margin: 17px auto;overflow: hidden;background: #EDF0FF;border-radius: 23px">&ndash;&gt;-->
        <!--<div style="margin: 17px auto;overflow: hidden;">-->
          <!--<x-input class="fication-input" :max="6" placeholder="请输入验证码" v-model="form.checkCode"></x-input>-->
          <!--<x-button class="fication" slot="right" plain @click.native="getCode"-->
                    <!--v-show="clockVisible==false">获取验证码</x-button>-->
          <!--<a slot="right" class="clock" v-show="clockVisible==true">{{clock}} s</a>-->
        <!--</div>-->
        <!--&lt;!&ndash;<x-input placeholder="密码不得少于八位" type="password" v-model="form.password" style="padding: 10px;background: #EDF0FF;border-radius: 12px"></x-input>&ndash;&gt;-->
        <!--<box gap="25px 0 10px">-->
          <!--<x-button class="linear" @click.native="regist">立即拿钱</x-button>-->
        <!--</box>-->
        <!--<div class="agreement">-->
          <!--<p>点击"立即借款"表示同意</p>-->
          <!--<p>《用户协议》《借款说明书》</p>-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->
    <div v-transfer-dom id="checkCode">
      <alert v-model="show" title="提示">验证码已发送，注意查收</alert>
    </div>
    <!--<div class="layer-box" v-if="layer">-->
      <!--<img src="../../static/images/browser.png" alt="浏览器打开">-->
    <!--</div>-->
	</div>
</template>
<script>
import { dumbWrapper } from '@/Ajax/vars'
import {getCode,login,getChannel} from '@/Ajax/post'
import { XInput, Group,XButton,Box,Flexbox, FlexboxItem,TransferDomDirective as TransferDom, Alert} from 'vux'
export default {
  name: '',
  directives: {
    TransferDom
  },
  components: {
    XInput, Group,
    XButton,Box,
    Flexbox, FlexboxItem,
    Alert
  },
  data () {
    return {
      clock:60,
      clockVisible:false,
      form:{
        phoneNum:null,
        checkCode:null,
        source:'网页',
      },
      agreeCheck:0,
      show:false,
      query:'',
      url:'',
      channel:'',
      layer: false
    }
  },
  methods:{
    userAgreement(){
      router.push({path:'/agreement'})
    },
    regist(){
      let data=[];
      if (this.$route.query.channel) {
        this.form.channelTag = this.$route.query.channel
      }
      if(!this.form.phoneNum||!this.form.checkCode){
        alert('请确认输入的内容不为空！');
      }else{
        data=this.form;
        let href = this.$route.query.channel;
        dumbWrapper({
          promise:login(data),
          successCB:(e)=>{
            if(e.state==0){
              setTimeout(function(){
                if (href) {
                  router.push({path:'/download?channel='+href})
                } else {
                  router.push({path:'/download'})
                }

              },500)
            }else if(e.state==30002){
              alert('手机号或验证码错误！')
            }
          }
        });
        // dumbWrapper({
        //   promise:signUpByPassword(data),
        //   successCB:(e)=>{
        //     if(e.state==0){
        //       setTimeout(function(){
        //         // router.push({path:'/login'})
        //         dumbWrapper({
        //           promise:loginByPassword(data),
        //           successCB:(e)=>{
        //             if(e.state==0){
        //               setTimeout(function(){
        //                 router.push({path:'/Homepage'})
        //               },500)
        //             }else if(e.state==10003){
        //               alert('密码错误！')
        //             }else{
        //               alert('请检查手机号或者密码是否正确！')
        //             }
        //           }
        //         })
        //       },500)
        //     }else if(e.state==30002){
        //       alert('手机号或验证码错误！')
        //     }else if(e.state==10002){
        //       alert("密码少于八位！")
        //     }
        //   }
        // })
      }
    },
    downLoad(){
      // let href = this.$route.query.channel;
      // if (href) {
      //   router.push({path:'/download?channel='+href})
      // } else {
      //   router.push({path:'/download'})
      // }


      var u = navigator.userAgent;
      var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
      var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
      if (isiOS) {
        _czc.push(["_trackEvent","ios下载",this.query]);
        window.location.href = "itms-services:///?action=download-manifest&url=https://dollmachine.oss-cn-hangzhou.aliyuncs.com/apk/dkwzw.plist"
      } else if (isAndroid) {
        _czc.push(["_trackEvent","android下载",this.channel]);
        window.location.href = this.url;
      }
    },
    getCode(){
      var self=this;
      if(!this.form.phoneNum){
        alert('请填写手机号！')
      }else if (this.form.phoneNum.search(/^[1][3,4,5,7,8][0-9]{9}$/)){
        alert('手机格式不正确！')
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
    }
  },
  mounted(){
    document.getElementsByClassName("regist")[0].style.height = window.innerHeight + "px";
    // 下载
    var channel = this.$route.query.channel;
    let self = this;
    dumbWrapper({
      promise:getChannel({
        channelTag:channel
      }),
      successCB:(e)=>{
        let href = e.data.channelSimpleName;
        self.url = "https://dollmachine.oss-cn-hangzhou.aliyuncs.com/apk/app-"+href+"-release.apk";
        console.log(self.url)
        self.channel = e.data.channelName;
      }
    });
    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    if (isWeixin) {
      this.layer = true
    }
  },
}
</script>

<style lang='less' scoped>
div.weui-dialog{background-color: rgba(255,255,255,1)!important;}
  .weui-dialog{background-color: rgba(255,255,255,1)!important;}
.regist{
  height: auto;
  .layer-box{
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    padding-top: 25px;
    background-color: rgba(0,0,0,.6);
    z-index: 1000;
    /*display: none;*/
    img {
      max-width: 100%;
      width: 80%;
      margin: 0 10%;
    }
  }
  background:#E4EFFF;
  .background{
    img{
      width: 100%;
    }
  }
  .content{
    background: #6A9DFC;
    padding: 16px;
    border-radius: 16px;
  }
  a.clock{
    display: inline-block;
    text-decoration:none;
    padding:10px 0;
    width: 40%;
    text-align: center;
    background:#ddd;
    border-radius: 23px;
    float: right;
  }
  span.checked-icon{
    display:inline-block;
    width:22px;
    height:22px;
    background:url("../../static/images/icon_sel@2x.png");
    background-size:100% 100%;
    margin-bottom:-5px;
  }
  span.unchecked-icon{
    display:inline-block;
    width:22px;
    height:22px;
    background:url("../../static/images/icon_nor@2x.png");
    background-size:100% 100%;
    margin-bottom:-5px;
  }
}
.linear{
  background: -webkit-linear-gradient(left,#FFF98C,#FFE453);
  background: -o-linear-gradient(left,#FFF98C,#FFE453);
  background: -moz-linear-gradient(left,#FFF98C,#FFE453);
  background: linear-gradient(left,#FFF98C,#FFE453);
  border-radius:23px;
  font-weight:900;
  color:#C0782B;
  padding:4px;
}
.fication{
  border:0;
  float:right;
  color:#C0782B;
  width:40%;
  line-height:1.1rem;
  font-size:1rem;
  border-radius:23px;
  padding: 14px 2%;
  background: -webkit-linear-gradient(left,#FFF98C,#FFE453);
  background: -o-linear-gradient(left,#FFF98C,#FFE453);
  background: -moz-linear-gradient(left,#FFF98C,#FFE453);
  background: linear-gradient(left,#FFF98C,#FFE453);
}
.fication-input{
  width: 50%;
  float: left;
  padding:10px;
  background: #EDF0FF;
  border-radius: 23px;
}
.agreement{
  text-align: center;
  color: #fff;
  font-weight: 100;
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
