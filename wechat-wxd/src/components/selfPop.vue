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
          <!-- <button @click="handleClick(item.loanProduct.productId,item.loanProduct.productName)">去领钱</button> -->
        </div>
        <div class="fl right-box">
          <button @click="handleClick(item.loanProduct.productId,item.loanProduct.productName)">立即<br/>领钱</button>
        </div>
      </div>
    </div>
    <!-- <flexbox style="padding:10px 30px;box-sizing:border-box;font-size:14px;">
      <flexbox-item :span="4"><div class="flex-demo">1/3</div></flexbox-item>
      <flexbox-item :span="8"><div class="flex-demo">
        <p>关注微信公众号 “打个借条APP” 查看更多贷款攻略资讯！</p></br>
        <p>（截屏保存到手机相册，扫码关注即可）</p>
      </div></flexbox-item>
    </flexbox> -->
  </div>
</template>

<script>
import { Flexbox, FlexboxItem, Divider } from 'vux'
import router from '@/router';
import { dumbWrapper } from '../Ajax/vars.js';
import { getPopUpAdSapce,clickOnLoanProduct } from '../Ajax/post.js';
export default {
  name: '',
  components: {
    Flexbox, FlexboxItem, Divider
  },
  data () {
    return {
      data:[]
    }
  },
  methods:{
    handleClick(id,name){
      dumbWrapper({
        promise:clickOnLoanProduct({
          productId:id
        })
      })
      // zhuge.track("产品详情",{
      //   'name':name,
      //   'from':"弹窗"
      // })
      router.push({ path: '/productDetail', query: { id: id }})
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
    // document.body.style.backgroundColor="#f0cc92 !important";

  }
}
</script>

<style scoped>
/*.pop-item{
  box-sizing: border-box;
  padding-bottom:15px;
}
.pop-item:last-child{
  padding-bottom:0;
}*/
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
  margin:0 20px 10px 20px;
  padding:15px 15px 0 15px;
  border-radius:10px;
  background:#ff8816;
}
.icon-box{
  margin-bottom:10px;
  width:18%;
}
.icon-box img{
  width:100%;
  border-radius:5px;
}
.intro-box{
  margin-bottom:10px;
  width:62%;
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
  margin-top:20px;
}
.intro-box button{
  background:#f8e71c;
  border:none;
  padding:4px 10px;
  border-radius:15px;
  color:#fe5200;
}
.right-box{
  margin-bottom:10px;
  width:20%;
}
.right-box button{
  background:#f8e71c;
  width:100%;
  border:none;
  padding:4px 20px;
  border-radius:30px;
  color:#fe5200;
  margin-top:10px;
}
</style>