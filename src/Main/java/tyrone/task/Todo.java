package tyrone.task;

/**
 * Represents a simple todo task in the Tyrone task management system.
 *
 * <p>A Todo is the most basic type of task with only a description and completion
 * status. Unlike {@link Deadline} and {@link Event}, it has no associated date or time.</p>
 *
 * <p>Example usage:
 * <pre>
 * Todo task = new Todo("Buy groceries");
 * System.out.println(task);  // Output: [T][ ] Buy groceries
 * task.mark();
 * System.out.println(task);  // Output: [T][X] Buy groceries
 * </pre>
 * </p>
 *
 * @author Toh Yi Sheng
 * @version 1.0
 * @since 1.0
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task with the specified description.
     *
     * <p>The task is initialized with {@code isDone} set to {@code false},
     * indicating that the task is not yet completed.</p>
     *
     * @param description A description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the type of this task.
     *
     * <p>Overrides the {@link Task#getType()} method to identify this as a Todo task.</p>
     *
     * @return The string "Todo" representing this task type
     */
    public String getType(){
        return "Todo";
    }
    /**
     * Returns a string representation of this todo task.
     *
     * <p>The format is: {@code [T][<status>] <description>}
     * where {@code <status>} is either "X" (done) or " " (not done).</p>
     *
     * <p>Examples:
     * <ul>
     *     <li>{@code [T][ ] Buy groceries} - Not completed</li>
     *     <li>{@code [T][X] Buy groceries} - Completed</li>
     * </ul>
     * </p>
     *
     * @return A formatted string representation of the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
