<template>
  <div class="pop" id="pop">
    <div class="pop-head">
      <img src="../../static/img/59cc750bc97f3_1024@2x.png">
    </div>
    <div class="pop-sec">
      <div v-for="item in data" :key="item.id" class="clear pop-item">
        <div class="fl icon-box">
          <img :src="item.loanProduct.iconUrl">
        </div>
        <div class="fl intro-box">
          <h4>{{item.loanProduct.intro}}</h4>
          <button @click="handleClick(item.loanProduct.productId)">去领钱</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router';
import { dumbWrapper } from '../Ajax/vars.js';
import { getPopUpAdSapce } from '../Ajax/post.js';
export default {
  name: '',
  components: {
  },
  data () {
    return {
      data:[]
    }
  },
  methods:{
    handleClick(id){
      var u = navigator.userAgent;
      if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1){
        android.jumpToProduct(id);
      }
      if (u.indexOf('iPhone') > -1){
        window.webkit.messageHandlers.iOSJumpAction.postMessage(id);
      }
    },
    getPopUpAdSapce(){
      dumbWrapper({
        promise:getPopUpAdSapce({}),
        successCB:(e)=>{
          this.data=e.data;
        }
      })
    }
  },
  mounted:function(){
    document.title = '产品推荐';
    this.getPopUpAdSapce();
    if(document.getElementById('pop').offsetHeight<window.screen.height){
      document.getElementById('pop').style.minHeight=window.screen.height+'px';
    }

  }
}
</script>

<style scoped>
.pop{
  background:#f0cc92;
  padding-bottom:10px;
  height:100%;
  box-sizing: border-box;
}
.pop-head img{
  width:100%;
}
.pop-sec{
  margin:10px 20px;
  padding:15px 15px 0 15px;
  border-radius:10px;
  background:#ff8816;
}
.icon-box{
  margin-bottom:10px;
  width:25%;
  border-radius:5px;
}
.icon-box img{
  width:100%;
  border-radius:5px;
}
.intro-box{
  margin-bottom:10px;
  width:74%;
  box-sizing:border-box;
  padding:0 10px;
  color:#fff;
}
.intro-box h4{
  white-space: nowrap;  
  text-overflow:ellipsis; 
  overflow:hidden;
  font-weight:normal;
  margin-bottom:5px;
}
.intro-box button{
  background:#f8e71c;
  border:none;
  padding:4px 10px;
  border-radius:15px;
  color:#fe5200;
}
</style>