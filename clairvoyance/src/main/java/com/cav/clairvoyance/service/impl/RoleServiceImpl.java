package com.cav.clairvoyance.service.impl;

import com.cav.clairvoyance.domain.Role;
import com.cav.clairvoyance.mapper.RoleMapper;
import com.cav.clairvoyance.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoleNames(String userId) {
        return null;
    }
}
