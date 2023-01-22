import java.util.Random;

public class Main {
    private static volatile int leaderId;
    private static final Random rand = new Random();

    public static void main(String[] args) {
        // Escolhe a thread líder de forma aleatória
        leaderId = rand.nextInt(10) + 1;
        System.out.println("Thread " + leaderId + " é o líder atual.");

        // Inicia as threads
        for (int i = 1; i <= 10; i++) {
            new Thread(new ProcessThread(i)).start();
        }
    }

    public static class ProcessThread implements Runnable {
        private int id;

        public ProcessThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // Tempo de espera aleatório
                    Thread.sleep(rand.nextInt(10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Simula a detecção da falha do líder
                if (rand.nextInt(10) == 0) {
                    System.out.println("Thread " + leaderId + " falhou.");
                    startElection();
                }
            }
        }

        private void startElection() {
            if (id > leaderId) {
                System.out.println("Thread " + id + " iniciando eleição.");
                leaderId = id;
                System.out.println("Thread " + id + " é o novo líder.");
            } else {
                System.out.println("Thread " + id + " enviando mensagem para thread " + leaderId + ".");
            }
        }
    }
}
