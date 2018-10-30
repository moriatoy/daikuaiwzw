<template>
  <div class="homepage">
    <!--滚屏-->
    <div class="roll-screen">
      <div class="left">
        <img src="../../static/images/laba.png" style="width: 16px;padding-top: 5px;display: block" alt="">
      </div>
      <div class="left right">
        <div id="roll" :style="rollStyle">
          <div v-for="item in rollArray">
            <span>{{item.left}}****{{item.right}}在</span>
            <span class="orange">{{item.name}}</span>
            <span>成功借款</span>
            <span class="orange">{{item.number}}元</span>
          </div>
        </div>
      </div>
    </div>
    <!--特批推荐-->
    <div class="classification">
      <ul ref="elem">
        <li  @click="getApprovedDetail(1)">
          <div class="classLogo one" :style="'height:'+heightChange+'px;'">
            <div class="fication">
              <div class="text">
                <div class="title">极速放款</div>
                <div class="test">3分钟审批&nbsp;不等待</div>
              </div>
            </div>
          </div>
        </li>
        <li @click="getApprovedDetail(2)">
          <div class="classLogo two" :style="'height:'+heightChange+'px;'">
            <div class="fication">
              <div class="text">
                <div class="title">超低利率</div>
                <div class="test">额度大&nbsp;利润低</div>
              </div>
            </div>
          </div>
        </li>
        <li @click="getApprovedDetail(3)">
          <div class="classLogo three" :style="'height:'+heightChange+'px;'">
            <div class="fication">
              <div class="text">
                <div class="title">大额贷款</div>
                <div class="test">精品臻选&nbsp;品质保证</div>
              </div>
            </div>
          </div>
        </li>
        <li @click="getApprovedDetail(4)">
          <div class="classLogo four" :style="'height:'+heightChange+'px;'">
            <div class="fication">
              <div class="text">
                <div class="title">高通过率</div>
                <div class="test">放款量大&nbsp;通过率高</div>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
    <!--随机推荐-->
    <div class="random">
      <div class="title">
        <div class="left">经统计，同时申请以下四家，下款率达 <span>90%</span></div>
        <div class="right" @click="randomList">换一批</div>
      </div>
      <ul ref="randomClass">
        <li v-for="item in randomArray" @click="randomHref(item.productUrl,item.loanProduct.productName,item.loanProduct.productId)">
          <div class="classLogo" :style="'height:'+heightRadom+'px;background:url('+item.picUrl+') center center / 100% no-repeat'"></div>
          <p>{{item.loanProduct.productName}}</p>
        </li>
      </ul>
    </div>
    <!--轮播图-->
    <!--<div class="carousel" @click="clickOnCarousel">-->
      <!--<swiper :list="demo_list" auto style="width:100%;margin:0 auto;"-->
        <!--v-model="demo_index" height="180px" dots-class="custom-bottom" dots-position="center"></swiper>-->
    <!--</div>-->
    <!--<div class="recommend-for clear" style="display: none">-->
      <!--<div class="recommend-for-item fl" v-for="item in rcmdForYou">-->
        <!--<div class="item-img" @click="handleRoute(item.recommendTagId,item.title)">-->
          <!--<img :src="item.picUrl">-->
        <!--</div>-->
        <!--<p>{{item.title}}</p>-->
      <!--</div>-->
    <!--</div>-->
    <!--今日推荐-->
    <div class="today-recommend">
      <div class="today-recommend-item" v-for="item in adSpace" :key="item.id">
        <div class="item-sec clear" @click="getProductDetail(item.loanProduct.productId,item.loanProduct.productName,item.productUrl)">
          <div class="item-icon fl">
            <img :src="item.picUrl">
            <span>{{item.loanProduct.productName}}</span>
          </div>
          <div class="item-intro fl">
            <h4 style="padding-top: 2px;">{{item.loanProduct.productName}}</h4>
            <!--<p>{{item.productIntro}}</p>-->
            <p>贷款金额：<span>{{item.loanProduct.loanRange}}</span></p>
            <!--<p>{{item.loanProduct.rateType}}：<span>{{item.loanProduct.rate}}</span></p>-->
            <p>{{item.message}}</span></p>
          </div>
          <div class="btn">
            立即申请
          </div>
        </div>
      </div>
    </div>
    <div class="footer">
      <div class="index">
        <img src="../../static/images/tab_icon_home_sel@2x.png">
        <p>首页</p>
      </div>
      <a @click="loan">
        <div class="discover">
          <img src="../../static/images/tab_icon_daikuan_nor@2x.png">
          <p>贷款</p>
        </div>
      </a>
      <a @click="shequ">
        <div class="discover">
          <img src="../../static/images/shequ_icon.png">
          <p>攻略</p>
        </div>
      </a>
      <a @click="mine">
        <div class="mine">
          <img src="../../static/images/tab_icon_mine_nor@2x.png">
          <p>我的</p>
        </div>
      </a>
    </div>
    <div class="dialog-demo" v-if="coleFun">
      <div class="img-box">
        <img :src="consulImage" alt="" @click="consulHref">
        <div class="cole" @click="coleFun = false"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { XDialog,Swiper,TransferDomDirective as TransferDom,Icon } from 'vux'
