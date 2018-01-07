package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Category;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value = CATEGORIES)
    public ResponseEntity<?> getCategoryList(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "25") Integer size) {
        // Create pageable
        Pageable pageable = new PageRequest(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        // Init a headers and add Content-Range
        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(categoryPage.getTotalElements()));
            }
        };

        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("data", categoryPage.getContent());
                            }
                        }), headers, HttpStatus.OK);

    }

    @PostMapping(value = CATEGORIES)
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        // Set created date
        category.setCreateAt(new Date());
        // Save to DB
        categoryRepository.save(category);
        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully created a new category");
                                put("data", category);
                            }
                        }), HttpStatus.OK);

    }

    @PutMapping(value = CATEGORIES_ID)
    public ResponseEntity<?> updateCategory(@PathVariable(value = "id") String categoryId,
                                     @RequestBody Category category) {
        Category currentCategory = categoryRepository.findOne(categoryId);
        // Set update properties
        currentCategory.setName(category.getName());
        currentCategory.setModifiedAt(new Date());
        // Save to DB
        categoryRepository.save(currentCategory);

        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully updated specified category");
                                put("data", category);
                            }
                        }), HttpStatus.OK);

    }

    @GetMapping(value = CATEGORIES_ID)
    public ResponseEntity<?> getCategory(@PathVariable(value = "id") String categoryId) {
        Category category = categoryRepository.findOne(categoryId);
        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully fetched specified category");
                                put("data", category);
                            }
                        }), HttpStatus.OK);

    }

    @DeleteMapping(value = CATEGORIES_ID)
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") String categoryId) {
        categoryRepository.delete(categoryId);
        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("message", "Successfully deleted specified category");
                                put("data", categoryId);
                            }
                        }), HttpStatus.OK);

    }

}