/**
 * @author medric
 *
 */
public class Square {
	private Position position;
	private Object object;

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the agent
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * @param agent the agent to set
	 */
	public void setObject(Object _object) {
		this.object = _object;
	}
	
	/**
	 * Square
	 * @param position
	 */
	public Square(Position position) {
		this.setPosition(position);
	}
}
