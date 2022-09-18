import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        StudentManager st = new StudentManager();
        ClassManager cl = new ClassManager();
        SchoolManager sch = new SchoolManager();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while(!exit){
            //màn hình console
            System.out.println("\033[H\033[2J");
            System.out.flush();
            System.out.println("Welcome to Student Management Application!");
            System.out.println("1. Display all students");
            System.out.println("2. Display all classes");
            System.out.println("3. Display all schools");
            System.out.println("4. Search for a student");
            System.out.println("5. Search for a class");
            System.out.println("6. Search for a school");
            System.out.println("7. Add a new student");
            System.out.println("8. Add a new class");
            System.out.println("9. Add a new school");
            System.out.println("10. Update a student's information");
            System.out.println("11. Update a class's information");
            System.out.println("12. Update a school's information");
            System.out.println("13. Delete a student");
            System.out.println("14. Delete a class");
            System.out.println("15. Delete a school");
            System.out.println("0. Exit");
            System.out.print("Enter your option: ");
            String choice = sc.nextLine();
            switch (choice){
                case "1":
                    st.displayAll();
                    System.out.println("Press Enter to get back to the main menu.");
                    sc.nextLine();
                    break;
                case "2":
                    cl.displayAll();
                    System.out.println("Press Enter to get back to the main menu.");
                    sc.nextLine();
                    break;
                case "3":
                    sch.displayAll();
                    System.out.println("Press Enter to get back to the main menu.");
                    sc.nextLine();
                    break;
                case "4":
                    st.search();
                    break;
                case "5":
                    cl.search();
                    break;
                case "6":
                    sch.search();
                    break;
                case "7":
                    st.add();
                    break;
                case "8":
                    cl.add();
                    break;
                case "9":
                    sch.add();
                    break;
                case "10":
                    st.update();
                    break;
                case "11":
                    cl.update();
                    break;
                case "12":
                    sch.update();
                    break;
                case "13":
                    st.delete();
                    break;
                case "14":
                    cl.delete();
                    break;
                case "15":
                    sch.delete();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }
}
