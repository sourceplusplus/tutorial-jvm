package spp.tutorial;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

  private final long FIVE_MINUTES_MILLIS = TimeUnit.MINUTES.toNanos(5);
  private final Map<String, Todo> mapTodos = new HashMap<>();

  public TodoController() {
    //delete completed todos created over 5 minutes ago
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(() -> {
      Instant deleteDate = Instant.ofEpochMilli(System.currentTimeMillis() - FIVE_MINUTES_MILLIS);
      mapTodos.values().removeIf(todo -> {
        if (todo.isCompleted() && todo.getCreateDate().isBefore(deleteDate)) {
          //old; let's remove
          return true;
        } else {
          //not old yet; ignore for now
          return false;
        }
      });
    }, 0, 1, TimeUnit.SECONDS);
  }

  @RequestMapping(method = GET)
  public HttpEntity<Collection<Todo>> listAll() {
    return new ResponseEntity<>(mapTodos.values(), HttpStatus.OK);
  }

  @RequestMapping(method = GET, value = "/{todo-id}")
  public HttpEntity<Todo> get(@PathVariable("todo-id") String id) {
    return respond(id);
  }

  @RequestMapping(method = POST, value = "/dup/{todo-id}")
  public HttpEntity<Todo> duplicateTodo(@PathVariable("todo-id") String id) {
    Todo existingTodo = mapTodos.get(id);
    Todo duplicateTodo = new Todo(
            UUID.randomUUID().toString(), existingTodo.getTitle(), existingTodo.isCompleted(), existingTodo.getOrder()
    );
    mapTodos.put(duplicateTodo.getId(), duplicateTodo);
    return new ResponseEntity<>(duplicateTodo, HttpStatus.OK);
  }

  @RequestMapping(method = POST)
  public HttpEntity<Todo> add(@RequestBody Todo todo) {
    todo.setId(UUID.randomUUID().toString());
    mapTodos.put(todo.getId(), todo);
    return new ResponseEntity<>(todo, HttpStatus.OK);
  }

  @RequestMapping(method = DELETE)
  public void deleteAll() {
    mapTodos.clear();
  }

  @RequestMapping(method = DELETE, value = "/clear_completed")
  public void clearCompleted() {
    mapTodos.values().removeIf(todo -> {
      if (todo.isCompleted() && todo.hasId()) {
        //completed; let's remove
        return true;
      } else {
        //not completed yet; ignore for now
        return false;
      }
    });
  }

  @RequestMapping(method = DELETE, value = "/{todo-id}")
  public void delete(@PathVariable("todo-id") String id) {
    mapTodos.remove(id);
  }

  @RequestMapping(method = PUT)
  public HttpEntity<Todo> update(@RequestBody Todo updatedTodo) {
    mapTodos.put(updatedTodo.getId(), updatedTodo);
    return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
  }

  private HttpEntity<Todo> respond(String todoId) {
    return Optional.ofNullable(mapTodos.get(todoId))
        .map(todo -> new ResponseEntity<>(todo, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
