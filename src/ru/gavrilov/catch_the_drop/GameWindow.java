package ru.gavrilov.catch_the_drop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class GameWindow extends JFrame {

    private static GameWindow game_Window; //объявление переменной
    private static long last_frame_time; //время ппоследнего кадра
    private static Image bg;
    private static Image drop;
    private static Image game_over;
    private static float drop_left = 50;
    private static float drop_top = -100; //верхняя граница капли
    private static float drop_v = 200;
    private static int score;

    public static void main(String[] args) throws IOException {
        bg = ImageIO.read(GameWindow.class.getResourceAsStream("bg.png"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("drop.png"));
        game_over = ImageIO.read(GameWindow.class.getResourceAsStream("game_over.png"));
        game_Window = new GameWindow(); //создание объекта (окна) GameWindow и помещение его в переменную game_Window
        game_Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // завршение программы при закрытии окна
        game_Window.setLocation(200, 100); //установка координат окна относительно левого верхнего угла
        game_Window.setSize(906, 478);
        game_Window.setResizable(false); // запрет на изменение размеров окна
        last_frame_time = System.nanoTime(); //возвращает текущее время в наносекундах
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { // вызывается при нажатии мыши
                int x = e.getX(); // координаты точки нажатия
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null); //определение правой границы капли
                float drop_bottom = drop_top + drop.getHeight(null); //определение нижней границы капли
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
                if (is_drop) {
                    drop_top = -100; //откидываем каплю за границу окна
                    drop_left = (int)(Math.random() * (game_field.getWidth() - drop.getWidth(null)));
                    drop_v = drop_v + 20; //увеличиваем скорость капли
                    score++;
                    game_Window.setTitle("Score: " + score); //вывод очков на границу окна
                }
            }
        });
        game_Window.add(game_field); //добавление объекта в окно
        game_Window.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime(); //текущее время
        float delta_time = (current_time - last_frame_time) * 0.000000001f; // в секундах
        last_frame_time = current_time;
        drop_top = drop_top + drop_v * delta_time; //отрисовка смещения по вретикали
        //drop_left = drop_left + drop_v * delta_time; //отрисовка смещения по горизонтали
        g.drawImage(bg, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if (drop_top > game_Window.getHeight()) {
            g.drawImage(game_over, 280, 150, null);
        }
    }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) { //вызывется когда требуется отрисовать панель
            super.paintComponent(g); //отрисовка панели родительским методом
            onRepaint(g);
            repaint(); // принудительная отрисовка
        }
    }
}
