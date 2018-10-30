<template>
  <div class="productDetail">
    <div class="title">
      <p>贷款详情</p>
      <div class="icon-back" @click="goBack">
        <img src="../../static/images/nav_back@2x.png">
      </div>
    </div>
    <div id="scroll-head" class="clear">
      <div class="fl">
        <h4>{{loanProduct.loanRange}}元</h4>
      </div>
      <div class="fr">
        <p>{{loanProduct.rateType}}：{{loanProduct.rate}}</p>
      </div>
    </div>
    <div id="scroll-title">
      <div class="sub-sec clear">
        <div class="left-sub-sec fl">
          <img :src="loanProduct.iconUrl">
        </div>
        <div class="right-sub-sec fl">
          <h4>{{loanProduct.productName}}</h4>
          <p>推荐指数：
            <span v-for="(value,index) in rcmdPoints" class="star">*</span>
            <span v-show="rcmdPoints!=loanProduct.rcmdIndex" class="halfStar">*</span>
          </p>
        </div>
      </div>
      <div class="bottom-sub-sec clear">
        <!--<div class="sec-cell fl">-->
          <!--<span>{{loanProduct.rateType}}</span>-->
          <!--<strong>{{loanProduct.rate}}</strong>-->
        <!--</div>-->
        <!--<div class="sec-cell fl">-->
          <!--<span>可贷额度</span>-->
          <!--<strong>{{loanProduct.loanRange}}</strong>-->
        <!--</div>-->
        <div class="sec-cell fl">
          <span>申请人数</span>
          <strong>{{loanProduct.applicants}}</strong>
        </div>
        <div class="sec-cell fl">
          <span>还款方式</span>
          <strong>{{loanProduct.paymentMethod}}</strong>
        </div>
        <div class="sec-cell fl">
          <span>期限范围</span>
          <strong>{{loanProduct.timeLimit}}</strong>
        </div>
      </div>
    </div>
    <div class="father">
      <div class="left">额度</div>
      <div class="right">{{loanProduct.loanRange}}元</div>
    </div>
    <div class="father">
      <div class="left">{{loanProduct.rateType}}</div>
      <div class="right">{{loanProduct.rate}}</div>
    </div>
    <tab :line-width="2" custom-bar-width="80px" bar-active-color="#F68400" v-model="infoType" style="z-index:50;">
      <tab-item selected active-class="active-6-3">申请流程</tab-item>
      <tab-item active-class="active-6-3">贷款计算</tab-item>
      <tab-item active-class="active-6-3">详细介绍</tab-item>
    </tab>
    <div v-show="infoType==0">
      <div class="intro-detail" style="padding: 30px 20px;">
        <!--<p class="left-border">&nbsp;&nbsp;申请流程</p>-->
        <img src="../../static/images/WechatIMG3.png" alt="" style="width: 100%;display: block;">
      </div>
    </div>
    <div v-show="infoType==1">
      <div class="loan-computed">
        <!--<p class="left-border">&nbsp;&nbsp;贷款计算</p>-->
        <div>
          <p class="detail">
            <span>金额</span>
            <group style="width:80px;display:inline-block;border:none;">
              <x-input v-model="loan.money" @on-change="handleChange" type="number" placeholder="0"
                       style="border:1px solid #aaa; border-radius:5px;padding:0 10px;text-align:center;margin-top:0;" :show-clear="false"></x-input>
            </group>
            <!-- <input class="inline-input" v-model="loan.money" @change="handleChange"> -->
            <span>元&nbsp;&nbsp;&nbsp;&nbsp;期限</span>
            <group style="width:80px;display:inline-block;">
              <x-input v-model="loan.timeRange" @on-change="handleChange" type="number" placeholder="0"
                       style="border:1px solid #aaa; border-radius:5px;padding:0 10px;text-align:center;" :show-clear="false"></x-input>
            </group>
            <!-- <input class="inline-input" v-model="loan.timeRange" @change="handleChange"> -->
            <span>{{getRateType()}}</span>
          </p>
          <div class="computed-result clear">
            <div class="result-cell fl">
              <p>日还款</p>
              <p>{{computed.dailyPay}}</p>
            </div>
            <div class="result-cell fl">
              <p>总利息</p>
              <p>{{computed.totalCredit}}</p>
            </div>
            <div class="result-cell fl">
              <p>{{loanProduct.rateType}}</p>
              <p>{{loanProduct.rate}}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-show="infoType==2">
      <div class="intro-detail">
        <!--<p class="left-border">&nbsp;&nbsp;详细介绍</p>-->
        <p class="detail">{{loanProduct.details}}</p>
      </div>
    </div>
    <div class="footer" @click="handleUrl(loanProduct.productUrl)">
      马上拿钱
    </div>
  </div>
