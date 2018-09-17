package com.ljb.utils;

import com.ljb.entity.Article;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longjinbin on 2018/7/21.
 */

public class ArticleServiceImpl implements ArticleService {


    @Override
    public List<Article> articlelist(Integer pageNum) {
        List<Article> list = getData(pageNum);
        return list;
    }
    public List<Article> getData(Integer pageNum){
        List<Article> list = new ArrayList<>();
        JSONObject jsonObject=null;

        return list;
    }

}
