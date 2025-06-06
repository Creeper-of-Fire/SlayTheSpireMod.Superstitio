package superstitio.cards.maso.BaseCard

import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier
import superstitio.DataManager
import superstitio.cards.IsMasoCard
import superstitio.cards.general.BaseCard.Invite
import superstitio.delayHpLose.DelayRemoveDelayHpLoseBlock

@IsMasoCard
class Invite_Maso : Invite(ID)
{
    override fun makeNewBlockType(): AbstractBlockModifier
    {
        return DelayRemoveDelayHpLoseBlock()
    }

    companion object
    {
        val ID: String = DataManager.MakeTextID(Invite::class.java.simpleName, Invite_Maso::class.java)
    }
}
