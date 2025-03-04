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

    private boolean sliding = false;

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

        context.fill(left, top, right, bottom, new Color(0, 0, 0, 120).getRGB());

        double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));
        int renderWidth = (int) (parent.parent.width * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));

        context.fill(left, top, left + renderWidth, bottom, new Color(255, 0, 0, 255).getRGB());

        if (sliding) {
            if (diff == 0) {
                numSet.setValue(numSet.getMin());
            } else {
                numSet.setValue(roundToPlace((diff/parent.parent.width) * (numSet.getMax()-numSet.getMin()) + numSet.getMin(), 2));
            }
        }


        context.drawText(tr, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2), left + 2, midCharYOffset, Color.WHITE.getRGB(), false);


        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY))
            sliding = true;
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding = false;
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
