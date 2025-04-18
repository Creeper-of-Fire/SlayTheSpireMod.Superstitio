package superstitio.customStrings.stringsSet

import com.evacipated.cardcrawl.mod.stslib.Keyword
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.LocalizedStrings
import superstitio.DataManager
import superstitio.Logger
import superstitio.customStrings.interFace.*
import superstitio.customStrings.stringsSet.ModifierStringsSet.ModifierStrings
import java.util.stream.Collectors

class ModifierStringsSet : HasTextID, HasOriginAndSFWVersion<ModifierStrings>
{
    override val SfwVersion: ModifierStrings = ModifierStrings()
    override val OriginVersion: ModifierStrings = ModifierStrings()
    private var NAME: String? = null
    private var NAME_SFW: String? = null
    private var BASIC_INFO: String? = null
    private var BASIC_INFO_SFW: String? = null
    private var DESCRIPTION: String? = null
    private var DESCRIPTION_SFW: String? = null
    private var EXTENDED_DESCRIPTION: Array<String>? = null
    private var EXTENDED_DESCRIPTION_SFW: Array<String>? = null
    override var textID: String? = null

    fun getNAME(): String
    {
        return getFromRightVersion { strings: ModifierStrings? -> strings!!.NAME }!!
    }

    fun getDESCRIPTION(): String
    {
        return getFromRightVersion { strings: ModifierStrings? -> strings!!.DESCRIPTION }!!
    }

    fun getBasicInfo(): String
    {
        if (textID!!.endsWith("Block")) return CardCrawlGame.languagePack.getUIString(
            DataManager.MakeTextID(
                "BlockModifier"
            )
        ).TEXT[0] + getBasicInfo_Pure()
        if (textID!!.endsWith("Damage")) return CardCrawlGame.languagePack.getUIString(
            DataManager.MakeTextID(
                "DamageModifier"
            )
        ).TEXT[0] + getBasicInfo_Pure()
        return getBasicInfo_Pure()
    }

    private fun getBasicInfo_Pure(): String
    {
        return getFromRightVersion { strings: ModifierStrings? -> strings!!.BASIC_INFO }!!
    }

    fun getEXTENDED_DESCRIPTION(index: Int): String
    {
        val EXTENDED_DESCRIPTION =
            getArrayFromRightVersion { strings: ModifierStrings? -> strings!!.EXTENDED_DESCRIPTION }
        if (index < EXTENDED_DESCRIPTION!!.size)
            return EXTENDED_DESCRIPTION[index]
        else
        {
            Logger.warning("Can't find the index " + index + " in the EXTENDED_DESCRIPTION array of" + this.NAME)
            return ""
        }
        //        return getArrayFromRightVersion(strings -> strings.EXTENDED_DESCRIPTION);
    }

    fun ToKeyWord(): Keyword
    {
        val keyword = Keyword()
        keyword.PROPER_NAME = this.getBasicInfo()
        keyword.NAMES = arrayOf(this.getNAME(), this.getBasicInfo_Pure())
        keyword.DESCRIPTION = this.getDESCRIPTION()
        return keyword
    }

    private fun toModifierNameReplaceRule(): WordReplace
    {
        return WordReplace(this.NAME, this.NAME_SFW)
    }

    override fun initialSelfBlack()
    {
        this.NAME = "[MISSING_TITLE]"
        this.DESCRIPTION = "[MISSING_DESCRIPTION]"
        this.BASIC_INFO = "[MISSING_DESCRIPTION+]"
        this.EXTENDED_DESCRIPTION = LocalizedStrings.createMockStringArray(10)
    }

    override fun initialOrigin(origin: ModifierStrings)
    {
        origin.NAME = NAME
        origin.BASIC_INFO = BASIC_INFO
        origin.DESCRIPTION = DESCRIPTION
        origin.EXTENDED_DESCRIPTION = EXTENDED_DESCRIPTION
    }

