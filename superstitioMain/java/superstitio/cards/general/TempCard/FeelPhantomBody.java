package superstitio.cards.general.TempCard;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import superstitio.DataManager;
import superstitio.cards.general.AbstractTempCard;
import superstitioapi.utils.ActionUtility;

public class FeelPhantomBody extends AbstractTempCard {
    public static final String ID = DataManager.MakeTextID(FeelPhantomBody.class);

    public static final CardType CARD_TYPE = CardType.STATUS;

    public static final CardRarity CARD_RARITY = CardRarity.SPECIAL;

    public static final CardTarget CARD_TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    private final AbstractCard sealCard;

    public FeelPhantomBody() {
        this(null);
    }

    public FeelPhantomBody(AbstractCard sealCard) {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.sealCard = sealCard;
        this.cardsToPreview = sealCard;
        this.setupMagicNumber(MAGIC, UPGRADE_MAGIC);
        CardModifierManager.addModifier(this, new ExhaustMod());
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot_drawCards();
        addToBot(new LoseHPAction(ActionUtility.getRandomMonsterSafe(), AbstractDungeon.player, this.magicNumber));
    }

    @Override
    public void triggerOnExhaust() {
//        this.dontTriggerOnUseCard = true;
    }

    @Override
    public void moveToDiscardPile() {
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        if (sealCard == null) return;
        ActionUtility.addToBot_makeTempCardInBattle(sealCard, ActionUtility.BattleCardPlace.Discard);
    }

    @Override
    public void upgradeAuto() {
    }
}