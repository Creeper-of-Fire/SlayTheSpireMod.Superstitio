package SuperstitioMod.cards.Lupa.BaseCard;

import SuperstitioMod.SuperstitioModSetup;
import SuperstitioMod.cards.Lupa.AbstractLupaCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HandJob extends AbstractLupaCard {
    public static final String ID = SuperstitioModSetup.MakeTextID(HandJob.class.getSimpleName());

    public static final CardType CARD_TYPE = CardType.ATTACK;

    public static final CardRarity CARD_RARITY = CardRarity.BASIC;

    public static final CardTarget CARD_TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    public HandJob() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET,"base");
        //添加基础攻击标签和将伤害设为6
        this.tags.add(CardTags.STARTER_STRIKE);
        this.setupDamage(ATTACK_DMG);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        damageToEnemy(monster, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}