package org.tjuscs.sevenwonders.kernel;

import java.io.Serializable;
import java.util.*;

import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.gui.BuyBoard;
import org.tjuscs.sevenwonders.kernel.RecManager.*;

/**
 * The Class Board.
 */
@SuppressWarnings("serial")
public class Board implements Serializable {

	/**
	 * TODO Added by zxn 4-8 Bug-09 Indicate resource list buy from neighbors to
	 * build a stage null means can't build a stg
	 */
	TradeResList ndToBdSgList = null;

	// 0 表示 A面， 1表示B面
	int sides = 0;

	boolean isBSide;

	// boolean canBuildNextStage = true;

	public int getSides() {
		return sides;
	}

	// public void setSides(int sides) {
	// System.out.println(Board.class.getName()+" - setSides()");
	// this.sides = sides;
	// }

	/**
	 * A map of all the goods the board already has.<br>
	 * 该奇迹（板）已经拥有的所有资源的汇总表。
	 * <p>
	 * The EnumMap<Resource, Integer> stores a map which use the Resource as the
	 * key, and the integer as the amount of the Resource.<br>
	 * EnumMap<Resource, Integer>构建了一个特殊的Map，它用Resource作为键，对应的整数作为该资源的数量。
	 */
	EnumMap<Resource, Integer> goods;

	/** The actions. */
	// List<Action> actions; // Not actually used!

	/**
	 * The free-build card list.<br>
	 * 可以免费建造的建筑列表。
	 */
	Set<String> freeList;

	/**
	 * The structures already built.<br>
	 * 已经建造好的建筑。
	 */
	Set<Card> structures;

	/**
	 * A map of all the color information of the cards the board already has.<br>
	 * 该奇迹（板）已经拥有的所有卡牌的颜色信息表。
	 * <p>
	 * The map is used to store the colors of the cards the board already has
	 * and the amount of the cards with each color.<br>
	 * 该Map记录了所有该奇迹（板）已有的牌的颜色信息，每种颜色对应的整数为该颜色卡牌的数量。
	 */
	Map<CardColor, Integer> colorCount;

	/**
	 * The name of the board.<br>
	 * 奇迹（板）的名字。
	 **/
	String brdName;

	/**
	 * The initial resource.<br>
	 * 奇迹的初始资源。
	 */
	Resource initialRes;

	/**
	 * The neighboring board(left/right).<br>
	 * 相邻的奇迹 （左/右）。
	 */
	Board leftNeighbor, rightNeighbor;

	/**
	 * The military victory points.<br>
	 * 军事胜利点
	 */
	int militaryVPS[][];

	final private int MAX = 500;

	/**
	 * The stages.<br>
	 * 奇迹级别
	 */
	Stage[] stages;

	/**
	 * The stages already completed.<br>
	 * 已经建好的奇迹数量
	 */
	int stagesCompleted;

	/**
	 * This map stores the optional-resource cards classified by resource type.<br>
	 * 这个Map存储了多选资源牌，所有的牌由包含的资源种类分类存放。
	 * <p>
	 * Note: One optional-resource card is always stored in multiple places in
	 * this map.<br>
	 * 一张多选资源牌一定会出现在这个Map的不同地方。
	 */
	EnumMap<Resource, ArrayList<Buildable>> orCardsbyRes;

	/**
	 * The list of all the optional-resource Cards.<br>
	 * 多选资源牌的列表
	 */
	ArrayList<Buildable> orCards;

	/**
	 * The list of all the optional-resources groups. <br>
	 * 多选资源组合的列表
	 */
	ArrayList<SimpleResList> orList;

	public ArrayList<SimpleResList> getOrList() {
		return orList;
	}

	/**
	 * The list of all the optional-resources groups that can be sold to other
	 * boards.<br>
	 * 可售出的多选资源牌的列表
	 */
	ArrayList<SimpleResList> sellOrList;

	/**
	 * The resource list.<br>
	 * 资源列表。
	 */
	SimpleResList resList;

	/**
	 * The player.<br>
	 * 玩家
	 */
	Player player;

	/**
	 * The income from selling the resource in this turn.<br>
	 * 本回合卖资源而获得的收入
	 */
	int turnsResourceIncome;

	/**
	 * Whether save the seventh card. <br>
	 * 是否保留每回合的第七张牌（最后一张牌）。
	 * <p>
	 * Note: used for action that allows play of seventh card
	 */
	boolean saveSeventhCard;

	/**
	 * The seventh card in a turn.<br>
	 * 一个回合中的第七张牌（最后一张牌）
	 */
	Card seventhCard;
	// changebhcs
	/**
	 * Whether can do free build action.<br>
	 * 是否可以进行免费建造一张牌的操作。
	 * <p>
	 * Note: used for FreeBuildAction.
	 */
	boolean canfreebuild;

	/**
	 * Gets the canfreebuild.<br>
	 */
	// public boolean getcanfreebuild() {
	// return canfreebuild;
	// }

	/**
	 * 多选一科技点数
	 * <p>
	 */
	int FreeSci;

	/**
	 * Gets the FreeSci.<br>
	 */
	public int getFreeSci() {
		return FreeSci;
	}

	/**
	 * Sets the FreeSci.<br>
	 */
	public void setFreeSci(int x) {
		this.FreeSci = x;
	}

	/**
	 * Whether use free build action.<br>
	 * 是否使用过免费建造操作。
	 */
	boolean usefreebuild;

	/**
	 * Gets the usefreebuild.<br>
	 */
	// public boolean getusefreebuild() {
	// return usefreebuild;
	// }

	/**
	 * Sets the usefreebuild.<br>
	 */
	public void setusefreebuild(boolean ufb) {
		this.usefreebuild = ufb;
	}

	/**
	 * The index of the board. <br>
	 * 奇迹序号 Note: used to build GameManager.out[] String
	 */
	int index;

	/**
	 * Gets the index of the board.<br>
	 * 获取奇迹序号
	 * 
	 * @return the index of the board.奇迹序号
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the index of the board.<br>
	 * 设置奇迹的序号
	 * 
	 * @param index
	 *            the index of the board. 奇迹序号
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * The cost of buying one unit of resource from the neighbors.<br>
	 * 从相邻奇迹处买一个单位的资源需要花的钱.<LIST> <li>leftRawCost: Cost of buying one unit of
	 * raw material from the left neighbor。<br>
	 * 从左侧相邻奇迹处买一单位原料需要的钱数<li>rightRawCost: Cost of buying one unit of raw
	 * material from the right neighbor.<br>
	 * 从右侧相邻奇迹处买一单位原料需要的钱数<li>manfCost: Cost of buying one unit of manufacture
	 * product from the neighbors.<br>
	 * 从两侧相邻奇迹处买一单位制造品需要的钱数</LIST>
	 */
	int leftRawCost = 2, rightRawCost = 2, manfCost = 2;

