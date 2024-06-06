package hgu.se.raonz.team.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeamRequest {
    private String name;
    private String content;
    private String emailList;
    private MultipartFile img;
}
