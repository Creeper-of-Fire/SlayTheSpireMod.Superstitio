package superstitio.cards.general.PowerCard.drawAndEnergy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import superstitio.DataManager;
import superstitio.SuperstitioImg;
import superstitio.cards.general.GeneralCard;
import superstitio.powers.AbstractSuperstitioPower;
import superstitio.powers.SexualHeat;
import superstitio.powers.SexualHeatNeededModifier;
import superstitio.powers.patchAndInterface.interfaces.orgasm.OnOrgasm_onOrgasm;
import superstitioapi.actions.AutoDoneInstantAction;
import superstitioapi.cards.DamageActionMaker;
import superstitioapi.powers.interfaces.OnPostApplyThisPower;


public class ChokeChoker extends GeneralCard {
    public static final String ID = DataManager.MakeTextID(ChokeChoker.class);

    public static final CardType CARD_TYPE = CardType.POWER;

    public static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;

    public static final CardTarget CARD_TARGET = CardTarget.SELF;

    private static final int COST = 1;

    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;


    public ChokeChoker() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupMagicNumber(MAGIC, UPGRADE_MAGIC);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot_applyPower(new ChokeChokerPower(player, this.magicNumber));
    }

    @Override
    public void upgradeAuto() {
    }

    @SuperstitioImg.NoNeedImg
    public static class ChokeChokerPower extends AbstractSuperstitioPower implements
            OnOrgasm_onOrgasm, OnPostApplyThisPower {
        public static final String POWER_ID = DataManager.MakeTextID(ChokeChokerPower.class);
        public static final int ChokeAmount = 1;

        public ChokeChokerPower(final AbstractCreature owner, int amount) {
            super(POWER_ID, owner, amount);
            this.loadRegion("choke");
        }

        @Override
        public void atStartOfTurnPostDraw() {
            AddPowers();
        }

        @Override
        public void InitializePostApplyThisPower(AbstractPower addedPower) {
            if (addedPower instanceof ChokeChokerPower)
                AutoDoneInstantAction.addToBotAbstract(() -> ((ChokeChokerPower) addedPower).AddPowers());
        }

        public void AddPowers() {
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new SexualHeatNeededModifier(this.owner, amount)));
        }


        @Override
        public void onOrgasm(SexualHeat SexualHeatPower) {
            this.flash();
            DamageActionMaker.maker(this.owner, this.amount, this.owner)
                    .setEffect(AbstractGameAction.AttackEffect.NONE)
                    .setSuperFast(true)
                    .setDamageType(DataManager.CanOnlyDamageDamageType.NoTriggerMasoRelicDamageType)
                    .addToTop();
//            for (int i = 0; i < this.amount; i++) {
//                this.addToBot(new LoseHPAction(this.owner, null, 1));
//            }
//            this.addToBot(new LoseHPAction(this.owner, null, this.amount));
        }

        @Override
        public void updateDescriptionArgs() {
            this.setDescriptionArgs(amount, ChokeAmount);
        }
    }
}