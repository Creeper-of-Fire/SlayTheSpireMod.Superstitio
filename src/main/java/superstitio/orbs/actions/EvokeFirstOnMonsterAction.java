package superstitio.orbs.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import superstitio.orbs.orbgroup.OrbGroup;

public class EvokeFirstOnMonsterAction extends AbstractGameAction {
    public OrbGroup target;

    public EvokeFirstOnMonsterAction(final OrbGroup target, final int amt) {
        this.target = target;
        this.amount = amt;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            for (int i = 0; i < this.amount; ++i) {
                target.evokeFirstOrb();
            }
        }
        this.tickDuration();
    }
}