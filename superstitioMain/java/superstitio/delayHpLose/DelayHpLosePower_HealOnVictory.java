package superstitio.delayHpLose;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import superstitio.DataManager;

public class DelayHpLosePower_HealOnVictory extends DelayHpLosePower_ApplyAtEndOfRound {
    public static final String POWER_ID = DataManager.MakeTextID(DelayHpLosePower_HealOnVictory.class);

    public DelayHpLosePower_HealOnVictory(AbstractCreature owner, int amount) {
        super(POWER_ID, owner, amount);
    }

    @Override
    public void onVictory() {
        this.isRemovedForApplyDamage = true;
//        AutoDoneInstantAction.addToTopAbstract(() -> {
        AbstractDungeon.effectsQueue.add(new HealEffect(owner.hb.cX - owner.animX, owner.hb.cY, amount));
        playRemoveEffect();
        this.amount = 0;
//        });
//        addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }
}