<template>
  <div class="loanProducts">
    <div></div>
    <div class="title">
      借款攻略
    </div>
    <div class="hot-loan">
      <div class="hot-loan-item clear" v-for="item in loans" :key="item.productId"
      @click="getProductDetail(item.informationUrl)">
        <div class="top">
          <div class="radius fl" :style="item.style">{{item.informationTag}}</div>
          <h4 class="h4 fl">{{item.informationTitle}}</h4>
        </div>
        <div class="bottom">
            <img :src="item.informationImage" alt="">
        </div>
        <!--<div class="item-icon fl">-->
          <!--<img :src="item.iconUrl">-->
        <!--</div>-->
        <!--<div class="item-intro fl">-->
          <!--&lt;!&ndash;<h4>{{item.loanRange}}元</h4>&ndash;&gt;-->
          <!--<h4>{{item.productName}}</h4>-->
          <!--&lt;!&ndash;<p>{{item.intro}}</p>&ndash;&gt;-->
          <!--<p>期限范围：<span style="color:#f6ad34;">{{item.timeLimit}}&nbsp;&nbsp;&nbsp;</span></p>-->
        <!--</div>-->
        <!--<div class="btn">立即申请</div>-->
        <!--<div class="describe">-->
          <!--<div class="item border fl">-->
            <!--<div class="text">{{item.loanRange}}</div>-->
            <!--<div class="des">可贷额度（元）</div>-->
          <!--</div>-->
          <!--<div class="item fl">-->
            <!--<div class="text">{{item.rate}}</div>-->
            <!--<div class="des">{{item.rateType}}</div>-->
          <!--</div>-->
        <!--</div>-->
      </div>
    </div>
    <div class="footer">
      <a @click="home">
        <div class="index">
          <img src="../../static/images/tab_icon_home_nor@2x.png">
          <p>首页</p>
        </div>
      </a>
      <a @click="loan">
        <div class="mine">
          <img src="../../static/images/tab_icon_daikuan_nor@2x.png">
          <p>贷款</p>
        </div>
      </a>
        <div class="discover">
          <img src="../../static/images/shequ_icon_nor.png">
          <p>攻略</p>
        </div>
      <!--<a @click="mine">-->
        <!--<div class="mine">-->
          <!--<img src="../../static/images/tab_icon_mine_nor@2x.png">-->
          <!--<p>我的</p>-->
        <!--</div>-->
      <!--</a>-->
    </div>
  </div>
</template>

<script>
import { Selector,Group } from 'vux'
import { dumbWrapper } from '../Ajax/vars.js';
import {
  getInformation,getUserInfo,clickOnLoanProduct,getTagsByType,selectLoanProductByTag
  } from '../Ajax/post.js';
export default {
  name: 'loanProducts',
  components: {
    Selector,Group
  },
  data () {
    return {
      identityValue:'',
      loanMount:'',
      loanType:'',
      identityList:[],
      loanMountList:[],
      loanTypeList:[],
      loans:[]
    }
  },
  methods:{
    home(){
      router.push({path:'/Homepage'})
    },
    loan(){
      router.push({path:'/loanProducts'})
    },
    mine(){
      router.push({path:'/mine'})
    },
    getProductDetail(url){
      location.href=url;
      dumbWrapper({
        promise:clickOnLoanProduct({
          productId:id
        }),
        successCB:(e)=>{
          router.push({ path: '/productDetail', query: { id: id,type: "log" }})
        },
        failCB:(e)=>{
          router.push({ path: '/productDetail', query: { id: id,type: "log" }})
        }
      })
    },
    getLabel(val,code){
      var self=this;
      let label=null;
      if(code==1){
        self.identityList.forEach(item=>{
          if(val==item.key){
            label=item.value;
          }
        })
        return label;
      }else if(code==2){
        self.loanTypeList.forEach(item=>{
          if(val==item.key){
            label=item.value;
          }
        })
        return label;
      }else if(code==3){
        self.loanMountList.forEach(item=>{
          if(val==item.key){
            label=item.value;
          }
        })
        return label;
      }
    },
    getTagsByType(type){
      dumbWrapper({
        promise:getTagsByType({
          tagType:type
        }),
        successCB:(e)=>{
          e.data.forEach(item=>{
            let arr={
              key:item[0],
              value:item[1]
            }
            if(type=='身份'){
              this.identityList.push(arr);
            }else if(type=='贷款类型'){
              this.loanTypeList.push(arr);
            }else if(type=='金额'){
              this.loanMountList.push(arr);
            }
          })
          if(type=='身份'){
              this.identityList.unshift({key:null,value:'全部'});
            }else if(type=='贷款类型'){
              this.loanTypeList.unshift({key:null,value:'全部'});
            }else if(type=='金额'){
              this.loanMountList.unshift({key:null,value:'全部'});
            }
        }
      })
    },
    getLoanProduct() {
      dumbWrapper({
        promise: getInformation(),
        successCB: (e) => {
          for (var i = 0; i < e.data.length; i++) {
            if (e.data[i].informationTagType === 1) {
              e.data[i].style = "border: 1px solid #FBAA46;color:#FBAA46"
            } else if (e.data[i].informationTagType === 2) {
              e.data[i].style = "border: 1px solid #ED3129;color:#ED3129"
            }
          }
          this.loans = e.data;
        },
        failCB:(e)=>{
          console.log(e)
        }
      });
    }
  },
  mounted(){
    this.getLoanProduct();
  }
}
</script>

