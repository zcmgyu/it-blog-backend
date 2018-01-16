package com.aptech.itblog.service;

import com.aptech.itblog.collection.Trend;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TrendService {
    List<Trend> getTopTrend();
}
