package hgu.se.raonz.post.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostUpdateRequest {
    private String title;
    private String content;
    private List<MultipartFile> fileList;
}