	/**
	 * Getters of leftRawCost, rightRawCost, manfCost Added on 4-5
	 * 
	 * @author zxn
	 */
	public int getLeftRawCost() {
		return leftRawCost;
	}

	public int getRightRawCost() {
		return rightRawCost;
	}

	public int getManfCost() {
		return manfCost;
	}

	/**
	 * Default constructor.<br>
	 * 默认构造函数
	 * <p>
	 * Build a no-name board(wonder) with no initial resource and 3 stages<br>
	 * 建立一个没有名字、没有初始资源、包含3个级别的奇迹(板)。
	 */
	public Board() {
		this("NO Name", Resource.FREE, 3);
	}

	/**
	 * Constructor with 3 parameters.<br>
	 * 带有3个参数的构造函数
	 * 
	 * @param name
	 *            the name of the board(wonder).奇迹（板）名称
	 * @param initRes
	 *            the initial resource.初始资源
	 * @param numStages
	 *            the number of stages.奇迹级别数(2-4)
	 */
	public Board(String name, Resource initRes, int numStages) {
		goods = new EnumMap<Resource, Integer>(Resource.class);
		brdName = name;
		initialRes = initRes;
		goods.put(Resource.COIN, 3);
		goods.put(Resource.VP, 0);
		militaryVPS = new int[2][4];// 2 * 4 = 2 sides * (3 ages + 1 totalCount)
		goods.put(initialRes, 1);
		stagesCompleted = 0;
		CommerceVps = 0;
		GuildVps = 0;
		FreeSci = 0;
		saveSeventhCard = true; // Here set false now!
		canfreebuild = false; // Here set false now!changebhcs
		usefreebuild = true;

		freeList = new HashSet<String>();
		structures = new HashSet<Card>();
		// actions = new ArrayList<Action>();
		stages = new Stage[numStages + 1];
		stages[numStages] = new Stage();
		colorCount = new EnumMap<CardColor, Integer>(CardColor.class);
		orCardsbyRes = new EnumMap<Resource, ArrayList<Buildable>>(
				Resource.class);
		orCards = new ArrayList<Buildable>();
		orList = new ArrayList<SimpleResList>();
		sellOrList = new ArrayList<SimpleResList>();
		resList = new SimpleResList();
		resList.addResource(initRes);
		turnsResourceIncome = 0;
	}

	/**
	 * Add the card into its structures.<br>
	 * 将这张卡牌加入该奇迹（板）的建筑群中。
	 * 
	 * @param c
	 *            the card need to add in。需要加入的卡牌
	 */
	public void addCard(Card c) {
		if (structures.contains(c)) {
			System.out.println("ERROR: Board already contains " + c);
			return;// JUST Return!?
		}
		
		if (!c.hasOrResources()) {
			// System.out.println("non-or card");
			resList.addCard(c);
		}

		Set<Resource> cardGoods = c.getGoods();
		int newAmt;
		for (Resource r : cardGoods) {
			newAmt = c.goodsCnt(r);
			if (goods.containsKey(r)) {
				newAmt += goods.get(r);
			}
			goods.put(r, newAmt);

			Resource[] orOpts = r.getOptionalRes(); // trying to add OR cards
			if (orOpts != null) {
				orCards.add(c);
				SimpleResList srl = new SimpleResList(c);
				orList.add(srl);
				if (c.getColor() == CardColor.BROWN
						|| c.getColor() == CardColor.GREY)
					sellOrList.add(srl);
				for (Resource res : orOpts) {
					if (!orCardsbyRes.containsKey(res)) {
						orCardsbyRes.put(res, new ArrayList<Buildable>());
					}
					orCardsbyRes.get(res).add(c);
				}
			}
		}

		CardColor cardClr = c.getColor();
		int numOfThatColor = 0;
		if (colorCount.containsKey(cardClr)) {
			numOfThatColor = colorCount.get(cardClr);
		}
		colorCount.put(cardClr, numOfThatColor + 1);
		if (c.hasAction())
			c.getAction().activate(this); // activate any actions
		structures.add(c);

		if (c.freeList != null)
			for (String name : c.freeList) {
				freeList.add(name);
			}
	} // end of addCard method

	/**
	 * Gets the stages already completed.<br>
	 * 返回已经建好的奇迹级别
	 * 
	 * @return the stages completed.<br>
	 *         奇迹级别
	 */
	public int getStagesCompleted() {
		return stagesCompleted;
	}

	/**
	 * Build a stage on the board.<br>
	 * 建好奇迹的某一级别.
	 * 
	 * @param stg
	 *            the stage
	 */
	// public void addStage(Stage stg) {
	// if (!stg.hasOrResources()) {
	// // System.out.println("non-or card");
	// resList.addCard(stg);
	// }
	//
	// Set<Resource> stageGoods = stg.getGoods();
	// int newAmt;
	// for (Resource r : stageGoods) {
	// newAmt = stg.goodsCnt(r);
	// if (goods.containsKey(r)) {
	// newAmt += goods.get(r);
	// }
	// goods.put(r, newAmt);
	//
	// Resource[] orOpts = r.getOptionalRes(); // trying to add OR cards
	// if (orOpts != null) {
	// // orCards.add(c);
	// SimpleResList srl = new SimpleResList(stg);
	// orList.add(srl);
	// }
	// }
	// if (stg.hasAction())
	// stg.getAction().activate(this); // activate any actions
	//
	// stages[stg.getStageNumber()] = stg;
	// stagesCompleted++;
	// }

	/**
	 * Gets the number of stages.<br>
	 * 获取当前奇迹级别数。
	 * 
	 * @return the number of stages.级别数。
	 */
	// public int getNumberOfStages() {
	// return stages.length;
	// }

	/**
	 * Gets the player's choice after giving the command options to the player
	 * and implement it.<br>
	 * 将命令选项传给玩家后获取玩家的选择并实现该命令。
	 * 
	 * @param cmdOps
	 *            the command options.可选的命令选项
	 * @return the player's choice。玩家选择的命令选项
	 */
	public void getPlayerChoice(CommandOption[] cmdOps) {
		System.out.println(Board.class.getName() + " - getPlayerChoice()");
		implementCommand(player.makeChoice(cmdOps));
	}

