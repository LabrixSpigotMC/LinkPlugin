package net.toofiy.link.commands;

import net.toofiy.link.LinkBootstrap;
import net.toofiy.link.utils.obj.LinkHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LinkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            return true;

        final Player player = (Player) sender;
        final LinkHandler linkHandler = LinkBootstrap.getInstance().getLinkHandler();

        switch (args.length){
            case 0:
                if(player.isOp())
                    player.sendMessage("Help: /links add <name> <url>");

                player.sendMessage("All links (" + linkHandler.getLinks().size() + "): ");

                linkHandler.getLinks().forEach(link ->
                    player.sendMessage("Name: " + link.getName() + ", Link: " + link.getURL()));

                break;
            case 3:
                if(player.isOp()){
                    if(args[0].equalsIgnoreCase("add")){
                        linkHandler.addLink(args[1], args[2], UUID.randomUUID());
                        return true;
                    }
                }
                break;
        }

        return false;
    }
}
