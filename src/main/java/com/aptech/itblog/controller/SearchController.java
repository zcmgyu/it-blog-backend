package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.LinkedHashMap;

import static com.aptech.itblog.common.CollectionLink.API;
import static com.aptech.itblog.common.CollectionLink.POSTS;
import static com.aptech.itblog.common.CollectionLink.SEARCH;

@RestController
@RequestMapping(value = API)
public class SearchController {
    @Autowired
    SearchService searchService;

    @PostMapping(value = SEARCH, headers = "Accept=application/json")
    public ResponseEntity<?> search(@RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "25") Integer size,
                                    @RequestParam String search) {

        Pageable pageable = new PageRequest(page, size);

        LinkedHashMap<String, ?> searchMap = searchService.search(search, pageable);

        HttpHeaders headers = getHeaders(((Page) searchMap.get("posts")));

        return new ResponseEntity<>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", searchMap);
            }
        }), headers, HttpStatus.OK);
    }

    private HttpHeaders getHeaders(Page<?> page) {
        return new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(page.getTotalElements()));
            }
        };
    }
}
