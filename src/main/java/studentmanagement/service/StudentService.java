package studentmanagement.service;

import java.util.List;

import studentmanagement.dto.StudentRequestDTO;
import studentmanagement.dto.StudentResponseDTO;

public interface StudentService {

	public int insertStudent(StudentRequestDTO dto);
	public int updateStudent(StudentRequestDTO dto);
	public int deleteStudent(StudentRequestDTO dto);
	public List<StudentResponseDTO> selectStudent();
	public List<StudentResponseDTO> selectOne(StudentRequestDTO dto);
}
