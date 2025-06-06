package superstitio.cards.general.TempCard

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower
import superstitio.DataManager
import superstitio.SPTT_Color
import superstitio.cards.SetCardColor
import superstitio.cards.general.AbstractTempCard
import superstitioapi.cards.addExhaustMod
import superstitioapi.hangUpCard.CardOrb
import superstitioapi.hangUpCard.Card_TriggerHangCardManually
import superstitioapi.hangUpCard.HangUpCardGroup
@SetCardColor(SPTT_Color.TempColor)
class GiveBirth() : AbstractTempCard(ID, CARD_TYPE, COST, CARD_RARITY, CARD_TARGET), Card_TriggerHangCardManually
{
    var sealPower: MutableList<AbstractPower> = ArrayList()
    var sealMonster: AbstractCreature? = null

    init
    {
        this.addExhaustMod()
        this.setupBlock(BLOCK, UPGRADE_PLUS_BLOCK)
    }

    constructor(sealPower: MutableList<AbstractPower>, sealMonster: AbstractCreature?) : this()
    {
        this.sealPower = sealPower
        if (sealMonster != null)
        {
            this.sealMonster = sealMonster
            this.name = this.originalName + ": " + sealMonster.name
        }
    }

    interface IPregnantCardOrb

    override fun use(player: AbstractPlayer?, monster: AbstractMonster?)
    {
        this.addToBot(AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, this.block))
        HangUpCardGroup.forEachHangUpCard {
            if (it !is IPregnantCardOrb)
                return@forEachHangUpCard
            if (it.ifShouldRemove())
                return@forEachHangUpCard
            it.setTriggerDiscardIfMoveToDiscard()
            it.setShouldRemove()
            this@GiveBirth.addToBot(AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, this.block))
        }.addToBotAsAbstractAction()
    }

    override fun forceFilterCardOrbToHoveredMode(orb: CardOrb): Boolean
    {
        if (orb !is IPregnantCardOrb)
            return false
        orb.targetType = CardOrb.HangOnTarget.None
        orb.actionType = CardOrb.HangEffectType.Bad
        return true
    }

    override fun forceChangeOrbCounterShown(orb: CardOrb): Int
    {
        return if (orb is IPregnantCardOrb)
            0
        else
            orb.orbCounter.toInt()
    }

    override fun upgradeAuto()
    {
    }

    override fun makeCopy(): AbstractCard
    {
        val newCard = super.makeCopy() as? GiveBirth?
        if (newCard != null)
        {
            newCard.sealMonster = this.sealMonster
            newCard.sealPower = this.sealPower
            return newCard
        }
        else
            return super.makeCopy()
    }

    companion object
    {
        val ID: String = DataManager.MakeTextID(GiveBirth::class.java)

        val CARD_TYPE: CardType = CardType.SKILL

        val CARD_RARITY: CardRarity = CardRarity.SPECIAL

        val CARD_TARGET: CardTarget = CardTarget.SELF

        private const val COST = 0
        private const val BLOCK = 5
        private const val UPGRADE_PLUS_BLOCK = 3
    }
}
