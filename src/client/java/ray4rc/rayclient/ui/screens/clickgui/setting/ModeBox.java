package ray4rc.rayclient.ui.screens.clickgui.setting;

import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.modules.settings.BooleanSetting;
import ray4rc.rayclient.modules.settings.ModeSetting;
import ray4rc.rayclient.modules.settings.Setting;
import ray4rc.rayclient.ui.screens.clickgui.ModuleButton;

import java.awt.*;

public class ModeBox extends Component {
    private ModeSetting modeSet = (ModeSetting) setting;
    public ModeBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.modeSet = (ModeSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int left = parent.parent.x;
        int top = parent.parent.y + parent.offset + offset;
        int right = left + parent.parent.width;
        int bottom = top + parent.parent.height;

        int charHeight = tr.fontHeight;
        int midCharYOffset = (bottom+top)/2 - charHeight/2;

        if (!isHovered(mouseX, mouseY)) {
            context.fill(left, top, right, bottom, new Color(0, 0, 0, 120).getRGB());
        } else {
            context.fill(left, top, right, bottom, new Color(0, 0, 0, 200).getRGB());
        }
        context.drawText(tr, modeSet.getName()+modeSet.getMode(), left + 2, midCharYOffset, Color.WHITE.getRGB(), false);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0)
            modeSet.cycle();
        super.mouseClicked(mouseX, mouseY, button);
    }
}
