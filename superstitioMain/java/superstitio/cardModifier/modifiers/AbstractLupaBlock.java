package superstitio.cardModifier.modifiers;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import superstitio.DataManager;
import superstitio.customStrings.interFace.StringSetUtility;
import superstitio.customStrings.stringsSet.ModifierStringsSet;
import superstitioapi.utils.UpdateDescriptionAdvanced;

import java.util.ArrayList;

public abstract class AbstractLupaBlock extends AbstractBlockModifier implements UpdateDescriptionAdvanced {
    public final ModifierStringsSet blockStrings;
    TooltipInfo tooltip = null;
    private Object[] descriptionArgs;

    public AbstractLupaBlock(String id) {
        this.blockStrings = getBlockStringsWithSFW(id);
        this.automaticBindingForCards = true;
    }

    public static ModifierStringsSet getBlockStringsWithSFW(String cardName) {
        return StringSetUtility.getCustomStringsWithSFW(cardName, DataManager.modifiers, ModifierStringsSet.class);
    }

    public AbstractLupaBlock removeAutoBind() {
        this.automaticBindingForCards = false;
        return this;
    }

    @Override
    public final String getName() {
        return blockStrings.getNAME();
    }

    /**
     * 卡牌的描述
     */
    @Override
    public final ArrayList<TooltipInfo> getCustomTooltips() {
        if (tooltip == null) {
            tooltip = new TooltipInfo(blockStrings.getBasicInfo(), getDescription());
        }
        ArrayList<TooltipInfo> tooltipInfos = new ArrayList<>();
        tooltipInfos.add(tooltip);
        return tooltipInfos;
    }

    /**
     * 鼠标放到格挡上的描述
     */
    @Override
    public String getDescription() {
        return getFormattedDescription();
    }

    @Override
    public Object[] getDescriptionArgs() {
        return descriptionArgs;
    }

    @Override
    public final void setDescriptionArgs(Object... args) {
        if (args[0] instanceof Object[]) descriptionArgs = (Object[]) args[0];
        else descriptionArgs = args;
    }

    @Override
    public void updateDescriptionArgs() {
    }

    @Override
    public String getDescriptionStrings() {
        return this.blockStrings.getDESCRIPTION();
    }


    /**
     * 显示在卡牌的类型旁边的那玩意
     */
    @Override
    public final String getCardDescriptor() {
        return blockStrings.getNAME();
    }

    @Override
    public boolean isInherent() {
        return true;
    }
}
