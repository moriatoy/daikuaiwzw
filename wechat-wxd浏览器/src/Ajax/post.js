import {
  server_url,
  dumbWrapper
} from './vars.js'
import request from 'superagent'

const factoryJson = (url) => (params) => {
  return Promise.resolve(
    new Promise((resolve, reject) => {
      request.post(server_url + url).type('application/json').send(params).end((error, res) => {
        error ? reject(error) : resolve(res.body);
      })
    })
  )
};

const factory = (url) => (params) => {
  return Promise.resolve(
    new Promise((resolve, reject) => {
      request.post(server_url + url).set('Content-Type', 'application/x-www-form-urlencoded').send(params).end((error, res) => {
        //console.log(error)
        error ? reject( res) : resolve(res.body);
      })
    })
  )
};
const upload=(url)=>(params)=>{
  return Promise.resolve(
    new Promise((resolve,reject)=>{
      request.post(server_url+url).send(params).end((error,res)=>{
         error ? reject( res) : resolve(res.body);
      })
    })
  )
};

//获取全局变量
export const getSystemInfoByKey = factory('/getSystemInfoByKey');

//上传图片
export const uploadPicture = factory('/media/upload');

//设置用户昵称
export const setUserName = factory('/setUserName');

//设置用户头像
export const setAvatar = factory('/setAvatar');

//上传图片
export const uploadPic = upload('/uploadPic');

//维护用户信息
export const setUserInfo = factory('/setUserInfo');

//获取用户信息
export const getUserInfo = factory('/getUserInfo');

//获取渠道列表
export const channel = factory('/channelList');

//退出登录
export const logout = factory('/logout');

//获取弹窗列表
export const getPopUpAdSapce = factory('/getPopUpAdSapce');

//通过tagId获取贷款产品
export const getOnlineLoanProductsByRecommendTag=factory('/getOnlineLoanProductsByRecommendTag');

//通过id获取产品详情
export const getLoanProductById = factory('/getLoanProductById');

//通过标签id筛选贷款产品
export const selectLoanProductByTag = factory('/selectLoanProductByTag');

//获取贷款标签
export const getTagsByType = factory('/getTagsByType');

//获取为你推荐
export const getNotNullRecommendTags = factory('/getNotNullRecommendTags');

//获取推荐位
export const getAdSpace = factory('/getAdSpace');

// 获取强推
export const getLoanProductByPosition = factory('/getLoanProductByPosition');

export const getPop = factory('/getPop');

//获取贷款详情
export const getCredit = factory('/getLoanProductByName');

//添加评论
export const addComment = factory('/addLabelComment');

//获取文章标签
export const getTags = factory('/getArticleTags');

//通过文章标签获得文章列表
export const getArticlesByTag = factory('/getArticlesByTag');

//通过文章标题获得文章内容
export const getArticle = factory('/getArticleByTitle');

//喜欢文章
export const likeArticle = factory('/likeArticle ');

//获取验证码
export const getCode = factory('/sendCheckCode');

//登录
export const login = factory('/userLogin');

//登录
export const commentLoan = factory('/commentLoan');

//获取开屏广告
export const getOpenScreenAd = factory('/getOpenScreenAd');

//获取轮播图
export const getCarouselPics = factory('/getCarouselPics');

//获取贷款平台
export const getApplets = factory('/getApplets');

//获取贷款产品
export const getOnlineLoanProduct = factory('/getOnlineLoanProduct');

//获取验证码
export const sendCheckCode = factory('/sendCheckCode');

//用户注册
export const signUpByPassword = factory('/signUpByPassword');

//用户通过密码登录
export const loginByPassword = factory('/loginByPassword');

//用户通过验证码登录
export const userLogin = factory('/userLogin');

//弹窗数据统计
export const clickOnOpenAd = factory('/clickOnOpenAd');

//小程序流量统计
export const clickOnApplet = factory('/clickOnApplet');

//贷款产品流量统计
export const clickOnLoanProduct = factory('/clickOnLoanProduct');

//轮播图流量统计
export const clickOnCarouselPic = factory('/clickOnCarouselPic');

//短信渠道统计
export const clickOnMainPage = factory('/clickOnMainPage');

export const getChannel = factory('/getChannel');

// 获取咨询列表
export const getInformation = factory('/getInformation');

// 获取公众号轮播图列表
export const getPublicCarousels1 = factory('/wx/selectCarousel1?rows=50&page=1');

// 获取公众号产品列表
export const getPublicLoanData1 = factory('/wx/getLoanProduct1?getType=1');

// 获取公众号轮播图列表
export const getPublicCarousels2 = factory('/wx/selectCarousel2?rows=50&page=1');

// 获取公众号产品列表
export const getPublicLoanData2 = factory('/wx/getLoanProduct2?getType=1');