	/**
	 * Implement the command option.<br>
	 * 实现这个命令选项。
	 * 
	 * @param cmdOpt
	 *            the command option.需要实现的命令选项
	 */
	public void implementCommand(CommandOption cmdOpt) {
		// System.out.println(Board.class.getName()+" - implementCommand()");
		assert cmdOpt != null;
		Command cmd = cmdOpt.getCommand();
		Card card = cmdOpt.getCard();
		System.out.println("\n\nBefore:  " + this + ": " + cmd.toString()
				+ "  " + card);
		switch (cmd) {
		case BUILD_CARD:
			// this.addToCoins(-(card.getCmd().getNeedToBuild())); //Original
			// version Changed by zxn 4-5 Bug-5

			/**
			 * TODO 4-2 4-5 Bug-2-5 Buy resources from neighbors
			 * 
			 * @author zxn
			 */
			SimpleResList buyFromLeft = BuyDecision.buyFromLeft;
			SimpleResList buyFromRight = BuyDecision.buyFromRight;
			int ndToBuild = 0;
			int coins = 0;
			if (buyFromLeft != null) {
				coins = coins
						+ this.leftRawCost
						* (buyFromLeft.srl[1] + buyFromLeft.srl[2]
								+ buyFromLeft.srl[3] + buyFromLeft.srl[4])
						+ manfCost
						* (buyFromLeft.srl[5] + buyFromLeft.srl[6] + buyFromLeft.srl[7]);
				this.leftNeighbor.buyResources(coins);
			}
			ndToBuild += coins;
			coins = 0;
			if (buyFromRight != null) {
				coins = coins
						+ this.rightRawCost
						* (buyFromRight.srl[1] + buyFromRight.srl[2]
								+ buyFromRight.srl[3] + buyFromRight.srl[4])
						+ manfCost
						* (buyFromRight.srl[5] + buyFromRight.srl[6] + buyFromRight.srl[7]);
				this.rightNeighbor.buyResources(coins);
			}
			ndToBuild += coins;
			if (ndToBuild < card.getCmd().getNeedToBuild())
				ndToBuild = card.getCmd().getNeedToBuild();
			this.addToCoins(-ndToBuild);
			// System.out.println("\n\nNeed To Build="+ndToBuild);
			BuyDecision.reset();
			// end @zxn

			addCard(card);

			/*
			 * System.out.println(Board.class.getName()+
			 * " - implementCommand() Build_Card - leftRawCost = "
			 * +this.leftRawCost); System.out.println(Board.class.getName()+
			 * " - implementCommand() Build_Card - rightRawCost = "
			 * +this.rightRawCost); System.out.println(Board.class.getName()+
			 * " - implementCommand() Build_Card - manfCost = "+this.manfCost);
			 */

			break;
		case BUILD_STAGE:
			
			Stage sta = stages[stagesCompleted];
			
			if (!sta.hasOrResources()) {
				resList.addCard(sta);
			}
			System.out.println("===============Built Stage ================ ");

			// TODO Added by zxn 4-8 Bug-09
			if (ndToBdSgList != null && ndToBdSgList.cost != 0) {
				int leftCost = ndToBdSgList.buyFromLeft.getTotalCost(
						leftRawCost, manfCost);
				int rightCost = ndToBdSgList.buyFromRight.getTotalCost(
						rightRawCost, manfCost);
				this.leftNeighbor.buyResources(leftCost);
				this.rightNeighbor.buyResources(rightCost);
				ndToBdSgList = null;
			}

			this.addToCoins(-(card.getCmd().getNeedToBuildStage()));
			Set<Resource> stageGoods = sta.getGoods();
			int newAmt;
			for (Resource r : stageGoods) {
				newAmt = sta.goodsCnt(r);
				if (goods.containsKey(r)) {
					newAmt += goods.get(r);
				}
				goods.put(r, newAmt);

				Resource[] orOpts = r.getOptionalRes(); // trying to add OR
														// cards
				if (orOpts != null) {
					orCards.add(sta);
					SimpleResList srl = new SimpleResList(sta);
					orList.add(srl);
					for (Resource res : orOpts) {
						if (!orCardsbyRes.containsKey(res)) {
							orCardsbyRes.put(res, new ArrayList<Buildable>());
						}
						orCardsbyRes.get(res).add(sta);
					}
				}
			}

			if (sta.hasAction())
				sta.getAction().activate(this); // activate any actions

			this.stagesCompleted++;

			// 如果是Halicarnassus A面的二阶
//			if(brdName == "Halicarnassus" && stagesCompleted == 2) {
//				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//				LinkedList<Card> dCard = KernelManager.getCardManager().getDiscardedCards2();
//				Iterator<Card> it = dCard.iterator();
//				Card c = it.next();
//				addCard(c);
//			}			

			break;
		case SELL_CARD:
			this.addToCoins(3);
			discardCard(card);
			KernelManager.getManager().out[getIndex()].append("\nSelling "
					+ card);
			break;
		case NONE:
		default:
			System.out
					.println("OOOPs, I haven't implemented that ability into takeTurn yet");
		}

		System.out.println("After:  " + this + ": ");
	}

