package superstitioapi.utils;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.ApologySlime;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import superstitioapi.Logger;
import superstitioapi.actions.AutoDoneInstantAction;
import superstitioapi.powers.AllCardCostModifier;

import java.util.ArrayList;
import java.util.Optional;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.currMapNode;

public class ActionUtility {

    public static void addToBot_applyPower(final AbstractPower power) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(power.owner, AbstractDungeon.player, power));
    }

    public static void addToBot_applyPower(final AbstractPower power, final AbstractCreature source) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(power.owner, source, power));
    }

    public static void addToTop_applyPower(final AbstractPower power) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(power.owner, AbstractDungeon.player, power));
    }

//    public static void addToBot_reducePower(final AbstractPower power, final AbstractCreature source) {
//        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(power.owner, source, power, power.amount));
//    }
    //不知道为什么这段代码不起作用，开摆
    //但是：this.addToBot(new ReducePowerAction(this.owner, this.owner, power, 1));这段代码就有用

    public static void addToBot_reducePower(
            final String powerId, final int amount, final AbstractCreature target, final AbstractCreature source) {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, source, powerId, amount));
    }

    public static void addToBot_removeSpecificPower(final AbstractPower power, final AbstractCreature source) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(power.owner, source, power));
    }

    public static void addToBot_removeSpecificPower(
            final String powerId, final AbstractCreature target, final AbstractCreature source) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, source, powerId));
    }

    public static void addToBot_makeTempCardInBattle(AbstractCard card, BattleCardPlace battleCardPlace, int amount) {
        addToBot_makeTempCardInBattle(card, battleCardPlace, amount, false);
    }

    public static AbstractMonster getRandomMonsterSafe() {
        final AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m != null && !m.isDeadOrEscaped() && !m.isDead) {
            return m;
        }
        return null;
    }

    public static AbstractMonster getRandomMonsterWithoutRngSafe() {
        final AbstractMonster m = currMapNode.room.monsters.getRandomMonster(null, true, new Random());
        if (m != null && !m.isDeadOrEscaped() && !m.isDead) {
            return m;
        }
        return null;
    }

    public static ArrayList<AbstractMonster> getMonsters() {
        return AbstractDungeon.getMonsters().monsters;
    }

    public static AbstractMonster[] getAllAliveMonsters() {
        AbstractMonster[] monsters = getMonsters().stream().filter(ActionUtility::isAlive).toArray(AbstractMonster[]::new);
        if (monsters.length == 0) {
            Logger.warning("no monsters alive, all monsters: " + getMonsters().stream().findAny().orElse(null));
            return new AbstractMonster[]{new ApologySlime()};
        }
        return monsters;
    }

    public static void addToBot_makeTempCardInBattle(AbstractCard card, BattleCardPlace battleCardPlace, int amount,
                                                     boolean upgrade) {
        if (upgrade)
            card.upgrade();
        switch (battleCardPlace) {
            case Hand:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, amount));
                break;
            case DrawPile:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, amount, true, true));
                break;
            case Discard:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(card, amount));
                break;
        }

        AutoDoneInstantAction.addToBotAbstract(() -> {
            Optional<AllCardCostModifier> power = AllCardCostModifier.getActivateOne();
            power.ifPresent(AllCardCostModifier::tryUseEffect);
        });
    }

    public static void addToBot_makeTempCardInBattle(AbstractCard card, BattleCardPlace battleCardPlace) {
        addToBot_makeTempCardInBattle(card, battleCardPlace, 1);
    }

    public static void addToBot_makeTempCardInBattle(AbstractCard card, BattleCardPlace battleCardPlace, boolean upgrade) {
        addToBot_makeTempCardInBattle(card, battleCardPlace, 1, upgrade);
    }

    public static boolean isAlive(final AbstractCreature c) {
        return c != null && !c.isDeadOrEscaped() && !c.isDead;
    }

    public static void addEffect(final AbstractGameEffect effect) {
        AbstractDungeon.effectList.add(effect);
    }

    public static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public enum BattleCardPlace {
        Hand, DrawPile, Discard
    }

    public interface VoidSupplier {
        void get();
    }

    public interface FunctionReturnSelfType {
        FunctionReturnSelfType get();
    }
}