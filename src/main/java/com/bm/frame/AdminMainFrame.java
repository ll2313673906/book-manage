package com.bm.frame;

import com.bm.entity.*;
import com.bm.factory.DAOFactory;
import com.bm.factory.ServiceFactory;
import com.bm.ui.ImgPanel;
import com.bm.utils.AliOSSUtil;
import net.coobird.thumbnailator.Thumbnails;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminMainFrame extends JFrame {
    private ImgPanel rootPanel;
    private JPanel centerPanel;
    private JButton 管理员Button;
    private JButton 图书信息Button;
    private JPanel bookPanel;
    private JPanel studentPanel;
    private JPanel tablePanel;
    private JPanel bookInfoPanel;
    private JPanel bookTopPanel;
    private JLabel bookCoverLabel;
    private JLabel bookNameLabel;
    private JLabel bookAuthorLabel;
    private JTextField bookPriceField;
    private JButton 编辑Button;
    private JLabel bookTypeLabel;
    private JLabel bookIdLabel;
    private JComboBox<BookType>typeComboBox ;
    private JTextField searchField;
    private JButton 搜索Button;
    private JButton 刷新数据Button;
    private JPanel bookContentPanel;
    private JButton 书架Button;
    private ImgPanel bookLeftPanel;
    private JPanel contentPanel;
    private JPanel studentContentPanel;
    private JComboBox<Student> studentIdComboBox;
    private JTextField studentSearchField;
    private JButton 搜索Button1;
    private JButton 新增图书Button;
    private JButton 刷新Button;
    private JTextField addBookField;
    private JButton 选择logo图Button;
    private JButton 确认新增Button;
    private JButton 重新选择Button;
    private JLabel logoLabel;
    private JTextField bookTypeField;
    private JTextField priceField;
    private JTextField authorField;
    private ImgPanel studentLeftPanel;
    private JButton 借阅管理Button;
    private JPanel borrowBookPanel;
    private JPanel borrowLeftPanel;
    private JPanel borrowRightPanel;
    private JComboBox<BorrowBookVo> borrowConboBox;
    private JButton 刷新Button1;

    //学生管理
    private int row;
    //新增图书
    private String uploadFileUrl;
    private File file;
    private File toPic;
    private JList jList;




    public AdminMainFrame(Admin admin) {
        setTitle("管理员登录");
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        rootPanel.setFileName("dd.jpg");
        rootPanel.repaint();


        bookLeftPanel.setFileName("left1.png");
        bookLeftPanel.repaint();
       studentLeftPanel.setFileName("left3.jpg");
       studentLeftPanel.repaint();

        //获取centerPanel的布局,获取的是LayoutManager，向下转型为cardLayout
        CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
        图书信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel,"Card1");
                List<BookVo> bookList = ServiceFactory.getBookServiceInstance().selectAll();
                showBookTable(bookList);


                BookType tip1 = new BookType();
                tip1.setTypeName("请选择图书类型");
                typeComboBox.addItem(tip1);
                //初始化下拉框
                List<BookType> bookTypeList = ServiceFactory.getBookTypeServiceInstance().selectAll();
                for (BookType t:bookTypeList) {
                    typeComboBox.addItem(t);
                }
                //下拉框设置监听，选中哪一项表格就显示该类型的图书
                typeComboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        //如果是选中的状态
                        if (e.getStateChange() == ItemEvent.SELECTED){
                            //得到选中项的索引
                            int index = typeComboBox.getSelectedIndex();
                            //排除第一项提示信息
                            if(index != 0){
                                int typeId = typeComboBox.getItemAt(index).getId();
                                List<BookVo> bookVoList = ServiceFactory.getBookServiceInstance().selectByTypeId(typeId);
                                showBookTable(bookVoList);
                            }
                        }
                    }
                });

            }
        });
        管理员Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               cardLayout.show(centerPanel,"Card2");
               List<Student> studentList = ServiceFactory.getStudentServiceInstance().selectAll();
               showStudent(studentList);
               Student tip = new Student();
               tip.setStudentName("请选择班级");
               studentIdComboBox.addItem(tip);
               //初始化下拉框
                List<Student> studentList1 = ServiceFactory.getStudentServiceInstance().selectAll();
                for (Student s:studentList1) {
                    studentIdComboBox.addItem(s);
                }
                //下拉框设置监听
                studentIdComboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED){
                        int index = studentIdComboBox.getSelectedIndex();
                        if(index != 0){
                            String studentId = studentIdComboBox.getItemAt(index).getId();
                            List<Student> studentList2 = ServiceFactory.getStudentServiceInstance().selectById(studentId);
                            showStudent(studentList2);

                        }
                        }
                    }
                });
            }
        });
        搜索Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //图书搜索
                String keywords = searchField.getText().trim();
                List<BookVo> bookVoList = ServiceFactory.getBookServiceInstance().selectByKeywords(keywords);
                if (bookVoList != null){
                    showBookTable(bookVoList);
                }
            }
        });
        刷新数据Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //初始化数据
                List<BookVo> bookVoList = ServiceFactory.getBookServiceInstance().selectAll();
                showBookTable(bookVoList);
                //类型下拉框还原
                typeComboBox.setSelectedIndex(0);
                //图书信息显示区域还原
                bookCoverLabel.setText("<html><img src=\"https://student-manage-ll.oss-cn-beijing.aliyuncs.com/logo/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20190527201732.png\"/></html>");
                bookIdLabel.setText("未选择");
                bookNameLabel.setText("未选择");
                bookAuthorLabel.setText("未选择");
                bookTypeLabel.setText("未选择");
                bookPriceField.setText("为选择");
                searchField.setText("");
                //编辑按钮隐藏
                编辑Button.setVisible(false);

            }
        });
        书架Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel,"Card3");
                showBookContentPanel();
                bookLeftPanel.setVisible(false);
            }
        });
        借阅管理Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            cardLayout.show(centerPanel,"Card4");
            List<BorrowBookVo> borrowBookVoList = ServiceFactory.getBorrowBookServiceInstance().selectAll();
            showBorrowBookTable(borrowBookVoList);
                BorrowBookVo tip1 = new BorrowBookVo();
                tip1.setBookName("请选择图书");
                borrowConboBox.addItem(tip1);
                //初始化下拉框
                List<BorrowBookVo> borrowBookVoList1 = ServiceFactory.getBorrowBookServiceInstance().selectAll();
                for (BorrowBookVo t:borrowBookVoList1) {
                    borrowConboBox.addItem(t);
                }
                //下拉框设置监听，选中哪一项表格就显示该类型的图书
                borrowConboBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        //如果是选中的状态
                        if (e.getStateChange() == ItemEvent.SELECTED){
                            //得到选中项的索引
                            int index = borrowConboBox.getSelectedIndex();
                            //排除第一项提示信息
                            if(index != 0){
                               int bookId = borrowConboBox.getItemAt(index).getId();
                                List<BorrowBookVo> borrowBookVoList = ServiceFactory.getBorrowBookServiceInstance().selectByBookName(bookId);
                                showBorrowBookTable(borrowBookVoList);
                            }
                        }
                    }
                });
            }
        });
        新增图书Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookLeftPanel.setVisible(true);
            }
        });
        刷新Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookLeftPanel.setVisible(false);
            }
        });
        bookTypeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            bookTypeField.setText("");
            }
        });
        priceField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            priceField.setText("");
            }
        });
        addBookField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            addBookField.setText("");
            }
        });
        选择logo图Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("D:\\"));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    Icon icon = new ImageIcon(file.getAbsolutePath());
                    ((ImageIcon) icon).setImage(((ImageIcon) icon).getImage().getScaledInstance(200, 220, Image.SCALE_DEFAULT));
                    logoLabel.setIcon(icon);
                    logoLabel.setVisible(true);
                    选择logo图Button.setVisible(false);
                }
            }
        });
        确认新增Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //上传文件到OSS并返回外链
                uploadFileUrl = AliOSSUtil.ossUpload(file);
                //创建图书对象，并设置相应的属性
                Book book = new Book();
                book.setBookName(addBookField.getText().trim());
                book.setTypeId(Integer.valueOf(bookTypeField.getText().trim()));
               book.setBookPrice(priceField.getText().trim());
               book.setAuthor(authorField.getText().trim());
                book.setCover(uploadFileUrl);
                //调用service实现新增的功能
                int n = ServiceFactory.getBookServiceInstance().insertBook(book);
                if (n == 1){
                    JOptionPane.showMessageDialog(rootPanel,"新增图书成功");
                    //新增成功之后，将侧面边栏隐藏
                    bookLeftPanel.setVisible(false);
                    //刷新界面数据
                    showBookContentPanel();
                    //将图片预览标签隐藏
                    logoLabel.setVisible(false);
                    //将选择图片的按钮可见
                    选择logo图Button.setVisible(true);
                    //清空文本框
                    addBookField.setText("");
                    bookTypeField.setText("");
                    priceField.setText("");
                    authorField.setText("");
                }else {
                    JOptionPane.showMessageDialog(rootPanel,"新增图书失败");
                }
            }
        });
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("D:/qn"));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION){
                    logoLabel.removeAll();
                    //选中文件，原图
                    file = fileChooser.getSelectedFile();
                    //指定缩略图的大小
                    toPic = fileChooser.getSelectedFile();
                    //此处把图片压成400乘以500的缩略图
                    try{
                        Thumbnails.of(file).size(200,200).toFile(toPic);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    //通过文件创建icon对象
                    Icon icon = new ImageIcon(toPic.getAbsolutePath());
                    //通过标签显示图片
                    logoLabel.setIcon(icon);
                    //设置标签可见
                    logoLabel.setVisible(true);
                }
            }
        });
        重新选择Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            logoLabel.setIcon(null);
            选择logo图Button.setVisible(true);
            }
        });

        搜索Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //图书搜索
                String keywords = studentSearchField.getText().trim();
                List<Student> studentList1 = ServiceFactory.getStudentServiceInstance().selectByKeywords(keywords);
                if (studentList1 != null){
                    showStudent(studentList1);
                }
            }

        });

        刷新Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               List<BorrowBookVo> borrowBookVoList = ServiceFactory.getBorrowBookServiceInstance().selectAll();
               showBorrowBookTable(borrowBookVoList);
                borrowConboBox.setSelectedIndex(0);
            }
        });
    }