	/**
	 * TODO Added by zxn 4-7 bug-9
	 * 
	 * @param newList
	 * @return
	 */
	private TradeResList getNdToBdSg(SimpleResList newList) {
		SimpleResList smpList;
		TradeResList res, minSRL;
		minSRL = new TradeResList();
		minSRL.cost = MAX;
		int i = 1, min = MAX;
		while (i < 8 && newList.srl[i] <= 0)
			i++;
		if (i == 8)
			return (new TradeResList());
		// Buy From Self
		for (int j = 0; j < orList.size(); j++) {
			smpList = orList.get(j);
			if (smpList.srl[i] > 0) {
				orList.remove(j);
				newList.srl[i] -= smpList.srl[i];
				res = getNdToBdSg(newList);
				if (res.cost < min) {
					min = res.cost;
					minSRL = new TradeResList(res);
				}
				newList.srl[i] += smpList.srl[i];
				orList.add(j, smpList);
			}
		}
		if (leftNeighbor.resList.srl[i] > 0) {
			leftNeighbor.resList.srl[i]--;
			newList.srl[i]--;
			res = new TradeResList(leftRawCost, rightRawCost, manfCost);
			res.buyFromLeft.srl[i] = 1;
			res.buyFromLeft.srl[0] = 1;

			if (i < 5)
				res.cost = leftRawCost;
			else
				res.cost = manfCost;

			TradeResList tmp = getNdToBdSg(newList);
			res.merge(tmp);

			// res+=get(newList);

			if (res.cost < min) {
				min = res.cost;
				minSRL = new TradeResList(res);
			}
			leftNeighbor.resList.srl[i]++;
			newList.srl[i]++;
		} else {
			for (int j = 0; j < leftNeighbor.sellOrList.size(); j++) {
				smpList = leftNeighbor.sellOrList.get(j);
				if (smpList.srl[i] > 0) {
					leftNeighbor.sellOrList.remove(j);
					newList.srl[i] -= smpList.srl[i];

					res = new TradeResList(leftRawCost, rightRawCost, manfCost);
					res.buyFromLeft.srl[i] = 1;
					res.buyFromLeft.srl[0] = 1;

					if (i < 5)
						res.cost = leftRawCost;
					else
						res.cost = manfCost;

					TradeResList tmp = getNdToBdSg(newList);
					res.merge(tmp);

					if (res.cost < min) {
						min = res.cost;
						minSRL = new TradeResList(res);
					}

					newList.srl[i] += smpList.srl[i];
					leftNeighbor.sellOrList.add(j, smpList);
				}
			}
		}
		if (rightNeighbor.resList.srl[i] > 0) {
			rightNeighbor.resList.srl[i]--;
			newList.srl[i]--;

			res = new TradeResList(leftRawCost, rightRawCost, manfCost);
			res.buyFromRight.srl[i] = 1;
			res.buyFromRight.srl[0] = 1;

			if (i < 5)
				res.cost = leftRawCost;
			else
				res.cost = manfCost;

			TradeResList tmp = getNdToBdSg(newList);
			res.merge(tmp);

			// res+=get(newList);

			if (res.cost < min) {
				min = res.cost;
				minSRL = new TradeResList(res);
			}
			rightNeighbor.resList.srl[i]++;
			newList.srl[i]++;
		} else {
			for (int j = 0; j < rightNeighbor.sellOrList.size(); j++) {
				smpList = rightNeighbor.sellOrList.get(j);
				if (smpList.srl[i] > 0) {
					rightNeighbor.sellOrList.remove(j);
					newList.srl[i] -= smpList.srl[i];
					res = new TradeResList(leftRawCost, rightRawCost, manfCost);
					res.buyFromRight.srl[i] = 1;
					res.buyFromRight.srl[0] = 1;

					if (i < 5)
						res.cost = leftRawCost;
					else
						res.cost = manfCost;

					TradeResList tmp = getNdToBdSg(newList);
					res.merge(tmp);

					// res+=get(newList);

					if (res.cost < min) {
						min = res.cost;
						minSRL = new TradeResList(res);
					}
					newList.srl[i] += smpList.srl[i];
					rightNeighbor.sellOrList.add(j, smpList);
				}
			}
		}
		return minSRL;
	}

	public int get(SimpleResList newList) { // TODO Original private, changed by
											// zxn
	// System.out.println(this.getClass().getName()+" - get() - "+newList+" -left="+leftRawCost+" -right="+rightRawCost+" -manf="+manfCost);
		SimpleResList smpList;
		int i = 1, min = MAX, res = MAX;
		while (i < 8 && newList.srl[i] <= 0)
			i++;
		if (i == 8)
			return 0;
		for (int j = 0; j < orList.size(); j++) {
			smpList = orList.get(j);
			if (smpList.srl[i] > 0) {
				orList.remove(j);
				newList.srl[i] -= smpList.srl[i];
				res = get(newList);
				if (res < min)
					min = res;
				newList.srl[i] += smpList.srl[i];
				orList.add(j, smpList);
			}
		}
		if (leftNeighbor.resList.srl[i] > 0) {
			leftNeighbor.resList.srl[i]--;
			newList.srl[i]--;
			if (i < 5)
				res = leftRawCost;
			else
				res = manfCost;
			res += get(newList);
			if (res < min)
				min = res;
			leftNeighbor.resList.srl[i]++;
			newList.srl[i]++;
		} else {
			for (int j = 0; j < leftNeighbor.sellOrList.size(); j++) {
				smpList = leftNeighbor.sellOrList.get(j);
				if (smpList.srl[i] > 0) {
					leftNeighbor.sellOrList.remove(j);
					newList.srl[i] -= smpList.srl[i];
					if (i < 5)
						res = leftRawCost;
					else
						res = manfCost;
					res += get(newList);
					if (res < min)
						min = res;
					newList.srl[i] += smpList.srl[i];
					leftNeighbor.sellOrList.add(j, smpList);
				}
			}
		}
		if (rightNeighbor.resList.srl[i] > 0) {
			rightNeighbor.resList.srl[i]--;
			newList.srl[i]--;
			if (i < 5)
				res = rightRawCost;
			else
				res = manfCost;
			res += get(newList);
			if (res < min)
				min = res;
			rightNeighbor.resList.srl[i]++;
			newList.srl[i]++;
		} else {
			for (int j = 0; j < rightNeighbor.sellOrList.size(); j++) {
				smpList = rightNeighbor.sellOrList.get(j);
				if (smpList.srl[i] > 0) {
					rightNeighbor.sellOrList.remove(j);
					newList.srl[i] -= smpList.srl[i]; // TODO Changed by zxn
					// Original newList.srl[i]-=smpList.srl[j];
					if (i < 5)
						res = rightRawCost;
					else
						res = manfCost;
					res += get(newList);
					if (res < min)
						min = res;
					newList.srl[i] += smpList.srl[i];
					rightNeighbor.sellOrList.add(j, smpList);
				}
			}
		}
		return min;
	}

	/**
	 * The Wonder(Board) takes their turn.<br>
	 * 奇迹开始它的回合内的操作。
	 * <p>
	 * It calls the player to make choice and then implement the choice<br>
	 * 奇迹调用玩家来做出选择，并实现该选择。
	 * 
	 * @param hand
	 *            the hand deck.分得的手牌堆
	 * @param trnNum
	 *            the turn number.回合序号
	 */
	public void takeTurn(Hand hand, int trnNum) {
		System.out.println(Board.class.getName() + " - takeTurn() Board Name="
				+ this.brdName);
		CommandOption[] opts = buildCommandOptions(hand);
		CommandOption opt = player.makeChoice(opts);
		
		// System.out.println(Board.class.getName()+" - CommandOption="+opt);
		this.implementCommand(opt);

		Card card = opt.getCard();
		hand.remove(card.getName());
		/* TODO 6th turn, need to check */
		if (trnNum == 6 && saveSeventhCard) {
			System.out.println("\nTESTING: " + this.toString() + " has: "
					+ hand.size() + " cards left in after 6th turn ");
			seventhCard = hand.get(0);
			/**
			 *  第七张牌应加入discard链表中
			 *  然后从手牌中remove掉
			 *  @author wanting
			 */
			discardCard(seventhCard);
			hand.remove(seventhCard.getName());
		}

		TurnInfo info = new TurnInfo();
		for (int i = 0; i < opts.length; i++) {
			if (opts[i].equals(opt)) {
				info.chosenCardIndex = i;
				break;
			}
		}
		info.turnNum = trnNum;
		info.playerIndex = this.index;
		info.chosenCommandType = opt.getCommand();
		info.chosenBuyDecision = null;
		Manager.getKernel().recordTurnInfo(info);
	}

