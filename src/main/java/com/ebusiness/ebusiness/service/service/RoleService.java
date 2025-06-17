package com.ebusiness.ebusiness.service.service;


import com.ebusiness.ebusiness.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getAllRoles();

    Optional<Role> getRoleById(Integer id);

    Optional<Role> getRoleByName(String roleName);

    Role createRole(Role role);

    Role updateRole(Integer id, Role updatedRole);

    void deleteRole(Integer id);
}