</template>

<script>
import { Tab, TabItem, XInput, Group, XButton, Cell } from 'vux'
import { dumbWrapper } from '../Ajax/vars.js';
import { getCredit,getLoanProductById,getUserInfo,channel } from '../Ajax/post.js';
export default {
  name: 'productDetail',
  components: {
    Tab,
    TabItem,
    XInput,
    XButton,
    Group,
    Cell
  },
  data () {
    return {
      infoType:0,
      firstVisible:false,
      secondVisible:false,
      thirdVisible:false,
      forthVisible:false,
      fifthVisible:false,
      sixthVisible:false,
      rateOne:null,
      rateTwo:null,
      rateThree:null,
      rateFour:null,
      rateFive:null,
      rateSix:null,
      loanProduct:[],
      productName:null,
      repaymentWay:null,
      recommendPoints:null,
      detail:null,
      loan:{
        money:null,
        timeRange:null
      },
      computed:{
        dailyPay:0,
        totalCredit:0
      },
      rcmdPoints:0,
      channel: null,
      query: ""
    }
  },
  watch:{},
  methods:{
    getRateType(){
      if(this.loanProduct.rateType=="日利率"){
        return '天';
      }
      if(this.loanProduct.rateType=="月利率"){
        return '月';
      }
      if(this.loanProduct.rateType=="周利率"){
        return '周';
      }
      if(this.loanProduct.rateType=="年利率"){
        return '年';
      }
    },
    handleChange(val){
      if(this.loan.money&&this.loan.timeRange){
        if(this.loan.money!=0&&this.loan.timeRange!=0){
          var str=this.loanProduct.rate;
          var i=str.indexOf("%");
          var data=str.substring(0,i)/100;
          //console.log(data);
          let rate=data;
          if(this.loanProduct.rateType=='日利率'){
            this.computed.dailyPay=(this.loan.money*(1+rate)/this.loan.timeRange).toFixed(2);
            this.computed.totalCredit=(this.loan.money*rate*this.loan.timeRange).toFixed(2);
          }
          if(this.loanProduct.rateType=='月利率'){
            this.computed.dailyPay=(this.loan.money*(1+rate)/this.loan.timeRange/30).toFixed(2);
            this.computed.totalCredit=(this.loan.money*rate*this.loan.timeRange).toFixed(2);
          }
          if(this.loanProduct.rateType=='周利率'){
            this.computed.dailyPay=(this.loan.money*(1+rate)/this.loan.timeRange/7).toFixed(2);
            this.computed.totalCredit=(this.loan.money*rate*this.loan.timeRange).toFixed(2);
          }
          if(this.loanProduct.rateType=='年利率'){
            this.computed.dailyPay=(this.loan.money*(1+rate)/this.loan.timeRange/365).toFixed(2);
            this.computed.totalCredit=(this.loan.money*rate*this.loan.timeRange).toFixed(2);
          }
        }
      }
    },
    handleUrl(url){
      let self = this;
      _czc.push(["_trackEvent","总量",self.loanProduct.productName]);
      let productUrl=url.replace(/\s/g,'');
      window.location.href = productUrl;
    },
    goBack(){
      // window.history.go(-1);
      router.push({path:'/loan?channel='+this.query})
    },
  },
  mounted(){
    if(this.$route.query.id){
      dumbWrapper({
        promise:getLoanProductById({
          productId:this.$route.query.id
        }),
        successCB:(e)=>{
          this.loanProduct = e.data;
          this.rcmdPoints=Math.floor(e.data.rcmdIndex);
          var self=this;
          if(e.data.applyProcess){
            let process=e.data.applyProcess;
            process.forEach(item=>{
              if(item[0].id==1){
                self.firstVisible=true;
                self.rateOne=item[1];
              }else if(item[0].id==2){
                self.secondVisible=true;
                self.rateTwo=item[1];
              }else if(item[0].id==3){
                self.thirdVisible=true;
                self.rateThree=item[1];
              }else if(item[0].id==4){
                self.forthVisible=true;
                self.rateFour=item[1];
              }else if(item[0].id==5){
                self.fifthVisible=true;
                self.rateFive=item[1];
              }else if(item[0].id==6){
                self.sixthVisible=true;
                self.rateSix=item[1];
              }
            })
          }
        }
      })
    }
  },
  created(){
    var self=this;
    document.onscroll=function(){
      if(self.$route.query.id){
        var t=document.documentElement.scrollTop||document.body.scrollTop;
        var h=document.getElementById('scroll-title').offsetHeight;
        if(t>=h){
          document.getElementById('scroll-head').style.opacity=1;
        }
        if(t<h){
          document.getElementById('scroll-head').style.opacity=0;
        }
      }
    };
    // 登录信息
    // dumbWrapper({
    //   promise:getUserInfo({}),
    //   successCB:(e)=>{
    //     if(e.state==="0"){
    //       if (e.data.channelTag !== null) {
    //         dumbWrapper({
    //           promise:channel({}),
    //           successCB:(item)=>{
    //             for (let i = 0; i < item.data.length; i++) {
    //               if (item.data[i].channelTag === e.data.channelTag) {
    //                 this.channel = item.data[i].channelName;
    //               }
    //             }
    //           }
    //         })
    //       }
    //     }
    //   }
    // });
    this.query = this.$route.query.channel;
  }
}
</script>

