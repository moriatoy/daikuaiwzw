<template>
	<div class="personalInfo" style="padding-top:45px;">
		<div class="title">
	      <p>个人信息</p>
	      <div class="icon-back" @click="goBack">
	        <img src="../../static/images/nav_back@2x.png">
	      </div>
	      <!--<div class="icon-right" @click="handleSave">-->
	        <!--<img src="../../static/images/ic_save.png">-->
	      <!--</div>-->
	    </div>
	    <div>
	    	<tab :line-width="2" custom-bar-width="80px" bar-active-color="#F68400" v-model="infoType" style="z-index:50;">
		      <tab-item selected active-class="active-6-3">基本信息</tab-item>
		      <tab-item active-class="active-6-3">身份信息</tab-item>
		       <tab-item active-class="active-6-3">资产信息</tab-item>
		    </tab>
		    <div v-show="infoType==0">
		    	<group style="margin-top:-21px;">
			      <x-input title="姓名" v-model="name" placeholder-align="right" label-width="105px" :show-clear="false">
			      	<img slot="right" src="../../static/images/icon_list_in@2x.png" style="width:8px;margin-right:-2px;">
			      </x-input>
			      <x-input title="身份证号" v-model="idNum" placeholder-align="right" label-width="105px" :show-clear="false">
			      	<img slot="right" src="../../static/images/icon_list_in@2x.png" style="width:8px;margin-right:-2px;">
			      </x-input>
			      <selector title="性别" :options="genderList" v-model="form.gender"></selector>
			      <x-address title="居住城市" v-model="city" :list="addressData" :raw-value="true" :hide-district="true"
			      value-text-align="left" label-width="105px" style="color:#000;">
				  </x-address>
			      <selector title="婚姻状况" :options="marriageList" v-model="form.maritalStatus"></selector>
			      <selector title="最高学历" :options="educationList" v-model="form.culturalLevel"></selector>
			      <datetime v-model="form.timeOfBirth" :min-year=1970 title="出生日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			      value-text-align="left"></datetime>
			    </group>
		    </div>
		    <div v-show="infoType==1">
		    	<group style="margin-top:-21px;">
		    		<selector placeholder="请选择" title="职业身份" :options="ocpList" v-model="form.ocp"></selector>
		    		<selector placeholder="请选择" title="月收入" :options="monthlyIncomeList" v-model="form.monthlyIncome"></selector>
		    		<selector placeholder="请选择" title="收入形式" :options="incomeFormList" v-model="form.incomeForm"></selector>
		    		<selector placeholder="请选择" title="本地社保" :options="localSocSecList" v-model="form.localSocSec"></selector>
		    		<selector placeholder="请选择" title="本地公积金" :options="localProvidentFundList" v-model="form.localProvidentFund"></selector>
		    	</group>
		    </div>
		    <div v-show="infoType==2">
		    	<group style="margin-top:-21px;">
		    		<selector placeholder="请选择" title="信用卡额度" :options="creditCardLimitList" v-model="form.creditCardLimit"></selector>
		    		<selector placeholder="请选择" title="名下房产" :options="propertyList" v-model="form.property"></selector>
		    		<selector placeholder="请选择" title="名下车产" :options="carList" v-model="form.car"></selector>
		    		<selector placeholder="请选择" title="是否贷款成功" :options="isLoanSuccessList" v-model="form.loanSuccess"></selector>
		    		<selector placeholder="请选择" title="个人保险" :options="psnlInsList" v-model="form.psnlIns"></selector>
		    		<selector placeholder="请选择" title="信用记录" :options="creditRecordList" v-model="form.creditRecord"></selector>
		    	</group>
		    </div>
	    </div>
      <div class="btn" @click="handleSave">保存</div>
	</div>
</template>

<script>
import { dumbWrapper } from '@/Ajax/vars'
import {getUserInfo,setUserInfo} from '@/Ajax/post'
import { Tab, TabItem,XInput, Group,XButton,Box,Flexbox, FlexboxItem,
	Selector,Datetime,XAddress, ChinaAddressV4Data,dateFormat,Value2nameFilter as value2name} from 'vux'
