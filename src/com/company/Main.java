package com.company;

/*
 * https://contest.yandex.ru/contest/50834/problems/A/
 *
 * A. USB-порты
 *
 * Вася подсчитал, что у него есть m гаджетов, которые нужно подключить к USB и всего n USB-портов на компьютере.
 * В ближайшем интернет-магазине продаются разветвители с одного USB на два за c2 рублей и USB-хабы с одного USB на 5 по c5 рублей.
 * Разветвители и хабы можно подключать друг к другу и к USB-портам компьютера.
 * Определите, какое минимальное количество рублей нужно потратить Васе, чтобы подключить все USB устройства.
 * При этом можно обеспечить возможность подключить и больше m гаджетов, главное минимизировать цену.
 *
 *
 * Формат ввода
 *
 * В первой строке входных данных записано число n (1 ≤ n ≤ 10^15) — количество USB-портов у компьютера.
 * Во второй строке записано число m (1 ≤ m ≤ 10^15) — количество USB-гаджетов.
 * В следующих двух строках записаны целые числа c2 и c5 (1 ≤ c2, c5 ≤ 1000)
 *
 *
 * Формат вывода
 *
 * Выведите одно целое число — минимальное количество рублей, которое надо потратить для покупки необходимого количества разветвителей и хабов.
 * */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean debugMode = false;

        int freePorts = 1; // порты
        int gadgets = 11; // гаджеты
        int c2price = 4; // разветвитель цена
        int c5price = 10; // хаб цена

        if (!debugMode) {
            Scanner scanner = new Scanner(System.in);
            freePorts = scanner.nextInt();
            gadgets = scanner.nextInt();
            c2price = scanner.nextInt();
            c5price = scanner.nextInt();
        }

        // constants
        int C2POWER = 1; // 1 разветвитель может добавить 1 порт
        int C5POWER = 4; // 1 хаб может добавить 4 порта
        double C2_PRICE_PER1 = (double) c2price / C2POWER;
        double C5_PRICE_PER1 = (double) c5price / C5POWER;

        boolean isHubEfficient = C5_PRICE_PER1 < C2_PRICE_PER1;

        // globals
        int totalPrice = 0;

        // пока свободных портов не будет больше чем кол-во гаджетов
        while (freePorts < gadgets) {
            // вычисляем сколько осталось гаджетов не подключенных
            int remainGadgets = gadgets - freePorts;
            // если цена за один порт рв дешевле чем цена за один порт хаба
            if (!isHubEfficient) {
                // покупаем только рв.
                totalPrice += remainGadgets * c2price;
                freePorts += remainGadgets * C2POWER;
            } else {
                // если не подключенных гаджетов больше чем хаб предоставляет кол-ов портов,
                // то добавляем стоимость одного хаба и увеличиваем свободные порты
                if (remainGadgets >= C5POWER) {
                    totalPrice += c5price;
                    freePorts += C5POWER;
                }
                // иначе не подключенных гаджетов меньше чем хаб предоставляет кол-ов портов,
                // то разумнее попытатся купить разветвитель
                else {
                    // если цена нужных нам разветвителей МЕНЬШЕ чем цена хаба,
                    // то добавляем к стоимости цену нужных нам разветвителей и увеличиваем свободные порты
                    if (remainGadgets * C2_PRICE_PER1 < c5price) {
                        totalPrice += remainGadgets * c2price;
                        freePorts += remainGadgets * C2POWER;
                    }
                    // иначе если цена нужных нам разветвителей БОЛЬШЕ чем цена хаба, то разумнее купить просто один хаб и все
                    else {
                        // добавляем стоимость одного хаба и увеличиваем свободные порты
                        totalPrice += c5price;
                        freePorts += C5POWER;
                    }
                }
            }
        }
        System.out.println(totalPrice);
    }
}
