package com.example.demo.server;

import com.example.demo.common.PublicThreadLocal;
import com.example.demo.entity.*;
import com.example.demo.mapper.testOne.MemberUserVoMapper;
import com.example.demo.mapper.testOne.TbMapper;
import com.example.demo.mapper.testTwo.TbtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liuhaibo
 * @Company: 优积谷
 * @Date: 2021/4/26 15:19
 * @Version: 1.0.0
 * @Description: 描述
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private Test test;


    @Autowired
    private MemberUserVoMapper memberUserVoMapper;

    @Autowired
    private TbMapper tbMapper;

    @Autowired
    private TbtMapper tbtMapper;

    @Override
    public void test() {
        PublicThreadLocal.set("siteId",1);
        List<Tb> tbs = tbMapper.selectAll();
        System.err.println("tb-------------"+tbs.toString());
        List<Tbt> tbts = tbtMapper.selectAll();
        System.err.println("tbt--------------"+tbts.toString());
    }

    @Override
    public void add(Table table) {
    }

    @Override
    public List<Table> queryAll() {
        return null;
    }

    @Override
    public void get() {
        MemberUserVo vo = new MemberUserVo();
        List<List<?>> l = memberUserVoMapper.selectPageByVoTotal(vo);
        System.err.println(l.toString());
    }
}
