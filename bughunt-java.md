# Java Bug Hunt

To help you learn how to utilize Source++, we've prepared a few manually introduced bugs.
These scenarios will make sure you understand how to create and use both live breakpoints and live logs for debugging live applications.

> Note: The goals of the bug scenarios are to find bugs, not solve them.

# Bug scenarios

## Level: Beginner

### Bug #1

**The bug:** <kbd>Clear completed</kbd> button does not work. When clicked - completed todos are not cleared.

#### Debug

1. Open the file `src/main/java/spp/tutorial/TodoController.java`

![TodoController.java](img/screenshots/code_location.png)

2. Navigate to the `clearCompleted` function

![Clear Completed](./img/screenshots/clear_completed_code.png)

3. Place the keyboard cursor at line 80 and bring up the live control bar by typing the keyboard shortcut <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>S</kbd>

![](./img/screenshots/line_80_control_bar.png)

4. Type the `breakpoint` command and press enter

![](./img/screenshots/line_80_breakpoint_command.png)

5. Press the enter key twice to create the live breakpoint with no condition and the default hit limit

![](./img/screenshots/line_80_breakpoint.png)

6. Repeat the previous three steps to create a live breakpoint on line 83

![](./img/screenshots/line_83_breakpoint.png)

7. Add a todo and mark this todo completed using the checkbox on the left of the task

8. Click the <kbd>Clear completed</kbd> button in the bottom right corner

9. Navigate back to the `clearCompleted` function and notice one of the live breakpoints is marked "Complete"

![](./img/screenshots/line_83_breakpoint_hit.png)

10. View the live breakpoint hits by expanding the breakpoint bar

![](./img/screenshots/line_83_breakpoint_json.png)

11. Click the live breakpoint hit to view collected live variables

![](./img/screenshots/line_83_breakpoint_data.png)

12. Notice the completed todo erroneously visits the "not completed yet" logic

13. Investigate why a completed todo would visit the "not completed yet" logic opposed to the "completed" logic

### Bug #2

**The bug:** Completed todos are not automatically deleted after five minutes.

#### Debug

1. Open the file `src/main/java/spp/tutorial/TodoController.java`

![TodoController.java](img/screenshots/code_location.png)

2. Navigate to the `TodoController` constructor

![](./img/screenshots/constructor_method.png)

3. Place the keyboard cursor at line 31 and bring up the live control bar by typing the keyboard shortcut <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>S</kbd>

![](./img/screenshots/control_bar.png)

4. Type the `log` command and press enter

![](./img/screenshots/add_live_log.png)

5. Type "Deleting todos older than: $deleteDate" and press enter

![](./img/screenshots/live_log_message.png)

6. Notice the logged time and determine if it correlates to the desired time

![](./img/screenshots/live_logging.gif)

# Next steps

Head over to the [official documentation](https://docs.sourceplusplus.com/) to understand all the Source++ components.
