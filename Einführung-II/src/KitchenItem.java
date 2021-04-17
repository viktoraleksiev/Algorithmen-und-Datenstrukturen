
public class KitchenItem extends Item {

	private int totalquantity;
	public int neededquantity;

	public KitchenItem(String name, int n) {
		super(name,"shared");
		this.totalquantity = n;		
	}

	public KitchenItem(String name, int n, String ownersName) {
		super(name,ownersName);
		this.totalquantity = n;
	}

	public KitchenItem(KitchenItem thing) {
		super(thing.item,thing.getOwner());
		totalquantity = thing.totalquantity;
		neededquantity = thing.neededquantity;
		intheCubby = thing.intheCubby;
	}

	public int getQuantity() {
		return totalquantity;
	}

	public void needed(int n) {
		neededquantity = n;
	}

}

