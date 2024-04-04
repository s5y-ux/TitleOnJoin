package net.ddns.vcccd;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	ConsoleCommandSender console = getServer().getConsoleSender();
	
	@Override
	public void onEnable() {
		
		//Used to get the config.yml file from the server folder
		FileConfiguration config = this.getConfig();
		
		//Adds the default configuration options to the plugin
		config.addDefault("HexCodes", false);
		config.addDefault("TitleOnJoin", "&6&lWelcome %PlayerName%");
		config.addDefault("SubTitleOnJoin", "To Our Server!");
		config.addDefault("Particles", false);
		config.addDefault("ParticleType", "Clouds");
		config.addDefault("Enter", 3);
		config.addDefault("Stay", 60);
		config.addDefault("Exit", 3);
		config.addDefault("PlaySoundOnJoin", false);
		config.addDefault("Sound", "EXP");
		
		//Saves the configuration file
		this.saveDefaultConfig();
		
		//Registers the player join event defined in OnPlayerJoin();
		getServer().getPluginManager().registerEvents(new OnPlayerJoin(this), this);
		
		//Woo hoo no errors!
		console.sendMessage(ChatColor.GREEN + "Title on join working as expected :)");
		
	}
	
	@Override
	public void onDisable() {
		
	}

}