public void showBookTable(List<BookVo> bookList) {
    tablePanel.removeAll();
    tablePanel.revalidate();
    //创建表格
    JTable table = new JTable();
    //表格数据模型
    DefaultTableModel model = new DefaultTableModel();
    table.setModel(model);
    //表头内容
    model.setColumnIdentifiers(new String[]{"编号","书名", "作者", "类型", "价格", "封面"});
    //遍历List数组，集成object数据
    for (BookVo b : bookList) {
        Object[] objects = new Object[]{b.getId(),b.getBookName(), b.getAuthor(), b.getTypeName(), b.getBookPrice(), b.getCover()};
        model.addRow(objects);
    }
    //将最后一列隐藏不显示
    TableColumn tc = table.getColumnModel().getColumn(5);
    tc.setMinWidth(0);
    tc.setMaxWidth(0);
    //获得表头
    JTableHeader head = table.getTableHeader();
    //表头居中
    DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
    hr.setHorizontalAlignment(JLabel.CENTER);
    head.setDefaultRenderer(hr);
    //设置表头大小
    head.setFont(new Font("楷体", Font.PLAIN, 32));
    //设置表头行高
    table.setRowHeight(35);
    table.setBackground(new Color(213, 218, 212));
    //表格内容居中
    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
    r.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, r);
    //表格加入滚动条
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    tablePanel.add(scrollPane);
    //表格刷新
    tablePanel.revalidate();
    //弹出菜单
    JPopupMenu jPopupMenu = new JPopupMenu();
    JMenuItem item = new JMenuItem("删除");
    jPopupMenu.add(item);
    table.add(jPopupMenu);

    //从表格内容选择监听
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            table.revalidate();
            row = table.getSelectedRow();
            bookIdLabel.setText(table.getValueAt(row,0).toString());
            bookNameLabel.setText(table.getValueAt(row,1).toString());
            bookAuthorLabel.setText(table.getValueAt(row,2).toString());
            bookTypeLabel.setText(table.getValueAt(row, 3).toString());
            bookPriceField.setText(table.getValueAt(row,4).toString());
            bookCoverLabel.setText("<html><img src = '" + table.getValueAt(row,5).toString() + "'/><html>");
            编辑Button.setVisible(true);
            编辑Button.setText("编辑");
            编辑Button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("编辑")) {
                        bookPriceField.setEditable(true);
                        bookPriceField.setEnabled(true);
                        编辑Button.setText("保存");
                    }
                    if (e.getActionCommand().equals("保存")) {
                        Book book = new Book();
                        book.setId(Integer.valueOf(bookIdLabel.getText()));
                        book.setBookPrice(bookPriceField.getText());
                        int n = ServiceFactory.getBookServiceInstance().updateBook(book);
                        if (n == 1) {
                            model.setValueAt(bookPriceField.getText(), row, 4);
                            bookPriceField.setEditable(false);
                            bookPriceField.setEnabled(false);
                            编辑Button.setText("编辑");
                        }
                    }
                }
            });
            if (e.getButton() == 3) {
                jPopupMenu.show(table, e.getX(), e.getY());
            }

        }
    });

    item.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = (int) table.getValueAt(row,0);
            int choice = JOptionPane.showConfirmDialog(bookPanel,"确认删除么?");
            if (choice == 0){
                if (row != -1){
                    model.removeRow(row);
                }
                ServiceFactory.getBookServiceInstance().deleteById(id);
            }
        }
    });

}

