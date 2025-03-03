package ray4rc.rayclient.ui.screens.clickgui.setting;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.modules.settings.Setting;
import ray4rc.rayclient.ui.screens.clickgui.ModuleButton;



public class Component {
    protected static MinecraftClient mc = MinecraftClient.getInstance();
    protected static TextRenderer tr = mc.textRenderer;

    public ModuleButton parent;
    public Setting setting;
    public int offset;

    public Component(Setting setting, ModuleButton parent, int offset) {
        this.parent = parent;
        this.setting = setting;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }

    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    public boolean isHovered(double mouseX, double mouseY) {
        int left = parent.parent.x;
        int top = parent.parent.y + parent.offset + offset;
        int right = left + parent.parent.width;
        int bottom = top + parent.parent.height;

        return mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
    }
}
