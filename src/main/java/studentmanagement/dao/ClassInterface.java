package studentmanagement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import studentmanagement.dto.ClassRequestDTO;
import studentmanagement.dto.ClassResponseDTO;

public interface ClassInterface {

	String insert = "INSERT INTO class (id, name) VALUES (#{id}, #{name})";
	String select = "SELECT * FROM class";
	String selectOne = "SELECT * FROM class WHERE id=#{id}";
	
	@Insert(insert)
	public int insertClass(ClassRequestDTO dto);
	
	@Select(select)
	public List<ClassResponseDTO> selectClass();
	
	@Select(selectOne)
	public List<ClassResponseDTO> selectOne(ClassRequestDTO dto);
}
