import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ClassManager extends Manager{
    private DatabaseInfo dt = new DatabaseInfo();
    private SchoolManager school = new SchoolManager();

    //Boolean kiểm tra xem lớp có tồn tại trong database hay ko
    public boolean check(String ID) throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        String line = "select * from Class where ClassID = '" + ID + "'";
        ResultSet rs = stmt.executeQuery(line);
        return rs.next();
    }

    //Hàm display danh sách các lớp học
    public void displayAll() throws SQLException{
        String a = "select * from Class order by SchoolID, ClassID";
        Statement stmt = dt.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(a);
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.printf("%-7s%-30s%-10s%n", "ID", "Homeroom Teacher", "School ID");
        while (rs.next()){
            System.out.printf("%-7s%-30s%-10s%n", rs.getString("ClassID"),
                            rs.getString("HomeroomTeacher"), rs.getString("SchoolID"));
        }
        rs.close();
        stmt.close();
    }

    //Hàm tìm kiếm thông tin một lớp học theo ID
    public void search() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("\nPlease enter the Class ID you want to search: ");
            String a = sc.nextLine();
            
            //Nếu tồn tại ID thì đưa ra thông tin và hỏi xem người dùng muốn search tiếp hay trở về menu
            if (check(a)){
                String sql = "select * from Class c inner join School sch on c.SchoolID = sch.SchoolID where ClassID = '" + a + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()){
                    System.out.println("\n========Class Information========");
                    System.out.println("Class ID: " + rs.getString("ClassID"));
                    System.out.println("Homeroom Teacher: " + rs.getString("HomeroomTeacher"));
                    System.out.println("School: " + rs.getString("SchoolName"));
                    System.out.println("Headmaster: " + rs.getString("Headmaster"));
                    System.out.println("School's address: " + rs.getString("SchoolAddress") + "\n");
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to search another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
            //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("Class ID not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to search another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            }
        }
    }

    //Hàm thêm một lớp học vào cơ sở dữ liệu
    public void add() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            //mô tả format của class id
            System.out.println("Class ID has the format of #A#, in which:");
            System.out.println("A is character A");
            System.out.println("# is a number and the first # is the class's grade");
            System.out.print("Please enter the Class ID you want to add: ");
            String a = sc.nextLine();

            //kiểm tra xem id có đúng format không
            if (!a.matches("\\d{1,2}A\\d")){
                System.out.println("Wrong ID's format.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to add another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                if (check(a)){
                    //Nếu đã tồn tại ID thì yêu cầu người dùng nhập lại ID khác hoặc trở về menu
                    System.out.println("Class existed.");
                    System.out.println("Press Enter to get back to the main menu.");
                    System.out.println("Enter any other key to add another class.");
                    String temp = sc.nextLine();
                    if (temp.equals("")) break;
                } else {
                    System.out.print("Enter homeroom teacher's name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter school's ID: ");
                    String schoolID = sc.nextLine();

                    //kiểm tra nếu không tồn tại school ID thì yêu cầu nhập lại
                    while (!school.check(schoolID)){
                        System.out.println("School's ID not existed.");
                        System.out.print("Enter school's ID again: ");
                        schoolID = sc.nextLine();
                    }

                    //thêm dữ liệu vào database
                    String sql = "insert into Class values ('" + a + "', '" + name + "', '" + schoolID + "')";
                    stmt.executeUpdate(sql);
                    System.out.println("Press Enter to get back to the main menu.");
                    System.out.println("Enter any other key to add another class.");
                    String temp = sc.nextLine();
                    if (temp.equals("")) break;
                }
            }
        }
    }

    //Hàm xóa lớp học khỏi cơ sở dữ liệu
    public void delete() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("Please enter the Class ID you want to delete: ");
            String a = sc.nextLine();
            if (!check(a)){
                //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("Class not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to delete another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                //Nếu có thì xác nhận rồi xóa class
                System.out.println("Are you sure you want delete class with ID " + a + "?");
                System.out.print("Enter yes if you do, otherwise enter any other word: ");
                String cf = sc.nextLine();
                if (cf.equalsIgnoreCase("yes")){
                    String sql = "delete from Class where ClassID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to delete another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            }
        }
    }

    //Hàm update dữ liệu của một lớp
    public void update() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("Please enter the Class ID you want to update: ");
            String a = sc.nextLine();
            if (!check(a)){
                //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("Class not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to update another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                System.out.print("Enter new homeroom teacher's name or press enter to skip: ");
                String teacher = sc.nextLine();
                if (!teacher.equals("")) {
                    String sql = "update Class set HomeroomTeacher = '" + teacher + "' where ClassID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.print("Enter new school's ID or press enter to skip: ");
                String schoolID = sc.nextLine();
                if (!schoolID.equals("")){
                    //kiểm tra nếu không tồn tại school ID thì yêu cầu nhập lại
                    while (!school.check(schoolID)){
                        System.out.println("School's ID not existed.");
                        System.out.print("Enter school's ID again: ");
                        schoolID = sc.nextLine();
                    }
                    String sql = "update Class set SchoolID = '" + schoolID + "' where ClassID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to update another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            }
        }
    }
}
