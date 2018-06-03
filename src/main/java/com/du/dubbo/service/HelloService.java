package com.du.dubbo.service;

import io.swagger.annotations.ApiOperation;

public interface HelloService {

    @ApiOperation(value = "获取用户", notes = "通过id取用户信息", response = String.class, httpMethod="GET")
    String sayHello(String name);
}
