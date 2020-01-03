package ru.gavrilov.catch_the_drop;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private static GameWindow game_Window; //объявление переменной

    public static void main(String[] args) {
        game_Window = new GameWindow(); //создание объекта (окна) GameWindow и помещение его в переменную game_Window
        game_Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // завршение программы при закрытии окна
        game_Window.setLocation(200, 100); //установка координат окна относительно левого верхнего угла
        game_Window.setSize(906, 478);
        GameField game_field = new GameField();
        game_Window.add(game_field); //добавление объекта в окно
        game_Window.setResizable(false); // запрет на изменение размеров окна
        game_Window.setVisible(true);
    }

    private static void onRepaint (Graphics g){
        g.fillOval(10,10,50,50);
        g.drawLine(50,300,800,300);
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent (Graphics g){
            super.paintComponent(g); //отрисовка панели родительским методом
            onRepaint(g);
        }
    }
}