	public void buildCommandOption(Hand hand) {
		System.out.println(Board.class.getName() + " - buildCommandOption()");
		ndToBdSgList = null;
		boolean opt, ava, canB, canBS;
		int ndToBd = 0, ndToBdSg = 0;
		SimpleResList tmp;
		tmp = SimpleResList.subtract(
				SimpleResList.buildCostList(stages[stagesCompleted]), resList);
		// ndToBdSg = get(tmp); //Original Version

		// TODO Changed by zxn 4-8 Bug-9
		TradeResList tmpList;
		tmpList = getNdToBdSg(tmp);
		ndToBdSg = tmpList.cost;
		// System.out.println(tmpList);

		if (ndToBdSg > this.getTotalCoins()
				|| stagesCompleted >= stages.length - 1) {
			canBS = false;
		} else {
			canBS = true;
			// TODO Added by zxn 4-8 bug-09
			ndToBdSgList = new TradeResList(tmpList);
		}
		System.out.println();
		for (int i = 0; i < hand.size(); i++) {
			Card cd = hand.hand.get(i);
			opt = false;
			cd.getCmd().setCanBuildStage(canBS);
			cd.getCmd().setNeedToBuildStage(ndToBdSg);
			tmp = SimpleResList.subtract(SimpleResList.buildCostList(cd),
					resList);
			if (tmp.srl[0] != 0) {
				opt = true;
			}
			/*
			 * System.out.println("\n\n\n\n\n\t\t\t\tCard: "+cd);
			 * System.out.println("\t\t\t\tneedList: "+tmp);
			 * System.out.println("\n\t\t\t\tYour resList: "+resList);
			 * System.out.println("\t\t\t\tYour orList: "+orList);
			 * System.out.println
			 * ("\t\t\t\tYour total coins: "+this.getTotalCoins());
			 * System.out.println
			 * ("\n\t\t\t\tLeft resList: "+leftNeighbor.resList);
			 * System.out.println
			 * ("\t\t\t\tLeft sellOrList: "+leftNeighbor.sellOrList);
			 * System.out.
			 * println("\n\t\t\t\tRight resList: "+rightNeighbor.resList);
			 * System
			 * .out.println("\t\t\t\tRight sellOrList: "+rightNeighbor.sellOrList
			 * );
			 * 
			 * System.out.println("\n\t\t\t\tleftRawCost: "+leftRawCost);
			 * System.out.println("\t\t\t\trightRawCost: "+rightRawCost);
			 * System.out.println("\t\t\t\tmanfCost: "+manfCost);
			 * System.out.println("\t\t\t\tIs options? "+opt);
			 */
			System.out.println("Card: " + cd);
			ndToBd = get(tmp);
			// System.out.println(Board.class.getName()+" get() = "+ndToBd);
			ndToBd += cd.getCoin();
			// System.out.println(Board.class.getName()+" ndToBd = "+ndToBd);

			if (ndToBd > this.getTotalCoins()) {
				canB = false;
				// System.out.println("\n\t\t\t\tCan't build");
				// System.out.println("\t\t\t\tNeed money: "+ndToBd);
			} else {
				canB = true;
				// System.out.println("\n\t\t\t\tCan build");
				// System.out.println("\t\t\t\tNeed money: "+ndToBd);
			}
			cd.getCmd().setOptions(opt);
			// System.out.println("\n\t\t\t\t"+cd.getCmd().isOptions());
			cd.getCmd().setCanBuildStage(canBS);
			cd.getCmd().setCanBuild(canB);
			cd.getCmd().setNeedToBuild(ndToBd);
			opt = (ndToBd == 0);
			//关于免费建造
			ava = freeList.contains(cd.getName());
			if (ava) {
				cd.getCmd().setOptions(false);
				cd.getCmd().setNeedToBuild(0);
				cd.getCmd().setCanBuild(true);
			}
			// System.out.println(Board.class.getName()+" availableFree = "+ava);
			cd.getCmd().setAvailableFree(ava);
			for (Card crd : structures) {
				if (crd.getName().equals(cd.getName())) {
					cd.getCmd().setAvailableFree(false);
					cd.getCmd().setCanBuild(false);
					break;
				}
			}
		}
		/**
		 * @author wanting
		 * 打印场上牌的信息和墓地中牌的信息
		 */
		getDiscarded();
		for(Card crd: structures) {
			System.out.println("Ground:" + crd.getName());
		}
		System.out.println("HandSize: " + hand.size());
	}

	/**
	 * Builds the command options according to the hand deck. <br>
	 * 根据传入的手牌堆得出可用的命令选项
	 * 
	 * <p>
	 * TODO need to add logic for using 'or' x3 and x4 resources and then
	 * neighbor.
	 * 
	 * @param hand
	 *            the hand deck.手牌堆
	 * @return the command options available<br>
	 *         可用的命令
	 */
	public CommandOption[] buildCommandOptions(Hand hand) {
		buildCommandOption(hand);
		CommandOption[] options = new CommandOption[hand.size()];
		for (int i = 0; i < hand.size(); i++) {
			options[i] = hand.hand.get(i).getCmd();
			options[i].setLeftSRL(leftNeighbor.resList);
			options[i].setRightSRL(rightNeighbor.resList);
			// System.out.println("\t\t\t\t\t"+i+options[i].isOptions());
		}
		return options;
	} // end of buildCommandOptions method

