[![Build Status](https://ci.loohpjames.com/job/InteractiveChat/badge/icon)](https://ci.loohpjames.com/job/InteractiveChat/)
# InteractiveChat

https://www.spigotmc.org/resources/75870/

Shows your item in hand, inventory content and ender chest content with your friends! Even across servers in your global bungeecord chat! This plugin also allows you to create your own interactive keywords for your players to use in chat!

More information (screenshots, commands, permissions) about the plugin can be found on the Spigot page linked above.

## Built against Spigot, Bungeecord, Velocity
Built against [Spigot's API](https://www.spigotmc.org/wiki/buildtools/) (required mc versions are listed on the spigot page above), [Bungeecord's API](https://github.com/SpigotMC/BungeeCord), and [Velocity's API](https://velocitypowered.com/), with support for the [Adventure Library](https://github.com/KyoriPowered/adventure) (Notably used by [Paper](https://papermc.io/) since Minecraft 1.16.5).
Plugins built against Spigot usually also work with [Paper](https://papermc.io/) while plugins built against Bungeecord usually also works with [Waterfall](https://github.com/PaperMC/Waterfall).

## Development Builds

- [Jenkins](https://ci.loohpjames.com/job/InteractiveChat/)

## Maven
```html
<repository>
  <id>loohp-repo</id>
  <url>https://repo.loohpjames.com/repository</url>
</repository>
```
```html
<dependency>
  <groupId>com.loohp</groupId>
  <artifactId>InteractiveChat</artifactId>
  <version>VERSION</version>
  <scope>provided</scope>
</dependency>
```
Replace `VERSION` with the version number.

## Dependencies 

- [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)
- [Vault](https://www.spigotmc.org/resources/vault.34315/)

## Soft Dependencies

- [EssentialsX](https://www.spigotmc.org/resources/essentialsx.9089/)
- [OpenInv](https://dev.bukkit.org/projects/openinv)
- [SuperVanish](https://www.spigotmc.org/resources/supervanish-be-invisible.1331/)
- [PremiumVanish](https://www.spigotmc.org/resources/premiumvanish-stay-hidden-bungee-support.14404/)
- [CMI](https://www.spigotmc.org/resources/cmi-270-commands-insane-kits-portals-essentials-economy-mysql-sqlite-much-more.3742/) ([Public API](https://github.com/Zrips/CMI-API))
- [DeluxeChat](https://www.spigotmc.org/resources/deluxechat.1277/)
- [DeathMessagesPrime](https://www.spigotmc.org/resources/deathmessagesprime.3789/)
- [ChatManager](https://www.spigotmc.org/resources/chat-manager-1-7-1-16-with-30-features-and-40-commands.52245/)
- [VentureChat](https://www.spigotmc.org/resources/venturechat.771/)
- [DiscordSRV](https://www.spigotmc.org/resources/discordsrv.18494/)
- [Dynmap](https://www.spigotmc.org/resources/dynmap.274/)
- [LuckPerms](https://luckperms.net/)

## Addons

- [InteractiveChat-DiscordSRV-Addon](https://www.spigotmc.org/resources/83917/) (Adds InteractiveChat functionalities to DiscordSRV)
