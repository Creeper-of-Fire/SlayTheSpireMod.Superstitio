package superstitio.cards.maso.SkillCard;

import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.SelfOrEnemyTargeting;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import superstitio.DataManager;
import superstitio.cardModifier.modifiers.block.PregnantBlock_sealPower;
import superstitio.cards.general.TempCard.GiveBirth;
import superstitio.cards.general.TempCard.SelfReference;
import superstitio.cards.maso.MasoCard;
import superstitioapi.actions.AutoDoneInstantAction;
import superstitioapi.utils.ActionUtility;

import java.util.ArrayList;

import static superstitioapi.utils.ActionUtility.addToBot_makeTempCardInBattle;
import static superstitioapi.utils.CardUtility.getSelfOrEnemyTarget;


//TODO 增加一个按照怪物体型获得格挡的效果
public class UnBirth extends MasoCard {
    public static final String ID = DataManager.MakeTextID(UnBirth.class);

    public static final CardType CARD_TYPE = CardType.SKILL;

    public static final CardRarity CARD_RARITY = CardRarity.UNCOMMON;

    public static final CardTarget CARD_TARGET = SelfOrEnemyTargeting.SELF_OR_ENEMY;

    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 3;

    public UnBirth() {
        super(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET);
        this.setupBlock(BLOCK, UPGRADE_BLOCK, new PregnantBlock_sealPower(new ArrayList<>(), null).removeAutoBind());
        this.cardsToPreview = new GiveBirth();
        //this.exhaust = true;
    }

    private static ArrayList<AbstractPower> doSealPowers(ArrayList<AbstractPower> monster) {
        ArrayList<AbstractPower> sealPower = new ArrayList<>();
        monster.forEach(power -> {
            if (power instanceof WeakPower || power instanceof VulnerablePower || power instanceof FrailPower || power instanceof ArtifactPower) {
                if (power instanceof InvisiblePower) return;
                power.owner = AbstractDungeon.player;
                power.amount = power.amount * 2;
                sealPower.add(power);
                AutoDoneInstantAction.addToBotAbstract(() -> monster.remove(power));
            }
        });
        return sealPower;
    }

    private void ForPlayer(AbstractPlayer player) {
        ArrayList<AbstractPower> sealPower = doSealPowers(player.powers);
        addToBot_gainCustomBlock(new PregnantBlock_sealPower(sealPower, player));
        this.exhaust = true;
        addToBot_makeTempCardInBattle(new SelfReference(), ActionUtility.BattleCardPlace.Hand, upgraded);
    }

    private void ForMonsterBrokenSpaceStructure(AbstractMonster monster) {
        ArrayList<AbstractPower> sealPower = doSealPowers(monster.powers);
        addToBot_gainCustomBlock(new PregnantBlock_sealPower(sealPower, monster));
        this.exhaust = true;
        addToBot_makeTempCardInBattle(new SelfReference(), ActionUtility.BattleCardPlace.Hand, upgraded);
    }

    private void ForMonster(AbstractMonster monster) {
        ArrayList<AbstractPower> sealPower = doSealPowers(monster.powers);
        addToBot_gainCustomBlock(new PregnantBlock_sealPower(sealPower, monster));
        addToBot_makeTempCardInBattle(new GiveBirth(), ActionUtility.BattleCardPlace.Discard, upgraded);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractCreature target = getSelfOrEnemyTarget(this, monster);
        if (target instanceof AbstractPlayer)
            ForPlayer(AbstractDungeon.player);
        else if (BlockModifierManager.blockInstances(target).stream()
                .anyMatch(blockInstance -> blockInstance.getBlockTypes().stream()
                        .filter(blockModifier -> blockModifier instanceof PregnantBlock_sealPower)
                        .anyMatch(blockModifier -> ((PregnantBlock_sealPower) blockModifier).sealCreature == monster)))
            ForMonsterBrokenSpaceStructure((AbstractMonster) target);
        else
            ForMonster((AbstractMonster) target);
    }

    @Override
    public void upgradeAuto() {
        upgradeCardsToPreview();
    }

    enum MonsterBodyType {
        Tiny,
        Small,
        Middle,
        Big,
        VeryBig
    }

//    MonsterBodyType getMonsterBodyType(AbstractCreature creature){
//        if (creature.maxHealth < 10)
//            return MonsterBodyType.Tiny;
//        if (creature.maxHealth < 20)
//            return MonsterBodyType.Middle
//
//    }
}