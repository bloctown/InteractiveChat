/*
 * This file is part of InteractiveChat.
 *
 * Copyright (C) 2022. LoohpJames <jamesloohp@gmail.com>
 * Copyright (C) 2022. Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.loohp.interactivechat.modules;

import com.loohp.interactivechat.InteractiveChat;
import com.loohp.interactivechat.api.InteractiveChatAPI;
import com.loohp.interactivechat.objectholders.ICPlayer;
import com.loohp.interactivechat.objectholders.ICPlayerFactory;
import com.loohp.interactivechat.objectholders.ReplaceTextBundle;
import com.loohp.interactivechat.utils.ChatColorUtils;
import com.loohp.interactivechat.utils.CollectionUtils;
import com.loohp.interactivechat.utils.ComponentCompacting;
import com.loohp.interactivechat.utils.ComponentReplacing;
import com.loohp.interactivechat.utils.CustomStringUtils;
import com.loohp.interactivechat.utils.InteractiveChatComponentSerializer;
import com.loohp.interactivechat.utils.PlaceholderParser;
import com.loohp.interactivechat.utils.VanishUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayernameDisplay implements Listener {

    private static final Random random = new Random();
    private static final AtomicInteger flag = new AtomicInteger();
    private static Collection<ReplaceTextBundle> names = new LinkedHashSet<>();

    public static void setup() {
        Bukkit.getPluginManager().registerEvents(new PlayernameDisplay(), InteractiveChat.plugin);
        Bukkit.getScheduler().runTaskTimerAsynchronously(InteractiveChat.plugin, () -> {
            int valid = flag.get();
            Collection<ReplaceTextBundle> names = getNames();
            Bukkit.getScheduler().runTask(InteractiveChat.plugin, () -> {
                if (flag.get() == valid) {
                    PlayernameDisplay.names = names;
                }
            });
        }, 0, 100);
    }

    public static Component process(Component component, Optional<ICPlayer> sender, Player receiver, long unix) {
        Collection<ReplaceTextBundle> names = PlayernameDisplay.names;
        if (names == null) {
            names = getNames();
        }
        for (ReplaceTextBundle entry : names) {
            component = processPlayer(entry.getPlaceholder(), entry.getPlayer(), sender, receiver, component, unix);
        }

        return ComponentCompacting.optimize(component);
    }

    private static Component processPlayer(String placeholder, ICPlayer player, Optional<ICPlayer> sender, Player receiver, Component component, long unix) {
        String plain = InteractiveChatComponentSerializer.plainText().serialize(component);
        if (InteractiveChat.usePlayerNameCaseSensitive) {
            if (!plain.contains(placeholder)) {
                return component;
            }
        } else {
            if (!plain.toLowerCase().contains(placeholder.toLowerCase())) {
                return component;
            }
        }
        HoverEvent<?> hoverEvent;
        ClickEvent clickEvent;
        if (InteractiveChat.usePlayerNameHoverEnable) {
            String playertext = ChatColorUtils.translateAlternateColorCodes('&', PlaceholderParser.parse(player, InteractiveChat.usePlayerNameHoverText));
            hoverEvent = HoverEvent.showText(LegacyComponentSerializer.legacySection().deserialize(playertext));
        } else {
            hoverEvent = null;
        }
        if (InteractiveChat.usePlayerNameClickEnable) {
            String playertext = PlaceholderParser.parse(player, InteractiveChat.usePlayerNameClickValue);
            clickEvent = ClickEvent.clickEvent(ClickEvent.Action.valueOf(InteractiveChat.usePlayerNameClickAction), playertext);
        } else {
            clickEvent = null;
        }
        String regex = InteractiveChat.usePlayerNameCaseSensitive ? CustomStringUtils.escapeMetaCharacters(placeholder) : "(?i)" + CustomStringUtils.escapeMetaCharacters(placeholder);
        component = ComponentReplacing.replace(component, regex, true, (result, replaced) -> {
            List<Component> children = new ArrayList<>();
            for (Component c : replaced) {
                Component edited = c;
                if (hoverEvent != null && (InteractiveChat.usePlayerNameOverrideHover || edited.hoverEvent() == null)) {
                    edited = edited.hoverEvent(hoverEvent);
                }
                if (clickEvent != null && (InteractiveChat.usePlayerNameOverrideClick || edited.clickEvent() == null)) {
                    edited = edited.clickEvent(clickEvent);
                }
                children.add(edited);
            }
            return Component.empty().children(children);
        });
        List<Component> children = new ArrayList<>(component.children());
        for (int i = 0; i < children.size(); i++) {
            Component child = children.get(i);
            if (child instanceof TranslatableComponent) {
                TranslatableComponent trans = (TranslatableComponent) child;
                List<Component> withs = new ArrayList<>(trans.args());
                for (int u = 0; u < withs.size(); u++) {
                    Component with = withs.get(u);
                    withs.set(u, processPlayer(placeholder, player, sender, receiver, with, unix));
                }
                trans = trans.args(withs);
                children.set(i, trans);
            }
        }
        return ComponentCompacting.optimize(component.children(children));
    }

    private static Collection<ReplaceTextBundle> getNames() {
        List<ReplaceTextBundle> names = new ArrayList<>();
        ICPlayerFactory.getOnlineICPlayers().forEach(each -> {
            if (VanishUtils.isVanished(each.getUniqueId())) {
                return;
            }
            names.add(new ReplaceTextBundle(ChatColorUtils.stripColor(each.getName()), each, each.getName()));
            if (InteractiveChat.useBukkitDisplayName && !ChatColorUtils.stripColor(each.getName()).equals(ChatColorUtils.stripColor(each.getDisplayName()))) {
                names.add(new ReplaceTextBundle(ChatColorUtils.stripColor(each.getDisplayName()), each, each.getDisplayName()));
            }
            for (String nickname : each.getNicknames()) {
                names.add(new ReplaceTextBundle(ChatColorUtils.stripColor(nickname), each, nickname));
            }
        });

        CollectionUtils.filter(names, each -> each.getPlaceholder().length() > 2);
        Collections.sort(names, Collections.reverseOrder());

        return new LinkedHashSet<>(names);
    }

    private PlayernameDisplay() {
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerLoginEvent event) {
        if (event.getResult().equals(PlayerLoginEvent.Result.ALLOWED)) {
            flag.set(random.nextInt());
            names = null;
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        flag.set(random.nextInt());
        names = null;
    }

}
