package studentmanagement.service;

import java.util.List;

import studentmanagement.dto.ClassRequestDTO;
import studentmanagement.dto.ClassResponseDTO;

public interface ClassService {

	public int insertClass(ClassRequestDTO dto);
	public List<ClassResponseDTO> selectClass();
	public List<ClassResponseDTO> selectOne(ClassRequestDTO dto);
}
