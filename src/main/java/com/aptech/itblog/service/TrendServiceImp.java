package com.aptech.itblog.service;

import com.aptech.itblog.collection.Trend;
import com.aptech.itblog.model.TrendViews;
import com.aptech.itblog.repository.TrendRepository;
import com.aptech.itblog.repository.TrendRepsitoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class TrendServiceImp implements TrendService {
    @Autowired
    TrendRepository trendRepository;

    @Autowired
    TrendRepsitoryCustom trendRepsitoryCustom;

    @Override
    public List<TrendViews> getTopTrend() {
        Calendar calendar = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date sevenDaysAgo = calendar.getTime();
//        List<Trend> trendsBefore = trendRepository.findAllByActiveDateBefore(sevenDaysAgo);
//        List<Trend> trendsAfter = trendRepository.findAllByActiveDateAfter(sevenDaysAgo);
        List<TrendViews> trendsCustome = trendRepsitoryCustom.getTopTrend();
//        return trendRepository.findAllByActiveDateAfter(sevenDaysAgo);
         return trendsCustome;
    }
}
