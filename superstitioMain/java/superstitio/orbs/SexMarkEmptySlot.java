package superstitio.orbs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import superstitio.DataManager;
import superstitio.customStrings.stringsSet.OrbStringsSet;

import static superstitio.orbs.AbstractLupaOrb.getPowerStringsWithSFW;

public class SexMarkEmptySlot extends EmptyOrbSlot {
    public static final String ORB_ID = DataManager.MakeTextID(SexMarkEmptySlot.class);
    protected final OrbStringsSet orbStringsSet = getPowerStringsWithSFW(ORB_ID);

    public SexMarkEmptySlot() {
        this(AbstractDungeon.player.drawX + AbstractDungeon.player.hb_x,
                AbstractDungeon.player.drawY + AbstractDungeon.player.hb_y + AbstractDungeon.player.hb_h / 2.0F);
    }

    public SexMarkEmptySlot(float x, float y) {
        super(x, y);
        this.ID = ORB_ID;
        this.name = orbStringsSet.getNAME();
        this.updateDescription();
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void update() {
    }

    @Override
    public void updateDescription() {
        this.description = "";
    }

    @Override
    public AbstractOrb makeCopy() {
        return new SexMarkEmptySlot();
    }
}
