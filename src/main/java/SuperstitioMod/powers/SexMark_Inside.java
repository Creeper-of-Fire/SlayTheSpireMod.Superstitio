package SuperstitioMod.powers;

import SuperstitioMod.SuperstitioModSetup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SexMark_Inside extends SexMark {
    public static final String POWER_ID = SuperstitioModSetup.MakeTextID(SexMark_Inside.class.getSimpleName() +
            "Power");
    public static final int MARKNeeded = 5;

    public static final int AOEDamage = 10;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public SexMark_Inside(final AbstractCreature owner, final String sexName) {
        super(powerStrings.NAME, POWER_ID, owner, sexName);
    }

    @Override
    protected void Trigger() {
        super.Trigger();
        this.addToBot(new DamageAllEnemiesAction(
                AbstractDungeon.player,
                AOEDamage,
                DamageInfo.DamageType.HP_LOSS,
                AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    protected float Height() {
        return this.owner.hb.cY + SexMark.BAR_RADIUS + SexMark.BAR_Blank;
    }

    @Override
    public void updateDescription() {
        StringBuilder names = new StringBuilder();
        for (String sexName : this.sexNames) {
            names.append(sexName);
        }
        this.description = String.format(SexMark_Inside.powerStrings.DESCRIPTIONS[0], names, MARKNeeded, AOEDamage);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
}