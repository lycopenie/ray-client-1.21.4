package ray4rc.rayclient.ui.screens.clickgui;

import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.modules.Mod;

public class ModuleButton {
    public Mod module;
    public Frame parent;
    public int offset;

    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
//        context.fill();
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + parent.height + offset;
    }
}
