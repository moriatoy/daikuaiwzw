<template>
	<div style="padding-top:30px;">
		<div class="title">
	      <p>个人信息</p>
	      <div class="icon-back" @click="goBack">
	        <img src="../../static/images/nav_back@2x.png">
	      </div>
	    </div>
	    <group>
	      <cell title="头像" is-link @click.native="handleClick">
	      	<div>
		      	<input type="file" style="display: none" name="file" ref="file" accept="image/*" @change="handleChange">
		      	<span>
		      		<img :src="photoUrl" v-if="photoUrl" style="height:80px;margin-right:10px;border-radius: 50%;">
		      	</span>
		    	</div>
	      </cell>
	      <x-input title="昵称" v-model="username" text-align="right" :show-clear="false" @on-blur="handleBlur">
	      	<img slot="right" src="../../static/images/icon_list_in@2x.png" style="width:8px;margin-right:-2px;margin-left:10px;">
	      </x-input>
	      <cell title="手机号">{{phoneNum}}</cell>
	    </group>
	    <div v-transfer-dom>
	      <loading :show="show1" :text="text1"></loading>
	    </div>
	</div>
</template>

<script>
import { dumbWrapper } from '@/Ajax/vars'
import {getUserInfo,uploadPic,setAvatar,setUserName,uploadPicture} from '@/Ajax/post'
import { Loading,Cell,Group,XInput,TransferDomDirective as TransferDom } from 'vux'
export default {
	name:'changePhoto',
	directives: {
	  TransferDom
	},
	components:{
		Loading,Cell,Group,XInput
	},
	data(){
		return{
			username:'昵称',
			photoUrl:null,
			phoneNum:null,
			file:null,
			show1:false,
			text1:'上传中',
      query:''
		}
	},
	methods:{
		handleBlur(val){
			console.log(val)
			dumbWrapper({
				promise:setUserName({userName:val}),
				successCB:(e)=>{
					if(e.state==0){
						alert("修改成功！")
					}
				}
			})
		},
		handleChange(e){
			var self=this;
			let upLoadDom=this.$refs.file;
			var file=upLoadDom.files[0];
			const type=file.type;
			var formData = new FormData();
			this.show1=true;
			dumbWrapper({
				promise:uploadPicture({type}),
				successCB:(e)=>{
					if(e.state==0){
						let data=e.data;
		        formData.append("OSSAccessKeyId",data.OSSAccessKeyId);
		        formData.append("Signature",data.Signature);
		        formData.append("callback",data.callback);
		        formData.append("key", data.key);
		        formData.append("policy",data.policy);
		        formData.append('file', file);
		        $.ajax({
		          url: 'http://wawazzcashloan.oss-cn-hangzhou.aliyuncs.com/',
		          data: formData,
		          type: "POST",
		          processData: false,
		          contentType: false,
		          cache: false
		        }).then((res) => {
		          console.log(res)
		          let url=res.url;
		          self.setHeadPhoto(url)
		        })

					}

				}
			})
		},
		setHeadPhoto(url){
			var self=this;
			this.show1=true;
			dumbWrapper({
				promise:setAvatar({url:url}),
				successCB:(e)=>{
					if(e.state==0){
						self.photoUrl=url;
						setTimeout(function(){
							self.show1=false;
						},1000)
					}
				}
			})
		},
		handleClick(){
			this.$refs.file.click();
		},
		goBack(){
			window.history.go(-1);
		},
		getUserInfo(){
		  let self = this;
			dumbWrapper({
		  		promise:getUserInfo({}),
		  		successCB:(e)=>{
		  			if(e.state==0){
		  				this.username=e.data.userName;
		  				this.photoUrl=e.data.avatar;
		  				this.phoneNum=e.data.phoneNum;
		  			}else if(e.state==10005){
		  				router.push({path:'/login?channel='+self.query})
		  			}
		  		},
		  		failCB:(e)=>{
		  			router.push({path:'/login?channel='+self.query})
		  		}
		  	})
		},
	},
	created(){
		this.getUserInfo();
	},
	mounted(){
    this.query = this.$route.query.channel;
  },
}
</script>

<style lang='less' scoped>
.title{
  padding:5px 0;
  position:fixed;
  top:0;
  width:100%;
  text-align:center;
  background:#fff;
  z-index:100;
  border-bottom:1px solid #eee;
  p{
    font-size:16px;
    padding:4px 0;
  }
  .icon-back{
    margin:6px 0;
    height:22px;
    position:absolute;
    left:5px;
    top:0;
    padding:5px;
    img{height:100%;}
  }
}
</style>
