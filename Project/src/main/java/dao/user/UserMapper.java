package dao.user;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
	@Select("SELECT role FROM users WHERE user_id = #{userId}")
    String getUserRoleById(@Param("userId") String userId);

    @Select("SELECT COUNT(*) FROM users WHERE user_id = #{userId} AND role IN ('admin', 'superadmin', 'manager')")
    Integer isAdminUser(@Param("userId") String userId);
}
