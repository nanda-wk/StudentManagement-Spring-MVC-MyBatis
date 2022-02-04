package studentmanagement.service;

import java.util.List;

import studentmanagement.dto.UserRequestDTO;
import studentmanagement.dto.UserResponseDTO;

public interface UserService {

	public int insertUser(UserRequestDTO dto);
	public int updateUser(UserRequestDTO dto);
	public int deleteUser(UserRequestDTO dto);
	public List<UserResponseDTO> selectUser();
	public List<UserResponseDTO> selectOne(UserRequestDTO dto);
}
