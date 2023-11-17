package net.ddns.vcccd;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class OnPlayerJoin implements Listener {

    //Used to store the main body program for config reference
    private final Main main;

    //Constructor
    public OnPlayerJoin(Main main) {

        //Used to set instance main to main as passed in the Main class
        this.main = main;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {

        //HashMap used to reference the Title and Sub Title
        HashMap < String, String > PlaceHolders = new HashMap < String, String > ();

        //Used to reference the config file in the program
        FileConfiguration config = main.getConfig();

        //Used to reference the player in the Join event
        Player player = event.getPlayer();

        //Placeholder hash-map, will replace later with more place holders
        PlaceHolders.put("%PlayerName%", player.getName());

        //Strings to pass onto the send title method as defined by the config
        String PassValueOne = config.getString("TitleOnJoin");
        String PassValueTwo = config.getString("SubTitleOnJoin");

        //Used to replace all of the regex held in the placeholder HashMap
        for (String PlaceHolderValues: PlaceHolders.keySet()) {
            PassValueOne = PassValueOne.replaceAll(PlaceHolderValues, PlaceHolders.get(PlaceHolderValues));
            PassValueTwo = PassValueTwo.replaceAll(PlaceHolderValues, PlaceHolders.get(PlaceHolderValues));
        }

        //Used to translate formatting codes from the standard Unicode Character
        String title = ChatColor.translateAlternateColorCodes('&', PassValueOne);
        String subtitle = ChatColor.translateAlternateColorCodes('&', PassValueTwo);

        //Send the player subtitles
        player.sendTitle(title, subtitle, config.getInt("Enter"), config.getInt("Stay"), config.getInt("Exit"));

        //Gets the player location
        Location PlayerLocation = player.getLocation();


        //Creates the HashMap for the particles
        HashMap < String, Particle > ParticleList = new HashMap < String, Particle > ();

        //Adds the particles to the map
        ParticleList.put("Clouds", Particle.CLOUD);
        ParticleList.put("Hearts", Particle.HEART);
        ParticleList.put("Spark", Particle.FIREWORKS_SPARK);
        ParticleList.put("Soul", Particle.SOUL_FIRE_FLAME);

        //Checks to see if we have the particles enabled in the config
        if (config.getBoolean("Particles")) {

            //Checks to see if it was configured correctly
            if (ParticleList.keySet().contains(config.getString("ParticleType"))) {

                //If so we spawn the particles
                for (int index = -5; index < 5; index++) {
                    player.getWorld().spawnParticle(ParticleList.get(config.getString("ParticleType")),
                        PlayerLocation.getX(), PlayerLocation.getY(), PlayerLocation.getZ(), 10);
                }

                //Otherwise we tell the console there is a problem...
            } else {
                main.console.sendMessage(ChatColor.RED + "Check to see if the particles in the console were spelled right...");
            }
        }
        
        HashMap <String, Sound> SoundMap = new HashMap <String, Sound>();
        
        SoundMap.put("EXP", Sound.ENTITY_PLAYER_LEVELUP);
        SoundMap.put("Anvil", Sound.BLOCK_ANVIL_HIT);
        SoundMap.put("Trade", Sound.ENTITY_VILLAGER_TRADE);
        SoundMap.put("Firework", Sound.ENTITY_FIREWORK_ROCKET_SHOOT);
        
        if(config.getBoolean("PlaySoundOnJoin")) {
        	if(SoundMap.keySet().contains(config.getString("Sound"))) {
        		player.playSound(PlayerLocation, SoundMap.get(config.getString("Sound")), 500, 0);
        	} else {
        		main.console.sendMessage(ChatColor.RED + "Unknown Sound Option in Config...");
        	}
        }


    }

}