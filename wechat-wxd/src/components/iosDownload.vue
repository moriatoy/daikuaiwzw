<template>
  <div class="download">
    <div class="title">
      <p>&nbsp;</p>
      <div class="icon-back" @click="goBack">
        <img src="../../static/images/nav_back@2x.png">
      </div>
    </div>
    <div class="image">
      <img src="../../static/images/WechatIMG11.png" alt="">
    </div>
    <div class="button">
      <button class="iphone" @click="iphone">&nbsp;&nbsp;iPhone版下载</button>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'download',
    components: {},
    data () {
      return {
        query:"",
      }
    },
    methods:{
      iphone () {
        _czc.push(["_trackEvent","ios下载",this.query]);
        window.location.href = "itms-services:///?action=download-manifest&url=https://dollmachine.oss-cn-hangzhou.aliyuncs.com/apk/dkwzw.plist"
      },
      goBack () {
        window.history.go(-1);
      }
    },
    mounted(){
      this.query = this.$route.query.name;
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
      margin-top: 41px;
      img{
        width: 100%;
        display: block;
      }
    }
    .button {
      width: 100%;
      position: fixed;
      bottom: 5%;
      text-align: center;
      button {
        border: 1px solid #F0E6C4;
        font-size: 16px;
        color: #3188FD;
        padding: 10px 60px;
        text-align: center;
        -webkit-border-radius: 30px;
        -moz-border-radius: 30px;
        border-radius: 30px;
        margin: 10px auto;
      }
    }
    .iphone {
      background: url("../../static/images/iphone.png") no-repeat 26px center white;
      background-size: 18%;
    }
  }
</style>
