package com.zoom.security.mapper;

import com.zoom.security.model.Permission;
import com.zoom.security.model.Person;
import com.zoom.security.model.Resource;
import com.zoom.security.model.Role;
import com.zoom.tools.mybatis.SqlUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Mapper
public interface AuthMapper {

    @Results(id = "modelResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "is_valid", property = "valid"),
            @Result(column = "is_delete", property = "delete"),
            @Result(column = "id", property = "roles",
                    many = @Many(select = "com.zoom.security.mapper.AuthMapper.findRoleByPersonId")),
    })
    @SelectProvider(type = AuthMapperProvider.class, method = "findPersonByUsername")
    Person findPersonByUsername(@Param("code") String username);

    @SelectProvider(type = AuthMapperProvider.class, method = "findRoleByPersonId")
    Set<Role> findRoleByPersonId(@Param("personId") Long id);

    @SelectProvider(type = AuthMapperProvider.class, method = "findPermissionByPersonId")
    Set<Permission> findPermissionByPersonId(@Param("roleIds") Set<Role> roles);

    @SelectProvider(type = AuthMapperProvider.class, method = "findResourceByPersonId")
    Set<Resource> findResourceByPersonId(@Param("permissionIds") Set<Permission> permissions);

    class AuthMapperProvider {

        public String findPersonByUsername() {
            return new SQL() {{
                SELECT("*");
                FROM("cm_person");
                WHERE(SqlUtil.enabled(null),
                        SqlUtil.eq(null, "code"));
                LIMIT(1);
            }}.toString();
        }

        public String findRoleByPersonId() {
            return new SQL() {{
                SELECT("r.*");
                FROM("sm_role r", "sr_person_role pr");
                WHERE("r.id = pr.role_id", SqlUtil.enabled("r"),
                        SqlUtil.eq("pr", "person_id"));
            }}.toString();
        }

        public String findPermissionByPersonId() {
            String sql = "select p.* from sm_permission p, sr_role_permission rp where p.id = rp.permission_id ";
            sql += SqlUtil.andEnabled("p");
            sql += SqlUtil.andIn("rp", "role_id");
            return SqlUtil.script(sql);
        }

        public String findResourceByPersonId() {
            String sql = "select r.* from sm_resource r, sr_permission_resource pr where r.id = pr.resource_id ";
            sql += SqlUtil.andEnabled("r");
            sql += SqlUtil.andIn("pr", "permission_id");
            return SqlUtil.script(sql);
        }
    }
}
