package com.mineev.bugTrackingSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class Main extends JFrame{
    JButton showUsers;
    JTextArea defArea;
    JButton chFile;
    JComboBox favItems;
    public static File file;
    public static String path1;
    public static Connection conn;
    static JPanel panel;
    
	public static void main(String[] args) {
     new Main();
	}
	
public Main () {
	this.setSize(700, 400);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setTitle("TaskTrackingSystem");
	panel = new JPanel();
	
	chFile= new JButton("Выберите файл");
	  ListenForButton2 lForButton2 = new ListenForButton2();
	    chFile.addActionListener(lForButton2);
            panel.add(chFile);
	
	showUsers = new JButton("Добавить/Удалить задачу");
      ListenForButton lForButton = new ListenForButton();
           showUsers.addActionListener(lForButton);
             panel.add(showUsers);  
             
     String [] options  = {"Исполнители", "Проекты","Задачи проекта", "Задачи исполнителя"};      
	   favItems = new JComboBox(options);
	   ListenForBox lForBox = new ListenForBox();
       favItems.addActionListener(lForBox);
	         panel.add(favItems);
	               
	
	
	
  defArea= new JTextArea(20,30);
		     defArea.setLineWrap(true);
		        defArea.setWrapStyleWord(true);
		 JScrollPane scrollBar1 = new JScrollPane(defArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		      panel.add(scrollBar1);          
	

    this.add(panel);
	this.setVisible(true);
} // конец конструктора Main


private class ListenForButton implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==showUsers) {
			Test hp = new Test();
			hp.editTasks();
		}
	}
	
     }
//внутренний класс для загрузки файла
private class ListenForButton2 implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==chFile) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(true);
			  int retVal = fileChooser.showOpenDialog(panel);
			  if (retVal==fileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					      path1 =  file.getAbsolutePath();
					conn = null;
					try {
					Class.forName("org.sqlite.JDBC");
		            conn = DriverManager.getConnection("jdbc:sqlite:"+path1);
					
					} catch (SQLException ex) {
			            
			        	
			            System.out.println("SQLException: " + ex.getMessage());                     
			            System.out.println("VendorError: " + ex.getErrorCode());
			        } 
			        
			        catch (ClassNotFoundException ex) {
						System.out.println("Драйвер не найден");
					}
					
				        JOptionPane.showMessageDialog(null, "База данных успешно открыта)");
			  
		}
	}
     }
   } //конец Класса загрузки файла

private class ListenForBox implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==favItems) {
			defArea.setText(null);
			if (favItems.getSelectedItem().equals("Исполнители")) {
				try {	           
	            Statement sqlState1 = conn.createStatement();
	            String quiery = "select Исполнитель from test";
	            ResultSet res1 = sqlState1.executeQuery(quiery);
	            while (res1.next()) {
	            defArea.append(res1.getString(1)+"\n");
	            }
				} catch (SQLException ex) {
		            
		        	
		            System.out.println("SQLException: " + ex.getMessage());                     
		            System.out.println("VendorError: " + ex.getErrorCode());
		        } 		        		        
					    	 
		      }
			else if (favItems.getSelectedItem().equals("Проекты")) {
				try {	           
	            Statement sqlState1 = conn.createStatement();
	            String quiery = "select Проект from test";
	            ResultSet res1 = sqlState1.executeQuery(quiery);
	            while (res1.next()) {
	            defArea.append(res1.getString(1)+"\n");
	            }
				} catch (SQLException ex) {		            		        	
		            System.out.println("SQLException: " + ex.getMessage());                     
		            System.out.println("VendorError: " + ex.getErrorCode());
		        } 		        		        
					    	 
		      }
			else if (favItems.getSelectedItem().equals("Задачи проекта")) {
				ChosenOne opt1 = new ChosenOne();       		        
					opt1.showTasks();    	 
		      }
			else  {
				ChosenOne2 opt2 = new ChosenOne2();       		        
					opt2.showTasks2();    	 
		      }
		}
	}
} // Конец класса ListenForBox

