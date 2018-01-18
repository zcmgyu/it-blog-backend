//package com.aptech.itblog.controller;
//
//import com.aptech.itblog.collection.Trend;
//import com.aptech.itblog.model.TrendViews;
//import com.aptech.itblog.service.GAService;
//import com.aptech.itblog.service.TrendService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class TrendController {
//    @Autowired
//    private GAService gaService;
//
//    @Autowired
//    private TrendService trendService;
//
//    @GetMapping(value = "/trends")
//    public void get() throws GeneralSecurityException, IOException {
////        gaService.reportCurrentTime();
//
//        List<TrendViews> trendPage = trendService.getTopTrend();
//
//
//
//        trendPage.forEach(trend -> System.out.println(trend));
//    }
//
//}