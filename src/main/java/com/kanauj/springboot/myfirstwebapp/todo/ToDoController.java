package com.kanauj.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("name")
public class ToDoController {
	
	private ToDoService toDoService;
	
	public ToDoController(ToDoService toDoService) {
		super();
		this.toDoService = toDoService;
	}

	@RequestMapping(value="list-todos")
	public String listAllToDos(ModelMap model)
	{
		String username = getLoggedInUsername(model);
		List<Todo> todos = toDoService.findByUsername(username);
		model.addAttribute("todos", todos);
		return "listToDos";
	}

	@RequestMapping(value="delete-todo")
	public String deleteToDo(@RequestParam int id)
	{
		toDoService.deleteByID(id);
		return "redirect:list-todos";
	}

	@RequestMapping(value="add-todo", method = RequestMethod.GET)
	public String showNewToDoPage(ModelMap model)
	{
		String username = getLoggedInUsername(model);
		Todo todo = new Todo(0, username, "", 
				LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value="add-todo", method = RequestMethod.POST)
	public String addNewToDoPage(ModelMap model, @Valid @ModelAttribute("todo") Todo todo, BindingResult result)
	{
		if(result.hasErrors())
			return "todo";
		String username = getLoggedInUsername(model);
		toDoService.addToDo(username, todo.getDescription(), 
				todo.getTargetDate(), false);
		return "redirect:list-todos";
	}

	@RequestMapping(value="update-todo", method = RequestMethod.GET)
	public String showUpdateToDoPage(@RequestParam int id, ModelMap model)
	{
		Todo todo=toDoService.findById(id);
		model.addAttribute("todo", todo);
		return "todo";
	}

	@RequestMapping(value="update-todo", method = RequestMethod.POST)
	public String updateToDoPage(ModelMap model, @Valid @ModelAttribute("todo") Todo todo, BindingResult result)
	{
		if(result.hasErrors())
			return "todo";
		String username = getLoggedInUsername(model);
		todo.setUsername(username);
		toDoService.updateToDo(todo);
		return "redirect:list-todos";
	}

	private String getLoggedInUsername(ModelMap model) {
		//return (String)model.get("name");
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}
