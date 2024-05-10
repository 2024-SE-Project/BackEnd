package hgu.se.raonz.post.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private List<String> fileList;
}
