<template>
  <div class="loanProducts">
    <div></div>
    <div class="title" @click="home">
      {{titleName}}
    </div>
    <!--<div class="classBtn">-->
      <!--<ul>-->
        <!--<li v-for="item in titleList" :class="item.state" @click="changeFun(item.name)">{{item.name}}</li>-->
      <!--</ul>-->
    <!--</div>-->
    <div class="hot-loan">
      <div class="hot-loan-item clear" v-for="item in loans" :key="item.productId"
      @click="getProductDetail(item.productUrl,item.loanProduct.productName,item.loanProduct.productId)">
        <div class="item-icon fl">
          <img :src="item.loanProduct.iconUrl">
        </div>
        <div class="item-intro fl">
          <!--<h4>{{item.loanRange}}元</h4>-->
          <h4>{{item.loanProduct.productName}}</h4>
          <!--<p>{{item.intro}}</p>-->
          <p>期限范围：<span style="color:#f6ad34;">{{item.loanProduct.timeLimit}}&nbsp;&nbsp;&nbsp;</span></p>
        </div>
        <div class="btn">立即申请</div>
        <div class="describe">
          <div class="item border fl">
            <div class="text">{{item.loanProduct.loanRange}}</div>
            <div class="des">可贷额度（元）</div>
          </div>
          <div class="item fl">
            <div class="text">{{item.loanProduct.rate}}</div>
            <div class="des">{{item.loanProduct.rateType}}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Selector,Group } from 'vux'
import { dumbWrapper } from '../Ajax/vars.js';
import {
  getAdSpace,clickOnLoanProduct,getTagsByType,selectLoanProductByTag,getUserInfo,addRecord
  } from '../Ajax/post.js';
export default {
  name: 'loanProducts',
  components: {
    Selector,Group
  },
  data () {
    return {
      titleList:[
        {
          name: "热门排序",
          state: "color"
        },
        {
          name: "额度排序",
          state: ""
        },
        {
          name: "利率排序",
          state: ""
        }
      ],
      identityValue:'',
      loanMount:'',
      loanType:'',
      identityList:[],
      loanMountList:[],
      loanTypeList:[],
      loans:[],
      query:"",
      titleName:""
    }
  },
  methods:{
    home(){
      router.go(-1);
    },
    getProductDetail(url,name,id){
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
                recordObj:"uv",
                eventType:this.$route.query.index
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
                productName:name,
                recordType:"product",
                channelTag:this.query,
                recordObj:"pv",
                eventType:this.$route.query.index
              };
              console.log(data);
              dumbWrapper({
                promise:addRecord(data),
                successCB:()=>{
                }
              });
            }
            router.push({ path: '/productDetail', query: { id: id,type: "log",channel: this.query,index:this.$route.query.index }})
          }else if(e.state==10005){
            router.push({path:'/login?channel='+this.query + "&"})
          }
        },
        failCB:(e)=>{
          router.push({path:'/login?channel='+this.query})
        }
      })

    },
    onChange(val){
      console.log(this.identityList);
      var self=this;
      let label1=null;
      let label2=null;
      let label3=null;
      let tag1=null;
      let tag2=null;
      let tag3=null;
      if(this.identityValue){
        tag1=this.identityValue;
        label1=this.getLabel(val,1);
      }
      if(this.loanType){
        tag2=this.loanType;
        label2=this.getLabel(val,2);
      }
      if(this.loanMount){
        tag3=this.loanMount;
        label3=this.getLabel(val,3);
      }
      dumbWrapper({
        promise:selectLoanProductByTag({
          tag1:tag1,
          tag2:tag2,
          tag3:tag3
        }),
        successCB:(e)=>{
          this.loans = e.data;
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
    getLoanProduct(item) {
      // console.log(item)
      // itme =
      dumbWrapper({
        promise: getAdSpace({sortType:item}),
        successCB: (e) => {
          var array = [];
          var length = Math.floor(e.data.length / 4);
          if (e.data.length > 4) {
            // 分配
            for (var i = 0; i < length; i++) {
              if (i === 0) {
                array.push(e.data[item-1])
              } else if (i === length-1) {
                array.push(e.data[i*4+item-1]);
                // 余数分配
                var than = e.data.length % 4;
                if (than >= item) {
                  array.push(e.data[i*4+item-1+4])
                }
              } else {
                array.push(e.data[i*4+item-1])
              }
            }
          } else {
            array.push(item-1)
          }
          this.loans = array;
        },
        failCB:(e)=>{
          console.log(e)
        }
      });
    },
    changeFun(item) {
      if (item === "热门排序") {
        this.getLoanProduct(1);
      } else if (item === "额度排序") {
        this.getLoanProduct(2);
      } else {
        this.getLoanProduct(3)
      }
      for (var i = 0; i < this.titleList.length; i++) {
        if (item === this.titleList[i].name) {
          this.titleList[i].state = "color"
        } else {
          this.titleList[i].state = ""
        }
      }
    }
  },
  mounted(){
    // this.getLoanProduct(1);
    // this.getTagsByType('身份');
    // this.getTagsByType('贷款类型');
    // this.getTagsByType('金额');
    if (this.$route.query.channel) {
      this.query = this.$route.query.channel;
    } else {
      this.query = "000000";
    }
    switch (this.$route.query.index) {
      case "1":
        this.titleName = "秒批口子";
        this.getLoanProduct(1);
        break;
      case "2":
        this.titleName = "身份证贷";
        this.getLoanProduct(2);
        break;
      case "3":
        this.titleName = "黑户专享";
        this.getLoanProduct(3);
        break;
      case "4":
        this.titleName = "更黑口子";
        this.getLoanProduct(4);
        break;
    }
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
  z-index:100;
  border-bottom:1px solid #eee;
  line-height:40px;
  background: url("../../static/images/nav_back@2x.png") no-repeat 10px center #fff;
  background-size: 16px;
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
    margin-top:42px;
    .hot-loan-item{
      position:relative;
      box-sizing:border-box;
      border-bottom:1px solid #eee;
      margin: 0 10px;
      padding:20px 0 10px;
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
        /*padding:10px;*/
        width:20%;
        box-sizing:border-box;
        border-radius:5px;
        img{width:90%;border-radius:5px;}
        p{margin:0;font-size:13px;color:#333;}
      }
      .btn{
        position: absolute;
        right: 5px;
        top: 40px;
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
          font-size: 20px;
          font-weight: 400;
          color: red;
        }
        .des {
          font-size: 12px;
          color: #aaa;
        }
      }
      /*.border {*/
        /*border-right: 1px solid #eee;*/
      /*}*/
    }
    .hot-loan-item:last-child{
      border-bottom: 0;
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
