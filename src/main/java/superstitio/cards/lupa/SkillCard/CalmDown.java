package superstitio.cards.lupa.SkillCard;

import superstitio.DataManager;
import superstitio.InBattleDataManager;
import superstitio.cards.lupa.AbstractLupaCard;
import superstitio.powers.SexualHeat;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CalmDown extends AbstractLupaCard {
    public static final String ID = DataManager.MakeTextID(CalmDown.class.getSimpleName());

    public static final CardType CARD_TYPE = CardType.SKILL;

    public static final CardRarity CARD_RARITY = CardRarity.COMMON;

    public static final CardTarget CARD_TARGET = CardTarget.SELF;

    private static final int COST = 0;
    private static final int MagicNumber = 1;
    private static final int UPGRADE_MagicNumber = 1;
    private static final int ExtraDrawNum = 1;


    private static final int HeatReduce = 6;


    public CalmDown() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupMagicNumber(MagicNumber);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        this.addToBot_drawCards(magicNumber);
        this.addToBot_reducePowerToPlayer(SexualHeat.POWER_ID, HeatReduce);
        if (InBattleDataManager.InOrgasm) {
            this.addToBot_drawCards(ExtraDrawNum);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        if (InBattleDataManager.InOrgasm) {
            this.glowColor = Color.PINK.cpy();
        }
    }

    @Override
    public void upgradeAuto() {
        upgradeMagicNumber(UPGRADE_MagicNumber);
    }
}
