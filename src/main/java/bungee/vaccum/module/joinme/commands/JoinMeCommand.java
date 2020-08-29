package bungee.vaccum.module.joinme.commands;

import bungee.vaccum.module.joinme.JoinMe;
import bungee.vaccum.module.joinme.utils.ImageAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class JoinMeCommand extends Command {

    private JoinMe joinMe = JoinMe.getInstance();
    private ImageAPI imageAPI = joinMe.getImageAPI();

    public JoinMeCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            BufferedImage head = parseHead(proxiedPlayer);
        }
    }

    private BufferedImage parseHead(ProxiedPlayer proxiedPlayer) {
        String request = "https://crafatar.com/avatars/" + proxiedPlayer.getUniqueId().toString() + "?size=64px";

        try {
            return ImageIO.read(new URL(request));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
