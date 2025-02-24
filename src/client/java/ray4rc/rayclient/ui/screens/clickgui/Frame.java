package ray4rc.rayclient.ui.screens.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.modules.Mod.Category;

import java.awt.*;

public class Frame {
    public int x, y, width, height, dragX, dragY;
    public Category category;

    public boolean dragging;

    private MinecraftClient mc = MinecraftClient.getInstance();
    private TextRenderer tr = mc.textRenderer;

    public Frame(Category category, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.category = category;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
        context.drawText(tr, category.name(), x + 2, y + 2, Color.WHITE.getRGB(), false);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            dragging = true;
            dragX = (int) (mouseX - x);
            dragY = (int) (mouseY - y);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging == true) {
            dragging = false;
        }
    }


    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void updatePosition(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);

        }
    }
}
