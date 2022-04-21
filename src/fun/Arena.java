package fun;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Arena {
	static JFrame sim = new JFrame();
	
	static JPanel panel = new JPanel();
	
	static int posX;
	static int posY;
	static int sizeX;
	static int sizeY;
	
	public Arena(int x, int y, int width, int height) throws InterruptedException {
		posX = x;
		posY = y;
		sizeX = width;
		sizeY = height;
		
		int frameWid = 1570;
		int frameHei = 1000;
		
		panel.setLayout(null);
		panel.setEnabled(false);
		panel.setBackground(new Color(255,255,255));
		
		
		sim.add(panel);
		sim.setSize(frameWid,frameHei);
		sim.setLocation(-20,0);
		sim.getContentPane().setBackground(new Color(255, 255, 255));
		sim.setComponentZOrder(panel,0);
		
		panel.addMouseListener(new MouseListener() {
	        public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) { }
	        public void mouseEntered(MouseEvent me) { }
	        public void mouseExited(MouseEvent me) { }
	        public void mouseClicked(MouseEvent me) { 
	        	System.out.println(MouseInfo.getPointerInfo().getLocation());
	        }
	        
	    });
		
		
		//Circle ha = new Player(600,400,1);
		//panel.add(ha);
		
		Circle jd = new Player(400,400,0);
		panel.add(jd);
		
		for(int i = 0; i < 1000; i++) {
			Circle a = new Circle((int)(Math.random()*400 + 200),(int)(Math.random()*300 + 250), Math.random()*3 - 6, Math.random()*3 - 6, (int) (Math.random() + 0.01));
			panel.add(a);
		}
		
		Wall top = new Wall(0,0,frameWid,posY);
		panel.add(top);
		Wall right = new Wall(posX + sizeX,0,frameWid,frameHei);
		panel.add(right);
		Wall bottom = new Wall(0,posY + sizeY,frameWid,frameHei);
		panel.add(bottom);
		Wall left = new Wall(0,0,posX,frameHei);
		panel.add(left);
		
		Wall obstacle1 = new Wall(675,0,50,375);
		panel.add(obstacle1);
		
		Wall obstacle2 = new Wall(675,425,50,300);
		panel.add(obstacle2);
		
		Wall obstacle3 = new Wall(675,350,50,100);
		panel.add(obstacle3);
		
		panel.setVisible(true);
		sim.setVisible(true);
		
		
		Thread.sleep(3000);
		Circle.remove(obstacle3);
		
		
		graphDensity();
	}
	
	public static void graphDensity() {
		Thread thread = new Thread() {
			public void run() {
				Wall leftLabel = new Wall(300, 600, 300, 100);
				Wall rightLabel = new Wall(800, 600, 300, 100);
				panel.add(leftLabel);
				panel.add(rightLabel);
				panel.setComponentZOrder(leftLabel, 0);
				panel.setComponentZOrder(rightLabel,0);
				Graph g = new Graph();
				int time = 0;
				while(true) {
					sim.repaint();
					double leftDensity = ParticleMethods.getDensity(posX,posY,700, posY + sizeY);
					double rightDensity = ParticleMethods.getDensity(700,posY,posX + sizeX, posY + sizeY);
					leftLabel.setText("Count: " + String.valueOf(ParticleMethods.particlesInside(posX,posY,700, posY + sizeY)) + ", Density: " + String.valueOf(leftDensity));
					rightLabel.setText("Count: " + String.valueOf(ParticleMethods.particlesInside(700,posY,posX + sizeX, posY + sizeY)) + ", Density: " + String.valueOf(rightDensity));
					g.point(time, (int) ((double) leftDensity * 500), new Color(255, 0, 0));
					g.point(time, (int) ((double) rightDensity * 500), new Color(0, 0, 255));
					
					time += 1;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.start();
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Arena(200, 200, 1000, 400);
		
	}
}
