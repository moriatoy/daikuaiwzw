<template>
  <div id="app">
    <transition name="fade">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'app',
  data () {
    return {

    }
  },
  mounted(){
    // document.body.removeChild(document.getElementById("Loading"))
  },
  created () {
    // document.body.removeChild(document.getElementById("Loading"))
    // document.getElementsByTagName("body").style.background = "#f5f5f5"
    // document.body.style.background = "#f5f5f5"
  }
}
</script>

<style lang="less">
@import '~vux/src/styles/reset.less';
html,body{
  height: 100%;
}
#app{
  height: 100%;
}
a{
	color:#999;
}
#scroll-head{
	transition:opacity 0.5s;
	opacity:0;
    background:#fff;
    padding:10px;
    border-bottom:1px solid #eee;
    h4{font-weight:normal;}
    position:fixed;
    width:100%;
    box-sizing:border-box;
    z-index:200;
  }
  .fl{float:left;}
  .fr{float:right;}
  .clear{zoom:1;}
  .clear:after{content:"";display:table;clear: both;}
  .weui-cells:before{
    border-top: 0!important;
  }
  .weui-cells:after{
    border-bottom: 0!important;
  }
  .weui-cell:before{
    border-top: 0!important;
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.25s ease-out;
  }

  .fade-enter, .fade-leave-to {
    opacity: 0;
  }
</style>
