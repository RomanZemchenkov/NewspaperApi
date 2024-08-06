package com.roman.web.controller.news;

import com.roman.service.dto.news.CreateNewsDto;
import com.roman.service.dto.news.ShowNewsDto;
import com.roman.service.dto.news.UpdateNewsDto;
import com.roman.service.service_impl.news.CreateNewsService;
import com.roman.service.service_impl.news.DeleteNewsService;
import com.roman.service.service_impl.news.FindNewsService;
import com.roman.service.service_impl.news.UpdateNewsService;
import com.roman.web.controller.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final CreateNewsService createService;
    private final FindNewsService findService;
    private final UpdateNewsService updateService;
    private final DeleteNewsService deleteService;

    @PostMapping
    public ResponseEntity<ShowNewsDto> create(@RequestBody CreateNewsDto dto){
        ShowNewsDto createdNews = createService.create(dto);
        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ShowNewsDto> findById(@PathVariable("id") String id){
        ShowNewsDto news = findService.findById(id);
        return new ResponseEntity<>(news,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ShowNewsDto>> findAll(){
        List<ShowNewsDto> news = findService.findAll();
        return new ResponseEntity<>(news,HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ShowNewsDto>> findAllByCategoryId(@PathVariable("id") String id){
        List<ShowNewsDto> news = findService.finaAllByCategory(id);
        return new ResponseEntity<>(news,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ShowNewsDto> update(@RequestBody UpdateNewsDto dto){
        ShowNewsDto updatedNews = updateService.update(dto);
        return new ResponseEntity<>(updatedNews,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") String id){
        deleteService.delete(id);
        String message = "Новость с id %s удалена.";
        return new ResponseEntity<>(new Response(message.formatted(id)), HttpStatus.NO_CONTENT);
    }

}
