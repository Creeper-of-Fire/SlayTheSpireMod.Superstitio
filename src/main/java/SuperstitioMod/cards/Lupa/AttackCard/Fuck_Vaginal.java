package SuperstitioMod.cards.Lupa.AttackCard;

import SuperstitioMod.SuperstitioModSetup;
import SuperstitioMod.cards.Lupa.AbstractLupaCard;
import SuperstitioMod.utils.CardUtility;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Fuck_Vaginal extends AbstractLupaCard {
    public static final String ID = SuperstitioModSetup.MakeTextID(Fuck_Vaginal.class.getSimpleName());

    public static final CardType CARD_TYPE = CardType.ATTACK;

    public static final CardRarity CARD_RARITY = CardRarity.COMMON;

    public static final CardTarget CARD_TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int Magic_Number = 3;
    public Fuck_Vaginal() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        setupDamage(ATTACK_DMG);
        setupMagicNumber(Magic_Number);
    }


    private int getOriginDamage() {
        if (this.upgraded)
            return ATTACK_DMG + UPGRADE_PLUS_DMG;
        else
            return ATTACK_DMG;
    }

    private void updateDamage() {
        int totalCost = AbstractDungeon.player.hand.group.stream()
                .filter(card -> card.costForTurn >= 1)
                .mapToInt(card -> card.costForTurn).sum();
        float damageDown = this.magicNumber;
        if (!AbstractDungeon.player.hand.group.isEmpty())
            damageDown = (float) totalCost * damageDown / AbstractDungeon.player.hand.group.size();
        if (damageDown * this.magicNumber >= 1)
            this.isDamageModified = true;
        this.baseDamage = (int) (this.getOriginDamage() - damageDown);

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    @Override
    public void use(final AbstractPlayer player, final AbstractMonster monster) {
        this.calculateCardDamage(null);
        damageToEnemy(monster,AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        CardUtility.gainSexMark_Inside(this.name);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.calculateCardDamage(null);
    }

    @Override
    public void onMoveToDiscard() {
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(final AbstractMonster monster) {
        updateDamage();
        super.calculateCardDamage(monster);
        this.initializeDescription();
    }
}