	/**
	 * Judge whether a card can be build.<br>
	 * 判断一张卡牌中的建筑是否能够建造 TODO: Not used by zxn
	 * <p>
	 * Note: this method will be using the new SRL classes to resolve the
	 * question
	 * 
	 * @param bld
	 *            the card attempt to build.尝试建造的建筑。
	 * @param cardCost
	 *            the cost to build the card.建造该建筑所需代价。
	 * @return true, if can build it.<br>
	 *         如果可以建造，返回true.
	 */
	// public boolean canBuild(Card bld, SimpleResList cardCost) {
	// boolean canAfford = false;
	// if (bld.isFreeToBuild()) {
	// // System.out.println("\t\tcanBuild: card is free" );
	// return true;
	// }
	//
	// if (cardCost.subtract(resList) == 0)
	// return true;// There are enough resources
	//
	// // use orlist and sellorlist to see if this helps
	// ArrayList<SimpleResList> compOrList = new ArrayList<SimpleResList>(
	// orList);
	// boolean listChanged = true;
	// int numMatch;
	// while (listChanged) {
	// listChanged = false;
	// for (SimpleResList srl : compOrList) {
	// numMatch = srl.findNumMatches(cardCost);
	// if (numMatch == 0) {
	// compOrList.remove(srl);
	// listChanged = true;
	// break;
	// }
	// if (numMatch == 1) { // There may be some problems here...
	// cardCost.subtract(srl);
	// compOrList.remove(srl);
	// listChanged = true;
	// break;
	// }
	// }
	// if (cardCost.getTotalRes() == 0
	// || cardCost.getTotalRes() <= compOrList.size()) {
	// return true;
	// }
	// }
	// System.out.println("canBuild:::numOrsLeft " + compOrList.size()
	// + " goodStillneeded: " + cardCost.getTotalRes());
	//
	// return canAfford;
	// }

	/**
	 * Judge whether the board can build next stage.<br>
	 * 判断该奇迹的下一个级别是否可以建造
	 * 
	 * @return true, if it can build the next stage.<br>
	 *         如果可以建造，返回true.
	 */
	public boolean canBuildNextStage() {
		boolean canBS;
		int ndToBdSg = 0;
		SimpleResList tmp;
		tmp = SimpleResList.subtract(
				SimpleResList.buildCostList(stages[stagesCompleted]), resList);
		ndToBdSg = get(tmp);
		if (ndToBdSg > this.getTotalCoins()) {
			canBS = false;
		} else {
			canBS = true;
		}
		return canBS;
	}

	// this method will be using the new SRL classes to resolve the question
	// This method is not in use. By ZXN
	/**
	 * Can build stage. 判断能不能建奇迹
	 * 
	 * @param bld
	 *            the bld
	 * @param stageCost
	 *            the stage cost
	 * @return true, if successful
	 */
	// public boolean canBuildStage(Stage bld, SimpleResList stageCost) {
	// boolean canAfford = false;
	//
	// if (stageCost.subtract(resList) == 0)
	// return true;
	//
	// // use orlist and sellorlist to see if this helps
	// ArrayList<SimpleResList> compOrList = new ArrayList<SimpleResList>(
	// orList);
	//
	// boolean listChanged = true;
	// int numMatch;
	// while (listChanged) {
	// listChanged = false;
	// for (SimpleResList srl : compOrList) {
	// numMatch = srl.findNumMatches(stageCost);
	// if (numMatch == 0) {
	// compOrList.remove(srl);
	// listChanged = true;
	// break;
	// }
	// if (numMatch == 1) {
	// stageCost.subtract(srl);
	// compOrList.remove(srl);
	// listChanged = true;
	// break;
	// }
	// }
	// if (stageCost.getTotalRes() == 0
	// || stageCost.getTotalRes() <= compOrList.size()) {
	// return true;
	// }
	// }
	// System.out.println("canBuildStage:::numOrsLeft " + compOrList.size()
	// + " goodStillneeded: " + stageCost.getTotalRes());
	// if (!canAfford) {
	//
	// System.out.println("\t\t Could buy "
	// + this.leftNeighbor.canBuy(new SimpleResList(stageCost))
	// + " resources from left neighbor");
	// System.out.println("\t\t Could buy "
	// + this.rightNeighbor.canBuy(new SimpleResList(stageCost))
	// + " resources from right neighbor");
	// }
	// return canAfford;
	// }
	//
	// this is used by neighbors to see if this board has certain resources
	// available to buy
	// returns the number of goods that can be bought
	/**
	 * Can buy. 判断一个简单资源列表中能买到几个资源
	 * 
	 * @param cardCost
	 *            the card cost
	 * @return the int
	 */
	// public int canBuy(SimpleResList cardCost) {
	// int totalNeeded = cardCost.getTotalRes();
	// int numLeft = totalNeeded;
	//
	// if ((numLeft = cardCost.subtract(resList)) == 0)
	// return totalNeeded;
	//
	// ArrayList<SimpleResList> copySellOrList = new ArrayList<SimpleResList>(
	// sellOrList);
	//
	// boolean listChanged = true;
	// int numMatch;
	// while (listChanged) {
	// listChanged = false;
	// for (SimpleResList srl : copySellOrList) {
	// numMatch = srl.findNumMatches(cardCost);
	// if (numMatch == 0) {
	// copySellOrList.remove(srl);
	// listChanged = true;
	// break;
	// }
	// if (numMatch == 1) {
	// // int dif = cardCost.getTotalRes();
	// numLeft = cardCost.subtract(srl);
	// copySellOrList.remove(srl);
	// listChanged = true;
	// break;
	// }
	// }
	// if (cardCost.getTotalRes() == 0
	// || cardCost.getTotalRes() <= copySellOrList.size()) {
	// return totalNeeded;
	// }
	// }
	// return totalNeeded - numLeft;
	// }
	
	public Set<Card> getStructure(){
		return structures;
	}
	/**
	 * Gets the number of completed stages. 返回奇迹完成的层数
	 * 
	 * @return the number completed stages
	 */
	// public int getNumberCompletedStages() {
	// return this.stagesCompleted;
	// }

	/**
	 * Gets the resource list. 返回拥有的资源列表
	 * 
	 * @return the resource list
	 */
	public SimpleResList getResourceList() {
		return new SimpleResList(resList);
	}

	/**
	 * Gets the list of all the optional-resources groups that can be sold to
	 * other 返回可售出的多选资源牌的列表
	 * 
	 * @return the sell or list
	 */
	public ArrayList<SimpleResList> getSellOrList() {
		return new ArrayList<SimpleResList>(sellOrList); // may be shallow copy
	}

	/**
	 * Gets the count of a certain card. 返回某种颜色对应的牌的个数
	 * 
	 * @param crdClr
	 *            the crd clr
	 * @return the color count
	 */
	public int getColorCount(CardColor crdClr) {
		int count = 0;
		if (colorCount.containsKey(crdClr)) {
			count = colorCount.get(crdClr);
		}
		return count;
	}

	/**
	 * Add some money to coins. 加钱
	 * 
	 * @param more
	 *            the more
	 */
	public void addToCoins(int more) {
		System.out.println(Board.class.getName()
				+ " - addToCoins - Board name = " + this.brdName + "  " + more);
		int coins = more;
		if (goods.containsKey(Resource.COIN)) {
			coins = goods.get(Resource.COIN);
			coins += more;
		}
		goods.put(Resource.COIN, coins);
	}

