package superstitio.powers.lupaOnly;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import superstitio.SuperstitioModSetup;
import superstitioapi.powers.barIndepend.BarRenderOnThing_Vertical;
import superstitioapi.powers.barIndepend.HasBarRenderOnCreature;
import superstitioapi.powers.barIndepend.HasBarRenderOnCreature_Power;

import java.util.function.Supplier;

public interface HasBarRenderOnCreature_SemenPower extends HasBarRenderOnCreature_Power, SemenPower {
    static Color semenColor() {
        if (SuperstitioModSetup.getEnableSFW())
            return Color.RED.cpy();
        else
            return Color.WHITE.cpy();
    }

    static BarRenderOnThing_Vertical makeNewBar_BodySemen(Supplier<Hitbox> hitbox, HasBarRenderOnCreature power) {
        BarRenderOnThing_Vertical bar = new BarRenderOnThing_Vertical(hitbox, power);
        bar.barTextColor = semenColor();
        return bar;
    }

    @Override
    default float Height() {
        return 170 * Settings.scale;
    }

    @Override
    default int getAmountForDraw() {
        return HasBarRenderOnCreature_Power.super.getAmountForDraw() * getSemenValue();
    }

    @Override
    default String uuidPointTo() {
        return "SemenOutside";
    }
}