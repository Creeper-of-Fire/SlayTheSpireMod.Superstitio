package superstitio.cards.maso.PowerCard;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import superstitio.DataManager;
import superstitio.cards.maso.MasoCard;
import superstitio.cards.maso.PowerCard.monsterGirl.FishGirlMode;

import java.util.ArrayList;


public class MonsterGirlMode extends MasoCard {
    public static final String ID = DataManager.MakeTextID(MonsterGirlMode.class);

    public static final CardType CARD_TYPE = CardType.POWER;

    public static final CardRarity CARD_RARITY = CardRarity.RARE;

    public static final CardTarget CARD_TARGET = CardTarget.SELF;

    private static final int COST = 2;

    public MonsterGirlMode() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        final ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new FishGirlMode());
        if (this.upgraded) {
            for (final AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }
        this.addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public void upgradeAuto() {
    }
}
