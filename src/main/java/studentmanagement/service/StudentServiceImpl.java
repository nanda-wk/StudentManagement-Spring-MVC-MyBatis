package studentmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import studentmanagement.dao.StudentInterface;
import studentmanagement.dto.StudentRequestDTO;
import studentmanagement.dto.StudentResponseDTO;

public class StudentServiceImpl implements StudentService {

	@Override
	public int insertStudent(StudentRequestDTO dto) {
		int i = 0;
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(StudentInterface.class);
			i = session.getMapper(StudentInterface.class).insertStudent(dto);
			session.commit();
		}
		return i;
	}

	@Override
	public int updateStudent(StudentRequestDTO dto) {
		int i = 0;
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(StudentInterface.class);
			i = session.getMapper(StudentInterface.class).updateStudent(dto);
			session.commit();
		}
		return i;
	}

	@Override
	public int deleteStudent(StudentRequestDTO dto) {
		int i = 0;
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(StudentInterface.class);
			i = session.getMapper(StudentInterface.class).deleteStudent(dto);
			session.commit();
		}
		return i;
	}

	@Override
	public List<StudentResponseDTO> selectStudent() {
		List<StudentResponseDTO> list = new ArrayList<StudentResponseDTO>();
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(StudentInterface.class);
			list = session.getMapper(StudentInterface.class).selectStudent();
		}
		return list;
	}

	@Override
	public List<StudentResponseDTO> selectOne(StudentRequestDTO dto) {
		List<StudentResponseDTO> list = new ArrayList<StudentResponseDTO>();
		try(SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()){
			session.getConfiguration().addMapper(StudentInterface.class);
			list = session.getMapper(StudentInterface.class).selectOne(dto);
		}
		return list;
	}
	

}
