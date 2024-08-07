package superstitio.cards.general.SkillCard.drawCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import superstitio.DataManager;
import superstitio.cards.general.GeneralCard;
import superstitioapi.hangUpCard.CardOrb;
import superstitioapi.hangUpCard.Card_TriggerHangCardManually;
import superstitioapi.hangUpCard.HangUpCardGroup;

public class CalmDown extends GeneralCard implements Card_TriggerHangCardManually {
    public static final String ID = DataManager.MakeTextID(CalmDown.class);

    public static final CardType CARD_TYPE = CardType.SKILL;

    public static final CardRarity CARD_RARITY = CardRarity.COMMON;

    public static final CardTarget CARD_TARGET = CardTarget.SELF;

    private static final int COST = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_MAGIC = 1;
    private static final int ExtraDrawNum = 1;


    private static final int HeatReduce = 6;


    public CalmDown() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupMagicNumber(MAGIC, UPGRADE_MAGIC);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        HangUpCardGroup.forEachHangUpCard((cardGroup, cardOrb) -> {
            if (cardOrb.ifShouldRemove()) return;
            cardOrb.setTriggerDiscardIfMoveToDiscard();
            cardOrb.setShouldRemove();
            addToBot_drawCards();
        }).addToBotAsAbstractAction();
        if (this.magicNumber >= 1)
            addToBot_drawCards(this.magicNumber);
    }

    @Override
    public void upgradeAuto() {
    }

    @Override
    public boolean forceFilterCardOrbToHoveredMode(CardOrb orb) {
        orb.targetType = CardOrb.HangOnTarget.None;
        orb.actionType = CardOrb.HangEffectType.Bad;
        return true;
    }

    @Override
    public int forceChangeOrbCounterShown(CardOrb orb) {
        return 0;
    }
}

