package tyrone.task;

public class Event extends Task {

    /**
     * Represents an event task that occurs at a specific time or location.
     * An event is a type of task that has a description and a time/location when it happens.
     * Events are denoted with an [E] tag when displayed.
     */
    private String eventLoctionOrTime;

    /**
     * Constructs an Event with the specified description and time/location.
     *
     * @param description The description of the event task.
     * @param eventLoctionOrTime The time or location when/where the event occurs.
     */
    public Event(String description, String eventLoctionOrTime) {
        super(description);
        eventLoctionOrTime = eventLoctionOrTime;
    }

    public String getType(){
        return "Event";
    }

    /**
     * Returns a string representation of the event task.
     * The format is "[E][status] description (at: time/location)" where status
     * indicates whether the task is done or not.
     *
     * @return A formatted string representing the event task.
     */

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + eventLoctionOrTime + ")";
    }
}
