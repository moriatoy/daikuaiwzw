<template>
	<div>
		<div class="title">
      <p>用钱管家注册协议</p>
      <div class="icon-back" @click="goBack">
        <img src="../../static/images/nav_back@2x.png">
      </div>
    </div>
    <div style="margin-top:40px;padding:10px;">{{agreement}}</div>
	</div>
</template>

<script>
import { dumbWrapper } from '../Ajax/vars.js';
import { getSystemInfoByKey } from '../Ajax/post.js';
export default {
	name:'',
	components:{

	},
	data(){
		return{
			agreement:''
		}
	},
	methods:{
		goBack(){
      window.history.go(-1);
    },
	},
	created(){
		dumbWrapper({
      promise:getSystemInfoByKey({key:'userAgreement'}),
      successCB:(e)=>{
        this.agreement=e.data;
      },
      failCB:(e)=>{
        
      }
    })
	},
	mounted(){},
}
</script>

<style lang='less' scoped>
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
</style>