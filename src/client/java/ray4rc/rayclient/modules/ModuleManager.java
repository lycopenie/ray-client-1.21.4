package ray4rc.rayclient.modules;

import ray4rc.rayclient.modules.combat.*;
import ray4rc.rayclient.modules.movement.Scaffold;
import ray4rc.rayclient.modules.movement.Sprint;

import java.util.ArrayList;
import java.util.List;

import ray4rc.rayclient.modules.Mod.Category;
import ray4rc.rayclient.modules.player.AutoPearlFlash;
import ray4rc.rayclient.modules.player.AutoSwitch;
import ray4rc.rayclient.modules.player.AutoTotem;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private List<Mod> modules = new ArrayList<>();
    public ModuleManager() {
        addModules();
    }

    public List<Mod> getModules() {
        return modules;
    }

    public <T extends Mod> T getModule(Class<T> mod) {
        for (Mod module: modules) {
            if (mod.isInstance(module)) {
                return (T) module;
            }
        }
        return null;
    }

    public List<Mod> getEnabledModules() {
        List<Mod> enabled = new ArrayList<>();
        for (Mod module: modules) {
            if (module.isEnabled()) enabled.add(module);
        }

        return enabled;
    }

    public List<Mod> getModulesInCategory(Category category) {
        List<Mod> categoryModules = new ArrayList<>();

        for (Mod mod : modules) {
            if (mod.getCategory() == category) {
                categoryModules.add(mod);
            }
        }

        return categoryModules;
    }

    private void addModules() {
        modules.add(new Sprint());
        modules.add(new Scaffold());
        modules.add(new KillAura());
        modules.add(new AimAssist());
        modules.add(new TriggerBot());
        modules.add(new AutoDTap());
        modules.add(new FastCrystal());
        modules.add(new AutoTotem());
        modules.add(new AutoPearlFlash());
        modules.add(new AttributeSwap());
        modules.add(new AutoSwitch());
        modules.add(new CartPlacer());
    }
}
