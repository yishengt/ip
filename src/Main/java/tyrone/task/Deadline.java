package tyrone.task;

/**
 * Represents a task with a deadline in the Tyrone task management system.
 *
 * <p>A Deadline is a type of task that must be completed by a specific date or time.
 * It extends the {@link Task} class and adds a deadline field to track when the
 * task is due.</p>
 *
 * <p>Example usage:
 * <pre>
 * Deadline homework = new Deadline("Submit assignment", "2024-03-15");
 * System.out.println(homework);  // Output: [D][ ] Submit assignment (by: 2024-03-15)
 * </pre>
 * </p>
 *
 * @author Toh Yi Sheng
 * @version 1.0
 * @since 1.0
 */
public class Deadline extends Task {

    /** The deadline by which this task must be completed. */
    private String by;


    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * <p>The task is initialized with {@code isDone} set to {@code false},
     * indicating that the task is not yet completed.</p>
     *
     * @param description A description of the task to be completed
     * @param by The deadline by which the task should be completed (e.g., "2024-03-15" or "next Monday")
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline of this task.
     *
     * @return The deadline string indicating when this task is due
     */
    public String getBy(){
        return this.by;
    }

    /**
     * Returns the type of this task.
     *
     * <p>Overrides the {@link Task#getType()} method to identify this as a Deadline task.</p>
     *
     * @return The string "Deadline" representing this task type
     */
    public String getType(){
        return "Deadline";
    }


    /**
     * Returns a string representation of this deadline task.
     *
     * <p>The format is: {@code [D][<status>] <description> (by: <deadline>)}
     * where {@code <status>} is either "X" (done) or " " (not done).</p>
     *
     * <p>Examples:
     * <ul>
     *     <li>{@code [D][ ] Submit report (by: 2024-03-15)} - Not completed</li>
     *     <li>{@code [D][X] Submit report (by: 2024-03-15)} - Completed</li>
     * </ul>
     * </p>
     *
     * @return A formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
