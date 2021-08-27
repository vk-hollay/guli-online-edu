import request from '@/utils/request'

export default {
 getBannerList() {
    return request({
      url: `/educms/bannerfront/getAllBanner`,
      method: 'get'
    })
 }
}
