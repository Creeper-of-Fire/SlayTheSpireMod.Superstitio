package SuperstitioMod.cards.BlockMod;

import SuperstitioMod.DataManager;
import SuperstitioMod.cards.Abstract.AbstractLupaBlock;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class SexBlock extends AbstractLupaBlock {

    public static final String ID = DataManager.MakeTextID(SexBlock.class.getSimpleName());

    public SexBlock() {
        super(ID);
    }

    @Override
    public void onThisBlockDamaged(DamageInfo info, int lostAmount) {

    }

    @Override
    public AbstractBlockModifier makeCopy() {
        return new SexBlock();
    }

    @Override
    public Color blockImageColor() {
        return Color.PINK.cpy();
    }
}