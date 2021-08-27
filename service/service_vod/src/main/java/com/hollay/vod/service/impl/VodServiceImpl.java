package com.hollay.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.hollay.servicebase.exception.MyException;
import com.hollay.vod.utils.AliyunKey;
import com.hollay.vod.service.VodService;
import com.hollay.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideoAliyun(MultipartFile file) {

        try {
            // fileName 上传文件原始名称
            String fileName = file.getOriginalFilename();
            // title 上传之后文件名称 (文件名从 01.mp4 截取为 01)
            String title = fileName != null ? fileName.substring(0, fileName.lastIndexOf(".")) : null;
            // inputStream 上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(AliyunKey.accessKeyId, AliyunKey.accessKeySecret, title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            //request.setShowWaterMark(true);
            /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
            //request.setCallback("http://callback.sample.com");
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeAliyunVideo(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(AliyunKey.accessKeyId, AliyunKey.accessKeySecret);
            //创建删除视频request对象
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            //向request中设置视频id
            deleteVideoRequest.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(deleteVideoRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "删除视频失败");
        }
    }

    @Override
    public void removeMoreAliyunVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(AliyunKey.accessKeyId, AliyunKey.accessKeySecret);
            //创建删除视频request对象
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            //将list集合的值转换成 1,2,3的字符串格式
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            //向request中设置视频id
            deleteVideoRequest.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(deleteVideoRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "删除视频失败");
        }
    }
}
