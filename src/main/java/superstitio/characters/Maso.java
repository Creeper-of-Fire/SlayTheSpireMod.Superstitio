package superstitio.characters;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import superstitio.DataManager;
import superstitio.cards.CardOwnerPlayerManager;
import superstitio.relics.a_starter.JokeDescription;
import superstitio.relics.a_starter.Maso.DevaBody_Masochism;
import superstitio.relics.a_starter.Maso.MasochismMode;
import superstitio.relics.a_starter.StartWithSexToy;

import java.util.ArrayList;

import static superstitio.DataManager.SPTT_DATA.MasoEnums.MASO_CARD;
import static superstitio.DataManager.SPTT_DATA.MasoEnums.MASO_Character;

// 继承CustomPlayer类
public class Maso extends BaseCharacter {
    public static final String ID = DataManager.MakeTextID("Maso");

    public Maso(String name) {
        super(ID, name, MASO_Character);
    }


    @Override
    // 初始遗物
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(StartWithSexToy.ID);
        retVal.add(JokeDescription.ID);
        retVal.add(DevaBody_Masochism.ID);
        retVal.add(MasochismMode.ID);
//        retVal.add(SorM.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], // 人物名字
                characterStrings.TEXT[0], // 人物介绍
                60, // 当前血量
                60, // 最大血量
                0, // 初始充能球栏位
                99, // 初始携带金币
                5, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }

    @Override
    protected boolean cardFilter(AbstractCard card) {
        return CardOwnerPlayerManager.isMasoCard(card);
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    @Override
    public AbstractCard.CardColor getCardColor() {
        return MASO_CARD;
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new Maso(this.name);
    }
}