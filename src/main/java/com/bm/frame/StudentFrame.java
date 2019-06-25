package com.bm.frame;

import com.bm.entity.BookCar;
import com.bm.entity.BookType;
import com.bm.entity.BookVo;
import com.bm.entity.User;
import com.bm.factory.DAOFactory;
import com.bm.factory.ServiceFactory;
import com.bm.ui.ImgPanel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentFrame extends JFrame{
    private ImgPanel rootPanel;
    private JButton 搜索图书Button;
    private JPanel centerPanel;
    private JButton 查看图书Button;
    private JPanel contentPanel;
    private JPanel bookPanel;
    private ImgPanel leftPanel;
    private JPanel searchPanel;
    private ImgPanel searchLeftPanel;
    private JPanel bookTablePanel;
    private JComboBox<BookType> typeComboBox;
    private JTextField bookSearchField;
    private JButton 搜索Button;
    private JButton 刷新数据Button;
    private JButton 我的图书Button;
    private JPanel bookCarPanel;
    private JPanel bookCarContentPanel;
    private int row;

    public StudentFrame(User user){
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
        showBookPanel();

        rootPanel.setFileName("bg1.jpg");
        rootPanel.repaint();

        leftPanel.setFileName("a.jpg");
        leftPanel.repaint();

        searchLeftPanel.setFileName("a.jpg");
        searchLeftPanel.repaint();

        CardLayout cardLayout = (CardLayout)centerPanel.getLayout();
        查看图书Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel,"Card1");
                showBookPanel();


            }
        });
        搜索图书Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel,"Card2");
                List<BookVo> bookVoList = ServiceFactory.getBookServiceInstance().selectAll();
                showBookTable(bookVoList);


                BookType tip1 = new BookType();
                tip1.setTypeName("选择图书类型");
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

        我的图书Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(centerPanel,"Card3");
                showBookCarContentPanel();
            }
        });
        搜索Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //图书搜索
                String keywords = bookSearchField.getText().trim();
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

            }
        });

    }
    private void showBookPanel(){
//移除原有的数据
    contentPanel.removeAll();
    //从serVice层获取到所以的图书
    List<BookVo> bookVoList = ServiceFactory.getBookServiceInstance().selectAll();
  GridLayout gridLayout = new GridLayout(0,3,15,10);
  contentPanel.setLayout(gridLayout);
    for (BookVo book:bookVoList) {
        //给每本图书创建一个面板
        JPanel depPanel = new JPanel();
        depPanel.setPreferredSize(new Dimension(200,470));
        JPanel imgPanel = new JPanel();
        imgPanel.setPreferredSize(new Dimension(250,200));
        JPanel infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(330,200));
        depPanel.setBorder(BorderFactory.createTitledBorder(book.getBookName()));
        JLabel logoLabel = new JLabel("<html><img src='" + book.getCover() + "'/></html>");
        depPanel.setFont(new Font("微软雅黑",Font.BOLD,23));
        JLabel authorLabel = new JLabel("作者:"+book.getAuthor());
        JLabel typeLabel = new JLabel("类型:"+book.getTypeName());
        JLabel priceLabel = new JLabel("价格:"+book.getBookPrice());
       authorLabel.setFont(new Font("微软雅黑",Font.BOLD,15));
        GridLayout gridLayout1 = new GridLayout(0,3,10,1);
        infoPanel.setLayout(gridLayout1);

        JButton deleteBtn = new JButton("借阅");
        deleteBtn.setPreferredSize(new Dimension(110,30));
        deleteBtn.setFont(new Font(null,Font.BOLD,20));
        deleteBtn.setSize(new Dimension(80,40));
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(depPanel,"确认借阅么？","确认对话框",JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION){
                    contentPanel.remove(depPanel);
                    contentPanel.repaint();
                    ServiceFactory.getBookServiceInstance().deleteById(book.getId());
                    showBookCarContentPanel();
                    System.out.println("已确认");
                }else if (n == JOptionPane.NO_OPTION){
                    System.out.println("已取消");
                }
            }
        });


        imgPanel.add(logoLabel);
        infoPanel.add(authorLabel);
        infoPanel.add(typeLabel);
        infoPanel.add(priceLabel);
        depPanel.add(imgPanel);
        depPanel.add(infoPanel);
        depPanel.add(deleteBtn);

        contentPanel.add(depPanel);
        contentPanel.revalidate();

    }

}
    public void showBookTable(List<BookVo> bookVoList){
        bookTablePanel.removeAll();
        //创建表格
        JTable table = new JTable();
        //表格数据模型
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        //表头内容
        model.setColumnIdentifiers(new String[]{"编号","书名","作者","类型","价格","封面"});
        //遍历List，集成object数据
        for (BookVo bookVo:bookVoList
        ) {
            Object[] objects = new Object[]{bookVo.getId(),bookVo.getBookName(),bookVo.getAuthor(),bookVo.getTypeName()
                    ,bookVo.getBookPrice(),bookVo.getCover()};
            model.addRow(objects);
        }
        //将最后一列隐藏头像地址不显示在表格中
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
        head.setPreferredSize(new Dimension(head.getWidth(),30));
        //设置表头字体
        head.setFont(new Font("楷体",Font.PLAIN,22));
        //设置表头行高
        table.setRowHeight(35);
        //表格背景色
        table.setBackground(new Color(172,141,185));
        //表格内容居中
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,r);
        //表格加入滚动面板，水平垂直方向带滚动条
        JScrollPane scrollPane = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        bookTablePanel.add(scrollPane);

        //表格刷新
        bookTablePanel.revalidate();

        //弹出菜单
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem item = new JMenuItem("跳转到书架");
        jPopupMenu.add(item);
        table.add(jPopupMenu);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3){
                    jPopupMenu.show(table,e.getX(),e.getY());
                }
            }
        });

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id= (int) table.getValueAt(row,0);
                int choice = JOptionPane.showConfirmDialog(bookTablePanel,"确认跳转么？");
                if (choice == 0){
                    if (row != -1){
                        model.removeRow(row);
                       List<BookVo> bookVoList1 = ServiceFactory.getBookServiceInstance().selectAll();

                        showBookPanel();
                    }

                }

            }
        });






    }
    public void showBookCarContentPanel(){
        bookCarContentPanel.removeAll();
        List<BookCar> bookCarList = ServiceFactory.getBookCarServiceInstance().selectAll();
        GridLayout gridLayout = new GridLayout(0,4,10,10);
        bookCarContentPanel.setLayout(gridLayout);
        for (BookCar bookCar:bookCarList) {
            JPanel depPanel = new JPanel();
            depPanel.setPreferredSize(new Dimension(300,400));
            depPanel.setBorder(BorderFactory.createTitledBorder(bookCar.getBookName()));
            JLabel logoLabel = new JLabel("<html><img src='" + bookCar.getCover() + "'/></html>");
            logoLabel.setPreferredSize(new Dimension(280,280));
            depPanel.add(logoLabel);
            JButton deleteBtn = new JButton("删除");
            JLabel infoLabel1 = new JLabel("作者:" + bookCar.getAuthor());
            JLabel infoLabel2 = new JLabel("价格:" +bookCar.getBookPrice());
            deleteBtn.setPreferredSize(new Dimension(110,42));
            deleteBtn.setFont(new Font(null,Font.BOLD,20));
            deleteBtn.setSize(new Dimension(80,40));
            deleteBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int n = JOptionPane.showConfirmDialog(depPanel,"确认删除么?","确认对话框",JOptionPane.YES_NO_OPTION);
                    if (n == JOptionPane.YES_OPTION){
                        bookCarContentPanel.remove(depPanel);
                        bookCarContentPanel.repaint();
                        ServiceFactory.getBookServiceInstance().deleteById(bookCar.getId());
                        System.out.println("已删除");
                    }else if (n == JOptionPane.NO_OPTION){
                        System.out.println("已取消");
                    }

                }
            });
            depPanel.add(infoLabel1);
            depPanel.add(infoLabel2);
            depPanel.add(deleteBtn);
            bookCarContentPanel.add(depPanel);
            bookCarContentPanel.revalidate();
        }

    }
    public static void main(String[] args) throws Exception {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lookAndFeel);
        new StudentFrame(DAOFactory.getUserDAOInstance().getAdminByAccount("aaa"));

    }
}
