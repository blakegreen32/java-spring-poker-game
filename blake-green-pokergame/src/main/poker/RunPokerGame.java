package src.main.poker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunPokerGame {
    public static void main(String[] args) { SpringApplication.run(RunPokerGame.class, args); }
    public static void pause() throws InterruptedException {
        Thread.sleep(100);
    }
}