    override fun initialSFW(sfw: ModifierStrings)
    {
        sfw.NAME = NAME_SFW
        sfw.BASIC_INFO = BASIC_INFO_SFW
        sfw.DESCRIPTION = DESCRIPTION_SFW
        sfw.EXTENDED_DESCRIPTION = EXTENDED_DESCRIPTION_SFW
    }

    override fun getRightVersion(): ModifierStrings
    {
        if (StringSetUtility.shouldReturnSFWVersion(SfwVersion.NAME)) return SfwVersion
        return OriginVersion
    }

    override fun getSubClass() = ModifierStrings::class.java

    override fun setupSFWStringByWordReplace(replaceRules: List<WordReplace>)
    {
        SfwVersion.NAME =
            updateFieldIfEmpty(SfwVersion.NAME, OriginVersion.NAME, replaceRules)
        SfwVersion.BASIC_INFO =
            updateFieldIfEmpty(SfwVersion.BASIC_INFO, OriginVersion.BASIC_INFO, replaceRules)
        SfwVersion.DESCRIPTION =
            updateFieldIfEmpty(SfwVersion.DESCRIPTION, OriginVersion.DESCRIPTION, replaceRules)
        SfwVersion.EXTENDED_DESCRIPTION =
            updateFieldIfEmpty(SfwVersion.EXTENDED_DESCRIPTION, OriginVersion.EXTENDED_DESCRIPTION, replaceRules)

        this.NAME_SFW = this.NAME_SFW.takeIfNullOrEmpty(SfwVersion.NAME)
        this.DESCRIPTION_SFW = this.DESCRIPTION_SFW.takeIfNullOrEmpty(SfwVersion.DESCRIPTION)
        this.BASIC_INFO_SFW = this.BASIC_INFO_SFW.takeIfNullOrEmpty(SfwVersion.BASIC_INFO)
        this.EXTENDED_DESCRIPTION_SFW = this.EXTENDED_DESCRIPTION_SFW.takeIfNullOrEmpty(SfwVersion.EXTENDED_DESCRIPTION)
    }

    override fun makeCopy(): HasDifferentVersionStringSet<ModifierStrings>
    {
        val clone = ModifierStringsSet()
        clone.NAME = this.NAME
        clone.DESCRIPTION = this.DESCRIPTION
        clone.BASIC_INFO = this.BASIC_INFO
        clone.EXTENDED_DESCRIPTION = this.EXTENDED_DESCRIPTION
        clone.NAME_SFW = this.NAME_SFW
        clone.DESCRIPTION_SFW = this.DESCRIPTION_SFW
        clone.BASIC_INFO_SFW = this.BASIC_INFO_SFW
        clone.EXTENDED_DESCRIPTION_SFW = this.EXTENDED_DESCRIPTION_SFW
        return clone
    }

    override fun makeSFWCopy(): HasDifferentVersionStringSet<ModifierStrings>
    {
        val clone: HasDifferentVersionStringSet<ModifierStrings> = this.makeCopy()
        if (clone is ModifierStringsSet)
        {
            clone.NAME = null
            clone.DESCRIPTION = null
            clone.BASIC_INFO = null
            clone.EXTENDED_DESCRIPTION = null
        }
        return clone
    }

    class ModifierStrings
    {
        var NAME: String? = null
        var BASIC_INFO: String? = null
        var DESCRIPTION: String? = null
        var EXTENDED_DESCRIPTION: Array<String>? = null
    }

    companion object
    {
        fun makeModifierNameReplaceRules(cards: List<ModifierStringsSet>): List<WordReplace>
        {
            return cards.stream().map(ModifierStringsSet::toModifierNameReplaceRule)
                .collect(Collectors.toList())
        }

        fun MakeKeyWords(): Array<Keyword>
        {
            return DataManager.modifiers.values
                .map(ModifierStringsSet::ToKeyWord).toTypedArray()
        }
    }
}
