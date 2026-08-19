package com.ddnik.controller;

import com.ddnik.db.IDto;
import com.ddnik.exceptions.ConsoleUserInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Универсальное консольное меню для выбора элемента из списка Dto.
 */
public class ItemsListMenu<T extends IDto> {

    private final List<T> items;
    private final String message;
    private final String tableHeader;

    private final Logger logger = LoggerFactory.getLogger(ItemsListMenu.class);

    public ItemsListMenu(List<T> items,
                         String message,
                         String tableHeader) {
        this.items = Objects.requireNonNull(items);
        this.message = Objects.requireNonNull(message);
        this.tableHeader = Objects.requireNonNull(tableHeader);
    }

    /**
     * Запускает работу меню выбора элемента.
     * @return выбранный из списка элемент.
     */
    public Optional<T> start() {
        boolean isRunning = true;
        int selectedItemIndex = 1;
        while (isRunning) {
            try {
                display();
                selectedItemIndex = ConsoleReader.chooseMenuItem(0, items.size());

                if (selectedItemIndex == 0) return Optional.empty();
                else isRunning = false;

            } catch (ConsoleUserInputException e) {
                System.out.println(e.getLocalizedMessage());
                logger.error("Ошибка консольного ввода.", e);
            }
        }
        return Optional.of(items.get(selectedItemIndex-1));
    }

    /**
     * Выводит таблицу элементов списка.
     */
    public void display() {
        System.out.println(message);
        System.out.println(tableHeader);
        int i = 1;
        for (IDto item : items) {
            System.out.println(i + " | " + item.toMenuTableRow());
            i++;
        }
        System.out.print("> ");
    }
}