import { dumbWrapper } from '../Ajax/vars.js';
import {
    getOpenScreenAd,getPop, getCarouselPics, getApplets, getOnlineLoanProduct,clickOnOpenAd,clickOnLoanProduct,clickOnCarouselPic,clickOnMainPage,getAdSpace,getNotNullRecommendTags,getUserInfo,channel,getLoanProductByPosition,addRecord
  } from '../Ajax/post.js';
export default {
  name: '',
  components: {
    XDialog,
    Swiper,
    Icon
  },
  directives: {
    TransferDom
  },
  data () {
    return {
      rollArray:[],
      rollStyle:"",
      heightChange:"",
      heightRadom:"",
      randomArray:[],
      popImage: {
        img: null,
        jumpUrl: null,
      },
      show:false,
      demo_index:0,
      demo_list:[],
      carouselList:[],
      loans:[],
      adSpace:[],
      produc:[],
      rcmdForYou:[],
      channel:null,
      query:'',
      consulUrl:'',
      consulImage:'',
      coleFun: false,
    }
  },
  methods:{
    loan(){
      router.push({path:'/loanProducts?channel='+this.query})
    },
    shequ(){
      router.push({path:'/consultation?channel='+this.query})
    },
    mine(){
      router.push({path:'/mine?channel='+this.query})
    },
    randomHref(url,name,id){
      // var i=this.demo_index;
      // var self=this;
      dumbWrapper({
        promise:getUserInfo({}),
        successCB:(e)=>{
          if(e.state==0){
            // 查看本地是否存在当天当前用户当前渠道的数据
            let time = new Date();
            let timeData = time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
            let nameData = "product_"+id+"_"+e.data.userId+"_"+this.query;
            if (window.localStorage.getItem(nameData) !== timeData) {
              // 记录打开应用的UV的统计
              let data = {
                productName:name,
                recordType:"product",
                channelTag:this.query,
                recordObj:"uv"
              };
              // alert(data.recordObj)
              window.localStorage.setItem(nameData,timeData);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{}
              });
            } else {
              // 记录打开应用PV的统计
              let data = {
                productName:name,
                recordType:"product",
                channelTag:this.query,
                recordObj:"pv"
              };
              // alert(data.recordObj)
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{}
              });
            }
            // location.href=url;
            router.push({ path: '/productDetail', query: { id: id,type: "log",channel:this.query }})
          }else if(e.state==10005){
            router.push({path:'/login?channel='+this.query})
          }
        },
        failCB:(e)=>{
          router.push({path:'/login?channel='+this.query})
        }
      })
    },
    consulHref(){
      dumbWrapper({
        promise:getUserInfo({}),
        successCB:(e)=>{
          if(e.state==0){
            // 查看本地是否存在当天当前用户当前渠道的数据
            let time = new Date();
            let timeData = time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
            let nameData = "event_"+e.data.userId+"_"+this.query;
            if (window.localStorage.getItem(nameData) !== timeData) {
              // 记录打开应用的UV的统计
              let data = {
                recordType:"event",
                channelTag:this.query,
                recordObj:"uv",
                eventType:0
              };
              console.log(data);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{
                  window.localStorage.setItem(nameData,timeData);
                }
              });
            } else {
              // 记录打开应用PV的统计
              let data = {
                recordType:"event",
                channelTag:this.query,
                recordObj:"pv",
                eventType:0
              };
              console.log(data);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{
                  // location.href=self.consulUrl;
                }
              });
            }
            var u = navigator.userAgent;
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isiOS) {
              window.webkit.messageHandlers.ShowBack.postMessage(window.location.href);
            }
            location.href= this.consulUrl;
          }else if(e.state==10005){
            router.push({path:'/login?channel='+this.query})
          }
        },
        failCB:(e)=>{
          router.push({path:'/login?channel='+this.query})
        }
      })
    },
    clickOnCarousel(){
      var i=this.demo_index;
      var self=this;
      dumbWrapper({
        promise:getUserInfo({}),
        successCB:(e)=>{
          if(e.state==0){
            if (this.channel) {
              _czc.push(["_trackEvent","总量",self.carouselList[i].name,this.channel]);
              _czc.push(["_trackEvent","轮播图",self.carouselList[i].name,this.channel]);
            } else {
              _czc.push(["_trackEvent","总量",self.carouselList[i].name]);
              _czc.push(["_trackEvent","轮播图",self.carouselList[i].name]);
            }
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            dumbWrapper({
              promise:clickOnCarouselPic({
                carouselId:this.carouselList[i].id
              }),
              successCB:(e)=>{
                if(e.state==0){
                  setTimeout(function(){
                    // if (isAndroid) {
                    //   Back.showBack(window.location.href);
                    // }
                    if (isiOS) {
                      window.webkit.messageHandlers.ShowBack.postMessage(window.location.href);
                    }
                    location.href=self.carouselList[i].url;
                    // router.push({path:'/thirdPage',query:{url:self.carouselList[i].url,name:self.carouselList[i].name}})
                  },100)
                }
              },
              failCB:(e)=>{
                setTimeout(function(){
                  if (isAndroid) {
                    Back.showBack(window.location.href);
                  } else if (isiOS) {
                    window.webkit.messageHandlers.ShowBack.postMessage(window.location.href);
                  }
                  location.href=self.carouselList[i].url;
                  // router.push({path:'/thirdPage',query:{url:self.carouselList[i].url,name:self.carouselList[i].name}})
                },100)
              }
            })
          }else if(e.state==10005){
            router.push({path:'/login?channel='+this.query})
          }
        },
        failCB:(e)=>{
          router.push({path:'/login?channel='+this.query})
        }
      })

    },
    jumpTo() {
      var self=this;
      dumbWrapper({
        promise:clickOnOpenAd({}),
        successCB:(e)=>{
          if(e.state==0){
            setTimeout(function(){
              location.href=self.popImage.jumpUrl;
            },100)
          }
        },
        failCB:(e)=>{
          setTimeout(function(){
            location.href=self.popImage.jumpUrl;
          },100)
        }
      })
      //router.push({path:'selfPop'});
      // setTimeout(function(){
      //   window.open(self.popImage.jumpUrl);
      // },1000)
    },
    getAd() {
      this.show=true;
      var myDate=new Date();
      localStorage.adTime=myDate.getTime();
      sessionStorage.adTime=1;
      dumbWrapper({
        promise: getOpenScreenAd({}),
        successCB: (e) => {
          if (e.data[0]) {
            this.popImage.img = e.data[0].picUrl;
            this.popImage.jumpUrl = e.data[0].jumpUrl;
          }
        }
      });
    },
    handleRoute(id,name){
      router.push({ path: '/recommendProduct', query: { tagId: id,tagName:name }})
    },
    getNotNullRecommendTags(){
      dumbWrapper({
        promise:getNotNullRecommendTags({}),
        successCB:(e)=>{
          if(e.data){
            let arr=[];
            for(var i=0;i<4;i++){
              if(e.data[i]){
                arr.push(e.data[i]);
              }
            }
            this.rcmdForYou=arr;
          }
        }
      })
    },
    getProductDetail(id,name,url){
      dumbWrapper({
        promise:getUserInfo({}),
        successCB:(e)=>{
          if(e.state==0){
            // 查看本地是否存在当天当前用户当前渠道的数据
            let time = new Date();
            let timeData = time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
            let nameData = "product_"+id+"_"+e.data.userId+"_"+this.query;
            if (window.localStorage.getItem(nameData) !== timeData) {
              // 记录打开应用的UV的统计
              let data = {
                productName:name,
                recordType:"product",
                channelTag:this.query,
                recordObj:"uv"
              };
              // alert(data.recordObj);
              window.localStorage.setItem(nameData,timeData);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{}
              });
            } else {
              // 记录打开应用PV的统计
              let data = {
                productName:name,
                recordType:"product",
                channelTag:this.query,
                recordObj:"pv"
              };
              // alert(data.recordObj);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{}
              });
            }
            router.push({ path: '/productDetail', query: { id: id,type: "log",channel:this.query }})


            // dumbWrapper({
            //   promise:clickOnLoanProduct({
            //     productId:id
            //   }),
            //   successCB:(e)=>{
            //     if(e.state==0){
            //       // let productUrl=url.replace(/\s/g,'');
            //       // window.location.href = productUrl;
            //       router.push({ path: '/productDetail', query: { id: id,type: "log",channel:this.query }})
            //     }
            //   },
            //   failCB:(e)=>{
            //     setTimeout(function(){
            //       // let productUrl=url.replace(/\s/g,'');
            //       // window.location.href = productUrl;
            //       router.push({ path: '/productDetail', query: { id: id,type: "log",channel:this.query }})
            //     },100)
            //   }
            // })
          }else if(e.state==10005){
            router.push({path:'/login?channel='+this.query + "&"})
          }
        },
        failCB:(e)=>{
          router.push({path:'/login?channel='+this.query})
        }
      })

      // zhuge.track("产品申请页",{
      //     'name':name,
      //     'from':"今日推荐"
      //   })
      // setTimeout(function(){
      //   window.open(productUrl);
      // },1000)
    },
    getApprovedDetail(id){
      dumbWrapper({
        promise:getUserInfo({}),
        successCB:(e)=>{
          if(e.state==0){
            // 查看本地是否存在当天当前用户当前渠道的数据
            let time = new Date();
            let timeData = time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
            let name = "event_"+id+"_"+e.data.userId+"_"+this.query;
            if (window.localStorage.getItem(name) !== timeData) {
              // 记录打开应用的UV的统计
              let data = {
                recordType:"event",
                channelTag:this.query,
                recordObj:"uv",
                eventType:id
              };
              console.log(data);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{
                  window.localStorage.setItem(name,timeData);
                }
              });
            } else {
              // 记录打开应用PV的统计
              let data = {
                recordType:"event",
                channelTag:this.query,
                recordObj:"pv",
                eventType:id
              };
              console.log(data);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{
                }
              });
            }
            // location.href=url;
            router.push({path:'/products?channel='+this.query+"&index="+id})
          }else if(e.state==10005){
            router.push({path:'/login?channel='+this.query})
          }
        },
        failCB:(e)=>{
          router.push({path:'/login?channel='+this.query})
        }
      });
    },
    gotoProductDetail(id,name){
      dumbWrapper({
        promise:clickOnLoanProduct({
          productId:id
        }),
        successCB:(e)=>{
          router.push({ path: '/productDetail', query: { id: id,type: "hot" }})
        },
        failCB:(e)=>{
          router.push({ path: '/productDetail', query: { id: id,type: "hot" }})
        }
      })

      // zhuge.track("产品详情",{
      //   'name':name,
      //   'from':"热门贷款"
      // })

    },
    // 贷款列表
    getAdSpace(){
      dumbWrapper({
        promise:getAdSpace({}),
        successCB:(e)=>{
          this.adSpace=e.data;
          this.rollScreen();
          this.randomList();
        }
      })
    },
    getLoanProductByPosition(){
      dumbWrapper({
        promise:getLoanProductByPosition({}),
        successCB:(e)=>{
          // console.log(e)
          var array = [];
          for (var i = 0; i < e.data.length; i++) {
            var obj = {};
            switch (e.data[i].position) {
              case 1:
                obj.name = "秒批口子";
                obj.image = require("../../static/images/title1.png");
                break;
              case 2:
                obj.name = "身份证贷";
                obj.image = require("../../static/images/title2.png");
                break;
              case 3:
                obj.name = "黑户专享";
                obj.image = require("../../static/images/title3.png");
                break;
              case 4:
                obj.name = "更黑口子";
                obj.image = require("../../static/images/title4.png");
                break;
            }
            obj.url = e.data[i].productUrl;
            array.push(obj)
          }
          this.produc = array;
          // 设置元素的高度
          var height = this.$refs.elem.offsetWidth;
          this.heightChange = height/100*30/100*66;
        }
      })
    },
    getCarousel(){
      dumbWrapper({
        promise: getCarouselPics({}),
        successCB: (e) => {
          if (e.data) {
            for (let i = 0; i < e.data.length; i++) {
              let buf = {
                img: e.data[i].carouselPicUrl,
                //url: e.data[i].redirectUrl,
              }
              this.demo_list.push(buf);
              let cal={
                url: e.data[i].redirectUrl,
                id:e.data[i].carouselPicId,
                name:e.data[i].carouselPicNote
              }
              this.carouselList.push(cal);
            }
          }
        }
      });
    },
    getLoanProduct() {
      // dumbWrapper({
      //   promise: getOnlineLoanProduct({}),
      //   successCB: (e) => {
      //     this.loans = e.data;
      //   },
      //   failCB:(e)=>{
      //     console.log(e)
      //   }
      // });
    },
    // 生成随机数
    random(start,end){
      return Math.floor(Math.random()*(end-start))-start;
    },
    // 随机生成手机号开头
    phoneNumber(){
      var array = [139,138,137,136,135,134,159,158,157,150,151,152,188,130,131,132,156,155,133,153,189];
      var number = this.random(0,array.length);
      return array[number]
    },
    // 随机生成手机后四位
    phoneEnd(){
      var number = this.random(0,9999);
      if (number < 10) {
        number = "000"+number;
      } else if (number >= 10 && number < 100) {
        number = "00"+number;
      } else if (number >= 100 && number < 1000) {
        number = "0"+number;
      }
      return number;
    },
    // 随机取两条贷款数据
    rollScreen(){
      // 随机取两条贷款数据
      var dataArray = [];
      for (var i = 0; i < 2; i++) {
        var data = this.adSpace[this.random(0,this.adSpace.length)];
        var dataObeject = {};
        dataObeject.left = this.phoneNumber();
        dataObeject.right = this.phoneEnd();
        dataObeject.name = data.loanProduct.productName;
        dataObeject.number = data.loanProduct.loanRange.split("-")[1];
        dataArray.push(dataObeject);
      }
      this.rollArray = dataArray;
    },
    // 定时三秒滚屏一次
    rollFun(){
      clearInterval(this.rollInter);
      var self = this;
      this.myInterval = setInterval(function() {
        var number = 1;
        clearInterval(self.interVal);
        self.interVal = setInterval(function() {
          number++;
          self.rollStyle = "top: -"+number+"px";
          if (number >= 22) {
            self.rollStyle = "top: 0";
            self.rollArray.splice(0,1);
            var data = self.adSpace[self.random(0,self.adSpace.length)];
            var dataObeject = {};
            dataObeject.left = self.phoneNumber();
            dataObeject.right = self.phoneEnd();
            dataObeject.name = data.loanProduct.productName;
            dataObeject.number = data.loanProduct.loanRange.split("-")[1];
            self.rollArray.push(dataObeject);
            clearInterval(self.interVal);
          }
        },20);
      },3440);
    },
    // 随机不重复的数字
    getx(arr,start,end){
      for(var i=0;i>-1;i++){
        var flag = true;
        var num = Math.floor(Math.random()*end)+start;
        for(var i in arr){
          if(arr[i] == num){
            flag= false;
            break;
          }
        }
        if(flag == true){
          arr.push(num);
          return num;
        }
      }
    },
    // 随机贷款列表
    randomList(){
      var dataArray = [];
      var array = [];
      for (var i = 0; i < 4; i++) {
        var index = this.getx(array,0,this.adSpace.length);
        var data = this.adSpace[index];
        dataArray.push(data);
      }
      this.randomArray = dataArray;
      var height = this.$refs.randomClass.offsetWidth;
      this.heightRadom = height/100*25/100*60;
    },
    getPop(){
      dumbWrapper({
        promise: getPop({}),
        successCB: (e) => {
          this.consulImage = e.data.popImage;
          this.consulUrl = e.data.popUrl;
        }
      });
    },
  },
  beforeDestroy() {
    clearInterval(this.interVal);
    clearInterval(this.myInterval);
  },
  mounted(){
    var self=this;
    if(this.$route.query.messageId){
      dumbWrapper({
        promise:clickOnMainPage({
          messageId:this.$route.query.messageId
        })
      })
    }else{
      dumbWrapper({
        promise:clickOnMainPage({
          messageId:2
        })
      })
    }
    if(localStorage.adTime){
      var myDate=new Date();
      if (myDate.getTime()-localStorage.adTime>=28800000){
        self.getAd();
      }
    }else{
      self.getAd();
    }
    //self.getAd();
    this.getCarousel();
    // this.getLoanProduct();
    this.getAdSpace();
    this.getNotNullRecommendTags();
    this.rollFun();
    this.getPop();
    this.getLoanProductByPosition();
    // 登录信息
    dumbWrapper({
      promise:getUserInfo({}),
      successCB:(e)=>{
        if(e.state==="0"){
          // 记录登录后每次重新打开应用
          window.sessionStorage.setItem("coleTest",'false');
          var first = window.sessionStorage.getItem("first");
          if (!first) {
            // 弹出弹框并标记已经弹出过
            this.coleFun = true;
            window.sessionStorage.setItem("first",true);
            // 查看本地是否存在当天当前用户当前渠道的数据
            let time = new Date();
            let timeData = time.getFullYear()+"-"+(time.getMonth()+1)+"-"+time.getDate();
            let name = "index_"+e.data.userId+"_"+this.query;
            if (window.localStorage.getItem(name) !== timeData) {
              // 记录打开应用的UV的统计
              let data = {
                recordType:"index",
                channelTag:this.query,
                recordObj:"uv"
              };
              console.log(data);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{
                  window.localStorage.setItem(name,timeData);
                }
              });
            } else {
              // 记录打开应用PV的统计
              let data = {
                recordType:"index",
                channelTag:this.query,
                recordObj:"pv"
              };
              console.log(data);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{
                }
              });
            }
          } else {
            this.coleFun = false;
          }
          // if (e.data.channelTag !== null) {
          //   dumbWrapper({
          //     promise:channel({}),
          //     successCB:(item)=>{
          //       for (let i = 0; i < item.data.length; i++) {
          //         if (item.data[i].channelTag === e.data.channelTag) {
          //           this.channel = item.data[i].channelName;
          //         }
          //       }
          //     }
          //   })
          // }
        }
      }
    });
    if (this.$route.query.channel) {
      this.query = this.$route.query.channel;
    } else {
      this.query = "000000";
    }
  }
}
</script>