<style lang='less' scoped>
  p.left-border{
    border-left:3px solid #00aeff;
  }
  .active-6-3 {
    color: #F68400 !important;
    /*border-color: red !important;*/
  }
  .father {
    overflow: hidden;
    background: #fff;
    border-bottom: 1px solid #eee;
    padding: 10px 30px;
    color: #666;
  }
  .father > .left {
    float: left;
  }
  .father > .right {
    float: right;
  }
.productDetail{
  padding-bottom:44px;
   padding-top:41px;
}
  .apply-process{
    background:#fff;
    /*margin-top:10px;*/
    padding-top:2px;
    p.apply{
      font-size:12px;
      display:block;
      margin:12px;
    }
    div.detail{
      position:relative;
      color:#fff;
      /*background:-webkit-linear-gradient(left,#2a50b2, #4273dd);*/
      .dashed-border{
        position:absolute;
        width:2px;
        border-left:1.5px dashed #CDCDCC;
        height:86%;
        left:11.8%;
        top:12%;
        bottom:7%;
      }
      .left-cell{
        padding:30px 30px 30px 35px;
        box-sizing:border-box;
        width:45%;
        img{width:100%;}
      }
      .right-cell{
        padding:10% 0 0 10%;
        box-sizing:border-box;
        color: #000;
        /*width:45%;*/
        span{
          font-size:1.5rem;
          font-weight:300;
        }
      }
      .detail-cell{
        .middle-cell>div{
          width:8px;
          height:8px;
          border-radius:8px;
          background:#fff;
          border: 3px solid #FB8100;
        }
      }
      /*.detail-cell:nth-child(3){*/
        /*.middle-cell>div{*/
          /*width:6px;*/
          /*height:6px;*/
          /*border-radius:3px;*/
          /*background:#fff;*/
        /*}*/
      /*}*/
      /*.detail-cell:nth-child(4){*/
        /*.middle-cell>div{*/
          /*width:8px;*/
          /*height:8px;*/
          /*border-radius:4px;*/
          /*background:#fff;*/
        /*}*/
      /*}*/
      /*.detail-cell:nth-child(5){*/
        /*.middle-cell>div{*/
          /*width:10px;*/
          /*height:10px;*/
          /*border-radius:5px;*/
          /*background:#fff;*/
        /*}*/
      /*}*/
      /*.detail-cell:nth-child(6){*/
        /*.middle-cell>div{*/
          /*width:12px;*/
          /*height:12px;*/
          /*border-radius:6px;*/
          /*background:#fff;*/
        /*}*/
      /*}*/
      /*.detail-cell:nth-child(7){*/
        /*.middle-cell>div{*/
          /*width:14px;*/
          /*height:14px;*/
          /*border-radius:7px;*/
          /*background:#fff;*/
        /*}*/
      /*}*/
      .middle-cell{
        width:10%;
        height:14px;
        margin-top:16.5%;
        div{
          position:absolute;
          left:12%;
          transform:translate(-50%,-50%);
          -webkit-transform: translate(-50%,-50%);
        }
      }
    }
  }
  div.footer{
    height:44px;
    box-sizing:border-box;
    padding:10px 0;
    font-size:15px;
    text-align:center;
    background:#F88A03;
    position:fixed;
    bottom:0;
    color:#fff;
    width:100%;
  }
  .loan-computed{
    background:#fff;
    /*margin-top:10px;*/
    padding:15px 12px;
    p{
      font-size:12px;
      margin-bottom:10px;
    }
    p.detail{
      color:#666;
      margin-left:12px;
      span{
        position:relative;
        top:-10px;
      }
      .weui-cells{
        margin-top:0;
      }
    }
    input.inline-input{
      text-align:center;
      color:#999;
      display:inline-block;
      width:50px;
      padding:3px 5px;
      border-radius:5px;
      border:1px solid #aaa;
      margin:0 5px;
    }
    .computed-result{
      padding:10px;
      .result-cell{
        border-radius:5px;
        background:#f7f7f7;
        text-align:center;
        width:29%;
        box-sizing:border-box;
        margin:2%;
        padding:10px;
        p{
          margin:2px 0;
          color:#F88A03;
        }
      }
    }
  }
  .intro-detail{
    background:#fff;
    /*margin-top:10px;*/
    padding:15px 12px;
    p{
      font-size:12px;
    }
    p.detail{
      color:#999;
      margin-left:12px;
    }
  }
  .bottom-sub-sec{
    padding:10px 0;
    background:#fff;
    text-align:center;
    border-bottom:1px solid #eee;
    .sec-cell{
      width:33%;
      box-sizing:border-box;
      margin-bottom:5px;
      span{
        font-size:12px;
        color:#999;
        display: block;
      }
      strong{
        font-weight:900;
        font-size:14px;
        margin-top:5px;
        display: block;
        color: #EC4D4E;
      }
    }
  }
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
  .sub-sec{
    padding-top:10px;
    background:#fff;
    .left-sub-sec{
      padding:10px 20px;
      img{
        width:70px;
      }
    }
    .right-sub-sec{
      padding:5px 0;
      margin-top: 14px;
      .star{
        color:rgba(0,0,0,0);
        display:inline-block;
        width:18px;
        background-image:url(../../static/images/icon_star@2x.png);
        background-size: 100%;
      }
      .halfStar{
        color:rgba(0,0,0,0);
        margin-left:-3px;
        display:inline-block;
        width:18px;
        background-image:url(../../static/images/half_star@2x.png);
      }
      h4{
        font-weight:normal;
        font-size:15px;
      }
      p{
        font-size:12px;
        color:#666;
        margin-top:5px;
      }
    }
  }
</style>
