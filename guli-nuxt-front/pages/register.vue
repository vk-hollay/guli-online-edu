<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a class="active" href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="params">

        <el-form-item class="input-prepend restyle" prop="nickname" :rules="[{ required: true, message: '请输入你的昵称', trigger: 'blur' }]">
          <div>
            <el-input type="text" placeholder="你的昵称" v-model="params.nickname"/>
            <i class="iconfont icon-user"/>
          </div>
        </el-form-item>

        <el-form-item class="input-prepend restyle no-radius" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
          <div>
            <el-input type="text" placeholder="手机号" v-model="params.mobile"/>
            <i class="iconfont icon-phone"/>
          </div>
        </el-form-item>

        <el-form-item class="input-prepend restyle no-radius" prop="code" :rules="[{ required: true, message: '请输入验证码', trigger: 'blur' }]">
          <div style="width: 100%;display: block;float: left;position: relative">
            <el-input type="text" placeholder="验证码" v-model="params.code"/>
            <i class="iconfont icon-phone"/>
          </div>
          <div class="btn" style="position:absolute;right: 0;top:7px;width: 40%;">
            <img :src="verifyCodeUrl" alt="验证码" @click="getVerifyCode()">{{codeTest}}
         </div>
        </el-form-item>

        <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
            <el-input type="password" placeholder="设置密码" v-model="params.password"/>
            <i class="iconfont icon-password"/>
          </div>
        </el-form-item>

        <div class="btn">
          <input type="button" class="sign-up-button" value="注册" @click="submitRegister()">
        </div>
        <p class="sign-up-msg">
          点击 “注册” 即表示您同意并愿意遵守简书
          <br>
          <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce">用户协议</a>
          和
          <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">隐私政策</a> 。
        </p>
      </el-form>
      <!-- 更多注册方式 -->
      <div class="more-sign">
        <h6>社交帐号直接注册</h6>
        <ul>
          <li><a id="weixin" class="weixin" target="_blank" href="http://huaan.free.idcfengye.com/api/ucenter/wx/login"><i
            class="iconfont icon-weixin"/></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  import '~/assets/css/sign.css'
  import '~/assets/css/iconfont.css'

  import registerApi from '@/api/register'

  export default {
    layout: 'sign',
    
    data() {
      return {
        params: { //封装注册输入数据
          mobile: '',
          code: '',  //验证码
          nickname: '',
          password: ''
        },
        second: 5,        //倒计时间
        codeTest: '',
        verifyCodeUrl: "http://127.0.0.1:8006/edusms/sms/verifyCode",
        disable: false
      }
    },
    methods: {
       //注册提交的方法
       submitRegister() {
         registerApi.registerMember(this.params).then(response => {
           console.log(response)
              //提示注册成功
              this.$message({
                type: 'success',
                message: "注册成功"
              })
            //跳转登录页面
            this.$router.push({path:'/login'})
          })
       },
      timeDown() {
         /* 计时器   js自带的函数setInterval，clearInterval */
        let result = setInterval(() => {
          --this.second;
          this.codeTest = this.second
          if (this.second < 1) {
            clearInterval(result);
            this.disable = false;
            this.second = 5;
            this.codeTest = ""
          }
        }, 1000);
      },
      checkPhone (rule, value, callback) {
        //debugger
        if (!(/^1[34578]\d{9}$/.test(value))) {
          return callback(new Error('手机号码格式不正确'))
        }
        return callback()
      },
      getVerifyCode() {
        if (this.disable === true) {
          return
        }
        this.disable = true
        //路径后面需要加一个时间戳参数，才能实现点击切换验证码功能
        //因为浏览器带有缓存功能，当一个src中的url请求一次后，再次请求相同的url浏览器就不会再去请求获得新数据，而是直接用上次请求返回的数据
        this.verifyCodeUrl = "http://127.0.0.1:8006/edusms/sms/verifyCode?timestamp=" + new Date().getTime()
        this.timeDown()
      }
    }
  }
</script>
