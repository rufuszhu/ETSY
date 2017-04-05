package com.rufus.etsy.util;

import com.rufus.etsy.data.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rzhu on 2017-04-04.
 */

public class TestDataFactory {

    public static List<Result> makeListResult(int count) {
        List<Result> resultList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            resultList.add(new Result());
        }
        return resultList;
    }
}
