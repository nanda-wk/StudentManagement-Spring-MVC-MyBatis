package studentmanagement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import studentmanagement.dto.StudentRequestDTO;
import studentmanagement.dto.StudentResponseDTO;

public interface StudentInterface {

	String insert = "INSERT INTO student (student_id, student_name, class_name, register_date, status) VALUES (#{studentId}, #{studentName}, #{className}, #{registerDate}, #{status})";

	String update = "UPDATE student set student_name=#{studentName}, class_name=#{className}, register_date=#{registerDate}, status=#{status} WHERE student_id=#{studentId}";

	String delete = "DELETE FROM student WHERE student_id=#{studentId}";

	String selectAll = "SELECT * FROM student";

	String selectFilter = "SELECT * FROM student WHERE student_id=#{studentId} OR student_name=#{studentName} OR class_name=#{className}";

	@Insert(insert)
	public int insertStudent(StudentRequestDTO dto);

	@Update(update)
	public int updateStudent(StudentRequestDTO dto);

	@Delete(delete)
	public int deleteStudent(StudentRequestDTO dto);

	@Select(selectAll)
	@Results(value = { @Result(property = "studentId", column = "student_id"),
			@Result(property = "studentName", column = "student_name"),
			@Result(property = "className", column = "class_name"),
			@Result(property = "registerDate", column = "register_date"),
			@Result(property = "status", column = "status"), })
	public List<StudentResponseDTO> selectStudent();

	@Select(selectFilter)
	@Results(value = { @Result(property = "studentId", column = "student_id"),
			@Result(property = "studentName", column = "student_name"),
			@Result(property = "className", column = "class_name"),
			@Result(property = "registerDate", column = "register_date"),
			@Result(property = "status", column = "status"), })
	public List<StudentResponseDTO> selectOne(StudentRequestDTO dto);
}
