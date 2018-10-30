<template>
  <div class="wxdPromotion">
    <div class="img-box">
      <img src="../../static/img/banner@2x.png">
    </div>
    <div class="products clear">
      <div class="product-item fl" v-for="item in productData" @click="getProductDetail(item.productId,item.productName)">
        <div class="item-img fl">
          <img :src="item.iconUrl">
          <p>{{item.productName}}</p>
        </div>
        <div class="item-intro fl">
          <p style="font-size:15px;color:rgb(255,114,13)">{{item.loanRange}}</p>
          <p style="font-size:10px;">{{item.rateType}} <span style="color:rgb(255,114,13)">{{item.rate}}</span></p>
          <p style="font-size:9px;">{{item.intro}}</p>
        </div>
      </div>
    </div>
    <div class="footer clear">
      <div class="fl footer-left">
        <!-- <img src="../../static/img/erweimabiankuang@2x.png"> -->
        <div class="qrCode-box" @click="show=true">
          <img src="../../static/img/qrCode.jpg">
        </div>
        <img src="../../static/img/money@2x.png">
      </div>
      <div class="fl footer-right">
        <p>VIP群：514679176</p>
        <p style="color:rgb(255,114,13)" @click="handleDownLoad">点击下载APP</p>
      </div>
    </div>
    <div v-transfer-dom @click="show=false">
      <x-dialog v-model="show" class="dialog-demo">
        <div class="img-box">
          <img src="../../static/img/qrCode.jpg" style="max-width:100%">
        </div>
        <div @click="show=false">
          <span class="vux-close"></span>
        </div>
      </x-dialog>
    </div>
  </div>
</template>

<script>
import { XDialog, XButton, Group, XSwitch, TransferDomDirective as TransferDom } from 'vux'
import { dumbWrapper } from '../Ajax/vars.js';
import {
    getOnlineLoanProduct,clickOnLoanProduct,clickOnMainPage
  } from '../Ajax/post.js';
export default {
  name:'',
  directives: {
    TransferDom
  },
  components:{
    XDialog,
    XButton,
    Group,
    XSwitch
  },
  data(){
    return{
      show:false,
      productData:[]
    }
  },
  methods:{
    handleDownLoad(){
      var u = navigator.userAgent;
      if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1){
        location.href="http://t.cn/R0YbbIR";
      }else  if (u.indexOf('iPhone') > -1){
        //alert('尚未上线，敬请期待');
        location.href="https://itunes.apple.com/cn/app/id1111267906?mt=8";
      }
    },
    viewImage(){
      window.open("../../static/img/qrCode.jpg")
    },
    getProductDetail(id,name){
      dumbWrapper({
        promise:clickOnLoanProduct({
          productId:id
        })
      })
      zhuge.track("产品详情",{
        'name':name,
        'from':"万象贷推广"
      })
      router.push({ path: '/productDetail', query: { id: id }})
    },
    getLoanProduct() {
      dumbWrapper({
        promise: getOnlineLoanProduct({}),
        successCB: (e) => {
          if(e.data.length<=20){
            this.productData = e.data;
          }else{
            for(var i=0;i<20;i++){
              this.productData.push(e.data[i]);
            }
          }
        },
        failCB:(e)=>{
          console.log(e)
        }
      });
    },
  },
  created(){
    document.title="万象贷";
    this.getLoanProduct();
    dumbWrapper({
      promise:clickOnMainPage({
        messageId:15
      })
    })
  },
  mounted(){},
}
</script>

<style lang='less' scoped>
.wxdPromotion{
  font-family: "Microsoft YaHei","微软雅黑","PingFang SC", "Helvetica Neue", Helvetica,  "Hiragino Sans GB",  Arial, sans-serif;
  .img-box{
    img{width:100%;}
  }
  .products{
    padding:5px;
    .product-item{
      width:48%;
      height:80px;
      margin:2px 1%;
      margin-bottom:10px;
      background:#fff;
      padding:5px 10px;
      box-sizing:border-box;
      border-radius:8px;
      .item-img{
        width:30%;
        font-size:10px;
        text-align:center;
        img{width:100%;margin-bottom:-3px;}
        p{
          margin:0;
          color:#717171;
          white-space: nowrap;  
          text-overflow:hidden; 
          overflow:hidden;
        }
      }
      .item-intro{
        padding-left:10px;
        width:70%;
        box-sizing:border-box;
        p{
          white-space: nowrap;  
          text-overflow:ellipsis; 
          overflow:hidden;
          color:#717171;
        }
      }
    }
  }
  .footer{
    background:#fff;
    padding:19px 20px 15px 20px;
    .footer-left{
      width:50%;
      padding-left:20px;
      box-sizing:border-box;
      .qrCode-box{
        width:55%;
        display:inline-block;
        padding:20px 10px 0 10px;
        box-sizing:border-box;
        background:url('../../static/img/erweimabiankuang@2x.png');
        background-size:100% 100%;
        img{width:100%;margin-bottom:-5px;}
      }
      &>img{
        width:35%;
      }
    }
    .footer-right{
      p{
        margin-top:10px;
        text-align:center;
        background:#f5f5f7;
        padding:0 5px;
        border-radius:5px;
        font-size:12px;
      }
    }
  }
}
</style>