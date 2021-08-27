import request from '@/utils/request.js'

export default {
  //获取所有教师（用于课程讲师下拉框）
  getTeacherList() {
    return request({
      url: `eduservice/teacher/findAll`,
      method: 'get'
    })
  },
  //添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: `eduservice/course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  //根据课程id查询课程基本信息
  getCourseInfo(id) {
    return request({
      url: `eduservice/course/getCourseInfo/${id}`,
      method: 'get'
    })
  },
  //修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `eduservice/course/updateCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  //课程确认信息显示
  getPublihCourseInfo(id) {
      return request({
          url: '/eduservice/course/getPublishCourseInfo/'+id,
          method: 'get'
      })
  },
  //课程最终发布
  publihCourse(id) {
      return request({
          url: '/eduservice/course/publishCourse/'+id,
          method: 'post'
      })
  },
  //获取课程列表
  getListCourse(current, limit, courseQuery) {
      return request({
          url: `/eduservice/course/${current}/${limit}`,
          method: 'post',
          data: courseQuery
      })
  },
  //删除课程
  deleteCourse(id) {
    return request({
      url: `/eduservice/course/deleteCourse/${id}`,
      method: 'delete'
    })
  }
}
