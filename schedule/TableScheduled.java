package schedule;

public class TableScheduled extends Scheduled {
	private String name;

	public TableScheduled(String title, Scheduled schedule){
		super(schedule.getStartTime(), schedule.getEndTime(), schedule.getMessage());
		name = title;
	}

	public String getName(){
		return name;
	}
}
