package com.roman.web.controller.category;

import com.roman.service.dto.category.CreateCategoryDto;
import com.roman.service.dto.category.ShowCategoryDto;
import com.roman.service.dto.category.UpdateCategoryDto;
import com.roman.service.service_impl.category.CreateCategoryService;
import com.roman.service.service_impl.category.DeleteCategoryService;
import com.roman.service.service_impl.category.FindCategoryService;
import com.roman.service.service_impl.category.UpdateCategoryService;
import com.roman.web.controller.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CreateCategoryService createService;
    private final FindCategoryService findService;
    private final UpdateCategoryService updateService;
    private final DeleteCategoryService deleteService;

    @PostMapping
    public ResponseEntity<ShowCategoryDto> create(@RequestBody CreateCategoryDto dto){
        ShowCategoryDto showCategoryDto = createService.create(dto);
        return new ResponseEntity<>(showCategoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowCategoryDto> findById(@PathVariable("id") String id){
        ShowCategoryDto dto = findService.findById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ShowCategoryDto>> findAll(){
        List<ShowCategoryDto> categories = findService.findAll();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ShowCategoryDto> update(@RequestBody UpdateCategoryDto dto){
        ShowCategoryDto updatedCategory = updateService.update(dto);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable("id") String id){
        deleteService.delete(id);
        String message = "Категория с id %s удалена.".formatted(id);
        return new ResponseEntity<>(new Response(message),HttpStatus.NO_CONTENT);
    }
}
