package SuperstitioMod.powers;

import SuperstitioMod.SuperstitioModSetup;
import SuperstitioMod.powers.interFace.OnOrgasm;
import SuperstitioMod.utils.PowerUtility;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SexualHeat extends AbstractPower {
    public static final String POWER_ID = SuperstitioModSetup.MakeTextID(SexualHeat.class.getSimpleName() + "Power");
    public static final int HeatReduce_PerCard_Origin = 6;
    public static final int HEAT_REQUIREDOrigin = 10;
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final int DRAW_CARD_INContinueOrgasm = 1;
    //绘制相关
    private static final float BAR_HEIGHT = 20.0f * Settings.scale;
    private static final float BG_OFFSET_X = 31.0f * Settings.scale;
    private static final float BAR_OFFSET_Y = -28.0f * Settings.scale;
    private static final float TEXT_OFFSET_Y = 11.0f * Settings.scale;
    private static final Color PINK = new Color(1f, 0.7529f, 0.7961f, 1.0f);
    private static final Color BarTextColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    private final Map<UUID, Integer> costMap = new HashMap<>();
    public Color barBgColor;
    public Color barShadowColor;
    public Color barTextColor;
    public Color barOrginColor;
    public Color barOrgasmShadowColor;
    public int orgasmTime = 0;
    private int HeatReduce_PerCard = HeatReduce_PerCard_Origin;
    private int heatRequired = HEAT_REQUIREDOrigin;

    public SexualHeat(final AbstractCreature owner, final int amount) {
        this.name = SexualHeat.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        if (this.owner.isPlayer)
            this.type = PowerType.BUFF;
        else
            this.type = PowerType.DEBUFF;

        this.amount = amount;
        if (this.owner.powers.stream().noneMatch(abstractPower -> Objects.equals(abstractPower.ID, SexualHeat.POWER_ID)))
            CheckOrgasm();

        // 添加一大一小两张能力图
        String path128 = SuperstitioModSetup.makeImgFilesPath_Power("default84");
        String path48 = SuperstitioModSetup.makeImgFilesPath_Power("default32");
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();

        this.hitbox = new Hitbox(this.owner.hb.width + BAR_HEIGHT * 3f, BAR_HEIGHT * 1.5f);
        this.hitbox.move(this.owner.hb.cX, this.owner.hb.cY + this.owner.hb.height + BAR_OFFSET_Y);

        this.barBgColor = new Color(0.0f, 0.0f, 0.0f, 0.3f);
        this.barShadowColor = this.barBgColor;
        this.barTextColor = BarTextColor;
        this.barOrginColor = PINK;
        this.barOrgasmShadowColor = Color.YELLOW;
    }

    public boolean isInOrgasm() {
        return orgasmTime != 0;
    }

    private float barWidth() {
        return this.owner.hb.width * (this.amount % getHeatRequired()) / getHeatRequired();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        this.hitbox.update();
        if (this.hitbox.hovered) {
            TipHelper.renderGenericTip(this.hitbox.cX + 96.0F * Settings.scale,
                    this.hitbox.cY + 64.0F * Settings.scale, this.name, this.description);
        }

        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

//    @SpirePatch(clz = AbstractPlayer.class, method = "render", paramtypes = {"com.badlogic.gdx.graphics.g2d" +
//            ".SpriteBatch"})
//    public static class PowerRenderPatch {
//        public static void Postfix(final AbstractPlayer this.owner, final SpriteBatch sb) {
//            Optional<AbstractPower> sexualHeatPowerOp =
//                    this.owner.powers.stream().filter(power -> Objects.equals(power.ID, SexualHeat.POWER_ID))
//                    .findFirst();
//            if (!sexualHeatPowerOp.isPresent()) return;
//            if (!(sexualHeatPowerOp.get() instanceof SexualHeat)) return;
//
//            SexualHeat sexualHeatPower = (SexualHeat) sexualHeatPowerOp.get();
//
//
//        }
//    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        float OwnerX = this.owner.hb.cX - this.owner.hb.width / 2.0F;
        float OwnerY = this.owner.hb.cY + this.owner.hb.height;
        this.renderAmountBarBackGround(sb, OwnerX, OwnerY);
        this.renderAmountBar(sb, OwnerX, OwnerY);
        this.renderOrgasmText(sb, OwnerY);
    }

    @Override
    public void onRemove() {
        this.EndOrgasm();
    }

    private void renderAmountBarBackGround(final SpriteBatch sb, final float x, final float y) {
        if (this.isInOrgasm()) {
            sb.setColor(this.barOrgasmShadowColor);
        } else {
            sb.setColor(this.barShadowColor);
        }
        sb.draw(ImageMaster.HB_SHADOW_L, x - BAR_HEIGHT, y - BG_OFFSET_X + 3.0f * Settings.scale, BAR_HEIGHT, BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_B, x, y - BG_OFFSET_X + 3.0f * Settings.scale, this.owner.hb.width, BAR_HEIGHT);
        sb.draw(ImageMaster.HB_SHADOW_R, x + this.owner.hb.width, y - BG_OFFSET_X + 3.0f * Settings.scale, BAR_HEIGHT, BAR_HEIGHT);
        sb.setColor(this.barBgColor);
        if (this.amount == getHeatRequired()) {
            return;
        }
        sb.draw(ImageMaster.HEALTH_BAR_L, x - BAR_HEIGHT, y + BAR_OFFSET_Y, BAR_HEIGHT, BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + BAR_OFFSET_Y, this.owner.hb.width, BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.owner.hb.width, y + BAR_OFFSET_Y, BAR_HEIGHT, BAR_HEIGHT);

    }

    private void renderOrgasmText(final SpriteBatch sb, final float y) {
        final float tmp = this.barTextColor.a;
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.amount + "/" + getHeatRequired() + "(" + this.orgasmTime + ")",
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

    @Override
    public void updateDescription() {
        if (this.owner.isPlayer)
            this.description = String.format(SexualHeat.powerStrings.DESCRIPTIONS[0], getHeatRequired());
        else
            this.description = String.format(SexualHeat.powerStrings.DESCRIPTIONS[1], getHeatRequired());
        if (this.isInOrgasm()) {
            this.description = this.description + String.format(SexualHeat.powerStrings.DESCRIPTIONS[2], this.orgasmTime);
        }
    }

    @Override
    public void stackPower(final int stackAmount) {
//        AbstractPower power = this;
//        String name = this.name;
//        this.addToTop(new AbstractGameAction() {
//            @Override
//            public void update() {
//                this.isDone = true;
//                PowerUtility.BubbleMessage(power, false, name);
//            }
//        });
        if (this.amount < 0)
            this.amount = 0;
        super.stackPower(stackAmount);
        CheckOrgasm();
        //updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
//        AbstractPower power = this;
//        String name = this.name;
//        this.addToTop(new AbstractGameAction() {
//            @Override
//            public void update() {
//                this.isDone = true;
//                PowerUtility.BubbleMessage(power, true, name);
//            }
//        });
        if (this.amount < 0)
            this.amount = 0;
        super.reducePower(reduceAmount);
        CheckEndOrgasm();
        //updateDescription();
    }

    private void CheckOrgasm() {
        this.owner.powers.forEach(power -> {
            if (!(power instanceof OnOrgasm))
                return;
            OnOrgasm onOrgasmPower = (OnOrgasm) power;
            onOrgasmPower.onCheckOrgasm(this);
        });

        int shouldOrgasm = this.amount / getHeatRequired();
        if (orgasmTime >= shouldOrgasm) return;
        this.StartOrgasm();
    }

    private void StartOrgasm() {
        boolean shouldOrgasm = true;
        for (AbstractPower abstractPower : this.owner.powers) {
            if (!(abstractPower instanceof OnOrgasm))
                continue;
            OnOrgasm onOrgasmPower = (OnOrgasm) abstractPower;
            if (onOrgasmPower.shouldOrgasm(this))
                continue;
            shouldOrgasm = false;
            break;
        }
        if (!shouldOrgasm)
            return;

        Orgasm();
        this.owner.powers.forEach(power -> {
            if (!(power instanceof OnOrgasm))
                return;
            OnOrgasm onOrgasmPower = (OnOrgasm) power;
            onOrgasmPower.afterOrgasm(this);
        });

        CheckOrgasm();
    }

    private void Orgasm() {
        //this.flash();
        orgasmTime++;
        boolean IsContinueOrgasm = orgasmTime > 1;
        if (IsContinueOrgasm)
            this.addToBot(new DrawCardAction(DRAW_CARD_INContinueOrgasm));
        AbstractPower power = this;
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                this.isDone = true;
                PowerUtility.BubbleMessageHigher(power, false, powerStrings.DESCRIPTIONS[IsContinueOrgasm ? 4 :
                        3]);
            }
        });
        if (this.owner.isPlayer)
            HandCardsCheaper();
        else
            this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StunMonsterPower((AbstractMonster) this.owner)));
    }

    private void CheckEndOrgasm() {
        if (amount <= 0)
            EndOrgasm();
    }

    private void EndOrgasm() {
        if (isInOrgasm())
            PowerUtility.BubbleMessageHigher(this, true, powerStrings.DESCRIPTIONS[5]);
        this.orgasmTime = 0;
        if (this.owner.isPlayer)
            HandCardsCostToOrigin();
    }

    private void HandCardsCheaper() {
        for (final AbstractCard card : AbstractDungeon.player.hand.group) {
            this.CardCostCheaper(card);
        }
        for (final AbstractCard card : AbstractDungeon.player.drawPile.group) {
            this.CardCostCheaper(card);
        }
        for (final AbstractCard card : AbstractDungeon.player.discardPile.group) {
            this.CardCostCheaper(card);
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (this.isInOrgasm() && this.owner.isPlayer)
            this.CardCostCheaper(card);
    }

//    /**
//     * 只会返回当前最高的连续高潮次数，直到清零。
//     */
//    public int getOrgasmTimes() {
//        return orgasmTime;
//    }

    private void CardCostCheaper(AbstractCard card) {
        if (card.costForTurn <= 0)
            return;
        if (costMap.keySet().stream().noneMatch(uuidInMap -> card.uuid == uuidInMap))
            costMap.put(card.uuid, card.costForTurn);
        final int newCost = getOriginCost(card) - this.orgasmTime;
        if (card.costForTurn == newCost)
            return;
        card.costForTurn = Math.max(newCost, 0);
        card.isCostModified = true;
        card.flash();
    }

    private void HandCardsCostToOrigin() {
        AbstractDungeon.player.hand.group.forEach(this::ACardCostToOrigin);
        AbstractDungeon.player.discardPile.group.forEach(this::ACardCostToOrigin);
        AbstractDungeon.player.drawPile.group.forEach(this::ACardCostToOrigin);
        costMap.clear();
    }

    private void ACardCostToOrigin(AbstractCard card) {
        if (card == null)
            return;
        if (!costMap.containsKey(card.uuid))
            return;
        card.flash();
        card.costForTurn = getOriginCost(card);
        card.isCostModified = false;
    }

    private int getOriginCost(AbstractCard card) {
        if (costMap.get(card.uuid) == null)
            return card.cost;
        return costMap.get(card.uuid);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster) {
        if (!this.isInOrgasm() || !this.owner.isPlayer)
            return;
        if (getOriginCost(card) < card.costForTurn)
            return;
        int reduceAmount = (getOriginCost(card) - card.costForTurn) * getHeatReduce_PerCard();
        if (reduceAmount <= 0)
            return;
        ACardCostToOrigin(card);
        Squirt(reduceAmount);
    }

    private void Squirt(int reduceAmount) {
        this.owner.powers.forEach(power -> {
            if (!(power instanceof OnOrgasm))
                return;
            OnOrgasm onOrgasmPower = (OnOrgasm) power;
            onOrgasmPower.beforeSquirt(this);
        });
        PowerUtility.BubbleMessageHigher(this, true, powerStrings.DESCRIPTIONS[6]);
        this.addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, reduceAmount));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    private int getHeatRequired() {
        return heatRequired;
    }

    public void setHeatRequired(int heatRequired) {
        if (heatRequired > 0)
            this.heatRequired = heatRequired;
        else
            this.heatRequired = 1;
    }

    public int getHeatReduce_PerCard() {
        return HeatReduce_PerCard;
    }

    public void setHeatReduce_PerCard(int heatReduce_PerCard) {
        HeatReduce_PerCard = heatReduce_PerCard;
    }
}