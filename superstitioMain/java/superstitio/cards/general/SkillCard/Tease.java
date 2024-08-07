package superstitio.cards.general.SkillCard;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import superstitio.DataManager;
import superstitio.cards.general.GeneralCard;
import superstitioapi.actions.AutoDoneInstantAction;

public class Tease extends GeneralCard {
    public static final String ID = DataManager.MakeTextID(Tease.class);

    public static final CardType CARD_TYPE = CardType.SKILL;

    public static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;

    public static final CardTarget CARD_TARGET = CardTarget.ENEMY;

    private static final int COST = 0;
    private static final int MAGIC = 15;
    private static final int UPGRADE_MAGIC = 5;

    public Tease() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupMagicNumber(MAGIC, UPGRADE_MAGIC);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int a = (int) (monster.maxHealth * MAGIC / 100f);
        AutoDoneInstantAction.addToBotAbstract(() ->
                monster.decreaseMaxHealth(a));
        addToBot_applyPower(new FrailPower(monster, 1, false));
        addToBot_applyPower(new WeakPower(monster, 1, false));
        addToBot(new GainBlockAction(monster, (int) (a * 0.75f)));//乐，实际上脆弱还是不起效
        addToBot_applyPower(new BarricadePower(monster));
    }

    @Override
    public void upgradeAuto() {
    }
}
