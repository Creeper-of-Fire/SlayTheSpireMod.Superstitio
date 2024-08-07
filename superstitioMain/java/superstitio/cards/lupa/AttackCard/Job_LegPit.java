package superstitio.cards.lupa.AttackCard;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import superstitio.DataManager;
import superstitio.cards.general.FuckJob_Card;
import superstitio.cards.lupa.LupaCard;
import superstitio.powers.SexualHeat;
import superstitioapi.SuperstitioApiSetup;
import superstitioapi.actions.AutoDoneInstantAction;

public class Job_LegPit extends LupaCard implements FuckJob_Card {
    public static final String ID = DataManager.MakeTextID(Job_LegPit.class);

    public static final CardType CARD_TYPE = CardType.ATTACK;

    public static final CardRarity CARD_RARITY = CardRarity.COMMON;

    public static final CardTarget CARD_TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;

    private static final int Attack_Num = 2;

    public Job_LegPit() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        FuckJob_Card.initFuckJobCard(this);
        this.setupDamage(DAMAGE, UPGRADE_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < Attack_Num; i++) {
            addToBot_dealDamage(monster, SuperstitioApiSetup.DamageEffect.HeartMultiInOne);
            AutoDoneInstantAction.addToBotAbstract(() -> {
                if (monster.lastDamageTaken > 0 && SexualHeat.isInOrgasm(AbstractDungeon.player))
                    addToBot_drawCards();
            });
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        if (SexualHeat.Orgasm.isPlayerInOrgasm()) {
            this.glowColor = Color.PINK.cpy();
        }
    }


    @Override
    public void upgradeAuto() {
    }

}
