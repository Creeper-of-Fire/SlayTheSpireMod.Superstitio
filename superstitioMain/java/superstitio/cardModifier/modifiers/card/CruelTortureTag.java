package superstitio.cardModifier.modifiers.card;

import com.megacrit.cardcrawl.cards.AbstractCard;
import superstitio.DataManager;
import superstitio.customStrings.stringsSet.UIStringsSet;

public class CruelTortureTag extends AbstractCardTagModifier {
    public static final String ID = DataManager.MakeTextID(CruelTortureTag.class);
    private static final UIStringsSet uiStrings = CruelTortureTag.getUIStringsWithSFW(ID);


    @Override
    protected UIStringsSet getUiStrings() {
        return uiStrings;
    }

    @Override
    protected String getID() {
        return ID;
    }

    @Override
    protected AbstractCard.CardTags getTag() {
        return DataManager.CardTagsType.CruelTorture;
    }
}