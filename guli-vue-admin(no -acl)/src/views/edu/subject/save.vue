<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download" />
          <a :href="'/static/subjectData.xlsx'">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload ref="upload" :auto-upload="false" :on-success="fileUploadSuccess" :on-error="fileUploadError"
          :disabled="importBtnDisabled" :limit="1" :action="BASE_API+'/eduservice/subject/addSubject'" name="file"
          accept="application/vnd.ms-excel"> <!-- accept=".xlsx, .xls"-->
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button :loading="loading" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        BASE_API: process.env.BASE_API, // 接口API地址
        // OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
        importBtnDisabled: false, // 按钮是否禁用,
        loading: false,
      }
    },
    created() {
    },
    methods: {
      //点击按钮上传文件到接口里面
      submitUpload() {
        this.importBtnDisabled = true
        this.loading = true
        this.$refs.upload.submit() //js: document.getElementById("upload").submit
      },
      //上传成功回调函数
      fileUploadSuccess(response) {
          console.log(response)
          if (response.success == true) {
            this.loading = false
            this.$message({
              type: 'success',
              message: '添加课程分类成功'
            })
          }
          else {
            this.loading = false
            this.$message({
              type: 'error',
              message: '上传文件类型错误'
            })
          }
          //跳转课程分类列表
      },
      //上传失败回调函数
      fileUploadError() {
        this.loading = false
        this.$message({
          type: 'error',
          message: '添加课程分类失败'
        })
      }
    }
  }
</script>

<style>
</style>
