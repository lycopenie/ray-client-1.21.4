package ray4rc.rayclient.ui.screens.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.modules.Mod;

import java.awt.*;

public class ModuleButton {
    public Mod module;
    public Frame parent;
    public int offset;

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final TextRenderer tr = mc.textRenderer;

    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (!isHovered(mouseX, mouseY)) {
            context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0, 0, 0, 120).getRGB());
        } else {
            context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0, 0, 0, 200).getRGB());
        }

        context.drawText(tr, module.getName(), parent.x + 2, parent.y + offset + 2, module.isEnabled() ? Color.red.getRGB() : -1, false);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else {
                // settings stuff idk
            }
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + parent.height + offset;
    }
}
