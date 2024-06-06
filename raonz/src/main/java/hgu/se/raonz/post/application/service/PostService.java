package hgu.se.raonz.post.application.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.entity.PostFile;
import hgu.se.raonz.post.domain.repository.PostFileRepository;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;

import hgu.se.raonz.postLike.domain.repository.PostLikeRepository;
import hgu.se.raonz.scrap.domain.repository.ScrapRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
//    @Value("${cloud.accessKey}")
//    private String accessKey;
//
//    @Value("${cloud.secretKey}")
//    private String secretKey;
//
//    @Value("${cloud.endPoint}")
//    private String endPoint;
//
//    @Value("${cloud.bucketName}")
//    private String bucketName;
//
//    private final AmazonS3 amazonS3;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostLikeRepository postLikeRepository;
    private final ScrapRepository scrapRepository;
    private final PostFileRepository postFileRepository;


    @Transactional
    public Long addPost(PostRequest postRequest, int type, String userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) return null;

        Post post = Post.toAdd(postRequest, type, user);
        postRepository.save(post);
        System.out.println("save the post: " + post.getId() + post.getTitle() + post.getContent());
        System.out.println("Uploading the post Files");
        for (MultipartFile file: postRequest.getFileList()) {
            try {
                String objectName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//                ObjectMetadata objectMetadata = new ObjectMetadata();
//                objectMetadata.setContentLength(file.getSize());
//                objectMetadata.setContentType(file.getContentType());
//                amazonS3.putObject(bucketName, objectName, file.getInputStream(), objectMetadata);
//
//                String uploadedImageUrl = String.format("%s/%s/%s", endPoint, bucketName, URLEncoder.encode(objectName, "UTF-8").replace("+", "%20"));
//                AccessControlList accessControlList = amazonS3.getObjectAcl(bucketName, objectName);
//                accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
//
//                amazonS3.setObjectAcl(bucketName, objectName, accessControlList);
                String uploadedImageUrl = objectName;
                PostFile postFile = postFileRepository.save(PostFile.toAdd(post, uploadedImageUrl, objectName));
                postFileRepository.save(postFile);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long deletePost(Long postId, String userId) {
        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) return null;
        if (!post.getUser().getId().equals(userId)) {
            System.out.println(userId);
            return null;
        }
        Long returnId = post.getId();
        postRepository.delete(post);

        return returnId;
    }

    @Transactional
    public Long updatePost(Long postId, PostUpdateRequest postUpdateRequest, String userId) {
        Post post = postRepository.findById(postId).orElse(null);

        if(post != null) {
            if (!post.getUser().getId().equals(userId)) { // 나중에 팀 클래스 생기면 수정 필요
                System.out.println(userId);
                return null;
            }
            post.setTitle(postUpdateRequest.getTitle());
            post.setContent(postUpdateRequest.getContent());
            postRepository.save(post);

            return post.getId();
        }

        return null;
    }

    @Transactional
    public PostResponse getPostResponse(Long postId, String userId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return null;
        System.out.println("Success to find Post");
        boolean isPostLike = postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId) != null;
        boolean isScrap = scrapRepository.findScrapByUserIdAndPostId(userId, postId) != null;
        return PostResponse.toResponse(post, isPostLike, isScrap);
    }

    @Transactional
    public List<PostResponse> getPostResponseList(int index, int type, String userId) {
        index--; // index == pageNum-1
        List<Post> postList = postRepository.findPostListByType(type);
        postList.sort(Comparator.comparing(Post::getId).reversed());
        List<PostResponse> postResponseList = new ArrayList<>();
        for (int i = index*10; i < index*10 + 10 && i < postList.size(); i++) {
            Long postId = postList.get(i).getId();
            boolean isPostLike = postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId) != null;
            boolean isScrap = scrapRepository.findScrapByUserIdAndPostId(userId, postId) != null;
            postResponseList.add(PostResponse.toResponse(postList.get(i), isPostLike, isScrap));
        }
        return postResponseList;
    }

    @Transactional
    public List<PostResponse> getPostResponseWithUserId(String userId) {
        List<Post> postList = postRepository.findPostListByUserId(userId);

        return postList.stream().map(PostResponse::toResponse).collect(Collectors.toList());
    }
}
