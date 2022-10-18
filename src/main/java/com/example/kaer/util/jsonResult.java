package com.example.kaer.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* Json格式的数据响应
* */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class jsonResult<E> implements Serializable {
//    状态码
    private Integer state;
//    描述信息
    private String message;
//    数据
    private E data;

    public jsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public jsonResult(Integer state) {
        this.state = state;
    }

    public jsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

}
