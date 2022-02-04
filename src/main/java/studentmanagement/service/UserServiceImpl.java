package studentmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import studentmanagement.dao.UserInterface;
import studentmanagement.dto.UserRequestDTO;
import studentmanagement.dto.UserResponseDTO;

public class UserServiceImpl implements UserService {

	@Override
	public int insertUser(UserRequestDTO dto) {
		int i = 0;
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(UserInterface.class);
			i = session.getMapper(UserInterface.class).insertUser(dto);
			session.commit();
		}
		return i;
	}

	@Override
	public int updateUser(UserRequestDTO dto) {
		int i = 0;
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(UserInterface.class);
			i = session.getMapper(UserInterface.class).updateUser(dto);
			session.commit();
		}
		return i;
	}

	@Override
	public int deleteUser(UserRequestDTO dto) {
		int i = 0;
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(UserInterface.class);
			i = session.getMapper(UserInterface.class).deleteUser(dto);
			session.commit();
		}
		return i;
	}

	@Override
	public List<UserResponseDTO> selectUser() {
		List<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(UserInterface.class);
			list = session.getMapper(UserInterface.class).selectUser();
		}
		return list;
	}

	@Override
	public List<UserResponseDTO> selectOne(UserRequestDTO dto) {
		List<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(UserInterface.class);
			list = session.getMapper(UserInterface.class).selectOne(dto);
		}
		return list;
	}

	
}