	/**
	 * Buy resources. 卖资源
	 * 
	 * @param coins
	 *            the coins
	 */
	public void buyResources(int coins) {
		System.out.println(Board.class.getName() + " - " + brdName
				+ " - buyResources(" + coins + ")");
		turnsResourceIncome += coins;
	}

	/**
	 * Adds the turn sales to the treasury.<br>
	 * 将本回合卖资源的收入纳入国库。
	 * <p>
	 * This function is only called at the end of each turn to ensure that the
	 * income a board got in a turn can only be used from the next turn.<br>
	 * 该函数仅仅在每回合结束时被调用，来保证当前回合的收入仅能从下一回合开始使用。
	 */
	void addTurnSales() {
		System.out.println(Board.class.getName() + " - " + this.brdName
				+ " - addTurnSales()");
		if (this.turnsResourceIncome > 0)
			addToCoins(this.turnsResourceIncome);
		turnsResourceIncome = 0;
	}

	/**
	 * Add the victory point to the board.<br>
	 * 增加一个奇迹的胜利点数。
	 * 
	 * @param more
	 *            the more victory points to add.增加的新胜利点数
	 */
	public void addToVPs(int more) {
		int vps = more;
		if (goods.containsKey(Resource.VP)) {
			vps = goods.get(Resource.VP);
			vps += more;
		}
		System.out.println(Board.class.getName() + " - addToVps "
				+ this.getName() + " : " + vps);
		goods.put(Resource.VP, vps);
	}

