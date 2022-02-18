package com.xiangxue.base.mvvm.model;

public interface IBaseModelListener<T> {
    void onLoadSuccess(MvvmBaseModel model, T data, PagingResult... pageResult);

    void onLoadFail(MvvmBaseModel model, String prompt, PagingResult... pageResult);
}