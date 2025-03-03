package ray4rc.rayclient.ui.screens.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import ray4rc.rayclient.RayClientClient;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.settings.BooleanSetting;
import ray4rc.rayclient.modules.settings.ModeSetting;
import ray4rc.rayclient.modules.settings.NumberSetting;
import ray4rc.rayclient.modules.settings.Setting;
import ray4rc.rayclient.ui.screens.clickgui.setting.Checkbox;
import ray4rc.rayclient.ui.screens.clickgui.setting.Component;
import ray4rc.rayclient.ui.screens.clickgui.setting.ModeBox;
import ray4rc.rayclient.ui.screens.clickgui.setting.Slider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ModuleButton {
    public Mod module;
    public Frame parent;
    public int offset;
    public boolean extended;

    public List<Component> components;

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final TextRenderer tr = mc.textRenderer;

    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
        this.components = new ArrayList<>();

        int setOffset = parent.height;

        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                components.add(new Checkbox(setting, this, setOffset));
            } else if (setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, this, offset));
            } else if (setting instanceof NumberSetting) {
                components.add(new Slider(setting, this, offset));
            }
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int left = parent.x;
        int top = parent.y + offset;
        int right = left + parent.width;
        int bottom = top + parent.height;

        if (!isHovered(mouseX, mouseY)) {
            context.fill(left, top, right, bottom, new Color(0, 0, 0, 120).getRGB());
        } else {
            context.fill(left, top, right, bottom, new Color(0, 0, 0, 200).getRGB());
        }
        context.drawText(tr, module.getName(), left + 2, top + 2, module.isEnabled() ? Color.red.getRGB() : -1, false);

        if (extended) {
            for (Component component : components) {
                component.render(context, mouseX, mouseY, delta);
            }
        }

    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                extended = !extended;
                parent.updateButtons();
            }
        }

        for (Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        if (!parent.extended) {
            return false;
        }
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + parent.height + offset;
    }
}
