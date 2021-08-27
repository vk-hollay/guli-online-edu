<template>
  <div class="app-container">
    讲师列表

    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList(1)">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="80" />

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

  <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />

  </div>
</template>

<script>

import teacherApi from '@/api/edu/teacher'

export default {
  // data: {
  // }，
  //在组件中的data必须写成函数的形式
  data() {
     return {
        list: null, //查询返回结果
        page: 1, //当前页
        total: 0, //总记录数
        limit: 7, //每页记录数
        teacherQuery: {} //查询条件
     }
  },
  created() { //页面渲染之前执行
    this.getList(1)
  },
  methods: {
    getList(page) {
      this.page = page
      teacherApi.getTeacherListPage(this.page, this.limit, this.teacherQuery)
        .then(response => {  //请求成功
            console.log(response)
            this.list = response.data.rows
            this.total = response.data.total
            console.log(this.list)
            console.log(this.total)

        })
        // .catch(error => {  //请求失败
        //     console.log(error)
        // })
    },
    resetData() {//清空的方法
        //表单输入项数据清空
        this.teacherQuery = {}
        //查询所有讲师数据
        this.getList(1)
    },
    //删除讲师的方法
    removeDataById(id) {
        this.$confirm('此操作将永久删除讲师记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {  //点击确定，执行then方法
            //调用删除的方法
            teacherApi.deleteTeacherId(id).then(response => { //删除成功
                this.$message({ //提示信息
                    type: 'success',
                    message: '删除成功!'
                });
                this.getList(this.page) //回到列表页面
            })
        }) //点击取消，执行catch方法
    }
  }
}

</script>

<style>
</style>
