package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UsersController {


	private final UserService userService;

	@Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }


	@GetMapping(value = "/")
	public String getAllUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "users";
	}

	@GetMapping(value = "/new")
	public String createUserForm(Model model) {
		model.addAttribute("user", new User());
		return "addUser";
	}

	@PostMapping
	public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "addUser";
		}
		userService.createUser(user);
		return "redirect:/";
	}

	@PostMapping("/delete")
	public String deleteUser(@RequestParam("id") long id) {
		userService.deleteUser(id);
		return "redirect:/";
	}

	@GetMapping(value = "/edit")
	public String editUserForm(@RequestParam long id, Model model) {
		User user = userService.readUser(id);
		model.addAttribute("user", user);
		return "editUser";
    }

	@PostMapping("/edit")
	public String editUser(@ModelAttribute("user") @Valid User user, @RequestParam long id) {
		user.setId(id);
		userService.updateUser(user);
		return "redirect:/";
	}


}