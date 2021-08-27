import request from '@/utils/request'
export default {

    //添加小节
    addVideo(video) {
        return request({
            url: '/eduservice/video/addVideo',
            method: 'post',
            data: video
        })
    },

    //删除小节
    deleteVideo(id) {
        return request({
            url: '/eduservice/video/deleteVideo/'+id,
            method: 'delete'
        })
    },
    
    //删除视频
    removeAliyunVideo(id) {
        return request({
            url: `/eduvod/video/removeAliyunVideo/${id}`,
            method: 'delete'
        })
    }
}
