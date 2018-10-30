<template>
  <div class="download">
    <a :href="url" id="down" download="w3logo"></a>
    <div class="title">
      <p>&nbsp;</p>
      <div class="icon-back" @click="goBack">
        <img src="../../static/images/nav_back@2x.png">
      </div>
    </div>

    <div class="image">
      <img src="../../static/images/WechatIMG9 .png" alt="">
    </div>
    <div class="button">
      <a :href="url" download="loan" @click="android" class="android">&nbsp;&nbsp;Android版下载</a>
    </div>
    <div class="button">
      <button class="iphone" @click="iphone">&nbsp;&nbsp;iPhone 版 下载</button>
    </div>
  </div>
</template>
<script>
  import { dumbWrapper } from '@/Ajax/vars'
  import { getChannel } from '@/Ajax/post'
  export default {
    name: 'download',
    components: {},
    data () {
      return {
        query: "",
        channel: "",
        url: ""
      }
    },
    methods:{
      android () {
        _czc.push(["_trackEvent","android下载",this.channel]);
        console.log(this.url)
      },
      iphone () {
        router.push({path:'/iosDownload?channel='+this.query+"&name="+this.channel})
      },
      goBack () {
        window.history.go(-1);
      },
    },
    mounted(){
      this.query = this.$route.query.channel;
      let self = this;
      dumbWrapper({
        promise:getChannel({
          channelTag:this.query
        }),
        successCB:(e)=>{
          let href = e.data.channelSimpleName;
          self.url = "https://dollmachine.oss-cn-hangzhou.aliyuncs.com/apk/app-"+href+"-release.apk";
          self.channel = e.data.channelName;
        }
      })
    },
  }
</script>

<style lang='less' scoped>
  .download {
    width: 100%;
    background: white;
    height: 100%;
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
    .image{
      img{
        width: 100%;
        padding-bottom: 40px;
      }
    }
    .button {
      text-align: center;
      a{
        display: inline-block;
        border: 1px solid #F0E6C4;
        font-size: 16px;
        /*color: #3188FD;*/
        color: #F7872A;
        padding: 10px 60px;
        text-align: center;
        -webkit-border-radius: 30px;
        -moz-border-radius: 30px;
        border-radius: 30px;
        margin: 10px auto;
      }
      button {
        /*width: 60%;*/
        /*height: 40px;*/
        /*line-height: 40px;*/
        border: 1px solid #F0E6C4;
        font-size: 16px;
        /*color: #3188FD;*/
        color: #F7872A;
        padding: 10px 60px;
        text-align: center;
        -webkit-border-radius: 30px;
        -moz-border-radius: 30px;
        border-radius: 30px;
        margin: 10px auto;
      }
    }
    .android  {
      background: url("../../static/images/android1.png") no-repeat 26px center;
      background-size: 14%;
    }
    .iphone {
      background: url("../../static/images/iphone1.png") no-repeat 26px center;
      background-size: 14%;
    }
  }
</style>
