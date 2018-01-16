package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Role;
import com.aptech.itblog.collection.Trend;
import com.aptech.itblog.service.GAService;
import com.aptech.itblog.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aptech.itblog.common.CollectionLink.ROLES_ID;

@RestController
@RequestMapping("/api")
public class TrendController {
    @Autowired
    private GAService gaService;

    @Autowired
    private TrendService trendService;

    @GetMapping(value = "/trends")
    public void updateRole() throws GeneralSecurityException, IOException {
//        gaService.reportCurrentTime();

        List<Trend> trendPage = trendService.getTopTrend();

        trendPage.forEach(trend -> System.out.println(trend));
    }

}