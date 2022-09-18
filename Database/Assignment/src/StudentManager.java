import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentManager extends Manager{
    private DatabaseInfo dt = new DatabaseInfo();
    private ClassManager cl = new ClassManager();

    //Boolean kiểm tra xem lớp có tồn tại trong database hay ko
    public boolean check(String ID) throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        String line = "select * from Student where StudentID = '" + ID + "'";
        ResultSet rs = stmt.executeQuery(line);
        return rs.next();
    }

    //Hàm display danh sách các học sinh
    public void displayAll() throws SQLException{
        String a = "select * from Student";
        Statement stmt = dt.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(a);
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.printf("%-7s%-30s%-10s%-8s%-30s%n", "ID", "Student Name", "Gender", "Class", "Address");
        while (rs.next()){
            System.out.printf("%-7s%-30s%-10s%-8s%-30s%n", rs.getString("StudentID"),
                            rs.getString("StudentName"), rs.getString("Gender"),
                            rs.getString("ClassID"), rs.getString("StudentAddress"));
        }
        rs.close();
        stmt.close();
    }

    //Hàm tìm kiếm thông tin một học sinh theo ID
    public void search() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("\nPlease enter the Student ID you want to search: ");
            String a = sc.nextLine();
            
            //Nếu tồn tại ID thì đưa ra thông tin và hỏi xem người dùng muốn search tiếp hay trở về menu
            if (check(a)){
                String sql = "select * from Student s inner join Class c on s.ClassID = c.ClassID "
                            + "inner join School sch on c.SchoolID = sch.SchoolID where StudentID = '" + a + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()){
                    System.out.println("\n========Student Information========");
                    System.out.println("Student ID: " + rs.getString("StudentID"));
                    System.out.println("Student's name: " + rs.getString("StudentName"));
                    System.out.println("Student's gender: " + rs.getString("Gender"));
                    System.out.println("Class: " + rs.getString("ClassID"));
                    System.out.println("Homeroom teacher: " + rs.getString("HomeroomTeacher"));
                    System.out.println("School: " + rs.getString("SchoolName"));
                    System.out.println("Student's address: " + rs.getString("StudentAddress") + "\n");
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to search another student.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
            //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("Student ID not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to search another student.");
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
            //mô tả format của student id
            System.out.println("Student ID has the format of S###, in which:");
            System.out.println("# is a digit");
            System.out.print("Please enter the Student ID you want to add: ");
            String a = sc.nextLine();

            //kiểm tra xem id có đúng format không
            if (!a.matches("S\\d{3}")){
                System.out.println("Wrong ID's format.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to add another student.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                if (check(a)){
                    //Nếu đã tồn tại ID thì yêu cầu người dùng nhập lại ID khác hoặc trở về menu
                    System.out.println("Student existed.");
                    System.out.println("Press Enter to get back to the main menu.");
                    System.out.println("Enter any other key to add another student.");
                    String temp = sc.nextLine();
                    if (temp.equals("")) break;
                } else {
                    System.out.print("Enter student's name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter student's gender: ");
                    String gender = sc.nextLine();
                    System.out.print("Enter class's ID: ");
                    String classID = sc.nextLine();

                    //kiểm tra nếu không tồn tại class ID thì yêu cầu nhập lại
                    while (!cl.check(classID)){
                        System.out.println("Class's ID not existed.");
                        System.out.print("Enter class's ID again: ");
                        classID = sc.nextLine();
                    }
                    
                    System.out.print("Enter student's address: ");
                    String address = sc.nextLine();

                    //thêm dữ liệu vào database
                    String sql = "insert into Student values ('" + a + "', '" + name + "', '" + gender + "', '"
                                    + classID + "', '" + address + "')";
                    stmt.executeUpdate(sql);
                    System.out.println("Press Enter to get back to the main menu.");
                    System.out.println("Enter any other key to add another student.");
                    String temp = sc.nextLine();
                    if (temp.equals("")) break;
                }
            }
        }
    }

    //Hàm xóa học sinh khỏi cơ sở dữ liệu
    public void delete() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("Please enter the Student ID you want to delete: ");
            String a = sc.nextLine();
            if (!check(a)){
                //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("Student not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to delete another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                //Nếu có thì xác nhận rồi xóa học sinh
                System.out.println("Are you sure you want delete student with ID " + a + "?");
                System.out.print("Enter yes if you do, otherwise enter any other word: ");
                String cf = sc.nextLine();
                if (cf.equalsIgnoreCase("yes")){
                    String sql = "delete from Student where StudentID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to delete another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            }
        }
    }

    
    //Hàm update dữ liệu của một học sinh
    public void update() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("Please enter the Student ID you want to update: ");
            String a = sc.nextLine();
            if (!check(a)){
                //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("Student not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to update another class.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                System.out.print("Enter new student's name or press enter to skip: ");
                String name = sc.nextLine();
                if (!name.equals("")) {
                    String sql = "update Student set StudentName = '" + name + "' where StudentID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.print("Enter new student's gender or press enter to skip: ");
                String gender = sc.nextLine();
                if (!name.equals("")) {
                    String sql = "update Student set Gender = '" + gender + "' where StudentID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.print("Enter new class's ID or press enter to skip: ");
                String classID = sc.nextLine();
                if (!classID.equals("")){
                    //kiểm tra nếu không tồn tại school ID thì yêu cầu nhập lại
                    while (!cl.check(classID)){
                        System.out.println("Class's ID not existed.");
                        System.out.print("Enter school's ID again: ");
                        classID = sc.nextLine();
                    }
                    String sql = "update Student set ClassID = '" + classID + "' where StudentID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.print("Enter new student's address or press enter to skip: ");
                String address = sc.nextLine();
                if (!name.equals("")) {
                    String sql = "update Student set StudentAddress = '" + address + "' where StudentID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to update another student.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            }
        }
    }
}
