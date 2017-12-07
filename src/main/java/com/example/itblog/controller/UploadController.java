package com.example.itblog.controller;

import com.example.itblog.service.StorageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    @Autowired
    StorageService storageService;

    List<String> files = new ArrayList<>();

    @PostMapping("/uploads/new")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = storageService.genarateFilename(file);
        try {
            storageService.store(file, filename);
            files.add(file.getOriginalFilename());

            Map<String, String> response = new HashMap() {
                {
                    put("url", "http://localhost:9292/uploads/images/" + filename);
                }
            };
            return new ResponseEntity(response, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//	@GetMapping("/getallfiles")
//	public ResponseEntity<List<String>> getListFiles(Model model) {
//		List<String> fileNames = files
//				.stream().map(fileName -> MvcUriComponentsBuilder
//						.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
//				.collect(Collectors.toList());
//
//		return ResponseEntity.ok().body(fileNames);
//	}

    @GetMapping("/uploads/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