<style lang='less' scoped>
.loanProducts{
  padding-bottom:50px;
}
.title{
  top:0;
  position:fixed;
  width:100%;
  text-align:center;
  background:#fff;
  z-index:100;
  border-bottom:1px solid #eee;
  line-height:40px;
}
.classBtn{
  top:41px;
  position:fixed;
  width:100%;
  text-align:center;
  background:#fff;
  z-index:100;
  border-bottom:1px solid #eee;
  /*line-height:40px;*/
  ul{
    list-style: none;
    padding: 10px 20px;
    overflow: hidden;
    li{
      width: calc(33.3333% - 0.5px);
      padding: 4px 0;
      float: left;
      border: 1px solid #F98902;
      font-size: 14px;
      font-weight: 200;
      color: #F98902;
    }
    li:last-child{
      border-left: 0;
      border-top-right-radius: 10px;
      border-bottom-right-radius: 10px;
    }
    li:first-child{
      border-right: 0;
      border-top-left-radius: 10px;
      border-bottom-left-radius: 10px;
    }
    .color{
      background: #F98902;
      color: #fff;
    }
  }
}
.hot-loan{
    padding-top:5px;
    background:#fff;
    margin-top:41px;
    .hot-loan-item{
      position:relative;
      box-sizing:border-box;
      border-bottom:1px solid #eee;
      margin: 0 10px;
      padding:10px 0;
      .top {
        padding: 0 10px 10px;
        overflow: hidden;
        .radius{
          border: 1px solid #eee;
          font-size: 14px;
          padding: 0 6px;
          -webkit-border-radius: 6px;
          -moz-border-radius: 6px;
          border-radius: 6px;
        }
        h4{
          padding-left: 10px;
          font-weight: 400;
        }
      }
      .bottom{
        padding: 0 6px;
        img{
          width: 100%;
          border-radius: 6px;
        }
      }
      /*h4{*/
        /*font-weight:normal;*/
        /*font-size:18px;*/
        /*margin:0;*/
      /*}*/
      /*p{*/
        /*white-space: nowrap;*/
        /*text-overflow:ellipsis;*/
        /*overflow:hidden;*/
        /*font-size:14px;*/
        /*color:#999;*/
      /*}*/
      /*.item-icon{*/
        /*text-align:center;*/
        /*!*padding:10px;*!*/
        /*width:20%;*/
        /*box-sizing:border-box;*/
        /*border-radius:5px;*/
        /*img{width:90%;border-radius:5px;}*/
        /*p{margin:0;font-size:13px;color:#333;}*/
      /*}*/
      /*.btn{*/
        /*position: absolute;*/
        /*right: 5px;*/
        /*top: 40px;*/
        /*padding: 5px 10px;*/
        /*border: 1px solid #F88700;*/
        /*border-radius: 8px;*/
        /*font-size: 13px;*/
        /*font-weight: 200;*/
        /*color: #F88700;*/
      /*}*/
      /*.item-intro{*/
        /*padding-top:5px;*/
        /*padding-left:5px;*/
        /*box-sizing:border-box;*/
        /*width:72%;*/
        /*margin-right:3%;*/
        /*overflow:hidden;*/
        /*p:last-child{*/
          /*margin-top:0;*/
          /*color:#aaa;*/
          /*font-size: 12px;*/
          /*font-weight: 100;*/
          /*span{*/
            /*font-size: 16px;*/
            /*font-weight: 300;*/
          /*}*/
        /*}*/
      /*}*/
      /*.describe{*/
        /*width: 100%;*/
        /*overflow: hidden;*/
        /*.item {*/
          /*width: calc(50% - 1px);*/
          /*text-align: center;*/
        /*}*/
        /*.text{*/
          /*font-size: 20px;*/
          /*font-weight: 400;*/
          /*color: red;*/
        /*}*/
        /*.des {*/
          /*font-size: 12px;*/
          /*color: #aaa;*/
        /*}*/
      /*}*/
      /*.border {*/
        /*border-right: 1px solid #eee;*/
      /*}*/
    /*}*/
    /*.hot-loan-item:last-child{*/
      /*border-bottom: 0;*/
    }
  }
div.header{
  background:#fff;
  height:44px;
}
div.footer{
    background:#fff;
    box-sizing:border-box;
    padding-top:5px;
    text-align:center;
    width:100%;
    height:50px;
    border-top:1px solid #ddd;
    position:fixed;
    bottom:0;
    color:#999;
    .index{
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;}
      img{width:25px;}
    }
    .discover{
      margin-left:60px;
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;color:#F98902;}
      img{width:25px;}
    }
    .mine{
      background:#fff;
      margin-left:60px;
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;}
      img{width:25px;}
    }
  }
</style>
