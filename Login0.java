package cn.edu.guet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login0 extends JFrame {
    /*
声明变量
 */
    private JTextField account;
    private JTextField password;
    private JPanel jPanel;
    private JButton login;
    public Login0(String title){
        super(title);
        setSize(400,280);
        // 禁止窗口缩放
        setResizable(false);
        jPanel= (JPanel) this.getContentPane();
        jPanel.setLayout(null);//布局为空：自己任意安排组件的位置
        account=new JTextField("zhangsan");

        account.setBounds(105,120,190,35);
        jPanel.add(account);
        password=new JTextField("zs1234");
        password.setBounds(105,160,190,35);
        jPanel.add(password);

        login=new JButton("登录");
        login.setBounds(105,200,190,35);
        login.addActionListener(e -> {
            String name=account.getText();
            String pass=password.getText();

            String sql= "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pstmt;

            Connection conn=null;
            ResultSet resultSet;
            String url="jdbc:oracle:thin:@175.178.96.170:1521:orcl";
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn= DriverManager.getConnection(url,"hwb","Grcl1234Hwb");
                System.out.println(conn);
                if(conn!=null){
                    System.out.println("连接成功");
                    pstmt=conn.prepareStatement(sql);
                    pstmt.setString(1,name);
                    pstmt.setString(2,pass);

                    resultSet=pstmt.executeQuery();
                    if(resultSet.next()){
                        System.out.println("登入成功");
                    }else{
                        System.out.println("用户名或密码错误");
                    }
                }
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        jPanel.add(login);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login0("登录窗口");
    }
}
