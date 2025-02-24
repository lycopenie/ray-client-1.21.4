package ray4rc.rayclient.ui.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import ray4rc.rayclient.modules.Mod;
import ray4rc.rayclient.modules.ModuleManager;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class HUD {
    private static MinecraftClient mc = MinecraftClient.getInstance();
    private static float totalTimeSeconds = 0.0f;
    private static TextRenderer tr = mc.textRenderer;

    public static void drawString(DrawContext context, String string, int x, int y, boolean shadow) {
        int timeOffset = x+y;
        int letterOffset = 0;

        // First letter
        context.drawText(tr, string.substring(0, 1), x, y, Color.getHSBColor((totalTimeSeconds - timeOffset)/180, 1, 1).getRGB(), shadow);

        // The rest
        for (int i = 1; i < string.length(); i++) {
            char[] stringArray = string.toCharArray();
            String letter = String.valueOf(stringArray[i]);
            String prevLetter = String.valueOf(stringArray[i-1]);
            int prevLetterWidth = tr.getWidth(prevLetter);

            letterOffset += prevLetterWidth;
            timeOffset += prevLetterWidth;

            context.drawText(tr, letter, x + letterOffset, y, Color.getHSBColor((totalTimeSeconds - timeOffset)/180, 1, 1).getRGB(), shadow);
        }
    }


    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        totalTimeSeconds += tickCounter.getLastFrameDuration();
        drawString(context, "Ray4rc Client | FPS: " + mc.getCurrentFps(), 10, 10, false);

        int index = 0;

        List<Mod> enabled = ModuleManager.INSTANCE.getEnabledModules();

        enabled.sort(Comparator.comparing(m -> (int)tr.getWidth(((Mod)m).getName())).reversed());

        for (Mod module : enabled) {
            drawString(context, module.getName(), 10, 20 + index*10, false);

            index += 1;
        }
    }
}