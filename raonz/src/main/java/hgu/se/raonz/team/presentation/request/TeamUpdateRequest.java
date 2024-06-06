package hgu.se.raonz.team.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamUpdateRequest {
    private String name;
    private String content;
    private String leaderId;
    private String emailList;
}
