package superstitio.cards.general.PowerCard.hangUpCard;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import superstitio.DataManager;
import superstitio.cards.general.GeneralCard;
import superstitio.powers.AbstractSuperstitioPower;
import superstitio.powers.SexualHeat;
import superstitio.powers.patchAndInterface.interfaces.orgasm.OnOrgasm_onOrgasm;
import superstitioapi.hangUpCard.HangUpCardGroup;


public class FastWindUp extends GeneralCard {
    public static final String ID = DataManager.MakeTextID(FastWindUp.class);

    public static final CardType CARD_TYPE = CardType.POWER;

    public static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;

    public static final CardTarget CARD_TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int COST_UPGRADED_NEW = 0;
    private static final int MAGIC = 1;

    public FastWindUp() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupMagicNumber(MAGIC);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot_applyPower(new FastWindUpPower(AbstractDungeon.player, this.magicNumber));
    }

    @Override
    public void upgradeAuto() {
        upgradeBaseCost(COST_UPGRADED_NEW);
    }

    public static class FastWindUpPower extends AbstractSuperstitioPower implements OnOrgasm_onOrgasm {
        public static final String POWER_ID = DataManager.MakeTextID(FastWindUpPower.class);

        public FastWindUpPower(final AbstractCreature owner, int amount) {
            super(POWER_ID, owner, amount);
        }

        @Override
        public void onOrgasm(SexualHeat SexualHeatPower) {
            for (int i = 0; i < this.amount; i++) {
                this.flash();
                HangUpCardGroup.forEachHangUpCard(orb ->
                                orb.forceAcceptAction(new FastWindUp()))
                        .addToBotAsAbstractAction();
            }
        }

        @Override
        public void updateDescriptionArgs() {
            setDescriptionArgs(this.amount);
        }
    }
}

