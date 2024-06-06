package hgu.se.raonz.scrap.presentation.controller;


import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.application.service.ScrapService;
import hgu.se.raonz.scrap.presentation.response.ScrapResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ScrapController {
    private final ScrapService scrapService;
    private final JWTProvider jwtProvider;

    @PostMapping("/scrape/add/{pid}")
    public ResponseEntity<Long> addScrap(@PathVariable Long pid, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long scrapId = scrapService.addScrap(pid, uid);

        return ResponseEntity.ok(scrapId);
    }

    @DeleteMapping("/scrape/delete/{pid}")
    public ResponseEntity<Long> deleteScrap(@PathVariable Long pid, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long returnId = scrapService.deleteScrap(pid, uid);

        return ResponseEntity.ok(returnId);
    }

    @GetMapping("/scrape/get")
    public ResponseEntity<List<ScrapResponse>> getAllScrap(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        List<ScrapDto> scrapDtoList = scrapService.getAllScrap(uid);
        List<ScrapResponse> scrapResponseList = scrapDtoList.stream().map(ScrapResponse::toResponse).toList();

        return ResponseEntity.ok(scrapResponseList);
    }


}