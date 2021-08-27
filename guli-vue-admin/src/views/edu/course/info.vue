<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"></el-step>
      <el-step title="创建课程大纲"></el-step>
      <el-step title="最终发布"></el-step>
    </el-steps>

    <el-form label-width="120px">

        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
        </el-form-item>

        <!-- 所属分类 TODO -->
        <el-form-item label="课程分类">
             <!-- 一级分类-->
            <el-select v-model="courseInfo.subjectParentId" placeholder="一级分类" @change="subjectLevelOneChanged">
                <el-option v-for="subject in subjectOneList" :key="subject.id" :label="subject.title" :value="subject.id"/>
            </el-select>

            <!-- 二级分类 -->
            <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
                <el-option v-for="subject in subjectTwoList" :key="subject.id" :label="subject.title" :value="subject.id"/>
            </el-select>
        </el-form-item>


        <!-- 课程讲师 TODO -->
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
          <el-select v-model="courseInfo.teacherId" placeholder="请选择">
              <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id"/>
          </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介 TODO -->
        <el-form-item label="课程简介">
            <!-- 富文本编辑器-->
            <tinymce :height="300" v-model="courseInfo.description"/>
        </el-form-item>


        <!-- 课程封面 TODO -->
        <!-- 课程封面-->
        <el-form-item label="课程封面">
            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :action="BASE_API+'/eduoss/fileoss'"
                class="avatar-uploader">
                <img :src="courseInfo.cover" style="width: 500px;">
            </el-upload>
        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
      </el-form>
  </div>
</template>

<script>
import courseApi from '@/api/edu/course.js'
import subjectApi from '@/api/edu/subject.js'
import Tinymce from '@/components/Tinymce' //引入富文本编辑器组件

export default {
   //声明组件(引入第三方组件需要声明)
  components: {
    Tinymce
  },
  data() {
    return {
       saveBtnDisabled:false,
        courseInfo:{
            title: '',
            subjectId: '',//二级分类id
            subjectParentId:'',//一级分类id
            teacherId: '',
            lessonNum: 0,
            description: '',
            cover: '/static/18005.jpg',
            price: 0
        },
        BASE_API: process.env.BASE_API, // 接口API地址
        teacherList:[],//封装所有的讲师
        subjectOneList:[],//一级分类
        subjectTwoList:[],//二级分类
        courseId: ''
    };
  },
  created() {
    //获取路由id值， 有值说明做修改操作，无值说明做添加操作
    if(this.$route.params && this.$route.params.id) {
       this.courseId = this.$route.params.id
       this.getInfo()
    } else {
      //初始化所有讲师
      this.getTeacherList()
      //初始化一级分类
      this.getOneSubject()
    }
  },
  watch: {  //监听
    $route(to, from) { //路由变化方式，路由发生变化，方法就会执行
      this.courseInfo = {
        description: '',
        cover: '/static/18005.jpg',
      }
    }
  },
  methods: {
     //上传封面成功调用的方法
    handleAvatarSuccess(res, file) {
        this.courseInfo.cover = res.data.url
    },
    //上传之前调用的方法
    beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2

        if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
    },
    //查询所有的讲师
    getTeacherList() {
      courseApi.getTeacherList().then(response => {
        console.log(response.data)
        this.teacherList = response.data.teacherList
      })
    },
    //查询所有的一级分类
    getOneSubject() {
      subjectApi.getSubjectTree().then(response => {
        console.log(response.data)
        this.subjectOneList = response.data.list
      })
    },
     //点击某个一级分类，触发change，显示对应二级分类
    subjectLevelOneChanged(value) {
        //value就是一级分类id值
        //遍历所有的分类，包含一级和二级
      for(var i=0;i<this.subjectOneList.length;i++) {
          //每个一级分类
          var oneSubject = this.subjectOneList[i]
          //判断：所有一级分类id 和 点击一级分类id是否一样
          if(value === oneSubject.id) {
              //从一级分类获取里面所有的二级分类
              this.subjectTwoList = oneSubject.children
              //把二级分类id值清空
              this.courseInfo.subjectId = ''
          }
      }
    },
    //保存并下一步
    addCourse() {
      if (this.courseInfo.teacherId == '' || this.courseInfo.title == '' || this.courseInfo.subjectId == '' || this.courseInfo.subjectParentId =='') {
        //提示
        this.$message({
            type: 'error',
            message: '请先填写必要信息!'
        });
      } else {
        courseApi.addCourseInfo(this.courseInfo).then(response => {
            //提示
            this.$message({
                type: 'success',
                message: '添加课程信息成功!'
            });
            //跳转到第二步
            this.$router.push({path:'/course/chapter/' + response.data.courseId})
          })
      }
    },
    //根据课程id查询
    getInfo() {
      courseApi.getCourseInfo(this.courseId).then(response => {
          //在courseInfo课程基本信息，包含 一级分类id 和 二级分类id
          this.courseInfo = response.data.courseInfoVo
          //1 查询所有分类，包含一级分类和二级分类
          subjectApi.getSubjectTree().then(response => {
              //2 获取所有一级分类
              this.subjectOneList = response.data.list
              //3 把所有的一级分类数组进行遍历
              for(var i=0;i<this.subjectOneList.length;i++) {
                  //获取每个一级分类
                  var oneSubject = this.subjectOneList[i]
                  //比较当前courseInfo里面一级分类id和所有的一级分类id
                  if(this.courseInfo.subjectParentId == oneSubject.id) {
                      //获取一级分类所有的二级分类
                      this.subjectTwoList = oneSubject.children
                  }
              }
          })
          //初始化所有讲师
          this.getTeacherList()
      })
    },
    //修改课程
    updateCourse() {
      if (this.courseInfo.title == '') {
        //提示
        this.$message({
            type: 'error',
            message: '课程标题信息不可为空!'
        });
        return
      }
      courseApi.updateCourseInfo(this.courseInfo).then(response => {
           //提示
          this.$message({
              type: 'success',
              message: '修改课程信息成功!'
          });
          //跳转到第二步
          this.$router.push({path:'/course/chapter/'+this.courseId})
       })
    },
    saveOrUpdate() {
       //判断添加还是修改
       if(!this.courseInfo.id) {
           //添加
           this.addCourse()
       } else {
            //修改
           this.updateCourse()
       }
    }
  }
}
</script>

<style scoped>
 /* 调整上传图片按钮的高度 */
.tinymce-container {
    line-height: 29px;
}
</style>
