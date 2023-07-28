package net.toofiy.link;

import net.toofiy.link.commands.LinkCommand;
import net.toofiy.link.utils.SimpleLinkHandler;
import net.toofiy.link.utils.obj.LinkHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class LinkBootstrap extends JavaPlugin {

    private static LinkBootstrap instance;

    private LinkHandler linkHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic

        init();
        saveDefaultConfig();

        getCommand("links").setExecutor(new LinkCommand());
    }

    private void init(){
        instance = this;
        linkHandler = new SimpleLinkHandler();
    }

    public LinkHandler getLinkHandler() {
        return linkHandler;
    }

    public static LinkBootstrap getInstance() {
        return instance;
    }
}
