package tyrone.task;

/**
 * Represents a task with a description and completion status.
 * Serves as the base class for Todo, Deadline, and Event tasks.
 */
public class Task {
    /** The description of the task. */
    protected String description;

    /** Whether the task is completed. */
    protected boolean isDone;

    /**
     * Creates a new incomplete task with the given description.
     *
     * @param description The task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the type of this task.
     * Subclasses should override this to return their specific type.
     *
     * @return "Task" for the base class
     */
    public String getType(){
        return "Task";
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmark() {
        isDone = false;
    }

    /**
     * Checks if this task is completed.
     *
     * @return true if completed, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Gets the status icon for this task.
     *
     * @return "X" if done, " " if not done
     */
    protected String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns string representation in format: [status] description
     *
     * @return Formatted task string
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}