//Класс показывающий задачи проекта
private class ChosenOne {
JFrame frame; 
JLabel label1;
JComboBox defBox;
JTextArea infoArea;
ResultSet rs3;
String option1;
public void showTasks() {
	frame = new JFrame("Show tasks");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setSize(400, 400);
	frame.setLocationRelativeTo(null);
	
	JPanel panel = new JPanel();
	label1 = new JLabel("Проект");
	
	infoArea = new JTextArea(20,30);
	   infoArea.setLineWrap(true);
	     infoArea.setWrapStyleWord(true);
	      JScrollPane scrollBar1 = new JScrollPane(infoArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        panel.add(scrollBar1);
		
	defBox = new JComboBox();	
	try {
		String sql1 = "select * from test ";
		PreparedStatement pst3 = conn.prepareStatement(sql1);
		rs3 = pst3.executeQuery();
		while (rs3.next()) {
		    option1 = rs3.getString("Проект");
			defBox.addItem(option1);	
			
		}
	}catch (SQLException ex){
			JOptionPane.showMessageDialog(null, "Ошибка базы данных");
		}
		
		defBox.addActionListener(new ActionListener()   {	    
		 	public void actionPerformed(ActionEvent e){
		 		infoArea.setText(null);
		try {
		if (defBox.getSelectedItem().equals(option1)) {
			
			String quiery5 = "select Задача from test WHERE Проект = ?";
			PreparedStatement pst5 = conn.prepareStatement(quiery5);
			pst5.setString(1, option1);
			ResultSet res5 = pst5.executeQuery();
			while (res5.next()) {
			infoArea.append(res5.getString(1)+"\n");						
		      } 
		  }
		}catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ошибка базы данных");
		}				 		
	  }
	});	
		
	
	        
	panel.add(label1);
	panel.add(defBox);
	panel.add(infoArea);
		
	frame.add(panel);
	frame.setVisible(true);
  
	
     }
  }


//Класс показывающий задачи исполнителя
private class ChosenOne2 {
JFrame frame; 
JLabel label1;
JComboBox defBox6;
JTextArea infoArea2;
ResultSet rs3;
String option6;

public void showTasks2() {
	frame = new JFrame("Show tasks");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setSize(400, 400);
	frame.setLocationRelativeTo(null);
	
	JPanel panel = new JPanel();
	label1 = new JLabel("Исполнитель");
	
	infoArea2 = new JTextArea(20,30);
	   infoArea2.setLineWrap(true);
	     infoArea2.setWrapStyleWord(true);
	      JScrollPane scrollBar1 = new JScrollPane(infoArea2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        panel.add(scrollBar1);
		
	defBox6 = new JComboBox();	
	try {
		String sql1 = "select * from test ";
		PreparedStatement pst3 = conn.prepareStatement(sql1);
		rs3 = pst3.executeQuery();
		while (rs3.next()) {
			option6 = rs3.getString("Исполнитель");
			defBox6.addItem(option6);
		
		}
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Ошибка базы данных");
	}
		defBox6.addActionListener(new ActionListener()   {	    
		 	public void actionPerformed(ActionEvent e){
		 		infoArea2.setText(null);
		
		if (defBox6.getSelectedItem().equals(option6)) {
			
		try {
			String query6 = "select Задача from test WHERE Исполнитель = ?";
			PreparedStatement pst6 = conn.prepareStatement(query6);
			pst6.setString(1, option6);
			ResultSet res6 = pst6.executeQuery();
			while (res6.next()) {
			infoArea2.append(res6.getString(1)+"\n");
		      }
		
	} catch (Exception ex) {
		JOptionPane.showMessageDialog(null, "Ошибка базы данных");	 	
		    }
		   }
		}
	});
	panel.add(label1);
	panel.add(defBox6);
	panel.add(infoArea2);
		
	frame.add(panel);
	frame.setVisible(true);
       
	 }
}
//Класс для редактирования задач
private class Test extends JFrame {
	
