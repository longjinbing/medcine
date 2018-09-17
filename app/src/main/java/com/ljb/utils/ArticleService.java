package com.ljb.utils;

import com.ljb.entity.Article;

import java.util.List;

/**
 * Created by longjinbin on 2018/7/21.
 */

public interface ArticleService {
    List<Article> articlelist(Integer pageNum);
}
