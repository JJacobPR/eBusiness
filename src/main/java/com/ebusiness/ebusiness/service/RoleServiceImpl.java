package com.ebusiness.ebusiness.service;

import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Integer id, Role updatedRole) {
        return roleRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedRole.getName());
                    // update other fields if any
                    return roleRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Role not found with id " + id));
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}
