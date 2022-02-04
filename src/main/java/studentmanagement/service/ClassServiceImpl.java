package studentmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import studentmanagement.dao.ClassInterface;
import studentmanagement.dto.ClassRequestDTO;
import studentmanagement.dto.ClassResponseDTO;

public class ClassServiceImpl implements ClassService {

	@Override
	public int insertClass(ClassRequestDTO dto) {
		int i = 0;
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(ClassInterface.class);
			i = session.getMapper(ClassInterface.class).insertClass(dto);
			session.commit();
		}
		return i;
	}

	@Override
	public List<ClassResponseDTO> selectClass() {
		List<ClassResponseDTO> list = new ArrayList<ClassResponseDTO>();
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(ClassInterface.class);
			list = session.getMapper(ClassInterface.class).selectClass();
		}
		return list;
	}

	@Override
	public List<ClassResponseDTO> selectOne(ClassRequestDTO dto) {
		List<ClassResponseDTO> list = new ArrayList<ClassResponseDTO>();
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(ClassInterface.class);
			list = session.getMapper(ClassInterface.class).selectOne(dto);
		}
		return list;
	}

}
