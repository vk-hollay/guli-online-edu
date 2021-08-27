import request from '@/utils/request.js'

export default {

  //讲师列表，条件查询分页
  //current：当前页， limit:每页记录数， teacherQuery：条件对象
  getTeacherListPage(current, limit, teacherQuery) {
    return request({
      //url: '/eduservice/teacher/pageTeacherCondition/' + current + "/" + limit;
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery为条件对象, 后端使用RequestBody获取数据，
      //data表示把对象转换成json后再传递到接口里面
      data: teacherQuery
    })
  },
   //删除讲师
  deleteTeacherId(id) {
    return request({
        url: `/eduservice/teacher/delete/${id}`,
        method: 'delete'
    })
  },
  //添加讲师
  addTeacher(teacher) {
    return request({
        url: `/eduservice/teacher/addTeacher`,
        method: 'post',
        data: teacher
    })
  },
  //根据id查询讲师
  getTeacherInfo(id) {
    return request({
        url: `/eduservice/teacher/getTeacher/${id}`,
        method: 'get'
     })
  },
  //修改讲师
  updateTeacherInfo(teacher) {
    return request({
        url: `/eduservice/teacher/updateTeacher`,
        method: 'post',
        data: teacher
     })
  }

}
