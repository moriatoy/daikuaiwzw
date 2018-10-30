<template>
  <div class="recommendProduct">
    <div class="title">
      <p>{{$route.query.tagName}}</p>
      <div class="icon-back" @click="goBack">
        <img src="../../static/images/nav_back@2x.png">
      </div>
    </div>
    <div class="hot-loan">
      <div class="hot-loan-item clear" v-for="item in loans" :key="item.productId"
      @click="getProductDetail(item.productId,item.productName)">
        <div class="item-icon fl">
          <img :src="item.iconUrl">
          <p>{{item.productName}}</p>
        </div>
        <div class="item-intro fl">
          <h4>{{item.loanRange}}元</h4>
          <p>{{item.intro}}</p>
          <p>期限&nbsp;<span style="color:#f6ad34;">{{item.timeLimit}}&nbsp;&nbsp;&nbsp;</span>
            <span style="color:#ddd;">|</span>&nbsp;&nbsp;&nbsp;{{item.rateType}}&nbsp;
            <span style="color:#f00;">{{item.rate}}</span></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { dumbWrapper } from '../Ajax/vars.js';
import {
    getOnlineLoanProductsByRecommendTag,clickOnLoanProduct,getTagsByType,selectLoanProductByTag
  } from '../Ajax/post.js';
export default {
  name: '',
  components: {
  },
  data () {
    return {
      loans:[],
    }
  },
  methods:{
    getProductDetail(id,name){
      dumbWrapper({
        promise:clickOnLoanProduct({
          productId:id
        })
      })
      // zhuge.track("产品详情",{
      //   'name':name,
      //   'from':"为你推荐"
      // })
      router.push({ path: '/productDetail', query: { id: id }})
    },
    goBack(){
      window.history.go(-1);
    },
    getLoanProduct() {
      dumbWrapper({
        promise: getOnlineLoanProductsByRecommendTag({
          tagId:this.$route.query.tagId
        }),
        successCB: (e) => {
          this.loans = e.data;
        },
        failCB:(e)=>{
          console.log(e)
        }
      });
    },
  },
  mounted(){
    this.getLoanProduct();
  }
}
</script>

<style lang='less' scoped>
.recommendProduct{
  padding-top:20px;
  .title{
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
      height:24px;
      position:absolute;
      left:5px;
      top:0;
      padding:5px;
      img{height:100%;}
    }
  }
  .hot-loan{
    padding-top:5px;
    background:#fff;
    margin-top:10px;
    .hot-loan-item{
      position:relative;
      box-sizing:border-box;
      border-bottom:1px solid #eee;
      padding:10px 5px;
      h4{
        font-weight:normal;
        font-size:18px;
        margin:0;
      }
      p{
        white-space: nowrap;  
        text-overflow:ellipsis; 
        overflow:hidden;
        font-size:14px;
        color:#999;
      }
      .item-icon{
        text-align:center;
        padding:10px;
        width:25%;
        box-sizing:border-box;
        border-radius:5px;
        img{width:90%;border-radius:5px;}
        p{margin:0;font-size:13px;color:#333;}
      }
      .item-intro{
        padding-top:10px;
        padding-left:5px;
        box-sizing:border-box;
        width:72%;
        margin-right:3%;
        overflow:hidden;
        p:last-child{margin-top:10px;color:#666;}
      }
    }
  }
}
</style>