package hgu.se.raonz.post.application.service;



import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.post.application.dto.PostFileDto;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.entity.PostFile;
import hgu.se.raonz.post.domain.repository.PostFileRepository;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;

import hgu.se.raonz.postLike.domain.repository.PostLikeRepository;
import hgu.se.raonz.scrap.domain.repository.ScrapRepository;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.team.presentation.response.TeamRankResponse;
import hgu.se.raonz.team.presentation.response.TeamResponse;
import hgu.se.raonz.teamUser.domain.entity.TeamUser;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostService {
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;
    @Value("${spring.cloud.gcp.storage.bucket}") // application.yml에 써둔 bucket 이름
    private String bucketName;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostLikeRepository postLikeRepository;
    private final ScrapRepository scrapRepository;
    private final PostFileRepository postFileRepository;
    InputStream keyFile;
    Storage storage;


    @Transactional
    public Long addPost(PostRequest postRequest, int type, String userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) return null;

        Post post = Post.toAdd(postRequest, type, user);
        postRepository.save(post);
        System.out.println("save the post: " + post.getId() + post.getTitle() + post.getContent());
        System.out.println("Uploading the post Files");

        try {
//            keyFile = new FileInputStream(keyFileName);
            keyFile = ResourceUtils.getURL(keyFileName).openStream();
            storage = (Storage) StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(keyFile))
                    .build()
                    .getService();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        for (MultipartFile file: postRequest.getFileList()) {
            try {
                String objectName = UUID.randomUUID() + "_" + file.getOriginalFilename();

                // 버킷 객체 가져오기
                Bucket bucket = storage.get(bucketName);

                // 객체 생성 및 업로드
                Blob blob = bucket.create(objectName, file.getInputStream(), file.getContentType());

                // 업로드된 객체의 url 만들기
                String uploadedImageUrl = "https://storage.cloud.google.com/" + bucketName + "/" + objectName;

                PostFile postFile = postFileRepository.save(PostFile.toAdd(post, uploadedImageUrl, objectName));
                postFileRepository.save(postFile);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            keyFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
        // Get the files associated with the post
        List<PostFile> postFiles = postFileRepository.findByPostId(postId);

        try {
            keyFile = ResourceUtils.getURL(keyFileName).openStream();
            storage = (Storage) StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(keyFile))
                    .build()
                    .getService();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        // Delete the files from the bucket
        for (PostFile postFile : postFiles) {
            try {
                BlobId blobId = BlobId.of(bucketName, postFile.getObjectName());
                boolean deleted = storage.delete(blobId);
                if (deleted) {
                    System.out.println("Deleted file from bucket: " + postFile.getObjectName());
                } else {
                    System.out.println("Failed to delete file from bucket: " + postFile.getObjectName());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        // Close the key file stream
        try {
            keyFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Long returnId = post.getId();
        // Delete the post and associated files from the database
        postFileRepository.deleteAll(postFiles);
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

    @Transactional
    public List<PostDto> getRankLikePostResponseList() {
        List<Post> postList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();
        postList.sort(Comparator.comparing(Post::getLikeCount).reversed());

        int size = postDtoList.size();
        if (size > 10) size = 10;
        // top 10
        for (int i = 0; i < size ; i++) {
            postDtoList.add(PostDto.toResponse(postList.get(i)));
        }
        return postDtoList;
    }
}