	/**
	 * Gets the number of defeats.<br>
	 * 获得战斗失败的次数。
	 * 
	 * @return the number of defeats。失败次数。
	 */
	public int getNumberOfDefeats() {
		int numDefeats = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 1; j <= 3; j++) {
				if (militaryVPS[i][j] == -1)
					numDefeats++;
			}
		}
		return numDefeats;
	}

	/**
	 * Gets the player.<br>
	 * 获得玩家。
	 * 
	 * @return the player。玩家
	 */
	// public Player getPlayer() {
	// return player;
	// }

	/**
	 * Sets the player.<br>
	 * 设置玩家。
	 * 
	 * @param plyer
	 *            the player.玩家
	 */
	public void setPlayer(Player plyer) {
		player = plyer;
	}

	/**
	 * Gets the left neighbor.<br>
	 * 获得左侧相邻的奇迹
	 * 
	 * @return the left neighbor。左侧相邻奇迹
	 */
	public Board getLeftNeighbor() {
		return leftNeighbor;
	}

	/**
	 * Sets the left neighbor.<br>
	 * 设置左侧相邻奇迹
	 * 
	 * @param leftNeighbor
	 *            the new left neighbor。新的左侧相邻奇迹
	 */
	public void setLeftNeighbor(Board leftNeighbor) {
		this.leftNeighbor = leftNeighbor;
	}

	/**
	 * Gets the right neighbor.<br>
	 * 获得右侧相邻奇迹。
	 * 
	 * @return the right neighbor。右侧相邻奇迹
	 */
	public Board getRightNeighbor() {
		return rightNeighbor;
	}

	/**
	 * Sets the right neighbor.<br>
	 * 设置右侧相邻奇迹
	 * 
	 * @param rightNeighbor
	 *            the new right neighbor。新的右侧相邻奇迹
	 */
	public void setRightNeighbor(Board rightNeighbor) {
		this.rightNeighbor = rightNeighbor;
	}

	/**
	 * Sets the stage.<br>
	 * 设置奇迹级别。
	 * 
	 * @param stg
	 *            the new stage。新的奇迹级别
	 */
	public void setStage(Stage stg) {
		stages[stg.stageNum - 1] = stg;
	}

	/**
	 * Score victory points got from the current scientific(green) cards.<br>
	 * 计算从现有科技牌获得的胜利点数。
	 * 
	 * @return the victory point from science.科技牌的胜利点数
	 */
	int max(int x, int y) {
		if (x >= y)
			return x;
		else
			return y;
	}

	public int calsvps(int x, int y, int z) {
		int numset, total = 0;
		numset = x;
		total = x * x;
		if (y < numset)
			numset = y;
		total += y * y;
		if (z < numset)
			numset = z;
		total += z * z;
		total += numset * 7;
		return total;
	}

	public int scoreVPs() {
		int[] symbols = new int[3];
		int numfs, total = 0;
		if (goods.containsKey(Resource.COG))
			symbols[0] = goods.get(Resource.COG);
		else
			symbols[0] = 0;
		if (goods.containsKey(Resource.COMPASS))
			symbols[1] = goods.get(Resource.COMPASS);
		else
			symbols[1] = 0;
		if (goods.containsKey(Resource.TABLET))
			symbols[2] = goods.get(Resource.TABLET);
		else
			symbols[2] = 0;
		numfs = this.getFreeSci();
		if (numfs == 0)
			total = this.calsvps(symbols[0], symbols[1], symbols[2]);
		if (numfs == 1) {
			total = this.calsvps(symbols[0] + 1, symbols[1], symbols[2]);
			total = this.max(total,
					this.calsvps(symbols[0], symbols[1] + 1, symbols[2]));
			total = this.max(total,
					this.calsvps(symbols[0], symbols[1], symbols[2] + 1));
		}
		if (numfs == 2) {
			total = this.calsvps(symbols[0] + 2, symbols[1], symbols[2]);
			total = this.max(total,
					this.calsvps(symbols[0] + 1, symbols[1] + 1, symbols[2]));
			total = this.max(total,
					this.calsvps(symbols[0] + 1, symbols[1], symbols[2] + 1));
			total = this.max(total,
					this.calsvps(symbols[0], symbols[1] + 2, symbols[2]));
			total = this.max(total,
					this.calsvps(symbols[0], symbols[1] + 1, symbols[2] + 1));
			total = this.max(total,
					this.calsvps(symbols[0], symbols[1], symbols[2] + 2));
		}
		return total;
	}

	/**
	 * Score victory points got from the coin.<br>
	 * 计算从现有剩余金钱获得的胜利点数。
	 * 
	 * @return the victory point from coin.剩余金钱胜利点数
	 */
	public int cscoreVPs() {
		int total = 0;
		if (goods.containsKey(Resource.COIN))
			total = goods.get(Resource.COIN);
		total /= 3;
		return total;
	}

	/**
	 * Score victory points according to the given science symbols from the
	 * green cards. <br>
	 * 根据传入的科技牌中的科技符号计算相应的胜利点数。
	 * 
	 * <p>
	 * Note:For player to call to get info on SCISYM points index 0 = cog, 1 =
	 * compass, 2 = tablet
	 * 
	 * @param symbols
	 *            the science symbols。科技牌中的科技符号
	 * @return the score from the symbols<br>
	 *         科技牌的胜利点数
	 */
	public static int scoreVPs(int[] symbols) {
		int numSets, total = 0;
		numSets = symbols[0];
		total = symbols[0] * symbols[0];
		for (int i = 1; i < 3; i++) {
			if (symbols[i] < numSets)
				numSets = symbols[i];
			total += symbols[i] * symbols[i];
		}
		total += numSets * 7;
		return total;
	}

	/*
	 * Overload the toString functions to give the game message
	 * 重载toString函数来返回函数信息
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = brdName;

		Set<Resource> goodsSet = goods.keySet();

		if (goodsSet.isEmpty())
			str += " Provides no resources ";
		else {
			str += " Provides: ";
			for (Resource r : goodsSet) {
				str += goods.get(r) + " " + r.toString() + "  ";
			}
		}
		str += "\n";
		return str;
	}

	/**
	 * Gets the total coins.<br>
	 * 获得总金币数。
	 * 
	 * @return the total coins。总金币数
	 */
	public int getTotalCoins() {
		return goods.get(Resource.COIN);
	}

	/**
	 * Gets the name.<br>
	 * 获得奇迹（板）的名称。
	 * 
	 * @return the name of the board.奇迹名称
	 */
	public String getName() {
		return brdName;
	}

	/**
	 * Discard a card.<br>
	 * 弃掉一张牌。
	 * 
	 * @param card
	 *            the card to be discarded.要弃掉的牌。
	 */
	private void discardCard(Card card) {
		KernelManager.getCardManager().discardCard(card);
		// GameManager.getManager().getCardManager().discardCard(card);
	}

	public void setBSide(boolean isBSide) {
		this.isBSide = isBSide;
		this.sides = isBSide ? 1 : 0;
	}

	public boolean isBSide() {
		return isBSide;
	}

	public int[][] getMilitaryVPS() {
		return militaryVPS;
	}

	/**
	 * @author tjumyk
	 * @version 12-06-08-39
	 * @return total victory points
	 */
	public int getTotalVPS() {
		return goods.get(Resource.VP);
	}

	/**
	 * @author tjumyk
	 * @version 12-06-08-39
	 * @return victory points from stages completed
	 */
	public int getStageCompleteVPS() {
		int svps = 0;
		for (int i = 0; i < stagesCompleted; i++)
			svps += stages[i].goodsCnt(Resource.VP);
		return svps;
	}

	/**
	 * @author tjumyk
	 * @version 12-06-08-39
	 * @return victory points from Civil cards (Blue cards)
	 */
	public int getCivilVPS() {
		int vps = 0;
		for (Card card : structures) {
			if (card.getColor().equals(CardColor.BLUE))
				vps += card.goodsCnt(Resource.VP);
		}
		return vps;
	}

	/**
	 * @author bhcs
	 * @version 12-06-19-38
	 * @return victory points from Commerce cards (Yellow cards)
	 */
	int CommerceVps;

	/**
	 * @author tjumyk
	 * @version 12-06-08-39
	 * @return victory points from Commerce cards (Yellow cards)
	 */
	public int getCommerceVPS() {
		return CommerceVps;
	}

	/**
	 * @author bhcs
	 * @version 12-06-19-38
	 * @return victory points from Guild cards (Purple cards)
	 */
	int GuildVps;

	/**
	 * @author tjumyk
	 * @version 12-06-08-39
	 * @return victory points from Guild cards (Purple cards)
	 */
	public int getGuildVPS() {
		return GuildVps;
	}

	/**
	 * TODO Added by zxn 4-11
	 * 
	 * @return
	 */

	public SimpleResList getResList() {
		SimpleResList list = new SimpleResList();
		if (goods.containsKey(Resource.BRICK))
			list.srl[1] = goods.get(Resource.BRICK);
		if (goods.containsKey(Resource.ORE))
			list.srl[2] = goods.get(Resource.ORE);
		if (goods.containsKey(Resource.STONE))
			list.srl[3] = goods.get(Resource.STONE);
		if (goods.containsKey(Resource.WOOD))
			list.srl[4] = goods.get(Resource.WOOD);
		if (goods.containsKey(Resource.CLOTH))
			list.srl[5] = goods.get(Resource.CLOTH);
		if (goods.containsKey(Resource.GLASS))
			list.srl[6] = goods.get(Resource.GLASS);
		if (goods.containsKey(Resource.PAPYRUS))
			list.srl[7] = goods.get(Resource.PAPYRUS);
		if (goods.containsKey(Resource.BRICK_ORE)) {
			list.srl[1]++;
			list.srl[2]++;
		}
		if (goods.containsKey(Resource.BRICK_STONE_ORE_WOOD)) {
			list.srl[1]++;
			list.srl[2]++;
			list.srl[3]++;
			list.srl[4]++;
		}
		if (goods.containsKey(Resource.STONE_BRICK)) {
			list.srl[1]++;
			list.srl[3]++;
		}
		if (goods.containsKey(Resource.STONE_WOOD)) {
			list.srl[3]++;
			list.srl[4]++;
		}
		if (goods.containsKey(Resource.ORE_STONE)) {
			list.srl[2]++;
			list.srl[3]++;
		}
		if (goods.containsKey(Resource.WOOD_BRICK)) {
			list.srl[1]++;
			list.srl[4]++;
		}
		if (goods.containsKey(Resource.WOOD_ORE)) {
			list.srl[2]++;
			list.srl[4]++;
		}
		if (goods.containsKey(Resource.PAPYRUS_GLASS_CLOTH)) {
			list.srl[5]++;
			list.srl[6]++;
			list.srl[7]++;
		}

		list.srl[0] = list.srl[1] + list.srl[2] + list.srl[3] + list.srl[4]
				+ list.srl[5] + list.srl[6] + list.srl[7];

		return list;
	}
	/**
	 * @author wanting
	 * Print the DisCarded card
	 */
	private void getDiscarded() {
		LinkedList<Card> myCard = KernelManager.getCardManager().getDiscardedCards2();
		Iterator<Card> it = myCard.iterator();
		int i = 1;
		while(it.hasNext()) {
			Card card = it.next();
			System.out.println("DisCard" + i + ":" + card.getName());
			++i;
		}
	}
}