	JLabel lId, lTask,lProject,lTheme,lType,lPriority,lPerson,lDescription; 
	JTextField tfId, tfTask,tfProject,tfTheme,tfType,tfPriority,tfPerson,tfDescription;
	PreparedStatement pst = null;
	PreparedStatement pst1 = null;
	Object[][] databaseResults;
	Object[] columns = {"id", "Задача","Проект","Тема","Тип","Приоритет","Исполнитель","Описание"};
    DefaultTableModel dTableModel = new DefaultTableModel(databaseResults, columns){
        public Class getColumnClass(int column) {
            Class returnValue;
           
            if ((column >= 0) && (column < getColumnCount())) {
              returnValue = getValueAt(0, column).getClass();
            } else {
              returnValue = Object.class;
            }
            return returnValue;
          }
        };
        
        JTable table = new JTable(dTableModel);
        
	
	public void editTasks (){
		
		JFrame frame = new JFrame("Edit Tasks");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		 	
        try {   
        Class.forName("org.sqlite.JDBC");
         conn = DriverManager.getConnection("jdbc:sqlite:"+path1);
            Statement sqlState = conn.createStatement();      
            String selectStuff = "select id, Задача, Проект, Тема, Тип, Приоритет, Исполнитель, Описание from test";
            PreparedStatement pst1 = conn.prepareStatement(selectStuff);
            ResultSet rows = sqlState.executeQuery(selectStuff);
            Object[] tempRow;
            
            while(rows.next()){          	
            	tempRow = new Object[]{rows.getString(1), rows.getString(2),rows.getString(3),rows.getString(4),rows.getString(5),rows.getString(6),rows.getString(7),rows.getString(8),};
            	dTableModel.addRow(tempRow);
            }
        } 
        
        catch (SQLException ex) {           
        	JOptionPane.showMessageDialog(null,"Откройте базу данных");
            return;
        } 
        
        catch (ClassNotFoundException e) {
			System.out.println("Драйвер не найден");
		}    	    
	    table.setAutoCreateRowSorter(true);
 
	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    
	    JButton addPres = new JButton("Добавить задачу");		    
	    addPres.addActionListener(new ActionListener(){	    
	    	public void actionPerformed(ActionEvent e){
	    		String sId = "", sTask = "",sProject = "",sTheme = "",sType = "",sPriority = "",sPerson = "",sDescription = "";
	    		sId = tfId.getText();
	    		sTask = tfTask.getText();
	    		sProject = tfProject.getText();
	    		sTheme = tfTheme.getText();
	    		sType = tfType.getText();
	    		sPriority = tfPriority.getText();
	    		sPerson = tfPerson.getText();
	    		sDescription = tfDescription.getText();
	    			    		    	
	    		try {
	    			String insertion = "INSERT INTO test (id, Задача, Проект, Тема, Тип, Приоритет, Исполнитель, Описание) VALUES(?,?,?,?,?,?,?,?)";
	    			PreparedStatement pst = conn.prepareStatement(insertion);
	    			pst.setString(1, tfId.getText());
	    			pst.setString(2, tfTask.getText());
	    			pst.setString(3, tfProject.getText());
	    			pst.setString(4, tfTheme.getText());
	    			pst.setString(5, tfType.getText());
	    			pst.setString(6, tfPriority.getText());
	    			pst.setString(7, tfPerson.getText());
	    			pst.setString(8, tfDescription.getText());
	
	    			pst.execute();
	    			pst.close();
	    		} catch (SQLException ex) {
	    			System.out.println("Ошибка базы данных");
	    		}
	    			    		
	    		Object[] tasks = {sId, sTask,sProject,sTheme,sType,sPriority,sPerson,sDescription};
	    		dTableModel.addRow(tasks);
	    		
	    	}
	    	
	    });
	    
	    JButton removePres = new JButton("Удалить задачу");	    
	    removePres.addActionListener(new ActionListener(){	    	
	    	public void actionPerformed(ActionEvent e){
	    		int row = table.getSelectedRow();
	    		String line1 = table.getModel().getValueAt(row, 0).toString();
	    		String removal = "DELETE FROM test where id = "+line1;
	    		try {
	    		PreparedStatement pst3 = conn.prepareStatement(removal);
	    		pst3.execute();
    			pst3.close();
	    		} catch (SQLException exep) {
	    			JOptionPane.showMessageDialog(null, "Не удалось удалить задачу!");
	    		}
	    		dTableModel.removeRow(table.getSelectedRow());
	    	}	
	    });
	    
	    JButton alterTask = new JButton("Изменить данные");	    
	    alterTask.addActionListener(new ActionListener(){	    	
	    	public void actionPerformed(ActionEvent e){	    		
	    	 try {  		
	    String value1 =  tfId.getText();
	    String value2 = tfTask.getText();
	    String value3 = tfProject.getText();
	    String value4 = tfTheme.getText();
	    String value5 = tfType.getText();
	    String value6 = tfPriority.getText();
	    String value7 = tfPerson.getText();
	    String value8 = tfDescription.getText();
	    			String quiery6 = "update test set id= ' "+value1+" ', Задача= ' "+value2+" ', Проект= ' "+value3+" ', Тема= ' "+value4+" ',Тип= ' "+value5+" ', Приоритет= ' "+value6+" ',Исполнитель= ' "+value7+" ',Описание= ' "+value8+" ' where id= ' "+value1+" ' ";
	    			PreparedStatement pst6 = conn.prepareStatement(quiery6);
	    			pst6.execute();
	    			JOptionPane.showMessageDialog(null, "Таблица успешно обновлена!");
	    			pst6.close();	    			
	    		} catch (Exception exep) {
	    			JOptionPane.showMessageDialog(null, "Не удалось изменить задачу!");
	    		}
	    	}	
	    });
	    
	    lId = new JLabel("id");
	    lTask = new JLabel("Задача");
	    lProject = new JLabel("Проект");
	    lTheme = new JLabel("Тема");
	    lType = new JLabel("Тип");
	    lPriority = new JLabel("Приоритет");
	    lPerson = new JLabel("Исполнитель");
	    lDescription = new JLabel("Описание");
	   
	    tfId = new JTextField(3);
	    tfTask = new JTextField(15);
	    tfProject = new JTextField(15);
	    tfTheme = new JTextField(10);
	    tfType = new JTextField(15);
	    tfPriority = new JTextField(15);
	    tfPerson = new JTextField(15);
	    tfDescription = new JTextField(20);
	    
	    
	    JPanel inputPanel = new JPanel();
	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new GridLayout(10,1));

	    
	    inputPanel.add(lId);
	    inputPanel.add(tfId);
	    inputPanel.add(lTask);
	    inputPanel.add(tfTask);
	    inputPanel.add(lProject);
	    inputPanel.add(tfProject);
	    inputPanel.add(lTheme);
	    inputPanel.add(tfTheme);
	    inputPanel.add(lType);
	    inputPanel.add(tfType);
	    inputPanel.add(lPriority);
	    inputPanel.add(tfPriority);
	    inputPanel.add(lPerson);
	    inputPanel.add(tfPerson);
	    inputPanel.add(lDescription);
	    inputPanel.add(tfDescription);
	    
	    buttonPanel.add(addPres);
	    buttonPanel.add(removePres);
	    buttonPanel.add(alterTask);
	    
	    
	    frame.add(inputPanel, BorderLayout.SOUTH);
	    frame.add(buttonPanel, BorderLayout.EAST);
	    frame.setSize(1600, 400);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);		
	}
	
} //конец класса Test
  }

