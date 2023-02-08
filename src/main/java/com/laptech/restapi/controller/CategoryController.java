package com.laptech.restapi.controller;

import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Category;
import com.laptech.restapi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(tags = "Type of Product", value = "Category controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Get all categories in system", response = Category.class)
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false, defaultValue = "1") Long page,
                                                         @RequestParam(required = false) Long size) {
        return ResponseEntity.ok(categoryService.findAll(page, size));
    }

    @ApiOperation(value = "Get one category in system", response = Category.class)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long categoryId) {
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

    @ApiOperation(value = "Create a new category", response = DataResponse.class)
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewCategory(@RequestBody Category category) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DataResponse(
                        HttpStatus.CREATED,
                        "Create new category success!",
                        categoryService.insert(category)
                ));
    }

    @ApiOperation(value = "Update a category", response = BaseResponse.class)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateCategory(@PathVariable("id") long categoryId, @RequestBody Category category) {
        categoryService.update(category, categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse(
                        HttpStatus.OK,
                        "Update category success!",
                        null
                ));
    }

    @ApiOperation(value = "Remove category", response = BaseResponse.class)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable("id") long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataResponse(
                        HttpStatus.OK,
                        "Update category success!",
                        null
                ));
    }
}
