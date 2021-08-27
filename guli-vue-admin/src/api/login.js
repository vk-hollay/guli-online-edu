import request from '@/utils/request'

/* export function login(username, password) {
  return request({
    url: '/eduservice/user/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/eduservice/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
} */

// 登录
export function login(username, password) {
 // debugger
  return request({
    url: '/admin_/acl/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

// 获取用户信息
export function getInfo(token) {
  return request({
    url: '/admin_/acl/index/info',
    method: 'get',
    params: { token }
  })
}

// 登出
export function logout() {
  //debugger
  return request({
    url: '/admin_/acl/index/logout',
    method: 'post'
  })
}

// 获取菜单权限数据
export function getMenu() {
  return request({
    url: '/admin_/acl/index/menu',
    method: 'get'
  })
}
