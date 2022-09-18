import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SchoolManager extends Manager {
    private DatabaseInfo dt = new DatabaseInfo();

    //Boolean kiểm tra xem trường có tồn tại trong database hay ko
    public boolean check(String ID) throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        String line = "select * from School where SchoolID = '" + ID + "'";
        ResultSet rs = stmt.executeQuery(line);
        return rs.next();
    }

    //Hàm display danh sách các trường
    public void displayAll() throws SQLException{
        String a = "select * from School order by SchoolID";
        Statement stmt = dt.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(a);
        System.out.println("\033[H\033[2J");
        System.out.flush();
        System.out.printf("%-8s%-30s%-30s%-30s%n", "ID", "School Name", "Headmaster", "School Address");
        while (rs.next()){
            System.out.printf("%-8s%-30s%-30s%-30s%n", rs.getString("SchoolID"), rs.getString("SchoolName"),
                            rs.getString("Headmaster"), rs.getString("SchoolAddress"));
        }
        rs.close();
        stmt.close();
    }

    //Hàm tìm kiếm thông tin một trường theo ID
    public void search() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("\nPlease enter the School ID you want to search: ");
            String a = sc.nextLine();
            
            //Nếu tồn tại ID thì đưa ra thông tin và hỏi xem người dùng muốn search tiếp hay trở về menu
            if (check(a)){
                String sql = "select * from School where SchoolID = '" + a + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()){
                    System.out.println("\n========School Information========");
                    System.out.println("School ID: " + rs.getString("SchoolID"));
                    System.out.println("Name: " + rs.getString("SchoolName"));
                    System.out.println("Headmaster: " + rs.getString("Headmaster"));
                    System.out.println("Address: " + rs.getString("SchoolAddress") + "\n");
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to search another school.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
            //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("School ID not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to search another school.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            }
        }
    }

    //Hàm thêm một trường vào cơ sở dữ liệu
    public void add() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            //mô tả format của school id
            System.out.println("School ID has the format of SC###, in which:");
            System.out.println("# is a digit");
            System.out.print("Please enter the School ID you want to add: ");
            String a = sc.nextLine();

            //kiểm tra xem id có đúng format không
            if (!a.matches("SC\\d{3}")){
                System.out.println("Wrong ID's format.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to add another school.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                if (check(a)){
                    //Nếu đã tồn tại ID thì yêu cầu người dùng nhập lại ID khác hoặc trở về menu
                    System.out.println("School existed.");
                    System.out.println("Press Enter to get back to the main menu.");
                    System.out.println("Enter any other key to add another school.");
                    String temp = sc.nextLine();
                    if (temp.equals("")) break;
                } else {
                    System.out.print("Enter school's name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter the name of school's headmaster: ");
                    String headmaster = sc.nextLine();
                    System.out.print("Enter school's address: ");
                    String address = sc.nextLine();

                    //thêm dữ liệu vào database
                    String sql = "insert into School values ('" + a + "', '" + name + "', '" + headmaster + "','" + address + "')";
                    stmt.executeUpdate(sql);
                    System.out.println("Press Enter to get back to the main menu.");
                    System.out.println("Enter any other key to add another school.");
                    String temp = sc.nextLine();
                    if (temp.equals("")) break;
                }
            }
        }
    }

    //Hàm xóa trường khỏi cơ sở dữ liệu
    public void delete() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            System.out.print("Please enter the School ID you want to delete: ");
            String a = sc.nextLine();
            if (!check(a)){
                //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("School not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to delete another school.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                //Nếu có thì xác nhận rồi xóa trường
                System.out.println("Are you sure you want delete school with ID " + a + "?");
                System.out.print("Enter yes if you do, otherwise enter any other word: ");
                String cf = sc.nextLine();
                if (cf.equalsIgnoreCase("yes")){
                    String sql = "delete from School where SchoolID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to delete another school.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            }
        }
    }

    //Hàm update dữ liệu của một trường
    public void update() throws SQLException{
        Statement stmt = dt.getConnection().createStatement();
        Scanner sc = new Scanner(System.in);
        while (true){
            displayAll();
            //mô tả format của class id
            System.out.print("Please enter the School ID you want to update: ");
            String a = sc.nextLine();
            if (!check(a)){
                //Nếu không tồn tại ID thì yêu cầu người dùng nhập lại ID hoặc trở về menu
                System.out.println("School not existed.");
                System.out.println("Press Enter to get back to the main menu.");
                System.out.println("Enter any other key to update another school.");
                String temp = sc.nextLine();
                if (temp.equals("")) break;
            } else {
                System.out.print("Enter new school's name or press enter to skip: ");
                String name = sc.nextLine();
                if (!name.equals("")) {
                    String sql = "update School set SchoolName = '" + name + "' where SchoolID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.print("Enter new headmaster's name or press enter to skip: ");
                String headmaster = sc.nextLine();
                if (!headmaster.equals("")) {
                    String sql = "update School set Headmaster = '" + headmaster + "' where SchoolID = '" + a + "'";
                    stmt.executeUpdate(sql);
                }
                System.out.print("Enter new school's address or press enter to skip: ");
                String address = sc.nextLine();
                if (!address.equals("")) {
                    String sql = "update School set SchoolAddress = '" + address + "' where SchoolID = '" + a + "'";
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