private void showBookContentPanel(){
contentPanel.removeAll();
List<BookVo> bookVoList = ServiceFactory.getBookServiceInstance().selectAll();
GridLayout gridLayout = new GridLayout(0,4,10,10);
contentPanel.setLayout(gridLayout);
    for (BookVo book:bookVoList) {
        JPanel depPanel = new JPanel();
        depPanel.setPreferredSize(new Dimension(300,400));
        depPanel.setBorder(BorderFactory.createTitledBorder(book.getBookName()));
        JLabel logoLabel = new JLabel("<html><img src='" + book.getCover() + "'/></html>");
        logoLabel.setPreferredSize(new Dimension(280,280));
        depPanel.add(logoLabel);
        JButton deleteBtn = new JButton("移除");
        JLabel infoLabel1 = new JLabel("作者:" + book.getAuthor());
        JLabel infoLabel2 = new JLabel("类型:" +book.getTypeName());
        deleteBtn.setPreferredSize(new Dimension(110,42));
        deleteBtn.setFont(new Font(null,Font.BOLD,20));
        deleteBtn.setSize(new Dimension(80,40));
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(depPanel,"确认删除么?","确认对话框",JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION){
                    contentPanel.remove(depPanel);
                    contentPanel.repaint();
                    ServiceFactory.getBookServiceInstance().deleteById(book.getId());
                    System.out.println("已删除");
                }else if (n == JOptionPane.NO_OPTION){
                    System.out.println("已取消");
                }

            }
        });
        depPanel.add(infoLabel1);
        depPanel.add(infoLabel2);
        depPanel.add(deleteBtn);
        contentPanel.add(depPanel);
        contentPanel.revalidate();
    }


}
public void showStudent(List<Student> studentList) {
    studentContentPanel.removeAll();
    //创建表格
    JTable table = new JTable();
    //表格数据模型
    DefaultTableModel model = new DefaultTableModel();
    table.setModel(model);
    //表头内容
    model.setColumnIdentifiers(new String[]{"编号", "姓名", "性别", "班级", "头像"});
    //遍历List，集成object数据
    for (Student s : studentList
    ) {
        Object[] objects = new Object[]{s.getId(), s.getStudentName(), s.getGender(),
                s.getStudentClass(), s.getStudentAvatar()};
        model.addRow(objects);
    }
    //将最后一列隐藏头像地址不显示在表格中
    TableColumn tc = table.getColumnModel().getColumn(4);
    tc.setMinWidth(0);
    tc.setMaxWidth(0);
    //获得表头
    JTableHeader head = table.getTableHeader();
    //表头居中
    DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
    hr.setHorizontalAlignment(JLabel.CENTER);
    head.setDefaultRenderer(hr);
    //设置表头大小
    head.setPreferredSize(new Dimension(head.getWidth(), 30));
    //设置表头字体
    head.setFont(new Font("楷体", Font.PLAIN, 22));
    //设置表头行高
    table.setRowHeight(35);
    //表格背景色
    table.setBackground(new Color(172, 141, 185));
    //表格内容居中
    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
    r.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, r);
    //表格加入滚动面板，水平垂直方向带滚动条
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    studentContentPanel.add(scrollPane);

    //表格刷新
    studentContentPanel.revalidate();

    //弹出菜单
    JPopupMenu jPopupMenu = new JPopupMenu();
    JMenuItem item = new JMenuItem("删除");
    jPopupMenu.add(item);
    table.add(jPopupMenu);
   table.addMouseListener(new MouseAdapter() {
       @Override
       public void mouseClicked(MouseEvent e) {

           if (e.getButton() == 3) {
               jPopupMenu.show(table, e.getX(), e.getY());
           }

       }
   });
    item.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = (String) table.getValueAt(row, 0);
            int choice = JOptionPane.showConfirmDialog(studentContentPanel, "确定删除么？");
            if (choice == 0) {
                if (row != -1) {
                    model.removeRow(row);
                }
                ServiceFactory.getStudentServiceInstance().deleteById(id);
            }

        }
    });

    }
