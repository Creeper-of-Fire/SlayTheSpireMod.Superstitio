package superstitio.cards.general;

import superstitio.DataManager;
import superstitio.cards.CardOwnerPlayerManager;
import superstitio.cards.SuperstitioCard;

import static superstitio.cards.CardOwnerPlayerManager.*;


public abstract class AbstractTempCard extends SuperstitioCard implements IsNotLupaCard, IsNotMasoCard  {
    /**
     * @param id         卡牌ID
     * @param cardType   卡牌类型
     * @param cost       卡牌消耗
     * @param cardRarity 卡牌稀有度
     * @param cardTarget 卡牌目标
     */
    public AbstractTempCard(String id, CardType cardType, int cost, CardRarity cardRarity, CardTarget cardTarget) {
        this(id, cardType, cost, cardRarity, cardTarget, "special");
    }

    private AbstractTempCard(String id, CardType cardType, int cost, CardRarity cardRarity, CardTarget cardTarget, String imgSubFolder) {
        super(id, cardType, cost, cardRarity, cardTarget, DataManager.SPTT_DATA.TempCardEnums.TempCard_CARD, imgSubFolder);
    }
}