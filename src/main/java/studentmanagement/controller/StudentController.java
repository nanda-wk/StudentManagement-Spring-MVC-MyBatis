package studentmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import studentmanagement.dto.ClassResponseDTO;
import studentmanagement.dto.StudentRequestDTO;
import studentmanagement.dto.StudentResponseDTO;
import studentmanagement.model.SearchBean;
import studentmanagement.model.StudentBean;
import studentmanagement.service.ClassServiceImpl;
import studentmanagement.service.StudentServiceImpl;

@Controller
public class StudentController {

	@Autowired
	private StudentServiceImpl dao;

	@Autowired
	private ClassServiceImpl cdao;

	@RequestMapping(value = "/studentsearch", method = RequestMethod.GET)
	public ModelAndView studentSearch(@ModelAttribute("success") String success, ModelMap model) {
		model.addAttribute("success", success);
		return new ModelAndView("BUD001", "ssBean", new SearchBean());
	}

	@RequestMapping(value = "/studentResult", method = RequestMethod.GET)
	public String studentResult(@ModelAttribute("ssBean") SearchBean bean, ModelMap model) {
		StudentRequestDTO dto = new StudentRequestDTO();
		List<StudentResponseDTO> list = new ArrayList<StudentResponseDTO>();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());

		if (!dto.getStudentId().equals("") || !dto.getStudentName().equals("") || !dto.getClassName().equals("")) {
			list = dao.selectOne(dto);
		} else {
			list = dao.selectStudent();
		}

		if (list.size() == 0) {
			model.addAttribute("error", "No Student found!");
		}
		model.addAttribute("stuList", list);
		return "BUD001";
	}

	@RequestMapping(value = "/studentRegisterModel", method = RequestMethod.GET)
	public ModelAndView studentRegisterModel(@ModelAttribute("success") String success, ModelMap model) {
		model.addAttribute("success", success);
		return new ModelAndView("BUD002", "stubean", new StudentBean());
	}

	@RequestMapping(value = "/studentRegister", method = RequestMethod.POST)
	public String studentRegister(@ModelAttribute("stubean") @Validated StudentBean bean, BindingResult br,
			ModelMap model, RedirectAttributes ra) {
		if (br.hasErrors() || bean.getYear().equals("Year") || bean.getMonth().equals("Month")
				|| bean.getDay().equals("Day")) {
			model.addAttribute("error", "Register date can't be blank!");
			return "BUD002-01";
		}

		String y = bean.getYear();
		String m = bean.getMonth();
		String d = bean.getDay();

		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentId(bean.getStudentId());
		List<StudentResponseDTO> list = dao.selectOne(dto);
		if (list.size() != 0) {
			model.addAttribute("error", "StudentID already exist!");
			return "BUD002";
		} else {
			dto.setStudentName(bean.getStudentName());
			dto.setClassName(bean.getClassName());
			dto.setRegisterDate(y + "-" + m + "-" + d);
			dto.setStatus(bean.getStatus());
			int i = dao.insertStudent(dto);
			if (i > 0) {
				ra.addAttribute("success", "Student successfully registered");
				return "redirect:/studentRegisterModel";
			} else {
				model.addAttribute("error", "Student register fail!");
				return "BUD002";
			}
		}
	}

	@RequestMapping(value = "/studentUpdateModel/{studentId}", method = RequestMethod.GET)
	public ModelAndView studentUpdateModel(@PathVariable String studentId, HttpServletRequest req) {
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentId(studentId);
		List<StudentResponseDTO> list = dao.selectOne(dto);
		StudentBean stuBean = new StudentBean();
		for (StudentResponseDTO res : list) {
			stuBean.setStudentId(res.getStudentId());
			stuBean.setStudentName(res.getStudentName());
			stuBean.setClassName(res.getClassName());
			stuBean.setStatus(res.getStatus());
			String[] dt = res.getRegisterDate().toString().split("-");

			stuBean.setYear(dt[0]);
			stuBean.setMonth(dt[1]);
			stuBean.setDay(dt[2]);
		}
		return new ModelAndView("BUD002-01", "stubean", stuBean);
	}

	@RequestMapping(value = "/studentUpdate", method = RequestMethod.POST)
	public String studentUpdate(@ModelAttribute("stubean") @Validated StudentBean bean, BindingResult br,
			ModelMap model) {
//		
		if (br.hasErrors() || bean.getYear().equals("Year") || bean.getMonth().equals("Month")
				|| bean.getDay().equals("Day")) {
			model.addAttribute("error", "Register date can't be blank!");
			return "BUD002-01";
		}

		String y = bean.getYear();
		String m = bean.getMonth();
		String d = bean.getDay();

		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		dto.setRegisterDate(y + "-" + m + "-" + d);
		dto.setStatus(bean.getStatus());
		int i = dao.updateStudent(dto);
		if (i == 0) {
			model.addAttribute("error", "Student update Fail");
			return "BUD002-01";
		}
		model.addAttribute("success", "Student successfully updated");
		return "BUD002-01";
	}

	@RequestMapping(value = "/studentDelete/{studentId}", method = RequestMethod.GET)
	public String studentDelete(@PathVariable String studentId, ModelMap model, RedirectAttributes ra) {
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentId(studentId);
		int i = dao.deleteStudent(dto);
		if (i > 0) {
			ra.addAttribute("success", "Delete successful");
		} else {
			ra.addAttribute("error", "Delete Fail!");
		}
		return "redirect:/studentsearch";
	}

	@ModelAttribute("classList")
	public List<ClassResponseDTO> classList() {

//		List<ClassResponseDTO> clist = cdao.selectClass();
		return cdao.selectClass();
	}
}
