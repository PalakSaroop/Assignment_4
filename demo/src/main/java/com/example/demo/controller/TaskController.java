package com.example.demo.controller;

import com.example.demo.entity.TaskEntity;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    // Show list of tasks and form
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", repo.findAll());
        model.addAttribute("taskForm", new TaskEntity());
        return "tasks";
    }

    // Create new task
    @PostMapping
    public String createTask(@ModelAttribute("taskForm") TaskEntity task) {
        repo.save(task);
        return "redirect:/tasks";
    }

    // TODO C: Load task for editing
    @GetMapping("/{id}/edit")
    public String editTask(@PathVariable("id") Long id, Model model) {

        TaskEntity task = repo.findById(id);

        model.addAttribute("taskForm", task);
        model.addAttribute("tasks", repo.findAll());

        return "tasks";
    }

    // TODO D: Update existing task
    @PostMapping("/{id}/update")
    public String updateTask(@PathVariable("id") Long id,
                             @ModelAttribute("taskForm") TaskEntity formTask) {

        TaskEntity existing = repo.findById(id);

        if (existing != null) {
            existing.setTitle(formTask.getTitle());
            existing.setDescription(formTask.getDescription());
            existing.setCompleted(formTask.isCompleted());

            repo.save(existing);
        }

        return "redirect:/tasks";
    }

    // TODO E: Delete task
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable("id") Long id) {

        repo.deleteById(id);

        return "redirect:/tasks";
    }
}