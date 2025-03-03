package ray4rc.rayclient.ui.screens.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.Mod.Category;
import ray4rc.rayclient.modules.ModuleManager;
import ray4rc.rayclient.ui.screens.clickgui.setting.Component;

import java.awt.*;
import java.util.ArrayList;

public class Frame {
    public int x, y, width, height, dragX, dragY;
    public Category category;

    public boolean dragging;
    public boolean extended;

    private ArrayList<ModuleButton> buttons;

    protected MinecraftClient mc = MinecraftClient.getInstance();
    protected TextRenderer tr = mc.textRenderer;

    public Frame(Category category, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = false;
        this.category = category;

        buttons = new ArrayList<>();

        int offset = height;
        for (Mod mod : ModuleManager.INSTANCE.getModulesInCategory(category)) {
            buttons.add(new ModuleButton(mod, this, offset));
            offset += height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
        context.drawText(tr, category.name(), x + 2, y + 2, Color.WHITE.getRGB(), false);

        if (extended) {
            for (ModuleButton button : buttons) {
                button.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
             if (button == 0) {
                 dragging = true;
                 dragX = (int) (mouseX - x);
                 dragY = (int) (mouseY - y);
             } else if (button == 1) {
                 extended = !extended;
             }

        }


        for (ModuleButton mb : buttons) {
            mb.mouseClicked(mouseX, mouseY, button);
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

    public void updateButtons() {
        int offset = height;

        for (ModuleButton button : buttons) {
            button.offset = offset;
            offset += height;

            if (button.extended) {
                for (Component component: button.components) {
                    if (component.setting.isVisible()) offset += height;
                }
            }

        }
    }
}
