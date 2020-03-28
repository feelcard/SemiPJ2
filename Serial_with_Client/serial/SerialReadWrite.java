package serial;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import msg.Client;
import msg.Client.Sender;
import msg.Msg;

public class SerialReadWrite implements SerialPortEventListener {

   CommPortIdentifier commPortIdentifier;
   CommPort comPort;
   InputStream in;
   BufferedInputStream bin;
   OutputStream out;
   String id;// "00000000";
   String data; // "0000000000000000";
   String msg; // id+data;
   static Client client;
   static Socket socket;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getData() {
      return data;
   }

   public void setData(String data) {
      this.data = data;
   }

   public void setMsg() {
      this.msg = getId()+getData();
   }
   
   public String getMsg() {
      return this.msg;
   }

   public SerialReadWrite() {
   }

   public SerialReadWrite(String portName) throws Exception {
      commPortIdentifier = CommPortIdentifier.getPortIdentifier(portName);
      System.out.println("Identifier Com Port!");
      connect();
      System.out.println("Connect Com Port!");
      new Thread(new Serialwrite()).start();
      System.out.println("Start Can Network!");
   }

   public void connect() throws Exception {

      if (commPortIdentifier.isCurrentlyOwned()) { // 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙構占� 占쌍댐옙占쏙옙
         System.out.println("Port is currently in use...");
      } else {
         comPort = commPortIdentifier.open(this.getClass().getName(), 5000);
         if (comPort instanceof SerialPort) {
            SerialPort serialPort = (SerialPort) comPort;
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(921600, // 占쏙옙탉撻占�
                  SerialPort.DATABITS_8, // 占쏙옙占쏙옙占쏙옙 占쏙옙트
                  SerialPort.STOPBITS_1, // stop 占쏙옙트
                  SerialPort.PARITY_NONE); // 占싻몌옙티
            in = serialPort.getInputStream();
            bin = new BufferedInputStream(in);
            out = serialPort.getOutputStream();
         } else {
            System.out.println("This port is not SerialPort.");
         }
      }

   }

   @Override
   public void serialEvent(SerialPortEvent event) {

      switch (event.getEventType()) {
      case SerialPortEvent.BI:
      case SerialPortEvent.OE:
      case SerialPortEvent.FE:
      case SerialPortEvent.PE:
      case SerialPortEvent.CD:
      case SerialPortEvent.CTS:
      case SerialPortEvent.DSR:
      case SerialPortEvent.RI:
      case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
         break;
      case SerialPortEvent.DATA_AVAILABLE:
         byte[] readBuffer = new byte[128];

         try {

            while (bin.available() > 0) {
               int numBytes = bin.read(readBuffer);
            }

            String ss = new String(readBuffer);
            System.out.println("Receive Low Data:" + ss + "||");
          
            if(ss.charAt(1)=='U') {
               String data =parseData(ss);
               client.sendData(data.substring(0, 8),data.substring(8));
            }
         

         } catch (Exception e) {
            e.printStackTrace();
         }
         break;
      }

   }

   public void send(String msg) {
      new Thread(new Serialwrite(msg)).start();
   }
   
   public void adminSend(String msg) {
      new Thread(new Serialwrite(msg)).start();
   }

   class Serialwrite implements Runnable {

      String data;

      public Serialwrite() {
         this.data = ":G11A9\r";
      }

      public Serialwrite(String msg) {
         this.data = convertData(msg);
      }

      public String convertData(String msg) {
         String id = "00000000";
         //msg = msg.toUpperCase();
         System.out.println("convertData msg: "+msg);
         rightList.add(msg);
         if(msg.length() == 1) {
            msg=id+"000000000000000"+msg;
         }else if(msg.length() == 2) {
            msg=id+"00000000000000"+msg;
         }else if(msg.length() == 3) {
            msg=id+"0000000000000"+msg;
         }else {
            System.out.println("msg가 null입니다.");
         }
         msg = "W28" + msg;

         char c[] = msg.toCharArray();
         int checkSum = 0;

         for (char ch : c) {
            checkSum += ch;
         }

         checkSum = (checkSum & 0xFF);

         String result = ":";
         result += msg + Integer.toHexString(checkSum).toUpperCase() + "\r";
         leftList.add(result);
         System.out.println("result: "+result);
         return result;

      }

