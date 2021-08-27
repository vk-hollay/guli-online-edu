<template>
  <div class="app-container">
    讲师表单
     <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0" />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="String(teacher.avatar)"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
        <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调 -->
        <image-cropper
            v-show="imagecropperShow"
            :width="300"
            :height="300"
            :key="imagecropperKey"
            :url="BASE_API+'/eduoss/fileoss'"
            field="file"
            @close="close"
            @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>
<script>
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: {
    ImageCropper, PanThumb
  },
  data() {
    return {
      teacher:{
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: ''
      },
      saveBtnDisabled:false  ,// 保存按钮是否禁用,
      BASE_API: process.env.BASE_API, // 获取dev.env.js里面地址
      imagecropperShow: false, // 是否显示上传弹框组件
      imagecropperKey: 0 // 上传组件id
    }
  },
  created() { //页面渲染之前执行
    this.init()
  },
  watch: {  //监听
    $route(to, from) { //路由变化方式，路由发生变化，方法就会执行
      this.init()
    }
  },
  methods:{
    close() { //关闭上传弹框的方法
      this.imagecropperShow = false
      //重新打开上传组件时初始化组件（设置新的上传组件id），否则会显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    },
     //上传成功方法
    cropSuccess(data) {
      this.imagecropperShow = false
      //上传之后接口返回图片地址
      this.teacher.avatar = data.url
      // 上传成功后，重新打开上传组件时初始化组件（设置新的上传组件id），否则会显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    },
    init() {
      //判断路径有id值,做修改
      if(this.$route.params && this.$route.params.id) {
          //从路径获取id值
          const id = this.$route.params.id
          //调用根据id查询的方法，用于回显
          this.getInfo(id)
      } else { //路径没有id值，做添加
        //清空表单
        this.teacher = {}
        this.teacher.avatar = 'https://my-guli-edu-oss.oss-cn-beijing.aliyuncs.com/2021/07/17/7ebbd02154534bc5b66421898a9e271dfile.png'
      }
    },
    //根据讲师id查询,用于修改时的回显
    getInfo(id) {
      teacherApi.getTeacherInfo(id).then(response => {
          this.teacher = response.data.teacher
       })
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据teacher是否有id
      if(!this.teacher.id) {
        //添加
        this.saveTeacher()
      } else {
        //修改
        this.updateTeacher()
      }
    },
    //修改讲师的方法
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher).then(response => {
          //提示信息
          this.$message({
              type: 'success',
              message: '修改成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/teacher/table'})
        })
    },
    //添加讲师的方法
    saveTeacher() {
      teacherApi.addTeacher(this.teacher).then(response => { //添加成功
          //提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/teacher/table'})
        })
    }

  }
}
</script>
