package com.laptech.restapi.controller;

import com.laptech.restapi.dto.request.CategoryDTO;
import com.laptech.restapi.dto.response.BaseResponse;
import com.laptech.restapi.dto.response.DataResponse;
import com.laptech.restapi.model.Category;
import com.laptech.restapi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nhat Phi
 * @since 2022-11-24
 */
@Api(description = "Type of Product", tags = "Category controller")
@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Get all categories in system", response = Category.class)
    @GetMapping("")
    public ResponseEntity<DataResponse> getAllCategories(@RequestParam(required = false) String sortBy,
                                                         @RequestParam(required = false) String sortDir,
                                                         @RequestParam(required = false) Long page,
                                                         @RequestParam(required = false) Long size) {
        return DataResponse.getCollectionSuccess(
                "Get all categories",
                categoryService.count(),
                categoryService.findAll(sortBy, sortDir, page, size)
        );
    }

    @ApiOperation(value = "Get categories with filter", response = Category.class)
    @GetMapping("filter")
    public ResponseEntity<DataResponse> getCategoryWithFilter(@RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String image,
                                                              @RequestParam(required = false) String description,
                                                              @RequestParam(required = false) String createdDate,
                                                              @RequestParam(required = false) String modifiedDate,
                                                              @RequestParam(required = false) String deletedDate,
                                                              @RequestParam(required = false) Boolean isDel,
                                                              @RequestParam(required = false) String updateBy,
                                                              @RequestParam(required = false) String sortBy,
                                                              @RequestParam(required = false) String sortDir) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("image", image);
        params.put("description", description);
        params.put("createdDate", createdDate);
        params.put("modifiedDate", modifiedDate);
        params.put("deletedDate", deletedDate);
        params.put("isDel", isDel);
        params.put("updateBy", updateBy);
        params.put("sortBy", sortBy);
        params.put("sortDir", sortDir);
        return DataResponse.getCollectionSuccess(
                "Get all categories",
                categoryService.findWithFilter(params)
        );
    }

    @ApiOperation(value = "Get one category in system", response = Category.class)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> getCategoryById(@PathVariable("id") long categoryId) {
        return DataResponse.getObjectSuccess(
                "Get category",
                categoryService.findById(categoryId)
        );
    }

    @ApiOperation(value = "Create a new category", response = DataResponse.class)
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<DataResponse> createNewCategory(@Valid @RequestBody CategoryDTO dto) {
        return DataResponse.success(
                "Create new category",
                categoryService.insert(CategoryDTO.transform(dto))
        );
    }

    @ApiOperation(value = "Update a category", response = BaseResponse.class)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> updateCategory(@PathVariable("id") long categoryId,
                                                       @Valid @RequestBody CategoryDTO dto) {
        categoryService.update(CategoryDTO.transform(dto), categoryId);
        return DataResponse.success("Update category");
    }

    @ApiOperation(value = "Remove category", response = BaseResponse.class)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable("id") long categoryId,
                                                       @RequestBody(required = false) Map<String, String> body) {
        categoryService.delete(categoryId, (body != null) ? body.get("updateBy") : null);
        return DataResponse.success("Delete category");
    }
}
