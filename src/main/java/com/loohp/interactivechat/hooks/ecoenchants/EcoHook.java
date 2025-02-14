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

package com.loohp.interactivechat.hooks.ecoenchants;

import com.willfp.eco.core.display.Display;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EcoHook {

    public static ItemStack setEcoLores(ItemStack itemStack) {
        return setEcoLores(itemStack, null);
    }

    public static ItemStack setEcoLores(ItemStack itemStack, Player player) {
        return Display.displayAndFinalize(itemStack.clone(), player);
    }

}
