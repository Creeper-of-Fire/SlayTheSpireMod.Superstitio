package superstitio.powers;

import superstitio.powers.interfaces.InvisiblePower_StillRenderAmount;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;

public abstract class AbstractWithBarPower extends AbstractLupaPower implements InvisiblePower_StillRenderAmount {
    //绘制相关
    private static final float BAR_HEIGHT = 20.0f * Settings.scale;
    private static final float BG_OFFSET_X = 31.0f * Settings.scale;
    private static final float BAR_OFFSET_Y = -28.0f * Settings.scale;
    private static final float TEXT_OFFSET_Y = 11.0f * Settings.scale;
    public Color barBgColor;
    public Color barShadowColor;
    public Color barTextColor;
    public Color barOrginColor;
    public int orgasmTime = 0;
    public Hitbox hitbox;

    public AbstractWithBarPower(String id, final AbstractCreature owner, int amount, PowerType type, boolean needUpdateDescription) {
        super(id, owner, amount, type, needUpdateDescription);

        this.hitbox = new Hitbox(this.owner.hb.width + BAR_HEIGHT * 3f, BAR_HEIGHT * 1.5f);
        this.hitbox.move(this.owner.hb.cX, Height() + this.owner.hb.cY + this.owner.hb.height + BAR_OFFSET_Y);

        this.barBgColor = setupBarBgColor();
        this.barShadowColor = setupBarShadowColor();
        this.barTextColor = setupBarTextColor();
        this.barOrginColor = setupBarOrginColor();
    }

    protected abstract float Height();

    protected abstract Color setupBarBgColor();

    protected abstract Color setupBarShadowColor();

    protected abstract Color setupBarTextColor();

    protected abstract Color setupBarOrginColor();

    protected float barWidth() {
        float v = this.owner.hb.width * (this.amount % maxBarAmount()) / maxBarAmount();
        if (this.amount % maxBarAmount() == 0 && this.amount != 0)
            v = this.owner.hb.width;
        return v;
    }

    protected abstract int maxBarAmount();

    @Override
    public void update(int slot) {
        super.update(slot);
        update_showTips(this.hitbox);
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        float OwnerX = this.owner.hb.cX - this.owner.hb.width / 2.0F;
        float OwnerY = Height() + this.owner.hb.cY + this.owner.hb.height;
        this.renderAmountBarBackGround(sb, OwnerX, OwnerY);
        this.renderAmountBar(sb, OwnerX, OwnerY);
        this.renderBarText(sb, OwnerY);
    }

    private void renderAmountBarBackGround(final SpriteBatch sb, final float x, final float y) {
        sb.setColor(this.barShadowColor);
        sb.draw(ImageMaster.HB_SHADOW_L, x - BAR_HEIGHT, y - BG_OFFSET_X + 3.0f * Settings.scale, BAR_HEIGHT,
                BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, y - BG_OFFSET_X + 3.0f * Settings.scale, this.owner.hb.width,
                BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + this.owner.hb.width, y - BG_OFFSET_X + 3.0f * Settings.scale,
                BAR_HEIGHT, BAR_HEIGHT);
        sb.setColor(this.barBgColor);
        sb.draw(ImageMaster.HEALTH_BAR_L, x - BAR_HEIGHT, y + BAR_OFFSET_Y, BAR_HEIGHT, BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + BAR_OFFSET_Y, this.owner.hb.width, BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.owner.hb.width, y + BAR_OFFSET_Y, BAR_HEIGHT, BAR_HEIGHT);

    }

    private void renderBarText(final SpriteBatch sb, final float y) {
        final float tmp = this.barTextColor.a;
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont,
                this.amount + "/" + maxBarAmount() + "(" + this.orgasmTime + ")",
                this.owner.hb.cX, y + BAR_OFFSET_Y + TEXT_OFFSET_Y, this.barTextColor);
        this.barTextColor.a = tmp;
    }

    private void renderAmountBar(final SpriteBatch sb, final float x, final float y) {
        sb.setColor(this.barOrginColor);
        sb.draw(ImageMaster.HEALTH_BAR_L, x - BAR_HEIGHT, y + BAR_OFFSET_Y, BAR_HEIGHT,
                BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + BAR_OFFSET_Y, this.barWidth(), BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.barWidth(), y + BAR_OFFSET_Y, BAR_HEIGHT,
                BAR_HEIGHT);
    }

}