package com.github.tonedahonda.calendar.frontend.listener.inventory;

import com.github.tonedahonda.calendar.backend.Main;
import com.github.tonedahonda.calendar.backend.storage.Storage;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentAdd;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentManager;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentRemove;
import com.github.tonedahonda.calendar.frontend.gui.appointment.AppointmentTrash;
import com.github.tonedahonda.calendar.frontend.gui.calendar.Calendar;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener {

    public InventoryCloseListener(InventoryCloseEvent event) {

        Inventory inventory = event.getInventory();

        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            Storage storage = Main.storages.get(player);

			/*
             * Checks if the Storage of the player isn't null, to prevent NullPointerExeptions.
			 */
            if (storage != null) {

                Calendar calendar = storage.getCalendar();
                if (calendar != null) {
                    if (inventory.equals(calendar.getInventory())) {
                        storage.setCalendar(null);
                    }
                }

                AppointmentManager appointmentManager = storage.getAppointmentManager();
                if (appointmentManager != null) {
                    if (inventory.equals(appointmentManager.getInventory())) {
                        storage.setAppointmentManager(null);
                    }
                }

                AppointmentAdd appointmentAdd = storage.getAppointmentAdd();
                if (appointmentAdd != null) {
                    if (inventory.equals(appointmentAdd.getInventory())) {
                        if (storage.getInputParameter() == null) {
                            storage.setAppointmentAdd(null);
                        }
                    }
                }

                AppointmentRemove appointmentRemove = storage.getAppointmentRemove();
                if (appointmentRemove != null) {
                    if (inventory.equals(appointmentRemove.getInventory())) {
                        storage.setAppointmentRemove(null);
                    }
                }

                AppointmentTrash appointmentTrash = storage.getAppointmentTrash();
                if (appointmentTrash != null) {
                    if (inventory.equals(appointmentTrash.getInventory())) {
                        storage.setAppointmentTrash(null);
                    }
                }

                if (storage.allNull()) {
                    Main.storages.remove(player);
                } else {
                    Main.storages.put(player, storage);
                }
            }

        }

    }

}
