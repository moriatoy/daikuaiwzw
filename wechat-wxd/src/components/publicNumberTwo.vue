<template>
  <div class="homepage">
    <div class="carousel">
      <swiper :list="demo_list" auto style="width:100%;margin:0 auto;"
              v-model="demo_index" height="180px" dots-class="custom-bottom" dots-position="center"></swiper>
    </div>
    <div class="today-recommend">
      <div class="today-recommend-title">
        <p><span>$</span>&nbsp;&nbsp;今日推荐</p>
      </div>
      <div class="hot-loan-item clear" v-for="item in adSpace" :key="item.id"
           @click="getProductDetail(item.productUrl,item.productName)">
        <div class="item-icon fl">
          <img :src="item.productIcon">
        </div>
        <div class="item-intro fl">
          <h4>{{item.productName}}</h4>
          <p>期限范围：<span style="color:#f6ad34;">{{item.productPayTime}}&nbsp;&nbsp;&nbsp;</span></p>
        </div>
        <div class="btn">立即申请</div>
        <div class="describe">
          <div class="item border fl">
            <div class="text">{{item.productMoney}}</div>
            <div class="des">可贷额度（元）</div>
          </div>
          <div class="item fl">
            <div class="text">{{item.productRate}}</div>
            <div class="des">{{item.productRateType === 1 ? "日利率":"月利率"}}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  import { dumbWrapper } from '../Ajax/vars.js';
  import {
    getPublicCarousels2,getPublicLoanData2
  } from '../Ajax/post.js';
  import { Swiper } from 'vux'
  export default {
    name: '',
    components: { Swiper },
    data () {
      return {
        popImage: {
          img: null,
          jumpUrl: null,
        },
        show:false,
        demo_index:0,
        demo_list:[],
        adSpace:[],
        channel:null,
        query:''
      }
    },
    methods:{
      getProductDetail(url,name){
        _czc.push(["_trackEvent","今日推荐",name]);
        window.location.href = url
      },
      getCarousel(){
        dumbWrapper({
          promise: getPublicCarousels2(),
          successCB: (e) => {
            if (e.data) {
              for (let i = 0; i < e.data.length; i++) {
                let buf = {
                  img: e.data[i].carouselPicUrl,
                  url: e.data[i].redirectUrl,
                };
                this.demo_list.push(buf);
              }
            }
          }
        });
      },
      getData(){
        dumbWrapper({
          promise: getPublicLoanData2(),
          successCB: (e) => {
            this.adSpace = e.data;
          }
        });
      },
    },
    mounted(){
      this. getCarousel();
      this.getData()
    }
  }
</script>

<style lang='less' scoped>
  .hot-loan{
    padding-top:5px;
    background:#fff;
    margin-top:10px;
    .hot-loan-title{
      font-size:12px;
      border-bottom: 2px solid #eee;
      padding:5px 20px;
      span{
        color:rgba(255,255,255,0);
        display:inline-block;
        width:19px;
        background:url('../../static/images/icon_rmtj@2x.png');
        background-size:100% 100%;
      }
    }
    .hot-loan-item{
      position:relative;
      box-sizing:border-box;
      border-bottom:1px solid #eee;
      margin: 0 10px;
      padding:10px 0;
      h4{
        font-weight:400;
        font-size:18px;
        margin:0;
        padding-top: 10px;
      }
      .item-icon{
        text-align:center;
        padding:10px 10px 10px 0;
        width:25%;
        box-sizing:border-box;
        border-radius:5px;
        img{width:90%;border-radius:5px;}
        /*p{margin:0;font-size:13px;color:#333;}*/
      }
      .btn{
        position: absolute;
        right: 5px;
        top: 40px;
        padding: 5px 10px;
        border: 1px solid #F88700;
        border-radius: 8px;
        font-size: 13px;
        font-weight: 100;
        color: #F88700;
      }
      .item-intro{
        padding-top:10px;
        padding-left:5px;
        box-sizing:border-box;
        width:72%;
        margin-right:3%;
        overflow:hidden;
        p:last-child{
          margin-top:0;
          color:#aaa;
          font-size: 12px;
          font-weight: 100;
          span{
            font-size: 16px;
            font-weight: 300;
          }
        }
      }
      .describe{
        width: 100%;
        overflow: hidden;
        .item{
          width: calc(50% - 1px);
          text-align: center;
          .text{
            font-size: 20px;
            font-weight: 300;
            color: red;
          }
          .des {
            font-size: 12px;
            color: #aaa;
          }
        }
        .border {
          border-right: 1px solid #eee;
        }
      }
    }
    .hot-loan-item:last-child{
      border-bottom: 0;
    }
  }
  .today-recommend{
    padding-top:5px;
    background:#fff;
    margin-top:10px;
    .today-recommend-title{
      font-size:12px;
      border-bottom:2px solid #eee;
      padding:5px 20px;
      span{
        color:rgba(255,255,255,0);
        display:inline-block;
        width:19px;
        background:url('../../static/images/icon_jrtj@2x.png');
        background-size:100% 100%;
      }
    }
  }
  .carousel{
    background:#eee;
  }


  .hot-loan-item{
    position:relative;
    box-sizing:border-box;
    border-bottom:1px solid #eee;
    margin: 0 10px;
    padding:20px 0 10px;
    h4{
      font-weight:normal;
      font-size:14px;
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
      /*padding:10px;*/
      width:16%;
      box-sizing:border-box;
      border-radius:5px;
      img{width:90%;border-radius:5px;display: block}
      p{margin:0;font-size:13px;color:#333;}
    }
    .btn{
      position: absolute;
      right: 5px;
      top: 32px;
      padding: 5px 10px;
      border: 1px solid #F88700;
      border-radius: 8px;
      font-size: 13px;
      font-weight: 200;
      color: #F88700;
    }
    .item-intro{
      padding-top:5px;
      padding-left:5px;
      box-sizing:border-box;
      width:72%;
      margin-right:3%;
      overflow:hidden;
      p:last-child{
        margin-top:0;
        color:#aaa;
        font-size: 12px;
        font-weight: 100;
        span{
          font-size: 16px;
          font-weight: 300;
        }
      }
    }
    .describe{
      width: 100%;
      overflow: hidden;
      .item {
        width: calc(50% - 1px);
        text-align: center;
      }
      .text{
        font-size: 14px;
        font-weight: 400;
        color: red;
      }
      .des {
        font-size: 12px;
        color: #aaa;
      }
    }
  }

</style>
