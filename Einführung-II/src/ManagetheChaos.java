import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ManagetheChaos {
	List<KitchenItem> allItems;
	Stack<Item> cubby;
	int cubbysize = 15;

	public ManagetheChaos(List<KitchenItem> kitchenItems) {
		allItems = kitchenItems;
		cubby = new Stack<Item>();
	}

	public ManagetheChaos(List<KitchenItem> kitchenItems, Stack C) {
		allItems = kitchenItems;
		if (C.size() > cubbysize)
			throw new RuntimeException("Cubby is not that big!");
		cubby = C;
	}

	public List<Item> findSpares() {
		List<Item> spares = new LinkedList<>();
  		for (int i =0;i < allItems.size();i++){
			if ( allItems.get(i).getQuantity() > allItems.get(i).neededquantity) {
				for (int j =0; j < allItems.get(i).getQuantity() - allItems.get(i).neededquantity;j++) {
					spares.add(new Item(allItems.get(i).item,allItems.get(i).getOwner()));
				}
			}
		}
  		return spares;
	}

	public void putAway() {
		List<Item> spares = this.findSpares();
		if (spares.size() + this.cubby.size() > cubbysize)
			throw new RuntimeException("Cubby is too full!");

		spares.sort(null);

		for (int i = 0; i < spares.size();i++){
			spares.get(i).intheCubby = true;
			cubby.push(spares.get(i));
		}
	}

	public void putAwaySmart() {
		List<Item> spares = this.findSpares();
		Stack<Item> temp = new Stack();
		if (spares.size() + this.cubby.size() > cubbysize)
			throw new RuntimeException("Cubby is too full!");

		spares.sort(null);
		while (!cubby.isEmpty()){
			temp.push(cubby.pop());
		}
		int i = 0;
		while (i<spares.size() || !temp.isEmpty()){
			if (!temp.isEmpty()) {
				Item item = temp.pop();
				if (item == null) {
					spares.get(i).intheCubby = true;
					cubby.push(spares.get(i));
					i++;
				}
				else cubby.push(item);
			}
			else {
				while (i<spares.size()) {
					spares.get(i).intheCubby = true;
					cubby.push(spares.get(i));
					i++;
				}
			}
		}

	}

	public boolean replaceable(Item item) {
		Stack<Item> stack2 = new Stack();
		boolean bool = false;
		while(!bool && !this.cubby.isEmpty()) {
			Item temp = this.cubby.pop();
			stack2.push(temp);
			if (item.equals(temp)){
				bool = true;
			}
		}

		while(!stack2.isEmpty()) {
			cubby.push(stack2.pop());
		}

		if (bool) return true;
		else return false;
	}

	public Item replace(Item item) {
		if (item == null)
			throw new RuntimeException("Nothing is given to be replaced");
		if ( !replaceable(item) )
			throw new RuntimeException("This item is not replaceable");


		Item found = null;
		Stack<Item> stack2 = new Stack();
		boolean bool = false;
		while(!bool) {
			Item temp = cubby.pop();
			stack2.push(temp);
			if (item.equals(temp)){
				stack2.pop();
				found = temp;
				bool = true;
			}
		}

		while(!stack2.isEmpty()) {
			cubby.push(stack2.pop());
		}

		return found;
	}

	public static void main(String[] args) {
		// USE FOR TESTING
	}
}

