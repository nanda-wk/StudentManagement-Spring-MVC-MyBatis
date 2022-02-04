package studentmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import studentmanagement.dto.UserRequestDTO;
import studentmanagement.dto.UserResponseDTO;
import studentmanagement.model.LoginBean;
import studentmanagement.model.SearchBean;
import studentmanagement.model.UserBean;
import studentmanagement.service.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl dao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {

		return new ModelAndView("LGN001", "bean", new LoginBean());
	}

	@RequestMapping(value = "/home")
	public String home() {

		return "M00001";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("bean") @Validated LoginBean loginBean, BindingResult br, ModelMap model,
			HttpSession session) {

		if (br.hasErrors()) {
			return "LGN001";
		}
		UserRequestDTO dto = new UserRequestDTO();
		dto.setId(loginBean.getId());
		List<UserResponseDTO> list = dao.selectOne(dto);
		if (list.size() == 0) {
			model.addAttribute("error", "UserID not found");
			return "LGN001";
		} else {
			if (list.get(0).getPassword().equals(loginBean.getPassword())) {
				session.setAttribute("userId", list.get(0).getId());
				session.setAttribute("userName", list.get(0).getName());
				return "M00001";
			} else {
				model.addAttribute("error", "Password Incorrect");
				return "LGN001";
			}
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/usermanagement", method = RequestMethod.GET)
	public ModelAndView userManagement(@ModelAttribute("success") String success, ModelMap model) {
		model.addAttribute("success", success);
		return new ModelAndView("USR001", "usBean", new SearchBean());
	}

	@RequestMapping(value = "/searchUser", method = RequestMethod.GET)
	public String searchUser(@ModelAttribute("usBean") SearchBean bean, ModelMap model) {
		UserRequestDTO dto = new UserRequestDTO();
		List<UserResponseDTO> list = new ArrayList<UserResponseDTO>();
		dto.setId(bean.getUserId());
		dto.setName(bean.getUserName());
		
		if(!dto.getId().equals("") || !dto.getName().equals("")) {
			list = dao.selectOne(dto);
		} else {
			list = dao.selectUser();
		}
		
		
		if (list.size() == 0) {
			model.addAttribute("error", "No user Found!");
		}
		model.addAttribute("userList", list);
		model.addAttribute("success", "Search done");
		return "USR001";
	}

	@RequestMapping(value = "/userRegister", method = RequestMethod.GET)
	public ModelAndView userRegister(@ModelAttribute("success") String success, ModelMap model) {
		model.addAttribute("success", success);
		return new ModelAndView("USR002", "urbean", new UserBean());
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("urbean") @Validated UserBean bean, BindingResult br, ModelMap model, RedirectAttributes ra) {
		if (br.hasErrors()) {
			return "USR002";
		}
		UserRequestDTO dto = new UserRequestDTO();
		if (bean.getPassword().equals(bean.getConPwd())) {
			dto.setId(bean.getId());
			
			List<UserResponseDTO> list = dao.selectOne(dto);
			if (list.size() != 0) {
				model.addAttribute("error", "UserID already exist!");
				return "USR002";

			} else {
				dto.setName(bean.getName());
				dto.setPassword(bean.getPassword());
				int i = dao.insertUser(dto);
				if (i > 0) {
					ra.addAttribute("success", "User successfuly registered");
					return "redirect:/userRegister";
				} else {
					model.addAttribute("error", "User register fail!");
					return "USR002";
				}
			}
		} else {
			model.addAttribute("error", "Password not match!");
			return "USR002";
		}
	}

	@RequestMapping(value = "/updateUserModel/{id}", method = RequestMethod.GET)
	public ModelAndView updateUserModel(@PathVariable String id) {
		UserRequestDTO dto = new UserRequestDTO();
		dto.setId(id);
		List<UserResponseDTO> list = dao.selectOne(dto);
		UserBean urBean = new UserBean();
		for (UserResponseDTO res : list) {
			urBean.setId(res.getId());
			urBean.setName(res.getName());
			urBean.setPassword(res.getPassword());
			urBean.setConPwd(res.getPassword());
		}
		return new ModelAndView("USR002-01", "urbean", urBean);
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("urbean") @Validated UserBean bean, BindingResult br, ModelMap model) {
		if (br.hasErrors()) {
			return "USR002-01";
		}

		UserRequestDTO dto = new UserRequestDTO();
		if (bean.getPassword().equals(bean.getConPwd())) {
			dto.setId(bean.getId());
			dto.setName(bean.getName());
			dto.setPassword(bean.getPassword());
			int i = dao.updateUser(dto);
			if (i == 0) {
				model.addAttribute("error", "User update Fail");
				return "USR002-01";
			}
			model.addAttribute("success", "User successfully update");
		} else {
			model.addAttribute("error", "Password not match");
			return "USR002-01";
		}
		return "USR002-01";
	}

	@RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String id, ModelMap model, RedirectAttributes ra, HttpSession session) {
		UserRequestDTO dto = new UserRequestDTO();
		dto.setId(id);
		if (dto.getId().equals(session.getAttribute("userId"))) {
			ra.addAttribute("success", "Can't delet logined user!");
		} else {
			int i = dao.deleteUser(dto);
			if (i == 0) {
				model.addAttribute("error", "User Delete Fail!");
			}
			ra.addAttribute("success", "Delete successful");
		}
		return "redirect:/usermanagement";
	}
}
