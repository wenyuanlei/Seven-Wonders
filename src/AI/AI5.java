package AI;

import Kernel.AI;
import Kernel.Player;

public class AI5 extends AI {
	public void load(Player player) {
		player.setChoose(player.getHands()[0], 3);
	}
}
