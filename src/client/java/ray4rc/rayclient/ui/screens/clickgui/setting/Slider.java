package ray4rc.rayclient.ui.screens.clickgui.setting;

import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.modules.settings.NumberSetting;
import ray4rc.rayclient.modules.settings.Setting;
import ray4rc.rayclient.ui.screens.clickgui.ModuleButton;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component {
    private NumberSetting numSet = (NumberSetting) setting;

    public Slider(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numSet = (NumberSetting) setting;
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


        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
    }

    private double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
