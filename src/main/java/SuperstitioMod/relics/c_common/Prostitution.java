package SuperstitioMod.relics.c_common;

import SuperstitioMod.DataManager;
import SuperstitioMod.cards.Lupa.AbstractLupaCard_FuckJob;
import SuperstitioMod.relics.AbstractLupaRelic;
import SuperstitioMod.relics.interFace.Countup;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Prostitution extends AbstractLupaRelic implements Countup {
    public static final String ID = DataManager.MakeTextID(Prostitution.class.getSimpleName());
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.COMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;
    private static final int SexAmount = 5;
    private static final int CashAmount = 1;

    public Prostitution() {
        super(ID, RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void atBattleStart() {
        setCounter(Countup.Zero);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard instanceof AbstractLupaCard_FuckJob)
            CountAdd();
    }

    @Override
    public void updateDescriptionArgs() {
        setDescriptionArgs(SexAmount, CashAmount);
    }

    @Override
    public void onCountMax() {
        AbstractDungeon.player.gainGold(CashAmount);
    }

    @Override
    public int getMaxNum() {
        return SexAmount;
    }
}