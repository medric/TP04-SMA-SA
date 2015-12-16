
public class Item {
	private String label;
	private Square currentSquare;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
		this.currentSquare.setObject(this);
	}
	
	public Square getCurrentSquare() {
		return this.currentSquare;
	}
	
	/**
	 * Item
	 * @param label
	 */
	public Item(String label) {
		this.setLabel(label);
	}
}