<style lang='less' scoped>
  .homepage{padding-bottom:50px;}
  /*滚屏*/
  .roll-screen{
    border-bottom: 2px solid #eee;
    background: #fff;
    padding: 5px 16px;
    font-size: 14px;
    overflow: hidden;
    .left{
      float: left;
    }
    .right{
      width:calc(100% - 23px);
      padding-left: 10px;
      height: 22px;
      overflow: hidden;
      position: relative;
      #roll{
        position: absolute;
        top: 0;
        left: 10px;
      }
    }
    .orange{
      color: #f6ad34;
    }

  }
  @media screen and (max-width: 360px) {
    .roll-screen{
      font-size: 13px;
    }
  }
  /*特批推荐*/
  .classification{
    background: #fff;
    margin-bottom: 10px;
    ul{
      list-style: none;
      overflow: hidden;
      padding: 0 10px;
      li{
        width: 50%;
        margin: 10px 0;
        float: left;
        .classLogo{
          width: 90%;
          margin: 0 5%;
          -webkit-border-radius: 10px;
          -moz-border-radius: 10px;
          border-radius: 10px;
        }
        .one{
          background: -webkit-linear-gradient(#FAD961,#F76B1C); /* Safari 5.1 - 6.0 */
          background: -o-linear-gradient(#FAD961,#F76B1C); /* Opera 11.1 - 12.0 */
          background: -moz-linear-gradient(#FAD961,#F76B1C); /* Firefox 3.6 - 15 */
          background: linear-gradient(#FAD961,#F76B1C); /* 标准的语法 */
          .fication{
            background: url("../../static/images/class1.png") no-repeat right bottom;
            background-size: 30%;
          }
        }
        .two{
          background: -webkit-linear-gradient(top left,#3023AE,#F679D7); /* Safari 5.1 - 6.0 */
          background: -o-linear-gradient(top left,#3023AE,#F679D7); /* Opera 11.1 - 12.0 */
          background: -moz-linear-gradient(top left,#3023AE,#F679D7); /* Firefox 3.6 - 15 */
          background: linear-gradient(top left,#3023AE,#F679D7); /* 标准的语法 */
          .fication{
            background: url("../../static/images/class2.png") no-repeat right bottom;
            background-size: 30%;
          }
        }
        .three{
          background: -webkit-linear-gradient(#82BFFE,#65DDFF); /* Safari 5.1 - 6.0 */
          background: -o-linear-gradient(#82BFFE,#65DDFF); /* Opera 11.1 - 12.0 */
          background: -moz-linear-gradient(#82BFFE,#65DDFF); /* Firefox 3.6 - 15 */
          background: linear-gradient(#82BFFE,#65DDFF); /* 标准的语法 */
          .fication{
            background: url("../../static/images/class3.png") no-repeat right bottom;
            background-size: 30%;
          }
        }
        .four{
          background: -webkit-linear-gradient(#6F90FF,#6249FF); /* Safari 5.1 - 6.0 */
          background: -o-linear-gradient(#6F90FF,#6249FF); /* Opera 11.1 - 12.0 */
          background: -moz-linear-gradient(#6F90FF,#6249FF); /* Firefox 3.6 - 15 */
          background: linear-gradient(#6F90FF,#6249FF); /* 标准的语法 */
          .fication{
            background: url("../../static/images/class4.png") no-repeat right bottom;
            background-size: 30%;
          }
        }
        .fication{
          width: 100%;
          height: 100%;
          font-size: .9rem;
          color: #fff;
          opacity: .7;
          position: relative;
          text-indent: 1rem;
          .text{
            position: absolute;
            top: 50%;
            transform: translate(0, -50%);
            .title{
              padding-bottom: 5%;
            }
          }
        }
        @media screen and (max-width: 360px) {
          .fication{
            font-size: .7rem;
          }
        }
      }
    }
  }
  /*随机推荐*/
  .random{
    margin-bottom: 10px;
    background: #fff;
    .title{
      border-bottom: 2px solid #eee;
      overflow: hidden;
      padding: 8px 16px;
      font-size: 14px;
      .left{
        float: left;
        span{
          color: #f6ad34;
        }
      }
      .right{
        float: right;
        color: #f6ad34;
      }
    }
    @media screen and (max-width: 360px) {
      .title{
        font-size: 12px;
        padding: 10px;
      }
    }
    ul{
      list-style: none;
      overflow: hidden;
      li{
        width: 25%;
        margin: 10px 0;
        float: left;
        .classLogo{
          background: red;
          width: 60%;
          margin: 0 20%;
          -webkit-border-radius: 50%;
          -moz-border-radius: 50%;
          border-radius: 50%;
        }
        p{
          margin-top: 10px;
          text-align: center;
          font-size: 14px;
        }
      }
    }
  }
  /**/
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
      /*p{*/
        /*white-space: nowrap;*/
        /*text-overflow:ellipsis;*/
        /*overflow:hidden;*/
        /*font-size:14px;*/
        /*color:#999;*/
      /*}*/
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
    .today-recommend-item{
      border-bottom:1px solid #f7f7f7;
      padding:0 10px;
      h4{
        font-weight:normal;
        font-size:14px;
        white-space: nowrap;
        text-overflow:ellipsis;
        overflow:hidden;
      }
      p{
        font-size:12px;
        color:#999;
        /*margin-top:5px;*/
      }
      .item-sec:first-child{
        border-bottom:0;
        padding:10px 0;
      }
      .item-sec{
        position: relative;
        padding-top:3px;
        border-bottom:1px solid #f0f0f0;
        .item-icon{
          position:relative;
          text-align:center;
          width:20%;
          margin-bottom:-6px;
          border-radius:10px;
          img{width:100%;height:100%;border-radius:10px;}
          span{
            white-space: nowrap;
            text-overflow:ellipsis;
            overflow:hidden;
            position:absolute;
            bottom:6px;
            left:0;
            width:100%;
            border-radius:0 0 10px 10px;
            background:rgba(0,0,0,0.3);
            color:#fff;
            font-size:13px;
          }
        }
        .item-intro{
          padding:0 10px 0 20px;
          box-sizing:border-box;
          width:80%;
          overflow:hidden;
          height:70px;
          h4{
            font-size: 14px;
          }
          p{
            span{
              color: rgb(246, 173, 52);
              font-size: 16px;
            }
          }
        }
        .btn{
          position: absolute;
          right: 5px;
          top: 24px;
          padding: 5px 10px;
          border: 1px solid #F88700;
          border-radius: 8px;
          font-size: 13px;
          font-weight: 100;
          color: #F88700;
        }
        /*.item-mark{*/
          /*padding-left:10px;*/
          /*box-sizing:border-box;*/

        /*}*/
        /*.item-button{*/
          /*button{*/
            /*font-size:14px;*/
            /*color:#00aeff;*/
            /*background:#fff;*/
            /*padding:5px;*/
            /*border:none;*/
          /*}*/
          /*span{*/
            /*padding:5px 10px;*/
            /*line-height:20px;*/
            /*img{width:12px;}*/

          /*}*/
        /*}*/
      }

    }
  }
  .recommend-for{
    background:#fff;
    padding:0 5px;
    .recommend-for-item{
      width:25%;
      box-sizing:border-box;
      padding:15px 0;
      text-align:center;
      font-size:12px;
      color:#666;
      .item-img{
        box-sizing:border-box;
        width:50px;
        height:50px;
        margin:0 auto;
        border-radius:25px;
        background:#eee;
        overflow:hidden;
        img{height:100%;}
      }
    }
  }
  .carousel{
    background:#eee;
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
      p{font-size:10px;margin-top:-5px;color:#F98902;}
      img{width:25px;}
    }
    .discover{
      margin-left:60px;
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;}
      img{width:25px;}
    }
    .mine{
      margin-left:60px;
      display:inline-block;
      text-align:center;
      p{font-size:10px;margin-top:-5px;}
      img{width:25px;}
    }
  }
  .dialog-demo{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.5);
    .img-box{
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      width: 80%;
      img{
        width: 100%;
      }
      .cole{
        width: 30px;
        height: 30px;
        background: url("../../static/images/icon.png") no-repeat center center;
        background-size: 100%;
        position: absolute;
        right: 0;
        top: -30px;
      }
    }
  }

  @media screen and (max-width: 340px) {
    .item-sec{
      .item-intro{
        padding: 10px 10px 0 10px!important;
      }
    }
  }
</style>
