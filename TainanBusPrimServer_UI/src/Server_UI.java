
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTable;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComboBox;


public class Server_UI extends JFrame {

	private JPanel contentPane;
	
	public static String userInterfaceIP;	
	public static int userInterfacePort;
	public static int listenportforUser;
	public static int listenportforIPC;
	
	public static JLabel lblNewLabel_5 ;
	
	public static String[] IPCIP;
	public static String[] ThreeGIP;  // All TC IP 
	public static String[] MasterIP;  // All IPC IP
	

	public static String DBDriver;
	public static String DB_connect_string;
	public static String DB_userid;
	public static String DB_password;
	public static String DB_database_name;
	
	public static String localDBIP;
	public static String CrossRoadDB_userid;
	public static String CrossRoadDB_password;
	
	public static Date Systemstart;
	private PrintStream standardOut;
	public static JButton btnNewButton;
	public static JButton btnNewButton_6;
	public static JButton btnNewButton_1;
	public static JButton btnNewButton_10;
	public JComboBox comboBox;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Systemstart = new Date();
					ClearText();
					Server_UI frame = new Server_UI();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Server_UI() {
		
		super("台南公車優先 通訊司服器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1252, 760);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("本機  IP : ");
		lblNewLabel.setBounds(10, 10, 59, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(71, 14, 110, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("網頁伺服器IP :");
		lblNewLabel_2.setBounds(10, 43, 94, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(102, 43, 110, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("資料庫 :");
		lblNewLabel_4.setBounds(10, 68, 59, 15);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setForeground(Color.BLACK);
		lblNewLabel_5.setBounds(60, 68, 267, 15);
		contentPane.add(lblNewLabel_5);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(318, 93, 867, 593);
		contentPane.add(tabbedPane);
		
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("System.out", null, scrollPane, null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		
		scrollPane.setViewportView(textArea);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Remote DB Settings", null, panel, null);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(146, 12, 210, 21);
		panel.add(textField);
		textField.setColumns(10);
		textField.setText("192.168.234.152");
		JLabel lblNewLabel_6 = new JLabel("DB IP Address :");
		lblNewLabel_6.setBounds(20, 13, 98, 18);
		panel.add(lblNewLabel_6);
		
		textField_1 = new JTextField();
		textField_1.setBounds(146, 43, 210, 21);
		panel.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("sa");
		
		JLabel lblNewLabel_7 = new JLabel("DB Account :");
		lblNewLabel_7.setBounds(20, 46, 83, 15);
		panel.add(lblNewLabel_7);
		
		textField_2 = new JTextField();
		textField_2.setBounds(146, 74, 210, 21);
		panel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("ceciits");
		
		JLabel lblNewLabel_8 = new JLabel("DB Password :");
		lblNewLabel_8.setBounds(20, 77, 83, 15);
		panel.add(lblNewLabel_8);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(20, 131, 808, 248);
		panel.add(scrollPane_2);
		
		final JTextArea textArea_2 = new JTextArea();
		scrollPane_2.setViewportView(textArea_2);
		
		JLabel lblNewLabel_9 = new JLabel("SQL Query");
		lblNewLabel_9.setBounds(20, 102, 83, 19);
		panel.add(lblNewLabel_9);
		
		JButton btnNewButton_3 = new JButton("Clear Query");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_2.setText("");	
			}
		});
		btnNewButton_3.setBounds(452, 389, 133, 23);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("SQL Execute");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String temp =textArea_2.getText();
				if( temp.isEmpty()){					
					JFrame frame= new JFrame();
                    JOptionPane.showMessageDialog(frame,"SQL query is empty");
				}else{
					String remoteDBIP=textField.getText();
					String remoteDBuser=textField_1.getText();
					String remoteDBpw=textField_2.getText();
					tabbedPane.setSelectedIndex(0);
					
					System.out.println("SQL Command ->"+temp);
					System.out.println("Remote DB IP  "+remoteDBIP);
					System.out.println("Remote DB User  "+remoteDBuser);
					System.out.println("Remote DB PW  "+remoteDBpw);
					
					//dbUpdate(String driver,String connect_string,String userid,String password,String queryString)
					String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
					String connect="jdbc:microsoft:sqlserver://"+remoteDBIP+":1433;DatabaseName=TainanBusPrim";
					int effected=MSDB.dbUpdate(driver,connect,remoteDBuser,remoteDBpw,temp);
					
					if(effected==-1){
						JFrame frame= new JFrame();
	                    JOptionPane.showMessageDialog(frame,"SQL Error");
					}else{
						JFrame frame= new JFrame();
	                    JOptionPane.showMessageDialog(frame,"SQL Executed "+effected+" Rows Effected");
					}
						
					//System.out.println("Rows effected  "+effected);
				}
								
			}
		});
		btnNewButton_4.setBounds(644, 389, 121, 23);
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_9 = new JButton(" Sql Multi-Execute ");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 Thread thread = new Thread(new Runnable() {
			            @Override
			            public void run() {
			            	
			            	try{
			            		String Error="";
			            		String temp =textArea_2.getText();
			    				if( temp.isEmpty()){					
			    					JFrame frame= new JFrame();
			                        JOptionPane.showMessageDialog(frame,"SQL query is empty");
			    				}else{
			    					
			    					for(String t:MasterIP){
			    						
			    						try{
			    							int tried=0;
				    						while(tried<3){
				    							try{
				    								String remoteDBIP=t;
							    					String remoteDBuser=textField_1.getText();
							    					String remoteDBpw=textField_2.getText();
							    					
							    					String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
							    					String connect="jdbc:microsoft:sqlserver://"+remoteDBIP+":1433;DatabaseName=TainanBusPrim";
							    					int effected=MSDB.dbUpdate(driver,connect,remoteDBuser,remoteDBpw,temp);
							    					
							    					if(effected==-1){
							    						//failed
							    						tried++;
							    						Thread.sleep(1000);
							    					}else{
							    						//Success
							    						tried=3;
							    					}
				    								
				    								
				    							}catch(Exception g){
				    								System.out.println("Sql Multi-Execute InnerWhile Error");
				    							}

				    							
				    						}
			    							
			    							
			    						}catch(Exception v){
			    							System.out.println("Sql Multi-Execute InnerFor Error");
			    						}
			    						

			    					}
			    					

			    				}
			            		
			            		
			            	}catch(Exception e){
			            		
			            		System.out.println(" Error-> Sql Multi-Execute ");
			            	}
			            	
			            	
			              
			            }
			        });
			        thread.start();
				
			}
		});
		btnNewButton_9.setBounds(614, 422, 178, 34);
		panel.add(btnNewButton_9);
		
		
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Upload", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Upload File");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String uploadip=textField_3.getText();
				String uploadport=textField_13.getText();
				String uploadfilepath=textField_4.getText()+"\\";
				String uploadfilename=textField_5.getText();
				
				if( uploadip.isEmpty() || uploadfilepath.isEmpty() || uploadfilename.isEmpty()){					
					JFrame frame= new JFrame();
                    JOptionPane.showMessageDialog(frame,"Upload Settings are Incomplete");
				}else{
					//System.out.println(HttpMultipartUpload.Upload("127.0.0.1:81","C://","test.txt"));
					boolean result=HttpMultipartUpload.Upload(uploadip+":"+uploadport,uploadfilepath,uploadfilename);
					
					if(result){
						JFrame frame= new JFrame();
                    JOptionPane.showMessageDialog(frame,"Upload Complete");
                    }else{
                    	JFrame frame= new JFrame();
                        JOptionPane.showMessageDialog(frame,"Upload Fail");
                    }
					
				}
				 
				
			}
		});
		btnNewButton_2.setBounds(211, 133, 114, 23);
		panel_1.add(btnNewButton_2);
		
		JLabel lblNewLabel_10 = new JLabel("Destination IP :");
		lblNewLabel_10.setBounds(31, 22, 105, 15);
		panel_1.add(lblNewLabel_10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(128, 19, 197, 21);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText("192.168.234.132");
		
		JLabel lblNewLabel_11 = new JLabel("File Path :");
		lblNewLabel_11.setBounds(31, 54, 70, 15);
		panel_1.add(lblNewLabel_11);
		
		textField_4 = new JTextField();
		textField_4.setBounds(128, 51, 197, 21);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		textField_4.setText("C:\\\\");
		
		JLabel lblNewLabel_12 = new JLabel("File Name :");
		lblNewLabel_12.setBounds(31, 88, 87, 15);
		panel_1.add(lblNewLabel_12);
		
		textField_5 = new JTextField();
		textField_5.setBounds(128, 85, 197, 21);
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		textField_5.setText("BusPriority_IPC.bin");
		
		textField_13 = new JTextField();
		textField_13.setBounds(412, 19, 96, 21);
		panel_1.add(textField_13);
		textField_13.setColumns(10);
		textField_13.setText("8002");
		
		JLabel lblNewLabel_21 = new JLabel("Port :");
		lblNewLabel_21.setBounds(356, 22, 46, 15);
		panel_1.add(lblNewLabel_21);
		
		JButton btnNewButton_8 = new JButton("Multi-Upload IPC OTA");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(new Runnable() {
		            @Override
		            public void run() {
		            		            		
		            		try{		            									
		        				
		            			String[] AllMastersIP=MSDB.getAllMasters_IP();
		        				String uploadport="8002";
		        				String uploadfilepath=textField_4.getText()+"\\";
		        				String uploadfilename=textField_5.getText();
		        				
		        				String fail=" ";
		        				
		        				 for(String t:AllMastersIP){
		        					 boolean result=HttpMultipartUpload.Upload(t+":"+uploadport,uploadfilepath,uploadfilename);
		        						
		        						if(result){
		        								System.out.println("Uploaded IPC "+t);
		        	                    }else{	                    	
		        	                    	fail=fail+" "+t;
		        	                    }
		        				 }
		        			         		        				 		        				
		        								
		        				JFrame frame= new JFrame();
		        				if(fail==" "){
		        					JOptionPane.showMessageDialog(frame,"Upload Finished "+fail);
		        				}else{
		        					JOptionPane.showMessageDialog(frame,"Upload Fail IPC "+fail);
		        					}
		            			
			            	}catch(Exception e){
			            		e.printStackTrace();
			            		System.out.println("Error in Multi-Upload IPC OTA ");
			            		
			            		
			            	}
		            	
		            }
		        });
		        thread.start();
																				
			}
		});
		
		btnNewButton_8.setBounds(364, 133, 223, 23);
		panel_1.add(btnNewButton_8);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Download Data", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_13 = new JLabel("CrossRoadDB IP :");
		lblNewLabel_13.setBounds(10, 25, 109, 15);
		panel_2.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("CrossRoad Account :");
		lblNewLabel_14.setBounds(10, 50, 132, 15);
		panel_2.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("CrossRoad PW :");
		lblNewLabel_15.setBounds(10, 75, 109, 15);
		panel_2.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("Local DB IP :");
		lblNewLabel_16.setBounds(10, 123, 132, 15);
		panel_2.add(lblNewLabel_16);
		
		JLabel lblNewLabel_17 = new JLabel("Local DB Account :");
		lblNewLabel_17.setBounds(10, 148, 132, 15);
		panel_2.add(lblNewLabel_17);
		
		JLabel lblNewLabel_18 = new JLabel("Local DB PW :");
		lblNewLabel_18.setBounds(10, 173, 132, 15);
		panel_2.add(lblNewLabel_18);
		
		textField_6 = new JTextField();
		textField_6.setBounds(165, 22, 200, 21);
		panel_2.add(textField_6);
		textField_6.setColumns(10);
		textField_6.setText("172.23.25.1");
		
		
		textField_7 = new JTextField();
		textField_7.setBounds(165, 47, 200, 21);
		panel_2.add(textField_7);
		textField_7.setColumns(10);
		textField_7.setText("sa");
		
		textField_8 = new JTextField();
		textField_8.setBounds(165, 72, 200, 21);
		panel_2.add(textField_8);
		textField_8.setColumns(10);
		textField_8.setText("tycgtcipc");
		
		textField_9 = new JTextField();
		textField_9.setBounds(165, 120, 200, 21);
		panel_2.add(textField_9);
		textField_9.setColumns(10);
		textField_9.setText("172.23.31.1");
		
		textField_10 = new JTextField();
		textField_10.setBounds(165, 145, 200, 21);
		panel_2.add(textField_10);
		textField_10.setColumns(10);
		textField_10.setText("sa");
		
		textField_11 = new JTextField();
		textField_11.setBounds(165, 170, 200, 21);
		panel_2.add(textField_11);
		textField_11.setColumns(10);
		textField_11.setText("tybuspriM1234");
		
		JLabel lblNewLabel_19 = new JLabel("Data Selection Query");
		lblNewLabel_19.setBounds(10, 251, 161, 21);
		panel_2.add(lblNewLabel_19);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(25, 272, 791, 271);
		panel_2.add(scrollPane_1);
		
		final JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		textArea_1.setText("SELECT * FROM [TaoyuanBusPrim].[dbo].[BusA1_Log]");
		
		JButton btnNewButton_5 = new JButton("Execute");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 String CrossRoadIP= textField_6.getText();
				 String temp_driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
				 String temp_connect="jdbc:microsoft:sqlserver://"+CrossRoadIP+":1433;DatabaseName=TaoyuanBusPrim";
				 String temp_userid=textField_7.getText().trim();
	        	 String temp_password=textField_8.getText().trim();
	        	 String temp_query=textArea_1.getText();
	        	 
	        	 String localIP= textField_9.getText();
	        	 String local_driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
				 String local_connect="jdbc:microsoft:sqlserver://"+localIP+":1433;DatabaseName=TaoyuanBusPrim";
				 String local_userid=textField_10.getText().trim();
	        	 String local_password=textField_11.getText().trim();
	        	 String local_table = textField_12.getText().trim();
	        	 
				boolean remoteconnection=MSDB.dbConnection(temp_driver,temp_connect,temp_userid,temp_password);
				//boolean localconnection=MSDB.dbConnection(temp_driver,temp_connect,temp_userid,temp_password);
				
				if(remoteconnection=false){
					JFrame frame= new JFrame();
                    JOptionPane.showMessageDialog(frame,"CrossRoad DB Connection Fail");
                    
				}else{
					
					ResultSet remotedata = MSDB.GetRemoteDBResultSet(temp_driver,temp_connect,temp_userid,temp_password,temp_query);
					try {
						MSDB.InsertRs(remotedata,local_driver,local_connect,local_userid,local_password,local_table);
						JFrame frame= new JFrame();
	                    JOptionPane.showMessageDialog(frame,"Local DB Insertion Finished");
					} catch (Exception e) {
						JFrame frame= new JFrame();
	                    JOptionPane.showMessageDialog(frame,"Local DB Insertion Fail");
						e.printStackTrace();
						tabbedPane.setSelectedIndex(0);
					}
					
					
					
				}
				
			}
		});
		btnNewButton_5.setBounds(537, 173, 212, 50);
		panel_2.add(btnNewButton_5);
		
		JLabel lblNewLabel_20 = new JLabel("Local Table :");
		lblNewLabel_20.setBounds(10, 216, 132, 15);
		panel_2.add(lblNewLabel_20);
		
		textField_12 = new JTextField();
		textField_12.setBounds(165, 213, 200, 21);
		panel_2.add(textField_12);
		textField_12.setColumns(10);
		textField_12.setText("BusA1_Log");
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Bus Prim Switch", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_22 = new JLabel("CrossRoad ID :");
		lblNewLabel_22.setBounds(10, 29, 106, 26);
		panel_3.add(lblNewLabel_22);
		
		textField_14 = new JTextField();
		textField_14.setBounds(96, 32, 179, 21);
		panel_3.add(textField_14);
		textField_14.setColumns(10);
		String[] BusPrimOnOff={"On","Off"};
		 comboBox = new JComboBox(BusPrimOnOff);
		comboBox.setBounds(295, 32, 50, 21);
		panel_3.add(comboBox);
		
		JButton btnNewButton_12 = new JButton("Send");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(new Runnable() {
		            @Override
		            public void run() { 

		            	
		            	String Switchip=textField_14.getText();
						int OnOff=comboBox.getSelectedIndex();
						//System.out.println(" Combo Box index "+OnOff);
						try{
							
							if( Switchip.isEmpty()){					
								JFrame frame= new JFrame();
			                    JOptionPane.showMessageDialog(frame,"Which Group ?");
							}else{
								if (OnOff==1){
									
									
										//System.out.println("Group "+m);
									     String GroupID= MSDB.getGroupID(Switchip);
										 //String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(Switchip);
										 String MasterIP=MSDB.getCrossRoad_IP(GroupID);
										 //for(String c:CrossRoadAddr){
											 //System.out.println("CrossRoad Addr "+c+" Send to "+MasterIP);									 
											byte[] cmd2=MessageCreator.createpackage("01",Switchip, "5F800100");  //1.Seq 2.Addr 3.�ʥ]���e
											String message=Protocol.bytesToHex(cmd2);
										 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
												UDPSender.Send(MasterIP,"20000",message);									
										 //}
																		
								
								
								JFrame frame= new JFrame();
			                    JOptionPane.showMessageDialog(frame,"Sent Off Message to "+Switchip);
								
								}else if(OnOff==0){
									
									
										//System.out.println("Group "+m);
									     String GroupID= MSDB.getGroupID(Switchip);
										 //String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(Switchip);
										 String MasterIP=MSDB.getCrossRoad_IP(GroupID);
										 //for(String c:CrossRoadAddr){
											 //System.out.println("CrossRoad Addr "+c+" Send to "+MasterIP);									 
											byte[] cmd2=MessageCreator.createpackage("01",Switchip, "5F800101");  //1.Seq 2.Addr 3.�ʥ]���e
											String message=Protocol.bytesToHex(cmd2);
										 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
												UDPSender.Send(MasterIP,"20000",message);									
										 //}
																		

									
								JFrame frame= new JFrame();
			                    JOptionPane.showMessageDialog(frame,"Sent On Message to "+Switchip);
								}									
								
							}
							
						}catch(Exception e){
							e.printStackTrace();
							JFrame frame= new JFrame();
		                    JOptionPane.showMessageDialog(frame,"Failed");
							
						}

		            	
		            	}
		        });
		        thread.start();
				
				
			}
		});
		btnNewButton_12.setBounds(379, 32, 87, 23);
		panel_3.add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("All CrossRoads");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Thread thread = new Thread(new Runnable() {
		            @Override
		            public void run() {
		            		            		
		            		try{	
		            			int OnOff=comboBox.getSelectedIndex();
		            			
		            			if (OnOff==0){
									
		            				try{
	            						String[] allMasterAddr=MSDB.getAllMasters_Addr();
	        							
	        							for(String m: allMasterAddr){
	        								//System.out.println("Group "+m);
	        								
	        								 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
	        								 String MasterIP=MSDB.getCrossRoad_IP(m);
	        								 for(String c:CrossRoadAddr){
	        									 //System.out.println("CrossRoad Addr "+c+" Send to "+MasterIP);									 
	        									byte[] cmd2=MessageCreator.createpackage("01",c, "5F800101");  //1.Seq 2.Addr 3.�ʥ]���e
	        									String message=Protocol.bytesToHex(cmd2);
	        								 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
	        										UDPSender.Send(MasterIP,"20000",message);									
	        								 }
	        																
	        							}  
	        							
	        							JFrame frame= new JFrame();
	        		                    JOptionPane.showMessageDialog(frame,"TurnOn Everything");
	           						
	            					}catch(Exception yy){
	            						System.out.println("Error in TurnOn Everything");            						
	            						System.out.println(yy.getMessage());
	            						JFrame frame= new JFrame();
	        		                    JOptionPane.showMessageDialog(frame,"Failed to TurnOn Everything");
	            					}
														
								}else if(OnOff==1){
									try{
	            						String[] allMasterAddr=MSDB.getAllMasters_Addr();
	        							
	        							for(String m: allMasterAddr){
	        								//System.out.println("Group "+m);
	        								
	        								 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
	        								 String MasterIP=MSDB.getCrossRoad_IP(m);
	        								 for(String c:CrossRoadAddr){
	        									 //System.out.println("CrossRoad Addr "+c+" Send to "+MasterIP);									 
	        									byte[] cmd2=MessageCreator.createpackage("01",c, "5F800100");  //1.Seq 2.Addr 3.�ʥ]���e
	        									String message=Protocol.bytesToHex(cmd2);
	        								 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
	        										UDPSender.Send(MasterIP,"20000",message);									
	        								 }
	        																
	        							}            						
	        							JFrame frame= new JFrame();
	        		                    JOptionPane.showMessageDialog(frame,"ShutDown Everything");
	        							
	            					}catch(Exception yy){
	            						System.out.println("Error in ShutDown Everything");
	            						System.out.println(yy.getMessage());
	            						JFrame frame= new JFrame();
	        		                    JOptionPane.showMessageDialog(frame," Failed to ShutDown Everything");
	            					}																							
							    }
		            			
		            			
			            	}catch(Exception e){
			            		e.printStackTrace();
			            		System.out.println("Error in All CrossRoads ");
			            	};
		            	
		            }
		        });
		        thread.start();
				
				
				
				
			}
		});
		btnNewButton_13.setBounds(379, 90, 136, 23);
		panel_3.add(btnNewButton_13);
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab(" CSV ", null, panel_4, null);
		panel_4.setLayout(null);
		
		JButton btnNewButton_16 = new JButton("Create CSV");
		btnNewButton_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Thread thread = new Thread(new Runnable() {
		            @Override
		            public void run() { 
		            	try{		            									
		    				String csvFileName =textField_15.getText();
		    				String GoPath =textField_16.getText();
		    				String BackPath =textField_17.getText();
		    				String fileDestination =textField_18.getText();
		    				String Port =textField_19.getText();
		    				String range =textField_20.getText();
		    				
		        			GenerateCsv.generateCsvFile(fileDestination+csvFileName+".csv",GoPath,BackPath,Port,range);
		        			//generateCsvFile(String sFileName,String Gopath,String Backpath,String port,String Range)
		    				
		            	}catch(Exception e){
		            		e.printStackTrace();
		            		System.out.println("Error in CSV ");
		            	};
		            	

		            }
		        });
		        thread.start();
				
				
				
			}
		});
		btnNewButton_16.setBounds(584, 211, 148, 33);
		panel_4.add(btnNewButton_16);
		
		JLabel lblNewLabel_23 = new JLabel("File Name :");
		lblNewLabel_23.setBounds(10, 21, 71, 26);
		panel_4.add(lblNewLabel_23);
		
		textField_15 = new JTextField();
		textField_15.setBounds(132, 24, 148, 21);
		panel_4.add(textField_15);
		textField_15.setColumns(10);
		textField_15.setText("11000");
		
		JLabel lblNewLabel_24 = new JLabel("Path GO :");
		lblNewLabel_24.setBounds(10, 57, 71, 26);
		panel_4.add(lblNewLabel_24);
		
		textField_16 = new JTextField();
		textField_16.setBounds(132, 60, 408, 21);
		panel_4.add(textField_16);
		textField_16.setColumns(10);
		textField_16.setText("PATH,110001,120.212079,22.996998,15,20,50");
		
		JLabel lblNewLabel_25 = new JLabel("Path BACK :");
		lblNewLabel_25.setBounds(10, 93, 71, 15);
		panel_4.add(lblNewLabel_25);
		
		textField_17 = new JTextField();
		textField_17.setBounds(132, 90, 408, 21);
		panel_4.add(textField_17);
		textField_17.setColumns(10);
		textField_17.setText("PATH,110002,120.309756,23.035659,15,20,20");
		
		JLabel lblNewLabel_26 = new JLabel("File Destination :");
		lblNewLabel_26.setBounds(10, 125, 112, 15);
		panel_4.add(lblNewLabel_26);
		
		textField_18 = new JTextField();
		textField_18.setBounds(132, 121, 408, 21);
		panel_4.add(textField_18);
		textField_18.setColumns(10);
		textField_18.setText("c:\\\\");
		
		JLabel lblNewLabel_27 = new JLabel("Transfer port :");
		lblNewLabel_27.setBounds(10, 162, 91, 15);
		panel_4.add(lblNewLabel_27);
		
		textField_19 = new JTextField();
		textField_19.setBounds(132, 159, 96, 21);
		panel_4.add(textField_19);
		textField_19.setColumns(10);
		textField_19.setText("21201");
		
		JLabel lblNewLabel_28 = new JLabel("Range :");
		lblNewLabel_28.setBounds(10, 193, 71, 15);
		panel_4.add(lblNewLabel_28);
		
		textField_20 = new JTextField();
		textField_20.setText("25");
		textField_20.setBounds(132, 190, 96, 21);
		panel_4.add(textField_20);
		textField_20.setColumns(10);
		
		// keeps reference of standard output stream
        standardOut = System.out;
         
        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);
        
         btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				//textArea_1.setText("");			
			}
		});
		btnNewButton.setBounds(318, 64, 87, 23);
		contentPane.add(btnNewButton);
		
		  btnNewButton_1 = new JButton("Contact");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedIndex(0);
				//TCPmultiport.LastContact();
				System.out.println("ONLINE "+PingIP.fullconnection);
              	System.out.println("OFFLINE "+PingIP.zeroconnection);
			}
		});
		btnNewButton_1.setBounds(415, 64, 87, 23);
		contentPane.add(btnNewButton_1);
		
		
		JButton btnNewButton_6 = new JButton("MultiPing");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PingIP.fullconnection.clear();
				PingIP.zeroconnection.clear();
				
				
				String[] All3GIP=MSDB.getAll3G_IP();
				
				for(String t:All3GIP){
					
					PingIP.OtherThread(t);
					
				}
				
				try{
					
					//btnNewButton_1.doClick();
					new ReminderBeep(30);
					
					/*
					Thread.sleep(30 * 1000);
					String[] IPCIP =  MSDB.getIPCIP();
					
					for(String a:IPCIP){
        	    		System.out.println("IP "+a);
    	    			if(PingIP.fullconnection.contains(a)){
    	    				MSDB.updateIPCStatus(a,0);
    	    			}
    	    			
    	    			if(PingIP.zeroconnection.contains(a)){
    	    				MSDB.updateIPCStatus(a,1);
    	    			}    	    			
    	    		}
					*/
					
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("Error in MultiPing");
				}
				//long sec= 25*1000;
				 //Timer timer = new Timer();
				    //timer.schedule(btnNewButton_1.doClick(), 25 * 1000);
			}
		});
		btnNewButton_6.setBounds(512, 64, 87, 23);
		contentPane.add(btnNewButton_6);
		
		
		JButton btnNewButton_7 = new JButton("3.0 Comm");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedIndex(0);
				TCPmultiport.LastContact();
			}
		});
		btnNewButton_7.setBounds(609, 64, 104, 23);
		contentPane.add(btnNewButton_7);
		
		
		 btnNewButton_10 = new JButton("BusPrim Check");
		btnNewButton_10.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				
				BusPrimChecker();
			}
		});
		btnNewButton_10.setBounds(723, 64, 129, 23);
		contentPane.add(btnNewButton_10);
		
		JButton btnNewButton_11 = new JButton("DB Collector");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DB_ThreadPool  multiport =new DB_ThreadPool();	
			}
		});
		btnNewButton_11.setBounds(862, 64, 112, 23);
		contentPane.add(btnNewButton_11);
		
		JButton btnNewButton_14 = new JButton(" Test ");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Thread thread = new Thread(new Runnable() {
		            @Override
		            public void run() { 
		            	
		            	CrossRoadCenter_Sync.DataVerify_start();
		            	
		            }
		        });
		        thread.start();
				
	     
							
			}
		});
		btnNewButton_14.setBounds(318, 35, 87, 23);
		contentPane.add(btnNewButton_14);
		
		
		try { //get Local IP address
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress();
			lblNewLabel_1.setText(ip);			

			//printLog();			
									
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lblNewLabel_1.setText("Cannot detect local IP Address ");
		}
		
		try { //Get Variables From Properties
			
			if(Properties()){
				
				lblNewLabel_3.setText(userInterfaceIP);
				lblNewLabel_5.setText(DB_connect_string);
				MSDB mssql =new MSDB();
				
				TCPmultiport  multiport =new TCPmultiport();				
				
				//IPCIP =  MSDB.getIPCIP();
				ThreeGIP = MSDB.getAll3G_IP();
				MasterIP =MSDB.getAllMasters_IP();
				
				DBconnection();
				IPCconnection();
				CrossRoadCollector();
				
				
				if(mssql.dbConnection()){
					
				lblNewLabel_5.setForeground(new Color(0,255,150));
				};
				
				
				updateGUI();
				
				//UDPSender.Send("10.36.90.73","20000","AABB0100A0000D5F8501AACC00");
				
			}						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lblNewLabel_3.setText("Cannot detect local IP Address ");
		}
		
		
		
	}
	
	 private static void ClearText() {
	        Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                while (true) {
	                    
	                    try {
	                        Thread.sleep(1000*60*5);
	                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                	    Date date = new Date();        	    
	                	    int int_minutes=date.getMinutes();
	                        //btnNewButton.doClick();
	                        //btnNewButton_6.doClick();
	                	    if(int_minutes==0 ){
	                	    	
	                    	System.out.println("Server_UI Start Time "+Systemstart);
	                    	long diff = date.getTime() - Systemstart.getTime();	    
	                  		long diffSeconds = diff / 1000 % 60;
	                  		long diffMinutes = diff / (60 * 1000) % 60;
	                  		long diffHours = diff / (60 * 60 * 1000) % 24;
	                  		long diffDays = diff / (24 * 60 * 60 * 1000);
	                  		
	                  		System.out.print(diffDays + " days, ");
	                  		System.out.print(diffHours + " hours, ");
	                  		System.out.print(diffMinutes + " minutes, ");
	                  		System.out.print(diffSeconds + " seconds.");
	                  		
	                	    }
	                	    
	                    } catch (Exception ex) {
	                        ex.printStackTrace();
	                    }
	                }
	            }
	        });
	        thread.start();
	    }
	 
	 
	 private void DBconnection() {
	        Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                while (true) {
	                   
	                    try {
	                    	Thread.sleep(1000*90);
	                    	
	                    	MSDB mssql = new MSDB();
	                    	if(mssql.dbConnection()){	       	                    		
	            				lblNewLabel_5.setForeground(new Color(0,255,150));
	            				//System.out.println("DB Connection ");
	            				}else{
	            					lblNewLabel_5.setForeground(Color.RED);
	            				};
	            				updateGUI();
	                        
	                    } catch (InterruptedException ex) {
	                        ex.printStackTrace();
	                    }
	                }
	            }
	        });
	        thread.start();
	    }
	 
	 private void IPCconnection() {
	        Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                while (true) {
	                	
	                    try {
	                    	
	                    	//String IP="172.23.25.";
	        				PingIP.fullconnection.clear();
	        				PingIP.zeroconnection.clear();
	        				
	        				
	        				
	        				for(String t:ThreeGIP){
	        					
	        					PingIP.OtherThread(t);
	        					
	        				}
	        				
	        				
	        				
	                    	Thread.sleep(1000*60*1);
	                    	
	                    	System.out.println("IPC Connection Check");
	                    	//WebSiteCommands();
	                    	btnNewButton_10.doClick();
	                    	//TCPmultiport.LastContact2();
	                    	
	                    	for(String t:ThreeGIP){	                    		
	                    		if(PingIP.zeroconnection.contains(t)){
	                    			MSDB.updateIPCOnOff(t,5);
	                    			MSDB.updateIPCStatus(t,7);
	                    		}
	                    		
	                    		if(PingIP.fullconnection.contains(t)){
	                    			MSDB.updateIPCOnOff(t,4);
	                    			//MSDB.updateIPCStatus(t,8);
	                    		}
	                    	}
	                    	
	                    	//System.out.println("ONLINE "+PingIP.fullconnection);
	                      	//System.out.println("OFFLINE "+PingIP.zeroconnection);
	                    	
	                    } catch (Exception ee) {
	                    	
	                        ee.printStackTrace();
	                        System.out.println("IPCconnection Error");
	                    }
	                }
	            }
	        });
	        thread.start();
	    }
	 
	 private void BusPrimChecker() {
	        Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	
	            	try{
	            		
						
						for(String a:ThreeGIP){
							if(MSDB.check0F04status(a)){
								MSDB.updateIPCStatus(a,7);
							}
							
						
						}
						
							
							String[] allMasterAddr=MSDB.getAllMasters_Addr();
							
							for(String m: allMasterAddr){
								//System.out.println("Group "+m);
								
								 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
								 String MasterIP=MSDB.getCrossRoad_IP(m);
								 for(String c:CrossRoadAddr){
									 //System.out.println("CrossRoad Addr "+c+" Send to "+MasterIP);									 
									byte[] cmd2=MessageCreator.createpackage("01",c, "5F9001");  //1.Seq 2.Addr 3.�ʥ]���e
									String message=Protocol.bytesToHex(cmd2);
								 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
										UDPSender.Send(MasterIP,"20000",message);									
								 }
																
							}

	
						
						//new ReminderBeep2(30);
	            	}catch(Exception e){
	            		e.printStackTrace();
	            		System.out.println("Error in BusPrimChecker ");
	            		
	            	}
	            	
	            }
	        });
	        thread.start();
	    }
	 
	 private void CrossRoadCollector() {
	        Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	while(true){	            		
	            		try{
	            			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                	    Date date = new Date();        	    
	                	    int int_minutes=date.getMinutes();
	                	    
	                	    if(int_minutes==0 ){  // Every Hour 
	                	    		                	    	
	                	    	DB_ThreadPool  multiport =new DB_ThreadPool();	
	                	    	
	                	    }
	                	    
	            			Thread.sleep(60*1000);
	            			
		            	}catch(Exception e){
		            		e.printStackTrace();
		            		System.out.println("Error in CrossRoadCollector ");
		            		
		            	}
	            	}
	            }
	        });
	        thread.start();
	    }
	 
	 private void Data_Compare() {
	        Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            	while(true){	            		
	            		try{
	            			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                	    Date date = new Date();
	                	    
	                	    int int_hours=date.getHours();
	                	    
	                	    if(int_hours==0 ){  // Only at midnight
	                	    		                	    	
	                	    	
	                	    	
	                	    }
	                	    
	            			Thread.sleep(60*1000);
	            			
		            	}catch(Exception e){
		            		e.printStackTrace();
		            		System.out.println("Error in Data_Compare ");
		            		
		            	}
	            	}
	            }
	        });
	        thread.start();
	    }
	 
	 
	 public void updateGUI() {
		   if (!SwingUtilities.isEventDispatchThread()) {
		     SwingUtilities.invokeLater(new Runnable() {
		       @Override
		       public void run() {
		          updateGUI();
		       }
		     });
		   }
		   //Now edit your gui objects
		   //lblNewLabel_5.setForeground(Color.PINK);
		}
	
	
	public static boolean Properties() {
		  
		   
	     Properties prop = new Properties();
		 InputStream input = null;
		 
			try {
				 					 
				input = new FileInputStream("C://TainanBusPrime.properties");
							
				prop.load(input);
			
					userInterfaceIP=prop.getProperty("userInterfaceIP");
					String STRuserInterfacePort=prop.getProperty("userInterfacePort");
					userInterfacePort= Integer.parseInt(STRuserInterfacePort);
					String STRlistenportforUser =prop.getProperty("listenportforUser");
					listenportforUser=Integer.parseInt(STRlistenportforUser);
					String STRlistenportforIPC =prop.getProperty("listenportforIPC");
					listenportforIPC=Integer.parseInt(STRlistenportforIPC);
					
					    DBDriver=prop.getProperty("MSSQL_Driver");
				        DB_connect_string=prop.getProperty("MSSQL_connect_string");
						DB_userid=prop.getProperty("MSSQL_userid");
						DB_password=prop.getProperty("MSSQL_password");
						DB_database_name=prop.getProperty("MSSQL_DatabaseName");
						
						localDBIP=prop.getProperty("LocalDB_IP");
					 	
						CrossRoadDB_userid=prop.getProperty("CrossRoadDB_userid");
						CrossRoadDB_password=prop.getProperty("CrossRoadDB_password");		
						
						 
						
						/*
					System.out.println(" userInterfaceIP "+userInterfaceIP);
					System.out.println(" userInterfacePort "+userInterfacePort);
					System.out.println(" listenportforUser "+listenportforUser);
					System.out.println(" listenportforIPC "+listenportforIPC);
		    			*/
						
		    			return true;
		    			
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.println("Error in Properties");
				return false;
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Error in IO Properties");
						return false;
					}
				}
			}
	   		  		   
 }
	private void WebSiteCommands() {
		  Thread thread = new Thread(new Runnable() {
	            @Override
	            public void run() {
	            		            		
	            		try{
	            			//[OnOff],[Time],[admin]
	            			String[] usercommands=MSDB.getLatestCommand();
	            			if(usercommands[2].contains("00")){
	            				// 5 AllOn 6 AllOff 7 Scan
	            				System.out.println("New Command from User Get to it !!");
	            				MSDB.GotTheCommand(usercommands[1]);  // Mark the command as acknowledged
	            				
	            				/*
	            				if( Switchip.isEmpty()){					
	        						JFrame frame= new JFrame();
	        	                    JOptionPane.showMessageDialog(frame,"Which IP ?");
	        					}else{
	        						if (OnOff==1){
	        						UDPSender.Send(Switchip,"20000","AABB01FFFF000D5F8000AACCA4");
	        						JFrame frame= new JFrame();
	        	                    JOptionPane.showMessageDialog(frame,"Sent Off Message to "+Switchip);
	        						
	        						}else if(OnOff==0){
	        						UDPSender.Send(Switchip,"20000","AABB01FFFF000D5F8001AACCA5");
	        						JFrame frame= new JFrame();
	        	                    JOptionPane.showMessageDialog(frame,"Sent On Message to "+Switchip);
	        						}									
	        						
	        					}
	            				*/
	            				
	            				
	            				if(usercommands[0].contains("5")){
	            					System.out.println("TurnOn Everything");
	            					//UDPSender.Send(Switchip,"20000","AABB01FFFF000D5F8001AACCA5");
	            					
	            					try{
	            						String[] allMasterAddr=MSDB.getAllMasters_Addr();
	        							
	        							for(String m: allMasterAddr){
	        								//System.out.println("Group "+m);
	        								
	        								 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
	        								 String MasterIP=MSDB.getCrossRoad_IP(m);
	        								 for(String c:CrossRoadAddr){
	        									 //System.out.println("CrossRoad Addr "+c+" Send to "+MasterIP);									 
	        									byte[] cmd2=MessageCreator.createpackage("01",c, "5F800101");  //1.Seq 2.Addr 3.�ʥ]���e
	        									String message=Protocol.bytesToHex(cmd2);
	        								 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
	        										UDPSender.Send(MasterIP,"20000",message);									
	        								 }
	        																
	        							}            						
	           						
	            					}catch(Exception yy){
	            						System.out.println("Error in TurnOn Everything");
	            						System.out.println(yy.getMessage());
	            					}
	            					
	            					
	            				}else if(usercommands[0].contains("6") || usercommands[0].contains("9")){
	            				
	            					System.out.println("ShutDown Everything");
	            					//UDPSender.Send(Switchip,"20000","AABB01FFFF000D5F8000AACCA4");
	            					
	            					try{
	            						String[] allMasterAddr=MSDB.getAllMasters_Addr();
	        							
	        							for(String m: allMasterAddr){
	        								//System.out.println("Group "+m);
	        								
	        								 String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
	        								 String MasterIP=MSDB.getCrossRoad_IP(m);
	        								 for(String c:CrossRoadAddr){
	        									 //System.out.println("CrossRoad Addr "+c+" Send to "+MasterIP);									 
	        									byte[] cmd2=MessageCreator.createpackage("01",c, "5F800100");  //1.Seq 2.Addr 3.�ʥ]���e
	        									String message=Protocol.bytesToHex(cmd2);
	        								 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
	        										UDPSender.Send(MasterIP,"20000",message);									
	        								 }
	        																
	        							}            						
	           						
	            					}catch(Exception yy){
	            						System.out.println("Error in ShutDown Everything");
	            						System.out.println(yy.getMessage());
	            					}
	            					
	            					
	            					
	            				}else if(usercommands[0].contains("7")){
	            					System.out.println("Scan The Switches Everything");
	            					
	            					try{
	            						String[] allMasterAddr=MSDB.getAllMasters_Addr();
	        							
	        							for(String m: allMasterAddr){
	        								//System.out.println("Group "+m);
	        								
	        								String[] CrossRoadAddr=MSDB.getCrossRoadAddr_Group(m);
	        								 String MasterIP=MSDB.getCrossRoad_IP(m);
	        								 for(String c:CrossRoadAddr){
	        									 
	        									 String StatusType_Addr=MSDB.getCrossRoad_IPCStatus(c);
	        									 System.out.println("StatusType "+StatusType_Addr);
	             								
	             								if(StatusType_Addr.contains("9")){
	             									System.out.println("Turn On "+c);
	             									byte[] cmd2=MessageCreator.createpackage("01",c, "5F800101");  //1.Seq 2.Addr 3.�ʥ]���e
		        									String message=Protocol.bytesToHex(cmd2);
		        								 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
		        										UDPSender.Send(MasterIP,"20000",message);		             									
	             									
	             								} else if(StatusType_Addr.contains("8")){
	             									System.out.println("Turn Off "+c);
	             									byte[] cmd2=MessageCreator.createpackage("01",c, "5F800100");  //1.Seq 2.Addr 3.�ʥ]���e
		        									String message=Protocol.bytesToHex(cmd2);
		        								 		//System.out.println("Message  " + Protocol.bytesToHex(cmd2));
		        										UDPSender.Send(MasterIP,"20000",message);	             									
	             								
	             								}
	        									 
	        																	
	        								 }
	        								
	        								 							
	        							}     
	        							
	        							
	        							
	        							
	           						
	            					}catch(Exception yy){
	            						System.out.println("Error in Scan Everything");
	            						System.out.println(yy.getMessage());
	            					}
	            					
	            					
	            					
	            					
	            					
	            					
	            					
	            				}
	            				
	            				
	            				
	            			}
	                	 
	            			
		            	}catch(Exception e){
		            		e.printStackTrace();
		            		System.out.println("Error in WebSiteCommands ");
		            		
		            	}
	            	
	            }
	        });
	        thread.start();
		
		
		
	}
}
