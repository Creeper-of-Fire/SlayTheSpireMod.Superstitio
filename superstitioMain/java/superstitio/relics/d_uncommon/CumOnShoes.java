package superstitio.relics.d_uncommon;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import superstitio.DataManager;
import superstitio.cards.general.FuckJob_Card;
import superstitio.relics.SuperstitioRelic;
import superstitio.relics.interFace.Countup;
import superstitioapi.utils.ActionUtility;


public class CumOnShoes extends SuperstitioRelic implements Countup {
    public static final String ID = DataManager.MakeTextID(CumOnShoes.class);
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;
    private static final int NeedAmount = 10;

    public CumOnShoes() {
        super(ID, RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void atBattleStart() {
        setCounter(Countup.Zero);
    }

    @Override
    public void updateDescriptionArgs() {
        setDescriptionArgs(NeedAmount);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c instanceof FuckJob_Card)
            CountAdd();
    }

    @Override
    public void onCountMax() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        ActionUtility.addToBot_applyPower(new DexterityPower(AbstractDungeon.player, 1));
    }

    @Override
    public int getMaxNum() {
        return NeedAmount;
    }
}