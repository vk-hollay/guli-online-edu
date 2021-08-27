'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
   // BASE_API: '"http://localhost:8001"' 
  // BASE_API: '"http://localhost:9001"' //改为nginx地址
  BASE_API: '"http://localhost:8222"' //改为gateway网关地址
})
