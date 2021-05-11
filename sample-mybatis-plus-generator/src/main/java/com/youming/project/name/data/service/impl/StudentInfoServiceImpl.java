package com.youming.project.name.data.service.impl;

import com.youming.project.name.data.entity.StudentInfo;
import com.youming.project.name.data.mapper.StudentInfoMapper;
import com.youming.project.name.data.service.StudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生用户表 服务实现类
 * </p>
 *
 * @author author
 * 
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

}
