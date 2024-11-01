package com.wen.post.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wen.common.domain.auth.vo.UserinfoVO;
import com.wen.common.domain.notification.NotificationAction;
import com.wen.common.domain.notification.UserNotification;
import com.wen.common.domain.notification.dto.LikePostNotificationDTO;
import com.wen.common.domain.post.*;
import com.wen.common.domain.post.dto.LikeDTO;
import com.wen.common.domain.post.dto.LikerDTO;
import com.wen.common.domain.post.dto.PostDTO;
import com.wen.common.domain.post.dto.ReviewPostDTO;
import com.wen.common.domain.post.vo.AuthorVO;
import com.wen.common.domain.post.vo.MediaVO;
import com.wen.common.domain.post.vo.PostVO;
import com.wen.common.lang.ResponseVO;
import com.wen.post.client.UserClient;
import com.wen.post.mapper.*;
import com.wen.post.service.PostService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostTopicMapper postTopicMapper;
    @Autowired
    private PostMediaMapper postMediaMapper;
    @Autowired
    private ReviewPostLogMapper reviewPostMapper;
    @Autowired
    private PostLikeMapper postLikeMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Resource
    private UserClient userClient;

    // 添加帖子
    @Override
    @Transactional
    public ResponseVO<?> addPost(PostDTO postDTO) {
        System.out.println("添加");
        Post post = new Post();
        post.setViews(0);
        post.setSaves(0);
        post.setComments(0);
        post.setShares(0);
        post.setLikes(0);
        post.setCreateAt(LocalDateTime.now());
        post.setType("3");
        post.setDelFlag(0);
        BeanUtils.copyProperties(postDTO, post);
        // 1. 添加帖子到数据库
        int i1 = postMapper.insert(post);
        if (i1 <= 0) {
            throw new RuntimeException("添加帖子失败");
        }
        
        // 2. 根据生成的帖子id，添加帖子-话题中间表数据
        // 2.1 获取话题id
        List<PostTopic> postTopics = new ArrayList<>();
        List<Long> topicIds = topicMapper.selectTopicIdsByTopicNames(postDTO.getTopics());
        Long postId = post.getId();
        for (Long topicId : topicIds) {
            PostTopic postTopic = new PostTopic();
            postTopic.setPostId(postId);
            postTopic.setTopicId(topicId);
            postTopics.add(postTopic);
        }
        // 2.3 批量添加帖子-话题中间表数据
        int i2 = postTopicMapper.insert(postTopics);
        if (i2 <= 0) {
            throw new RuntimeException("添加帖子-话题中间表数据失败");
        }

        // 3. 添加帖子的图片或视频数据
        // 3.1 获取图片或视频地址
        List<String> mediaUrls = postDTO.getMedias().getUrls();
        String videoId = postDTO.getMedias().getVideoId();
        String mediaType = postDTO.getMedias().getType();

        // 3.2 将图片视频的格式转换成实体类的格式
        List<PostMedia> postMedias = new ArrayList<>();

        // 判断媒体类型并创建 PostMedia 对象
        if (mediaUrls != null && !mediaUrls.isEmpty()) {
            for (String mediaUrl : mediaUrls) {
                PostMedia postMedia = createPostMedia(postId, mediaType, mediaUrl, null);
                postMedias.add(postMedia);
            }
        } else if (videoId != null && !videoId.isEmpty()) {
            PostMedia postMedia = createPostMedia(postId, mediaType, null, videoId);
            postMedias.add(postMedia);
        }

        // 3.3 批量添加图片或视频数据
        if (postMedias.isEmpty() || postMediaMapper.insert(postMedias) <= 0) {
            throw new RuntimeException("添加媒体数据失败");
        }

        // 3.3 批量添加图片或视频数据
        int i3 = postMediaMapper.insert(postMedias);
        if (i3 <= 0) {
            throw new RuntimeException("添加媒体数据失败");
        }

        return ResponseVO.success("成功发布帖子");
    }

    // 创建 PostMedia 实例
    private PostMedia createPostMedia(Long postId, String mediaType, String mediaUrl, String videoId) {
        PostMedia postMedia = new PostMedia();
        postMedia.setPostId(postId);
        postMedia.setType(mediaType);

        if (MediaType.IMAGE.name().equals(mediaType)) {
            postMedia.setUrl(mediaUrl);
        } else {
            postMedia.setVideoId(videoId);
        }

        return postMedia;
    }

    // 审核帖子
    @Override
    @Transactional
    public ResponseVO<?> reviewPost(ReviewPostDTO reviewPostDTO) {
        // 1. 查询帖子
        Post post = postMapper.selectById(reviewPostDTO.getPostId());
        if (post == null) {
            return new ResponseVO<>(404, "帖子不存在");
        }
        // 2. 审核帖子
        // 2.1 设置审核状态
        ReviewPostLog reviewPostLog = new ReviewPostLog();
        reviewPostLog.setPostId(reviewPostDTO.getPostId());
        reviewPostLog.setReviewerId(reviewPostDTO.getReviewerId());
        reviewPostLog.setReviewTime(LocalDateTime.now());
        reviewPostLog.setStatus(reviewPostDTO.getStatus());
        reviewPostLog.setComments(reviewPostDTO.getComments());

        // 2.2 添加审核记录并通过审核
        // 审核通过
        if (reviewPostDTO.getStatus().equals("1")) {
            // 2.2.1 审核通过，修改帖子状态为已发布
            post.setType("1");
            post.setCategoryId(reviewPostDTO.getCategoryId());
            int review = postMapper.review(post);
            if (review <= 0) {
                throw new RuntimeException("审核通过失败");
            }
            // 2.2.2 添加审核记录
            int i = reviewPostMapper.insert(reviewPostLog);
            if (i <= 0) {
                throw new RuntimeException("添加审核记录失败");
            }
            return ResponseVO.success("审核通过");
        }

        // 审核不通过
        int i = reviewPostMapper.insert(reviewPostLog);
        if (i <= 0) {
            throw new RuntimeException("添加审核记录失败");
        }
        return ResponseVO.success("审核不通过");
    }

    //点赞
    @Override
    public ResponseVO<?> likePost(LikeDTO likeDTO) {
        Long postId = likeDTO.getPostId();
        Long userId = likeDTO.getLiker().getUserId();
        Long targetUserId = likeDTO.getTargetUserId();

        PostLike postLike = postLikeMapper.selectByPostIdAndUserId(postId, userId);
        String TYPE = "like";

        System.out.println(postLike);
        if (postLike != null) {
            // 取消点赞逻辑
            int delete = postLikeMapper.delete(postId, userId);
            if (delete <= 0) {
                throw new RuntimeException("取消点赞失败");
            }

            String ACTION = NotificationAction.DELETE.name();
            // 封装取消点赞通知
            UserNotification cancelLikeNotification = createNotification(postId,likeDTO.getLiker(),targetUserId, TYPE, ACTION);//设置通知对象以及通知类型为点赞通知
            LikePostNotificationDTO likePostNotificationDTO = new LikePostNotificationDTO();
            likePostNotificationDTO.setNotification(cancelLikeNotification);
            likePostNotificationDTO.setAction(NotificationAction.DELETE.name());//取消点赞操作
            notificationService.sendNotification(likePostNotificationDTO);

            return ResponseVO.success("取消点赞成功");
        } else {
            // 点赞逻辑
            postLike = new PostLike();
            postLike.setPostId(postId);
            postLike.setUserId(userId);
            postLike.setCreatedAt(LocalDateTime.now());
            int insert = postLikeMapper.insert(postLike);
            if (insert <= 0) {
                throw new RuntimeException("点赞失败");
            }

            // 封装点赞通知
            String ACTION = NotificationAction.ADD.name();
            UserNotification likeNotification = createNotification(postId,likeDTO.getLiker(),targetUserId, TYPE, ACTION);//设置通知对象以及通知类型为点赞通知
            LikePostNotificationDTO likePostNotificationDTO = new LikePostNotificationDTO();
            likePostNotificationDTO.setNotification(likeNotification);
            likePostNotificationDTO.setAction(NotificationAction.ADD.name());//点赞操作
            notificationService.sendNotification(likePostNotificationDTO);

            return ResponseVO.success("点赞成功");
        }
    }

    // 模拟推荐帖子
    @Override
    public ResponseVO<?> testRecommendPost(Long userId) {
        List<Post> posts = postMapper.selectAll();
        List<PostVO> postVOs = new ArrayList<>();
        for (Post post : posts) {
            PostVO postVO = new PostVO();
            BeanUtils.copyProperties(post, postVO);
            // 添加帖子话题
            List<Long> topicIds = postTopicMapper.selectTopicIdsByPostId(post.getId());
            List<String> topicNames = topicMapper.selectTopicNames(topicIds);
            System.out.println("topicNames: " + topicNames);
            postVO.setTopics(topicNames);

            // 添加帖子分类
            if (post.getCategoryId() != null) {
                Category category = categoryMapper.selectById(post.getCategoryId());
                postVO.setCategory(category.getName());
            }

            // 添加帖子图片
            List<PostMedia> postMedia = postMediaMapper.selectByPostId(post.getId());
            String mediaType = postMedia.get(0).getType();
            List<String> mediaUrls = new ArrayList<>();
            for (PostMedia media : postMedia) {
                mediaUrls.add(media.getType());
            }
            MediaVO mediaVO = new MediaVO();
            mediaVO.setType(mediaType);
            mediaVO.setUrl(mediaUrls);
            postVO.setMedia(mediaVO);

            // 封装作者信息
            UserinfoVO user = userClient.getUserById(post.getUserId());
            AuthorVO author = new AuthorVO();
            author.setUserId(user.getId());
            author.setNickname(user.getNickname());
            author.setAvatar(user.getAvatar());
            postVO.setAuthor(author);

            // 添加点赞状态（假设未点赞）
            postVO.setLikeFlag(0);
            // 添加收藏状态（假设未收藏）
            postVO.setSaveFlag(0);

            postVOs.add(postVO);
        }


        return ResponseVO.success(postVOs);
    }

    // 收藏

    // 评论

    /**
     * 创建通知对象
     */
    private UserNotification createNotification(Long postId, LikerDTO likerDTO, Long targetUserId, String type, String action) {
        JSONObject content = new JSONObject();
        UserNotification notification = new UserNotification();

        if(action.equals(NotificationAction.ADD.name())){
            // 点赞通知的内容

            Post post = postMapper.selectById(postId);
            String contentSummary = getSummary(post.getContent(), 15, 30  );
            content.put("user_nickname", likerDTO.getNickname());
            content.put("user_avatar", likerDTO.getAvatar());
            content.put("content_title", contentSummary);
            String contentsStr = content.toJSONString();
            System.out.println("contentsStr:"+contentsStr);

            // 设置通知对象
            notification.setTargetUserId(targetUserId);
            notification.setTargetId(postId);
            notification.setTargetType("post");
            notification.setType(type);
            notification.setContent(contentsStr);
            notification.setIsRead(0);  // 未读
            notification.setCreatedAt(LocalDateTime.now());
            notification.setUpdatedAt(LocalDateTime.now());
        }else {
            notification.setTargetUserId(targetUserId);
            notification.setTargetId(postId);
            notification.setTargetType("post");
            notification.setType(type);
        }

        return notification;
    }

    /**
     * 截取内容
     */

    public static String getSummary(String content, int chineseCharLimit, int byteLimit) {
        int byteCount = 0;
        int length = content.length();
        StringBuilder summary = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char c = content.charAt(i);
            int charByteLength = String.valueOf(c).getBytes().length;

            // 判断字符的字节数，中文字符通常占2字节，其他字符占1字节
            if (charByteLength > 1) {
                byteCount += 2;  // 中文字符
            } else {
                byteCount += 1;  // 其他字符
            }

            // 如果字节数达到上限，截取并添加省略号
            if (byteCount + 3 > byteLimit) {  // 3 是省略号的字节长度
                return summary.append("...").toString();
            }

            summary.append(c);
        }

        // 如果内容没有超过字节上限，直接返回整个内容
        return summary.toString();
    }

}
