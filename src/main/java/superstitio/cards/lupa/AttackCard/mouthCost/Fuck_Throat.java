package superstitio.cards.lupa.AttackCard.mouthCost;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import superstitio.DataManager;
import superstitio.InBattleDataManager;
import superstitio.cards.lupa.AbstractLupaCard_FuckJob;

public class Fuck_Throat extends AbstractLupaCard_FuckJob {
    public static final String ID = DataManager.MakeTextID(Fuck_Throat.class.getSimpleName());

    public static final CardType CARD_TYPE = CardType.ATTACK;

    public static final CardRarity CARD_RARITY = CardRarity.COMMON;

    public static final CardTarget CARD_TARGET = CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MagicNum = 2;

    public Fuck_Throat() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupDamage(DAMAGE, UPGRADE_DAMAGE);
        this.setupMagicNumber(MagicNum);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot_dealDamage(monster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        InBattleDataManager.getHangUpCardOrbGroup().ifPresent(group -> {
            if (group.hasOrb())
                addToBot(new GainEnergyAction(this.magicNumber));
        });
        AbstractLupaCard_FuckJob.addToTop_gainSexMark_Inside(this.name);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        InBattleDataManager.getHangUpCardOrbGroup().ifPresent(group -> {
            if (group.hasOrb())
                this.glowColor = Color.PINK.cpy();
        });
    }

    @Override
    public void upgradeAuto() {
    }
}