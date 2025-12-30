public class CliApp {
    private final ConsoleIO io = new ConsoleIO();

    public void run(){
        io.println("=== RechnungFlow CLI ===");

        boolean running = true;
        while(running){
            printMenu();
            int choice = io.readInt("Choose option: ", 0, 3);

            switch (choice){
                case 1 -> io.println("[TODO] Create invoice");
                case 2 -> io.println("[TODO] List invoices");
                case 3 -> io.println("[TODO] Show invoice");
                case 0 -> {
                    io.println("Bye!");
                    running = false;
                }
            }
            io.println("");
        }
    }

    private void printMenu(){
        io.println("1) Create invoice");
        io.println("2) List invoices");
        io.println("3) Show invoice");
        io.println("0) Exit");
    }


}
