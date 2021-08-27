import request from '@/utils/request.js'

export default {

  //获取课程分类列表（树形结构）  
  getSubjectTree() {
    return request({
      url: 'eduservice/subject/getAllSubject',
      method: 'get'
    })
  }

}
