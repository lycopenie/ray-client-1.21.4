package ray4rc.rayclient.modules;

import ray4rc.rayclient.modules.combat.AimAssist;
import ray4rc.rayclient.modules.combat.AutoDTap;
import ray4rc.rayclient.modules.combat.KillAura;
import ray4rc.rayclient.modules.combat.TriggerBot;
import ray4rc.rayclient.modules.movement.Scaffold;
import ray4rc.rayclient.modules.movement.Sprint;

import java.util.ArrayList;
import java.util.List;

import ray4rc.rayclient.modules.Mod.Category;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private List<Mod> modules = new ArrayList<>();
    public ModuleManager() {
        addModules();
    }

    public List<Mod> getModules() {
        return modules;
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
    }
}
