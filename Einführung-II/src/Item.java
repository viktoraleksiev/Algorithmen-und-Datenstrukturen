
public class Item implements Comparable<Item> {
	public String item;
	private String owner;
	public boolean intheCubby;

	public Item(String name, String ownersName) {
		this.item = name;
		this.owner = ownersName;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String name) {
		this.owner = name;
	}

	@Override
	public int compareTo(Item o) {
		return this.getOwner().compareTo(o.getOwner());
	}

	@Override
	public String toString() {
		String string = this.owner + ": " + this.item;
		return string;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		else {
			Item m = (Item) obj;
			return this.item.equals(m.item);
		}
	}

	@Override
	public int hashCode() {
		return this.item.hashCode();
	}

}

