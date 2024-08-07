package superstitio.cards.general.TempCard;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import superstitio.DataManager;
import superstitio.cards.general.AbstractTempCard;
import superstitio.cards.general.FuckJob_Card;
import superstitio.cards.maso.AttackCard.Fuck_Eye;
import superstitioapi.SuperstitioApiSetup;
import superstitioapi.utils.ActionUtility;

public class Fuck_Ear extends AbstractTempCard implements FuckJob_Card {
    public static final String ID = DataManager.MakeTextID(Fuck_Ear.class);

    public static final CardType CARD_TYPE = CardType.ATTACK;

    public static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;

    public static final CardTarget CARD_TARGET = CardTarget.ENEMY;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 1;

    public Fuck_Ear() {
        this(false);
        this.cardsToPreview = new Fuck_Eye(false);
    }

    public Fuck_Ear(boolean blank) {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        FuckJob_Card.initFuckJobCard(this);
        this.setupDamage(DAMAGE, UPGRADE_DAMAGE);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot_dealDamage(monster, SuperstitioApiSetup.DamageEffect.HeartMultiInOne);
        addToBot_dealDamage(monster, SuperstitioApiSetup.DamageEffect.HeartMultiInOne);
        ActionUtility.addToBot_makeTempCardInBattle(new Fuck_Eye(), ActionUtility.BattleCardPlace.Hand, this.upgraded);
        addToBot_applyPower(new FrailPower(AbstractDungeon.player, 1, false));

    }

//    @Override
//    public void triggerWhenDrawn() {
//        if (!CardModifierManager.hasModifier(this, RetainMod.ID))
//            CardModifierManager.addModifier(this, new RetainMod());
//    }

    @Override
    public void upgradeAuto() {
        upgradeCardsToPreview();
    }
}