      @Override
      public void run() {

         byte[] outData = data.getBytes();

         try {
            out.write(outData);
         } catch (IOException e) {
            e.printStackTrace();
         }

      }

   }

   public static String parseData(String ss) {
      StringBuilder sb = new StringBuilder(ss);
      String id= sb.substring(4, 12);
      String txt = sb.substring(26, 28);
      txt=Integer.parseInt(txt,16)+"";
      System.out.println("id:"+id+" "+ "txt : "+txt);
      return id+txt;
   }
   
   Frame frame;
	List leftList,rightList;
	Panel panel,centerPanel;
	TextField txtTx;
	Label lb;
	

	
	ArrayList<String> msgList;
	ArrayList<String> sendtoECUList;
	
	public void receiveList(
	HashMap<String,ObjectOutputStream> maps) {
		Set<String> keys = maps.keySet();
		Iterator<String> skeys = 
				keys.iterator();
		msgList = new ArrayList<>();
		while(skeys.hasNext()) {
			String msg = skeys.next();
			msgList.add(msg);
			rightList.add(msg);
		}
		
	}
	
	public void txtList(
			HashMap<String,ObjectOutputStream> maps) {
				Set<String> keys = maps.keySet();
				Iterator<String> skeys = 
						keys.iterator();
				sendtoECUList = new ArrayList<>();
				while(skeys.hasNext()) {
					String msg = skeys.next();
					leftList.add(msg);
			
				}
		
				
			}
			
	
	public void consoleStart() {
		frame = new Frame();
		leftList = new List();
		rightList = new List();
		panel = new Panel();
		centerPanel = new Panel();
		txtTx = new TextField(8);
		lb = new Label("접속자수:0");
		
		panel.add(lb);
		panel.add(txtTx);

		
		
		frame.add(panel,"North");
		centerPanel.setLayout(
				new GridLayout(1, 2));
		centerPanel.add(leftList);
		centerPanel.add(rightList);
		frame.add(centerPanel,"Center");
		
		frame.setSize(500,400);
		frame.setVisible(true);
		
		
		
		rightList.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				String msg = 
						msgList.get(
					         (int)e.getItem()
						);
				txtTx.setText(msg);
				
			}
		});
		
			leftList.addItemListener(new ItemListener() {
						
			@Override
			public void itemStateChanged(ItemEvent e) {
				String txt = 
						sendtoECUList.get(
					         (int)e.getItem()
						);
				txtTx.setText(txt);
				
			}
		});
		
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
				if(socket != null) {
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
   
   public static void main(String[] args) {

      SerialReadWrite sc = null;
     
      try {
         sc = new SerialReadWrite("COM9");
         sc.consoleStart();
         client = new Client("ip", 8888);
         new Thread(new Runnable() {
         
         @Override
         public void run() {
            client.startClient();
            
         }
      }).start();
         
         socket= client.getSocket();
       String vel = client.getVel();
       final SerialReadWrite finalsc = sc;
       new Thread(new Runnable(){
          
          @Override
          public void run() {
             while(true){
               
                if(client.getSerialThreadControl() && client.getVel() !=null){
                   System.out.println("send to ECU");
                   finalsc.adminSend(client.getVel());
                  client.setSerialThreadControl(false);    
                }
                try {
                  Thread.sleep(500);
               } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
                continue;
               
             }
             
          }
       }).start();
      
         
      } catch (Exception e) {
         System.out.println("Connect Fail !");
         e.printStackTrace();
      }

   }

}