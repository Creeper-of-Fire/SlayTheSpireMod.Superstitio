package SuperstitioMod.powers;

import SuperstitioMod.SuperstitioModSetup;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Samsara extends AbstractPower {
    public static final String POWER_ID = SuperstitioModSetup.MakeTextID(Samsara.class.getSimpleName() + "Power");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public Samsara(final AbstractCreature owner) {
        this.name = Samsara.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.type = PowerType.BUFF;

        this.amount = -1;
        // 添加一大一小两张能力图
        String path128 = SuperstitioModSetup.makeImgFilesPath_Power("default84");
        String path48 = SuperstitioModSetup.makeImgFilesPath_Power("default32");
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();

    }


    @Override
    public void updateDescription() {
        this.description = String.format(Samsara.powerStrings.DESCRIPTIONS[0]);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        this.addToBot(new DrawCardAction(1));
    }
}