export default {
	name: '',
	components: {
		Tab,
	    TabItem,
	    XInput, Group,
	    XButton,Box,
	    Flexbox, FlexboxItem,
	    Selector,Datetime,XAddress
	},
	data () {
		return {
			infoType:0,
			addressData: ChinaAddressV4Data,
			name:null,
			idNum:null,
			city:[],
			form:{
				maritalStatus:null,
				culturalLevel:null,
				ocp:null,
				monthlyIncome:null,
				incomeForm:null,
				localSocSec:null,
				localProvidentFund:null,
				creditCardLimit:null,
				property:null,
				car:null,
				loanSuccess:null,
				psnlIns:null,
				creditRecord:null,
				gender:null,
				timeOfBirth:null,
			},
			marriageList:['未婚','已婚'],
			educationList:['高中','大专','本科以上','中专','初中'],
			ocpList:["上班族","自由职业","个体户"],
			monthlyIncomeList:["两千元以下","两千到四千元","四千到六千元","六千到八千元","八千元以上"],
			incomeFormList:["银行代发","转账工资","现金发放"],
			localSocSecList:["无","三个月以下","连续三个月","连续六个月"],
			localProvidentFundList:["无","三个月以下","连续三个月","连续六个月"],
			creditCardLimitList:["无","三千元以下","三千到一万元","一万到三万元","三万元以上"],
			propertyList:["无房产","有房产可接受抵押","有房产不接受抵押"],
			carList:["无车产","无车准备购买","有车可接受抵押","有车不接受抵押"],
			isLoanSuccessList:[
				{key:true,value:"是"},
				{key:false,value:"否"}
			],
			psnlInsList:["无","投保人寿险且投保两年以下","投保人寿险且投保两年以上"],
			creditRecordList:["无信用记录","信用记录良好","少量逾期"],
			genderList:['男','女'],
      query:''
		}
	},
	methods:{
		handleSave(){
			let data={...this.form};
			let birthday=data.timeOfBirth;
			data.timeOfBirth=Date.parse(birthday);
			if(this.city!=[]){
				data.address=this.getAddress();
			}
			data.realName=this.name;
			data.idNum=this.idNum;
			dumbWrapper({
				promise:setUserInfo(data),
				successCB:(e)=>{
					if(e.state==0){
						alert('保存成功！')
						this.getUserInfo();
						// setTimeout(function(){
						// 	router.push({path:'/mine'})
						// },500)
					}
				}
			})
		},
		getAddress(){
			let address=value2name(this.city, ChinaAddressV4Data).replace(/\s/g, "");
			return address;
		},
		goBack(){
			window.history.go(-1);
		},
		getUserInfo(){
		  let self = this
			dumbWrapper({
		  		promise:getUserInfo({}),
		  		successCB:(e)=>{
		  			if(e.state==0){
		  				this.name=e.data.realName;
		  				this.idNum=e.data.idNum;
			  			let data=e.data.userInfo;
			  			data.timeOfBirth=dateFormat(data.timeOfBirth, 'YYYY-MM-DD');
			  			this.form=data;
			  			let arr=e.data.address.split("省")
			  			arr[0]+="省"
			  			this.city=arr;
		  			}else{
		  				router.push({path:'/login?channel='+self.query})
		  			}
		  		},
		  		failCB:(e)=>{
		  			router.push({path:'/login?channel='+self.query})
		  		}
		  	})
		},
	},
	mounted(){
		this.getUserInfo();
    this.query = this.$route.query.channel;
	},
}
</script>

<style lang='less' scoped>
.active-6-3 {
  color: #F68400 !important;
  /*border-color: red !important;*/
}
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
  .icon-right{
  	margin:5px 0;
    height:24px;
    position:absolute;
    right:5px;
    top:0;
    padding:5px;
    img{height:100%;}
  }
}
.btn {
  position: fixed;
  bottom: 0;
  width: 100%;
  background: #F88A03;
  color: white;
  font-size: 20px;
  font-weight: 300;
  text-align: center;
  padding: 10px 0;
}
</style>
