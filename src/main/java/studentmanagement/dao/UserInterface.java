package studentmanagement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import studentmanagement.dto.UserRequestDTO;
import studentmanagement.dto.UserResponseDTO;

public interface UserInterface {
	String insert = "INSERT INTO user (id, name, password) VALUES (#{id}, #{name}, #{password})";

	String update = "UPDATE user SET name=#{name}, password=#{password} WHERE id=#{id}";
	
	String delete = "DELETE FROM user WHERE id=#{id}";
	
	String selectAll = "SELECT * FROM user";
	
	String selectFilter = "SELECT * FROM user WHERE id=#{id} OR name=#{name}";
	
	@Select(selectAll)
	public List<UserResponseDTO> selectUser();
	
	@Select(selectFilter)
	public List<UserResponseDTO> selectOne(UserRequestDTO dto);
	
	@Delete(delete)
	public int deleteUser(UserRequestDTO dto);
	
	@Update(update)
	public int updateUser(UserRequestDTO dto);
	
	@Insert(insert)
	public int insertUser(UserRequestDTO dto);
}
