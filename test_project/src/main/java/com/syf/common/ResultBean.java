package com.syf.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -2306973800384249300L;

    private int code;
    private String msg;
    private T data;

}
