package superstitio.cards.maso.PowerCard;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import superstitio.DataManager;
import superstitio.cards.SuperstitioCard;
import superstitio.cards.general.TempCard.FeelPhantomBody;
import superstitio.cards.maso.MasoCard;
import superstitio.powers.EasyBuildAbstractPowerForPowerCard;
import superstitioapi.actions.AutoDoneInstantAction;
import superstitioapi.utils.ActionUtility;
import superstitioapi.utils.CardUtility;

import static superstitioapi.utils.ActionUtility.addToBot_makeTempCardInBattle;


public class OrgasmWithLostBody extends MasoCard {
    public static final String ID = DataManager.MakeTextID(OrgasmWithLostBody.class);

    public static final CardType CARD_TYPE = CardType.POWER;

    public static final CardRarity CARD_RARITY = CardRarity.RARE;

    public static final CardTarget CARD_TARGET = CardTarget.SELF;

    private static final int COST = 1;

    public OrgasmWithLostBody() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.cardsToPreview = new FeelPhantomBody();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot_applyPower(new OrgasmWithLostBodyPower());
    }

    @Override
    public void upgradeAuto() {
        this.upgradeCardsToPreview();
    }

    public static class OrgasmWithLostBodyPower extends EasyBuildAbstractPowerForPowerCard {
        public OrgasmWithLostBodyPower() {
            super(-1);
        }

        private static boolean hasEnoughEnergyOrTurnEnd(AbstractCard card) {
            if (AbstractDungeon.actionManager.turnHasEnded) {
                return false;
            }
            return EnergyPanel.totalCount >= card.costForTurn || card.freeToPlay() || card.isInAutoplay;
        }

        @Override
        public void onCardDraw(AbstractCard card) {
            super.onCardDraw(card);
            if (CardUtility.isNotInBattle()) return;
            tryBecomeFeelPhantomBodyCard(card);
        }

        private void tryBecomeFeelPhantomBodyCard(AbstractCard card) {
            if (card.canUse(AbstractDungeon.player, null)) return;
            //不是因为能量不够或者对象不对而无法打出
//            if (!(card.cardPlayable(null) && hasEnoughEnergyOrTurnEnd(card))) return;
            //似乎检测对象是否正确会导致攻击牌出问题，所以只加了这个检测，但是可能会引发其他错误
            if (!(hasEnoughEnergyOrTurnEnd(card))) return;
            AutoDoneInstantAction.addToBotAbstract(() -> {
                AbstractDungeon.player.hand.removeCard(card);
                AbstractDungeon.effectList.add(new PurgeCardEffect(card));
            });
            addToBot_makeTempCardInBattle(new FeelPhantomBody(card), ActionUtility.BattleCardPlace.Hand);
        }

        @Override
        public void updateDescriptionArgs() {
        }

        @Override
        protected SuperstitioCard makePowerCard() {
            return new OrgasmWithLostBody();
        }
    }
}
