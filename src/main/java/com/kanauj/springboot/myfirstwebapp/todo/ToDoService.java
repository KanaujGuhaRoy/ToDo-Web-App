package com.kanauj.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class ToDoService {
	
	private static List<Todo> todos = new ArrayList<>();
	private static int toDoCount=0;
	
	static {
		todos.add(new Todo(++toDoCount, "kanauj", "Learn Spring", 
				LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++toDoCount, "kanauj", "Learn DevOps", 
				LocalDate.now().plusYears(2), false));
		todos.add(new Todo(++toDoCount, "kanauj", "Learn Full Stack", 
				LocalDate.now().plusYears(3), false));
	}
	
	public List<Todo> findByUsername(String username)
	{
		Predicate<? super Todo> predicate = 
				todo -> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}
	
	public void addToDo(String username, String description, LocalDate targetDate, boolean done)
	{
		todos.add(new Todo(++toDoCount, username, description, targetDate, false));
	}
	
	public void deleteByID(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId()==id;
		todos.removeIf(predicate);
	}

	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId()==id;
		Todo toDo = todos.stream().filter(predicate).findFirst().get();
		return toDo;
	}

	public void updateToDo(@Valid Todo todo) {
		deleteByID(todo.getId());
		todos.add(todo);
		
	}

}