public void showBorrowBookTable(List<BorrowBookVo> borrowBookVoList){
    borrowRightPanel.removeAll();
    //创建表格
    JTable table = new JTable();
    //表格数据模型
    DefaultTableModel model = new DefaultTableModel();
    table.setModel(model);
    //表头内容
    model.setColumnIdentifiers(new String[]{"图书编号","姓名","性别","学号","所借书名","作者","价格","所借时间","封面图"
    });
    //遍历List，集成object数据
    for (BorrowBookVo b : borrowBookVoList
    ) {
        Object[] objects = new Object[]{b.getId(),b.getStudentName(),b.getGender(),b.getStudentId(),
                b.getBookName(),b.getAuthor(),b.getPrice(),b.getBorrowTime(),b.getCover()};
        model.addRow(objects);
    }
    //将最后一列隐藏头像地址不显示在表格中
    TableColumn tc = table.getColumnModel().getColumn(8);
    tc.setMinWidth(0);
    tc.setMaxWidth(0);
    //获得表头
    JTableHeader head = table.getTableHeader();
    //表头居中
    DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
    hr.setHorizontalAlignment(JLabel.CENTER);
    head.setDefaultRenderer(hr);
    //设置表头大小
    head.setPreferredSize(new Dimension(head.getWidth(), 30));
    //设置表头字体
    head.setFont(new Font("楷体", Font.PLAIN, 22));
    //设置表头行高
    table.setRowHeight(35);
    //表格背景色
    table.setBackground(new Color(172, 141, 185));
    //表格内容居中
    DefaultTableCellRenderer r = new DefaultTableCellRenderer();
    r.setHorizontalAlignment(JLabel.CENTER);
    table.setDefaultRenderer(Object.class, r);
    //表格加入滚动面板，水平垂直方向带滚动条
    JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    borrowRightPanel.add(scrollPane);

    //表格刷新
    borrowRightPanel.revalidate();

    //弹出菜单
    JPopupMenu jPopupMenu = new JPopupMenu();
    JMenuItem item = new JMenuItem("删除");
    jPopupMenu.add(item);
    table.add(jPopupMenu);
    table.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getButton() == 3) {
                jPopupMenu.show(table, e.getX(), e.getY());
            }

        }
    });
    item.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = (int) table.getValueAt(row, 0);
            int choice = JOptionPane.showConfirmDialog(studentContentPanel, "确定删除么？");
            if (choice == 0) {
                if (row != -1) {
                    model.removeRow(row);
                }
                ServiceFactory.getBorrowBookServiceInstance().deleteById(id);
            }

        }
    });

}



    public static void main(String[] args) throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new AdminMainFrame(DAOFactory.getAdminDAOInstance().getAdminByAccount("aaa@qq.com"));

    }
}
