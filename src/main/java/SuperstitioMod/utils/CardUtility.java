package SuperstitioMod.utils;

import SuperstitioMod.SuperstitioModSetup;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.List;
import java.util.stream.Collectors;

public class CardUtility {


    /**
     * @param ThisMod 当为true时， IsCardColorVanilla()被短路
     * @param Vanilla 当为true时， IsCardColorThisMod()被短路
     * @return 输入为true ture时，输出为满足两个条件之一的结果。输入为1true1false时，输出由true项决定。输入为false false时，输出两者都不满足的结果。
     */
    public static AbstractCard getRandomCurseCard(boolean ThisMod, boolean Vanilla) {
        List<AbstractCard> list = getCardsListForMod(ThisMod, Vanilla).stream()
                .filter(card -> card.type == AbstractCard.CardType.STATUS)
                .collect(Collectors.toList());
        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }

    public static List<AbstractCard> getCardsListForMod(boolean ThisMod, boolean Vanilla) {
        List<AbstractCard> list;
        list = CardLibrary.cards.values().stream()
                .filter(card -> {
                    if (ThisMod && Vanilla)
                        return !(!IsCardColorVanilla(card) && !IsCardColorThisMod(card));
                    else if (ThisMod)
                        return !IsCardColorVanilla(card) && IsCardColorThisMod(card);
                    else if (Vanilla)
                        return IsCardColorVanilla(card) && !IsCardColorThisMod(card);
                    else
                        return !IsCardColorVanilla(card) && !IsCardColorThisMod(card);
                })
                .collect(Collectors.toList());
        return list;
    }

    public static boolean IsCardColorVanilla(AbstractCard card) {
        return !card.cardID.contains(":");
    }

    public static boolean IsCardColorThisMod(AbstractCard card) {
        return !card.cardID.contains(SuperstitioModSetup.MakeTextID(""));
    }


}