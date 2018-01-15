package com.aptech.itblog.collection;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Trend {
    @Id
    private String id;

    private Date activedDate;

    private String title;

    private String path;

    private long views;
}
