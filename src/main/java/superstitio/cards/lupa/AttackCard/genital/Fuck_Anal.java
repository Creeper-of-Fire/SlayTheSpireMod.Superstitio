package superstitio.cards.lupa.AttackCard.genital;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import superstitio.DataManager;
import superstitio.cards.lupa.AbstractLupaCard_FuckJob;
import superstitio.powers.SexualDamage;

import static superstitio.actions.AutoDoneInstantAction.addToBotAbstract;

public class Fuck_Anal extends AbstractLupaCard_FuckJob {
    public static final String ID = DataManager.MakeTextID(Fuck_Anal.class.getSimpleName());

    public static final CardType CARD_TYPE = CardType.ATTACK;

    public static final CardRarity CARD_RARITY = CardRarity.COMMON;

    public static final CardTarget CARD_TARGET = CardTarget.ENEMY;


    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;

    public Fuck_Anal() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupDamage(DAMAGE, UPGRADE_DAMAGE);
        this.setupMagicNumber(MAGIC, UPGRADE_MAGIC);
    }

    @Override
    public void upgradeAuto() {
    }

    @Override
    public void use(final AbstractPlayer player, final AbstractMonster monster) {
        addToBot_dealDamage(monster);
        addToBotAbstract(() ->
                monster.powers.stream().filter(power -> power instanceof SexualDamage).map(power -> (SexualDamage) power)
                        .findAny().ifPresent(sexualDamage -> addToBot_applyPower(
                                new SexualDamage(monster, (int) (sexualDamage.amount * 0.1 * this.magicNumber), AbstractDungeon.player))));
        AbstractLupaCard_FuckJob.addToTop_gainSexMark_Inside(this.name);
    }
}