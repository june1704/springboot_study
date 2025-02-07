package com.korit.springboot_study.util.db;

import com.korit.springboot_study.entity.Role;
import com.korit.springboot_study.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
// 서버 실행될 때 자동 실행
@Component
public class AutoCreateDML implements CommandLineRunner {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void run(String... args) throws Exception {
//        insertRole(); // 한 번 실행 후에는 주석처리
    }
    // 최초의 한 번 insert가 실행됨
    private void insertRole() {
        List<String> roleNames = List.of("USER", "ADMIN", "MANAGER");
        roleMapper.insertAll(roleNames
                .stream()
                .map(name -> Role.builder().roleName("ROLE_" + name).build())
                .collect(Collectors.toList()));
    }
}
