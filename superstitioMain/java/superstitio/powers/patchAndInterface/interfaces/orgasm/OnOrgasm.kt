package superstitio.powers.patchAndInterface.interfaces.orgasm

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import superstitio.powers.SexualHeat
import superstitioapi.hangUpCard.HangUpCardGroup
import superstitioapi.relicToBlight.InfoBlight
import superstitioapi.utils.CardUtility

interface OnOrgasm
{
    /**
     * 检测高潮时的钩子
     */
    fun onCheckOrgasm(SexualHeatPower: SexualHeat)
    {
    }

    /**
     * 高潮时的优先处理
     */
    fun onOrgasmFirst(SexualHeatPower: SexualHeat)
    {
    }

    /**
     * 高潮时的处理
     */
    fun onOrgasm(SexualHeatPower: SexualHeat)
    {
    }

    /**
     * 连续高潮时的额外处理
     */
    fun onContinuallyOrgasm(SexualHeatPower: SexualHeat)
    {
    }

    /**
     * 高潮结束后的处理
     */
    fun onEndOrgasm(SexualHeatPower: SexualHeat)
    {
    }


    /**
     * 调用时已经判断高潮成立，如果返回true则禁止本次高潮
     */
    fun preventOrgasm(SexualHeatPower: SexualHeat): Boolean
    {
        return false
    }

    /**
     * 潮吹之前
     */
    fun onSquirt(SexualHeatPower: SexualHeat, card: AbstractCard)
    {
    }

    fun onSuccessfullyPreventOrgasm(SexualHeatPower: SexualHeat)
    {
    }

    companion object
    {
        fun AllOnOrgasm(owner: AbstractCreature): Sequence<OnOrgasm>
        {
            val onOrgasms =
                owner.powers.filterIsInstance<OnOrgasm>().toMutableList()
            if (owner.isPlayer)
            {
                onOrgasms.addAll(
                    InfoBlight.getAllRelics(AbstractRelic::class.java).filterIsInstance<OnOrgasm>()
                )
                onOrgasms.addAll(
                    AbstractDungeon.player.blights.filterIsInstance<OnOrgasm>()
                )
                onOrgasms.addAll(
                    CardUtility.AllCardInBattle().filterIsInstance<OnOrgasm>()
                )
                onOrgasms.addAll(
                    AbstractDungeon.player.relics.filterIsInstance<OnOrgasm>()
                )
                onOrgasms.addAll(
                    HangUpCardGroup.getEachCardOrb().filterIsInstance<OnOrgasm>()
                )
            }
            return onOrgasms.asSequence()
        }
    }
}
