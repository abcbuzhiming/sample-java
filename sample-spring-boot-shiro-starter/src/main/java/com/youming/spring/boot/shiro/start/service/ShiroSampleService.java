package com.youming.spring.boot.shiro.start.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

/**
 * @author youming
 */
@Service
public class ShiroSampleService {

    @RequiresPermissions("read")
    public String read() {
        return "reading...";
    }

    @RequiresPermissions("write")
    public String write() {
        return "writting...";
    }
}
